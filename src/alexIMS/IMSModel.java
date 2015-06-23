package alexIMS;

import java.util.ArrayList;

/**
 * 
 * @author Alexander Neil
 *
 */
public class IMSModel {
	
	private ArrayList<Product> productList;
	
	IMSModel(){
		productList = new ArrayList<Product>();
	}
	
	//Was for task1 debug, testing 
	/*
	protected void printStock(){
		System.out.println("Id\tName \t\t\tStock");
		for(Product p : productList){
			System.out.println(p.getProductId() + "\t" + p.getProductName() + "\t\t" + p.getCurrentStock());
		}
	}
	*/
	
	protected void addProduct(Product newProduct){
		productList.add(newProduct);
	}
}
