package alexIMS;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {

	private ArrayList<Product> rows;
	
	public ProductTableModel(ArrayList<Product> productRows){
		
	}
	
	@Override
	public int getColumnCount() {
		return Product.NUMBER_OF_VALUES;
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Product p = rows.get(rowIndex);
		
		switch(columnIndex){
		case 1:
			return p.getProductId();
		case 2:
			return p.getProductName();
		case 3:
			return p.getCurrentStock();
		case 4:
			return p.getCriticalStock();
		case 5:
			return p.getRequiredStock();
		case 6:
			return p.getCurrentPrice();
		case 7:
			return p.getDateUpdated();
		default:
			return "Error";
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){
		if(columnIndex == 2) return true;
		return false;
	}

}
