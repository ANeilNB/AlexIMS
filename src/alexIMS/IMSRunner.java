package alexIMS;

import java.io.File;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


/**
 * 
 * @author aneil
 * @version 1.0
 */
public class IMSRunner {
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://10.50.15.11:3306/nb_ims";
	public static final String USER = "imanager";
	public static final String PASS = "nbgardens";
	
	public static final String IMG_PATH_PRIMARY = "/home/developer/AlexIMS/res/";
	public static final String IMG_PATH_SECONDARY = "res/";
	
	public static final String ICON_NAME = "nbgicon.png";
	public static final String LOGO_NAME = "nbgardens.png";
	
	public static boolean primaryPath;

	/**
	 * Runs the NBGardens IMS system.
	 * Creates the model, view and controller classes and informs them of each other, then
	 * starts the UI.
	 * @param args
	 */
	public static void main(String[] args) {
		
		File f = new File(IMG_PATH_PRIMARY, LOGO_NAME);
		
		if(f.exists()){
			primaryPath = true;
		}
		else{
			primaryPath = false;
		}
		
		
		
		IMSModel model = new IMSModel();
		IMSView view = new IMSView(model);
		IMSController controller = new IMSController(model, view);
	
		view.createSplashScreen();
		
		model.refreshProductList();

		view.addController(controller);
		
		view.initUI();
		
	}
	
}
