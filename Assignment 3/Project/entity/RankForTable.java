package entity;

/**
 * The class RankForTable represents a generic Rank for table view
 * 
 * @author gal
 *
 */
public class RankForTable implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int customerId;
	private double rank;
	private String firstName;
	private String lastName;

	/**
	 * RankForTable constructor with fields as parameters
	 * 
	 * @param customerId The RankForTable customer id
	 * @param rank       The RankForTable rank
	 */
	public RankForTable(int customerId, double rank) {
		this.customerId = customerId;
		this.rank = rank;
	}
	
	/**
	 * RankForTable constructor with all fields as parameters
	 * 
	 * @param customerId The RankForTable customer id
	 * @param rank       The RankForTable rank
	 * @param firstName  The first name
	 * @param lastName   The last name
	 */
	public RankForTable(int customerId, double rank, String firstName, String lastName) {
		this.customerId = customerId;
		this.rank = rank;
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
	 * Get the customer id
	 * 
	 * @return customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * Customer id setter method
	 * 
	 * @param customerId
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * Get the rank
	 * 
	 * @return rank
	 */
	public double getRank() {
		return rank;
	}

	/**
	 * Rank setter method
	 * 
	 * @param rank
	 */
	public void setRank(double rank) {
		this.rank = rank;
	}

}
