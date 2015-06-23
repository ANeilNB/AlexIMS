package alexIMS;

import java.io.BufferedWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 * @author Alexander Neil
 *
 */
public class IMSModel {
	
	private ArrayList<Product> productList;
	
	private final String reportFormat = "%-6s\t%-45s\t%-5s\n";
	
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
	protected void printStockReport(){		
		String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance()
				.getTime());
		
		String reportString = "Stock Report - " + date + "\n-------------------------\n";
		reportString = reportString + String.format(reportFormat, "Id", "Product Name", "Stock");
		
		for(Product p : productList){
			reportString = reportString + String.format(reportFormat, p.getProductId(), p.getProductName(), p.getCurrentStock());
		}
		
		System.out.println(reportString);
	}
	
	protected void addProduct(Product newProduct){
		productList.add(newProduct);
	}
}
