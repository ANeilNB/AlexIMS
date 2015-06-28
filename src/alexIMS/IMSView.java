package alexIMS;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		String[] tableColumns = {"Id, Product Name, Current Stock"};
		JTable productTable = new JTable(new IMSTableModel(model.productList));
		
	}
	
	
	
	private Object[][] populateTable(){
		Object[][] productTableData = new Object[model.productList.size()][3];
		
		for(int i=0; i<model.productList.size(); i++){
			productTableData[i][0] = model.productList.get(i).getProductId();
			productTableData[i][1] = model.productList.get(i).getProductName();
			productTableData[i][0] = model.productList.get(i).getCurrentStock();
		}
		return productTableData;
	}
}
