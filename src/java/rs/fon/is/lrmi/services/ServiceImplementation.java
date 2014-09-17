/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.fon.is.lrmi.services;

import rs.fon.is.lrmi.domen.CreativeWork;
import rs.fon.is.lrmi.domen.Search;
import java.util.Collection;
import rs.fon.is.lrmi.util.Constants;

/**
 *
 * @author ANA
 */
public class ServiceImplementation implements Service {

    @Override
    public Collection<CreativeWork> getCousera(String name, String inLanguage, String organizationName, String typicalAgeRange, String duration ) {
        QueryService queryService = new QueryService();
        Collection<CreativeWork> courses = queryService.getCoursesCousera(name, inLanguage, organizationName, typicalAgeRange, duration);
        return courses;
    }

    @Override
    public Collection<CreativeWork> getUdacity(String name, String duration, String organizationName, String typicalAgeRange) {
        QueryService queryService = new QueryService();
        Collection<CreativeWork> courses = queryService.getCoursesUdacity(name, duration, organizationName, typicalAgeRange);

      
        return courses;
    }
     @Override
     public Collection<String> getLanguages(){
       QueryService queryService = new QueryService();
       return queryService.getLanguages();
     }
       @Override
     public Collection<String> getTypicalAgeRange(){
       QueryService queryService = new QueryService();
       return queryService.getTypicalAgeRange();
     }
     
       @Override
     public Collection<String> getPublishers(){
       QueryService queryService = new QueryService();
       return queryService.getPublishers();
     }
        @Override
     public Collection<String> getDuration(){
      QueryService queryService = new QueryService();
       return queryService.getDuration();
     }



    @Override
    public Collection<CreativeWork> getCourses(Search ss) {
        QueryService queryService = new QueryService();
        Collection<CreativeWork> courses = queryService.getCourses(ss);
        return courses;
    }
    
      @Override
     public Collection<String> getLinks(){
       QueryService queryService = new QueryService();
       return queryService.getLinks();
     }
    
    

}
