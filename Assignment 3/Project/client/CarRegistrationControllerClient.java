package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import client.ClientUI;
import client.MyFuelClient;
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
import entity.Customer;
import entity.Vehicle;

/**
 * The class CarRegistrationControllerClient defined how the car registration
 * window would work
 * 
 * @author Eylon
 *
 */
public class CarRegistrationControllerClient implements Initializable {
	
	private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	Customer[] arrCustomers;
	Vehicle[] arrVehiclesForShow;
	ObservableList<Vehicle> arrListVehicles = FXCollections.observableArrayList();
	Vehicle[] arrVehicles;
	@FXML
	private Label txtClearAllFields;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnAddCar;

	@FXML
	private Button btnFinish;

	@FXML
	private TextField customerID;

	@FXML
	private TextField licensePlateNumber;

	@FXML
	private ToggleGroup vehicle;

	@FXML
	private RadioButton carRadiobtn;

	@FXML
	private RadioButton scooterRadiobtn;

	@FXML
	private ComboBox<String> cmbxFuelType;

	@FXML
	private TableView<Vehicle> tableVehicles;

	@FXML
	private TableColumn<Vehicle, String> clmnFirstName;

	@FXML
	private TableColumn<Vehicle, String> cmnLastName;

	@FXML
	private TableColumn<Vehicle, Integer> clmnCustomerID;

	@FXML
	private TableColumn<Vehicle, Integer> clmnLicenseNumber;

	@FXML
	private TableColumn<Vehicle, String> clmnVheicleType;

	@FXML
	private TableColumn<Vehicle, String> clmnFuelType;

	@FXML
	private Label txtErrorCustomerID;

	@FXML
	private Label txtErrorLicensePlateNumber;

	@FXML
	private Label txtErrorVehicleType;

	@FXML
	private Label txtErrorFuelType;

	@FXML
	private Label pathCarRegistration;

	@FXML
	private Pane MyPane;

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
	 * The function sets the fuel types in the combo box
	 */
	private void setFuelTypeComboBox() {
		ArrayList<String> al = new ArrayList<String>();
		al.add("Benzene");
		al.add("Diesel");
		al.add("Scooter's fuel");

		list = FXCollections.observableArrayList(al);
		cmbxFuelType.setItems(list);
	}

