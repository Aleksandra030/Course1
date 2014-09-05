/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import domen.CreativeWork;
import domen.Search;
import java.util.ArrayList;
import java.util.Collection;
import persistence.QueryExecutor;
import persistence.RDFModel;
import util.Constants;

/**
 *
 * @author ANA
 */
public class QueryService {

    private QueryExecutor queryExecutor = new QueryExecutor();

    public Collection<CreativeWork> getCoursesCousera(String name, String inLanguage,
            String organizationName, String typicalAgeRange, String duration) {

        Collection<CreativeWork> products = new ArrayList<CreativeWork>();

        String where = " ?courses a schema:CreativeWork. ";
        String filter = "";

        if (!name.isEmpty()) {
            System.out.println("usao name");
            where += "?courses schema:name ?name. ";
            filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
        }
        if ( inLanguage!=null) {
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
            products.add(c);
        }
        System.out.println(products.size() + "br ulisti");

        return products;
    }

    public Collection<CreativeWork> getCourses(Search ss) {

        Collection<CreativeWork> products = new ArrayList<CreativeWork>();

        String name = ss.getName();
        String inLanguage = ss.getInLanguage();
        String organizationName = ss.getPublishers();
        String typicalAgeRange = ss.getTypicalAgeRange();
        String duration=ss.getDuration();

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

        if (organizationName != null) {
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

        if (typicalAgeRange != null) {
            System.out.println("usao typicalagerange");
            where += "?courses schema:typicalAgeRange ?typicalAgeRange. ";
            filter += "FILTER regex( ?typicalAgeRange, \"" + typicalAgeRange + "\", \"i\" ) ";
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
            CreativeWork c= getCousera(string);
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
                + "SELECT DISTINCT ?lang \n"
                + "WHERE { ?x schema:inLanguage ?lang }"
                + "ORDER BY ?lang";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "lang",
                RDFModel.getInstance().getModel());
    }

    public Collection<String> getTypicalAgeRange() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?lang \n"
                + "WHERE { ?x schema:typicalAgeRange ?lang }"
                + "ORDER BY ?lang";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "lang",
                RDFModel.getInstance().getModel());
    }

    public Collection<String> getPublishers() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?lang \n"
                + "WHERE { ?x rdfs:name ?lang }"
                + "ORDER BY ?lang";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "lang",
                RDFModel.getInstance().getModel());
    }

    public Collection<String> getDuration() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?lang \n"
                + "WHERE { ?x schema:duration ?lang }"
                + "ORDER BY ?lang";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "lang",
                RDFModel.getInstance().getModel());
    }


}
