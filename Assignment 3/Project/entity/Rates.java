package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class Rates represents a generic rates
 * 
 * @author Eylon
 *
 */
public class Rates implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String chain;
	private String fuelType;
	private Double rate;

	/**	
	 * Rates constructor with all fields as parameters
	 * 
	 * @param chain    The Rates chain name
	 * @param fuelType The Rates fuel type
	 * @param rate     The Rates rate
	 */
	public Rates(String chain, String fuelType, Double rate) {
		this.chain = chain;
		this.fuelType = fuelType;
		this.rate = rate;
	}

	/**
	 * Rates constructor with all fields coming from the DB using ResultSet
	 * 
	 * @param resultSet Has all the rates parameters
	 * @throws SQLException
	 */
	public Rates(ResultSet resultSet) throws SQLException {
		this.chain = resultSet.getString(1);
		this.fuelType = resultSet.getString(2);
		this.rate = resultSet.getDouble(3);
	}

	/**
	 * Get the rates fuel type
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
	 * Get the rate
	 * 
	 * @return rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * Rate setter method
	 * 
	 * @param rate
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * Get the rates chain
	 * 
	 * @return
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

}
