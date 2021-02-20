package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class PurchasesReport represents a generic Purchases Report
 * 
 * @author gal
 *
 */
public class PurchasesReport implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String fuelType;
	private int numOfPurchases;
	private double totalSumOfPurchases;
	private String chainName;
	private String stationName;
	private int quarter;
	private int year;

	/**
	 * PurchasesReport constructor with all fields as parameters
	 * 
	 * @param fuelType            The fuel type
	 * @param numOfPurchases      The number of purchases
	 * @param totalSumOfPurchases The total sum of purchases
	 * @param chainName           The chain name
	 * @param stationName         The station name
	 * @param quarter             The quarter
	 * @param year                the year
	 */
	public PurchasesReport(String fuelType, int numOfPurchases, double totalSumOfPurchases, String chainName,
			String stationName, int quarter, int year) {
		this.fuelType = fuelType;
		this.numOfPurchases = numOfPurchases;
		this.totalSumOfPurchases = totalSumOfPurchases;
		this.chainName = chainName;
		this.stationName = stationName;
		this.quarter = quarter;
		this.year = year;
	}

	/**
	 * Another PurchasesReport constructor with fields as parameters
	 * 
	 * @param resultSet The data from DB
	 * @throws SQLException
	 */
	public PurchasesReport(ResultSet resultSet) throws SQLException {
		this.fuelType = resultSet.getString(3);
		this.numOfPurchases = Integer.parseInt(resultSet.getString(4));
		this.totalSumOfPurchases = Double.parseDouble(resultSet.getString(5));
		this.chainName = resultSet.getString(1);
		this.stationName = resultSet.getString(2);
		this.quarter = Integer.parseInt(resultSet.getString(6));
		this.year = Integer.parseInt(resultSet.getString(7));
	}

	/**
	 * Get the quarter
	 * 
	 * @return The quarter
	 */
	public int getQuarter() {
		return quarter;
	}

	/**
	 * Set the quarter
	 * 
	 * @param quarter The quarter
	 */
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	/**
	 * Get the year
	 * 
	 * @return The year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Set the year
	 * 
	 * @param year The year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Get the chain name
	 * 
	 * @return The chain name
	 */
	public String getChainName() {
		return chainName;
	}

	/**
	 * Set the chain name
	 * 
	 * @param chainName The chain name
	 */
	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

	/**
	 * Get the station name
	 * 
	 * @return The station name
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * Set the station name
	 * 
	 * @param stationName The station name
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * Get the fuel type
	 * 
	 * @return The fuel type
	 */
	public String getFuelType() {
		return fuelType;
	}

	/**
	 * Set the fuel type
	 * 
	 * @param fuelType The fuel type
	 */
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	/**
	 * Get the number of purchases
	 * 
	 * @return The number of purchases
	 */
	public int getNumOfPurchases() {
		return numOfPurchases;
	}

	/**
	 * Set the number of purchases
	 * 
	 * @param numOfPurchases The number of purchases
	 */
	public void setNumOfPurchases(int numOfPurchases) {
		this.numOfPurchases = numOfPurchases;
	}

	/**
	 * Get the total sum of purchases
	 * 
	 * @return The total sum of purchases
	 */
	public double getTotalSumOfPurchases() {
		return totalSumOfPurchases;
	}

	/**
	 * Set the total sum of purchases
	 * 
	 * @param totalSumOfPurchases The total sum of purchases
	 */
	public void setTotalSumOfPurchases(double totalSumOfPurchases) {
		this.totalSumOfPurchases = totalSumOfPurchases;
	}

}