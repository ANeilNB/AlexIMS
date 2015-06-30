package alexIMS;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.javafx.collections.MappingChange.Map;

public class IMSAddView extends JDialog implements ActionListener {

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
	private Map productMap;
	
	
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
		
		this.setSize(200, 200);
		this.setContentPane(panel);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
