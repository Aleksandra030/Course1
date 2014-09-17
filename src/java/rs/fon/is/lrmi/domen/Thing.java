/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rs.fon.is.lrmi.domen;
import java.net.URI;
import thewebsemantic.Id;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import rs.fon.is.lrmi.util.Constants;

/**
 *
 * @author ANA
 */
@Namespace(Constants.SCHEMA)
@RdfType("Thing")
public class Thing {

    public Thing() {
    }
    
    @Id
	private URI uri;
	
	@RdfProperty(Constants.SCHEMA+ "url")
	private URI url;
	
	@RdfProperty(Constants.SCHEMA + "name")
	private String name;
	
	@RdfProperty(Constants.SCHEMA + "description")
	private String description;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	
	
    
}
