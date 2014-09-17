/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.fon.is.lrmi.domen;

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
import rs.fon.is.lrmi.util.Constants;
import java.net.URI;

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

    @RdfProperty(Constants.SCHEMA + "provider")
    private Organization provider;
    @RdfProperty(Constants.SCHEMA + "license")
    private URI license;

    public URI getLicense() {
        return license;
    }

    public void setLicense(URI license) {
        this.license = license;
    }

    
    public Organization getProvider() {
        return provider;
    }

    public void setProvider(Organization provider) {
        this.provider = provider;
    }

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
        publishers = new ArrayList<Organization>();
        authors = new ArrayList<Person>();
    }

}
