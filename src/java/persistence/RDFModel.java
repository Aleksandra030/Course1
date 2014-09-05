/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import persistence.dataProvider.DataProvider;
import persistence.dataProvider.TDBDataProvider;
import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;
import util.Constants;

/**
 *
 * @author ANA
 */
public class RDFModel {

    DataProvider dataProvider;
    Model graph;
    Bean2RDF writer;
    RDF2Bean reader;

    private static RDFModel INSTANCE;

    private RDFModel() {
        dataProvider = new TDBDataProvider();
        graph = getModel();
        graph.setNsPrefix("schema", Constants.SCHEMA);
        graph.setNsPrefix("xsd", Constants.XSD);
        graph.setNsPrefix("courses", Constants.NS);

        writer = new Bean2RDF(getModel());
        reader = new RDF2Bean(getModel());
    }

    public static RDFModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RDFModel();
        }

        return INSTANCE;
    }

    public Model getModel() {
        return dataProvider.getDataModel();
    }

    public void save(Object o) {
        writer.save(o);
    }

    public void closeModel() {
        dataProvider.close();
    }

    public Object load(String uri) {
        return reader.load(uri);
    }

    public void printOut() {
        graph.write(System.out, "RDF/XML");
    }

    public synchronized void read(String filename, String syntax) throws FileNotFoundException {
        getModel().read(new FileInputStream(filename), syntax);
    }

    public synchronized void write(String filename, String syntax) throws FileNotFoundException {
        getModel().write(new FileOutputStream(filename), syntax);
    }

    public Dataset getDataset() {
        return dataProvider.getDataset();
    }

//	public void readData(String filename, String syntax) throws FileNotFoundException {
//		System.out.println("Importing data");
//		getModel().read(new FileInputStream(filename), syntax);
//		System.out.println("Import finished");
//	}
}
