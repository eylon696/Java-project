package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.PurchasesReport;
import entity.QuantityOfItemsInStockReport;
import entity.QuarterRevenueReport;

/**
 * The class ReportControllerServer responsible to the connections to the DB
 * that relevant to the reports
 * 
 * @author Eylon
 *
 */
public class ReportControllerServer {
	/**
	 * This method update or insert values in quarter revenue report table in the DB
	 * by the received data from the client and according to the query in the method
	 * 
	 * @param msg Object that received from client (quarter revenue report) to save
	 *            in the DB
	 */
	public static void insertQuarterRevenueReport(QuarterRevenueReport msg) {
		if (checkIfQuarterRevenueReportExists(msg)) {// need to update
			try {
				PreparedStatement preparedStmt = EchoServer.con.prepareStatement(
						"UPDATE quarterrevenuereport SET Revenue = ? WHERE ChainName = ? AND StationName = ? AND Year = ? AND Quarter = ?");
				preparedStmt.setDouble(1, msg.getRevenue());
				preparedStmt.setString(2, msg.getChainName());
				preparedStmt.setString(3, msg.getStationName());
				preparedStmt.setInt(4, msg.getYear());
				preparedStmt.setInt(5, msg.getQuarter());
				preparedStmt.executeUpdate();
				preparedStmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		else {// need to insert
			String query = "INSERT INTO quarterrevenuereport (ChainName,StationName,Year,Quarter,Revenue)"
					+ "VALUES(?,?,?,?,?)";
			try {
				PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
				preparedStmt.setString(1, msg.getChainName());
				preparedStmt.setString(2, msg.getStationName());
				preparedStmt.setInt(3, msg.getYear());
				preparedStmt.setInt(4, msg.getQuarter());
				preparedStmt.setDouble(5, msg.getRevenue());
				preparedStmt.execute();

				preparedStmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This method checks if a given a QuarterRevenueReport is already in the DB
	 * 
	 * @param msg the given report
	 * @return True/False according to the result
	 */
	public static boolean checkIfQuarterRevenueReportExists(QuarterRevenueReport msg) {
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM quarterrevenuereport;");
			while (rs.next()) {
				if (rs.getInt(3) == msg.getYear() && rs.getInt(4) == msg.getQuarter()
						&& rs.getString(1).equals(msg.getChainName()) && rs.getString(2).equals(msg.getStationName()))
					return true;

			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * This method checks how many rows are in the quarter revenue report table
	 * 
	 * @param msg the correct chain name
	 * @return number of rows
	 */
	public static int numOfRowsQuarterReportForChain(String msg) {// need to change name because the signature
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM quarterrevenuereport;");
			while (res.next()) {
				if (res.getString(1).equals(msg))
					count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method returns an array of Quarter Revenue report for chain
	 * 
	 * @param message the wanted chain
	 * @return array of reports
	 */
	public static QuarterRevenueReport[] getQuarterRevenueForChain(String message) {
		String[] arrOfStr = message.split(" ");
		QuarterRevenueReport[] arrQuarterRevenueReportForChain = new QuarterRevenueReport[numOfRowsQuarterReportForChain(
				arrOfStr[1])];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM quarterrevenuereport;");
			while (rs.next()) {
				if (rs.getString(1).equals(arrOfStr[1])) // id from marketing representative
				{
					arrQuarterRevenueReportForChain[i] = (new QuarterRevenueReport(rs));
					i++;

				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrQuarterRevenueReportForChain;
	}

	/**
	 * This method checks if a given a QuantityOfItemsInStockReport is already in
	 * the DB
	 * 
	 * @param msg the given report
	 * @return True/False according to the result
	 */
	public static boolean checkIfQuantityOfItemsInStockReportExists(QuantityOfItemsInStockReport[] msg) {
		Statement stmt;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM quantityofitemsinstockreport;");
			while (rs.next()) {
				if (rs.getString(1).equals(msg[0].getChainName()) && rs.getString(2).equals(msg[0].getStationName())
						&& rs.getInt(5) == msg[0].getQuarter() && rs.getInt(6) == msg[0].getYear())
					return true;

			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * This method update or insert values in Quantity Of Items In Stock Report
	 * table in the DB by the received data from the client and according to the
	 * query in the method
	 * 
	 * @param msg Object that received from client (Quantity Of Items In Stock
	 *            Report) to save in the DB
	 */
	public static void insertQuantityOfItemsInStockReport(QuantityOfItemsInStockReport[] msg) {
		if (checkIfQuantityOfItemsInStockReportExists(msg)) {// need to update
			try {
				PreparedStatement preparedStmt = EchoServer.con.prepareStatement(
						"UPDATE quantityofitemsinstockreport SET QuantityInStock = ? WHERE ChainName = ? AND StationName = ? AND FuelType = ? AND Quarter = ? AND Year = ?");
				for (int i = 0; i < msg.length; i++) {
					preparedStmt.setDouble(1, msg[i].getQuantity());
					preparedStmt.setString(2, msg[i].getChainName());
					preparedStmt.setString(3, msg[i].getStationName());
					preparedStmt.setString(4, msg[i].getFuelType());
					preparedStmt.setInt(5, msg[i].getQuarter());
					preparedStmt.setInt(6, msg[i].getYear());
					preparedStmt.executeUpdate();

				}
				preparedStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		else {// need to insert
			String query = "INSERT INTO quantityofitemsinstockreport (ChainName,StationName,FuelType,QuantityInStock,Quarter,Year)"
					+ "VALUES(?,?,?,?,?,?)";
			try {
				PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
				for (int i = 0; i < msg.length; i++) {
					preparedStmt.setString(1, msg[i].getChainName());
					preparedStmt.setString(2, msg[i].getStationName());
					preparedStmt.setString(3, msg[i].getFuelType());
					preparedStmt.setDouble(4, msg[i].getQuantity());
					preparedStmt.setInt(5, msg[i].getQuarter());
					preparedStmt.setInt(6, msg[i].getYear());
					preparedStmt.execute();
				}
				preparedStmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * This method returns an array of QuantityOfItemsInStockReport for chain
	 * 
	 * @param message the wanted chain
	 * @return array of reports
	 */
	public static QuantityOfItemsInStockReport[] getQuantityOfItemsInStockReport(String message) {
		String[] arrOfStr = message.split(" ");
		QuantityOfItemsInStockReport[] arrQuantityOfItemsInStockReport = new QuantityOfItemsInStockReport[numOfRowsQuantityOfItemsInStockReport(
				arrOfStr[1])];

		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM quantityofitemsinstockreport;");
			while (rs.next()) {
				if (rs.getString(1).equals(arrOfStr[1])) {
					arrQuantityOfItemsInStockReport[i] = (new QuantityOfItemsInStockReport(rs));
					i++;

				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrQuantityOfItemsInStockReport;
	}

	/**
	 * This method checks how many rows are in the QuantityOfItemsInStockReport
	 * table
	 * 
	 * @param msg the correct chain name
	 * @return number of rows
	 */
	private static int numOfRowsQuantityOfItemsInStockReport(String msg) {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM quantityofitemsinstockreport;");
			while (res.next()) {
				if (res.getString(1).equals(msg))
					count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * This method checks if a given aPurchasesReport is already in the DB
	 * 
	 * @param report the given report
	 * @return True/False according to the result
	 */

	public static boolean checkIfPurchasesReportExists(PurchasesReport report) {
		Statement stmt;
		ResultSet res;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM purchasesreport;");
			while (res.next()) {
				if (res.getString(1).equals(report.getChainName()) && res.getString(2).equals(report.getStationName())
						&& res.getString(3).equals(report.getFuelType()) && res.getInt(6) == report.getQuarter()
						&& res.getInt(7) == report.getYear())
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method update or insert values in Purchases Report table in the DB by
	 * the received data from the client and according to the query in the method
	 * 
	 * @param msg Object that received from client (Purchases Report) to save in the
	 *            DB
	 */
	public static boolean updateOrInsertPurchasesReport(PurchasesReport[] msg) { //// report
		int i;
		String query1 = "INSERT INTO purchasesreport (ChainName,StationName,FuelType,NumOfPurchases,TotalSumOfPurchases,Quarter,Year) "
				+ "VALUES(?,?,?,?,?,?,?)";
		String query2 = "UPDATE purchasesreport SET NumOfPurchases = ?,TotalSumOfPurchases=? WHERE ChainName = ? AND StationName = ? AND FuelType = ? AND Quarter=? AND Year=?";
		try {
			if (checkIfPurchasesReportExists(msg[0]) == false) {
				PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query1);
				for (i = 0; i < msg.length; i++) {
					preparedStmt.setString(1, msg[i].getChainName());
					preparedStmt.setString(2, msg[i].getStationName());
					preparedStmt.setString(3, msg[i].getFuelType());
					preparedStmt.setInt(4, msg[i].getNumOfPurchases());
					preparedStmt.setDouble(5, msg[i].getTotalSumOfPurchases());
					preparedStmt.setInt(6, msg[i].getQuarter());
					preparedStmt.setInt(7, msg[i].getYear());
					preparedStmt.execute();
				}
				preparedStmt.close();
			} else {
				PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query2);
				for (i = 0; i < msg.length; i++) {
					preparedStmt.setString(3, msg[i].getChainName());
					preparedStmt.setString(4, msg[i].getStationName());
					preparedStmt.setString(5, msg[i].getFuelType());
					preparedStmt.setInt(1, msg[i].getNumOfPurchases());
					preparedStmt.setDouble(2, msg[i].getTotalSumOfPurchases());
					preparedStmt.setInt(6, msg[i].getQuarter());
					preparedStmt.setInt(7, msg[i].getYear());
					preparedStmt.executeUpdate();
				}
				preparedStmt.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * This method checks how many rows are in the PurchasesReport table
	 * 
	 * @return number of rows
	 */
	public static int numOfRowsPurchasesReport() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM purchasesreport;");
			while (res.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * This method returns an array of PurchasesReport
	 * 
	 * @return array of reports
	 */
	public static PurchasesReport[] showPurchasesReport() {
		int num = numOfRowsPurchasesReport();
		PurchasesReport[] arrPurchasesReport = new PurchasesReport[num];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM purchasesreport;");
			while (rs.next()) {
				arrPurchasesReport[i] = (new PurchasesReport(rs.getString(3), rs.getInt(4), rs.getDouble(5),
						rs.getString(1), rs.getString(2), rs.getInt(6), rs.getInt(7)));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrPurchasesReport;
	}

}
