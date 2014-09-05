/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import domen.Thing;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 *
 * @author ANA
 */
public class URIGenerator {
    public synchronized static <T extends Thing> URI generate(T resource) throws URISyntaxException{
		String uri =Constants.NS+UUID.randomUUID();
                System.out.println("generator"+uri);
		return new URI(uri);
	}
}
