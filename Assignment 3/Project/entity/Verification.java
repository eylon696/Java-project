package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class Verification represents a generic verification
 * 
 * @author Shoval David
 *
 */

public class Verification implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int RequestID;
	private int EmployeeID;
	private String Name;
	private String Role;
	private String OrganizationalAffiliation;
	private String Station;
	private String Status;
	private String Description;

	/**
	 * Verification constructor with all fields as parameters
	 * 
	 * @param RequestID                 - The request ID (Key)
	 * @param EmployeeID                - The employee ID
	 * @param Name                      - The first name and last name of the
	 *                                  employee which send the request
	 * @param Role                      - The role of the employee
	 * @param OrganizationalAffiliation - The chain which the employee is working
	 * @param Station                   - The station the employee is working
	 * @param Status                    - The status of the verification
	 * @param Description               - The description of the request ( what the
	 *                                  employee demands)
	 */
	public Verification(int RequestID, int EmployeeID, String Name, String Role, String OrganizationalAffiliation,
			String Station, String Status, String Description) {
		this.RequestID = RequestID;
		this.EmployeeID = EmployeeID;
		this.Name = Name;
		this.Role = Role;
		this.OrganizationalAffiliation = OrganizationalAffiliation;
		this.Station = Station;
		this.Status = Status;
		this.Description = Description;
	}

	/**
	 * Verification constructor with all fields coming from the DB using ResultSet
	 * 
	 * @param resultSet - Has all the employee's parameters
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public Verification(ResultSet resultSet) throws NumberFormatException, SQLException {
		this.RequestID = Integer.parseInt(resultSet.getString(1));
		this.EmployeeID = Integer.parseInt(resultSet.getString(2));
		this.Name = resultSet.getString(3);
		this.Role = resultSet.getString(4);
		this.OrganizationalAffiliation = resultSet.getString(5);
		this.Station = resultSet.getString(6);
		this.Status = resultSet.getString(7);
		this.Description = resultSet.getString(8);
	}

	/**
	 * Get the request ID
	 * 
	 * @return The request ID
	 */
	public int getRequestID() {
		return RequestID;
	}

	/**
	 * Set the request ID
	 * 
	 * @param requestID - The new value of request ID
	 */
	public void setRequestID(int requestID) {
		RequestID = requestID;
	}

	/**
	 * Get the employee ID
	 * 
	 * @return The employee ID
	 */
	public int getEmployeeID() {
		return EmployeeID;
	}

	/**
	 * Set the employee ID
	 * 
	 * @param employeeID - The new value of employee ID
	 */
	public void setEmployeeID(int employeeID) {
		EmployeeID = employeeID;
	}

	/**
	 * Get the employee name
	 * 
	 * @return The employee name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Set the employee name
	 * 
	 * @param name - The new name of the employee
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * Get the employee role
	 * 
	 * @return The employee role
	 */
	public String getRole() {
		return Role;
	}

	/**
	 * Set the employee role
	 * 
	 * @param role - The new employee role
	 */
	public void setRole(String role) {
		Role = role;
	}

	/**
	 * Get the employee organizational affiliation
	 * 
	 * @return The employee organizational affiliation
	 */
	public String getOrganizationalAffiliation() {
		return OrganizationalAffiliation;
	}

	/**
	 * Set the employee organizational affiliation
	 * 
	 * @param OrganizationalAffiliation - The new organizational affiliation
	 */
	public void setOrganizationalAffiliation(String OrganizationalAffiliation) {
		this.OrganizationalAffiliation = OrganizationalAffiliation;
	}

	/**
	 * Get the status of the request
	 * 
	 * @return The status of the request
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * Set new status
	 * 
	 * @param status - The new status
	 */
	public void setStatus(String status) {
		Status = status;
	}

	/**
	 * Get the description of the request
	 * 
	 * @return The request description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * Set new description
	 * 
	 * @param description - The new description
	 */
	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * Get the station of the employee
	 * 
	 * @return get the station name
	 */
	public String getStation() {
		return Station;
	}

	/**
	 * Set new station
	 * 
	 * @param station - The new station
	 */
	public void setStation(String station) {
		Station = station;
	}

}
