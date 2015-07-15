package alexIMS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

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
	
	/**
	 * Constructs an IMSModel, initialising and filling the product list.
	 */
	IMSModel(){
		productList = new ArrayList<Product>();
	}
	

	/**
	 * Connects to the nb_ims database and collects an up-to-date list of products, placing them in productList.
	 * Uses {@link #addProduct(Product)}, passing Product's constructor a single integer to identify the product.
	 */
	void refreshProductList(){
		Connection con;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(IMSRunner.JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(IMSRunner.DB_URL, IMSRunner.USER, IMSRunner.PASS);
			
			
			String sqlQuery = "SELECT product_id FROM products;";
			stmt = conn.prepareStatement(sqlQuery);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){			
				addProduct(new Product(rs.getInt("product_id")));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(stmt != null)conn.close();
			}
			catch (SQLException e){}
			try{
				if(conn != null)
					conn.close();
			}
			catch (SQLException e){
				e.printStackTrace();
			}
			//Redundant on a successful database connection, as individual product connections will close the database.
			System.out.println("Closed Database!");
		}	
	}
	
	
	/**
	 * Prints a simple stock report containing the date and the current stock levels of all items available.
	 * Output is written to output/stockreport_yyyy-MM-dd_HH-mm.txt
	 * Uses the reportFormat string to maintain alignment in the report for all legal product name lengths.
	 */
	protected boolean printStockReport(String filepath){
		
		
		
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance()
				.getTime());
		String filenameDate = new SimpleDateFormat("yyyy-MM-dd_HH_mm").format(Calendar.getInstance()
				.getTime());
		
		//Constructs report to be written as a string
		String reportString = "Stock Report - " + date + System.getProperty("line.separator")
				+ "-------------------------" + System.getProperty("line.separator");
		reportString = reportString + String.format(reportFormat, "Id", "Product Name", "Stock");
		
		for(Product p : productList){
			reportString = reportString + String.format(reportFormat, p.getProductId(), p.getProductName(), p.getCurrentStock());
		}
		
		//Attempts to write the built report string to the file.
		try{
			
			File file = new File(filepath, "stockreport_" + filenameDate + ".txt");
		
			if(!file.exists()) file.createNewFile();
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());	
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(reportString);
			writer.close();
			//System.out.println("File Written!");
			return true;
		}
		catch (IOException e){
			return false;
		}
	}
	
	/**
	 * Adds a new Product to {@link #productList}
	 * @param newProduct An instance of the Product class to be added to the product list.
	 */
	protected void addProduct(Product newProduct){
		productList.add(newProduct);
	}
	
	/**
	 * Adds a new product to {@link #productList}
	 * The product's ID is gotten automatically as part of creating the product object.
	 * @param productName Name of product to add to the list
	 * @param currentStock Current stock of the product
	 * @param criticalStock What is considered critical stock level for the product
	 * @param currentPrice The product's price
	 */
	void addProduct(String productName, int currentStock, int criticalStock, double currentPrice){
		Product newProduct = new Product(productName, currentStock, criticalStock, currentPrice);
		addProduct(newProduct);
		
		//System.out.println("New product added");
	}
	
	/**
	 * Removes a product with the given ID from the array and database.
	 * @param removeId ID of the product to remove.
	 */
	void removeProduct(int removeId){
		//Loops through productList to find a product with the ID.
		for(Product p: productList){
			if(p.getProductId() == removeId){
				p.deleteProduct();
				productList.remove(p);
				System.out.println("Product removed");
				return;
			}
		}
	}
	
	/**
	 * Begins the simulation and defines it's task and interval.
	 * The simulation by default runs every 10 seconds and decrements a random Product's stock by up to 10.
	 * This is a simple and crude simulation and does not account for current stock levels.
	 */
	void enableDecrementTimer(){
		simulationTimer = new Timer();
		
		simulationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				//System.out.println("Timer is go!");
				Random randomGenerator = new Random();
				
				/*
				 * Generates a random number to pick an entry from productList, then decrements it's current stock by a random number that is between 0 and 10.
				 * Could perhaps decrement something in relation to critical stock.
				 */
				int i;
				productList.get(i =randomGenerator.nextInt(productList.size())).setCurrentStock(productList.get(i).getCurrentStock() - randomGenerator.nextInt(10));
				System.out.println(productList.get(i).getProductName() + " decremented. Stock now at: " + productList.get(i).getCurrentStock());
			}
		}, 10*1000, 10*1000); //starts after 10 seconds then runs every 10 seconds afterwards.
	}
	
	/**
	 * Disables the previous simulation by nullifying the timer variable to have the object garbage collected.
	 */
	void disableDecrementTimer(){
		simulationTimer = null;
	}
}
