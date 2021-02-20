package entity;

/**
 * The class FuelingGas represents a generic fueling gas
 * 
 * @author Eylon
 *
 */
public class FuelingGas extends Product implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String productName; // benzene,diesel,scooter'sFuel

	/**		
	 * FuelingGas constructor with all fields as parameters
	 * 
	 * @param productId    The fueling gas first product Id
	 * @param productPrice The fueling gas first product price
	 * @param productName  The fueling gas first product name
	 */
	public FuelingGas(int productId, int productPrice, String productName) {
		super(productId, productPrice);
		this.productName = productName;
	}	
	
	/**	
	 * Get the fueling gas product name
	 * 
	 * @return The product name
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Product name setter method
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

}
