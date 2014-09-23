/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.fon.is.lrmi.main;

import com.hp.hpl.jena.tdb.TDB;
import rs.fon.is.lrmi.rest.ServiceResource;
import rs.fon.is.lrmi.domen.CreativeWork;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import org.apache.http.client.HttpResponseException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import rs.fon.is.lrmi.parser.CourseraParser;
import rs.fon.is.lrmi.parser.Parser;
import rs.fon.is.lrmi.parser.UdacityParser;
import rs.fon.is.lrmi.persistence.RDFModel;
import rs.fon.is.lrmi.services.QueryService;

/**
 *
 * @author ANA
 */
public class Main {

    public static void main(String[] args) {
  try {
        // BasicConfigurator.configure();
        //  COUSERA
//          Parser parser=new CourseraParser();
//       List<CreativeWork> list1 = parser.parse();
      //    CourseraParser c=new CourseraParser();
        //  List<CreativeWork> list1 = c.parse2sAlISTOM();
//   
           
         
//     ServiceResource ks = new ServiceResource();
//    String ss=ks.getDuration();
//     //   for (String string : ss) {
//            System.out.println(ss);
//     //   }
     
//////            String col = ks.getCoursesCousera("", "", "", "beginner","");
//////            System.out.println(col);
//         Collection<String>  li = ks.getLink();
//         System.out.println("pozvao metodu");
//         for (String string : li) {
//             System.out.println("ispisi");
//            System.out.println(string+"dsfsd");
//        }
     //  System.out.println(li.);
////        String li2 = ks.getDuration();
////        System.out.println(li2 + "br u listi typical");
       

//            UDACITY 
      
           Parser parser2=new UdacityParser();
           List <CreativeWork > list2 = parser2.parse();
        RDFModel.getInstance().write("listaKurseva.rdf", "TURTLE");
            TDB.sync(RDFModel.getInstance().getDataset());
//                    RDFModel.getInstance().write("dr.rdf", "TURTLE");
//            TDB.sync(RDFModel.getInstance().getDataset());
//            ServiceResource ks = new ServiceResource();
//            String col = ks.getCoursesUdacity("", "", "", "beginner");
//            System.out.println(col);
            //oba
       
        } catch (IOException e) {			
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
