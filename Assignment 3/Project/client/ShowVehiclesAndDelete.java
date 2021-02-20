package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.ClientUI;
import client.MyFuelClient;
import entity.Customer;
import entity.Vehicle;
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

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * This class ShowVehiclesAndDelete defined how the ShowVehiclesAndDelete window
 * would work
 * 
 * @author gal
 *
 */
public class ShowVehiclesAndDelete implements Initializable {

	@FXML
	private Pane MyPane;

	@FXML	
	private Button btnBack = null;

	@FXML
	private Button btnDelete = null;

	@FXML
	private TableView<Vehicle> tableVehicles;

	@FXML
	private TableColumn<Vehicle, String> clmnFirstName;

	@FXML
	private TableColumn<Vehicle, String> clmnLastName;

	@FXML
	private TableColumn<Vehicle, Integer> clmnCustomerID;

	@FXML
	private TableColumn<Vehicle, Integer> clmnLicenseNumber;

	@FXML
	private TableColumn<Vehicle, String> clmnVType;

	@FXML
	private TableColumn<Vehicle, String> clmnFuelType;

	@FXML
	private Label lbl1;

	@FXML
	private Label lblError;

	@FXML
	private Button Minus;
	@FXML
	private Button X;

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

	Vehicle[] arrVehicles;
	Customer[] arrCustomers;
	Vehicle[] arrVehiclesForShow;
	ObservableList<Vehicle> arrListVehicles = FXCollections.observableArrayList();

	/**
	 * This method initialize the show vehicles and delete screen when it opened.
	 * Takes data of vehicles from DB and presents the data.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int i, j, k = 0;
		clmnFirstName.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("firstName"));
		clmnLastName.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("lastName"));
		clmnCustomerID.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("customerId"));
		clmnLicenseNumber.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("licensePlateNumber"));
		clmnVType.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vType"));
		clmnFuelType.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("fuelType"));
		ClientUI.chat.accept("showVehicles");
		arrVehicles = (Vehicle[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showCustomers");
		arrCustomers = (Customer[]) MyFuelClient.msgFromServer;
		arrVehiclesForShow = new Vehicle[arrVehicles.length];
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
		for (i = 0; i < arrVehiclesForShow.length; i++) {
			arrListVehicles.add(arrVehiclesForShow[i]);
		}
		tableVehicles.setItems(arrListVehicles);
	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backClick(MouseEvent event) {
		changeScreen("/gui/SystemOperationMarketingRepresentative.fxml");
	}

	/**
	 * This method delete the vehicle and also delete it from the DB. User can
	 * delete vehicle if he has more than one vehicle and by his purchase plan.
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void deleteVehicleClick(MouseEvent event) {
		lblError.setText("");
		int i = 0;
		int count = 0;
		if (tableVehicles.getSelectionModel().getSelectedItem() != null) {
			int customerId = tableVehicles.getSelectionModel().getSelectedItem().getCustomerId();
			int licensePlateNumber = tableVehicles.getSelectionModel().getSelectedItem().getLicensePlateNumber();
			ClientUI.chat.accept("checkSubscriptionTypeForCustomerID:" + customerId);
			String subscriptionType = (String) MyFuelClient.msgFromServer;
			for (i = 0; i < arrVehicles.length; i++)
				if (arrVehicles[i].getCustomerId() == customerId)
					count++;
			if (subscriptionType.contains("multiple") && count <= 2)
				lblError.setText(
						"* The subscription type of this customer is multiple monthly so he must have atleast 2 vehicles!\nTo delete, first add another vehicle");
			else if (!subscriptionType.contains("multiple") && count <= 1)
				lblError.setText(
						"* The customer must have atleast 1 vehicle registered, to delete, first add another vehicle");
			else {
				ClientUI.chat.accept("deleteVehicle:" + customerId + ":" + licensePlateNumber);
				if ((boolean) MyFuelClient.msgFromServer) {
					changeScreen("/gui/ShowVehicle.fxml"); // to stay in the form
				}
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
			MyPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}