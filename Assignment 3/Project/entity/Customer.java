package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class customer represents a generic customer
 * 
 * @author Eylon
 *
 */	
public class Customer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private int ID;	
	private String password;
	private String email;
	private int creditCardNumber;
	private String type;
	private String SubscriptionType;

	/**	
	 * Customer constructor with all fields as parameters
	 * 
	 * @param firstName        The customer's first name
	 * @param lastName         The customer's last name
	 * @param ID               The customer's ID
	 * @param password         The customer's password
	 * @param email            The customer's email
	 * @param creditCardNumber The customer's credit card number
	 * @param type             The customer's type
	 * @param SubscriptionType The customer's subscription type
	 */
	public Customer(String firstName, String lastName, int ID, String password, String email, int creditCardNumber,
			String type, String SubscriptionType) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.ID = ID;
		this.password = password;
		this.email = email;
		this.creditCardNumber = creditCardNumber;
		this.type = type;
		this.SubscriptionType = SubscriptionType;
	}

	/**
	 * Customer constructor with all fields coming from the DB using ResultSet
	 * 
	 * @param resultSet Has all the customer's parameters
	 * @throws SQLException
	 */
	public Customer(ResultSet resultSet) throws SQLException {
		this.firstName = resultSet.getString(1);
		this.lastName = resultSet.getString(2);
		this.ID = Integer.parseInt(resultSet.getString(3));
		this.password = resultSet.getString(4);
		this.email = resultSet.getString(5);
		this.creditCardNumber = Integer.parseInt(resultSet.getString(6));
		this.type = resultSet.getString(7);
		this.SubscriptionType = resultSet.getString(8);

	}

	/**
	 * Get the customer's first name
	 * 
	 * @return The first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * First name setter method
	 * 
	 * @param firstName The customer's first name
	 */

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the customer's last name
	 * 
	 * @return The last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Last name setter method
	 * 
	 * @param lastName The customer's last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the customer's ID
	 * 
	 * @return The ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * ID setter method
	 * 
	 * @param iD The customer's ID
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Get the customer's email
	 * 
	 * @return The email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Email setter method
	 * 
	 * @param email The customer's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the customer's password
	 * 
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Password setter method
	 * 
	 * @param password The customer's password
	 */

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the customer's credit card number
	 * 
	 * @return The credit card number
	 */
	public int getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * Credit card number setter method
	 * 
	 * @param creditCardNumber The customer's credit card number
	 */

	public void setCreditCardNumber(int creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * Get the customer's type
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Type setter method
	 * 
	 * @param type The customer's type
	 */

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get the customer's subscription type
	 * 
	 * @return subscription type
	 */
	public String getSubscriptionType() {
		return SubscriptionType;
	}

	/**
	 * Subscription type setter method
	 * 
	 * @param subscriptionType The customer's subscription type
	 */
	public void setSubscriptionType(String subscriptionType) {
		SubscriptionType = subscriptionType;
	}

}