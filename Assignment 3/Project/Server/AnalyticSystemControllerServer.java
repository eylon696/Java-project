package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import entity.RankForTable;

/**
 * The class AnalyticSystemControllerServer responsible to the connections from
 * DB that relavnts to AnalyticSystemController
 * 
 * @author gal
 *
 */		

public class AnalyticSystemControllerServer {
	/**
	 * This method count the number of the rows in this specific table (in the DB)
	 * 
	 * @return Num of rows in ranks table (in the DB)
	 */
	public static int numOfRowsRanks() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM ranks;");
			while (res.next()) {
				count++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method adds ranks to DB by the recived data from the client and
	 * according to the query in the method
	 * 
	 * @param msg Object that recived from client to work on him(for any changes in
	 *            the DB)
	 * @return true/false if there is a change in the table
	 */
	public static boolean addRanksToDB(Object msg) {
		int i;

		try {
			Statement statement = EchoServer.con.createStatement();
			statement.executeUpdate("TRUNCATE ranks");
		} catch (SQLException e) {
			System.out.println("Could not truncate ranks " + e.getMessage());
		}
		int num = numOfRowsRanks();
		RankForTable[] arr = (RankForTable[]) msg;
		String query1 = "INSERT INTO ranks (CustomerId,CustomerRank) " + "VALUES(?,?)";
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query1);
			for (i = 0; i < arr.length; i++) {
				RankForTable rank = new RankForTable(arr[i].getCustomerId(), arr[i].getRank());
				preparedStmt.setInt(1, rank.getCustomerId());
				preparedStmt.setDouble(2, rank.getRank());
				preparedStmt.execute();
			}
			preparedStmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (numOfRowsRanks() != num)
			return true;
		return false;
	}

}
