package alexIMS;

public class IMSRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IMSModel model = new IMSModel();
		
		//populating model for testing purposes
		model.addProduct(new Product(1,"Gnom", 3, 2.50, 1));
		model.addProduct(new Product(2,"Diamond Sun Lounger", 2, 30000.0, 1));
		model.addProduct(new Product(3,"Gareth Gnome", 7, 25.99, 3));
		model.addProduct(new Product(4,"James", 20, 15.50, 2));
		model.addProduct(new Product(5,"Alien Gnome", 22, 19.99, 5));
		model.addProduct(new Product(6,"King Gnome", 8, 99.99, 2));
		model.addProduct(new Product(7,"Fat Gnome", 11, 8.99, 3));
		model.addProduct(new Product(8,"Gnomebuilder Academy", 12, 65.00, 4));
		model.addProduct(new Product(9,"Tiny Fountain", 99, 2.50, 15));
		model.addProduct(new Product(10,"Gnome Gnome",75, 10.00,12));
		
		model.enableDecrementTimer();
		//model.printStockReport();
		//model.printStock();
	}

}
