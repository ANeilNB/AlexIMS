package alexIMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	Connection conn;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/nb_ims";
	static final String USER = "imanager";
	static final String PASS = "nbgardens";
	
	public Product(int productId){
		this.productId = productId;
		/*
		 * Access database and read values for productName, currentStock, criticalStock
		 * and currentPrice from the database.
		 */
		
		
		conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			
			String sqlQuery = "SELECT product_name, current_stock, critical_stock, product_price FROM products WHERE product_id = ?;";
			stmt = conn.prepareStatement(sqlQuery);
			stmt.setInt(1, productId);
			
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			
			this.productName = rs.getString("product_name");
			this.currentStock = rs.getInt("current_stock");
			this.criticalStock = rs.getInt("critical_stock");
			this.currentPrice = rs.getDouble("product_price");
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
	public Product(String productName, int currentStock, int criticalStock, double currentPrice){
		this.productName = productName;
		this.currentStock = currentStock;
		this.criticalStock = criticalStock;
		this.currentPrice = currentPrice;
		
		conn = null;
		PreparedStatement stmt = null;
		ResultSet rs;
		try{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			
			String sqlQuery = "INSERT INTO products (product_name, current_stock, critical_stock, product_price) VALUES (?,?,?,?);";
			stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, productName);
			stmt.setInt(2,currentStock);
			stmt.setInt(3, criticalStock);
			stmt.setDouble(4, currentPrice);
			
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
	
	public int getCurrentStock() {
		return currentStock;
	}
	public boolean setCurrentStock(int newStock) {
		
		boolean increasedFlag = false;
		
		if(newStock > currentStock){
			//System.out.println("Stock of " + productName + " (ID: " + productId + ") increased to " +
			//						newStock + ".");
			increasedFlag = true;
		}
		
		this.currentStock = newStock;
		
		updateDatabase();
		
		return increasedFlag;
		//if(currentStock < 0) System.err.println("Stock of " + productName + " is all gone!");
		//else if(currentStock <= criticalStock) System.out.println("Stock of " + productName + " critically low!");
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
	
	public double getPrice() {
		return currentPrice;
	}
	

	private void updateDatabase(){
		
		conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			
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
	
	public void deleteProduct(){
		conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			
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