	/**
	 * The function sets information in the table
	 */
	public void setTable() {
		arrListVehicles.clear();
		int i, j, k = 0;
		clmnFirstName.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("firstName"));
		cmnLastName.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("lastName"));
		clmnCustomerID.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("customerId"));
		clmnLicenseNumber.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("licensePlateNumber"));
		clmnVheicleType.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vType"));
		clmnFuelType.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("fuelType"));
		ClientUI.chat.accept("showCustomers");
		arrCustomers = (Customer[]) MyFuelClient.msgFromServer;
		arrVehicles = new Vehicle[vehicles.size()];
		for (i = 0; i < vehicles.size(); i++) {
			arrVehicles[i] = vehicles.get(i);
		}
		arrVehiclesForShow = new Vehicle[arrVehicles.length];
		if (CustomerRegistrationControllerClient.customerToRegister == null) {
			for (j = 0; j < arrCustomers.length; j++) {
				for (i = 0; i < arrVehicles.length; i++) {
					if (arrCustomers[j].getID() == arrVehicles[i].getCustomerId()) {
						arrVehiclesForShow[k] = new Vehicle(arrVehicles[i].getCustomerId(),
								arrVehicles[i].getLicensePlateNumber(), arrVehicles[i].getVType(),
								arrVehicles[i].getFuelType(), false, false, arrCustomers[j].getFirstName(),
								arrCustomers[j].getLastName());
						k++;
					}
				}
			}
		} else {

			for (i = 0; i < arrVehicles.length; i++) {
				arrVehiclesForShow[k] = new Vehicle(arrVehicles[i].getCustomerId(),
						arrVehicles[i].getLicensePlateNumber(), arrVehicles[i].getVType(), arrVehicles[i].getFuelType(),
						false, false, CustomerRegistrationControllerClient.customerToRegister.getFirstName(),
						CustomerRegistrationControllerClient.customerToRegister.getLastName());
				k++;
			}
		}
		for (i = 0; i < arrVehiclesForShow.length; i++) {

			arrListVehicles.add(arrVehiclesForShow[i]);
		}
		tableVehicles.setItems(arrListVehicles);
	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setFuelTypeComboBox();
		if (CustomerRegistrationControllerClient.customerToRegister != null) {
			customerID.setText(String.valueOf(CustomerRegistrationControllerClient.customerToRegister.getID()));
			customerID.setEditable(false);
			pathCarRegistration.setText("System Operations -> Customer Registration -> Car Addition & Affiliation");
		}
		setTable();

	}

	/**
	 * The function adds a vehicle to the vehicles list of the customer
	 */

	@FXML
	public void addCar(MouseEvent event) {
		txtErrorCustomerID.setText("");
		txtErrorFuelType.setText("");
		txtErrorLicensePlateNumber.setText("");
		txtErrorVehicleType.setText("");
		if (checkAddAndFinishCar()) {
			addCarAlert("Success", "The vehicle details are saved, to complete the progress press Finish");
			if (CustomerRegistrationControllerClient.customerToRegister == null)
				customerID.clear();
			licensePlateNumber.clear();
			vehicle.getSelectedToggle().setSelected(false);
			cmbxFuelType.setValue(null);
		}
		setTable();
	}

	/**
	 * The function sends the registered vehicles to the server in order to save
	 * them in the DB
	 */
	@FXML
	public void carFinish(MouseEvent event) {
		txtErrorCustomerID.setText("");
		txtErrorFuelType.setText("");
		txtErrorLicensePlateNumber.setText("");
		txtErrorVehicleType.setText("");

		if (CustomerRegistrationControllerClient.customerToRegister == null) {// we came from system operation form
			if (checkAddAndFinishCar()) {
				ClientUI.chat.accept(vehicles);
				addCarAlert("Success", "Cars added successfuly");
				changeScreen("/gui/SystemOperationMarketingRepresentative.fxml");
			}
		} else {// we came from customer registration form
			if (CustomerRegistrationControllerClient.customerToRegister.getSubscriptionType()
					.equals("multiple monthly")) {
				if (checkAddAndFinishCar()) {
					if (vehicles.size() < 2) {
						vehicles.remove(vehicles.size() - 1);
						addCarAlert("Error",
								"You must affiliate atleast two vehicles becuase you chose multiple monthly subscription");
					}

					else {
						ClientUI.chat.accept(CustomerRegistrationControllerClient.customerToRegister);
						ClientUI.chat.accept(vehicles);
						addCarAlert("Success", "Customer registration has been successfuly committed");
						CustomerRegistrationControllerClient.customerToRegister = null;
						changeScreen("/gui/SystemOperationMarketingRepresentative.fxml");
					}
				}
			} else {
				if (checkAddAndFinishCar()) {
					ClientUI.chat.accept(CustomerRegistrationControllerClient.customerToRegister);
					ClientUI.chat.accept(vehicles);
					addCarAlert("Success", "Customer registration has been successfuly committed");
					CustomerRegistrationControllerClient.customerToRegister = null;
					changeScreen("/gui/SystemOperationMarketingRepresentative.fxml");

				}

			}
		}
	}

	/**
	 * This function checks all the input from the user and verifies that the input
	 * is valid
	 */
	public boolean checkAddAndFinishCar() {

		boolean flagCarInTheList = false;
		RadioButton selectedRadioButton = (RadioButton) vehicle.getSelectedToggle();

		if ((!customerID.getText().matches("[0-9]+") || customerID.getText().length() != 9))
			txtErrorCustomerID.setText("* You inserted invalid customer id");
		else if (!licensePlateNumber.getText().matches("[0-9]+") || licensePlateNumber.getText().length() < 5
				|| licensePlateNumber.getText().length() > 8)
			txtErrorLicensePlateNumber.setText("* You inserted invalid license plate number");
		else if (vehicle.getSelectedToggle() == null) // we need to check
			txtErrorVehicleType.setText("* You must choose between car or scooter");
		else if (cmbxFuelType.getValue() == null)
			txtErrorFuelType.setText("* You must choose a fuel type");
		else {
			if (CustomerRegistrationControllerClient.customerToRegister == null) {// we came from system operation form
				ClientUI.chat.accept("checkIfCustomerIdExists " + customerID.getText());
				if (!(boolean) MyFuelClient.msgFromServer)
					txtErrorCustomerID.setText("* The id does not exist");
				else {
					ClientUI.chat.accept("checkIfLicensePlateNumberExists " + licensePlateNumber.getText());
					if ((boolean) MyFuelClient.msgFromServer)
						txtErrorLicensePlateNumber.setText("* The license plate number already exists");
					else {
						for (Vehicle i : vehicles) {
							if (i.getLicensePlateNumber() == Integer.valueOf(licensePlateNumber.getText())) {
								txtErrorLicensePlateNumber.setText("* You alredy inserted this license plate number");
								flagCarInTheList = true;
								break;
							}
						}
						if (!flagCarInTheList) {
							vehicles.add(new Vehicle(Integer.valueOf(customerID.getText()),
									Integer.valueOf(licensePlateNumber.getText()),
									selectedRadioButton.getText().toLowerCase(),
									cmbxFuelType.getValue().toString().toLowerCase(), true, false));
							return true;
						}
					}
				}
			} else {// we came from customer registration form
				ClientUI.chat.accept("checkIfLicensePlateNumberExists " + licensePlateNumber.getText());
				if ((boolean) MyFuelClient.msgFromServer)
					txtErrorLicensePlateNumber.setText("* The license plate number already exists");
				else {
					for (Vehicle i : vehicles) {
						if (i.getLicensePlateNumber() == Integer.valueOf(licensePlateNumber.getText())) {
							txtErrorLicensePlateNumber.setText("* You alredy inserted this license plate number");
							flagCarInTheList = true;
							break;
						}
					}
					if (!flagCarInTheList) {
						vehicles.add(new Vehicle(Integer.valueOf(customerID.getText()),
								Integer.valueOf(licensePlateNumber.getText()),
								selectedRadioButton.getText().toLowerCase(),
								cmbxFuelType.getValue().toString().toLowerCase(), true, false));
						return true;
					}
				}

			}

		}
		return false;
	}

	/**
	 * This method clears the form
	 * 
	 * @param event The event we handle
	 */

	@FXML
	public void clearAllFields(MouseEvent event) {
		if (CustomerRegistrationControllerClient.customerToRegister == null)
			customerID.clear();
		licensePlateNumber.clear();
		if (vehicle.getSelectedToggle() != null)
			vehicle.getSelectedToggle().setSelected(false);
		setFuelTypeComboBox();
		cmbxFuelType.setValue(null);
		txtErrorCustomerID.setText("");
		txtErrorFuelType.setText("");
		txtErrorLicensePlateNumber.setText("");
		txtErrorVehicleType.setText("");
	}

	/**
	 * The function changes the fuel types combo box based on the chosen vehicle
	 * (car)
	 */
	@FXML
	void changeFuelTypeToCar(MouseEvent event) {

		ArrayList<String> al = new ArrayList<String>();
		al.add("Benzene");
		al.add("Diesel");

		list = FXCollections.observableArrayList(al);
		cmbxFuelType.setItems(list);

	}

	/**
	 * The function changes the fuel types combo box based on the chosen vehicle
	 * (scooter)
	 */
	@FXML
	void changeFuelTypeToScooter(MouseEvent event) {
		ArrayList<String> al = new ArrayList<String>();
		al.add("Scooter's fuel");

		list = FXCollections.observableArrayList(al);
		cmbxFuelType.setItems(list);
		cmbxFuelType.setValue("Scooter's fuel");
	}

	/**
	 * The function pops an alert to the user based on the case (success/error)
	 * 
	 * @param msg1 the windows title
	 * @param msg2 the windows content
	 */
	public void addCarAlert(String msg1, String msg2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(msg1);
		alert.setHeaderText(null);
		alert.setContentText(msg2);
		alert.show();
	}

	/**
	 * The function brings the user back to the previous window
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void backFunction(MouseEvent event) throws IOException {
		if (CustomerRegistrationControllerClient.customerToRegister != null) {
			if (vehicles.size() >= 1) {
				customerID.setText(String.valueOf((vehicles.get(vehicles.size() - 1).getCustomerId())));
				licensePlateNumber.setText(String.valueOf((vehicles.get(vehicles.size() - 1).getLicensePlateNumber())));
				if (String.valueOf((vehicles.get(vehicles.size() - 1).getVType())).equals("car")) {
					vehicle.selectToggle(carRadiobtn);
					if (String.valueOf((vehicles.get(vehicles.size() - 1).getFuelType())).equals("benzene"))
						cmbxFuelType.setValue("Benzene");
					else
						cmbxFuelType.setValue("Diesel");
				} else {
					vehicle.selectToggle(scooterRadiobtn);
					cmbxFuelType.setValue("Scooter's fuel");
				}
				vehicles.remove(vehicles.size() - 1);
				setTable();
			} else
				changeScreen("/gui/CustomerRegistrationForm.fxml");
		} else {
			if (vehicles.size() >= 1) {
				customerID.setText(String.valueOf((vehicles.get(vehicles.size() - 1).getCustomerId())));
				licensePlateNumber.setText(String.valueOf((vehicles.get(vehicles.size() - 1).getLicensePlateNumber())));
				if (String.valueOf((vehicles.get(vehicles.size() - 1).getVType())).equals("car")) {
					vehicle.selectToggle(carRadiobtn);
					if (String.valueOf((vehicles.get(vehicles.size() - 1).getFuelType())).equals("benzene"))
						cmbxFuelType.setValue("Benzene");
					else
						cmbxFuelType.setValue("Diesel");
				} else {
					vehicle.selectToggle(scooterRadiobtn);
					cmbxFuelType.setValue("Scooter's fuel");
				}
				vehicles.remove(vehicles.size() - 1);
				setTable();
			} else
				changeScreen("/gui/SystemOperationMarketingRepresentative.fxml");
		}
	}

	/**
	 * The function limits the text field only to numbers
	 * 
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
