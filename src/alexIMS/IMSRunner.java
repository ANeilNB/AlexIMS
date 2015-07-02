package alexIMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IMSRunner {
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://10.50.25.28:3306/nb_ims";
	public static final String USER = "imanager";
	public static final String PASS = "nbgardens";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IMSModel model = new IMSModel();
		//IMSView view = new IMSView(model);
		
		//populating model for testing purposes
		/*
		model.addProduct(new Product(1,"Gnom", 3, 2.50, 1));
		model.addProduct(new Product(2,"Diamond Sun Lounger", 2, 30000.0, 1));
		model.addProduct(new Product(3,"Gareth Gnome", 7, 25.99, 3));
		model.addProduct(new Product(4,"James", 20, 15.50, 2));
		model.addProduct(new Product(5,"Alien Gnome", 22, 19.99, 5));
		model.addProduct(new Product(6,"King Gnome", 8, 99.99, 2));
		model.addProduct(new Product(7,"Fat Gnome", 11, 8.99, 3));
		model.addProduct(new Product(8,"Gnomebuilder Academy", 12, 65.00, 4));
		model.addProduct(new Product(9,"Tiny Fountain", 99, 2.50, 15));
		model.addProduct(new Product(10,"Gnome Gnome",75, 10.00,12));
		*/
		Connection con;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			
			String sqlQuery = "SELECT product_id FROM products;";
			stmt = conn.prepareStatement(sqlQuery);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){			
				model.addProduct(new Product(rs.getInt("product_id")));
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
			System.out.println("Closed Database!");
		}		
		
		IMSView view = new IMSView(model);
		IMSController controller = new IMSController(model, view);
		view.addController(controller);
		
		view.initUI();
		//model.enableDecrementTimer();
		//model.printStockReport();
		//model.printStock();
		
	}

}
