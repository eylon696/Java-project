package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.Employee;
import entity.Verification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * The class VarificationController defined how the Verification window would
 * work
 * 
 * @author Shoval David
 *
 */
public class VarificationController implements Initializable {
	Employee employee = UserLoginController.employee;

	private Verification[] arrVerification;
	ObservableList<Verification> arrListVerification = FXCollections.observableArrayList();
	@FXML
	private TableView<Verification> myTable;
	@FXML
	private TableColumn<Verification, Integer> colRequestID;
	@FXML
	private TableColumn<Verification, Integer> colEmployeeID;
	@FXML
	private TableColumn<Verification, String> colName;
	@FXML
	private TableColumn<Verification, String> colRole;
	@FXML
	private TableColumn<Verification, String> colOrganizationalAffiliation;
	@FXML
	private TableColumn<Verification, String> colStation;
	@FXML
	private TableColumn<Verification, String> colStatus;
	@FXML
	private TableColumn<Verification, String> colDescription;

	@FXML
	private Button X;
	@FXML
	private Button Minus;
	@FXML
	private Button Back;
	@FXML
	private Button Approve;
	@FXML
	private Button Update;
	@FXML
	private Button Remove;
	@FXML
	private TextField RequestID;
	@FXML
	private Pane MyPane;
	@FXML
	private Label RequestIDLable;

	/**
	 * This function exit the current scene (exit the application)
	 * 
	 * @param event , the event we handle
	 */
	@FXML
	void exit(ActionEvent event) {
		FunctionForGUI.exit(event);
	}

	/**
	 * This function sends the user to the previous page
	 * 
	 * @throws IOException
	 */
	@FXML
	void minimize(ActionEvent event) {
		FunctionForGUI.minimizePane(event, MyPane);
	}

