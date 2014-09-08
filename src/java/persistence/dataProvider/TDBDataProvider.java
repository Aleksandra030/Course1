/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence.dataProvider;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.test.ServiceResource;

/**
 *
 * @author ANA
 */
public class TDBDataProvider implements DataProvider{

    public static final String directory =util.Constants.U;
  
   /// public static final String tek=System.getProperty("user.dir")+"\\td5";
  //  private String s=kontroler.Kontroler.vratiObj().mapa.get("tdb");
	private Dataset dataset;
    public TDBDataProvider() {
   
		dataset = TDBFactory.createDataset(directory);
                  System.out.println(directory+"putanja");
            //   System.out.println(util.Constants.tdb);
	}
        @Override
    public Model getDataModel() {
        return dataset.getDefaultModel();
    }

    @Override
    public void close() {
        dataset.close();
    }

    @Override
    public Dataset getDataset() {
        	return dataset;
    }
    
}
