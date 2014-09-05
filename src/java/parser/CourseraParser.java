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
public class CourseraParser extends Parser{
   
 public static final String COUSERA="https://api.coursera.org/api/catalog.v1/courses?id={0}&includes=sessions,instructors,universities&fields=instructor,language,shortDescription,previewLink,targetAudience";
  public static final String INSTRUCTORS="https://api.coursera.org/api/catalog.v1/instructors?id={0}";
 public static final String UNIVERSITIES ="https://api.coursera.org/api/catalog.v1/universities?id={0}&fields=name";
  public static final String SESSIONS="https://api.coursera.org/api/catalog.v1/sessions?id={0}&fields=startDay,startMonth,startYear,durationString";
 public static final String LISTAKURSEVA="https://api.coursera.org/api/catalog.v1/courses";
 
 @Override
    public  List<CreativeWork> parse() throws IOException,
            URISyntaxException, ParseException {
        List<CreativeWork> list = new ArrayList<>();
        String stringResponse =COUSERA;
        for (int i = 1; i < 4; i++) {
            String url = MessageFormat.format(COUSERA, i);
//            String c = Integer.toString(i);
//            String url = stringResponse.replace("#", c);
            System.out.println(url);
            //promeni
            JsonArray searchResults = ParserUtil.vratiPodatke(url, "elements");
            System.out.println("rez"+searchResults);
            if (!searchResults.isJsonNull()) {
                for (JsonElement jsonElement : searchResults) {

                    CreativeWork creativeWork = parseCreativeWork(jsonElement);
                    System.out.println("dodao");
                    RDFModel.getInstance().save(creativeWork);
                    list.add(creativeWork);
                }
            }
        }
        System.out.println(list.size() + "list");
        return list;
    }
       public  List<CreativeWork> parse2sAlISTOM() throws IOException,
            URISyntaxException, ParseException {
        List<CreativeWork> list = new ArrayList<>();
        JsonArray searchResults = ParserUtil.vratiPodatke(LISTAKURSEVA, "elements");
        String stringResponse =COUSERA;
        for (JsonElement jsonElement : searchResults) {
          
            int id= jsonElement.getAsJsonObject().get("id").getAsInt();
            System.out.println(id+"idd");
          String c = Integer.toString(id);
            String url = stringResponse.replace("#", c);
            System.out.println(url);
            //promeni
            JsonArray searchResults2 = ParserUtil.vratiPodatke(url, "elements");
            System.out.println("rez"+searchResults);
//            if (!searchResults.isJsonNull()) {
//                for (JsonElement jsonElement1 : searchResults2) {
//
//                    CreativeWork creativeWork = parseCreativeWork(jsonElement1);
//                    System.out.println("dodao");
//                    RDFModel.getInstance().save(creativeWork);
//                    list.add(creativeWork);
//                }
//            }
        }
        System.out.println(list.size() + "list");
        return list;
    }
    
