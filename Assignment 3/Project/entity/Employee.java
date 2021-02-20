package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class Employee represents a generic employee
 * 
 * @author Eylon
 *
 */
public class Employee implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private int ID;
	private String password;	
	private String email;
	private String role;
	private String organizationalAffiliation;
	private String gasStation;

	/**	
	 * Employee constructor with all fields as parameters
	 * 
	 * @param FirstName                 The employee's first name
	 * @param LastName                  The employee's last name
	 * @param ID                        The employee's ID
	 * @param Password                  The employee's password
	 * @param Email                     The employee's email
	 * @param Role                      The employee's role
	 * @param OrganizationalAffiliation The employee's organizational affiliation
	 * @param gasStation                The employee's gas station
	 */
	public Employee(String FirstName, String LastName, int ID, String Password, String Email, String Role,
			String OrganizationalAffiliation, String gasStation) {
		this.firstName = FirstName;
		this.lastName = LastName;
		this.ID = ID;
		this.password = Password;
		this.email = Email;
		this.role = Role;
		this.organizationalAffiliation = OrganizationalAffiliation;
		this.gasStation = gasStation;
	}

	/**
	 * Employee constructor with all fields coming from the DB using ResultSet
	 * 
	 * @param resultSet Has all the employee's parameters
	 * @throws SQLException	
	 */
	public Employee(ResultSet resultSet) throws SQLException {
		this.firstName = resultSet.getString(1);
		this.lastName = resultSet.getString(2);
		this.ID = Integer.parseInt(resultSet.getString(3));
		this.password = resultSet.getString(4);
		this.email = resultSet.getString(5);
		this.role = resultSet.getString(6);
		this.organizationalAffiliation = resultSet.getString(7);
		this.gasStation = resultSet.getString(8);
	}

	/**
	 * First name setter method
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Last name setter method
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * ID setter method
	 * 
	 * @param ID
	 */
	public void setEmployeeID(int ID) {
		this.ID = ID;
	}

	/**
	 * Email setter method
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Role setter method
	 * 
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Password setter method
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Organizational Affiliation setter method
	 * 
	 * @param organizationalAffiliation
	 */
	public void setOrganizationalAffiliation(String organizationalAffiliation) {
		this.organizationalAffiliation = organizationalAffiliation;
	}

	/**
	 * Get the employee's first name
	 * 
	 * @return The first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Get the employee's last name
	 * 
	 * @return The last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Get the employee's gas station
	 * 
	 * @return The gas station
	 */
	public String getGasStation() {
		return gasStation;
	}

	/**
	 * Gas station setter method
	 * 
	 * @param gasStation
	 */
	public void setGasStation(String gasStation) {
		this.gasStation = gasStation;
	}

	/**
	 * Get the employee's ID
	 * 
	 * @return The ID
	 */
	public Integer getEmployeeID() {
		return ID;
	}

	/**
	 * Get the employee's email
	 * 
	 * @return The email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Get the employee's role
	 * 
	 * @return The role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Get the employee's organizational affiliation
	 * 
	 * @return The organizational affiliation
	 */
	public String getOrganizationalAffiliation() {
		return organizationalAffiliation;
	}

	/**
	 * Get the employee's password
	 * 
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}

}