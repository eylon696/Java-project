package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class OrderHomeHeatingFuel represents a generic order home heating fuel
 * 
 * @author Eylon
 *
 */
public class OrderHomeHeatingFuel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int customerId;
	private String date;
	private double quantity;
	private String address;
	private String estimatedDate;
	private String deliveryType;
	private float price;
	private String payment;
	private String delivered;
	
	/**
	 * OrderHomeHeatingFuel constructor with all fields as parameters
	 * 
	 * @param id            The OrderHomeHeatingFuel id
	 * @param customerId    The OrderHomeHeatingFuel customer id
	 * @param date          The OrderHomeHeatingFuel date
	 * @param quantity      The OrderHomeHeatingFuel quantity
	 * @param address       The OrderHomeHeatingFuel address
	 * @param estimatedDate The OrderHomeHeatingFuel estimatedDate
	 * @param deliveryType  The OrderHomeHeatingFuel deliveryType
	 * @param price         The OrderHomeHeatingFuel price
	 * @param payment       The OrderHomeHeatingFuel payment
	 * @param delivered     The OrderHomeHeatingFuel delivered
	 */
	public OrderHomeHeatingFuel(int id, int customerId, String date, double quantity, String address,
			String estimatedDate, String deliveryType, float price, String payment, String delivered) {
		this.id = id;
		this.customerId = customerId;
		this.date = date;
		this.quantity = quantity;
		this.address = address;
		this.estimatedDate = estimatedDate;
		this.deliveryType = deliveryType;
		this.price = price;
		this.payment = payment;
		this.delivered = delivered;
	}
	
	/**
	 * OrderHomeHeatingFuel constructor with all fields coming from the DB using
	 * ResultSet
	 * 
	 * @param resultSet Has all the order home heating fuel parameters
	 * @throws SQLException
	 */
	public OrderHomeHeatingFuel(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getInt(1);
		this.customerId = resultSet.getInt(2);
		this.date = resultSet.getString(3);
		this.quantity = resultSet.getDouble(4);
		this.address = resultSet.getString(5);
		this.estimatedDate = resultSet.getString(6);
		this.deliveryType = resultSet.getString(7);
		this.price = resultSet.getFloat(8);
		this.payment = resultSet.getString(9);
		this.delivered = resultSet.getString(10);
	}

	/**
	 * Get the order home heating fuel customer id
	 * 
	 * @return The customer id
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
	 * Get the order home heating fuel id
	 * 
	 * @return The id
	 */
	public int getId() {
		return id;
	}

	/**
	 * id setter method
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the order home heating fuel date
	 * 
	 * @return The date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Customer id setter method
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Get the order home heating fuel quantity
	 * 
	 * @return The quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * Quantity setter method
	 * 
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * Get the order home heating fuel address
	 * 
	 * @return The address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Address setter method
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get the order home heating fuel estimated date
	 * 
	 * @return The estimatedDate
	 */
	public String getEstimatedDate() {
		return estimatedDate;
	}

	/**
	 * Estimated date setter method
	 * 
	 * @param estimatedDate
	 */
	public void setEstimatedDate(String estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	/**
	 * Get the order home heating fuel delivery type
	 * 
	 * @return The deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * Delivery type setter method
	 * 
	 * @param deliveryType
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * Get the order home heating fuel price
	 * 
	 * @return The price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Price setter method
	 * 
	 * @param price
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Get the order home heating fuel payment
	 * 
	 * @return The payment
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * Payment setter method
	 * 
	 * @param payment
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * Get the order home heating fuel delivered
	 * 
	 * @return The delivered
	 */
	public String getDelivered() {
		return delivered;
	}

	/**
	 * Delivered setter method
	 * 
	 * @param delivered
	 */
	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}

}
