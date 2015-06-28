package alexIMS;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class IMSTableModel extends AbstractTableModel {

	private String[] columnNames = {"Id", "Product Name", "Stock"};
	private Class[] columnTypes = {Integer.class, String.class, Integer.class};
	private List<Object[]> data;
	
	public IMSTableModel(ArrayList<Product> products){
		super();
		data = new ArrayList<Object[]>();
		for(Product p : products){
			Object[] productData = {p.getProductId(), p.getProductName(), p.getCurrentStock()};
			data.add(productData);
		}
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		// TODO Auto-generated method stub
		return data.get(rowIndex)[colIndex];
	}

}
