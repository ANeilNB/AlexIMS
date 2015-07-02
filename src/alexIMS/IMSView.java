package alexIMS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
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

	JFrame mainFrame;
	JPanel mainPanel;
	JTable productTable;
	final Object[] COLUMN_NAMES = {"Product Id", "Product Name", "Stock", "Critical Stock", "Price"};
	DefaultTableModel tableModel;
	
	
	IMSView(IMSModel model){
		this.model = model;
		
	}
	
	void addController(IMSController controller){
		this.controller = controller;
	}
	
	
	void initUI(){
		
		mainFrame = new JFrame();
		mainFrame.setTitle("NB Gardens IMS");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		JMenu simulationMenu = new JMenu("Simulation");
		JCheckBoxMenuItem simulationSelection;
		simulationSelection = new JCheckBoxMenuItem("Enable Simulation");
		simulationMenu.add(simulationSelection);
		
		topMenuBar.add(fileMenu);
		topMenuBar.add(simulationMenu);
		
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout()); 
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,5));
		
		//IMSTableModel tableModel = new IMSTableModel();
		
		initializeTable();

		
		
		JButton refreshButton = new JButton("Refresh");
		JButton addButton = new JButton("Add Product");
		JButton deleteButton = new JButton("Delete");
		//deleteButton.setEnabled(false);
		
		buttonPanel.add(refreshButton);
		buttonPanel.add(new JLabel());
		buttonPanel.add(addButton);
		buttonPanel.add(new JLabel());
		buttonPanel.add(deleteButton);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		printItem.addActionListener(controller);
		exitItem.addActionListener(controller);
		simulationSelection.addActionListener(controller);
		
		refreshButton.addActionListener(controller);
		addButton.addActionListener(controller);
		deleteButton.addActionListener(controller);
		
		mainFrame.setJMenuBar(topMenuBar);
		mainFrame.setContentPane(mainPanel);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
	}

	
	void initializeTable(){

		productTable = null;
		tableModel = null;
		tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		for(Product p : model.productList){
			Object[] oArray = {p.getProductId(), p.getProductName(), p.getCurrentStock(), p.getCriticalStock(), p.getPrice()};
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
		productTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(3).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(4).setPreferredWidth(20);
		
		mainPanel.add(new JScrollPane(this.productTable), BorderLayout.CENTER);
		

		tableModel.addTableModelListener(controller);
		
		System.out.println("Table call");
	}
	
	void refreshTable(){
		
		tableModel.removeTableModelListener(controller);
		tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		for(Product p : model.productList){
			Object[] oArray = {p.getProductId(), p.getProductName(), p.getCurrentStock(), p.getCriticalStock(), p.getPrice()};
			tableModel.addRow(oArray);
			System.out.println(oArray[1]);
		}
		/*
		productTable = new JTable(tableModel){
			@Override
			public boolean isCellEditable(int row, int column){
				if(column == 2) return true;
				return false;
			}
		};
		*/	
		
		productTable.setModel(tableModel);
		
		productTable.getTableHeader().setReorderingAllowed(false);
		productTable.getTableHeader().setResizingAllowed(false);
		
		productTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(0).setMinWidth(20);
		productTable.getColumnModel().getColumn(1).setPreferredWidth(175);
		productTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(3).setPreferredWidth(20);
		productTable.getColumnModel().getColumn(4).setPreferredWidth(20);		
		
		tableModel.addTableModelListener(controller);
		
		System.out.println("Table call");

	}
}
