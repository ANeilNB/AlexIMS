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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class IMSAddView extends JDialog {

	private JLabel idLabel;
	private JTextField idField;
	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel stockLabel;
	private SpinnerNumberModel stockInputModel;
	private JSpinner stockField;
	private JLabel criticalLabel;
	private SpinnerNumberModel criticalInputModel;
	private JSpinner criticalField;
	private JLabel priceLabel;
	private SpinnerNumberModel priceInputModel;
	private JSpinner priceField;
	private JButton addButton;
	private JButton cancelButton;
	private HashMap productMap;
	
	private ActionListener addListener;
	
	
	public IMSAddView(Frame parent){
		super(parent, "Add New Product", true);
	}
		
		
	void initGUI(){
		System.out.println("Dialog");
		
		JPanel panel = new JPanel(new GridLayout(6,2));
		
		idLabel = new JLabel("Product Id (optional)");
		nameLabel = new JLabel("Product Name");
		stockLabel = new JLabel("Current Stock");
		criticalLabel = new JLabel("Critical Stock");
		priceLabel = new JLabel("Product Price");
				
		try{
			
			idField = new JTextField();
			idField.setColumns(5);
			idField.setEnabled(false);
			
			nameField = new JTextField(35);
			
			stockInputModel = new SpinnerNumberModel(1,0,9999,1);
			stockField = new JSpinner(stockInputModel);
			
			criticalInputModel = new SpinnerNumberModel(1,0,9999,1);
			criticalField = new JSpinner(criticalInputModel);
			
			priceInputModel = new SpinnerNumberModel(1.0,0,100000.00,1);
			priceField = new JSpinner(priceInputModel);
		}
		catch(Exception e){
			System.out.println("RIP MaskFormatter!");
		}
		
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
		
		if(addListener != null){
			addButton.addActionListener(addListener);
		}
			
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		//cancelButton.addActionListener(this);
		
		this.setSize(450, 175);
		this.setResizable(false);
		this.setContentPane(panel);
		this.setVisible(true);
	}
	
	void setAddActionListener(ActionListener al){
		this.addListener = al;
	}
	
	boolean validateInput(){
		
		int id;
		String name;
		int stock;
		int critical;
		double price;
		
		try{
			//id = Integer.parseInt(idField.getText());
			name = nameField.getText();
			stock = stockInputModel.getNumber().intValue();
			critical = criticalInputModel.getNumber().intValue();
			price = priceInputModel.getNumber().doubleValue();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		//System.out.println(name + " " + stock + " " + critical + " " + price);
		
		if(((name.length() > 3)&&(name.length() < 35))||((stock >= 1)&&(stock < 9999))||
				((critical >= 1)&&(critical < 2000))||(price>0)){
			productMap = new HashMap(5);
			productMap.put("product_name", name);
			productMap.put("current_stock", stock);
			productMap.put("critical_stock", critical);
			productMap.put("product_price", price);
			return true;
		}
		else{
			System.err.println("Rekt");
			return false;
		}
	}
	
	HashMap getProductInfo(){
		if(validateInput()){
			return productMap;
		}
		return null;
	}
}

