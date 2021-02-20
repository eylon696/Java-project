package entity;

/**
 * The class SalePattern represents a generic Sale Pattern
 * 
 * @author gal
 *
 */
public class SalePattern implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int idPattern;
	private String startDate;
	private String endDate;
	private String description;

	/**
	 * SalePattern constructor with all fields as parameters
	 * 
	 * @param id          The Pattern ID
	 * @param startDate   The start date of sale
	 * @param endDate     The end date of sale
	 * @param description The description
	 */
	public SalePattern(int id, String startDate, String endDate, String description) {
		this.idPattern = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}

	/**
	 * Get the Pattern ID
	 * 
	 * @return The Pattern ID
	 */
	public int getIdPattern() {
		return idPattern;
	}

	/**
	 * Get the start date of sale
	 * 
	 * @return The start date of sale
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Get the end date of sale
	 * 
	 * @return The end date of sale
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Get the description
	 * 
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the Pattern ID
	 * 
	 * @param idPattern The Pattern ID
	 */
	public void setIdPattern(int idPattern) {
		this.idPattern = idPattern;
	}

	/**
	 * Set the start date of sale
	 * 
	 * @param startDate The start date of sale
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Set the end date of sale
	 * 
	 * @param endDate The end date of sale
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * Set the description
	 * 
	 * @param description The description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}