package client;

import java.io.IOException;

import client.ClientUI;
import client.MyFuelClient;
import entity.Employee;
import entity.Rates;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * The class RatesController defined how the Rates would work
 * 
 * @author Shoval David
 *
 */
public class RatesController {

	private Employee employee = UserLoginController.employee;
	private Double fuelPrice = null;

	public static final Double benzeneRate = 30.0;
	public static final Double dieselRate = 20.0;
	public static final Double scooterRate = 10.0;

	@FXML
	private Button X;
	@FXML
	private Button Minus;
	@FXML
	private ToggleGroup fuelTypeGroup;
	@FXML
	private RadioButton Benzene;
	@FXML
	private RadioButton Diesel;
	@FXML
	private RadioButton Scooter;
	@FXML
	private TextField MaximumPrice;
	@FXML
	private TextField RateCurrentPaz;
	@FXML
	private TextField RateCurrentTen;
	@FXML
	private TextField RateCurrentDelek;
	@FXML
	private TextField NewRate;
	@FXML
	private Button ButtonBack;
	@FXML
	private Button ButtonSendRequest;
	@FXML
	private Pane MyPane;
	@FXML
	private Label FuelTypeLabel;
	@FXML
	private Label NewRateLabel;

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
	 * 
	 * @param event - the event we handle
	 */
	@FXML
	void Back(ActionEvent event) {
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
	 * This function send new rate request to the chain manager
	 */
	@FXML
	void SendRequest() {
		// if the the user did not choose any radio button and did not insert new rate
		if (!Scooter.isSelected() && !Diesel.isSelected() && !Benzene.isSelected() && NewRate.getText().isEmpty()) {
			FuelTypeLabel.setText("* You must choose fuel type");
			NewRateLabel.setText("* You must insert new rate");
		} else if (!Scooter.isSelected() && !Diesel.isSelected() && !Benzene.isSelected()
				&& !NewRate.getText().isEmpty()) {
			FuelTypeLabel.setText("* You must choose fuel type");
			NewRateLabel.setText("");
		} else if (!(!Scooter.isSelected() && !Diesel.isSelected() && !Benzene.isSelected())
				&& NewRate.getText().isEmpty()) // if the new rate text field is empty.
		{
			FuelTypeLabel.setText("");
			NewRateLabel.setText("* You must insert new rate");
		} else if (fuelPrice != null && Double.valueOf(NewRate.getText()) > fuelPrice) // if the new rate is higher then
																						// the maximum rate.
		{
			FunctionForGUI.showAlert("Error", "Please enter rate that is lower or equal to the maximum rate.");
			NewRate.setText("");
			NewRateLabel.setText("");
			FuelTypeLabel.setText("");
		} else // If i got to this place everything OK.
		{
			FuelTypeLabel.setText("");
			NewRateLabel.setText("");
			if (Scooter.isSelected())
				sendNewRequestAtDB("scooter");
			else if (Diesel.isSelected())
				sendNewRequestAtDB("diesel");
			else if (Benzene.isSelected())
				sendNewRequestAtDB("benzene");
		}
	}

	/**
	 * This function is a sub function of sendRequest function that send a request
	 * of new rate of the chain manager
	 * 
	 * @param fuelType - the type of the fuel
	 */
	void sendNewRequestAtDB(String fuelType) {
		int employeeID = employee.getEmployeeID();
		String firstName = employee.getFirstName();
		String lastName = employee.getLastName();
		String role = employee.getRole();
		String chain = employee.getOrganizationalAffiliation();
		if (Double.parseDouble(NewRate.getText()) == Double.parseDouble(RateCurrentPaz.getText())
				&& Double.parseDouble(NewRate.getText()) == Double.parseDouble(RateCurrentTen.getText())
				&& Double.parseDouble(NewRate.getText()) == Double.parseDouble(RateCurrentDelek.getText())) // Its the
																											// same
																											// number,
																											// meaning
																											// no
																											// change.
			FunctionForGUI.showAlert("Error",
					"The new rate is the same as the old one, please insert diffrent number.");
		else {
			String str = "sendRequest " + employeeID + " " + firstName + " " + lastName + " " + role + " " + chain + " "
					+ fuelType + " " + NewRate.getText();
			ClientUI.chat.accept(str);
			boolean isUpdated = (boolean) MyFuelClient.msgFromServer;
			if (isUpdated) {
				FunctionForGUI.showAlert("Operation Succeed",
						"The request of changing the rate of fuel type " + fuelType + " has been sent.");
				refreshScreen();
			} else
				FunctionForGUI.showAlert("Error", "There is more than one varifications to the fuel " + fuelType
						+ ", please wait until all the requests for this fuel are approved.");
		}
	}

	/**
	 * This function refresh the rates window after finishing to send request
	 */
	public void refreshScreen() {
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
	 * This function check if benzene is selected
	 */
	@FXML
	void benzene() {
		if (!Benzene.isSelected())
			return;
		setTextAtTextFields("benzene", UserLoginController.employee.getOrganizationalAffiliation());
	}

	/**
	 * This function check if diesel is selected
	 */
	@FXML
	void diesel() {
		if (!Diesel.isSelected())
			return;
		setTextAtTextFields("diesel", UserLoginController.employee.getOrganizationalAffiliation());
	}

	/**
	 * This function check if scotter is selected
	 */
	@FXML
	void scotter() {
		if (!Scooter.isSelected())
			return;
		setTextAtTextFields("scooter", UserLoginController.employee.getOrganizationalAffiliation());
	}

	/**
	 * This function is a sub function of scotter,diesel,benzene and sets the fields
	 * MaximumPrice and CurrentRate in the given fuel type and chain name
	 * 
	 * @param fuelType  - The fuel type
	 * @param chainName - the chain name
	 */
	void setTextAtTextFields(String fuelType, String chainName) {
		ClientUI.chat.accept("getRate " + "paz" + " " + fuelType);
		Rates ratePaz = (Rates) MyFuelClient.msgFromServer;

		ClientUI.chat.accept("getRate " + "ten" + " " + fuelType);
		Rates rateTen = (Rates) MyFuelClient.msgFromServer;

		ClientUI.chat.accept("getRate " + "delek" + " " + fuelType);
		Rates rateDelek = (Rates) MyFuelClient.msgFromServer;

		if (ratePaz != null && rateTen != null && rateDelek != null) {
			switch (fuelType) {
			case "benzene":
				MaximumPrice.setText(String.valueOf(benzeneRate));
				fuelPrice = benzeneRate;
				break;
			case "diesel":
				MaximumPrice.setText(String.valueOf(dieselRate));
				fuelPrice = dieselRate;
				break;
			case "scooter":
				MaximumPrice.setText(String.valueOf(scooterRate));
				fuelPrice = scooterRate;
				break;
			}
			RateCurrentPaz.setText(String.valueOf(ratePaz.getRate()));
			RateCurrentTen.setText(String.valueOf(rateTen.getRate()));
			RateCurrentDelek.setText(String.valueOf(rateDelek.getRate()));
		}
		return;
	}

	/**
	 * This function makes the text field accept only double or integer strings
	 * 
	 * @param e
	 */
	@FXML
	public void onlyNumbers(KeyEvent e) {
		String c = e.getCharacter();
		if (!c.matches("[0-9]+") && !c.equals("."))
			e.consume();
		else if (NewRate.getText().isEmpty() && c.equals("."))
			e.consume();
		else if (c.equals(".") && NewRate.getText().contains("."))
			e.consume();

	}
}
