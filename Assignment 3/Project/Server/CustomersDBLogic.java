package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import entity.Customer;
import entity.CustomerForTable;
import entity.PurchasePlan;

/**
 * This class defined the customer logic
 * 
 * @author Shoval David
 *
 */
public class CustomersDBLogic {

	/**	
	 * This method gives the number of rows of customer table
	 * 
	 * @return the number of rows
	 */
	public static int numOfRows() {
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
	 * This method gives the number of rows at vehicle table
	 * 
	 * @return number of rows
	 */
	public static int numOfRowsVehicle() {
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

	/**
	 * This method gets all the customers inside the DB
	 * 
	 * @return array of type customer
	 */
	public static Customer[] showCustomers() {
		Customer[] arrCustomer = new Customer[numOfRows()];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer;");
			while (rs.next()) {
				arrCustomer[i] = (new Customer(rs));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrCustomer;
	}

	/**
	 * This method update customer details inside the DB
	 */
	public static void updateCustomerDetails(CustomerForTable msg) {
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(
					"UPDATE customer SET FirstName = ? , LastName = ?, Email = ?, CreditCardNumber = ?, Type = ?, SubscriptionType = ? WHERE ID = ?");
			preparedStmt.setString(1, msg.getFirstName().toLowerCase());
			preparedStmt.setString(2, msg.getLastName().toLowerCase());
			preparedStmt.setString(3, msg.getEmail().toLowerCase());
			preparedStmt.setInt(4, msg.getCreditCardNumber());
			preparedStmt.setString(5, msg.getType().toLowerCase());
			preparedStmt.setString(6, msg.getSubscriptionType().toLowerCase());
			preparedStmt.setInt(7, msg.getID());
			preparedStmt.executeUpdate();

			preparedStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method removes customer from DB by his ID
	 */
	public static void removeCustomerByID(String message) {
		int ID;
		String[] messageArray = message.split(" ");
		ID = Integer.valueOf(messageArray[1]);
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement("DELETE FROM customer WHERE ID = ?");
			preparedStmt.setInt(1, ID);
			preparedStmt.executeUpdate(); // The number of lines effected.
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		removeVehiclesForCustomer(ID);
	}

	/**
	 * This method removes vehicles which owned by customer inside the DB
	 */
	public static void removeVehiclesForCustomer(int id) {
		try {
			PreparedStatement preparedStmt = EchoServer.con
					.prepareStatement("DELETE FROM vehicle WHERE CustomerID = ?");
			preparedStmt.setInt(1, id);
			preparedStmt.executeUpdate(); // The number of lines effected.
			preparedStmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * This method gets the number of vehicles of particular customer
	 * 
	 * @return number of vehicles
	 */
	public static int getNumOfVehiclesForCustomer(String msg) {
		int ID;
		Statement stmt;
		ResultSet res;
		String[] messageArray = msg.split(" ");
		ID = Integer.valueOf(messageArray[1]);
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT CustomerID FROM vehicle ;");
			while (res.next()) {
				if (ID == res.getInt(1))
					count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method check how much purchase plans we got inside the DB
	 * 
	 * @return number of purchase plans
	 */
	public static int numOfPurchasePlans() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM purchaseplan;");
			while (res.next()) {
				count++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method gets all the purchase plan and insert it to an array
	 * 
	 * @return array of purchase plan entity
	 */
	public static PurchasePlan[] showPurchasePlansInDB() {
		PurchasePlan[] arrPurchasePlan = new PurchasePlan[numOfPurchasePlans()];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM purchaseplan;");
			while (rs.next()) {
				{
					arrPurchasePlan[i] = (new PurchasePlan(rs));
					i++;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Customer[] arrCustomers = showCustomers();
		for (i = 0; i < arrCustomers.length; i++) {
			for (int j = 0; j < arrPurchasePlan.length; j++) {
				if (arrCustomers[i].getID() == arrPurchasePlan[j].getCustomerID()) {
					arrPurchasePlan[j].setFirstName(arrCustomers[i].getFirstName());
					arrPurchasePlan[j].setLastName(arrCustomers[i].getLastName());
				}
			}
		}
		return arrPurchasePlan;
	}
}
