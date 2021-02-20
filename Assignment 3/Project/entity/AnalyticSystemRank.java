package entity;

/**
 * The class AnalyticSystemRank represents a generic analytic system rank
 * 
 * @author gal
 *
 */
public class AnalyticSystemRank implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int customerId;
	private double rank;
	private String customerType;
	private String timePurchase;
	private String vehicleFuelType; // benzene,diesel,scooter'sFuel

	/**
	 * AnalyticSystemRank constructor with all fields as parameters
	 * 
	 * @param customerId      The customer id
	 * @param customerType    The customer type
	 * @param timePurchase    The time of the purchase
	 * @param vehicleFuelType The vehicle fuel type
	 * @param rank            The rank of customer
	 */	
	public AnalyticSystemRank(int customerId, String customerType, String timePurchase, String vehicleFuelType,
			double rank) {
		this.customerId = customerId;
		this.customerType = customerType;
		this.timePurchase = timePurchase;
		this.vehicleFuelType = vehicleFuelType;
		this.rank = rank;
	}

	/**
	 * Another AnalyticSystemRank constructor with some fields as parameters
	 * 
	 * @param customerId The customer id
	 * @param rank       The rank of customer
	 */
	public AnalyticSystemRank(int customerId, Double rank) {
		this.customerId = customerId;
		this.rank = rank;
	}

	/**
	 * Get the customer type
	 * 
	 * @return The customer type
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Set the customer type
	 * 
	 * @param customerType The customer type
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * Get the time of purchase
	 * 
	 * @return The time of purchase
	 */
	public String getTimePurchase() {
		return timePurchase;
	}

	/**
	 * Set the time of purchase
	 * 
	 * @param timePurchase The time of purchase
	 */
	public void setTimePurchase(String timePurchase) {
		this.timePurchase = timePurchase;
	}

	/**
	 * Get the vehicle fuel type
	 * 
	 * @return The vehicle fuel type
	 */
	public String getVehicleFuelType() {
		return vehicleFuelType;
	}

	/**
	 * Set the vehicle fuel type
	 * 
	 * @param vehicleFuelType The vehicle fuel type
	 */
	public void setVehicleFuelType(String vehicleFuelType) {
		this.vehicleFuelType = vehicleFuelType;
	}

	/**
	 * Get the customer id
	 * 
	 * @return The customer id
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * Set the customer id
	 * 
	 * @param customerId The customer id
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * Get the rank of customer
	 * 
	 * @return The rank of customer
	 */
	public double getRank() {
		return rank;
	}

	/**
	 * Set the rank of customer
	 * 
	 * @param rank The rank of customer
	 */
	public void setRank(double rank) {
		this.rank = rank;
	}

}
