package alexIMS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class IMSView {

	IMSModel model;
	
	IMSView(IMSModel model){
		this.model = model;
		
		initUI();
	}
	
	private void initUI(){
		
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("NB Gardens IMS");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar topMenuBar = new JMenuBar();
		topMenuBar.setOpaque(true);
		topMenuBar.setPreferredSize(new Dimension(200, 20));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout()); 
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4,1));
		
		//IMSTableModel tableModel = new IMSTableModel();
		Object[] columnNames = {"Product Id", "Product Name", "Stock"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
				
		for(Product p : model.productList){
			Object[] oArray = {p.getProductId(), p.getProductName(), p.getCurrentStock()};
			tableModel.addRow(oArray);
		}
		
		JTable productTable = new JTable(tableModel);
		
		mainPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.EAST);
		
		mainFrame.setJMenuBar(topMenuBar);
		mainFrame.setContentPane(mainPanel);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
}
