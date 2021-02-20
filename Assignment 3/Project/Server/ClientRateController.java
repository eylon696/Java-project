package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import entity.GasStation;
import entity.Rates;
import entity.Verification;

/**
 * This class taking data from the DB and the functions inside connected to Rate
 * and Verification controllers
 * 
 * @author Shoval David
 *	
 */
public class ClientRateController {

//***********************************************************//	
//*******************Rates functions Start*******************//
//***********************************************************//	
	/**
	 * Send a new request to the DB of changing the rate of particular fuel type at
	 * all the stations at the DB
	 * 
	 * @param employeeID                - The employee ID
	 * @param employeeName              - The employee name
	 * @param employeeRole              - The employee Role
	 * @param OrganizationalAffiliation - The employee chain
	 * @param fuelType                  - The fuel type for the request
	 * @param newRate                   - The new rate we want to change the fuel
	 *                                  type
	 * @return
	 */
	public static boolean sendRequest(int employeeID, String employeeName, String employeeRole,
			String OrganizationalAffiliation, String fuelType, String newRate) {
		int newRequestID;
		ArrayList<String> chainArrayList;
		String status = "wait for approval";
		String description = "update " + fuelType + " fuel rate to " + newRate;
		if (checkIfVerificationExists(OrganizationalAffiliation, fuelType))
			return false;
		chainArrayList = getChainNames();
		for (String name : chainArrayList) {
			newRequestID = getLastRequestID();
			Verification verification = new Verification(newRequestID, employeeID, employeeName, employeeRole, name,
					"all"/* station */ , status, description);
			insertNewVerificationToTable(verification);
		}
		return true;	
	}

