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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rs.fon.is.lrmi.persistence.RDFModel;
import rs.fon.is.lrmi.util.Constants;
import rs.fon.is.lrmi.util.URIGenerator;

/**
 *
 * @author ANA
 */
public class CourseraParser extends Parser {

    public static final String COUSERA = "https://api.coursera.org/api/catalog.v1/courses?id={0}&includes=sessions,instructors,universities&fields=instructor,language,shortDescription,previewLink,targetAudience";
    public static final String INSTRUCTORS = "https://api.coursera.org/api/catalog.v1/instructors?id={0}";
    public static final String UNIVERSITIES = "https://api.coursera.org/api/catalog.v1/universities?id={0}&fields=name";
    public static final String SESSIONS = "https://api.coursera.org/api/catalog.v1/sessions?id={0}&fields=startDay,startMonth,startYear,durationString";
    public static final String LISTAKURSEVA = "https://api.coursera.org/api/catalog.v1/courses";

    @Override
    public List<CreativeWork> parse() throws IOException,
            URISyntaxException, ParseException {
        List<CreativeWork> list = new ArrayList<>();
        JsonArray searchResults = ParserUtil.vratiPodatke(LISTAKURSEVA, "elements");
        
        for (JsonElement jsonElement : searchResults) {
             boolean obj = jsonElement.getAsJsonObject().has("id");
             if(obj==true){
            int id = jsonElement.getAsJsonObject().get("id").getAsInt();
            System.out.println(id + "idd");
            String c = Integer.toString(id);
            String url = MessageFormat.format(COUSERA, c);
            JsonArray searchResults2 = ParserUtil.vratiPodatke(url, "elements");
            System.out.println("rez" + searchResults);
            if (!searchResults.isJsonNull()) {
                for (JsonElement jsonElement1 : searchResults2) {
                    CreativeWork creativeWork = parseCreativeWork(jsonElement1);
                    RDFModel.getInstance().save(creativeWork);
                    System.out.println("dodao");
                    list.add(creativeWork);
                }
            }
             }
        }
        System.out.println(list.size() + "list");
        return list;
    }

    static CreativeWork parseCreativeWork(JsonElement jsonElement) throws IOException, ParseException, URISyntaxException {
        CreativeWork creativeWork = new CreativeWork();
      
        int id = jsonElement.getAsJsonObject().get("id").getAsInt();
        System.out.println(id + "id");

        boolean objName = jsonElement.getAsJsonObject().has("name");
        if (objName == true) {
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            System.out.println(name + "name");
            if (name != null) {
                creativeWork.setName(name);
            } else {
                creativeWork.setName("/");
            }
        }
        boolean objLangu = jsonElement.getAsJsonObject().has("language");
        if (objLangu == true) {
            String language = jsonElement.getAsJsonObject().get("language").getAsString();
            System.out.println(language + "language");
            if (language != null) {
                creativeWork.setInLanguage(language);
            } else {
                creativeWork.setInLanguage("/");
            }
        }

        boolean objTarget = jsonElement.getAsJsonObject().has("targetAudience");
        if (objTarget == true) {
            int targetAudience = jsonElement.getAsJsonObject().get("targetAudience").getAsInt();
            if (targetAudience > 0) {
                String ta = "";
                switch (targetAudience) {
                    case 1:
                        ta = "beginner";
                        break;
                    case 2:
                        ta = "advanced";
                        break;
                    case 3:
                        ta = "intermediate";
                        break;

                }
                creativeWork.setTypicalAgeRange(ta);
            } 
        }
        boolean objDest = jsonElement.getAsJsonObject().has("shortDescription");
        if (objDest == true) {
            String shortDescription = jsonElement.getAsJsonObject().get("shortDescription").getAsString();
            if (shortDescription != null) {
                creativeWork.setDescription(shortDescription);
            } else {
                creativeWork.setDescription("/");
            }
        }
        JsonObject links = jsonElement.getAsJsonObject().get("links").getAsJsonObject();

        boolean objUniversities = links.has("universities");
        if (objUniversities == true) {
            JsonArray universities = links.get("universities").getAsJsonArray();
            for (JsonElement jsonElement2 : universities) {
                int u = jsonElement2.getAsJsonPrimitive().getAsInt();
                    System.out.println(universities + "universities");
                    Organization organization = parseOrganization(u);
                    
                    if(organization.getName()!=null){
                        System.out.println("unsdfsfiversities");
                        System.out.println(organization.getName());
                    creativeWork.getPublishers().add(organization);
                } 

            }
        }
        boolean objInst = links.has("instructors");
        if (objInst == true) {
            JsonArray instustors = links.get("instructors").getAsJsonArray();
            for (JsonElement jsonElement3 : instustors) {
                int i = jsonElement3.getAsJsonPrimitive().getAsInt();
                    Person person = parsePerson(i);
                    if(person.getName()!=null){
                    creativeWork.getAuthors().add(person);
                } 

            }
        }
        boolean objSession = links.has("sessions");
        if (objSession == true) {
            JsonArray sessions = links.get("sessions").getAsJsonArray();
            for (JsonElement jsonElement1 : sessions) {
                int s = jsonElement1.getAsJsonPrimitive().getAsInt();
                CreativeWork child = parseSession(s);
                if(child!=null){
                creativeWork.getChildren().add(child);
                child.setParent(creativeWork);
                }
            }
        }

        
        boolean objLink = jsonElement.getAsJsonObject().has("previewLink");
        System.out.println(objLink+"link");
        if (objLink == true) {
            String link = jsonElement.getAsJsonObject().get("previewLink").getAsString();
            if (link != null) {
                System.out.println("usao");
             //   URI uriLink=new URI(link);
                creativeWork.setLicense(new URI(link));
            } 
        }
        Organization o=new Organization();
        o.setName("Coursera");
        o.setUri(new URI("https://www.udacity.org/"));
        creativeWork.setProvider(o);
        creativeWork.setUri(URIGenerator.generate(creativeWork));
        return creativeWork;
    }

