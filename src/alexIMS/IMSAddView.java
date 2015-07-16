package alexIMS;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private JLabel requiredLabel;
	private SpinnerNumberModel requiredInputModel;
	private JSpinner requiredField;
	private JLabel priceLabel;
	private SpinnerNumberModel priceInputModel;
	private JSpinner priceField;
	private JLabel porouswareLabel;
	private JCheckBox porouswareField;
	private JButton addButton;
	private JButton cancelButton;
	private HashMap<String, Object> productMap;
	
	private ActionListener addListener;
	
	
	public IMSAddView(Frame parent){
		super(parent, "Add New Product", true);
	}
		
		
	/**
	 * Creates the product creation GUI.
	 * Creates and adds all elements as well as calibration to ensure only valid entries can be input.
	 */
	void initGUI(){
		//System.out.println("Dialog");
		
		JPanel panel = new JPanel(new GridLayout(8,2));
		
		try{
			this.setIconImage(ImageIO.read(new File("res/nbgicon.png")));
		}
		catch(IOException ioe){
			//System.out.println("Icon Error!");
		}
		
		idLabel = new JLabel("Product Id (optional)");
		nameLabel = new JLabel("Product Name");
		stockLabel = new JLabel("Current Stock");
		criticalLabel = new JLabel("Critical Stock");
		requiredLabel = new JLabel("Required Stock");
		priceLabel = new JLabel("Product Price");
		porouswareLabel = new JLabel("Porousware?");
				
		try{
			
			idField = new JTextField();
			idField.setColumns(5);
			idField.setEnabled(false);
			
			nameField = new JTextField(35);
			
			stockInputModel = new SpinnerNumberModel(1,0,9999,1);
			stockField = new JSpinner(stockInputModel);
			
			criticalInputModel = new SpinnerNumberModel(1,0,9999,1);
			criticalField = new JSpinner(criticalInputModel);
			
			requiredInputModel = new SpinnerNumberModel(1,0,9999,1);
			requiredField = new JSpinner(requiredInputModel);
			
			priceInputModel = new SpinnerNumberModel(1.0,0,100000.00,0.01);
			priceField = new JSpinner(priceInputModel);
			JSpinner.NumberEditor priceEditor = (JSpinner.NumberEditor) priceField.getEditor();
			DecimalFormat format = priceEditor.getFormat();
			format.setMinimumFractionDigits(2);
			format.setMaximumFractionDigits(2);
			
			porouswareField = new JCheckBox();
		}
		catch(Exception e){
			//System.out.println("RIP MaskFormatter!");
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
		panel.add(requiredLabel);
		panel.add(porouswareLabel);
		panel.add(requiredField);
		panel.add(porouswareField);
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
		
		this.setSize(450, 175);
		this.setResizable(false);
		this.setContentPane(panel);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int locationX = (screenSize.width - this.getWidth()) / 2;
		int locationY = (screenSize.height - this.getHeight()) / 2;
		this.setLocation(locationX,locationY);
		
		this.setVisible(true);
	}
	
	/**
	 * Takes an ActionListener to capture the action to create the product.
	 * @param al ActionListener that will handle new product adding
	 */
	void setAddActionListener(ActionListener al){
		this.addListener = al;
	}
	
	/**
	 * Validates the input currently in the fields on the UI.
	 * @return true if the input is valid, false if it is not
	 */
	boolean validateInput(){
		
		int id;
		String name;
		int stock;
		int critical;
		int required;
		double price;
		boolean porousware;
		
		try{
			name = nameField.getText();
			stock = stockInputModel.getNumber().intValue();
			critical = criticalInputModel.getNumber().intValue();
			required = requiredInputModel.getNumber().intValue();
			price = priceInputModel.getNumber().doubleValue();
			porousware = porouswareField.isSelected();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
		if(((name.length() > 3)&&(name.length() < 35))||((stock >= 1)&&(stock < 9999))||
				((critical >= 1)&&(critical < 2000))||(price>0)){
			productMap = new HashMap<String, Object>(6);
			productMap.put("product_name", name);
			productMap.put("current_stock", stock);
			productMap.put("critical_stock", critical);
			productMap.put("required_stock", required);
			productMap.put("product_price", price);
			productMap.put("porousware", porousware);
			return true;
		}
		else{
			//System.err.println("Rekt");
			return false;
		}
	}
	
	/**
	 * Returns a HashMap of data a Product requires, using hash keys with the same names as database columns
	 * @return HashMap of product data
	 */
	HashMap<String, Object> getProductInfo(){
		if(validateInput()){
			return productMap;
		}
		return null;
	}
}

