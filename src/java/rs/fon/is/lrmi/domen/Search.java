/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rs.fon.is.lrmi.domen;

/**
 *
 * @author ANA
 */
public class Search {
    
    private String name;
    private String inLanguage;
    private String publishers;
    private String typicalAgeRange;
    private String duration;
    private String imeKursa;

  
    
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImeKursa() {
        return imeKursa;
    }

    public void setImeKursa(String imeKursa) {
        this.imeKursa = imeKursa;
    }
    
 
    public Search() {
        name="";
        inLanguage="";
        publishers="";
        typicalAgeRange="";
        duration="";
        
        
        
    }

 
    

    public Search(String name, String inLanguage, String publishers, String typicalAgeRange) {
        this.name = name;
        this.inLanguage = inLanguage;
        this.publishers = publishers;
        this.typicalAgeRange = typicalAgeRange;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInLanguage() {
        return inLanguage;
    }

    public void setInLanguage(String inLanguage) {
        this.inLanguage = inLanguage;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public String getTypicalAgeRange() {
        return typicalAgeRange;
    }

    public void setTypicalAgeRange(String typicalAgeRange) {
        this.typicalAgeRange = typicalAgeRange;
    }
    

}