	/**
	 * This function sends the user back to the system operation window
	 */
	@FXML
	void Back() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(HomePageController.SOstring));
			MyPane.getChildren().clear();
			MyPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function initialize the verification screen when it opened
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		colRequestID.setCellValueFactory(new PropertyValueFactory<Verification, Integer>("RequestID"));
		colEmployeeID.setCellValueFactory(new PropertyValueFactory<Verification, Integer>("EmployeeID"));
		colName.setCellValueFactory(new PropertyValueFactory<Verification, String>("Name"));
		colRole.setCellValueFactory(new PropertyValueFactory<Verification, String>("Role"));
		colOrganizationalAffiliation
				.setCellValueFactory(new PropertyValueFactory<Verification, String>("OrganizationalAffiliation"));
		colStation.setCellValueFactory(new PropertyValueFactory<Verification, String>("Station"));
		colStatus.setCellValueFactory(new PropertyValueFactory<Verification, String>("Status"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Verification, String>("Description"));
		showVarifications();
		HomePageController.checkIfToSetRequestForSupplier();
	}

	/**
	 * This function show all the verification of the particular employee role
	 */
	public void showVarifications() {
		if (employee.getRole().equals("station manager"))
			ClientUI.chat.accept("showVerification " + employee.getOrganizationalAffiliation());
		else if (employee.getRole().equals("chain manager"))
			ClientUI.chat.accept("showChainManagerVerification " + employee.getOrganizationalAffiliation());
		else if (employee.getRole().equals("supplier"))
			ClientUI.chat.accept("showSupplierVerification " + employee.getEmployeeID());
		else // We are on marketing manager
			ClientUI.chat.accept("showMarketingManagerVerification");

		arrVerification = (Verification[]) MyFuelClient.msgFromServer;
		setTableToRightRole(arrVerification);
		myTable.setItems(arrListVerification);
	}

	/**
	 * This function sets the table to the correct verification depends on the
	 * employee role
	 * 
	 * @param arrVerification - array of verification of the particular employee
	 */
	void setTableToRightRole(Verification[] arrVerification) {
		int i;
		switch (employee.getRole()) {
		case "chain manager":
			for (i = 0; i < arrVerification.length; i++) {
				if (arrVerification[i].getOrganizationalAffiliation().equals(employee.getOrganizationalAffiliation()))
					arrListVerification.add(arrVerification[i]);
			}
			break;
		case "marketing manager":
			for (i = 0; i < arrVerification.length; i++) {
				if (arrVerification[i].getRole().equals(employee.getRole()))
					arrListVerification.add(arrVerification[i]);
			}
			break;
		case "station manager":
			for (i = 0; i < arrVerification.length; i++) {
				if (arrVerification[i].getStation().equals(employee.getGasStation()))
					arrListVerification.add(arrVerification[i]);
			}
			break;
		case "supplier":
			for (i = 0; i < arrVerification.length; i++) {
				if (arrVerification[i].getRole().equals(employee.getRole()))
					arrListVerification.add(arrVerification[i]);
			}
			break;
		}
	}

	/**
	 * This function update specific verification by the marketing manager
	 */
	@FXML
	public void UpdateForMarketingManager() {
		subFunctionForRemoveAndApprove("update", "updated");
		refreshScreen();
	}

	/**
	 * This function approve the verification
	 */
	@FXML
	public void Approve() {
		subFunctionForRemoveAndApprove("approve", "approved");
		refreshScreen();
	}

	/**
	 * This function remove the verification
	 */
	@FXML
	public void Remove() {
		subFunctionForRemoveAndApprove("remove", "removed");
		refreshScreen();
	}

	/**
	 * This function refresh the current screen
	 */
	public void refreshScreen() {
		arrListVerification.clear();
		showVarifications();
		RequestID.setText("");
	}

	/**
	 * This function is a sub function for remove/approve/update verification
	 * 
	 * @param actionForServer - The action i want to take: remove/approve/update
	 * @param actionForAlert  - parameter for the alert window
	 */
	public void subFunctionForRemoveAndApprove(String actionForServer, String actionForAlert) {
		String role = "";
		if (RequestID.getText().isEmpty()) // If the text field is empty , or the user did not selected row in the
											// table.
			RequestIDLable.setText("* You must choose request id");
		else {
			RequestIDLable.setText("");
			if ((employee.getRole().equals("marketing manager") || employee.getRole().equals("supplier"))
					&& !checkIfVarificationExistMarketingManager())
				FunctionForGUI.showAlert("Error", "Please enter valid request id.");
			else if (!employee.getRole().equals("marketing manager") && !employee.getRole().equals("supplier")
					&& !checkIfVarificationExist()) // If the request id inside the text field not exist.
				FunctionForGUI.showAlert("Error", "Please enter valid request id.");
			else {
				ClientUI.chat.accept("getVerification " + RequestID.getText());
				Verification verification = (Verification) MyFuelClient.msgFromServer;
				boolean operationSucceed;
				if (actionForServer.equals("update")) // If the marketing manager want to update his rate (must update).
				{
					String[] description = verification.getDescription().split(" ");
					String chainName = verification.getOrganizationalAffiliation();
					role = employee.getRole();
					if (role.equals("supplier")) // If i am supplier
					{
						String quantityRequire = description[1];
						String fuelType = description[4];
						String stationName = verification.getStation();
						ClientUI.chat.accept("updateSupplierVerification " + RequestID.getText() + " " + chainName + " "
								+ stationName + " " + fuelType + " " + quantityRequire);
					} else // Its update for gas marketing manager.
					{
						String fuelType = description[1];
						String newRate = description[5];
						ClientUI.chat.accept(actionForServer + "Verification " + RequestID.getText() + " " + chainName
								+ " " + fuelType + " " + newRate);
					}
				} else
					ClientUI.chat.accept(actionForServer + "Verification " + RequestID.getText());
				operationSucceed = (boolean) MyFuelClient.msgFromServer;
				if (operationSucceed) // Is always succeed , if it fail it because the SQL, not if i update
										// verification with status Approve again.
					FunctionForGUI.showAlert("Succeed",
							"You have " + actionForAlert + " request number : " + RequestID.getText());
				else {
					if (actionForServer.equals("approve") && verification.getStatus().equals("disapproved"))
						FunctionForGUI.showAlert("Error",
								"You have already disapproved request number : " + RequestID.getText());
					else if (actionForServer.equals("approve"))
						FunctionForGUI.showAlert("Error",
								"You have already " + actionForAlert + " request number : " + RequestID.getText());
					else if (actionForServer.equals("update") && role.equals("supplier")
							&& verification.getStatus().equals("supplied"))
						FunctionForGUI.showAlert("Error",
								"Request number " + RequestID.getText() + " has already supplied.");
					else if (actionForServer.equals("update") && role.equals("supplier")
							&& verification.getStatus().equals("disapproved"))
						FunctionForGUI.showAlert("Error",
								"Request number " + RequestID.getText() + " has been disapproved.");
					else // actionForServer = update for station manager.
						FunctionForGUI.showAlert("Error", "Request number " + RequestID.getText()
								+ " Does not approved yet, please wait until it is approved.");
				}
			}
		}
	}

	/**
	 * This function disapprove verification
	 */
	@FXML
	public void Disapprove() {
		if (RequestID.getText().isEmpty()) // If the text field is empty , or the user did not selected row in the
											// table.
			FunctionForGUI.showAlert("Error", "Please enter request id.");
		else if (!checkIfVarificationExist())
			FunctionForGUI.showAlert("Error", "Please enter valid request id.");
		else {
			ClientUI.chat.accept("getVerification " + RequestID.getText()); // Made it to get the fuelType and newRate
																			// data.
			Verification verification = (Verification) MyFuelClient.msgFromServer;
			ClientUI.chat.accept("disapproveVerification " + RequestID.getText());
			boolean operationSucceed = (boolean) MyFuelClient.msgFromServer;
			if (operationSucceed) {
				FunctionForGUI.showAlert("Succeed", "You have disapproved request number : " + RequestID.getText());
				refreshScreen();
			} else {
				if (verification.getStatus().equals("disapproved"))
					FunctionForGUI.showAlert("Error",
							"You have already disapproved request number : " + RequestID.getText());
				else
					FunctionForGUI.showAlert("Error",
							"You have already approved request number : " + RequestID.getText());
			}
		}
	}

	/**
	 * This function get the last verification ID inside the table
	 * 
	 * @return the last verification index
	 */
	public int getLastVerificationRequestID() {
		int lastRequestID;
		ClientUI.chat.accept("lastVerification");
		lastRequestID = (Integer) MyFuelClient.msgFromServer;
		return lastRequestID;
	}

	/**
	 * This function checks if the verification exist inside the DB
	 * 
	 * @return true if exist false if not
	 */
	public boolean checkIfVarificationExist() {
		boolean varificationExist;
		ClientUI.chat
				.accept("verificationExist " + RequestID.getText() + " " + employee.getOrganizationalAffiliation());
		varificationExist = (boolean) MyFuelClient.msgFromServer;
		return varificationExist;
	}

	/**
	 * Check if verification exist in the side of the marketing manager
	 * 
	 * @return true if exist false if not
	 */
	public boolean checkIfVarificationExistMarketingManager() {
		boolean varificationExist;
		ClientUI.chat.accept("verificationExistMarketingManager " + RequestID.getText());
		varificationExist = (boolean) MyFuelClient.msgFromServer;
		return varificationExist;
	}

	/**
	 * This function set the requestID text field to the row selected at the table
	 */
	@FXML
	public void setTextIfRowSelected() {
		Verification verification = myTable.getSelectionModel().getSelectedItem();
		if (verification != null)
			RequestID.setText(String.valueOf(verification.getRequestID()));
	}

	/**
	 * This function set only numbers inside the requestID text field
	 */
	@FXML
	public void onlyNumbers(KeyEvent e) {
		String c = e.getCharacter();
		if (!c.matches("[0-9]+")) {
			e.consume();
		}
	}

}
