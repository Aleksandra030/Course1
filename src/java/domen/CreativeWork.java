/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;

/**
 *
 * @author ANA
 */
@Namespace(Constants.SCHEMA)
@RdfType("CreativeWork")
public class CreativeWork extends Thing {

    @RdfProperty(Constants.SCHEMA + "authors")
    Collection<Person> authors;
    @RdfProperty(Constants.SCHEMA + "publishers")
    private Collection<Organization> publishers;

    // TODO
    @RdfProperty(Constants.SCHEMA + "hasPart")
    private List<CreativeWork> children;

    @RdfProperty(Constants.SCHEMA + "inLanguage")
    private String inLanguage;
    @RdfProperty(Constants.SCHEMA + "typicalAgeRange")
    private String typicalAgeRange;
    @RdfProperty(Constants.SCHEMA + "duration")
    private Duration duration;

    @RdfProperty(Constants.SCHEMA + "dateCreated")
    private Date dateCreated;
    
  @RdfProperty(Constants.SCHEMA + "isPartOf")
    private CreativeWork parent;

    public CreativeWork getParent() {
        return parent;
    }

    public void setParent(CreativeWork parent) {
        this.parent = parent;
    }
  
    public List<CreativeWork> getChildren() {
        return children;
    }

    public void setChildren(List<CreativeWork> children) {
        this.children = children;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Collection<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Person> authors) {
        this.authors = authors;
    }

    public Collection<Organization> getPublishers() {
        return publishers;
    }

    public void setPublishers(Collection<Organization> publishers) {
        this.publishers = publishers;
    }

   

    public String getInLanguage() {
        return inLanguage;
    }

    public void setInLanguage(String inLanguage) {
        this.inLanguage = inLanguage;
    }

    public String getTypicalAgeRange() {
        return typicalAgeRange;
    }

    public void setTypicalAgeRange(String typicalAgeRange) {
        this.typicalAgeRange = typicalAgeRange;
    }

    public CreativeWork() {
        children = new ArrayList<CreativeWork>();
        publishers=new ArrayList<Organization>();
        authors=new ArrayList<Person>();
    }
//      public JsonObject returnAsJson(){
//		JsonObject jsonCreateWork = new JsonObject();
//		
//		jsonCreateWork.addProperty("URI", getUri().toString());
//               jsonCreateWork.addProperty("description", getDescription());
//               jsonCreateWork.addProperty("inLanguage", inLanguage);
//               jsonCreateWork.addProperty("typicalAgeRange", typicalAgeRange);
//                jsonCreateWork.addProperty("name", getName());
//		jsonCreateWork.add("author", author.returnAsJson());
//                jsonCreateWork.add("publisher", publisher.returnAsJson());
//           for(int i=0;i<sessions.size();i++){
//           String kljuc=i+"";
//            jsonCreateWork.add(kljuc, sessions.get(i).returnAsJson());
//            kljuc="";
//           }
//		return jsonCreateWork;
//	}

}