    private static CreativeWork parseSession(int s) throws IOException, ParseException, URISyntaxException {
        CreativeWork child = new CreativeWork();
        String link = SESSIONS;
        String c = Integer.toString(s);
        String url = MessageFormat.format(SESSIONS, c);
        JsonArray searchResults = ParserUtil.vratiPodatke(url, "elements");
        for (JsonElement jsonElement : searchResults) {
if (!searchResults.isJsonNull()) {
            String d = jsonElement.getAsJsonObject().get("durationString").getAsString();
            String trajanje="/";
            if(!d.equals("")){
            String[] niz = d.split(" ");
            trajanje = niz[0] + Character.toUpperCase(niz[1].charAt(0));
            }
            Duration duration = new Duration();
            duration.setDescription(trajanje);
            duration.setUri(URIGenerator.generate(duration));

            boolean obj = jsonElement.getAsJsonObject().has("startDay");
            if (obj == true) {
                int startDay = jsonElement.getAsJsonObject().get("startDay").getAsInt();
                System.out.println(startDay + "dan");
                int startMonth = jsonElement.getAsJsonObject().get("startMonth").getAsInt();
                int startYear = jsonElement.getAsJsonObject().get("startYear").getAsInt();
                String date = startYear + "-" + startMonth + "-" + startDay;
                Date dateModified = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                child.setDateCreated(dateModified);
            }
            
           

            child.setDuration(duration);
          
            child.setUri(URIGenerator.generate(child));
        }

        return child;
        } 
        return null;
    }

    private static Person parsePerson(int instustors) throws IOException, URISyntaxException {
        Person person = new Person();
        String link = INSTRUCTORS;
        String c = Integer.toString(instustors);
        System.out.println(instustors + "brojevi");
        Map map = new HashMap();
        map.put("{0}", instustors);
        String url = MessageFormat.format(INSTRUCTORS, c);
        JsonArray searchResults = ParserUtil.vratiPodatke(url, "elements");
          if (!searchResults.isJsonNull()) {
        for (JsonElement jsonElement : searchResults) {
            String firstName = jsonElement.getAsJsonObject().get("firstName").getAsString();
            String lastName = jsonElement.getAsJsonObject().get("lastName").getAsString();
            String name = firstName + " " + lastName;
            person.setName(name);
            person.setUri(URIGenerator.generate(person));
            System.out.println(name + " ime osobe");
        }
        return person;
          }
          return null;
          
    }

    private static Organization parseOrganization(int universities) throws IOException, URISyntaxException {
        Organization organization = new Organization();
        String link = UNIVERSITIES;
        String c = Integer.toString(universities);
        String url = MessageFormat.format(UNIVERSITIES, c);
        System.out.println(url);
        JsonArray searchResults = ParserUtil.vratiPodatke(url, "elements");
        System.out.println(searchResults.toString());
       if (!searchResults.isJsonNull()) {
        for (JsonElement jsonElement : searchResults) {
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            organization.setName(name);
            organization.setUri(URIGenerator.generate(organization));
            System.out.println(name + " ime organizacijee");
        }
         return organization;
       }
       return null;
    }
}
