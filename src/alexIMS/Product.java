package alexIMS;

/**
 * 
 * @author aneil
 *
 */
public class Product {

	protected int productID;
	protected String productName;
	protected int currentStock;
	protected int currentPrice;
	protected int criticalStock;
	
	
	public int getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(int newStock) {
		this.currentStock = newStock;
	}
	public int getProductID() {
		return productID;
	}
	public String getProductName() {
		return productName;
	}
	public int getCurrentPrice() {
		return currentPrice;
	}
	public int getCriticalStock() {
		return criticalStock;
	}
	
	protected void updateStock(){
		/*
		 * Update currentStock with data from database
		 */
	}
	
	protected void updateDatabase(){
		/*
		 * Update database with current stock values.
		 */
	}
	
	/**
	 * Checks to see if the current stock level of the item (currentStock) is at
	 * or below the critical threshold (criticalStock).
	 * @return true if stock is at or below critical threshold, false if it is above.
	 */
	public boolean checkStock(){
		if(currentStock <= criticalStock) return true;
		return false;
	}
	
}
