package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class CustomerRegistrationControllerClient defined how the customer
 * registration window would work
 * 
 * @author Eylon
 *
 */
public class CustomerRegistrationControllerClient implements Initializable {
		
	@FXML
	private Tooltip toolTipSubType;

	@FXML
	private RadioButton customerTypePrivate;

	@FXML
	private ToggleGroup customertype;

	@FXML
	private RadioButton customerTypeCompany;

	@FXML
	private Button btnNext;

	@FXML
	private TextField txtCreditCardNumber;

	@FXML
	private TextField txtFirstName;

	@FXML
	private TextField txtLastName;

	@FXML
	private TextField txtID;

	@FXML
	private TextField txtPassword;

	@FXML
	private TextField txtEmail;

	@FXML
	private Label errorFirstName;

	@FXML
	private Label errorCreditCardNumber;

	@FXML
	private Label errorEmail;

	@FXML
	private Label errorPassword;

	@FXML
	private Label errorID;

	@FXML
	private Label errorLastName;

	@FXML
	private Label errorCustomerType;

	@FXML
	private ComboBox<String> cmbxSubscriptionType;

	@FXML
	private Button btnBack;

	@FXML
	private Pane MyPane;

	@FXML
	private Button Minus;
	@FXML
	private Button X;

	@FXML
	private Label btnClearAllFields;
	ObservableList<String> list;
	public static Customer customerToRegister;

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
	 * This function minimize the window
	 * 
	 * @param event , the even we handle
	 */
	@FXML
	private void minimize(ActionEvent event) {
		FunctionForGUI.minimizePane(event, MyPane);
	}

	/**
	 * The function sets the subscription types in the combo box
	 */
	private void setSubscriptionTypeComboBox() {
		ArrayList<String> al = new ArrayList<String>();
		al.add("Casual");
		al.add("Single monthly");
		al.add("Multiple monthly");
		al.add("Full monthly");

		list = FXCollections.observableArrayList(al);
		cmbxSubscriptionType.setItems(list);
		cmbxSubscriptionType.setValue("Casual");
	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		toolTipSubType.setText(
				"Casual - no discount\nSingle monthly - 4% discount for a single vehicle\nMultiple monthly - 10% discount + 4% discount per vehicle\nFull monthly - fixed price - 13% discount + 4% discount per vehicle");
		LimitAreaAndTextFields.limitTxtField(txtCreditCardNumber, 16);
		setSubscriptionTypeComboBox();
		if (customerToRegister != null) {
			if (customerToRegister.getType().equals("private"))
				customertype.selectToggle(customerTypePrivate);
			else
				customertype.selectToggle(customerTypeCompany);
			txtFirstName.setText(customerToRegister.getFirstName());
			txtLastName.setText(customerToRegister.getLastName());
			txtID.setText(String.valueOf(customerToRegister.getID()));
			txtPassword.setText(customerToRegister.getPassword());
			txtEmail.setText(customerToRegister.getEmail());
			txtCreditCardNumber.setText(String.valueOf(customerToRegister.getCreditCardNumber()));
			cmbxSubscriptionType.setValue(customerToRegister.getSubscriptionType());
		} else
			init();
	}

	/**
	 * The function inits all the data in the form
	 */
	public void init() {
		customerToRegister = null;
		txtFirstName.clear();
		txtLastName.clear();
		txtEmail.clear();
		txtID.clear();
		txtPassword.clear();
		txtCreditCardNumber.clear();
		customertype.selectToggle(null);
		cmbxSubscriptionType.setValue("Casual");
		errorCustomerType.setText("");
		errorFirstName.setText("");
		errorLastName.setText("");
		errorID.setText("");
		errorPassword.setText("");
		errorEmail.setText("");
		errorCreditCardNumber.setText("");
	}

