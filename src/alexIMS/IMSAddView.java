package alexIMS;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.javafx.collections.MappingChange.Map;

public class IMSAddView extends JDialog {

	private JLabel idLabel;
	private JFormattedTextField idField;
	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel stockLabel;
	private JFormattedTextField stockField;
	private JLabel criticalLabel;
	private JFormattedTextField criticalField;
	private JLabel priceLabel;
	private JFormattedTextField priceField;
	private JButton addButton;
	private JButton cancelButton;
	private HashMap productMap;
	
	
	public IMSAddView(Frame parent){
		super(parent, "Add New Product", true);
		 
		System.out.println("Dialog");
		
		JPanel panel = new JPanel(new GridLayout(6,2));
		
		idLabel = new JLabel("Product Id (optional)");
		nameLabel = new JLabel("Product Name");
		stockLabel = new JLabel("Current Stock");
		criticalLabel = new JLabel("Critical Stock");
		priceLabel = new JLabel("Product Price");
		
		idField = new JFormattedTextField(NumberFormat.getNumberInstance());
		idField.setColumns(6);
		idField.setEnabled(false);
		
		nameField = new JTextField(35);
		
		stockField = new JFormattedTextField(NumberFormat.getNumberInstance());
		stockField.setColumns(5);
		
		criticalField = new JFormattedTextField(NumberFormat.getNumberInstance());
		criticalField.setColumns(5);
		
		priceField = new JFormattedTextField(NumberFormat.getCurrencyInstance());
		priceField.setColumns(8);
		
		addButton = new JButton("Add");
		cancelButton = new JButton("Cancel");
		
		panel.add(idLabel);
		panel.add(nameLabel);
		panel.add(idField);
		panel.add(nameField);
		panel.add(stockLabel);
		panel.add(criticalLabel);
		panel.add(stockField);
		panel.add(criticalField);
		panel.add(priceLabel);
		panel.add(addButton);
		panel.add(priceField);
		panel.add(cancelButton);
		
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		//cancelButton.addActionListener(this);
		
		this.setSize(450, 175);
		this.setContentPane(panel);
		this.setVisible(true);
		
	}
	
	void setAddActionListener(ActionListener al){
		addButton.addActionListener(al);
	}
	
	private boolean validateInput(){
		int id = Integer.parseInt(idField.getText());
		String name = nameField.getText();
		int stock = Integer.parseInt(stockField.getText());
		int critical = Integer.parseInt(criticalField.getText());
		double price = Double.parseDouble(priceField.getText());
		
		if(((name.length() > 3)&&(name.length() < 35))||((stock >= 1)&&(stock < 3000))||
				((critical >= 1)&&(critical < 250))||(price>0)){
			productMap = new HashMap(5);
			productMap.put("product_name", name);
			productMap.put("current_stock", stock);
			productMap.put("critical_stock", critical);
			productMap.put("product_price", price);
			return true;
		}
		else{
			return false;
		}
	}
	

}
