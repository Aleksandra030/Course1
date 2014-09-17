/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.fon.is.lrmi.services;

import java.util.ArrayList;
import java.util.Collection;
import rs.fon.is.lrmi.domen.CreativeWork;
import rs.fon.is.lrmi.domen.Search;
import rs.fon.is.lrmi.persistence.QueryExecutor;
import rs.fon.is.lrmi.persistence.RDFModel;
import rs.fon.is.lrmi.util.Constants;

/**
 *
 * @author ANA
 */
public class QueryService {

    private QueryExecutor queryExecutor = new QueryExecutor();

    public Collection<CreativeWork> getCoursesCousera(String name, String inLanguage,
            String organizationName, String typicalAgeRange, String duration) {

        Collection<CreativeWork> listakurseva = new ArrayList<CreativeWork>();

        String where = " ?courses a schema:CreativeWork. ";
        String filter = "";

        if (!name.isEmpty()) {
            System.out.println("usao name");
            where += "?courses schema:name ?name. ";
            filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
        }
        if (inLanguage != null) {
            System.out.println("usao inlanguage");
            where += "?courses schema:inLanguage ?inLanguage. ";
            filter += "FILTER regex( ?inLanguage, \"" + inLanguage + "\", \"i\" ) ";
        }

        if (!organizationName.isEmpty()) {
            System.out.println("usao organiz");
            String[] publishersArray = organizationName.split(",");
            for (int i = 0; i < publishersArray.length; i++) {

                where += "?courses schema:publishers ?publishers" + i + ". ?publishers" + i
                        + " schema:name ?name" + i + " FILTER regex(?name" + i
                        + ", \"" + publishersArray[i] + "\") ";

            }
        }

        if (!duration.isEmpty()) {
            System.out.println("usao duration");
            where += "?courses schema:duration ?duration. "
                    + "?duration schema:description ?description.";
            filter += "FILTER regex( ?description, \"" + duration + "\", \"i\" ) ";
        }
        if (!typicalAgeRange.isEmpty()) {
            System.out.println("usao typicalagerange");
            where += "?courses schema:typicalAgeRange ?typicalAgeRange. ";
            filter += "FILTER regex( ?typicalAgeRange, \"" + typicalAgeRange + "\", \"i\" ) ";
        }
       
        

        String query = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT ?courses " + "WHERE { " + where + filter + " } ";
        System.out.println(query + "upiiiit");
        Collection<String> lista = queryExecutor
                .executeOneVariableSelectSparqlQuery(query, "courses",
                        RDFModel.getInstance().getModel());

        for (String string : lista) {
            CreativeWork c = getCousera(string);
            listakurseva.add(c);
        }
        System.out.println(listakurseva.size() + "br ulisti");

        return listakurseva;
    }

    public Collection<CreativeWork> getCourses(Search ss) {

        Collection<CreativeWork> products = new ArrayList<CreativeWork>();

        String name = ss.getName();
        System.out.println(name+"ime");
        String inLanguage = ss.getInLanguage();
        System.out.println(inLanguage+"lang");
        String organizationName = ss.getPublishers();
        System.out.println(organizationName+"orgname");
        String typicalAgeRange = ss.getTypicalAgeRange();
        System.out.println(typicalAgeRange+"type");
        String duration = ss.getDuration();
        System.out.println(duration+"duration");

        System.out.println("stigao do ovde");
        String where = " ?courses a schema:CreativeWork. ";
        String filter = "";
        System.out.println(name + "name");
        System.out.println(inLanguage + "inLanguage");
        System.out.println(organizationName + "organizationName");
        System.out.print(ss.getName());
        if (!name.isEmpty()) {
            System.out.println("usao name");
            where += "?courses schema:name ?name. ";
            filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
        }
        if (!inLanguage.isEmpty()) {
            System.out.println("usao inlanguage");
            where += "?courses schema:inLanguage ?inLanguage. ";
            filter += "FILTER regex( ?inLanguage, \"" + inLanguage + "\", \"i\" ) ";
        }

        if (!organizationName.isEmpty()) {
            System.out.println("usao organiz");
            String[] publishersArray = organizationName.split(",");
            for (int i = 0; i < publishersArray.length; i++) {

                where += "?courses schema:publishers ?publishers" + i + ". ?publishers" + i
                        + " schema:name ?name" + i + " FILTER regex(?name" + i
                        + ", \"" + publishersArray[i] + "\") ";

            }
        }
        if (!duration.isEmpty()) {
//udacity
            if (ss.getImeKursa().equals("Udacity")||ss.getImeKursa().equals("Oba")) {
                System.out.println("usao duration za udacity");
                where += "?courses schema:duration ?duration. "
                        + "?duration schema:description ?description. ";
                filter += "FILTER regex( ?description, \"" + duration + "\", \"i\" ) ";
            }
            if (ss.getImeKursa().equals("Coursera")||ss.getImeKursa().equals("Oba")) {
                System.out.println("usao duration za cousera");
        where +="OPTIONAL {?courses schema:isPartOf ?isPartOf. }";
                
               
    //   "OPTIONAL {?courses schema:isPartOf ?isPartOf. } "
        
//         where += "OPTIONAL {?courses schema:isPartOf ?isPartOf. } "
//                +"OPTIONAL {?courses schema:duration ?duration. "
//                        + "?duration schema:description ?description.} ";
//                
             //   filter += "FILTER (!bound(?isPartOf) && ?description= \"" + duration + "\") ";
                
               filter += "FILTER (!bound(?isPartOf)) ";

//                where += "?courses schema:hasPart ?hasPart. "
//                        + "?hasPart IN {SELECT ?hasPart WHERE { "
//                        + "?courses schema:duration ?duration. "
//                        + "?duration schema:description ?description"
//                        + " FILTER regex( ?description, \"" + duration + "\", \"i\" ) }";

          //  filter += "FILTER regex( ?description, \"" + duration + "\", \"i\" ) ";
                //    ?courses a schema:CreativeWork. ";
            }
        }

        if (!typicalAgeRange.isEmpty()) {
            System.out.println("usao typicalagerange");
            where += "?courses schema:typicalAgeRange ?typicalAgeRange. ";
            filter += "FILTER regex( ?typicalAgeRange, \"" + typicalAgeRange + "\", \"i\" ) ";
        }
         if (ss.getImeKursa().equals("Udacity")||ss.getImeKursa().equals("Coursera")) {
                System.out.println("usao duration za udacity");
                where += "?courses schema:provider ?provider. "
                        + "?provider schema:name ?name. ";
                filter += "FILTER regex( ?name, \"" + ss.getImeKursa() + "\", \"i\" ) ";
         }

        String query = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT ?courses " + "WHERE { " + where + filter + " } ";

        System.out.println(query + "upiiiit");
        Collection<String> coursestUris = queryExecutor
                .executeOneVariableSelectSparqlQuery(query, "courses",
                        RDFModel.getInstance().getModel());

        for (String string : coursestUris) {
            CreativeWork c = getCousera(string);
            products.add(c);
        }
        System.out.println(products.size() + "br ulisti");

        return products;
    }

