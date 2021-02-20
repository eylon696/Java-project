package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import entity.Sale;
import entity.SalePattern;

/**
 * The class SaleControllerServer responsible to the connections from DB that
 * relevant to SaleController
 * 
 * @author gal
 *
 */
public class SaleControllerServer {
	/**
	 * This method count the number of the rows in this specific table (in the DB)
	 * 
	 * @return Num of rows in sale pattern table (in the DB)
	 */
	public static int numOfRowsSalePattern() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM salepattern;");
			while (res.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method get data from sale pattern table in DB
	 * 
	 * @return Array of the sale patterns (as in the table in DB)
	 */
	public static SalePattern[] showPatterns() {
		int num = numOfRowsSalePattern();
		SalePattern[] arrPatterns = new SalePattern[num];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM salepattern;");
			while (rs.next()) {
				arrPatterns[i] = (new SalePattern(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrPatterns;
	}

	/**
	 * This method adds sale patterns to DB by the recived data from the client and
	 * according to the query in the method
	 * 
	 * @param msg Object that recived from client to work on him(for any changes in
	 *            the DB)
	 * @return true/false if there is a change in the table
	 */
	public static boolean addSalePatternToDB(Object msg) {
		SalePattern salePattern = (SalePattern) msg;
		int num = numOfRowsSalePattern();
		String query = "INSERT INTO salepattern (PatternID,StartDate,EndDate,Description)" + "VALUES(?,?,?,?)";
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			preparedStmt.setInt(1, salePattern.getIdPattern());
			preparedStmt.setString(2, salePattern.getStartDate());
			preparedStmt.setString(3, salePattern.getEndDate());
			preparedStmt.setString(4, salePattern.getDescription());
			// preparedStmt.setNull(2, Types.NULL);
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (numOfRowsSalePattern() != num)
			return true;
		return false;
	}

	/**
	 * This method count the number of the rows in this specific table (in the DB)
	 * 
	 * @return Num of rows in sale table (in the DB)
	 */
	public static int numOfRowsSale() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM sale;");
			while (res.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method checks if exist a sale in the DB by a sale name that recived from
	 * the client
	 * 
	 * @param msg Including the sale name that we want to find in the DB
	 * @return true/false if exist or not
	 */
	public static boolean checkIfSaleNameExists(String msg) {
		String[] arr = msg.split("SaleNameStartsHere");
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM sale;");
			while (rs.next()) {
				if (rs.getString(1).equals(arr[1]))
					return true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method adds sale to DB by the recived data from the client and according
	 * to the query in the method
	 * 
	 * @param msg Object that recived from client to work on him(for any changes in
	 *            the DB)
	 * @return true/false if there is a change in the table
	 */
	public static boolean addSaleToDB(Object msg) {
		Sale sale = (Sale) msg;
		int num = numOfRowsSale();
		String query = "INSERT INTO sale (OperationName,PatternId,OperationReason,Discount,NumOfCustomersPurchased,SumOfAllPurchases)"
				+ "VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			preparedStmt.setString(1, sale.getSaleName());
			preparedStmt.setInt(2, sale.getPatternID());
			preparedStmt.setString(3, sale.getSaleReason());
			preparedStmt.setInt(4, sale.getDiscount());
			preparedStmt.setInt(5, sale.getNumOfCustomersPurchased());
			preparedStmt.setDouble(6, sale.getSumOfAllPurchases());
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (numOfRowsSale() != num)
			return true;
		return false;
	}

	/**
	 * This method get data from sale table in DB
	 * 
	 * @return Array of the sales (as in the table in DB)
	 */
	public static Sale[] showSales() {
		int num = numOfRowsSale();
		Sale[] arrSales = new Sale[num];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM sale;");
			while (rs.next()) {
				arrSales[i] = (new Sale(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getDouble(6)));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrSales;
	}

	/**
	 * This method update values in sale table in the DB by the recived data from
	 * the client and according to the query in the method
	 * 
	 * @param msg Object that recived from client to work on him(for any changes in
	 *            the DB)
	 * @return true/false if the upadate occurred
	 */
	public static boolean updateSale(Object msg) {
		Sale sale = (Sale) msg;
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(
					"UPDATE sale SET NumOfCustomersPurchased = ?,SumOfAllPurchases = ? WHERE OperationName = ?");
			preparedStmt.setInt(1, sale.getNumOfCustomersPurchased());
			preparedStmt.setDouble(2, sale.getSumOfAllPurchases());
			preparedStmt.setString(3, sale.getSaleName());
			int result = preparedStmt.executeUpdate(); // The number of lines effected.
			if (result == 0)
				return false;
			preparedStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * This method update values in sale pattern table in the DB by the recived data
	 * from the client and according to the query in the method
	 * 
	 * @param msg Object that recived from client to work on him(for any changes in
	 *            the DB)
	 * @return true/false if the upadate occurred
	 */
	public static boolean updateSalePatternWhitIdPattern(String msg) {
		String[] arr = msg.split(":");
		try {
			PreparedStatement preparedStmt = EchoServer.con
					.prepareStatement("UPDATE salepattern SET EndDate = ? WHERE PatternID = ?");
			preparedStmt.setString(1, arr[2]);
			preparedStmt.setInt(2, Integer.valueOf(arr[1]));
			int result = preparedStmt.executeUpdate(); // The number of lines effected.
			if (result == 0)
				return false;
			preparedStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
