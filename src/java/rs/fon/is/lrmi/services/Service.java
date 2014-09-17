/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.fon.is.lrmi.services;

import rs.fon.is.lrmi.domen.CreativeWork;
import rs.fon.is.lrmi.domen.Search;
import java.util.Collection;

/**
 *
 * @author ANA
 */
public interface Service {

    public Collection<CreativeWork> getCousera(String name, String inLanguage, String organizationName, String typicalAgeRange, String duration);

    public Collection<CreativeWork> getUdacity(String name, String duration, String organizationName, String typicalAgeRange);

    public Collection<CreativeWork> getCourses(Search ss);

    public Collection<String> getLanguages();

    public Collection<String> getTypicalAgeRange();

    public Collection<String> getPublishers();

    public Collection<String> getDuration();

    public Collection<String> getLinks();

}
