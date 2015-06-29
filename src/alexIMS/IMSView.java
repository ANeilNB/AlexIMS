package alexIMS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
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
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout()); 
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4,1));
		
		//IMSTableModel tableModel = new IMSTableModel();
		
		initializeTable();
		tableModel.addTableModelListener(controller);

		
		
		JButton refreshButton = new JButton("Refresh");
		JButton addButton = new JButton("Add");
		
		
		mainPanel.add(buttonPanel, BorderLayout.EAST);
		
		mainFrame.setJMenuBar(topMenuBar);
		mainFrame.setContentPane(mainPanel);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	void initializeTable(){
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
		
		mainPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
		
		System.out.println("Table call");
	}
	
}
