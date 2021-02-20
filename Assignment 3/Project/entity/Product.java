package entity;

/**
 * The class Product represents a generic product
 * 
 * @author Eylon
 *
 */
public class Product implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int productId;
	private int productPrice;

	/**
	 * Product constructor with all fields as parameters
	 * 	
	 * @param productId    The Product product Id
	 * @param productPrice The Product product price
	 */
	public Product(int productId, int productPrice) {
		this.productId = productId;
		this.productPrice = productPrice;
	}

	/**	
	 * Get the product Id
	 * 
	 * @return productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * Product Id setter method
	 * 
	 * @param productId
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * Get the product price
	 * 
	 * @return product Price
	 */
	public int getProductPrice() {
		return productPrice;
	}

	/**
	 * Product price setter method
	 * 
	 * @param productPrice
	 */
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

}
