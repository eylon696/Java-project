package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class ProductsInGasStation represents a generic products in gas station
 * 
 * @author Eylon
 *
 */
public class ProductsInGasStation implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String chain;
	private String station;
	private String fuelType;
	private double stockQuantity;
	private double thresholdLevel;

	/**		
	 * ProductsInGasStation constructor with all fields as parameters
	 * 
	 * @param chain          The ProductsInGasStation chain name
	 * @param station        The ProductsInGasStation station name
	 * @param fuelType       The ProductsInGasStation fuel type
	 * @param stockQuantity  The ProductsInGasStation stock quantity
	 * @param thresholdLevel The ProductsInGasStation thresholdLevel
	 */
	public ProductsInGasStation(String chain, String station, String fuelType, double stockQuantity,
			double thresholdLevel) {
		this.chain = chain;
		this.station = station;
		this.fuelType = fuelType;
		this.stockQuantity = stockQuantity;
		this.thresholdLevel = thresholdLevel;
	}

	/**
	 * ProductsInGasStation constructor with all fields coming from the DB using
	 * 
	 * @param resultSet Has all the products in gas station parameters
	 * @throws SQLException
	 */
	public ProductsInGasStation(ResultSet resultSet) throws SQLException {
		this.chain = resultSet.getString(1);
		this.station = resultSet.getString(2);
		this.fuelType = resultSet.getString(3);
		this.stockQuantity = resultSet.getDouble(4);
		this.thresholdLevel = resultSet.getDouble(5);

	}

	/**
	 * Get the products in gas chain name
	 * 
	 * @return chain
	 */
	public String getChain() {
		return chain;
	}

	/**
	 * Chain setter method
	 * 
	 * @param chain
	 */
	public void setChain(String chain) {
		this.chain = chain;
	}

	/**
	 * Get the products in gas station name
	 * 
	 * @return station
	 */
	public String getStation() {
		return station;
	}

	/**
	 * Station setter method
	 * 
	 * @param station
	 */
	public void setStation(String station) {
		this.station = station;
	}

	/**
	 * Get the products in gas fuel type
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
	 * Get the products in gas stock quantity
	 * 
	 * @return stockQuantity
	 */
	public double getStockQuantity() {
		return stockQuantity;
	}

	/**
	 * Stock quantity setter method
	 * 
	 * @param stockQuantity
	 */
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	/**
	 * Get the products in gas thresholdLevel
	 * 
	 * @return thresholdLevel
	 */
	public double getThresholdLevel() {
		return thresholdLevel;
	}

	/**
	 * ThresholdLevel setter method
	 * 
	 * @param thresholdLevel
	 */
	public void setThresholdLevel(double thresholdLevel) {
		this.thresholdLevel = thresholdLevel;
	}

}
