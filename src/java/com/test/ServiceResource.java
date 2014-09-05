/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hp.hpl.jena.sparql.ARQConstants;
import com.hp.hpl.jena.tdb.TDB;
import domen.CreativeWork;
import domen.Duration;
import domen.Search;
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
import persistence.RDFModel;
import rest.parser.ProductJSONParser;
import services.Service;
import services.ServiceImplementation;

/**
 * REST Web Service
 *
 * @author ANA
 */
@Path("service")
public class ServiceResource {

//    @Context
//    private UriInfo context;
   
    public Service s = new ServiceImplementation();
   

    public ServiceResource() {
        System.out.println("dosao service resource");
        
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCoursesCousera(
            @DefaultValue("") @QueryParam("name") String name,
            @DefaultValue("") @QueryParam("inLanguage") String inLanguage,
            @DefaultValue("") @QueryParam("publishers") String organizationName,
            @DefaultValue("") @QueryParam("typicalAgeRange") String typicalAgeRange,
            @DefaultValue("") @QueryParam("duration") String duration) {

        System.out.println(name + "name");
        System.out.println(inLanguage + "inLanguage");
        System.out.println(organizationName + "organizationName");
        System.out.println(typicalAgeRange + "typicalAgeRange");
        Collection<CreativeWork> courses = s.getCousera(name, inLanguage, organizationName, typicalAgeRange, duration);
        if (courses != null && !courses.isEmpty()) {
            JsonArray productArray = new JsonArray();
            System.out.println(productArray.size() + "array kod rest servisa");
            for (CreativeWork c : courses) {

                JsonObject productJson = ProductJSONParser.serialize(c);
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
        System.out.println(ss.getName() + "ime");
        System.out.println(ss.getInLanguage() + "language");
        System.out.println(ss.getPublishers() + "publisher");
        System.out.print(ss.getTypicalAgeRange() + "tupe");
        Collection<CreativeWork> courses = s.getCourses(ss);
        System.out.println(courses.size() + "lista kod service resource");
        System.out.println(courses);
        return courses;

    }

    @Path("/ana")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String ana(Search ss) throws Exception {
        
     
        System.out.println(ss.getName() + "ime");
        System.out.println(ss.getInLanguage() + "language");
        System.out.println(ss.getPublishers() + "publisher");
        System.out.print(ss.getTypicalAgeRange() + "tupe");
        Collection<CreativeWork> courses = s.getCourses(ss);
        System.out.println(courses.size() + "lista kod service resource");
        
        JsonArray productArray = new JsonArray();
        System.out.println(productArray.size() + "array kod rest servisa");
        for (CreativeWork c : courses) {

            JsonObject productJson = ProductJSONParser.serialize(c);
            productArray.add(productJson);
        }
        System.out.println(productArray);
        return productArray.toString();

    }

    @Path("/izbrisis")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCoursesUdacity(
            @DefaultValue("") @QueryParam("name") String name,
            @DefaultValue("") @QueryParam("duration") String duration,
            @DefaultValue("") @QueryParam("publishers") String organizationName,
            @DefaultValue("") @QueryParam("typicalAgeRange") String typicalAgeRange) {

        System.out.println(name + "name");
        System.out.println(duration + "duration");
        System.out.println(organizationName + "organizationName");
        System.out.println(typicalAgeRange + "typicalAgeRange");
        Collection<CreativeWork> courses = s.getUdacity(name, duration, organizationName, typicalAgeRange);
        System.out.println(courses.size() + "service resource");
        if (courses != null && !courses.isEmpty()) {
            JsonArray productArray = new JsonArray();

            for (CreativeWork c : courses) {

                JsonObject productJson = ProductJSONParser.serialize(c);
                productArray.add(productJson);
            }
            TDB.sync(RDFModel.getInstance().getModel());

            return productArray.toString();
        }

        throw new WebApplicationException(Response.Status.NO_CONTENT);
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
    public Collection<String> getpublishers() {

        return s.getPublishers();
    }
    @Path("/duration")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<String> getDuration() {

        return s.getDuration();
    }
//    @GET
//    @Path("{id}/events")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getProductReveiws(@PathParam("id") String id) {
//        Collection<Event> events = s.getEventForProduct(id);
//
//        if (events != null && !events.isEmpty()) {
//         //   JsonObject reviewsJson = ProductJSONParser.serialize(events, id);
//            return  new reviewsJson.toString();
//        }
//
//        throw new WebApplicationException(Response.Status.NO_CONTENT);
//    }
//    @GET
//    @Produces("text/html")
//    public String getHtml() {
//return "<h1>Get some Rest!</>";
//    }

    /**
     * PUT method for updating or creating an instance of ServiceResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