	/**
	 * The function brings the user back to the previous window
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void backFunction(MouseEvent event) {
		customerToRegister = null;
		changeScreen("/gui/SystemOperationMarketingRepresentative.fxml");
	}

	/**
	 * This method clears the form
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clearAllFields(MouseEvent event) {
		txtFirstName.clear();
		txtLastName.clear();
		txtEmail.clear();
		txtID.clear();
		txtPassword.clear();
		txtCreditCardNumber.clear();
		customertype.selectToggle(null);
		cmbxSubscriptionType.setValue("Casual");
		errorCustomerType.setText("");
		errorFirstName.setText("");
		errorLastName.setText("");
		errorID.setText("");
		errorPassword.setText("");
		errorEmail.setText("");
		errorCreditCardNumber.setText("");

	}

	/**
	 * The function limits the text field only to numbers
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void limit(KeyEvent event) {
		String c = event.getCharacter();
		if (!c.matches("[0-9]+"))
			event.consume();
	}

	/**
	 * The function saves all the customer information and goes to the car
	 * registration form
	 * 
	 * @param event the event we handle
	 * @throws IOException
	 */

	@FXML
	void nextFunction(MouseEvent event) throws IOException {
		int flag = 0;
		RadioButton selectedRadioButton = (RadioButton) customertype.getSelectedToggle();
		errorCustomerType.setText("");
		errorFirstName.setText("");
		errorLastName.setText("");
		errorID.setText("");
		errorPassword.setText("");
		errorEmail.setText("");
		errorCreditCardNumber.setText("");
		if (customertype.getSelectedToggle() == null || txtFirstName.getText().equals("")
				|| txtLastName.getText().equals("") || txtID.getText().equals("") || txtPassword.getText().equals("")
				|| txtEmail.getText().equals("") || txtCreditCardNumber.getText().equals("")
				|| txtID.getText().length() != 9) {
			if (customertype.getSelectedToggle() == null)
				errorCustomerType.setText("* You must choose customer type");
			if (txtFirstName.getText().equals(""))
				errorFirstName.setText("* You must enter first name");
			if (txtLastName.getText().equals(""))
				errorLastName.setText("* You must enter last name");
			if (txtID.getText().equals("") || txtID.getText().length() != 9)
				errorID.setText("* You must enter a valid ID number");
			if (txtPassword.getText().equals(""))
				errorPassword.setText("* You must enter a password");
			if (txtEmail.getText().equals(""))
				errorEmail.setText("* You must enter an email");
			if (txtCreditCardNumber.getText().equals(""))
				errorCreditCardNumber.setText("* You must enter a credit card number");
		}

		else {// check if the id exists in the DB
			ClientUI.chat.accept("checkIfCustomerIdExists " + txtID.getText());
			if ((boolean) MyFuelClient.msgFromServer)
				errorID.setText("* This ID number is already registered");
			else
				flag++;
			ClientUI.chat.accept("checkIfEmailExists " + txtEmail.getText());
			if ((boolean) MyFuelClient.msgFromServer)
				errorEmail.setText("* This email is already registered");
			else
				flag++;
			ClientUI.chat.accept("checkIfCreditCardExists " + txtCreditCardNumber.getText());
			if ((boolean) MyFuelClient.msgFromServer)
				errorCreditCardNumber.setText("* This credit card number is already registered");
			else
				flag++;
			if (flag == 3) {
				customerToRegister = new Customer(txtFirstName.getText().toLowerCase(),
						txtLastName.getText().toLowerCase(), Integer.valueOf(txtID.getText()), txtPassword.getText(),
						txtEmail.getText().toLowerCase(), Integer.valueOf(txtCreditCardNumber.getText()),
						selectedRadioButton.getText().toLowerCase(), cmbxSubscriptionType.getValue().toLowerCase());
				changeScreen("/gui/CarAffiliationForm.fxml");
			}
		}
	}

	/**
	 * This method changes screen to the chosen screen
	 * 
	 * @param fxml The form to change to
	 */
	public void changeScreen(String fxml) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			MyPane.getChildren().clear();
			MyPane.getChildren().remove(MyPane);
			MyPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
