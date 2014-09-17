/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.fon.is.lrmi.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import rs.fon.is.lrmi.domen.CreativeWork;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import rs.fon.is.lrmi.persistence.RDFModel;
import rs.fon.is.lrmi.util.Constants;

/**
 *
 * @author ANA
 */
public abstract class Parser {

    
public abstract List<CreativeWork> parse ()throws IOException,
            URISyntaxException, ParseException;
  

   
}
