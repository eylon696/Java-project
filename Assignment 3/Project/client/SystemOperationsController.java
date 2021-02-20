package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * The class SystemOperationsController defined how the System Operation would
 * work
 * 
 * @author Shoval David
 *
 */
public class SystemOperationsController implements Initializable {
	@FXML
	private AnchorPane MyAnchorPane;

	@FXML
	private Button X;

	@FXML
	private Button Minus;

	@FXML
	private Button CustomerRegistration;

	@FXML
	private Button CarAdditionAffiliation;

	@FXML
	private Button MakingReport;

	@FXML
	private Button Rates;

	@FXML
	private Button Verifications;

	@FXML
	private Button UpdateCustomerDetails;

	@FXML
	private Button showPurchasePlans;

	@FXML
	private Button RemoveCarAffiliation;
	@FXML
	private Button showReports;

	@FXML
	private Button Sales;

	@FXML
	private Button DefinePatterns;

	@FXML
	private Button OperationTracking;

	@FXML
	private Button ShowAndUpdateSales;

	@FXML
	private Button PurchasesReport;

	@FXML
	private Button ProduceReports;

	@FXML
	private Label numVerificationNotification;
	@FXML
	private Pane MyPane;

	public static String verificationForm;

	public static boolean flagIfCameFromSystemOperationsMarketingManager = false;

	/**
	 * This function exit the current scene (exit the application)
	 * 
	 * @param event , the event we handle
	 */
	@FXML
	private void exit(ActionEvent event) {
		FunctionForGUI.exit(event);
	}

	/**
	 * This function sends the user to the previous page
	 * 
	 * @throws IOException
	 */
	@FXML
	private void minimize(ActionEvent event) {
		FunctionForGUI.minimizeAnchorPane(event, MyAnchorPane);
	}

	/**
	 * The function transfer us to rates after clicking it inside the system
	 * operation
	 */
	@FXML
	private void clickRates() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/Rates.fxml"));
			MyPane.getChildren().clear();
			MyPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The function transfer us to verification after clicking it inside the system
	 * operation
	 */
	@FXML
	private void clickVarification() {
		setVerificationForm();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(verificationForm));
			MyPane.getChildren().clear();
			MyPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function sets the variable , verificationForm based on the employee role
	 */
	public void setVerificationForm() {
		switch (UserLoginController.employee.getRole()) {
		case "chain manager":
			verificationForm = "/gui/VerificationChainManager.fxml";
			break;
		case "marketing manager":
			verificationForm = "/gui/VerificationMarketingManager.fxml";
			break;
		case "supplier":
			verificationForm = "/gui/VerificationSupplier.fxml";
			break;
		case "station manager":
			verificationForm = "/gui/VerificationStationManager.fxml";
			break;
		}
	}

	/**
	 * The function transfer us to "show and update sales form" after clicking it
	 * inside the system operation
	 */
	@FXML
	void goToShowAndUpdateSalesForm(MouseEvent event) {
		changeScreen("/gui/ShowSalesAndUpdate.fxml");
	}

	/**
	 * The function transfer us to "produce reports" after clicking it inside the
	 * system operation
	 */
	@FXML
	void goToProduceReports(MouseEvent event) {
		changeScreen("/gui/ProduceReports.fxml");
	}

	/**
	 * The function transfer us to "customer registration" after clicking it inside
	 * the system operation
	 */
	@FXML
	public void goToCustomerRegistrationForm(MouseEvent event) {
		CustomerRegistrationControllerClient.customerToRegister = null;
		changeScreen("/gui/CustomerRegistrationForm.fxml");
	}

	/**
	 * The function transfer us to "update and show purchase plan form" after
	 * clicking it inside the system operation
	 */
	@FXML
	public void goToUpdateAndShowPurchasePlanForm(MouseEvent event) {
		changeScreen("/gui/ShowAndUpdatePurchasePlanForm.fxml");
	}

	/**
	 * The function transfer us to "threshold level" after clicking it inside the
	 * system operation
	 */
	@FXML
	public void goToThresholdLevelForm(MouseEvent event) {
		changeScreen("/gui/ShowProductsInGasStationUpdateThresholdLevel.fxml");
	}

	/**
	 * The function transfer us to "car registration form" after clicking it inside
	 * the system operation
	 */
	@FXML
	void goToCarRegistrationForm(MouseEvent event) {
		CustomerRegistrationControllerClient.customerToRegister = null;
		changeScreen("/gui/CarAffiliationForm.fxml");
	}

