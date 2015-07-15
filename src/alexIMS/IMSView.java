package alexIMS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class IMSView {

	IMSModel model;
	IMSController controller;
	
	JFrame splash;

	JFrame mainFrame;
	JPanel mainPanel;
	JTable productTable;
	final Object[] COLUMN_NAMES = {"Product Id", "Product Name", "Stock", "Critical Stock", "Required Stock", "Porousware", "Price"};
	DefaultTableModel tableModel;
	
	
	/**
	 * Initializes the view with the model
	 * @param model
	 */
	IMSView(IMSModel model){
		this.model = model;
		
	}
	
	/**
	 * Sets the relevant controller for the view.
	 * @param controller The controller that relates to the view
	 */
	void addController(IMSController controller){
		this.controller = controller;
	}
	

	/**
	 * Creates the splash screen for the program.
	 * Displays a borderless JFrame in the center of the screen containing the NBGardens logo and loading status text.
	 */
	void createSplashScreen(){
		
		splash = new JFrame();
		splash.setUndecorated(true);
				
		JPanel splashPane = new JPanel(new BorderLayout());
		
		splash.setContentPane(splashPane);
		
		JLabel loadingLabel = new JLabel("Loading...");
		loadingLabel.setFont(new Font("Serif", Font.PLAIN, 28));;
		
		String path;
		if(IMSRunner.primaryPath) path = IMSRunner.RES_PATH_PRIMARY;
		else path = IMSRunner.RES_PATH_SECONDARY;
		
		splashPane.add(new ImagePanel(path + IMSRunner.LOGO_NAME), BorderLayout.CENTER);
		splashPane.add(loadingLabel, BorderLayout.SOUTH);
		
		splash.setSize(500, 600);
		splash.setResizable(false);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int locationX = (screenSize.width - splash.getWidth()) / 2;
		int locationY = (screenSize.height = splash.getHeight()) / 2;
		splash.setLocation(locationX,locationY);
		
		splash.setVisible(true);
	}

	
	
	/**
	 * Initializes the main UI.
	 * Creates all of the UI elements, initializes the table and actionlisteners and sets the frame to show in the center of the screen.
	 */
	void initUI(){
			
		mainFrame = new JFrame();
		mainFrame.setTitle("NB Gardens IMS");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String path = IMSRunner.RES_PATH_PRIMARY;
		
		try{
			
			if(IMSRunner.primaryPath) path = IMSRunner.RES_PATH_PRIMARY;
			else path = IMSRunner.RES_PATH_SECONDARY;
			mainFrame.setIconImage(ImageIO.read(new File(path, IMSRunner.ICON_NAME)));
		}
		catch(IOException ioe){
			System.out.println("Icon Error!");
		}
		JMenuBar topMenuBar = new JMenuBar();
		topMenuBar.setOpaque(true);
		topMenuBar.setPreferredSize(new Dimension(200, 20));
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem printItem;
		JMenuItem exitItem;
		fileMenu.getAccessibleContext().setAccessibleDescription("Print and exit commands");
		printItem = new JMenuItem("Print Stock Report");
		fileMenu.add(printItem);
		exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		
		/*
		JMenu simulationMenu = new JMenu("Simulation");
		JCheckBoxMenuItem simulationSelection;
		simulationSelection = new JCheckBoxMenuItem("Enable Simulation");
		simulationMenu.add(simulationSelection);
		*/
		topMenuBar.add(fileMenu);
		//topMenuBar.add(simulationMenu);
	
		
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout()); 
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,5));
		
		initializeTable();

		
		
		JButton refreshButton = new JButton("Refresh");
		JButton addButton = new JButton("Add Product");
		JButton deleteButton = new JButton("Delete");
		
		buttonPanel.add(refreshButton);
		buttonPanel.add(new JLabel());
		buttonPanel.add(addButton);
		buttonPanel.add(new JLabel());
		buttonPanel.add(deleteButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		ImagePanel iPanel = new ImagePanel(path + IMSRunner.LOGO_NAME);
		mainPanel.add(iPanel, BorderLayout.WEST);
		
		printItem.addActionListener(controller);
		exitItem.addActionListener(controller);
		//simulationSelection.addActionListener(controller);
		
		refreshButton.addActionListener(controller);
		addButton.addActionListener(controller);
		deleteButton.addActionListener(controller);
		
		deleteButton.setEnabled(false);
		
		mainFrame.setJMenuBar(topMenuBar);
		mainFrame.setContentPane(mainPanel);
		mainFrame.pack();
		mainFrame.setResizable(false);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int locationX = (screenSize.width - mainFrame.getWidth()) / 2;
		int locationY = (screenSize.height - mainFrame.getHeight()) / 2;
		mainFrame.setLocation(locationX,locationY);
		
		try{
			splash.dispose();
		}
		catch(NullPointerException npe){
		}
		
		mainFrame.setVisible(true);
		
	}

	
	/**
	 * Initializes the table with column names and adds the products from the model to it.
	 * Also sets widths for the columns and ensures uneditability for all non-stock columns.
	 */
	void initializeTable(){

		productTable = null;
		tableModel = null;
		tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		for(Product p : model.productList){
			Object[] oArray = {p.getProductId(), p.getProductName(), p.getCurrentStock(), p.getCriticalStock(), p.getRequiredStock(), p.isPorousware(), p.getPrice()};
			tableModel.addRow(oArray);
		}
		productTable = new JTable(tableModel){
			@Override
			public boolean isCellEditable(int row, int column){
				if(column == 2) return true;
				return false;
			}
		};
		productTable.getTableHeader().setReorderingAllowed(false);
		productTable.getTableHeader().setResizingAllowed(false);
		
		productTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(0).setMinWidth(20);
		productTable.getColumnModel().getColumn(1).setPreferredWidth(175);
		productTable.getColumnModel().getColumn(1).setPreferredWidth(175);
		productTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(3).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(4).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(5).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(6).setPreferredWidth(20);
		
		mainPanel.add(new JScrollPane(this.productTable), BorderLayout.CENTER);
		

		tableModel.addTableModelListener(controller);
		
		System.out.println("Table call");
	}
	
	/**
	 * Refreshes the table with new data.
	 */
	void refreshTable(){
		
		tableModel.removeTableModelListener(controller);
		tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		for(Product p : model.productList){
			Object[] oArray = {p.getProductId(), p.getProductName(), p.getCurrentStock(), p.getCriticalStock(), p.getRequiredStock(), p.isPorousware(), p.getPrice()};
			tableModel.addRow(oArray);
			System.out.println(oArray[1]);
		}
		
		productTable.setModel(tableModel);
		
		productTable.getTableHeader().setReorderingAllowed(false);
		productTable.getTableHeader().setResizingAllowed(false);
		
		productTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(0).setMinWidth(20);
		productTable.getColumnModel().getColumn(1).setPreferredWidth(175);
		productTable.getColumnModel().getColumn(1).setPreferredWidth(175);
		productTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(3).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(4).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(5).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(6).setPreferredWidth(20);	
		
		tableModel.addTableModelListener(controller);
		
		System.out.println("Table call");

	}
	
	
	/**
	 * A simple class to show an image in a JPanel.
	 * @author aneil
	 *
	 */
	public class ImagePanel extends JPanel{
		
		/**
		 * Creates a JLabel that renders an image on top of a JPanel
		 * @param address Address of the image
		 */
		public ImagePanel(String address){
			
			JLabel imageLabel;
			
			try{
				ImageIcon icon = new ImageIcon(address);
				imageLabel = new JLabel();
				imageLabel.setIcon(icon);
			}
			catch(Exception e){
				imageLabel = new JLabel("Image error!");
			}
			this.add(imageLabel);
		}
		
	}
}
