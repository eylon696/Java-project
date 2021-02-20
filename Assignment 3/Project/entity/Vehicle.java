package entity;

/**
 * The class Vehicle represents a generic vehicle
 * 
 * @author Eylon
 *
 */
public class Vehicle implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int customerId;
	private int licensePlateNumber;
	private String vType;
	private String fuelType;
	private boolean flagAddCar;
	private boolean flagRemoveCar;
	private String firstName;
	private String lastName;

	/**
	 * Vehicle constructor with all fields as parameters
	 * 
	 * @param customerId         The Vehicle customer Id	
	 * @param licensePlateNumber The Vehicle license Plate Number
	 * @param vType              The Vehicle vehicle Type
	 * @param fuelType           The Vehicle fuel Type
	 * @param flagAddCar         The Vehicle flag Add Car
	 * @param flagRemoveCar      The Vehicle flag Add Car
	 */
	public Vehicle(int customerId, int licensePlateNumber, String vType, String fuelType, boolean flagAddCar,
			boolean flagRemoveCar) {
		this.customerId = customerId;
		this.licensePlateNumber = licensePlateNumber;
		this.vType = vType;
		this.fuelType = fuelType;
		this.flagAddCar = flagAddCar;
		this.flagRemoveCar = flagRemoveCar;
	}
	
	/**
	 * Vehicle constructor with all fields as parameters
	 * 
	 * @param customerId         The Vehicle customer Id
	 * @param licensePlateNumber The Vehicle license Plate Number
	 * @param vType              The Vehicle vehicle Type
	 * @param fuelType           The Vehicle fuel Type
	 * @param flagAddCar         The Vehicle flag Add Car
	 * @param flagRemoveCar      The Vehicle flag Add Car
	 * @param firstName          The Vehicle first name owner
	 * @param lastName           The Vehicle last name owner
	 */
	public Vehicle(int customerId, int licensePlateNumber, String vType, String fuelType, boolean flagAddCar,
			boolean flagRemoveCar, String firstName, String lastName) {
		this.customerId = customerId;
		this.licensePlateNumber = licensePlateNumber;
		this.vType = vType;
		this.fuelType = fuelType;
		this.flagAddCar = flagAddCar;
		this.flagRemoveCar = flagRemoveCar;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Get first name
	 * 
	 * @return The first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set first name
	 * 
	 * @param firstName The first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get last name
	 * 
	 * @return The last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set last name
	 * 
	 * @param lastName The last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Get the id of the vehicle owner
	 * 
	 * @return customer Id
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * Customer Id setter method
	 * 
	 * @param customerId
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * Get the License Plate Number of the vehicle
	 * 
	 * @return licensePlateNumber
	 */
	public int getLicensePlateNumber() {
		return licensePlateNumber;
	}

	/**
	 * license Plate Number setter method
	 * 
	 * @param licensePlateNumber
	 */
	public void setLicensePlateNumber(int licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	/**
	 * Get the vType of the vehicle
	 * 
	 * @return vType
	 */
	public String getVType() {
		return vType;
	}

	/**
	 * vType setter method
	 * 
	 * @param vType
	 */
	public void setVType(String vType) {
		this.vType = vType;
	}

	/**
	 * Get the fuel Type of the vehicle
	 * 
	 * @return fuelType
	 */
	public String getFuelType() {
		return fuelType;
	}

	/**
	 * fuel Type setter method
	 * 
	 * @param fuelType
	 */
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public boolean isFlagAddCar() {
		return flagAddCar;
	}

	public void setFlagAddCar(boolean flagAddCar) {
		this.flagAddCar = flagAddCar;
	}

	public boolean isFlagRemoveCar() {
		return flagRemoveCar;
	}

	public void setFlagRemoveCar(boolean flagRemoveCar) {
		this.flagRemoveCar = flagRemoveCar;
	}
}