	/**
	 * The function transfer us to "define patterns form" after clicking it inside
	 * the system operation
	 */
	@FXML
	void goToDefinePatternsForm(MouseEvent event) {
		changeScreen("/gui/SalePattern.fxml");
	}

	/**
	 * The function transfer us to "operation tracking form" after clicking it
	 * inside the system operation
	 */
	@FXML
	void goToOperationTrackingForm(MouseEvent event) {
		changeScreen("/gui/OperationTracking.fxml");
	}

	/**
	 * The function transfer us to "show and edit customers details" after clicking
	 * it inside the system operation
	 */
	@FXML
	void goToShowAndEditCustomersDetails(MouseEvent event) {
		changeScreen("/gui/ShowRemoveUpdateCustomersForm.fxml");
	}

	/**
	 * The function transfer us to "show purchase plans" after clicking it inside
	 * the system operation
	 */
	@FXML
	void goToShowPurchasePlans(MouseEvent event) {
		changeScreen("/gui/ShowPurchasePlansForm.fxml");
	}

	/**
	 * The function transfer us to "show fueling purchases form" after clicking it
	 * inside the system operation
	 */
	@FXML
	void goToShowFuelingPurchasesForm(MouseEvent event) {
		changeScreen("/gui/ShowFuelingPurchases.fxml");
	}

	/**
	 * The function transfer us to "sales form" after clicking it inside the system
	 * operation
	 */
	@FXML
	void goToSalesForm(MouseEvent event) {
		flagIfCameFromSystemOperationsMarketingManager = true;
		changeScreen("/gui/SalesForm.fxml");
	}

	/**
	 * The function transfer us to "show vehicle" after clicking it inside the
	 * system operation
	 */
	@FXML
	void goToShowShowVehicle(MouseEvent event) {
		changeScreen("/gui/ShowVehicle.fxml");
	}

	/**
	 * The function transfer us to "show rates" after clicking it inside the system
	 * operation
	 */
	@FXML
	void goToShowRates() {
		changeScreen("/gui/ShowRates.fxml");
	}

	/**
	 * The function transfer us to "report form" after clicking it inside the system
	 * operation
	 */
	@FXML
	void showReportsForm(MouseEvent event) {
		changeScreen("/gui/ShowReportsForChainManagerForm.fxml");
	}

	/**
	 * The function transfer us to "making report" after clicking it inside the
	 * system operation
	 */
	@FXML
	void goToMakingReportsForm(MouseEvent event) {
		changeScreen("/gui/MakingReportsForm.fxml");
	}

	/**
	 * The function transfer us to "show employee form" after clicking it inside the
	 * system operation
	 */
	@FXML
	void goToShowEmployeesForm(MouseEvent event) {
		changeScreen("/gui/ShowEmployeesForm.fxml");
	}

	/**
	 * The function transfer us to "show home heating fuel purchase" after clicking
	 * it inside the system operation
	 */
	@FXML
	void goToShowHomeHeatingFuelPurchases(MouseEvent event) {
		changeScreen("/gui/ShowHomeHeatingFuelPurchasesForm.fxml");
	}

	/**
	 * This function change the current screen to the FXML
	 * 
	 * @param fxml - the FXML name
	 */
	public void changeScreen(String fxml) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			MyPane.getChildren().clear();
			MyPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method initialize the System operations form
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if (UserLoginController.employee != null) {
			String chain = UserLoginController.employee.getOrganizationalAffiliation();
			String role = UserLoginController.employee.getRole();
			String gasStation = UserLoginController.employee.getGasStation();

			String strNumOfRequests = "";
			int numOfRequests = 0;

			switch (role) {
			case "chain manager":
				ClientUI.chat.accept("ChainManagerNumOfWaitForApprovalRequests " + chain);
				break;
			case "marketing manager":
				ClientUI.chat.accept("MarketingManagerNumOfActionRequiredRequests");
				break;
			case "station manager":
				ClientUI.chat.accept("StationManagerNumOfActionRequiredRequests " + chain + " " + gasStation);
				break;
			case "supplier":
				ClientUI.chat.accept("SupplierNumOfActionRequiredRequests");
				break;
			}
			if (!UserLoginController.employee.getRole().equals("marketing representative")) {
				numOfRequests = (Integer) MyFuelClient.msgFromServer;
				strNumOfRequests = numOfRequests > 3 ? "3+" : numOfRequests == 0 ? "" : String.valueOf(numOfRequests);
				numVerificationNotification.setText(strNumOfRequests);
			}
		}
	}
}
