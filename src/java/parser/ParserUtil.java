/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author ANA
 */
public class ParserUtil {
      public static JsonArray vratiPodatke(String url, String podaci) throws IOException {
        JsonArray searchResults = new JsonArray();
        HttpClient httpClient = new DefaultHttpClient();
        String stringResponse = null;
        HttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                response.getEntity().writeTo(out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //    System.out.println("fsdds" + out.toString());
            stringResponse = out.toString();
            JsonParser parser = new JsonParser();
            JsonObject jSonObject = new JsonObject();
            // searchResults = (JsonArray) parser.parse(stringResponse).getAsJsonObject().get("elements");
            searchResults = (JsonArray) parser.parse(stringResponse).getAsJsonObject().get(podaci);

        }

        return searchResults;
    }
}
