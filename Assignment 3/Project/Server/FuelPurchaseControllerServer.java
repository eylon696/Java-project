package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import entity.ChainOfGas;
import entity.Customer;
import entity.GasStation;
import entity.ProductsInGasStation;
import entity.Purchase;
import entity.PurchasePlan;
import entity.Vehicle;

/**
 * The class FuelPurchaseControllerServer responsible to the connections from DB
 * that relavnts to FuelPurchaseController
 * 
 * @author gal
 *
 */
public class FuelPurchaseControllerServer {
	/**
	 * This method count the number of the rows in this specific table (in the DB)
	 * according to the suitable condition
	 * 
	 * @param idConnect The id that comes from the client(id of the login user)
	 * @return Num of rows in vehicle table according to the suitable condition (in
	 *         the DB)
	 */	
	public static int numOfRowsVehicleForIdConnect(int idConnect) {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM vehicle;");
			while (res.next()) {
				if (res.getInt(1) == idConnect) /// id from login
					count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method gets a string from tha client with some data and takes stock from
	 * the DB according to the query and the condition
	 * 
	 * @param msg String contains chain name ,station name and fuel type
	 * @return stock from the gasstions table in DB according to the condition
	 */
	public static double getStockOfGasStationByChainStationFuelType(String msg) {
		String[] arr = msg.split(":");
		Statement stmt;
		double stock = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM gasstations;");
			while (rs.next()) {
				if (rs.getString(1).equals(arr[1]) && rs.getString(2).equals(arr[2])
						&& arr[3].contains(rs.getString(3))) {
					stock = rs.getDouble(4);
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stock;
	}

	/**
	 * This method returns array of vehicles from the DB by the id got from the
	 * client(id of the login user)
	 * 
	 * @param msg String contains the id that comes from the client(id of the login
	 *            user)
	 * @return Array of the vehicles according to the id
	 */
	public static Vehicle[] getDetailsOfVehiclesForCustomerId(String msg) {
		int i = 0;
		Statement stmt;
		String[] arr = msg.split(":");
		Vehicle[] arrVehiclesForCustomerId = new Vehicle[numOfRowsVehicleForIdConnect(Integer.valueOf(arr[1]))];
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle;");
			while (rs.next()) {
				if (rs.getInt(1) == Integer.valueOf(arr[1])) /// id from login
				{
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
	 * This method gets a string from tha client with some data and takes price for
	 * liter from the DB according to the query and the condition
	 * 
	 * @param productName String contains chain name and fuel type
	 * @return priceForLitre from the rates table in DB according to the condition
	 */
	public static double getPriceForLitreVehicleForCustomerId(String productName) {
		Statement stmt;
		double priceForLitre = 0;
		String[] fuelType = productName.split(":");
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM rates;");
			while (rs.next()) {
				if (fuelType[1].contains(rs.getString(2)) && fuelType[2].equals(rs.getString(1)))
					priceForLitre = rs.getDouble(3);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return priceForLitre;
	}

	/**
	 * This method count the number of the rows in this specific table (in the DB)
	 * 
	 * @return Num of rows in purchase table (in the DB)
	 */
	public static int numOfRowsPurchase() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM purchase;");
			while (res.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method adds purchase to DB by the recived data from the client and
	 * according to the query in the method
	 * 
	 * @param msg Object that recived from client to work on him(for any changes in
	 *            the DB)
	 * @return true/false if there is a change in the table
	 */
	public static boolean addFuelingToDB(Object msg) {
		Purchase purchase = (Purchase) msg;
		int num = numOfRowsPurchase();
		String query = "INSERT INTO purchase (PurchaseID,CustomerId,LicenseNumber,Date,Time,Quantity,Price,Payment,ChainName,StationName,OperationName)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(query);
			preparedStmt.setInt(1, purchase.getIdPurchase());
			preparedStmt.setInt(2, purchase.getCustomerId());
			preparedStmt.setInt(3, purchase.getLicenseNum());
			preparedStmt.setString(4, purchase.getDatePurchase());
			preparedStmt.setString(5, purchase.getTimePurchase());
			preparedStmt.setDouble(6, purchase.getQuantityPurchase());
			preparedStmt.setDouble(7, purchase.getPricePurchase());
			preparedStmt.setString(8, purchase.getPaymentPurchase());
			preparedStmt.setString(9, purchase.getChainName());
			preparedStmt.setString(10, purchase.getStationName());
			if (purchase.getSaleName() == null || purchase.getSaleName().equals(""))
				preparedStmt.setNull(11, Types.NULL);
			else
				preparedStmt.setString(11, purchase.getSaleName());
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (numOfRowsPurchase() != num)
			return true;
		return false;
	}

	/**
	 * This method count the number of the rows in this specific table (in the DB)
	 * 
	 * @return Num of rows in vehicle table (in the DB)
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
	 * This method get data from purchase table in DB
	 * 
	 * @return Array of the purchases (as in the table in DB)
	 */
	public static Purchase[] showPurchases() {
		int num = numOfRowsPurchase();
		Purchase[] arrPurchases = new Purchase[num];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM purchase;");
			while (rs.next()) {
				arrPurchases[i] = (new Purchase(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11)));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrPurchases;
	}

	/**
	 * This method get data from vehicle table in DB
	 * 
	 * @return Array of the vehicles (as in the table in DB)
	 */
	public static Vehicle[] showVehicles() {
		int num = numOfRowsVehicle();
		Vehicle[] arrVehicles = new Vehicle[num];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle;");
			while (rs.next()) {
				arrVehicles[i] = (new Vehicle(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), false,
						false));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrVehicles;
	}

	/**
	 * This method returns purchase plan from the DB by the id got from the
	 * client(id of the login user)
	 * 
	 * @param msg String contains the id that comes from the client(id of the login
	 *            user)
	 * @return purchase plan according to the id
	 */
	public static PurchasePlan getDetailsOfPurchasePlanForCustomerIdConnecnt(String msg) {
		Statement stmt;
		String[] arr = msg.split(":");
		PurchasePlan purchasePlansForCustomerId = null;
		ArrayList<ChainOfGas> arrGasStation = new ArrayList<ChainOfGas>();
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM purchaseplan;");
			while (rs.next()) {
				if (rs.getInt(1) == Integer.valueOf(arr[1])) /// id from login
				{
					arrGasStation.add(new ChainOfGas(rs.getString(3)));
					arrGasStation.add(new ChainOfGas(rs.getString(4)));
					arrGasStation.add(new ChainOfGas(rs.getString(5)));
					purchasePlansForCustomerId = (new PurchasePlan(rs.getInt(1), rs.getString(2), arrGasStation));
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return purchasePlansForCustomerId;
	}

	/**
	 * This method count the number of the rows in this specific table (in the DB)
	 * 
	 * @return Num of rows in chain table (in the DB)
	 */
	public static int numOfRowsChain() {
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
	 * This method returns array of chains from the DB
	 * 
	 * @return Array of the chains
	 */
	public static ChainOfGas[] getDetailsOfChain() {
		int num = numOfRowsChain();
		ChainOfGas[] arrChainOfGas = new ChainOfGas[num];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM chain;");
			while (rs.next()) {
				arrChainOfGas[i] = (new ChainOfGas(rs.getString(1)));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrChainOfGas;
	}

	/**
	 * This method count the number of the rows in this specific table (in the DB)
	 * 
	 * @param chainName The chain name that recived from the client
	 * @return Num of rows in gasstations table (in the DB) according to the chain
	 *         name
	 */
	public static int numOfRowsGasStationForChain(String chainName) {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM gasstations;");
			while (res.next()) {
				if (res.getString(1).equals(chainName))
					count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method returns array of gasstations from the DB according to the chain
	 * name recived from the client
	 * 
	 * @param msg String contains the chain name
	 * @return Array of the gasstations according to the chain name
	 */
	public static GasStation[] getDetailsOfGasStationForChain(String msg) {
		String[] arr = msg.split(":");
		int num = numOfRowsGasStationForChain(arr[1]);
		GasStation[] arrGasStation = new GasStation[num];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM gasstations;");
			while (rs.next()) {
				if (rs.getString(1).equals(arr[1])) {
					arrGasStation[i] = (new GasStation(rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getDouble(4), rs.getDouble(5)));
					i++;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrGasStation;
	}

	/**
	 * This method returns customer from the DB by the id got from the client(id of
	 * the login user)
	 * 
	 * @param msg String contains the id that comes from the client(id of the login
	 *            user)
	 * @return customer according to the id
	 */
	public static Customer getDetailsOfCustomerIdConnecnt(String msg) {
		Statement stmt;
		String[] arr = msg.split(":");
		Customer customer = null;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer;");
			while (rs.next()) {
				if (rs.getInt(3) == Integer.valueOf(arr[1])) /// id from login
					customer = new Customer(rs);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	/**
	 * This method update values in gasstations table in the DB by the recived data
	 * from the client and according to the query in the method
	 * 
	 * @param msg Object that recived from client to work on him(for any changes in
	 *            the DB)
	 * @return true/false if the upadate occurred
	 */
	public static boolean updateStockInGasStation(Object msg) {
		GasStation gasStation = (GasStation) msg;
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(
					"UPDATE gasstations SET StockQuantity = ? WHERE (ChainName = ?) AND (StationName=?) AND (FuelType=?)");
			preparedStmt.setDouble(1, gasStation.getStockQuantity());
			preparedStmt.setString(2, gasStation.getChainName());
			preparedStmt.setString(3, gasStation.getStationName());
			preparedStmt.setString(4, gasStation.getFuelType());
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
	 * This method count the number of the rows in this specific table (in the DB)
	 * 
	 * @return Num of rows in gasstations table (in the DB)
	 */
	public static int numOfRowsGasStation() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM gasstations;");
			while (res.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * This method get data from gasstations table in DB
	 * 
	 * @return Array of the gasstations (as in the table in DB)
	 */
	public static GasStation[] getDetailsOfAllGasStation() {
		int num = numOfRowsGasStation();
		GasStation[] arrGasStation = new GasStation[num];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM gasstations;");
			while (rs.next()) {
				arrGasStation[i] = (new GasStation(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
						rs.getDouble(5)));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrGasStation;
	}

	/**
	 * This method count the number of the rows in this specific table (in the DB)
	 * according to chain and gasstation
	 * 
	 * @param chain      chain name from the client
	 * @param gasStation station name from the client
	 * @return Num of rows in gasstations table (in the DB) according to chain and
	 *         gasstation
	 */
	public static int numOfRowsProductsInGasStationInDB(String chain, String gasStation) {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM gasstations;");
			while (res.next()) {
				if (res.getString(1).equals(chain) && res.getString(2).equals(gasStation))
					count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method get data from gasstations table in DB according to chain and
	 * gasstation
	 * 
	 * @param message String contains chain name and station name
	 * @return Array of the gasstations (as in the table in DB) according to chain
	 *         and gasstation
	 */
	public static ProductsInGasStation[] showProductsInGasStationInDB(String message) {
		String[] arrMsg = message.split(":");
		String chain = arrMsg[1];
		String gasStation = arrMsg[2];
		ProductsInGasStation[] arrProducts = new ProductsInGasStation[numOfRowsProductsInGasStationInDB(chain,
				gasStation)];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM gasstations;");
			while (rs.next()) {
				if (rs.getString(1).equals(chain) && rs.getString(2).equals(gasStation)) {
					arrProducts[i] = (new ProductsInGasStation(rs));
					i++;
				}

			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrProducts;
	}

	/**
	 * This method update values in gasstations table in the DB by the recived data
	 * from the client and according to the query in the method
	 * 
	 * @param msg Object of ProductsInGasStation that recived from client to work on
	 *            him(for any changes in the DB)
	 */
	public static void updateThresholdLevel(ProductsInGasStation msg) {
		try {
			PreparedStatement preparedStmt = EchoServer.con.prepareStatement(
					"UPDATE gasstations SET ThresholdLevel = ? WHERE ChainName = ? AND StationName = ? AND FuelType = ?");
			preparedStmt.setDouble(1, msg.getThresholdLevel());
			preparedStmt.setString(2, msg.getChain().toLowerCase());
			preparedStmt.setString(3, msg.getStation().toLowerCase());
			preparedStmt.setString(4, msg.getFuelType().toLowerCase());
			preparedStmt.executeUpdate();

			preparedStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}