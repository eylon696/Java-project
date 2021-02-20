package entity;

/**	
 * The class ChainOfGas represents a generic chain of gas
 * 
 * @author Eylon
 *
 */	
public class ChainOfGas implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String chainName;

	/**
	 * ChainOfGas constructor with all fields as parameters
	 * 
	 * @param chainName The ChainOfGas name
	 */
	public ChainOfGas(String chainName) {
		this.chainName = chainName;
	}	
	
	/**	
	 * Get the ChainOfGas name
	 * 
	 * @return The chain name
	 */
	public String getName() {
		return chainName;
	}

	/**
	 * Chain name setter method
	 * 
	 * @param chainName The chainName name
	 */

	public void setName(String chainName) {
		this.chainName = chainName;
	}
}
