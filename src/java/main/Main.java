/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.hp.hpl.jena.tdb.TDB;
import com.test.ServiceResource;
import domen.CreativeWork;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import org.apache.http.client.HttpResponseException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import parser.CourseraParser;
import parser.Parser;
import parser.UdacityParser;
import persistence.RDFModel;
import services.QueryService;

/**
 *
 * @author ANA
 */
public class Main {

    public static void main(String[] args) {
  // try {
        // BasicConfigurator.configure();
        //  COUSERA
//          Parser parser=new CourseraParser();
//       List<CreativeWork> list1 = parser.parse();
//      //    CourseraParser c=new CourseraParser();
//        //  List<CreativeWork> list1 = c.parse2sAlISTOM();
////   
//            RDFModel.getInstance().write("dfsfd.rdf", "TURTLE");
//            TDB.sync(RDFModel.getInstance().getDataset());
         
////      ServiceResource ks = new ServiceResource();
//////            String col = ks.getCoursesCousera("", "", "", "beginner","");
//////            System.out.println(col);
//////        Collection<String> li = ks.getLanguages();
//////        System.out.println(li.size() + "br u listi leng");
//////        for (String string : li) {
//////            System.out.println(string);
//////        }
////        String li2 = ks.getDuration();
////        System.out.println(li2 + "br u listi typical");
       

//            UDACITY 
      
       //    Parser parser2=new UdacityParser();
         //  List <CreativeWork > list2 = parser2.parse();
//                    RDFModel.getInstance().write("drugiKurs.rdf", "TURTLE");
//            TDB.sync(RDFModel.getInstance().getDataset());
//            ServiceResource ks = new ServiceResource();
//            String col = ks.getCoursesUdacity("", "", "", "beginner");
//            System.out.println(col);
            //oba
        //   RDFModel.getInstance().write("oba.rdf", "TURTLE");
//             RDFModel.getInstance().closeModel();
//        } catch (IOException e) {			
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//           e.printStackTrace();
//        }
    }
}
