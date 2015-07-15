package alexIMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

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
	protected int requiredStock;
	protected boolean porousware;
	
	static final int NUMBER_OF_VALUES = 7;
	
	Connection conn;
	
	/**
	 * Constructs a Product object from a product ID, using data taken from the nb_ims database.
	 * @param productId The ID of the product this object represents
	 */
	public Product(int productId){
		this.productId = productId;
		/*
		 * Access database and read values for productName, currentStock, criticalStock
		 * and currentPrice from the database.
		 */
		
		
		conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(IMSRunner.JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(IMSRunner.DB_URL, IMSRunner.USER, IMSRunner.PASS);
			
			
			String sqlQuery = "SELECT product_name, current_stock, critical_stock, required_stock, product_price, porousware FROM products WHERE product_id = ?;";
			stmt = conn.prepareStatement(sqlQuery);
			stmt.setInt(1, productId);
			
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			
			this.productName = rs.getString("product_name");
			this.currentStock = rs.getInt("current_stock");
			this.criticalStock = rs.getInt("critical_stock");
			this.currentPrice = rs.getDouble("product_price");
			this.requiredStock = rs.getInt("required_stock");
			this.porousware = rs.getBoolean("porousware");
			//Removed until new database implementation!
		}
		catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
			System.out.println("Closed Database!");
		}
		
	}
	/**
	 * Constructor used to manually create a new product not currently in the database.
	 * @param productName Name of the product.
	 * @param currentStock Current stock levels of the product.
	 * @param currentPrice Price of the product.
	 * @param criticalStock Amount of stock that is considered critically low.
	 */
	/**
	 * @param productName Name of the product
	 * @param currentStock Current stock levels of the product
	 * @param criticalStock Amount of stock that is critically low
	 * @param requiredStock Amount of stock that is required during restocking
	 * @param currentPrice Current price of the product
	 * @param porousware If the item has porousware
	 */
	public Product(String productName, int currentStock, int criticalStock, int requiredStock, double currentPrice, boolean porousware){
		this.productName = productName;
		this.currentStock = currentStock;
		this.criticalStock = criticalStock;
		this.currentPrice = currentPrice;
		this.requiredStock = requiredStock;
		this.porousware = porousware;
		
		conn = null;
		PreparedStatement stmt = null;
		ResultSet rs;
		try{
			Class.forName(IMSRunner.JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(IMSRunner.DB_URL, IMSRunner.USER, IMSRunner.PASS);
			
			
			String sqlQuery = "INSERT INTO products (product_name, current_stock, critical_stock, required_stock, product_price) VALUES (?,?,?,?,?);";
			stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, productName);
			stmt.setInt(2,currentStock);
			stmt.setInt(3, criticalStock);
			stmt.setInt(4, requiredStock);
			stmt.setDouble(5, currentPrice);
			
			int result = stmt.executeUpdate();
			
			rs = stmt.getGeneratedKeys();
	
			rs.next();
			
			this.productId = rs.getInt(1);
			
			System.out.println("Update result: " + result);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
			System.out.println("Closed Database!");
		}
	}
	
	/**
	 * Gets the current stock level of the product
	 * @return Current stock level of the product
	 */
	public int getCurrentStock() {
		return currentStock;
	}
	
	/**
	 * Updates the current stock level of the product and updates the database with this value
	 * @param newStock New value of stock for the product to be set
	 * @return If the new stock value entered is valid and accepted
	 */
	public boolean setCurrentStock(int newStock) {
		
		boolean increasedFlag = false;
		
		if(newStock > currentStock){
			//System.out.println("Stock of " + productName + " (ID: " + productId + ") increased to " +
			//						newStock + ".");
			increasedFlag = true;
		}
		
		this.currentStock = newStock;
		
		updateStock();
		
		return increasedFlag;
		//if(currentStock < 0) System.err.println("Stock of " + productName + " is all gone!");
		//else if(currentStock <= criticalStock) System.out.println("Stock of " + productName + " critically low!");
	}
	
	/**
	 * Gets the ID of the product
	 * @return The product's ID
	 */
	public int getProductId() {
		return this.productId;
	}
	
	/**
	 * Gets the name of the product
	 * @return The name of the product
	 */
	public String getProductName() {
		return productName;
	}
	
	/**
	 * Gets the price of the product
	 * @return The price of the product
	 */
	public double getCurrentPrice() {
		return currentPrice;
	}
	
	/**
	 * Gets the critical stock level of the product
	 * @return the level at which stock is critical
	 */
	public int getCriticalStock() {
		return criticalStock;
	}
	
	/**
	 * Sets the critical stock level to a new value.
	 * Validated to make sure it is not greater than the required stock level
	 * @param criticalStock The new critical stock value to be set
	 */
	public void setCriticalStock(int criticalStock){
		if(criticalStock > requiredStock){
			//Error
		}
		else{
			this.criticalStock = criticalStock;
		}
	}
	
	/**
	 * Gets the price of the product
	 * @return the price of the product
	 */
	public double getPrice() {
		return currentPrice;
	}
	
	/**
	 * Gets the required stock level of the product
	 * @return the required stock level of the product
	 */
	public int getRequiredStock(){
		return requiredStock;
	}
	
	/**
	 * Returns if the product has porousware or not
	 * @return if the product is porouswared or not
	 */
	public boolean isPorousware(){
		return porousware;
	}
	/*
	public String getDateUpdated(){
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String updated = df.format(dateUpdated);
		return updated;
	}
	

	public void setDateUpdated(){
		dateUpdated = Calendar.getInstance().getTime();
	}
	
	public void setDateUpdated(String newDate){
		
		if(Pattern.matches("^\\d{4}-[0-1]\\d-[0-3]\\d$", "newDate")){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try{
				dateUpdated = df.parse(newDate);
			}
			catch(ParseException pe){
				pe.printStackTrace();
			}
		}
	}
	*/
	
	/**
	 * Sets the required stock of the product to a new value.
	 * Checks to make sure the new required stock level is valid ie, not below the critical threshold
	 * @param requiredStock
	 */
	public void setRequiredStock(int requiredStock){
		if(criticalStock > requiredStock){
			//Error
		}
		else{
			this.requiredStock = requiredStock;
		}
	}
	

	/**
	 * Updates the database with new stock levels
	 */
	private void updateStock(){
		
		conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(IMSRunner.JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(IMSRunner.DB_URL, IMSRunner.USER, IMSRunner.PASS);

			
			String sqlQuery = "UPDATE products SET current_stock = ? WHERE product_id = ?;";
			stmt = conn.prepareStatement(sqlQuery);
			stmt.setInt(1, this.currentStock);
			stmt.setInt(2, this.productId);
			
			int result = stmt.executeUpdate();
			
			System.out.println(result);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(stmt != null)conn.close();
			}
			catch (SQLException e){	}
			try{
				if(conn != null)
					conn.close();
			}
			catch (SQLException e){
				e.printStackTrace();
			}
			System.out.println("Closed Database!");
		}
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
	
	/**
	 * Removes the product from the database.
	 */
	public void deleteProduct(){
		conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(IMSRunner.JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(IMSRunner.DB_URL, IMSRunner.USER, IMSRunner.PASS);

			
			String sqlQuery = "DELETE FROM products WHERE product_id = ?;";
			stmt = conn.prepareStatement(sqlQuery);
			stmt.setInt(1, this.productId);
			
			int result = stmt.executeUpdate();
			
			System.out.println(result);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(stmt != null)conn.close();
			}
			catch (SQLException e){	}
			try{
				if(conn != null)
					conn.close();
			}
			catch (SQLException e){
				e.printStackTrace();
			}
			System.out.println("Closed Database!");
		}
	}

	
}
