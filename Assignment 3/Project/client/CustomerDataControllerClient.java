package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.xml.internal.ws.util.StringUtils;

import client.ClientUI;
import client.MyFuelClient;
import entity.Customer;
import entity.CustomerForTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class CustomerDataControllerClient defined how the customers window would
 * work
 * 
 * @author Eylon
 *
 */
public class CustomerDataControllerClient implements Initializable {
	Customer[] arrCustomer;
	ObservableList<CustomerForTable> arrListCustomer = FXCollections.observableArrayList();

	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnUpdate;

	@FXML
	private TableView<CustomerForTable> tableCustomers;

	@FXML
	private TableColumn<CustomerForTable, String> clmnFirstName;

	@FXML
	private TableColumn<CustomerForTable, String> clmnLastName;

	@FXML
	private TableColumn<CustomerForTable, Integer> clmnID;

	@FXML
	private TableColumn<CustomerForTable, String> clmnEmail;

	@FXML
	private TableColumn<CustomerForTable, Integer> clmnCreditCard;

	@FXML
	private TableColumn<CustomerForTable, String> clmnCustomerType;

	@FXML
	private TableColumn<CustomerForTable, String> clmSubscriptionType;

	@FXML
	private ComboBox<String> cmbxSubscriptionType;

	@FXML
	private Label lblErrorEmail;
	
	@FXML
	private Label lblErrorCredit;

	@FXML
	private TextField txtCreditCard;

	@FXML
	private TextField txtFirstName;

	@FXML
	private TextField txtLastName;

	@FXML
	private TextField txtEmail;

	@FXML
	private RadioButton rbPrivate;

	@FXML
	private ToggleGroup customerType;

	@FXML
	private RadioButton rbCompany;

	@FXML
	private Button btnRemove;

	@FXML
	private Button Minus;
	@FXML
	private Button X;

	ObservableList<String> list;

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
	 * The function brings the user back to the previous window
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void backFunction(MouseEvent event) {
		changeScreen("/gui/SystemOperationMarketingRepresentative.fxml");

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
	}

	/**
	 * The function sends to the server controller the customer that the user wants
	 * to remove
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void removeCustomer(MouseEvent event) {
		if (tableCustomers.getSelectionModel().getSelectedItem() != null) {
			CustomerForTable temp = tableCustomers.getSelectionModel().getSelectedItem();
			ClientUI.chat.accept("removeCustomerByID " + temp.getID());
			initTable();
		}

	}

	/**
	 * The function sends to the server controller the customer with updated details
	 * that the user wants to update
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void updateCustomer(MouseEvent event) {
		boolean emailFlag = false;
		boolean creditFlag = false;
		int tempCredit = 0;
		String tempEmail = "";
		RadioButton selectedRadioButton = (RadioButton) customerType.getSelectedToggle();
		if (tableCustomers.getSelectionModel().getSelectedItem() != null) {
			lblErrorEmail.setText("");
			lblErrorCredit.setText("");
			CustomerForTable temp = tableCustomers.getSelectionModel().getSelectedItem();
			ClientUI.chat.accept("getNumOfVehiclesForCustomer " + temp.getID());
			int numOfVehicles = (Integer) MyFuelClient.msgFromServer;
			if (!txtFirstName.getText().equals(""))
				temp.setFirstName(txtFirstName.getText());
			if (!txtLastName.getText().equals(""))
				temp.setLastName(txtLastName.getText());
			if (!txtEmail.getText().equals("")) {

				ClientUI.chat.accept("checkIfEmailExists " + txtEmail.getText());
				if ((boolean) MyFuelClient.msgFromServer) {
					emailFlag = true;
					tempEmail = txtEmail.getText();

				}

				else
					temp.setEmail(txtEmail.getText());
			}
			if (!txtCreditCard.getText().equals("")) {
				ClientUI.chat.accept("checkIfCreditCardExists " + txtCreditCard.getText());
				if ((boolean) MyFuelClient.msgFromServer) {
					creditFlag = true;
					tempCredit = Integer.valueOf(txtCreditCard.getText());
				} else
					temp.setCreditCardNumber(Integer.valueOf(txtCreditCard.getText()));
			}
			if (customerType.getSelectedToggle() != null)
				temp.setType(selectedRadioButton.getText().toLowerCase());
			if (cmbxSubscriptionType.getValue() != null
					&& cmbxSubscriptionType.getValue().toLowerCase().equals("multiple monthly") && numOfVehicles == 1) {
				myPopAlert("Error",
						"You must have atleast two vehicles in order to change subscription type to multiple monthly. First, add another vehicle");
			} else {
				if (cmbxSubscriptionType.getValue() != null)
					temp.setSubscriptionType(cmbxSubscriptionType.getValue().toLowerCase());
				ClientUI.chat.accept(temp);
				initTable();
				if (emailFlag) {
					lblErrorEmail.setText("* This email is already registered");
					txtEmail.setText(tempEmail);
				}
				if (creditFlag) {
					lblErrorCredit.setText("* This credit card is already registered");
					txtCreditCard.setText(String.valueOf(tempCredit));
				}
			}
		}
	}

	/**
	 * The function pops an alert to the user based on the case (success/error)
	 * 
	 * @param msg1 the windows title
	 * @param msg2 the windows content
	 */
	public void myPopAlert(String msg1, String msg2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(msg1);
		alert.setHeaderText(null);
		alert.setContentText(msg2);
		alert.show();
	}

	/**
	 * The function shows the relevant data in a table view
	 */
	public void initTable() {
		int i;
		arrListCustomer.clear();
		ClientUI.chat.accept("showCustomersInDB");
		arrCustomer = (Customer[]) MyFuelClient.msgFromServer;
		for (i = 0; i < arrCustomer.length; i++) {
			arrListCustomer.add(new CustomerForTable(StringUtils.capitalize(arrCustomer[i].getFirstName()),
					StringUtils.capitalize(arrCustomer[i].getLastName()), arrCustomer[i].getID(),
					arrCustomer[i].getEmail(), arrCustomer[i].getCreditCardNumber(), arrCustomer[i].getType(),
					arrCustomer[i].getSubscriptionType()));
		}
		tableCustomers.setItems(arrListCustomer);
		txtEmail.setText("");
		txtFirstName.setText("");
		txtLastName.setText("");
		txtCreditCard.setText("");
		customerType.selectToggle(null);
		cmbxSubscriptionType.setValue(null);
		lblErrorEmail.setText("");
		lblErrorCredit.setText("");
	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setSubscriptionTypeComboBox();
		LimitAreaAndTextFields.limitTxtField(txtCreditCard, 16);
		clmnFirstName.setCellValueFactory(new PropertyValueFactory<CustomerForTable, String>("firstName"));
		clmnLastName.setCellValueFactory(new PropertyValueFactory<CustomerForTable, String>("lastName"));
		clmnID.setCellValueFactory(new PropertyValueFactory<CustomerForTable, Integer>("ID"));
		clmnEmail.setCellValueFactory(new PropertyValueFactory<CustomerForTable, String>("email"));
		clmnCreditCard.setCellValueFactory(new PropertyValueFactory<CustomerForTable, Integer>("creditCardNumber"));
		clmnCustomerType.setCellValueFactory(new PropertyValueFactory<CustomerForTable, String>("type"));
		clmSubscriptionType.setCellValueFactory(new PropertyValueFactory<CustomerForTable, String>("SubscriptionType"));
		tableCustomers.setItems(arrListCustomer);
		initTable();

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
