package alexIMS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class IMSController implements ActionListener, TableModelListener {

	private IMSModel model;
	private IMSView view;
	
	public IMSController(IMSModel model, IMSView view){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = e.getFirstRow();
		TableModel tableModel = (TableModel)e.getSource();
		
		int productId = (int) tableModel.getValueAt(row, 0);
		
		try{
			int newStock = Integer.parseInt((String) tableModel.getValueAt(row, 2));
			
			for(Product p : model.productList){
				if(productId == p.getProductId()){
					p.setCurrentStock(newStock);
				}
			}	
		}
		catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(view.mainFrame, "Not a valid number!", "Input Error!", JOptionPane.ERROR_MESSAGE);
			view.tableModel.setValueAt(String.valueOf(model.productList.get(row).getCurrentStock()), row, e.getColumn());
		}
		finally{
			System.out.println(model.productList.get(row).getCurrentStock());
		}
	}

}
