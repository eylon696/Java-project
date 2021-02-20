package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class GasStation represents a generic Gas Station
 * 
 * @author gal
 *
 */
public class GasStation implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private final Double maxStock = 1000.0;

	private String ChainName;
	private String StationName;
	private String FuelType;
	private Double StockQuantity;
	private Double ThresholdLevel;

	/**	
	 * GasStation constructor with all fields as parameters
	 * 
	 * @param ChainName      The chain name
	 * @param StationName    The station name
	 * @param FuelType       The fuel type
	 * @param StockQuantity  The stock quantity
	 * @param ThresholdLevel The threshold level
	 */
	public GasStation(String ChainName, String StationName, String FuelType, Double StockQuantity,
			Double ThresholdLevel) {
		this.ChainName = ChainName;
		this.StationName = StationName;
		this.FuelType = FuelType;
		this.StockQuantity = StockQuantity;
		this.ThresholdLevel = ThresholdLevel;
	}

	/**
	 * Another GasStation constructor with fields as parameters
	 * 
	 * @param resultSet The data from DB
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public GasStation(ResultSet resultSet) throws NumberFormatException, SQLException {
		this.ChainName = resultSet.getString(1);
		this.StationName = resultSet.getString(2);
		this.FuelType = resultSet.getString(3);
		this.StockQuantity = resultSet.getDouble(4);
		this.ThresholdLevel = resultSet.getDouble(5);
	}

	/**
	 * Get the chain name
	 * 
	 * @return The chain name
	 */
	public String getChainName() {
		return ChainName;
	}

	/**
	 * Set the chain name
	 * 
	 * @param chainName The chain name
	 */
	public void setChainName(String chainName) {
		ChainName = chainName;
	}

	/**
	 * Get the station name
	 * 
	 * @return The station name
	 */
	public String getStationName() {
		return StationName;
	}

	/**
	 * Set the station name
	 * 
	 * @param stationName The station name
	 */
	public void setStationName(String stationName) {
		StationName = stationName;
	}

	/**
	 * Get the fuel type
	 * 
	 * @return The fuel type
	 */
	public String getFuelType() {
		return FuelType;
	}

	/**
	 * Set the fuel type
	 * 
	 * @param fuelType The fuel type
	 */
	public void setFuelType(String fuelType) {
		FuelType = fuelType;
	}

	/**
	 * Get the stock quantity
	 * 
	 * @return The stock quantity
	 */
	public Double getStockQuantity() {
		return StockQuantity;
	}

	/**
	 * Set the stock quantity
	 * 
	 * @param stockQuantity The stock quantity
	 */
	public void setStockQuantity(Double stockQuantity) {
		StockQuantity = stockQuantity;
	}

	/**
	 * Get the threshold level
	 * 
	 * @return The threshold level
	 */
	public Double getThresholdLevel() {
		return ThresholdLevel;
	}

	/**
	 * Set the threshold level
	 * 
	 * @param thresholdLevel The threshold level
	 */
	public void setThresholdLevel(Double thresholdLevel) {
		ThresholdLevel = thresholdLevel;
	}

	/**
	 * Get the max stock
	 * 
	 * @return The max stock
	 */
	public Double getMaxStock() {
		return maxStock;
	}

}