    public Collection<CreativeWork> getCoursesUdacity(String name, String duration,
            String organizationName, String typicalAgeRange) {

        Collection<CreativeWork> products = new ArrayList<CreativeWork>();

        String where = " ?courses a schema:CreativeWork. ";
        String filter = "";

        if (!name.isEmpty()) {
            System.out.println("usao name");
            where += "?courses schema:name ?name. ";
            filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
        }
        if (!duration.isEmpty()) {
            System.out.println("usao duration");
            where += "?courses schema:duration ?duration. "
                    + "?duration schema:description ?description.";
            filter += "FILTER regex( ?description, \"" + duration + "\", \"i\" ) ";
        }

        if (!organizationName.isEmpty()) {
            System.out.println("usao organiz");
            String[] publishersArray = organizationName.split(",");
            for (int i = 0; i < publishersArray.length; i++) {

                where += "?courses schema:publishers ?publishers" + i + ". ?publishers" + i
                        + " schema:name ?name" + i + " FILTER regex(?name" + i
                        + ", \"" + publishersArray[i] + "\") ";

            }
        }
        if (!typicalAgeRange.isEmpty()) {
            System.out.println("usao typicalagerange");
            where += "?courses schema:typicalAgeRange ?typicalAgeRange. ";
            filter += "FILTER regex( ?typicalAgeRange, \"" + typicalAgeRange + "\", \"i\" ) ";
        }

        String query = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT ?courses " + "WHERE { " + where + filter + " } ";
        System.out.println(query + "upiiiit");
        Collection<String> coursesUris = queryExecutor
                .executeOneVariableSelectSparqlQuery(query, "courses",
                        RDFModel.getInstance().getModel());

        for (String string : coursesUris) {
            CreativeWork c = getCousera(string);
            products.add(c);
        }
        System.out.println(products.size() + "br ulisti");

        return products;
    }

    public CreativeWork getCousera(String uri) {
        CreativeWork courses = queryExecutor.getCousera(uri);
        System.out.println(courses.getName() + "url");
        return courses;
    }

    public Collection<String> getLanguages() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:inLanguage ?l }"
                + "ORDER BY ?l";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "l",
                RDFModel.getInstance().getModel());
    }

    public Collection<String> getTypicalAgeRange() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:typicalAgeRange ?l }"
                + "ORDER BY ?l";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "l",
                RDFModel.getInstance().getModel());
    }

    public Collection<String> getPublishers() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:publishers ?publishers. "
                + "?publishers schema:name ?l }"
                + "ORDER BY ?l";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "l",
                RDFModel.getInstance().getModel());
    }

    public Collection<String> getDuration() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:duration ?duration. "
                + "?duration schema:description ?l }"
                + "ORDER BY ?l";


        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "lang",
                RDFModel.getInstance().getModel());
    }

    Collection<String> getLinks() {
 String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:provider ?provider. "
                + "?provider schema:name ?l }"
                + "ORDER BY ?l";


        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "l",
                RDFModel.getInstance().getModel());
    }

}
