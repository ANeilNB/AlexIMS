package alexIMS;

import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;


/**
 * 
 * @author aneil
 * @version 1.0
 */
public class IMSRunner {
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://10.50.15.2:3306/nb_ims";
	public static final String USER = "imanager";
	public static final String PASS = "nbgardens";

	/**
	 * Runs the NBGardens IMS system.
	 * Creates the model, view and controller classes and informs them of each other, then
	 * starts the UI.
	 * @param args
	 */
	public static void main(String[] args) {
		
		IMSModel model = new IMSModel();
		IMSView view = new IMSView(model);
		IMSController controller = new IMSController(model, view);
	
		//view.createSplashScreen();

			
		
		
		view.addController(controller);
		
		view.initUI();
		//model.enableDecrementTimer();
		//model.printStockReport();
		//model.printStock();
		
	}
	
}
