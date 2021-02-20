package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class QuarterRevenueReport represents a generic quarter revenue report
 * 
 * @author Eylon
 *
 */	
public class QuarterRevenueReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String chainName;
	private String stationName;	
	private int year;
	private int quarter;
	private double revenue;

	/**
	 * QuarterRevenueReport constructor with all fields as parameters
	 * 
	 * @param chainName   The QuarterRevenueReport chain name
	 * @param stationName The QuarterRevenueReport station name
	 * @param year        The QuarterRevenueReport year
	 * @param quarter     The QuarterRevenueReport quarter
	 * @param revenue     The QuarterRevenueReport revenue
	 */
	public QuarterRevenueReport(String chainName, String stationName, int year, int quarter, double revenue) {
		this.chainName = chainName;
		this.stationName = stationName;
		this.year = year;
		this.quarter = quarter;
		this.revenue = revenue;
	}

	/**
	 * QuarterRevenueReport constructor with all fields coming from the DB using
	 * ResultSet
	 * 
	 * @param resultSet Has all the Quarter Revenue Report parameters
	 * @throws SQLException
	 */
	public QuarterRevenueReport(ResultSet resultSet) throws SQLException {
		this.chainName = resultSet.getString(1);
		this.stationName = resultSet.getString(2);
		this.year = resultSet.getInt(3);
		this.quarter = resultSet.getInt(4);
		this.revenue = resultSet.getDouble(5);
	}

	/**
	 * Get the Quarter Revenue Report chain name
	 * 
	 * @return chainName
	 */
	public String getChainName() {
		return chainName;
	}

	/**
	 * Chain name setter method
	 * 
	 * @param chainName
	 */
	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

	/**
	 * Get the Quarter Revenue Report station name
	 * 
	 * @return stationName
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * Station name setter method
	 * 
	 * @param stationName
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * Get the Quarter Revenue Report year
	 * 
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Year name setter method
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Get the Quarter Revenue Report quarter
	 * 
	 * @return quarter
	 */
	public int getQuarter() {
		return quarter;
	}

	/**
	 * Quarter name setter method
	 * 
	 * @param quarter
	 */
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	/**
	 * Get the Quarter Revenue Report revenue
	 * 
	 * @return revenue
	 */
	public double getRevenue() {
		return revenue;
	}

	/**
	 * Revenue name setter method
	 * 
	 * @param revenue
	 */
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

}
