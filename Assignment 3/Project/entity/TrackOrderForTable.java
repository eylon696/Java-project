package entity;

/**
 * The class TrackOrderForTable represents a generic track order for table view
 * 
 * @author Eylon
 *
 */
public class TrackOrderForTable implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String date;
	private float price;

	/**
	 * TrackOrderForTable constructor with all fields as parameters
	 * 
	 * @param id    The track order's id
	 * @param date  The track order's date
	 * @param price The track order's price
	 */
	public TrackOrderForTable(int id, String date, float price) {
		this.id = id;
		this.date = date;
		this.price = price;
	}
	
	/**
	 * Get the track order's id
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Id setter method
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the track order's date
	 * 
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Date setter method
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Get the track order's price
	 * 
	 * @return price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Price setter method
	 * 
	 * @param price
	 */
	public void setPrice(float price) {
		this.price = price;
	}

}
