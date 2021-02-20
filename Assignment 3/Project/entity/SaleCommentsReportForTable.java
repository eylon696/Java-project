package entity;

/**
 * The class SaleCommentsReportForTable represents a generic for sale comments
 * report table view
 * 
 * @author gal
 *
 */
public class SaleCommentsReportForTable implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int customerId;
	private double totalPrice;
	private String firstName;
	private String lastName;

	/**
	 * SaleCommentsReportForTable constructor with fields as parameters
	 * 
	 * @param customerId The SaleCommentsReportForTable customer id
	 * @param totalPrice The SaleCommentsReportForTable total price
	 */
	public SaleCommentsReportForTable(int customerId, double totalPrice) {
		this.customerId = customerId;
		this.totalPrice = totalPrice;
	}	

	/**
	 * SaleCommentsReportForTable constructor with all fields as parameters
	 * 
	 * @param customerId The SaleCommentsReportForTable customer id
	 * @param totalPrice The SaleCommentsReportForTable total price
	 * @param firstName  The first name
	 * @param lastName   The last name
	 */
	public SaleCommentsReportForTable(int customerId, double totalPrice, String firstName, String lastName) {
		this.customerId = customerId;
		this.totalPrice = totalPrice;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * Get first name
	 * 
	 * @return The first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set first name
	 * 
	 * @param firstName The first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get last name
	 * 
	 * @return The last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set last name
	 * 
	 * @param lastName The last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the customer Id
	 * 
	 * @return customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * Customer Id setter method
	 * 
	 * @param customerId
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * Get the total price
	 * 
	 * @return totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Total price setter method
	 * 
	 * @param totalPrice
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
