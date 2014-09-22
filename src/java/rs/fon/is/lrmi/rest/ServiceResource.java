/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.fon.is.lrmi.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hp.hpl.jena.sparql.ARQConstants;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.tdb.TDB;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.ws.rs.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import rs.fon.is.lrmi.domen.CreativeWork;
import rs.fon.is.lrmi.domen.Duration;
import rs.fon.is.lrmi.domen.Search;
import rs.fon.is.lrmi.persistence.RDFModel;
import rs.fon.is.lrmi.rest.parser.JSONParser;
import rs.fon.is.lrmi.services.Service;
import rs.fon.is.lrmi.services.ServiceImplementation;

/**
 * REST Web Service
 *
 * @author ANA
 */
@Path("service")
public class ServiceResource {

    public Service s = new ServiceImplementation();

    public ServiceResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCoursesCousera(
            @DefaultValue("") @QueryParam("name") String name,
            @DefaultValue("") @QueryParam("inLanguage") String inLanguage,
            @DefaultValue("") @QueryParam("publishers") String organizationName,
            @DefaultValue("") @QueryParam("typicalAgeRange") String typicalAgeRange,
            @DefaultValue("") @QueryParam("duration") String duration) {

        Collection<CreativeWork> courses = s.getCousera(name, inLanguage, organizationName, typicalAgeRange, duration);
        if (courses != null && !courses.isEmpty()) {
            JsonArray productArray = new JsonArray();
            for (CreativeWork c : courses) {

                JsonObject productJson = JSONParser.serialize(c);
                productArray.add(productJson);
            }
            TDB.sync(RDFModel.getInstance().getModel());

            return productArray.toString();
        }

        throw new WebApplicationException(Response.Status.NO_CONTENT);
    }

    @Path("/find")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<CreativeWork> find(Search ss) throws Exception {
        Collection<CreativeWork> courses = s.getCourses(ss);
        return courses;

    }

    @Path("/courses")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String courses(Search se) throws Exception {
        Collection<CreativeWork> courses1 = new ArrayList<>();
        if (se.getInLanguage().equals("all")) {
            se.setInLanguage("");

        }
        if (se.getTypicalAgeRange().equals("all")) {
            se.setTypicalAgeRange("");

        }
        System.out.println(se.getDuration() + "pre");
        if (se.getDuration().contains("all")) {

            se.setDuration("");
        }
        System.out.println(se.getImeKursa() + "ime kursa na pocetku");
        if (se.getImeKursa().equals("Oba")) {
            se.setImeKursa("Coursera");
            System.out.println(se.getImeKursa() + "kad je oba");
            courses1 = s.getCourses(se);
            se.setImeKursa("Udacity");
            System.out.println(se.getImeKursa() + "kad je oba drugi put");
            System.out.println(courses1.size() + "br u listiiii");

        }
        Collection<CreativeWork> courses = s.getCourses(se);
        for (CreativeWork creativeWork : courses1) {
            courses.add(creativeWork);
        }

        JsonArray productArray = new JsonArray();
        for (CreativeWork c : courses) {
            JsonObject productJson = JSONParser.serialize(c);
            productArray.add(productJson);
        }
        System.out.println(productArray);
        return productArray.toString();

    }

    @Path("/languages")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLanguages() {
        Collection<String> lista = s.getLanguages();
        JsonArray productArray = new JsonArray();
        for (String string : lista) {
            JsonObject jsonLan = new JsonObject();
            jsonLan.addProperty("inLanguage", string);
            productArray.add(jsonLan);
            System.out.println(string);
        }

        return productArray.toString();
    }

    @Path("/typicalAgeRange")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTypicalAgeRange() {
        Collection<String> lista = s.getTypicalAgeRange();
        JsonArray productArray = new JsonArray();
        for (String string : lista) {
            JsonObject jsonLan = new JsonObject();
            jsonLan.addProperty("typicalAgeRange", string);
            productArray.add(jsonLan);
            System.out.println(string);
        }
        return productArray.toString();
    }

    @Path("/publishers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPublishers() {
        Collection<String> lista = s.getPublishers();

        JsonArray productArray = new JsonArray();
        for (String string : lista) {
            JsonObject jsonLan = new JsonObject();
            jsonLan.addProperty("publishers", string);
            productArray.add(jsonLan);
            System.out.println(string);
        }
        return productArray.toString();

    }

    @Path("/link")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLink() {
        Collection<String> lista = s.getLinks();

        JsonArray productArray = new JsonArray();
        for (String string : lista) {

            JsonObject jsonLan = new JsonObject();
            jsonLan.addProperty("naziv", string);
            productArray.add(jsonLan);
            System.out.println(string);
        }
        return productArray.toString();

    }

    @Path("/duration")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDuration() {

        Collection<String> lista = s.getDuration();
        Collection<String> lista1 = new ArrayList<>();

        for (String string : lista) {
            int br = -1;
            if (!string.isEmpty()) {
                if (string.contains("D")) {
                    br = string.indexOf("D");
                }
                if (string.contains("W")) {
                    br = string.indexOf("W");
                }
                if (string.contains("M")) {
                    br = string.indexOf("W");
                }
                if(br!=-1){
                
                String p = string.substring(0, br);
                
                if(!lista1.contains(p))
                lista1.add(p);
                }
            }
        }
        JsonArray productArray = new JsonArray();
        for (String s : lista1) {
            JsonObject jsonLan = new JsonObject();
            jsonLan.addProperty("d", s);
            productArray.add(jsonLan);
            System.out.println(s);
        }
        return productArray.toString();
    }

    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
