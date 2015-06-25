package alexIMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Product class acts as a container for all the data referring to a product in
 * the IMS. It also contains methods for determining if it's own stock levels are 
 * low.
 * Product also encapsulates the methods for obtaining the stock levels relevant to
 * it from a database and updating the database should the stock levels change.
 * @author Alexander Neil
 *
 */
public class Product {

	protected int productId;
	protected String productName;
	protected int currentStock;
	protected double currentPrice;
	protected int criticalStock;
	
	public Product(int productId){
		this.productId = productId;
		/*
		 * Access database and read values for productName, currentStock, criticalStock
		 * and currentPrice from the database.
		 */
	}
	/**
	 * Constructor used to manually create a new product not currently in the database.
	 * @param productName Name of the product.
	 * @param currentStock Current stock levels of the product.
	 * @param currentPrice Price of the product.
	 * @param criticalStock Amount of stock that is considered critically low.
	 */
	public Product(String productName, int currentStock, double currentPrice, int criticalStock){
		this.productName = productName;
		this.currentStock = currentStock;
		this.criticalStock = criticalStock;
		this.currentPrice = currentPrice;
		/*
		 * Create new row in Products table in the database for the product.
		 */
		this.productId = (Integer) null; //Will be gotten from database's auto incremented ID when row is created.
	}
	
	public Product(int productId, String productName, int currentStock, double currentPrice, int criticalStock){
		this.productId = productId;
		this.productName = productName;
		this.currentStock = currentStock;
		this.criticalStock = criticalStock;
		this.currentPrice = currentPrice;
		/*
		 * Create new row in Products table in the database for the product.
		 */
	}
	
	public int getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(int newStock) {
		this.currentStock = newStock;
	}
	public int getProductId() {
		return this.productId;
	}
	public String getProductName() {
		return productName;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public int getCriticalStock() {
		return criticalStock;
	}
	
	protected void updateStock(){
		System.out.println("Testing Database!");
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e){
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
		
		System.out.println("MySQL Driver is go!");
		Connection connection = null;
		
		try{
			connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306", "imanager", "nbgardens");
		}
		catch(SQLException e){
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return;
		}
		
		//unfinished test due to MySQL server issues
	}
	
	protected void updateDatabase(){
		/*
		 * Update database with current stock values.
		 */
	}
	
	/**
	 * Checks to see if the current stock level of the item (currentStock) is at
	 * or below the critical threshold (criticalStock).
	 * @return true if stock is at or below critical threshold, false if it is above.
	 */
	public boolean checkStock(){
		if(currentStock <= criticalStock) return true;
		return false;
	}
	
}
