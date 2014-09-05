/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domen.CreativeWork;
import domen.Duration;

import domen.Organization;
import domen.Person;
import java.text.SimpleDateFormat;
import java.util.Collection;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author ANA
 */
public class ProductJSONParser {

    public static JsonObject serialize(CreativeWork p) {

        JsonObject jsonCreateWork = new JsonObject();

        jsonCreateWork.addProperty("URI", p.getUri().toString());
        jsonCreateWork.addProperty("inLanguage", p.getInLanguage());
        jsonCreateWork.addProperty("description", p.getDescription());
        if (p.getDuration() != null) {
            jsonCreateWork.add("duration", serijalizeDuration(p.getDuration()));
        }
        jsonCreateWork.addProperty("typicalAgeRange", p.getTypicalAgeRange());
        jsonCreateWork.addProperty("name", p.getName());

        JsonArray namePerson = new JsonArray();
        for (Person person : p.getAuthors()) {

            namePerson.add(serializeAuthor(person));

        }
        jsonCreateWork.add("authors", namePerson);

        JsonArray nameOrganization = new JsonArray();
        for (Organization organization : p.getPublishers()) {

            nameOrganization.add(serializePublisher(organization));

        }
        jsonCreateWork.add("publisher", nameOrganization);

        JsonArray nameSession = new JsonArray();
        for (CreativeWork creativeWork : p.getChildren()) {

            nameSession.add(serializeSession(creativeWork));

        }
        jsonCreateWork.add("session", nameSession);
        return jsonCreateWork;
    }

    private static JsonElement serializeAuthor(Person author) {
        JsonObject jsonPerson = new JsonObject();
        jsonPerson.addProperty("name", author.getName());

        return jsonPerson;
    }

    private static JsonElement serijalizeDuration(Duration duration) {
        JsonObject jsonDuration = new JsonObject();
        jsonDuration.addProperty("description", duration.getDescription());
        return jsonDuration;
    }

    private static JsonElement serializePublisher(Organization publisher) {
        JsonObject jsonOrganization = new JsonObject();

        jsonOrganization.addProperty("name", publisher.getName());

        return jsonOrganization;
    }

    private static JsonElement serializeSession(CreativeWork creativeWork) {
        JsonObject jsonEvent = new JsonObject();

        jsonEvent.addProperty("URI", creativeWork.getUri().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jsonEvent.addProperty("startDate", (creativeWork.getDateCreated() == null) ? "" : sdf.format(creativeWork.getDateCreated()));
        jsonEvent.add("duration", serijalizeDuration(creativeWork.getDuration()));

        return jsonEvent;
    }

}
