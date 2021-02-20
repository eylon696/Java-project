package entity;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * The class Purchase represents a generic purchase
 * 
 * @author gal
 *
 */
public class Purchase implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int idPurchase;
	private int customerId;
	private int licenseNum;
	private String datePurchase;
	private String timePurchase;
	private double quantityPurchase;
	private double pricePurchase;
	private String paymentPurchase; // creditCard,cash
	private String chainName;
	private String stationName;
	private String saleName;

	/**
	 * Purchase constructor with all fields as parameters
	 * 
	 * @param id               The purchase id
	 * @param customerId       The customer id
	 * @param licenseNum       The cars license number of the customer
	 * @param quantityPurchase The quantity of the purchase
	 * @param pricePurchase    The price of the purchase
	 * @param paymentPurchase  The payment type of the purchase
	 * @param chainName        The chain name
	 * @param stationName      The station name
	 * @param saleName         The sale name
	 */
	public Purchase(int id, int customerId, int licenseNum, double quantityPurchase, double pricePurchase,
			String paymentPurchase, String chainName, String stationName, String saleName) {
		this.idPurchase = id;
		this.customerId = customerId;
		this.licenseNum = licenseNum;
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now1 = LocalDateTime.now();
		this.datePurchase = dtf1.format(now1);
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now2 = LocalDateTime.now();
		this.timePurchase = dtf2.format(now2);
		this.quantityPurchase = quantityPurchase;
		this.pricePurchase = pricePurchase;
		this.paymentPurchase = paymentPurchase;
		this.chainName = chainName;
		this.stationName = stationName;
		this.saleName = saleName;
	}

	/**
	 * Another Purchase constructor with fields as parameters
	 * 
	 * @param id               The purchase id
	 * @param customerId       The customer id
	 * @param licenseNum       The cars license number of the customer
	 * @param datePurchase     The date of the purchase
	 * @param timePurchase     The time of the purchase
	 * @param quantityPurchase The quantity of the purchase
	 * @param pricePurchase    The price of the purchase
	 * @param paymentPurchase  The payment type of the purchase
	 * @param chainName        The chain name
	 * @param stationName      The station name
	 * @param saleName         The sale name
	 */
	public Purchase(int id, int customerId, int licenseNum, String datePurchase, String timePurchase,
			double quantityPurchase, double pricePurchase, String paymentPurchase, String chainName, String stationName,
			String saleName) {
		this.idPurchase = id;
		this.customerId = customerId;
		this.licenseNum = licenseNum;
		this.datePurchase = datePurchase;
		this.timePurchase = timePurchase;
		this.quantityPurchase = quantityPurchase;
		this.pricePurchase = pricePurchase;
		this.paymentPurchase = paymentPurchase;
		this.chainName = chainName;
		this.stationName = stationName;
		this.saleName = saleName;
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

	/**
	 * Get the station name
	 * 
	 * @return The station name
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * Set the station name
	 * 
	 * @param stationName The station name
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
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
	 * Get the car's license number of customer
	 * 
	 * @return The license number
	 */
	public int getLicenseNum() {
		return licenseNum;
	}

	/**
	 * Set car's license number of customer
	 * 
	 * @param licenseNum The license number
	 */
	public void setLicenseNum(int licenseNum) {
		this.licenseNum = licenseNum;
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
	 * Set customer id
	 * 
	 * @param customerId The customer id
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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
	 * Set time of purchase
	 * 
	 * @param timePurchase The time of purchase
	 */
	public void setTimePurchase(String timePurchase) {
		this.timePurchase = timePurchase;
	}

	/**
	 * Get the id purchase
	 * 
	 * @return The id purchase
	 */
	public int getIdPurchase() {
		return idPurchase;
	}

	/**
	 * Set id purchase
	 * 
	 * @param idPurchase The id purchase
	 */
	public void setIdPurchase(int idPurchase) {
		this.idPurchase = idPurchase;
	}

	/**
	 * Get the date of purchase
	 * 
	 * @return The date of purchase
	 */
	public String getDatePurchase() {
		return datePurchase;
	}

	/**
	 * Set date of purchase
	 * 
	 * @param datePurchase The date of purchase
	 */
	public void setDatePurchase(String datePurchase) {
		this.datePurchase = datePurchase;
	}

	/**
	 * Get the quantity of purchase
	 * 
	 * @return The quantity of purchase
	 */
	public double getQuantityPurchase() {
		return quantityPurchase;
	}

	/**
	 * Set quantity of purchase
	 * 
	 * @param quantityPurchase The quantity of purchase
	 */
	public void setQuantityPurchase(double quantityPurchase) {
		this.quantityPurchase = quantityPurchase;
	}

	/**
	 * Get the price of purchase
	 * 
	 * @return The price of purchase
	 */
	public double getPricePurchase() {
		return pricePurchase;
	}

	/**
	 * Set the price of purchase
	 * 
	 * @param pricePurchase The price of purchase
	 */
	public void setPricePurchase(double pricePurchase) {
		this.pricePurchase = pricePurchase;
	}

	/**
	 * Get the payment type of purchase
	 * 
	 * @return the payment type of purchase
	 */
	public String getPaymentPurchase() {
		return paymentPurchase;
	}

	/**
	 * Set payment type of purchase
	 * 
	 * @param paymentPurchase The payment type of purchase
	 */
	public void setPaymentPurchase(String paymentPurchase) {
		this.paymentPurchase = paymentPurchase;
	}

}