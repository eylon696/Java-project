package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;
import client.ClientUI;
import entity.Customer;
import entity.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The class HomePageController defined how the homePage would work
 * 
 * @author Shoval David
 *
 */
public class HomePageController implements Initializable {
	Customer customer = UserLoginController.customer;
	Employee employee = UserLoginController.employee;
	Stage stage = UserLoginController.stage;
	static String SOstring;

	@FXML
	Label UserName;
	@FXML
	Label Role;
	@FXML
	Button Minus;
	@FXML
	Button X;
	@FXML
	Pane MyPane;
	@FXML
	VBox MyVbox;
	@FXML
	Button Home;
	@FXML
	Button SystemOperations;
	@FXML
	Button HomeHeatingFuel;
	@FXML
	Button About;
	@FXML
	Button Logout;
	@FXML
	Button SearchIconButtton;
	@FXML
	TextField SearchText;

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
		FunctionForGUI.minimizePane(event, MyPane);
	}

	/**
	 * This function makes the user logout of the system
	 * 
	 * @param event - the event we handle
	 * @throws IOException
	 */
	public void Logout(ActionEvent event) throws IOException {
		try {
			if (employee != null) {
				ClientUI.chat.accept("RemoveUserFromArrayList " + employee.getEmployeeID());
				employee = null;
				customer = null;
				UserLoginController.employee = null;
			} else if (customer != null) {

				ClientUI.chat.accept("RemoveUserFromArrayList " + customer.getID());
				customer = null;
				UserLoginController.customer = null;
			}
			Parent root = FXMLLoader.load(getClass().getResource("/gui/UserLogin.fxml"));
			Scene scene = new Scene(root);
			FunctionForGUI functionForGUI = new FunctionForGUI();
			functionForGUI.MakeScreenDragble(root, stage);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function sets the static variable SOstring to the right value depending
	 * on the employee role
	 */
	public void SystemOperations() {
		switch (employee.getRole().toLowerCase()) {
		case "chain manager":
			changeScreen("/gui/SystemOperationsChainManager.fxml");
			SOstring = "/gui/SystemOperationsChainManager.fxml";
			break;
		case "station manager":
			changeScreen("/gui/SystemOperationStationManager.fxml");
			SOstring = "/gui/SystemOperationStationManager.fxml";
			break;
		case "marketing manager":
			changeScreen("/gui/SystemOperationsMarketingManager.fxml");
			SOstring = "/gui/SystemOperationsMarketingManager.fxml";
			break;
		case "marketing representative":
			changeScreen("/gui/SystemOperationMarketingRepresentative.fxml");
			SOstring = "/gui/SystemOperationMarketingRepresentative.fxml";
			break;
		case "supplier":
			changeScreen("/gui/SystemOperationSupplier.fxml");
			SOstring = "/gui/SystemOperationSupplier.fxml";
			break;
		}
	}

	/**
	 * This function switches to the screen Home
	 */
	public void Home() {
		changeScreen("/gui/Home.fxml");
	}

	/**
	 * This function switches to the screen HomeHeatingFuel
	 */
	public void HomeHeatingFuel() {
		changeScreen("/gui/HomeHeatingFuel.fxml");
	}

	/**
	 * This function switches to the screen About
	 */
	public void About() {
		changeScreen("/gui/AboutForm.fxml");
	}

	/**
	 * This function change the screen in the given FXML name
	 * 
	 * @param fxml - the FXML name
	 */
	public void changeScreen(String fxml) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			MyVbox.getChildren().clear();
			MyVbox.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This function initialize the HomePage screen when it opened
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		changeScreen("/gui/Home.fxml");
		setTextAtUserName();
		buttonEnterPressed();
		switch (UserLoginController.homePageFxmlName) {
		case "/gui/HomePageCustomer.fxml":
			customerAutoCompletionText();
			break;
		case "/gui/HomePageEmployee.fxml":
			employeeAutoCompletionText();
			break;
		case "/gui/HomePageEmployeeAndCustomer.fxml":
			employeeAndCustomerAutoCompletionText();
			break;
		}
	}

	/**
	 * This function sets the name of the user
	 */
	private void setTextAtUserName() {
		if (UserLoginController.employee != null) // if the user is employee add this text to the label over the user.
		{
			UserName.setText(employee.getFirstName() + " " + employee.getLastName());
			Role.setText(employee.getRole());
		} else // if the user is custom add this text to the label over the user.
		{
			UserName.setText(customer.getFirstName() + " " + customer.getLastName());
			Role.setText("Customer");
		}
	}

	/**
	 * This function set a request for the station manager if the stock is under the
	 * threshold level
	 */
	public static void checkIfToSetRequestForSupplier() {
		String str = "EmployeeLoginQueryUpdateStockRequest";
		ClientUI.chat.accept(str);
	}

	/**
	 * This function search inside the GUI the value inserted in the search field
	 */
	@FXML
	public void Search() {
		String homePageFxmlName = UserLoginController.homePageFxmlName;
		String textEntered = SearchText.getText().toLowerCase();
		switch (homePageFxmlName) {
		case "/gui/HomePageCustomer.fxml":
			SearchCustomer(textEntered);
			break;
		case "/gui/HomePageEmployee.fxml":
			SearchEmployee(textEntered);
			break;
		case "/gui/HomePageEmployeeAndCustomer.fxml":
			SearchCustomerWithoutHomeAboutCases(textEntered);
			SearchEmployee(textEntered);
			break;
		}
	}

	/**
	 * This function search inside the customer GUI the text that entered by the
	 * user
	 * 
	 * @param textEntered - the text the user gave
	 */
	private void SearchCustomer(String textEntered) {
		switch (textEntered) {
		case "home":
			changeScreen("/gui/Home.fxml");
			break;
		case "home heating fuel":
			changeScreen("/gui/HomeHeatingFuel.fxml");
			break;
		case "purchase":
			changeScreen("/gui/PurchaseHomeHeatingFuelForm.fxml");
			break;
		case "track order":
			changeScreen("/gui/TrackOrderHomeHeatingFuelForm.fxml");
			break;
		case "about":
			changeScreen("/gui/AboutForm.fxml");
			break;
		}
	}

	/**
	 * This function search inside the customer GUI without the about and home
	 * screens
	 * 
	 * @param textEntered - the text the user gave
	 */
	private void SearchCustomerWithoutHomeAboutCases(String textEntered) {
		switch (textEntered) {
		case "home heating fuel":
			changeScreen("/gui/HomeHeatingFuel.fxml");
			break;
		case "purchase":
			changeScreen("/gui/PurchaseHomeHeatingFuelForm.fxml");
			break;
		case "track order":
			changeScreen("/gui/TrackOrderHomeHeatingFuelForm.fxml");
			break;
		}
	}

	/**
	 * This function search in particular role the text the employee entered
	 * 
	 * @param textEntered - the text the user gave
	 */
	private void SearchEmployee(String textEntered) {
		SearchEmployeeChainManager(textEntered);
		SearchEmployeeMarketingManager(textEntered);
		SearchEmployeeMarketingRepresentative(textEntered);
		SearchEmployeeStationManager(textEntered);
		SearchEmployeeSupplier(textEntered);
	}

	/**
	 * This function search at the GUI of the chain manager
	 * 
	 * @param textEntered - the text the user gave
	 */
	private void SearchEmployeeChainManager(String textEntered) {
		switch (textEntered) {
		case "home":
			changeScreen("/gui/Home.fxml");
			break;
		case "verifications":
			changeScreen("/gui/VerificationChainManager.fxml");
			break;
		case "show rates":
			changeScreen("/gui/ShowRates.fxml");
			break;
		case "show reports":
			changeScreen("/gui/ShowReportsForChainManagerForm.fxml");
			break;
		case "show fueling purchases":
			changeScreen("/gui/ShowFuelingPurchases.fxml");
			break;
		case "quarterly revenue":
			changeScreen("/gui/ShowQuarterlyRevenueReportForm.fxml");
			break;
		case "show purchases report":
			changeScreen("/gui/ShowPurchasesReportForm.fxml");
			break;
		case "quantity of item in stock":
			changeScreen("/gui/ShowQuantityInStockReportForm.fxml");
			break;
		case "about":
			changeScreen("/gui/AboutForm.fxml");
			break;
		}
	}

	/**
	 * This function search at the GUI of the marketing manager
	 * 
	 * @param textEntered - the text the user gave
	 */
	private void SearchEmployeeMarketingManager(String textEntered) {
		switch (textEntered) {
		case "home":
			changeScreen("/gui/Home.fxml");
			break;
		case "rates":
			changeScreen("/gui/Rates.fxml");
			break;
		case "verifications":
			changeScreen("/gui/VerificaionMarketingManager.fxml");
			break;
		case "show employees":
			changeScreen("/gui/ShowEmployeesForm.fxml");
			break;
		case "sales":
			SystemOperationsController.flagIfCameFromSystemOperationsMarketingManager = true;
			changeScreen("/gui/SalesForm.fxml");
			break;
		case "define pattern":
			changeScreen("/gui/SalePattern.fxml");
			break;
		case "operation tracking":
			changeScreen("/gui/OperationTracking.fxml");
			break;
		case "show home heating fuel purchases":
			changeScreen("/gui/ShowHomeHeatingFuelPurchasesForm.fxml");
			break;
		case "show and update sales":
			changeScreen("/gui/ShowSalesAndUpdate.fxml");
			break;
		case "produce reports":
			changeScreen("/gui/ProduceReports.fxml");
			break;
		case "show sale comments report":
			changeScreen("/gui/SaleCommentsReport.fxml");
			break;
		case "customer periodic characterization report":
			changeScreen("/gui/PeriodicCharacterizationReportForm.fxml");
			break;
		case "about":
			changeScreen("/gui/AboutForm.fxml");
			break;
		}
	}

	/**
	 * This function search at the GUI of the marketing representative
	 * 
	 * @param textEntered - the text the user gave
	 */
	private void SearchEmployeeMarketingRepresentative(String textEntered) {
		switch (textEntered) {
		case "home":
			changeScreen("/gui/Home.fxml");
			break;
		case "customer registration":
			changeScreen("/gui/CustomerRegistrationForm.fxml");
			break;
		case "car addition and affiliation":
			changeScreen("/gui/CarAffiliationForm.fxml");
			break;
		case "show and update purchase plan":
			changeScreen("/gui/ShowAndUpdatePurchasePlanForm.fxml");
			break;
		case "show and edit customer details":
			changeScreen("/gui/ShowRemoveUpdateCustomersForm.fxml");
			break;
		case "show and remove car affiliation":
			changeScreen("/gui/ShowVehicle.fxml");
			break;
		case "about":
			changeScreen("/gui/AboutForm.fxml");
			break;
		}
	}

	/**
	 * This function search at the GUI of the station manager
	 * 
	 * @param textEntered - the text the user gave
	 */
	private void SearchEmployeeStationManager(String textEntered) {
		switch (textEntered) {
		case "home":
			changeScreen("/gui/Home.fxml");
			break;
		case "determine threshold level":
			changeScreen("/gui/ShowProductsInGasStationUpdateThresholdLevel.fxml");
			break;
		case "verifications":
			changeScreen("/gui/VerificaionStationManager.fxml");
			break;
		case "making report":
			changeScreen("/gui/MakingReportsForm.fxml");
			break;
		case "quarterly revenue":
			changeScreen("/gui/MakingQuarterlyRevenueReportForm.fxml");
			break;
		case "purchases report":
			changeScreen("/gui/PurchasesReportForm.fxml");
			break;
		case "quantity of item in stock":
			changeScreen("/gui/MakingQuantityOfItemsInStockReportForm.fxml");
			break;
		case "about":
			changeScreen("/gui/AboutForm.fxml");
			break;
		}
	}

	/**
	 * This function search at the GUI of the supplier
	 * 
	 * @param textEntered - the text the user gave
	 */
	private void SearchEmployeeSupplier(String textEntered) {
		switch (textEntered) {
		case "home":
			changeScreen("/gui/Home.fxml");
			break;
		case "verifications":
			changeScreen("/gui/VerificationSupplier.fxml");
			break;
		case "about":
			changeScreen("/gui/AboutForm.fxml");
			break;
		}
	}

	/**
	 * This function makes auto completion for the customer
	 */
	private void customerAutoCompletionText() {
		TextFields.bindAutoCompletion(SearchText, "home", "home heating fuel", "purchase", "track order", "about");
	}

	/**
	 * This function makes auto completion for the employee, taking the role as case
	 */
	private void employeeAutoCompletionText() {
		switch (employee.getRole()) {
		case "chain manager":
			TextFields.bindAutoCompletion(SearchText, "home", "verifications", "show rates", "show reports",
					"show fueling purchases", "quarterly revenue", "show purchases report", "quantity of item in stock",
					"about");
			break;
		case "marketing manager":
			TextFields.bindAutoCompletion(SearchText, "home", "rates", "verifications", "show employees", "sales",
					"define pattern", "operation tracking", "show home heating fuel purchases", "show and update sales",
					"produce reports", "show sale comments report", "customer periodic characterization report",
					"about");
			break;
		case "marketing representative":
			TextFields.bindAutoCompletion(SearchText, "home", "customer registration", "car addition and affiliation",
					"show and update purchase plan", "show and edit customer details",
					"show and remove car affiliation", "about");
			break;
		case "station manager":
			TextFields.bindAutoCompletion(SearchText, "home", "determine threshold level", "verifications",
					"making report", "quarterly revenue", "purchases report", "quantity of item in stock", "about");
			break;
		case "supplier":
			TextFields.bindAutoCompletion(SearchText, "home", "verifications", "about");
			break;
		}
	}

	/**
	 * This function makes auto completion for employee and customer together,
	 * taking the role as case
	 */
	private void employeeAndCustomerAutoCompletionText() {
		switch (employee.getRole()) {
		case "chain manager":
			TextFields.bindAutoCompletion(SearchText, "home", "verifications", "show rates", "show reports",
					"show fueling purchases", "quarterly revenue", "show purchases report", "quantity of item in stock",
					"about", "home heating fuel", "purchase", "track order");
			break;
		case "marketing manager":
			TextFields.bindAutoCompletion(SearchText, "home", "rates", "verifications", "show employees", "sales",
					"define pattern", "operation tracking", "show home heating fuel purchases", "show and update sales",
					"produce reports", "show sale comments report", "customer periodic characterization report",
					"about", "home heating fuel", "purchase", "track order");
			break;
		case "marketing representative":
			TextFields.bindAutoCompletion(SearchText, "home", "customer registration", "car addition and affiliation",
					"show and update purchase plan", "show and edit customer details",
					"show and remove car affiliation", "about", "home heating fuel", "purchase", "track order");
			break;
		case "station manager":
			TextFields.bindAutoCompletion(SearchText, "home", "determine threshold level", "verifications",
					"making report", "quarterly revenue", "purchases report", "quantity of item in stock", "about",
					"home heating fuel", "purchase", "track order");
			break;
		case "supplier":
			TextFields.bindAutoCompletion(SearchText, "home", "verifications", "about", "home heating fuel", "purchase",
					"track order");
			break;
		}
	}

	/**
	 * This function. after selecting one of the options from the completing text by
	 * clicking enter the search function will work
	 */
	public void buttonEnterPressed() {
		SearchText.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER)
				Search();
		});
	}
}
