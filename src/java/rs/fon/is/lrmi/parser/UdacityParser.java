/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rs.fon.is.lrmi.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import rs.fon.is.lrmi.domen.CreativeWork;
import rs.fon.is.lrmi.domen.Duration;
import rs.fon.is.lrmi.domen.Organization;
import rs.fon.is.lrmi.domen.Person;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.fon.is.lrmi.persistence.RDFModel;
import rs.fon.is.lrmi.util.Constants;
import rs.fon.is.lrmi.util.URIGenerator;

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
            CreativeWork creativeWork = parseCreativeWork(jsonElement);
            RDFModel.getInstance().save(creativeWork);
            list.add(creativeWork);
            System.out.println("dodao");
        }
        return list;
    }
    
    static CreativeWork parseCreativeWork(JsonElement jsonElement) throws URISyntaxException {
        CreativeWork creativeWork = new CreativeWork();
        String id = jsonElement.getAsJsonObject().get("key").getAsString();
        String name = jsonElement.getAsJsonObject().get("title").getAsString();

        if (name != null) {
            creativeWork.setName(name);
        }
        String description = jsonElement.getAsJsonObject().get("subtitle").getAsString();
        if (description != null) {
            creativeWork.setDescription(description);
        }
        String targetAudience = jsonElement.getAsJsonObject().get("level").getAsString();
        if (targetAudience != null) {
            creativeWork.setTypicalAgeRange(targetAudience);
        }
        int expected_duration = jsonElement.getAsJsonObject().get("expected_duration").getAsInt();

        String expected_duration_unit = jsonElement.getAsJsonObject().get("expected_duration_unit").getAsString();
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
        }

        JsonArray affiliates = jsonElement.getAsJsonObject().get("affiliates").getAsJsonArray();

        for (JsonElement jsonElement2 : affiliates) {
            Organization organization = new Organization();
            String imeOsobe = jsonElement2.getAsJsonObject().get("name").getAsString();
            organization.setName(imeOsobe);
            organization.setUri(URIGenerator.generate(organization));
            creativeWork.getPublishers().add(organization);
        }
        
         boolean objLink = jsonElement.getAsJsonObject().has("homepage");
        System.out.println(objLink+"link");
        if (objLink == true) {
            String link = jsonElement.getAsJsonObject().get("homepage").getAsString();
            if (link != null) {
                System.out.println(link+"usao");
                creativeWork.setLicense(new URI(link));
            } 
        }
         
        
        
        Organization o=new Organization();
        o.setName("Udacity");
        o.setUri(new URI("https://www.udacity.com/"));
        System.out.println("ovde");
        creativeWork.setProvider(o);
       
        creativeWork.setUri(URIGenerator.generate(creativeWork));
        return creativeWork;
    }
    
}
