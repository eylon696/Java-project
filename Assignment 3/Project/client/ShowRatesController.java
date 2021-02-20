package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.Employee;
import entity.Rates;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * The class ShowRatesController defined how the ShowRates would work
 * 
 * @author Shoval David
 *
 */
public class ShowRatesController implements Initializable {
	
	@FXML
	private Pane MyPane;
	@FXML
	private Button X;
	@FXML
	private Button Minus;
	@FXML
	private TextField BenzeneTextField;
	@FXML
	private TextField DieselTextField;
	@FXML
	private TextField ScooterTextField;
	@FXML
	private Button ButtonBack;

	@FXML
	private TextField txtChain;

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
	 * This function initialize the HomePage screen when it opened
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtChain.setText(UserLoginController.employee.getOrganizationalAffiliation());
		txtChain.setEditable(false);
		getTextFieldData();
	}

	/**
	 * This function sets all the text fields inside the window
	 */
	private void getTextFieldData() {
		Employee employee = UserLoginController.employee;
		ClientUI.chat.accept("getRate " + employee.getOrganizationalAffiliation() + " " + "benzene");
		Rates rateBenzene = (Rates) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("getRate " + employee.getOrganizationalAffiliation() + " " + "diesel");
		Rates rateDiesel = (Rates) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("getRate " + employee.getOrganizationalAffiliation() + " " + "scooter");
		Rates rateScooter = (Rates) MyFuelClient.msgFromServer;
		BenzeneTextField.setText(String.valueOf(rateBenzene.getRate()));
		DieselTextField.setText(String.valueOf(rateDiesel.getRate()));
		ScooterTextField.setText(String.valueOf(rateScooter.getRate()));
	}

}
