package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import entity.ChainOfGas;
import entity.Customer;
import entity.Employee;
import entity.GasStation;
import entity.PurchasePlan;
import entity.Vehicle;
import entity.Verification;

/**
 * This class manages the connections to the DB that are relevant to the users
 * 
 * @author Eylon
 *
 */
public class UserControllerServer {
	/**
	 * This method removes a user who logged out
	 * 
	 * @param userID the logged in user id
	 * @return True
	 */
	public static boolean removeCustomerEmployeeLoginArrayList(String userID) {
		int index = EchoServer.customerEmployeeLoginArrayList.indexOf(Integer.parseInt(userID));
		EchoServer.customerEmployeeLoginArrayList.remove(index);
		return true;
	}

	/**
	 * This method adds a user who logged in
	 * 
	 * @param userID the logged in user id
	 * @return True/False
	 */
	public static boolean customerEmployeeLoginArrayList(String userID) {
		if (EchoServer.customerEmployeeLoginArrayList.contains(Integer.parseInt(userID)))
			return true;
		EchoServer.customerEmployeeLoginArrayList.add(Integer.parseInt(userID));
		return false;
	}

	/**
	 * This method check how many rows are in the customer table
	 * 
	 * @return number of rows
	 */
	public static int numOfRowsCustomer() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM customer;");
			while (res.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method returns an array of customers to the client
	 * 
	 * @return array of customers
	 */
	public static Customer[] showCustomers() {
		int num = numOfRowsCustomer();
		Customer[] arrCustomers = new Customer[num];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer;");
			while (rs.next()) {
				arrCustomers[i] = (new Customer(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrCustomers;
	}

	/**
	 * This method inserts Vehicles to the Vehicle table in the DB
	 * 
	 * @param msg array list of Vehicles
	 */

	@SuppressWarnings("unchecked")
	public static void addCarsToCustomers(Object msg) {
		String query = "INSERT INTO vehicle (CustomerID,LicenseNumber,Vtype,FuelType)" + "VALUES(?,?,?,?)";
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			for (Vehicle i : (ArrayList<Vehicle>) msg) {
				preparedStmt.setInt(1, i.getCustomerId());
				preparedStmt.setInt(2, i.getLicensePlateNumber());
				preparedStmt.setString(3, i.getVType());
				preparedStmt.setString(4, i.getFuelType());

				preparedStmt.execute();
			}
			preparedStmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * This method checks how many Vehicles a customer has in the DB
	 * 
	 * @param msg the customer's id
	 * @return number of rows
	 */
	public static int numOfRows(String msg) {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM vehicle;");
			while (res.next()) {
				if (res.getInt(1) == Integer.valueOf(msg))
					count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method returns the Vehicles of the specific customer
	 * 
	 * @param msg the customer's id
	 * @return array of Vehicles
	 */
	public static Vehicle[] getDetailsOfVehiclesForCustomerId(Object msg) {
		msg = (String) msg;
		String[] arrOfStr = msg.toString().split(" ");
		Vehicle[] arrVehiclesForCustomerId = new Vehicle[numOfRows(arrOfStr[1])];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle;");
			while (rs.next()) {
				if (rs.getInt(1) == Integer.valueOf(arrOfStr[1])) {
					arrVehiclesForCustomerId[i] = (new Vehicle(rs.getInt(1), rs.getInt(2), rs.getString(3),
							rs.getString(4), false, false));
					i++;

				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrVehiclesForCustomerId;
	}

	/**
	 * This method check how many chains are in the DB
	 * 
	 * @return number of chains in the DB
	 */
	public static int numOfRowsGasStation() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM chain;");
			while (res.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method return all the gas chains in the DB
	 * 
	 * @return array of chains
	 */
	public static ChainOfGas[] getAllGasCompanies() {
		ChainOfGas[] arrGasStation = new ChainOfGas[numOfRowsGasStation()];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM chain;");
			while (rs.next()) {
				arrGasStation[i] = (new ChainOfGas(rs.getString(1)));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrGasStation;
	}

	/**
	 * This method updates purchase plan to a customer in the DB
	 * 
	 * @param msg the new purchase plan to update
	 */
	@SuppressWarnings("resource")
	public static void addPurchasePlanToCustomers(Object msg) {
		PurchasePlan purchasePlanToUpdate = (PurchasePlan) msg;
		try {
			PreparedStatement preparedStmt = EchoServer.con
					.prepareStatement("UPDATE purchaseplan SET PlanType = ? WHERE CustomerID = ?");
			preparedStmt.setString(1, purchasePlanToUpdate.getPlanType().toLowerCase());
			preparedStmt.setInt(2, purchasePlanToUpdate.getCustomerID());
			preparedStmt.executeUpdate();
			preparedStmt.close();
			if (purchasePlanToUpdate.getArrGasStation().size() == 0) {
				preparedStmt = EchoServer.con.prepareStatement(
						"UPDATE purchaseplan SET GasCompany1 = ? , GasCompany2 = ? , GasCompany3 = ? WHERE CustomerID = ?");
				preparedStmt.setNull(1, Types.NULL);
				preparedStmt.setNull(2, Types.NULL);
				preparedStmt.setNull(3, Types.NULL);
				preparedStmt.setInt(4, purchasePlanToUpdate.getCustomerID());
				preparedStmt.executeUpdate();
				preparedStmt.close();
			} else if (purchasePlanToUpdate.getArrGasStation().size() == 1) {
				preparedStmt = EchoServer.con.prepareStatement(
						"UPDATE purchaseplan SET GasCompany1 = ? , GasCompany2 = ? , GasCompany3 = ? WHERE CustomerID = ?");
				preparedStmt.setString(1, purchasePlanToUpdate.getArrGasStation().get(0).getName());
				preparedStmt.setNull(2, Types.NULL);
				preparedStmt.setNull(3, Types.NULL);
				preparedStmt.setInt(4, purchasePlanToUpdate.getCustomerID());
				preparedStmt.executeUpdate();
				preparedStmt.close();

			} else if (purchasePlanToUpdate.getArrGasStation().size() == 2) {
				preparedStmt = EchoServer.con.prepareStatement(
						"UPDATE purchaseplan SET GasCompany1 = ? , GasCompany2 = ? , GasCompany3 = ? WHERE CustomerID = ?");
				preparedStmt.setString(1, purchasePlanToUpdate.getArrGasStation().get(0).getName());
				preparedStmt.setString(2, purchasePlanToUpdate.getArrGasStation().get(1).getName());
				preparedStmt.setNull(3, Types.NULL);
				preparedStmt.setInt(4, purchasePlanToUpdate.getCustomerID());
				preparedStmt.executeUpdate();
				preparedStmt.close();
			} else {
				preparedStmt = EchoServer.con.prepareStatement(
						"UPDATE purchaseplan SET GasCompany1 = ? , GasCompany2 = ? , GasCompany3 = ? WHERE CustomerID = ?");
				preparedStmt.setString(1, purchasePlanToUpdate.getArrGasStation().get(0).getName());
				preparedStmt.setString(2, purchasePlanToUpdate.getArrGasStation().get(1).getName());
				preparedStmt.setString(3, purchasePlanToUpdate.getArrGasStation().get(2).getName());
				preparedStmt.setInt(4, purchasePlanToUpdate.getCustomerID());
				preparedStmt.executeUpdate();
				preparedStmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method checks if an email already exists in the DB
	 * 
	 * @param msg the email to check
	 * @return True/False
	 */
	public static boolean checkIfEmailExists(String msg) {
		String[] arrOfStr = msg.toString().split(" ");
		String email = arrOfStr[1];
		boolean employeeFlag = checkIfEmployeeEmailExists(email);
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Email FROM customer;");
			while (rs.next()) {
				if (email.equals(rs.getString(1)))
					return true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeeFlag;
	}

	/**
	 * This method checks if an employee email already exists in the DB
	 * 
	 * @param email the employee email
	 * @return True/False
	 */
	public static boolean checkIfEmployeeEmailExists(String email) {
		System.out.println(email);
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Email FROM employee;");
			while (rs.next()) {
				if (email.equals(rs.getString(1)))
					return true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method checks if a credit card already exists in the DB
	 * 
	 * @param msg the credit card to check
	 * @return True/False
	 */
	public static boolean checkIfCreditCardExists(String msg) {
		String[] arrOfStr = msg.toString().split(" ");
		int creditCard = Integer.valueOf(arrOfStr[1]);
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT CreditCardNumber FROM customer;");
			while (rs.next()) {
				if (creditCard == rs.getInt(1))
					return true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method insert new customer to the DB
	 * 
	 * @param customer the customer to add
	 */
	public static void insertNewCustomerToTheDb(Customer customer) {
		String query1 = "INSERT INTO customer (FirstName,LastName,ID,Password,Email,CreditCardNumber,Type,SubscriptionType)"
				+ "VALUES(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query1);

			preparedStmt.setString(1, customer.getFirstName());
			preparedStmt.setString(2, customer.getLastName());
			preparedStmt.setInt(3, customer.getID());
			preparedStmt.setString(4, customer.getPassword());
			preparedStmt.setString(5, customer.getEmail());
			preparedStmt.setInt(6, customer.getCreditCardNumber());
			preparedStmt.setString(7, customer.getType());
			preparedStmt.setString(8, customer.getSubscriptionType());
			preparedStmt.execute();
			preparedStmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String query2 = "INSERT INTO purchaseplan (CustomerID,PlanType,GasCompany1,GasCompany2,GasCompany3)"
				+ "VALUES(?,?,?,?,?)";
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query2);

			preparedStmt.setInt(1, customer.getID());
			preparedStmt.setString(2, "none");
			preparedStmt.setNull(3, Types.NULL);
			preparedStmt.setNull(4, Types.NULL);
			preparedStmt.setNull(5, Types.NULL);
			preparedStmt.execute();
			preparedStmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * This method checks if a customer already exists in the DB
	 * 
	 * @param msg the customer to check
	 * @return True/False
	 */
	public static boolean checkIfCustomerIdExists(Object msg) {
		msg = (String) msg;
		String[] arrOfStr = msg.toString().split(" ");
		int customerID = Integer.valueOf(arrOfStr[1]);
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ID FROM customer;");
			while (rs.next()) {
				if (customerID == rs.getInt(1))
					return true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method checks if a license plate number already exists in the DB
	 * 
	 * @param msg the license plate number to check
	 * @return True/False
	 */
	public static boolean checkIfLicensePlateNumberExists(Object msg) {
		msg = (String) msg;
		String[] arrOfStr = msg.toString().split(" ");
		int customerLicensePlateNumber = Integer.valueOf(arrOfStr[1]);
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT LicenseNumber FROM vehicle;");
			while (rs.next()) {
				if (customerLicensePlateNumber == rs.getInt(1))
					return true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * This method returns the price of home heating fuel
	 * 
	 * @return price
	 */
	public static float priceOfHomeHeatingFuel() {
		Statement stmt;
		float temp = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Pprice FROM homeheatingfuel;");
			if (rs.next())
				temp = rs.getFloat(1);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;

	}

	/**
	 * This method check if a user id exists in the table
	 * 
	 * @param tableName employee table/ customer table
	 * @param ID        the id to check
	 * @return True/False
	 */
	public static boolean LoginQueryUserExist(String tableName, String ID) // For employee table and customer table.
	{
		try {
			String query = "SELECT * FROM " + tableName + " WHERE ID = ?";
			PreparedStatement preparedStatement = EchoServer.con.prepareStatement(query);
			preparedStatement.setString(1, ID); // In the first question mark put IDtxt.
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method check if a customer id and password exists in the table
	 * 
	 * @param Password the password to check
	 * @param ID       the id to check
	 * @return matching customer/null
	 */

	// Check if the password of the user is correct.
	public static Customer UserLoginQueryPasswordCorrect(String ID, String Password) {
		try {
			Customer customer = null;
			String query = "SELECT * FROM customer WHERE ID = '" + ID + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				customer = new Customer(resultSet);
			if (customer != null && customer.getPassword().equals(Password))
				return customer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method check if a employee id and password exists in the table
	 * 
	 * @param Password the password to check
	 * @param ID       the id to check
	 * @return matching employee/null
	 */
	public static Employee EmployeeLoginQueryPasswordCorrect(String ID, String Password) {
		try {
			Employee employee = null;
			String query = "SELECT * FROM employee WHERE ID = '" + ID + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				employee = new Employee(resultSet);
			if (employee != null && employee.getPassword().equals(Password))
				return employee;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method checks if a request for stock update exists
	 * 
	 * @return True/False
	 */
	// Set new verification for the supplier if the stock is low.
	public static boolean EmployeeLoginQueryUpdateStockRequest() {
		ArrayList<GasStation> gasStations = getGasStations();
		ArrayList<Employee> suppliers = getSuppliers(); // If i want to choose specific supplier if we change the data
														// base
		for (GasStation gasStation : gasStations) {
			if (gasStation.getStockQuantity() < gasStation.getThresholdLevel()) // Meaning we need to fill the stock.
			{
				Employee supplier = suppliers.get(0);
				if (supplier != null) {
					Double quantityNeeded = (gasStation.getThresholdLevel() - gasStation.getStockQuantity()) * 10;
					int requestID = ClientRateController.getLastRequestID();
					int employeeID = supplier.getEmployeeID();
					String name = supplier.getFirstName() + " " + supplier.getLastName();
					String role = supplier.getRole();
					String chain = gasStation.getChainName();
					String station = gasStation.getStationName();
					String status = "wait for approval";
					String fuelType = gasStation.getFuelType();
					String discription = "Order " + quantityNeeded + " litre of " + fuelType + " to " + chain + " "
							+ station + " station.";
					Verification verification = new Verification(requestID, employeeID, name, role, chain, station,
							status, discription);
					if (checkIfVerificationForSupplierExist(verification, chain, station))
						ClientRateController.insertNewVerificationToTable(verification);
				}
			}
		}
		return true;
	}

	/**
	 * This method Check if the verification exist , checking it by the description
	 * chain and station
	 * 
	 * @param verification  the wanted verification
	 * @param employeeChain the wanted chain
	 * @param station       the wanted station
	 * @return
	 */
	public static boolean checkIfVerificationForSupplierExist(Verification verification, String employeeChain,
			String station) {
		try {
			String[] descriptionSplit = verification.getDescription().split(" ");
			String status = "wait for approval";
			String fuelType = descriptionSplit[4];
			String str = "litre of " + fuelType;
			String query = "SELECT * FROM verification WHERE Description LIKE '%" + str + "%'"
					+ " AND OrganizationalAffiliation = '" + employeeChain + "'" + " AND Status = '" + status + "'"
					+ " AND Station = '" + station + "'";
			Statement statement;
			statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				return false;
			status = "approved";
			query = "SELECT * FROM verification WHERE Description LIKE '%" + str + "%'"
					+ " AND OrganizationalAffiliation = '" + employeeChain + "'" + " AND Status = '" + status + "'"
					+ " AND Station = '" + station + "'";
			statement = EchoServer.con.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * This method returns all the gas stations
	 * 
	 * @return array list of gas station
	 */
	// Get all the gas stations from the table "gasstations".
	public static ArrayList<GasStation> getGasStations() {
		ArrayList<GasStation> gasStations = new ArrayList<GasStation>();
		try {
			String query = "SELECT * FROM gasstations";
			Statement statement;
			statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) // Get all the rows inside gas station table.
				gasStations.add(new GasStation(resultSet));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gasStations;
	}

	/**
	 * This method Get all the suppliers from the table "employee"
	 * 
	 * @return array list of suppliers
	 */
	public static ArrayList<Employee> getSuppliers() {
		ArrayList<Employee> suppliers = new ArrayList<Employee>();
		try {
			String role = "supplier";
			String query = "SELECT * FROM employee WHERE Role = '" + role + "'";
			Statement statement;
			statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) // Get all the rows inside gas station table.
				suppliers.add(new Employee(resultSet));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return suppliers;
	}

	/*********************************************************************************/
	/********************************
	 * Login Form End
	 ***********************************/
	/*********************************************************************************/

	/*-------------------------------------------------------------------------------------------------------*/

	/*********************************************************************************/
	/************************
	 * Forgot Password Form Start
	 *******************************/
	/*********************************************************************************/
	/**
	 * This method Check if the customer email of the customer is correct.
	 * 
	 * @param ID    the wanted ID
	 * @param Email the wanted email
	 * @return customer/null
	 */
	public static Customer UserLoginQueryEmailCorrect(String ID, String Email) {
		try {
			Customer customer = null;
			String query = "SELECT * FROM customer WHERE ID = '" + ID + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				customer = new Customer(resultSet);
			if (customer != null && customer.getEmail().equals(Email))
				return customer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method Check if the employee email of the customer is correct.
	 * 
	 * @param ID    the wanted ID
	 * @param Email the wanted email
	 * @return employee/null
	 */
	// Check if the email of the employee is correct.
	public static Employee EmployeeLoginQueryEmailCorrect(String ID, String Email) {
		try {
			Employee employee = null;
			String query = "SELECT * FROM employee WHERE ID = '" + ID + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				employee = new Employee(resultSet);
			if (employee != null && employee.getEmail().equals(Email))
				return employee;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*********************************************************************************/
	/************************
	 * Forgot Password Form End
	 *******************************/
	/*********************************************************************************/
	/**
	 * This method check the subscription type for a specific customer
	 * 
	 * @param msg the customer's id
	 * @return subscription type
	 */
	public static String checkSubscriptionTypeForCustomerID(String msg) {
		String arr[] = msg.split(":");
		Statement stmt;
		String subscriptionType = "";
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer;");
			while (rs.next()) {
				if (rs.getInt(3) == Integer.valueOf(arr[1]))
					subscriptionType = rs.getString(8);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subscriptionType;
	}

	/**
	 * This method removes a Vehicle for a specific customer
	 * 
	 * @param msg the id and Vehicle of customer
	 * @return True/False
	 */
	public static boolean deleteVehicle(String msg) {
		int num = numOfRowsVehicles();
		String arr[] = msg.split(":");
		String query = "DELETE FROM vehicle WHERE (CustomerID = ?) AND (LicenseNumber = ?)";
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			preparedStmt.setInt(1, Integer.valueOf(arr[1]));
			preparedStmt.setInt(2, Integer.valueOf(arr[2]));
			preparedStmt.execute();
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (numOfRowsVehicles() != num)
			return true;
		return false;
	}

	/**
	 * This method checks how many Vehicles are in the DB
	 * 
	 * @return number of rows
	 */
	public static int numOfRowsVehicles() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM vehicle;");
			while (res.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
