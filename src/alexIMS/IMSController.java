package alexIMS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class IMSController implements ActionListener, TableModelListener {

	private IMSModel model;
	private IMSView view;
	
	private IMSAddView addDialog;
	
	public IMSController(IMSModel model, IMSView view){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		System.out.println(e.getActionCommand());
		String actionName = e.getActionCommand();
		
		switch (actionName){
		case "Add Product":
			
			addDialog = new IMSAddView(view.mainFrame);	
			
			addDialog.setAddActionListener(this);
			
			addDialog.initGUI();
			
			break;
		case "Refresh":
			view.refreshTable();
			break;
		case "Delete":
			int row = view.productTable.getSelectedRow();
			
			System.out.println(row);
			
			if(row == -1){
				JOptionPane.showMessageDialog(view.mainFrame, "No row selected!", "Error",0);
			}else{
				int deleteId = (int) view.productTable.getValueAt((row), 0);
				model.removeProduct(deleteId);
			}
			
			view.refreshTable();
			break;
		case "Add":
			if(addDialog != null){
				
				if(addDialog.validateInput()){
					HashMap newInfo = addDialog.getProductInfo();
					
					String newName = (String) newInfo.get("product_name");
					int newStock = (int) newInfo.get("current_stock");
					int newCritical = (int) newInfo.get("critical_stock");
					double newPrice = (double) newInfo.get("product_price");
					
					model.addProduct(newName, newStock, newCritical, newPrice);
					
					addDialog.dispose();
					
					view.refreshTable();
				}
				else{
					JOptionPane.showMessageDialog(view.mainFrame, "Invalid input", "Error", 0);
				}
			}
			break;
		case "Print Stock Report":
			model.printStockReport();
			JOptionPane.showMessageDialog(view.mainFrame, "Report Generated!", "Success", 1);
			break;
		case "Enable Simulation":
			break;
		case "Exit":
			System.exit(0);
		default:
			break;
		}
		//System.out.println("Action complete!");
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = e.getFirstRow();
		TableModel tableModel = (TableModel)e.getSource();
		
		tableModel.removeTableModelListener(this);
		
		int productId = (int) tableModel.getValueAt(row, 0);
		
		try{
			int newStock = Integer.parseInt((String) tableModel.getValueAt(row, 2));
			
			if((newStock < 0)||(newStock > 2500)){
				JOptionPane.showMessageDialog(view.mainFrame, "Unreasonable value. (Below 0 or above 2500)", "Input Error!", JOptionPane.ERROR_MESSAGE);
				view.tableModel.setValueAt(String.valueOf(model.productList.get(row).getCurrentStock()), row, e.getColumn());
			}
			else{
				for(Product p : model.productList){
					if(productId == p.getProductId()){
						if(p.setCurrentStock(newStock)) JOptionPane.showMessageDialog(view.mainFrame, "Stock of " + p.getProductName() + " (ID: " + p.getProductId() + ") increased.");
						if(p.checkStock()) JOptionPane.showMessageDialog(view.mainFrame, "Stock of " + p.getProductName() + " (ID: " + p.getProductId() + ") critically low.");
					}
				}
			}
		}
		catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(view.mainFrame, "Not a valid number!", "Input Error!", JOptionPane.ERROR_MESSAGE);
			view.tableModel.setValueAt(String.valueOf(model.productList.get(row).getCurrentStock()), row, e.getColumn());
		}
		finally{
			tableModel.addTableModelListener(this);
			System.out.println(model.productList.get(row).getCurrentStock());
		}
	}
}