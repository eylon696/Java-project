package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import entity.OrderHomeHeatingFuel;

/**
 * This class defined the order home heating fuel functionality
 * 
 * @author Shoval David
 *
 */	
public class OrderHomeHeatingFuelControllerServer {

	/**
	 * This method add an order of home heating fuel purchase
	 * 
	 * @param msg - an OrderHomeHeatingFuel object which contains all the data of
	 *            the order
	 */
	public static void addPurchaseHomeHeatingFuelToTheDb(OrderHomeHeatingFuel msg) {
		String query = "INSERT INTO orderhomeheatingfuel (OrderID,CustomerID,Date,Quantity,Address,EstimatedDeliveryTime,DeliveryType,Price,Payment,Delivered )"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);

			preparedStmt.setInt(1, msg.getId());
			preparedStmt.setInt(2, msg.getCustomerId());
			preparedStmt.setString(3, msg.getDate());
			preparedStmt.setDouble(4, msg.getQuantity());
			preparedStmt.setString(5, msg.getAddress());
			preparedStmt.setString(6, msg.getEstimatedDate());
			preparedStmt.setString(7, msg.getDeliveryType());
			preparedStmt.setFloat(8, msg.getPrice());
			preparedStmt.setString(9, msg.getPayment());
			preparedStmt.setString(10, msg.getDelivered());

			preparedStmt.execute();
			preparedStmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * This method gets the next ID inside the purchase home heating fuel inside the
	 * DB
	 * 
	 * @return the next ID
	 */
	public static int findTheNextIDInPurchaseHomeHeatingFuel() {
		Statement stmt;
		int temp = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT OrderID FROM orderhomeheatingfuel;");
			while (rs.next())
				temp = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * This method gets the number of orders of specific customer
	 * 
	 * @param id - The customer ID
	 * @return The number of orders
	 */
	public static int numOfOrdersForSpecificID(int id) {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM orderhomeheatingfuel;");
			while (res.next()) {
				if (res.getInt(2) == id)
					count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method gets all the orders of a customer from the DB and puts it inside
	 * an array
	 * 
	 * @param msg - message with the data
	 * @return array of entity OrderHomeHeatingFuel which contains the orders of
	 *         customer
	 */
	public static OrderHomeHeatingFuel[] getOrdersHomeHeatingFuelForCustomerID(String msg) {
		String[] arrStr = msg.split(" ");
		OrderHomeHeatingFuel[] arrOrders = new OrderHomeHeatingFuel[numOfOrdersForSpecificID(
				Integer.valueOf(arrStr[1]))];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM orderhomeheatingfuel;");
			while (rs.next()) {
				if (rs.getInt(2) == Integer.valueOf(arrStr[1])) {
					arrOrders[i] = (new OrderHomeHeatingFuel(rs));
					i++;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrOrders;
	}

	/**
	 * This method gets the number or rows inside the table order home heating fuel
	 * at the DB
	 * 
	 * @return number of orders
	 */
	public static int numOfOrders() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM orderhomeheatingfuel;");
			while (res.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method insert to array all the home heating fuel purchases from the DB
	 * 
	 * @return array of OrderHomeHeatingFuel entity
	 */
	public static OrderHomeHeatingFuel[] showHomeHeatingPurchasesInDB() {
		OrderHomeHeatingFuel[] arrOrders = new OrderHomeHeatingFuel[numOfOrders()];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM orderhomeheatingfuel;");
			while (rs.next()) {
				{
					arrOrders[i] = (new OrderHomeHeatingFuel(rs));
					i++;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrOrders;
	}

	/**
	 * This method update the orders delivered to "yes" meaning the order is
	 * delivered
	 */
	public static void updateOrderDeliveredToYes() {
		OrderHomeHeatingFuel[] arrOrders = showHomeHeatingPurchasesInDB();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// convert String to LocalDate

		try {
			PreparedStatement preparedStmt = EchoServer.con
					.prepareStatement("UPDATE orderhomeheatingfuel SET Delivered = ? WHERE OrderID = ?");
			for (int i = 0; i < arrOrders.length; i++) {
				LocalDate localDate = LocalDate.parse(arrOrders[i].getEstimatedDate(), formatter);
				if (localDate.isBefore(LocalDate.now())) {
					preparedStmt.setString(1, "yes");
					preparedStmt.setInt(2, i + 1);
				} else {
					preparedStmt.setString(1, "no");
					preparedStmt.setInt(2, i + 1);
				}
				preparedStmt.executeUpdate();
			}
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
