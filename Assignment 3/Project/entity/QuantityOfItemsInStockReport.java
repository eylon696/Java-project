package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class QuantityOfItemsInStockReport represents a generic quantity of items
 * in stock report
 * 
 * @author Eylon
 *
 */
public class QuantityOfItemsInStockReport implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String chainName;
	private String stationName;
	private String fuelType;
	private double quantity;
	private int year;
	private int quarter;

	/**
	 * QuantityOfItemsInStockReport constructor with all fields as parameters
	 * 
	 * @param chainName   The QuantityOfItemsInStockReport chain name
	 * @param stationName The QuantityOfItemsInStockReport station name
	 * @param fuelType    The QuantityOfItemsInStockReport fuel type
	 * @param quantity    The QuantityOfItemsInStockReport quantity
	 * @param year        The QuantityOfItemsInStockReport year
	 * @param quarter     The QuantityOfItemsInStockReport quarter
	 */
	public QuantityOfItemsInStockReport(String chainName, String stationName, String fuelType, double quantity,
			int year, int quarter) {
		this.chainName = chainName;
		this.stationName = stationName;
		this.fuelType = fuelType;
		this.quantity = quantity;
		this.year = year;
		this.quarter = quarter;
	}

	/**
	 * QuantityOfItemsInStockReport constructor with all fields coming from the DB
	 * using ResultSet
	 * 
	 * @param resultSet Has all the Quantity Of Items In Stock Report parameters
	 * @throws SQLException
	 */
	public QuantityOfItemsInStockReport(ResultSet resultSet) throws SQLException {
		this.chainName = resultSet.getString(1);
		this.stationName = resultSet.getString(2);
		this.fuelType = resultSet.getString(3);
		this.quantity = resultSet.getDouble(4);
		this.year = resultSet.getInt(6);
		this.quarter = resultSet.getInt(5);
	}

	/**
	 * Get the year Of Items In Stock report
	 * 
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Year setter method
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Get the quarter Of Items In Stock report
	 * 
	 * @return quarter
	 */
	public int getQuarter() {
		return quarter;
	}

	/**
	 * Quarter setter method
	 * 
	 * @param quarter
	 */
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	/**
	 * Get the Quantity Of Items In Stock Report chain name
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
	 * Get the Quantity Of Items In Stock Report station name
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
	 * Get the Quantity Of Items In Stock Report fuel type
	 * 
	 * @return fuelType
	 */
	public String getFuelType() {
		return fuelType;
	}

	/**
	 * Fuel type setter method
	 * 
	 * @param fuelType
	 */
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	/**
	 * Get the Quantity Of Items In Stock Report quantity
	 * 
	 * @return quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * Quantity setter method
	 * 
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

}
