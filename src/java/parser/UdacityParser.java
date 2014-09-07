/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domen.CreativeWork;
import domen.Duration;
import domen.Organization;
import domen.Person;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import persistence.RDFModel;
import util.Constants;
import util.URIGenerator;

/**
 *
 * @author ANA
 */
public class UdacityParser extends Parser{
    
    public static final String UDACITY= "https://www.udacity.com/public-api/v0/courses";
    

    @Override
    public List<CreativeWork> parse() throws IOException, URISyntaxException, ParseException {
           List<CreativeWork> list = new ArrayList<>();
        JsonArray searchResults = ParserUtil.vratiPodatke(UDACITY, "courses");
        for (JsonElement jsonElement : searchResults) {
            //    System.out.println(searchResults.size() + "sizeee");
            CreativeWork creativeWork = parseCreativeWork(jsonElement);
            RDFModel.getInstance().save(creativeWork);
            list.add(creativeWork);
        }
        System.out.println(list.size() + "list");
        return list;
    }
    
    static CreativeWork parseCreativeWork(JsonElement jsonElement) throws URISyntaxException {
        CreativeWork creativeWork = new CreativeWork();
        //    System.out.println("Ovo je jsonElement" + jsonElement.toString());
        String id = jsonElement.getAsJsonObject().get("key").getAsString();
     //   System.out.println(id + " id");
        String name = jsonElement.getAsJsonObject().get("title").getAsString();
     //   System.out.println(name + " title");

        if (name != null) {
            creativeWork.setName(name);
        }
        String description = jsonElement.getAsJsonObject().get("subtitle").getAsString();
   //     System.out.println(description + " subtitle");
        if (description != null) {
            creativeWork.setDescription(description);
        }
        String targetAudience = jsonElement.getAsJsonObject().get("level").getAsString();
   //     System.out.println(targetAudience + " target");
        if (targetAudience != null) {
            creativeWork.setTypicalAgeRange(targetAudience);
        }
        int expected_duration = jsonElement.getAsJsonObject().get("expected_duration").getAsInt();
    //    System.out.println(expected_duration + " koliko traje");

        String expected_duration_unit = jsonElement.getAsJsonObject().get("expected_duration_unit").getAsString();
    //    System.out.println(expected_duration_unit + " dana");
        String d =expected_duration +" "+expected_duration_unit;
         
        if (d != null) {
             String [] niz=d.split(" ");
            String trajanje=niz[0]+Character.toUpperCase(niz[1].charAt(0));
            Duration duration=new Duration();
            duration.setUri(URIGenerator.generate(duration));
            duration.setDescription(trajanje);
            creativeWork.setDuration(duration);
        }
       
 JsonArray instructors = jsonElement.getAsJsonObject().get("instructors").getAsJsonArray();
        for (JsonElement jsonElement1 : instructors) {
            Person person = new Person();
            String imeOsobe = jsonElement1.getAsJsonObject().get("name").getAsString();
            person.setName(imeOsobe);
            person.setUri(URIGenerator.generate(person));
            creativeWork.getAuthors().add(person);
    //        System.out.println(imeOsobe + " instruktor");
        }

        JsonArray affiliates = jsonElement.getAsJsonObject().get("affiliates").getAsJsonArray();

        for (JsonElement jsonElement2 : affiliates) {
            Organization organization = new Organization();
            String imeOsobe = jsonElement2.getAsJsonObject().get("name").getAsString();
            organization.setName(imeOsobe);
            organization.setUri(URIGenerator.generate(organization));
            creativeWork.getPublishers().add(organization);
     //       System.out.println(imeOsobe + " organizacija");
        }
        creativeWork.setUri(URIGenerator.generate(creativeWork));
        return creativeWork;
    }
    
}
