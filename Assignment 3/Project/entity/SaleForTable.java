package entity;

/**
 * The class SaleForTable represents a generic sale with some fields of sale
 * pattern
 * 
 * @author gal
 *
 */
public class SaleForTable implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String saleName;
	private String startDate;
	private String endDate;
	private int discount;
	private String saleReason;

	/**
	 * SaleForTable constructor with all fields as parameters
	 * 
	 * @param saleName   The sale name
	 * @param startDate  The start date of the sale
	 * @param endDate    The end date of the sale
	 * @param discount   The discount of the sale
	 * @param saleReason The sale reason
	 */
	public SaleForTable(String saleName, String startDate, String endDate, int discount, String saleReason) {
		this.saleName = saleName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.discount = discount;
		this.saleReason = saleReason;
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
	 * Set sale name
	 * 
	 * @param saleName The sale name
	 */
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	/**
	 * Get the start date of the sale
	 * 
	 * @return The start date of the sale
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Set start date of the sale
	 * 
	 * @param startDate The start date of the sale
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get the end date of the sale
	 * 
	 * @return The end date of the sale
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Set end date of the sale
	 * 
	 * @param endDate The end date of the sale
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * Get the discount of the sale
	 * 
	 * @return The discount of the sale
	 */
	public int getDiscount() {
		return discount;
	}

	/**
	 * Set discount of the sale
	 * 
	 * @param discount The discount of the sale
	 */
	public void setDiscount(int discount) {
		this.discount = discount;
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