	/**
	 * Get all the gas station names from the specific chain
	 * 
	 * @param chainName - The chain name
	 * @return hash map of all the gas station
	 */
	public static HashMap<String, Integer> getStationNames(String chainName) {
		HashMap<String, Integer> gasStationsHash = new HashMap<String, Integer>();
		try {
			GasStation gasStation;
			int i = 1;
			String query = "SELECT * FROM gasstations WHERE ChainName = '" + chainName + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				gasStation = new GasStation(resultSet);
				if (!gasStationsHash.containsKey(gasStation.getStationName()))
					gasStationsHash.put(gasStation.getStationName(), i++);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gasStationsHash;
	}

	/**
	 * Get all the chain names from the specific chain
	 * 
	 * @return array list of all the chain names
	 */
	public static ArrayList<String> getChainNames() {
		ArrayList<String> chainArrayList = new ArrayList<String>();
		try {

			String chainName;
			String query = "SELECT * FROM chain";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				chainName = resultSet.getString(1);
				chainArrayList.add(chainName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chainArrayList;
	}

	/**
	 * Check if the verification have the pattern of "update " + fuelType , meaning
	 * we got same fuel and same chain that
	 * 
	 * @param employeeChain - The employee chain
	 * @param fuelType      - The fuel type
	 * @return true if the verification exist , otherwise false
	 */
	public static boolean checkIfVerificationExists(String employeeChain, String fuelType) {
		try {
			String str = "update " + fuelType;
			String status = "wait for approval";
			String query = "SELECT * FROM verification WHERE Description LIKE '%" + str + "%'" + " AND Status = '"
					+ status + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Get the last request ID from the DB
	 * 
	 * @return The last request ID
	 */
	public static int getLastRequestID() {
		Verification verification = null;
		try {
			String query = "SELECT * FROM verification";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
				verification = new Verification(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (verification == null) // There is nothing inside the table , initialize with number one.
			return 0;
		return verification.getRequestID();
	}

	/**
	 * Get the rate of the particular chain and fuel type
	 * 
	 * @param chainName - The chain name
	 * @param fuelType  - The fuel type name
	 * @return the rate of the particular chain name and fuel type
	 */
	public static Rates getRate(String chainName, String fuelType) {
		Rates rate = null;
		try {
			String query = "SELECT * FROM rates WHERE FuelType = '" + fuelType + "'" + " AND ChainName = '" + chainName
					+ "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				rate = new Rates(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rate;
	}

	/**
	 * Update the old rate to the new rate
	 * 
	 * @param chainName - The chain name
	 * @param fuelType  - The fuel type
	 * @param newRate   - The new rate i want to update
	 * @return true if the updated passed, otherwise false
	 * @throws SQLException
	 */
	public static boolean updateRate(String chainName, String fuelType, String newRate) throws SQLException {
		PreparedStatement preparedStmt = EchoServer.con
				.prepareStatement("UPDATE rates SET rate = ? WHERE FuelType = ? AND ChainName = ?");
		preparedStmt.setDouble(1, Double.parseDouble(newRate));
		preparedStmt.setString(2, fuelType);
		preparedStmt.setString(3, chainName);
		int result = preparedStmt.executeUpdate(); // The number of lines effected.
		if (result == 0) // ID need to be main key so that's why only one line need to be effected.
			return false;
		preparedStmt.close();
		return true;
	}

//***********************************************************//	
//*******************Rates functions end*********************//
//***********************************************************//	

//-----------------------------------------------------------//

//***********************************************************//	
//***************Verification functions start****************//
//***********************************************************//

	/**
	 * Get the number of the rows that connected to the chain manager
	 * 
	 * @param chainName - The name of the chain
	 * @return The number of rows that connected to the chain manager
	 */
	public static int numOfRowsChainManager(String chainName) {
		Statement stmt;
		ResultSet resultSet;
		String role = "marketing manager";
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			resultSet = stmt.executeQuery("SELECT * FROM verification WHERE OrganizationalAffiliation = '" + chainName
					+ "' AND Role = '" + role + "'");
			while (resultSet.next())
				count++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * Get an array of values that going be shown at the table verification of chain
	 * manager role
	 * 
	 * @param chainName - The name of the chain
	 * @return Array of type verification
	 */
	public static Verification[] showChainManagerVerification(String chainName) {
		Verification[] arrVerification = new Verification[numOfRowsChainManager(chainName)];
		Statement stmt;
		String role = "marketing manager";
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM verification WHERE OrganizationalAffiliation = '"
					+ chainName + "' AND Role = '" + role + "'");
			while (resultSet.next())
				arrVerification[i++] = new Verification(resultSet);
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrVerification;
	}

	/**
	 * Insert new verification to the table
	 * 
	 * @param verification - The new verification
	 */
	public static void insertNewVerificationToTable(Verification verification) {
		try {
			String query = "INSERT INTO verification (RequestID , EmployeeID , Name , Role , OrganizationalAffiliation , Station , Status , Description)"
					+ "VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			preparedStmt.setInt(1, verification.getRequestID() + 1);
			preparedStmt.setInt(2, verification.getEmployeeID());
			preparedStmt.setString(3, verification.getName());
			preparedStmt.setString(4, verification.getRole());
			preparedStmt.setString(5, verification.getOrganizationalAffiliation());
			preparedStmt.setString(6, verification.getStation());
			preparedStmt.setString(7, verification.getStatus());
			preparedStmt.setString(8, verification.getDescription());

			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the number of the rows of station manager
	 * 
	 * @param chainName - The name of the chain
	 * @return The number of rows that connected to the station manager
	 */
	public static int numOfRows(String chainName) {
		Statement stmt;
		ResultSet resultSet;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			resultSet = stmt
					.executeQuery("SELECT * FROM verification WHERE OrganizationalAffiliation = '" + chainName + "'");
			while (resultSet.next())
				count++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * Get a verification with the request ID given
	 * 
	 * @param requestID - The request id
	 * @return verification entity
	 */
	public static Verification getVerification(String requestID) {
		Verification verification = null;
		try {
			String query = "SELECT * FROM verification WHERE RequestID = '" + requestID + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				verification = new Verification(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return verification;
	}

	/**
	 * Get the number of the rows for supplier which contains at status "wait for
	 * approval"
	 * 
	 * @param status - The status of the verification
	 * @param role   - The role of the employee
	 * @return The number of rows that connected to the supplier
	 */
	public static int SupplierNumOfWaitForApprovalRequests(String status, String role) {
		Statement stmt;
		ResultSet resultSet;
		int result = 0;
		try {
			stmt = EchoServer.con.createStatement();
			resultSet = stmt.executeQuery(
					"SELECT * FROM verification WHERE Status = '" + status + "' AND Role = '" + role + "'");
			while (resultSet.next())
				result++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Get the number of the rows for station manager which contains at status "wait
	 * for approval"
	 * 
	 * @param status     - The status of the verification
	 * @param role       - The role of the employee
	 * @param chain      - The chain of the employee
	 * @param gasStation - The gasStation of the employee
	 * @return The number of rows that connected to the station manager
	 */
	public static int StationManagergetNumOfWaitForApprovalRequests(String status, String role, String chain,
			String gasStation) {
		Statement stmt;
		ResultSet resultSet;
		int result = 0;
		try {
			stmt = EchoServer.con.createStatement();
			resultSet = stmt.executeQuery("SELECT * FROM verification WHERE Status = '" + status + "' AND Role = '"
					+ role + "' AND OrganizationalAffiliation = '" + chain + "' AND Station = '" + gasStation + "'");
			while (resultSet.next())
				result++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Get the number of the rows for marketing manager which contains at status
	 * "approved"
	 * 
	 * @param status - The status of the verification
	 * @param role   - The role of the employee
	 * @return The number of rows that connected to the marketing manager
	 */
	public static int MarketingManagergetNumOfApprovedRequests(String status, String role) {
		Statement stmt;
		ResultSet resultSet;
		int result = 0;
		try {
			stmt = EchoServer.con.createStatement();
			resultSet = stmt.executeQuery(
					"SELECT * FROM verification WHERE Status = '" + status + "' AND Role = '" + role + "'");
			while (resultSet.next())
				result++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Get the number of the rows for chain manager which contains at status "wait
	 * for approval"
	 * 
	 * @param chain  - The chain of the employee
	 * @param status - The status of the verification
	 * @param role   - The role of the employee
	 * @return The number of rows that connected to the chain manager
	 */
	public static int ChainManagerGetNumOfWaitForApprovalRequests(String chain, String status, String role) {
		Statement stmt;
		ResultSet resultSet;
		int result = 0;
		try {
			stmt = EchoServer.con.createStatement();
			resultSet = stmt.executeQuery("SELECT * FROM verification WHERE Status = '" + status
					+ "' AND OrganizationalAffiliation = '" + chain + "' AND Role = '" + role + "'");
			while (resultSet.next())
				result++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Get an array of verification that going be shown at the table verification of
	 * marketing manager role
	 * 
	 * @return Array of type verification
	 */
	public static Verification[] showMarketingManagerVerifications() {
		int i = 0;
		String role = "marketing manager";
		ArrayList<Verification> listVerification = new ArrayList<Verification>();
		Verification[] arrVerification = null;
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM verification WHERE Role = '" + role + "'");
			while (resultSet.next())
				listVerification.add(new Verification(resultSet));
			resultSet.close();
			arrVerification = new Verification[listVerification.size()];
			for (Verification verification : listVerification)
				arrVerification[i++] = verification;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrVerification;
	}

	/**
	 * Get an array of verification that going be shown at the table verification of
	 * supplier role
	 * 
	 * @param employeeID - The employee ID
	 * @return Array of type verification
	 */
	public static Verification[] showSupplierVerifications(String employeeID) {
		int i = 0;
		ArrayList<Verification> listVerification = new ArrayList<Verification>();
		Verification[] arrVerification = null;
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet resultSet = stmt
					.executeQuery("SELECT * FROM verification WHERE EmployeeID = '" + employeeID + "'");
			while (resultSet.next())
				listVerification.add(new Verification(resultSet));
			resultSet.close();
			arrVerification = new Verification[listVerification.size()];
			for (Verification verification : listVerification)
				arrVerification[i++] = verification;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrVerification;
	}

	/**
	 * Get an array of verification that going be shown at the table verification of
	 * station manager role
	 * 
	 * @param chainName - The employee chain name
	 * @return Array of type verification
	 */
	public static Verification[] showVerifications(String chainName) {
		Verification[] arrVerification = new Verification[numOfRows(chainName)];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet resultSet = stmt
					.executeQuery("SELECT * FROM verification WHERE OrganizationalAffiliation = '" + chainName + "'");
			while (resultSet.next())
				arrVerification[i++] = new Verification(resultSet);
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrVerification;
	}

	/**
	 * Check if verification exist already inside the DB
	 * 
	 * @param requestID - The request ID of the verification
	 * @param chainName - The chain name
	 * @return true if the verification exist otherwise false
	 */
	public static boolean verificationExist(String requestID, String chainName) {
		try {
			String query = "SELECT * FROM verification WHERE RequestID = '" + requestID
					+ "' AND OrganizationalAffiliation = '" + chainName + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Check if verification exist already inside the DB with role of marketing
	 * manager
	 * 
	 * @param requestID - The request ID of the verification
	 * @return true if the verification exist otherwise false
	 */
	public static boolean verificationExistMarketingManager(String requestID) {
		try {
			String query = "SELECT * FROM verification WHERE RequestID = '" + requestID + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update the supplier verification and set the status to supplied and set the
	 * gas station table to the new stock.
	 * 
	 * @param requestID       - The request ID of the verification
	 * @param chainName       - The chain name of the employee
	 * @param stationName     - The station name of the employee
	 * @param fuelType        - The fuel type of the verification
	 * @param quantityRequire - The quantity require we need to supply to the
	 *                        station
	 * @return true if the the we update the stock otherwise false
	 */
	public static boolean updateSupplierrVerification(String requestID, String chainName, String stationName,
			String fuelType, String quantityRequire) {
		try {
			String query = "UPDATE verification SET Status = ? WHERE RequestID = ? AND Status = ?";
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			preparedStmt.setString(1, "supplied");
			preparedStmt.setInt(2, Integer.parseInt(requestID));
			preparedStmt.setString(3, "approved");
			int result = preparedStmt.executeUpdate(); // The number of lines effected.
			if (result == 0) // ID need to be main key so that's why only one line need to be effected.
				return false;
			preparedStmt.close();
			updateStationStock(chainName, stationName, fuelType, quantityRequire);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// Update the station stock
	/**
	 * Update the station stock.
	 * 
	 * @param chainName       - The chain name of the employee
	 * @param stationName     - The station name of the employee
	 * @param fuelType        - The fuel type of the verification
	 * @param quantityRequire - The quantity require we need to supply to the
	 *                        station
	 * @return true if the the we update the stock otherwise false
	 */
	public static boolean updateStationStock(String chainName, String stationName, String fuelType,
			String quantityRequire) throws SQLException {
		GasStation gasStation = getGasStation(chainName, stationName, fuelType);
		PreparedStatement preparedStmt = EchoServer.con.prepareStatement(
				"UPDATE gasstations SET StockQuantity = ? WHERE ChainName = ? AND StationName = ? AND FuelType = ?");
		preparedStmt.setDouble(1, Double.parseDouble(quantityRequire) + gasStation.getStockQuantity());
		preparedStmt.setString(2, chainName);
		preparedStmt.setString(3, stationName);
		preparedStmt.setString(4, fuelType);
		int result = preparedStmt.executeUpdate(); // The number of lines effected.
		if (result == 0) // ID need to be main key so that's why only one line need to be effected.
			return false;
		preparedStmt.close();
		return true;
	}

	/**
	 * Update the verification from the side of station manager
	 * 
	 * @param requestID - The request ID of the verification
	 * @param chainName - The chain name of the employee
	 * @param fuelType  - The fuel type of the verification
	 * @param newRate   - The new rate we want to update
	 * @return true if the the we update the stock otherwise false
	 */
	public static boolean updateStationManagerVerification(String requestID, String chainName, String fuelType,
			String newRate) {
		try {
			String query = "UPDATE verification SET Status = ? WHERE RequestID = ? AND Status = ?";
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			preparedStmt.setString(1, "updated");
			preparedStmt.setInt(2, Integer.parseInt(requestID));
			preparedStmt.setString(3, "approved");
			int result = preparedStmt.executeUpdate(); // The number of lines effected.
			if (result == 0) // ID need to be main key so that's why only one line need to be effected.
				return false;
			preparedStmt.close();
			updateRate(chainName, fuelType, newRate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Change the status of the verification to approved
	 * 
	 * @param requestID - The request ID
	 * @return true if the status has been changed , otherwise false
	 */
	public static boolean approveVerification(String requestID) {
		try {
			String query = "UPDATE verification SET Status = ? WHERE RequestID = ? AND Status = ?";
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			preparedStmt.setString(1, "approved");
			preparedStmt.setDouble(2, Integer.parseInt(requestID));
			preparedStmt.setString(3, "wait for approval");
			int result = preparedStmt.executeUpdate(); // The number of lines effected.
			if (result == 0) // ID need to be main key so that's why only one line need to be effected.
				return false;
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Change the status of the verification to disapprove
	 * 
	 * @param requestID - The request ID
	 * @return true if the status has been changed , otherwise false
	 */
	public static boolean disapproveVerification(String requestID) {
		try {
			String query = "UPDATE verification SET Status = ? WHERE RequestID = ? AND Status = ?";
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			preparedStmt.setString(1, "disapproved");
			preparedStmt.setDouble(2, Integer.parseInt(requestID));
			preparedStmt.setString(3, "wait for approval");
			int result = preparedStmt.executeUpdate();
			if (result == 0) // ID need to be main key so that's why only one line need to be effected.
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// Remove the verification from the table.
	/**
	 * Remove the verification from the table
	 * 
	 * @param requestID - The request ID
	 * @return true if we removed the verification , otherwise false
	 */
	public static boolean removeVerification(String requestID) {
		try {
			String query = "DELETE FROM verification WHERE RequestID = '" + requestID + "'";
			Statement statement = EchoServer.con.createStatement();
			int isUpdate = statement.executeUpdate(query);
			if (isUpdate == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// ***********************************************************//
	// ***************Verification functions end******************//
	// ***********************************************************//

	/**
	 * Get gas station
	 * 
	 * @param chainName   - The chain name of the gas station
	 * @param stationName - The station name
	 * @param fuelType    - The fuel type
	 * @return gas station entity
	 */
	public static GasStation getGasStation(String chainName, String stationName, String fuelType) {
		GasStation gasStation = null;
		try {
			String query = "SELECT * FROM gasstations WHERE ChainName = '" + chainName + "'" + " AND StationName = '"
					+ stationName + "'" + " AND FuelType = '" + fuelType + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				gasStation = new GasStation(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gasStation;
	}
}
