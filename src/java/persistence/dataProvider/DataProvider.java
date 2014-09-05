/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence.dataProvider;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
/**
 *
 * @author ANA
 */
public interface DataProvider {
    	Model getDataModel();
	public Dataset getDataset();
	void close();
}
