package alexIMS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author Alexander Neil
 *
 */
public class IMSModel {
	
	ArrayList<Product> productList;
	
	Timer simulationTimer;
	
	//Simple string for use with String.format to create table-like alignment for stock report file
	private final String reportFormat = "%-6s\t%-45s\t%-5s" + System.getProperty("line.separator");
	
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
		String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance()
				.getTime());
		
		String reportString = "Stock Report - " + date + System.getProperty("line.separator")
				+ "-------------------------" + System.getProperty("line.separator");
		reportString = reportString + String.format(reportFormat, "Id", "Product Name", "Stock");
		
		for(Product p : productList){
			reportString = reportString + String.format(reportFormat, p.getProductId(), p.getProductName(), p.getCurrentStock());
		}
		
		//System.out.println(reportString);
		
		try{
			File file = new File("output/stockreport_" + date + ".txt");
		
			if(!file.exists()) file.createNewFile();
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());	
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(reportString);
			writer.close();
			System.out.println("File Written!");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	protected void addProduct(Product newProduct){
		productList.add(newProduct);
	}
	
	void addProduct(String productName, int currentStock, int criticalStock, double currentPrice){
		Product newProduct = new Product(productName, currentStock, criticalStock, currentPrice);
		addProduct(newProduct);
	}
	
	void enableDecrementTimer(){
		simulationTimer = new Timer();
		
		simulationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Timer is go!");
				Random randomGenerator = new Random();
				
				/*
				 * Generates a random number to pick an entry from productList, then decrements it's current stock by a random number that is between 0 and 10.
				 * Could perhaps decrement something in relation to critical stock.
				 */
				int i;
				productList.get(i =randomGenerator.nextInt(productList.size())).setCurrentStock(productList.get(i).getCurrentStock() - randomGenerator.nextInt(10));
				System.out.println(productList.get(i).getProductName() + " decremented. Stock now at: " + productList.get(i).getCurrentStock());
			}
		}, 10*1000, 10*1000);
	}
	
	void disableDecrementTimer(){
		simulationTimer = null;
	}
}
