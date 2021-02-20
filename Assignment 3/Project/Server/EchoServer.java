// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import ocsf.server.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Customer;
import entity.CustomerForTable;
import entity.GasStation;
import entity.OrderHomeHeatingFuel;
import entity.ProductsInGasStation;
import entity.Purchase;
import entity.PurchasePlan;
import entity.PurchasesReport;
import entity.QuantityOfItemsInStockReport;
import entity.QuarterRevenueReport;
import entity.RankForTable;
import entity.Sale;
import entity.SalePattern;
import entity.Vehicle;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */

public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	public static Connection con;
	public static ArrayList<Integer> customerEmployeeLoginArrayList = new ArrayList<Integer>();

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	public EchoServer(int port) {
		super(port);
		connectToDB();
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Message received: " + msg + " from " + client);
		try {
			if (msg instanceof String) {
				String message = (String) msg;
				showEmployeeFunctionality(message, client);
				loginFunctionality(message, client);
				checkIfCustomerIdExistsFunctionality(message, client);
				checkIfLicensePlateNumberExistsFunctionality(message, client);
				getDetailsOfVehiclesForCustomerIdFunctionality(message, client);
				getAllGasCompaniesFunctionality(message, client);
				checkIfEmailExistsFunctionality(message, client);
				checkIfCreditCardExistsFunctionality(message, client);
				priceOfHomeHeatingFuelFunctionality(message, client);
				findTheNextIDInPurchaseHomeHeatingFuelFunctionality(message, client);
				showOrdersHomeHeatingFuelForCustomerIDFunctionality(message, client);
				showCustomersFunctionality(message, client);
				removeCustomerByIDFunctionality(message, client);
				getNumOfVehiclesForCustomerFunctionality(message, client);
				showHomeHeatingPurchasesInDBFunctionality(message, client);
				showPlansInDBFunctionality(message, client);
				showProductsInGasStationInDBFunctionality(message, client);
				RateFunctionality(message, client);
				VerificationFunctionality(message, client);
				getQuarterRevenueForChainFunctionality(message, client);
				getQuantityOfItemsInStockReportFunctionality(message, client);
				customerEmployeeLoginArrayList(message, client);
				updateOrderDeliveredToYesFunctionality(message, client);

				////// Gal+shiraz///////

				if (((String) msg).contains("updateSalePatternWithIdPattern"))
					client.sendToClient(SaleControllerServer.updateSalePatternWhitIdPattern(((String) msg)));
				////// Gal+shiraz///////
				else if (((String) msg).equals("showPurchases"))
					client.sendToClient(FuelPurchaseControllerServer.showPurchases());
				else if (((String) msg).equals("showSalesPatterns"))
					client.sendToClient(SaleControllerServer.showPatterns());
				else if (((String) msg).equals("showSales"))
					client.sendToClient(SaleControllerServer.showSales());
				else if (((String) msg).equals("showPurchasesReport"))
					client.sendToClient(ReportControllerServer.showPurchasesReport());

				else if (((String) msg).contains("getStockOfGasStationByChainStationFuelType")) /// For eylonnnnn
					client.sendToClient(
							FuelPurchaseControllerServer.getStockOfGasStationByChainStationFuelType((String) msg));

				else if (((String) msg).equals("showVehicles"))
					client.sendToClient(FuelPurchaseControllerServer.showVehicles());
				else if (((String) msg).equals("showCustomers"))
					client.sendToClient(UserControllerServer.showCustomers());
				else if (((String) msg).contains("getDetailsOfVehiclesForCustomerIdConnecnt"))
					client.sendToClient(FuelPurchaseControllerServer.getDetailsOfVehiclesForCustomerId((String) msg));
				else if (((String) msg).contains("getDetailsOfPurchasePlanForCustomerIdConnecnt"))
					client.sendToClient(
							FuelPurchaseControllerServer.getDetailsOfPurchasePlanForCustomerIdConnecnt((String) msg));
				else if (((String) msg).equals("getDetailsOfChain"))
					client.sendToClient(FuelPurchaseControllerServer.getDetailsOfChain());
				else if (((String) msg).contains("getDetailsOfGasStationForChain"))
					client.sendToClient(FuelPurchaseControllerServer.getDetailsOfGasStationForChain((String) msg));
				else if (((String) msg).contains("showAllGasStations"))
					client.sendToClient(FuelPurchaseControllerServer.getDetailsOfAllGasStation());
				else if (((String) msg).equals("checkNumOfRowsOfPurchases"))
					client.sendToClient(FuelPurchaseControllerServer.numOfRowsPurchase());
				else if (((String) msg).contains("getPriceForLitreForChoosenFuelType"))
					client.sendToClient(
							FuelPurchaseControllerServer.getPriceForLitreVehicleForCustomerId(((String) msg)));
				else if (((String) msg).contains("getDetailsOfCustomerIdConnecnt")) //
					client.sendToClient(FuelPurchaseControllerServer.getDetailsOfCustomerIdConnecnt((String) msg));
				else if (((String) msg).contains("deleteVehicle"))
					client.sendToClient(UserControllerServer.deleteVehicle(((String) msg)));
				else if (((String) msg).contains("checkSubscriptionTypeForCustomerID"))
					client.sendToClient(UserControllerServer.checkSubscriptionTypeForCustomerID(((String) msg)));
				else if (((String) msg).equals("checkIdPatternToInsert"))
					client.sendToClient(SaleControllerServer.numOfRowsSalePattern());
				else if (((String) msg).contains("checkIfSaleNameExists"))
					client.sendToClient(SaleControllerServer.checkIfSaleNameExists(((String) msg)));

			} else if (msg instanceof ArrayList<?>) {
				if (((ArrayList<Vehicle>) msg).get(0).isFlagAddCar()) {
					UserControllerServer.addCarsToCustomers(msg);
					client.sendToClient("car added successfuly");
				}
			} else if (msg instanceof PurchasePlan) {
				UserControllerServer.addPurchasePlanToCustomers(msg);
				client.sendToClient("purchase plan added successfuly");
			} else if (msg instanceof Customer) {
				insertNewCustomerToTheDbFunctionality((Customer) msg, client);

			} else if (msg instanceof ProductsInGasStation) {
				FuelPurchaseControllerServer.updateThresholdLevel((ProductsInGasStation) msg);
				client.sendToClient("Threshold level updated successfuly");
			} else if (msg instanceof OrderHomeHeatingFuel) {
				OrderHomeHeatingFuelControllerServer.addPurchaseHomeHeatingFuelToTheDb((OrderHomeHeatingFuel) msg);
				client.sendToClient("purchase home heating fuel added successfuly");
			} else if (msg instanceof CustomerForTable) {
				CustomersDBLogic.updateCustomerDetails((CustomerForTable) msg);
				client.sendToClient("Customer details updated successfuly");
			} else if (msg instanceof QuantityOfItemsInStockReport[]) {
				ReportControllerServer.insertQuantityOfItemsInStockReport((QuantityOfItemsInStockReport[]) msg);
				client.sendToClient("Quantity of items in stock report details updated successfuly");
			} else if (msg instanceof QuarterRevenueReport) {
				ReportControllerServer.insertQuarterRevenueReport((QuarterRevenueReport) msg);
				client.sendToClient("Quarter Revenue Report saved successfuly");
			}
			///
			else if (msg instanceof PurchasesReport[]) { /////////////////////////////
				client.sendToClient(ReportControllerServer.updateOrInsertPurchasesReport((PurchasesReport[]) msg));
			} else if (msg instanceof Sale) {
				Sale sale = (Sale) msg;
				if (sale.getSumOfAllPurchases() == 0)
					client.sendToClient(SaleControllerServer.addSaleToDB((Sale) msg));
				else
					client.sendToClient(SaleControllerServer.updateSale((Sale) msg));
			} else if (msg instanceof SalePattern)
				client.sendToClient(SaleControllerServer.addSalePatternToDB((SalePattern) msg));
			else if (msg instanceof GasStation)
				client.sendToClient(FuelPurchaseControllerServer.updateStockInGasStation((GasStation) msg));
			else if (msg instanceof Purchase)
				client.sendToClient(FuelPurchaseControllerServer.addFuelingToDB(((Purchase) msg)));
			///

			else if (msg instanceof RankForTable[])
				client.sendToClient(AnalyticSystemControllerServer.addRanksToDB(msg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * This method connecting to the DB
	 */
	public static void connectToDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/test?serverTimezone=IST", "root", "Aa123456");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	/**
	 * This method describe the functionality of the customer
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void showCustomersFunctionality(String message, ConnectionToClient client) {
		if (message.contains("showCustomersInDB")) {
			try {
				client.sendToClient(CustomersDBLogic.showCustomers());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method update order delivered to yes
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	private void updateOrderDeliveredToYesFunctionality(String message, ConnectionToClient client) {
		if (message.contains("updateOrderDeliveredToYes")) {
			OrderHomeHeatingFuelControllerServer.updateOrderDeliveredToYes();
			try {
				client.sendToClient("Order delivery updated successfuly");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * This method describe the users that connected to our server
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void customerEmployeeLoginArrayList(String message, ConnectionToClient client) {
		String[] strArray = message.split(" ");
		switch (strArray[0]) {
		case "AddUserToArrayList":
			try {
				String userID = strArray[1];
				client.sendToClient(UserControllerServer.customerEmployeeLoginArrayList(userID));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "RemoveUserFromArrayList":
			try {
				String userID = strArray[1];
				client.sendToClient(UserControllerServer.removeCustomerEmployeeLoginArrayList(userID));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}

	}

	/**
	 * This method show home heating purchases at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	private void showHomeHeatingPurchasesInDBFunctionality(String message, ConnectionToClient client) {
		if (message.contains("showHomeHeatingPurchasesInDB")) {
			try {
				client.sendToClient(OrderHomeHeatingFuelControllerServer.showHomeHeatingPurchasesInDB());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method show purchase plans at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	private void showPlansInDBFunctionality(String message, ConnectionToClient client) {
		if (message.contains("showPlansInDB")) {
			try {
				client.sendToClient(CustomersDBLogic.showPurchasePlansInDB());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method show products in the gas station at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	private void showProductsInGasStationInDBFunctionality(String message, ConnectionToClient client) {
		if (message.contains("showProductsInGasStationInDB")) {
			try {
				client.sendToClient(FuelPurchaseControllerServer.showProductsInGasStationInDB(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method show gets quarter revenue for chain manager at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	private void getQuarterRevenueForChainFunctionality(String message, ConnectionToClient client) {
		if (message.contains("getQuarterRevenueForChain")) {
			try {
				client.sendToClient(ReportControllerServer.getQuarterRevenueForChain(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method show gets quantity of items in stock report at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	private void getQuantityOfItemsInStockReportFunctionality(String message, ConnectionToClient client) {
		if (message.contains("getQuantityOfItemsInStockReport")) {
			try {
				client.sendToClient(ReportControllerServer.getQuantityOfItemsInStockReport(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method show the number of vehicles for customers at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void getNumOfVehiclesForCustomerFunctionality(String message, ConnectionToClient client) {
		if (message.contains("getNumOfVehiclesForCustomer")) {
			try {
				client.sendToClient(CustomersDBLogic.getNumOfVehiclesForCustomer(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method show employees at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void showEmployeeFunctionality(String message, ConnectionToClient client) {
		if (message.contains("showEmployeesInDB")) {
			try {
				client.sendToClient(EmployeeDBLogic.showEmployees());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method show remove customer by ID at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	private void removeCustomerByIDFunctionality(String message, ConnectionToClient client) {
		// TODO Auto-generated method stub
		if (message.contains("removeCustomerByID")) {
			try {
				CustomersDBLogic.removeCustomerByID(message);
				client.sendToClient("Employee was removed successfuly");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method show the order of home heating fuel for the customer by ID at the
	 * DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	private void showOrdersHomeHeatingFuelForCustomerIDFunctionality(String message, ConnectionToClient client) {
		// TODO Auto-generated method stub
		if (message.contains("showOrdersHomeHeatingFuelForCustomerID")) {
			try {
				client.sendToClient(
						OrderHomeHeatingFuelControllerServer.getOrdersHomeHeatingFuelForCustomerID(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method find the next ID inside the purchase home heating fuel table at
	 * the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void findTheNextIDInPurchaseHomeHeatingFuelFunctionality(String message, ConnectionToClient client) {
		if (message.equals("findTheNextIDInPurchaseHomeHeatingFuel"))
			try {
				client.sendToClient(OrderHomeHeatingFuelControllerServer.findTheNextIDInPurchaseHomeHeatingFuel());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	/**
	 * This method get the price of home heating fuel at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void priceOfHomeHeatingFuelFunctionality(String message, ConnectionToClient client) {
		if (message.equals("priceOfHomeHeatingFuel")) {
			try {
				client.sendToClient(UserControllerServer.priceOfHomeHeatingFuel());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method contains all the functions that operate the login
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void loginFunctionality(String message, ConnectionToClient client) {
		String[] strArray = message.split(" ");
		if (message.contains("LoginQuery")) {
			switch (strArray[0]) {
			case "LoginQueryUserExist":
				try {
					String ID = strArray[1];
					String password = strArray[2];
					client.sendToClient(UserControllerServer.LoginQueryUserExist(ID, password));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "UserLoginQueryPasswordCorrect":
				try {
					String ID = strArray[1];
					String password = strArray[2];
					client.sendToClient(UserControllerServer.UserLoginQueryPasswordCorrect(ID, password));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "EmployeeLoginQueryPasswordCorrect":
				try {
					String ID = strArray[1];
					String password = strArray[2];
					client.sendToClient(UserControllerServer.EmployeeLoginQueryPasswordCorrect(ID, password));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "UserLoginQueryEmailCorrect":
				try {
					String ID = strArray[1];
					String email = strArray[2];
					client.sendToClient(UserControllerServer.UserLoginQueryEmailCorrect(ID, email));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "EmployeeLoginQueryEmailCorrect":
				try {
					String ID = strArray[1];
					String email = strArray[2];
					client.sendToClient(UserControllerServer.EmployeeLoginQueryEmailCorrect(ID, email));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "EmployeeLoginQueryUpdateStockRequest":
				try {
					client.sendToClient(UserControllerServer.EmployeeLoginQueryUpdateStockRequest());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	/**
	 * This method checks if customer ID exist at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void checkIfCustomerIdExistsFunctionality(String message, ConnectionToClient client) {
		if (message.contains("checkIfCustomerIdExists")) {
			try {
				client.sendToClient(UserControllerServer.checkIfCustomerIdExists(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method checks if an email is exists inside the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void checkIfEmailExistsFunctionality(String message, ConnectionToClient client) {
		if (message.contains("checkIfEmailExists")) {
			try {
				client.sendToClient(UserControllerServer.checkIfEmailExists(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method check if credit card exist at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void checkIfCreditCardExistsFunctionality(String message, ConnectionToClient client) {
		if (message.contains("checkIfCreditCardExists")) {
			try {
				client.sendToClient(UserControllerServer.checkIfCreditCardExists(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method check if the license plate number exist at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void checkIfLicensePlateNumberExistsFunctionality(String message, ConnectionToClient client) {
		if (message.contains("checkIfLicensePlateNumberExists")) {
			try {
				client.sendToClient(UserControllerServer.checkIfLicensePlateNumberExists(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method gets the details of vehicles of customer at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void getDetailsOfVehiclesForCustomerIdFunctionality(String message, ConnectionToClient client) {
		if (message.contains("getVehiclesForCustomerId")) {
			try {
				client.sendToClient(UserControllerServer.getDetailsOfVehiclesForCustomerId(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method get all the gas companies at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void getAllGasCompaniesFunctionality(String message, ConnectionToClient client) {
		if (message.equals("getAllGasCompanies")) {
			try {
				client.sendToClient(UserControllerServer.getAllGasCompanies());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method insert new customer at the DB
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void insertNewCustomerToTheDbFunctionality(Customer message, ConnectionToClient client) {

		try {
			UserControllerServer.insertNewCustomerToTheDb(message);
			client.sendToClient("customer registered successfuly");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method contains all the functions that define the verification
	 * functionality
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void VerificationFunctionality(String message, ConnectionToClient client) {
		String[] strArray = message.split(" ");
		switch (strArray[0]) {
		case "updateSupplierVerification":
			try {
				String requestID = strArray[1];
				String chainName = strArray[2];
				String stationName = strArray[3];
				String fuelType;
				String quantityRequire;
				if (stationName.equals("tel")) {
					stationName = strArray[3] + " " + strArray[4];
					fuelType = strArray[5];
					quantityRequire = strArray[6];
				} else {
					fuelType = strArray[4];
					quantityRequire = strArray[5];
				}
				client.sendToClient(ClientRateController.updateSupplierrVerification(requestID, chainName, stationName,
						fuelType, quantityRequire));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "getVerification":
			try {
				String requestID = strArray[1];
				client.sendToClient(ClientRateController.getVerification(requestID));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "updateVerification":
			try {
				String requestID = strArray[1];
				String chainName = strArray[2];
				String fuelType = strArray[3];
				String newRate = strArray[4];
				client.sendToClient(
						ClientRateController.updateStationManagerVerification(requestID, chainName, fuelType, newRate));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "lastVerification":
			try {
				client.sendToClient(ClientRateController.getLastRequestID());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "removeVerification":
			try {
				String requestID = strArray[1];
				client.sendToClient(ClientRateController.removeVerification(requestID));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "verificationExist":
			try {
				String requestID = strArray[1];
				String chainName = strArray[2];
				client.sendToClient(ClientRateController.verificationExist(requestID, chainName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "verificationExistMarketingManager":
			try {
				String requestID = strArray[1];
				client.sendToClient(ClientRateController.verificationExistMarketingManager(requestID));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "disapproveVerification":
			try {
				String requestID = strArray[1];
				client.sendToClient(ClientRateController.disapproveVerification(requestID));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "approveVerification":
			try {
				String requestID = strArray[1];
				client.sendToClient(ClientRateController.approveVerification(requestID));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "showSupplierVerification":
			try {
				String employeeID = strArray[1];
				client.sendToClient(ClientRateController.showSupplierVerifications(employeeID));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "showVerification":
			try {
				String chainName = strArray[1];
				client.sendToClient(ClientRateController.showVerifications(chainName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "showMarketingManagerVerification":
			try {
				client.sendToClient(ClientRateController.showMarketingManagerVerifications());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "showChainManagerVerification":
			try {
				String chainName = strArray[1];
				client.sendToClient(ClientRateController.showChainManagerVerification(chainName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "ChainManagerNumOfWaitForApprovalRequests":
			try {
				String role = "marketing manager";
				String status = "wait for approval";
				String chainName = strArray[1];
				client.sendToClient(
						ClientRateController.ChainManagerGetNumOfWaitForApprovalRequests(chainName, status, role));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "MarketingManagerNumOfActionRequiredRequests":
			try {
				String role = "marketing manager";
				String status = "approved";
				client.sendToClient(ClientRateController.MarketingManagergetNumOfApprovedRequests(status, role));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "StationManagerNumOfActionRequiredRequests":
			try {
				String role = "supplier";
				String chain = strArray[1];
				String gasStation = strArray[2];
				String status = "wait for approval";
				client.sendToClient(ClientRateController.StationManagergetNumOfWaitForApprovalRequests(status, role,
						chain, gasStation));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "SupplierNumOfActionRequiredRequests":
			try {
				String role = "supplier";
				String status = "wait for approval";
				client.sendToClient(ClientRateController.SupplierNumOfWaitForApprovalRequests(status, role));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * This method contains all the functions that define the rate functionality
	 * 
	 * @param message - The message from the user
	 * @param client  - The client
	 */
	public void RateFunctionality(String message, ConnectionToClient client) {
		if (message.contains("getRate") || message.contains("setRate") || message.contains("sendRequest")) {
			String[] strArray = message.split(" ");
			int employeeID;
			String employeeName;
			String employeeRole;
			String chainName;
			String fuelType;
			String newRate;
			switch (strArray[0]) {
			case "getRate":
				try {
					chainName = strArray[1];
					fuelType = strArray[2];
					client.sendToClient(ClientRateController.getRate(chainName, fuelType));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "updateRate":
				chainName = strArray[1];
				fuelType = strArray[2];
				newRate = strArray[3];
				try {
					client.sendToClient(ClientRateController.updateRate(chainName, fuelType, newRate));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case "sendRequest":
				employeeID = Integer.parseInt(strArray[1]);
				employeeName = strArray[2] + " " + strArray[3];
				if (strArray.length == 9) // If we got 2 words in role.
				{
					employeeRole = strArray[4] + " " + strArray[5];
					chainName = strArray[6];
					fuelType = strArray[7];
					newRate = strArray[8];
				} else {
					employeeRole = strArray[4];
					chainName = strArray[5];
					fuelType = strArray[6];
					newRate = strArray[7];
				}
				try {
					client.sendToClient(ClientRateController.sendRequest(employeeID, employeeName, employeeRole,
							chainName, fuelType, newRate));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

}