     static CreativeWork parseCreativeWork(JsonElement jsonElement) throws IOException, ParseException, URISyntaxException {
        CreativeWork creativeWork = new CreativeWork();
     //   System.out.println("Ovo je jsonElement" + jsonElement.toString());
        int id = jsonElement.getAsJsonObject().get("id").getAsInt();
        System.out.println(id + "id");

        String name = jsonElement.getAsJsonObject().get("name").getAsString();
        System.out.println(name + "name");
        if (name != null) {
            creativeWork.setName(name);
        }else{
        creativeWork.setName("/");
        }

        String language = jsonElement.getAsJsonObject().get("language").getAsString();
        System.out.println(language + "language");
        if (language != null) {
            creativeWork.setInLanguage(language);
        }else{
        creativeWork.setInLanguage("/");
        }


        int targetAudience = jsonElement.getAsJsonObject().get("targetAudience").getAsInt();
        if (targetAudience > 0) {
            String ta = "";
            switch (targetAudience) {
                case 1:
                  //  ta = "Advanced undergraduates or beginning graduates";
                    ta="beginner";
                    break;
                case 2:
                  //  ta = "Advanced graduates";
                    ta="advanced";
                    break;
                case 3:
                 //   ta = "Other";
                    ta="intermediate";
                    break;

            }
          //  System.out.println(ta + "ta");
            creativeWork.setTypicalAgeRange(ta);
        }else{
        creativeWork.setTypicalAgeRange("/");
        }

        String shortDescription = jsonElement.getAsJsonObject().get("shortDescription").getAsString();
      //  System.out.println(shortDescription + "shortDesritpion");
        if (shortDescription != null) {
            creativeWork.setDescription(shortDescription);
        }else{
        creativeWork.setDescription("/");
        }

         JsonObject links = jsonElement.getAsJsonObject().get("links").getAsJsonObject();

        int universities = links.get("universities").getAsInt();
        if(universities>0){
          // System.out.println(universities + "universities");
        Organization organization = parseOrganization(universities);
        creativeWork.getPublishers().add(organization);
        }else{
          creativeWork.getPublishers().add(new Organization());
        }
        
       
        int instustors = links.get("instructors").getAsInt();
        if(instustors>0){
     //   System.out.println(instustors);
        Person person = parsePerson(instustors);
        creativeWork.getAuthors().add(person);
        }else{
        creativeWork.getAuthors().add(new Person());
        }
        JsonArray sessions = links.get("sessions").getAsJsonArray();
        for (JsonElement jsonElement1 : sessions) {
            int s = jsonElement1.getAsJsonPrimitive().getAsInt();
            CreativeWork child = parseSession(s);
            creativeWork.getChildren().add(child);
            child.setParent(creativeWork);
            System.out.println(s + "id sesije");
        }
 
        creativeWork.setUri(URIGenerator.generate(creativeWork));

        System.out.println(links + "sdfds");

        
        return creativeWork;
    }
      private static CreativeWork parseSession(int s) throws IOException, ParseException, URISyntaxException {
       CreativeWork child = new CreativeWork();
        String link = SESSIONS;
//        String c = Integer.toString(s);
//        String url = link.replace("#", c);
        String url = MessageFormat.format(SESSIONS, s);
        JsonArray searchResults = ParserUtil.vratiPodatke(url, "elements");

        System.out.println("u nizu za event ima" + searchResults.size());
        for (JsonElement jsonElement : searchResults) {
            String d = jsonElement.getAsJsonObject().get("durationString").getAsString();
          //  10 weeks
            
            Duration duration=new Duration();
             duration.setDescription(d);
            duration.setUri(URIGenerator.generate(duration));
           
            int startDay = jsonElement.getAsJsonObject().get("startDay").getAsInt();
            int startMonth = jsonElement.getAsJsonObject().get("startMonth").getAsInt();
            int startYear = jsonElement.getAsJsonObject().get("startYear").getAsInt();
            child.setDuration(duration);
            System.out.println(duration.getDescription()+"trajanje");
            String date = startYear + "-" + startMonth + "-" + startDay;
            Date dateModified = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            child.setDateCreated(dateModified);
            child.setUri(URIGenerator.generate(child));
         //   RDFModel.getInstance().save(event);
        }

        return child;
    }

    private static Person parsePerson(int instustors) throws IOException, URISyntaxException {
        Person person = new Person();
        String link = INSTRUCTORS;
//        String c = Integer.toString(instustors);
//        String url = link.replace("#", c);
        String url = MessageFormat.format(INSTRUCTORS, instustors);
        System.out.println(url);
        JsonArray searchResults = ParserUtil.vratiPodatke(url, "elements");
      //  System.out.println("u nizu za peson ima" + searchResults.size());
        for (JsonElement jsonElement : searchResults) {
            String firstName = jsonElement.getAsJsonObject().get("firstName").getAsString();
            String lastName = jsonElement.getAsJsonObject().get("lastName").getAsString();
            String name = firstName + " " + lastName;
            person.setName(name);
            person.setUri(URIGenerator.generate(person));
            System.out.println(name + " ime osobe");
        }
        //RDFModel.getInstance().save(person);
        return person;
    }

    private static Organization parseOrganization(int universities) throws IOException, URISyntaxException {
        Organization organization = new Organization();
        String link = UNIVERSITIES;
//        String c = Integer.toString(universities);
//        String url = link.replace("#", c);
        String url = MessageFormat.format(UNIVERSITIES, universities);
        JsonArray searchResults = ParserUtil.vratiPodatke(url, "elements");
   //     System.out.println("u nizu za organization" + searchResults.size());
        for (JsonElement jsonElement : searchResults) {
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            organization.setName(name);
            organization.setUri(URIGenerator.generate(organization));
            System.out.println(name + " ime organizacijee");

        }
   //    RDFModel.getInstance().save(organization);
        return organization;
    }
}
