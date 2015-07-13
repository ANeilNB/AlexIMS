package alexIMS;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


/**
 * 
 * @author aneil
 * @version 1.0
 */
public class IMSRunner {
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://10.50.15.4:3306/nb_ims";
	public static final String USER = "imanager";
	public static final String PASS = "nbgardens";

	/**
	 * Runs the NBGardens IMS system.
	 * Creates the model, view and controller classes and informs them of each other, then
	 * starts the UI.
	 * @param args
	 */
	public static void main(String[] args) {
		

		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		IMSModel model = new IMSModel();
		IMSView view = new IMSView(model);
		IMSController controller = new IMSController(model, view);
	
		view.createSplashScreen();

		view.addController(controller);
		
		view.initUI();
		
	}
	
}
