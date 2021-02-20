package entity;

/**
 * The class PeriodicCharacterizationReportForTable represents a generic
 * periodic characterization report
 * 
 * @author gal
 *
 */
public class PeriodicCharacterizationReportForTable implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int customerID;
	private int numOfPurchases;
	private String chainName;
	private String firstName;
	private String lastName;
	
	/**
	 * PeriodicCharacterizationReportForTable constructor with all fields as
	 * parameters
	 * 
	 * @param customerID     The customer id
	 * @param numOfPurchases The number of purchases
	 * @param chainName      The chain name
	 */
	public PeriodicCharacterizationReportForTable(int customerID, int numOfPurchases, String chainName) {
		this.customerID = customerID;
		this.numOfPurchases = numOfPurchases;
		this.chainName = chainName;
	}

	/**
	 * PeriodicCharacterizationReportForTable constructor with all fields as
	 * parameters
	 * 
	 * @param customerID     The customer id
	 * @param numOfPurchases The number of purchases
	 * @param chainName      The chain name
	 * @param firstName      The first name
	 * @param lastName       The last name
	 */
	public PeriodicCharacterizationReportForTable(int customerID, int numOfPurchases, String chainName,String firstName, String lastName) {
		this.customerID = customerID;
		this.numOfPurchases = numOfPurchases;
		this.chainName = chainName;
		this.firstName=firstName;
		this.lastName=lastName;
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
	 * Get the customer id
	 * 
	 * @return The customer id
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * Set the customer id
	 * 
	 * @param customerID The customer id
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * Get the number of purchases
	 * 
	 * @return The number of purchases
	 */
	public int getNumOfPurchases() {
		return numOfPurchases;
	}

	/**
	 * Set the number of purchases
	 * 
	 * @param numOfPurchases The number of purcases
	 */
	public void setNumOfPurchases(int numOfPurchases) {
		this.numOfPurchases = numOfPurchases;
	}

	/**
	 * Get the chain name
	 * 
	 * @return The chain name
	 */
	public String getChainName() {
		return chainName;
	}

	/**
	 * Set the chain name
	 * 
	 * @param chainName The chain name
	 */
	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

}
