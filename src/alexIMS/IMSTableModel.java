package alexIMS;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class IMSTableModel extends AbstractTableModel {

	private String[] columnNames = {"Id", "Product Name", "Stock"};
	private Class[] columnTypes = {Integer.class, String.class, Integer.class};
	private List<Object[]> data;
	
	public IMSTableModel(){
		super();
		data = new ArrayList<Object[]>();
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
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return data.get(row)[column];
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		if(column == 3) return true;
		return false;
	}

	public void addRow(Object[] oArray) {
		// TODO Auto-generated method stub
		
	}

}
