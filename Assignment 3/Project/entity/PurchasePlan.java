package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The class PurchasePlan represents a generic purchase plan
 * 
 * @author Eylon
 * 
 */
public class PurchasePlan implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int customerID;
	private String planType;
	private ArrayList<ChainOfGas> arrGasStation = new ArrayList<ChainOfGas>();
	private String firstName;
	private String lastName;

	/**
	 * PurchasePlan constructor with all fields as parameters
	 * 
	 * @param customerID    The PurchasePlan customer id
	 * @param planType      The PurchasePlan plan type
	 * @param arrGasStation The PurchasePlan gas stations
	 */
	public PurchasePlan(int customerID, String planType, ArrayList<ChainOfGas> arrGasStation) {
		this.customerID = customerID;
		this.planType = planType;
		this.arrGasStation = arrGasStation;
	}

	/**
	 * PurchasePlan constructor with all fields as parameters
	 * 
	 * @param customerID    The PurchasePlan customer id
	 * @param planType      The PurchasePlan plan type
	 * @param arrGasStation The PurchasePlan gas stations
	 * @param firstName     The PurchasePlan customer first name
	 * @param lastName      The PurchasePlan customer last name
	 */
	public PurchasePlan(int customerID, String planType, ArrayList<ChainOfGas> arrGasStation, String firstName,
			String lastName) {
		this.customerID = customerID;
		this.planType = planType;
		this.arrGasStation = arrGasStation;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Get the purchase plan customer first name
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Customer first name setter method
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the purchase plan customer last name
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Customer last name setter method
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * PurchasePlan constructor with all fields coming from the DB using ResultSet
	 * 
	 * @param resultSet Has all the purchase plan parameters
	 * @throws SQLException
	 */
	public PurchasePlan(ResultSet resultSet) throws SQLException {
		this.customerID = resultSet.getInt(1);
		this.planType = resultSet.getString(2);
		this.arrGasStation.add(new ChainOfGas(resultSet.getString(3)));
		this.arrGasStation.add(new ChainOfGas(resultSet.getString(4)));
		this.arrGasStation.add(new ChainOfGas(resultSet.getString(5)));
		this.firstName = "";
		this.lastName = "";
	}

	/**
	 * Get the purchase plan customer id
	 * 
	 * @return customerID
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * Customer id setter method
	 * 
	 * @param customerID
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * Get the purchase plan plan type
	 * 
	 * @return planType
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * Get the purchase plan gas station 1
	 * 
	 * @return name of gas station 1
	 */
	public String getGasCompany1() {
		return arrGasStation.get(0).getName();
	}

	/**
	 * GasCompany1 setter method
	 * 
	 * @param GasCompany1
	 */
	public void setGasCompany1(String GasCompany1) {
		arrGasStation.set(0, new ChainOfGas(GasCompany1));
	}

	/**
	 * Get the purchase plan gas station 2
	 * 
	 * @return name of gas station 2
	 */
	public String getGasCompany2() {
		return arrGasStation.get(1).getName();
	}

	/**
	 * GasCompany2 setter method
	 * 
	 * @param GasCompany2
	 */
	public void setGasCompany2(String GasCompany2) {
		arrGasStation.set(1, new ChainOfGas(GasCompany2));
	}

	/**
	 * Get the purchase plan gas station 3
	 * 
	 * @return name of gas station 3
	 */
	public String getGasCompany3() {
		return arrGasStation.get(2).getName();
	}

	/**
	 * 
	 * GasCompany3 setter method
	 * 
	 * @param GasCompany3
	 */

	public void setGasCompany3(String GasCompany3) {
		arrGasStation.set(2, new ChainOfGas(GasCompany3));
	}

	/**
	 * PlanType setter method
	 * 
	 * @param planType
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * Get all gas station
	 * 
	 * @return arrGasStation
	 */
	public ArrayList<ChainOfGas> getArrGasStation() {
		return arrGasStation;
	}

	/**
	 * ArrGasStation setter method
	 * 
	 * @param arrGasStation
	 */
	public void setArrGasStation(ArrayList<ChainOfGas> arrGasStation) {
		this.arrGasStation = arrGasStation;
	}

}
