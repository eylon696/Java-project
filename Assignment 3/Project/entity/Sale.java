package entity;

/**
 * The class Sale represents a generic Sale
 * 
 * @author gal
 *
 */
public class Sale implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String saleName;
	private int patternID;
	private String saleReason;
	private int discount;
	private int numOfCustomersPurchased;
	private double sumOfAllPurchases;

	/**
	 * Sale constructor with all fields as parameters
	 * 
	 * @param saleName                The sale name
	 * @param patternID               The pattern ID
	 * @param saleReason              The sale reason
	 * @param discount                The discount of sale
	 * @param numOfCustomersPurchased The number of customers purchased
	 * @param sumOfAllPurchases       The sum of all purchases
	 */
	public Sale(String saleName, int patternID, String saleReason, int discount, int numOfCustomersPurchased,
			double sumOfAllPurchases) {
		this.saleName = saleName;
		this.patternID = patternID;
		this.saleReason = saleReason;
		this.discount = discount;
		this.numOfCustomersPurchased = numOfCustomersPurchased;
		this.sumOfAllPurchases = sumOfAllPurchases;
	}

	/**
	 * Get the discount of sale
	 * 
	 * @return The discount of sale
	 */
	public int getDiscount() {
		return discount;
	}

	/**
	 * Set the discount of sale
	 * 
	 * @param discount The discount of sale
	 */
	public void setDiscount(int discount) {
		this.discount = discount;
	}

	/**
	 * Get number of customers purchased
	 * 
	 * @return The number of customers purchased
	 */
	public int getNumOfCustomersPurchased() {
		return numOfCustomersPurchased;
	}

	/**
	 * Set number of customers purchased
	 * 
	 * @param numOfCustomersPurchased The number of customers purchased
	 */
	public void setNumOfCustomersPurchased(int numOfCustomersPurchased) {
		this.numOfCustomersPurchased = numOfCustomersPurchased;
	}

	/**
	 * Get the sum of all purchases
	 * 
	 * @return The sum of all purchases
	 */
	public double getSumOfAllPurchases() {
		return sumOfAllPurchases;
	}

	/**
	 * Set the sum of all purchases
	 * 
	 * @param sumOfAllPurchases The sum of all purchases
	 */
	public void setSumOfAllPurchases(double sumOfAllPurchases) {
		this.sumOfAllPurchases = sumOfAllPurchases;
	}

	/**
	 * Get the sale name
	 * 
	 * @return The sale name
	 */
	public String getSaleName() {
		return saleName;
	}

	/**
	 * Set the sale name
	 * 
	 * @param saleName The sale name
	 */
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	/**
	 * Get the pattern ID
	 * 
	 * @return The pattern ID
	 */
	public int getPatternID() {
		return patternID;
	}

	/**
	 * Set the pattern ID
	 * 
	 * @param patternID The pattern ID
	 */
	public void setPatternID(int patternID) {
		this.patternID = patternID;
	}

	/**
	 * Get the sale reason
	 * 
	 * @return The sale reason
	 */
	public String getSaleReason() {
		return saleReason;
	}

	/**
	 * Set the sale reason
	 * 
	 * @param saleReason The sale reason
	 */
	public void setSaleReason(String saleReason) {
		this.saleReason = saleReason;
	}

}