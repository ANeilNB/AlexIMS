package alexIMS;

public class IMSRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IMSModel model = new IMSModel();
		
		
		model.addProduct(new Product(1,"Gnom", 3, 2.50, 1));
		model.addProduct(new Product(2,"Diamond Sun Lounger", 2, 30000.0, 1));
		
		//model.printStock();
	}

}
