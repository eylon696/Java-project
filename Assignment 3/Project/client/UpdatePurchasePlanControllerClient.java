package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.Vehicle;
import entity.ChainOfGas;
import entity.PurchasePlan;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * 
 * The class UpdatePurchasePlanControllerClient defined how the update purchase
 * plan window would work
 * 
 * @author Eylon
 *
 */
public class UpdatePurchasePlanControllerClient implements Initializable {

	public Vehicle vehicleArr[];
	public ChainOfGas gasStationArr[];
	PurchasePlan[] arrPurchasePlans;
	ObservableList<PurchasePlan> arrListPurchasePlans = FXCollections.observableArrayList();

	@FXML
	private TableView<PurchasePlan> tablePlans;

	@FXML
	private TableColumn<PurchasePlan, String> clmnFirstName;

	@FXML
	private TableColumn<PurchasePlan, String> clmnLastName;

	@FXML
	private TableColumn<PurchasePlan, Integer> clmnCustomerID;

	@FXML
	private TableColumn<PurchasePlan, String> clmnPlanType;

	@FXML
	private TableColumn<PurchasePlan, String> clmnGasCompany1;

	@FXML
	private TableColumn<PurchasePlan, String> clmnGasCompany2;

	@FXML
	private TableColumn<PurchasePlan, String> clmnGasCompany3;

	@FXML
	private RadioButton planTypeNone;

	@FXML
	private ToggleGroup PlanType;

	@FXML
	private RadioButton planTypeExclusive;

	@FXML
	private RadioButton planTypeMultiple;

	@FXML
	private ComboBox<String> cmbxGasCompany1;

	@FXML
	private ComboBox<String> cmbxGasCompany2;

	@FXML
	private ComboBox<String> cmbxGasCompany3;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnUpdate;

	@FXML
	private Label btnClear;

	@FXML
	private Label txtErrorGasCompany;

	@FXML
	private Pane MyPane;

	ObservableList<Integer> list;

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
	 * @param event the even we handle
	 */
	@FXML
	private void minimize(ActionEvent event) {
		FunctionForGUI.minimizePane(event, MyPane);
	}

	/**
	 * The function sets the combo box options if exlusive type was chosen
	 * 
	 * @param event the even we handle
	 */
	@FXML
	private void setExclusiveCmbx(MouseEvent event) {
		ObservableList<String> list_gas;
		cmbxGasCompany1.setDisable(false);
		cmbxGasCompany2.setDisable(true);
		cmbxGasCompany3.setDisable(true);
		cmbxGasCompany1.setValue(null);
		cmbxGasCompany2.setValue(null);
		cmbxGasCompany3.setValue(null);

		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < gasStationArr.length; i++) {
			al.add(gasStationArr[i].getName());

			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany1.setItems(list_gas);
		}

	}

	/**
	 * The function sets the combo box options if multiple type was chosen
	 * 
	 * @param event the even we handle
	 */
	@FXML
	private void setMultipleCmbx(MouseEvent event) {
		ObservableList<String> list_gas;
		cmbxGasCompany1.setDisable(false);
		cmbxGasCompany2.setDisable(false);
		cmbxGasCompany3.setDisable(false);
		cmbxGasCompany1.setValue(null);
		cmbxGasCompany2.setValue(null);
		cmbxGasCompany3.setValue(null);

		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < gasStationArr.length; i++)
			al.add(gasStationArr[i].getName());

		list_gas = FXCollections.observableArrayList(al);
		cmbxGasCompany1.setItems(list_gas);
		cmbxGasCompany2.setItems(list_gas);
		cmbxGasCompany3.setItems(list_gas);

	}

	/**
	 * The function sets the combo box options if none type was chosen
	 * 
	 * @param event the even we handle
	 */
	@FXML
	private void setNoneCmbx(MouseEvent event) {
		cmbxGasCompany1.setDisable(true);
		cmbxGasCompany2.setDisable(true);
		cmbxGasCompany3.setDisable(true);
		cmbxGasCompany1.setValue(null);
		cmbxGasCompany2.setValue(null);
		cmbxGasCompany3.setValue(null);
	}

	/**
	 * The function gets all the gas companies from the DB
	 */
	private void getAllGasCompanies() {
		ClientUI.chat.accept("getAllGasCompanies");
		gasStationArr = (ChainOfGas[]) MyFuelClient.msgFromServer;

	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list_gas;
		cmbxGasCompany1.setDisable(true);
		cmbxGasCompany2.setDisable(true);
		cmbxGasCompany3.setDisable(true);
		getAllGasCompanies();
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < gasStationArr.length; i++)
			al.add(gasStationArr[i].getName());

		list_gas = FXCollections.observableArrayList(al);
		cmbxGasCompany1.setItems(list_gas);
		cmbxGasCompany2.setItems(list_gas);
		cmbxGasCompany3.setItems(list_gas);
		clmnFirstName.setCellValueFactory(new PropertyValueFactory<PurchasePlan, String>("firstName"));
		clmnLastName.setCellValueFactory(new PropertyValueFactory<PurchasePlan, String>("lastName"));
		clmnCustomerID.setCellValueFactory(new PropertyValueFactory<PurchasePlan, Integer>("customerID"));
		clmnPlanType.setCellValueFactory(new PropertyValueFactory<PurchasePlan, String>("planType"));
		clmnGasCompany1.setCellValueFactory(new PropertyValueFactory<PurchasePlan, String>("GasCompany1"));
		clmnGasCompany2.setCellValueFactory(new PropertyValueFactory<PurchasePlan, String>("GasCompany2"));
		clmnGasCompany3.setCellValueFactory(new PropertyValueFactory<PurchasePlan, String>("GasCompany3"));
		tablePlans.setItems(arrListPurchasePlans);
		initTable();
	}

	/**
	 * The function shows the relevant data in a table view
	 */
	public void initTable() {
		int i;
		arrListPurchasePlans.clear();
		ClientUI.chat.accept("showPlansInDB");
		arrPurchasePlans = (PurchasePlan[]) MyFuelClient.msgFromServer;
		for (i = 0; i < arrPurchasePlans.length; i++) {
			arrListPurchasePlans.add(arrPurchasePlans[i]);
		}
		tablePlans.setItems(arrListPurchasePlans);
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
	 * The function sets the second and third gas companies combo boxes
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void cmbxGasCompany1Clicked(MouseEvent event) {
		ObservableList<String> list_gas;
		ArrayList<String> al = new ArrayList<String>();
		if (planTypeExclusive.isSelected()) {
			for (int i = 0; i < gasStationArr.length; i++)
				al.add(gasStationArr[i].getName());
			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany1.setItems(list_gas);
		} else {
			if (planTypeMultiple.isSelected()) {
				if (cmbxGasCompany2.getValue() == null && cmbxGasCompany3.getValue() == null) {
					for (int i = 0; i < gasStationArr.length; i++)
						al.add(gasStationArr[i].getName());
					list_gas = FXCollections.observableArrayList(al);
					cmbxGasCompany1.setItems(list_gas);
				} else if (cmbxGasCompany2.getValue() == null && cmbxGasCompany3.getValue() != null) { // cmbx gas
																										// company 3 is
																										// not null
					for (int i = 0; i < gasStationArr.length; i++)
						if (!(cmbxGasCompany3.getValue().equals(gasStationArr[i].getName())))
							al.add(gasStationArr[i].getName());
					list_gas = FXCollections.observableArrayList(al);
					cmbxGasCompany1.setItems(list_gas);
				} else if (cmbxGasCompany3.getValue() == null && cmbxGasCompany2.getValue() != null) {// cmbx gas
																										// company 2 is
																										// not null
					for (int i = 0; i < gasStationArr.length; i++)
						if (!(cmbxGasCompany2.getValue().equals(gasStationArr[i].getName())))
							al.add(gasStationArr[i].getName());
					list_gas = FXCollections.observableArrayList(al);
					cmbxGasCompany1.setItems(list_gas);
				} else {
					for (int i = 0; i < gasStationArr.length; i++)
						if (!(cmbxGasCompany2.getValue().equals(gasStationArr[i].getName()))
								&& !(cmbxGasCompany3.getValue().equals(gasStationArr[i].getName())))
							al.add(gasStationArr[i].getName());
					list_gas = FXCollections.observableArrayList(al);
					cmbxGasCompany1.setItems(list_gas);
				}
			}
		}
	}

	/**
	 * The function sets the first and third gas companies combo boxes
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void cmbxGasCompany2Clicked(MouseEvent event) {
		ObservableList<String> list_gas;
		ArrayList<String> al = new ArrayList<String>();
		if (cmbxGasCompany1.getValue() == null && cmbxGasCompany3.getValue() == null) {
			for (int i = 0; i < gasStationArr.length; i++)
				al.add(gasStationArr[i].getName());
			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany2.setItems(list_gas);
		} else if (cmbxGasCompany1.getValue() == null && cmbxGasCompany3.getValue() != null) { // cmbx gas company 3 is
																								// not null
			for (int i = 0; i < gasStationArr.length; i++)
				if (!(cmbxGasCompany3.getValue().equals(gasStationArr[i].getName())))
					al.add(gasStationArr[i].getName());
			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany2.setItems(list_gas);
		} else if (cmbxGasCompany3.getValue() == null && cmbxGasCompany1.getValue() != null) {// cmbx gas company 1 is
																								// not null
			for (int i = 0; i < gasStationArr.length; i++)
				if (!(cmbxGasCompany1.getValue().equals(gasStationArr[i].getName())))
					al.add(gasStationArr[i].getName());
			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany2.setItems(list_gas);
		} else {
			for (int i = 0; i < gasStationArr.length; i++)
				if (!(cmbxGasCompany1.getValue().equals(gasStationArr[i].getName()))
						&& !(cmbxGasCompany3.getValue().equals(gasStationArr[i].getName())))
					al.add(gasStationArr[i].getName());
			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany2.setItems(list_gas);
		}
	}

	/**
	 * The function sets the first and second gas companies combo boxes
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void cmbxGasCompany3Clicked(MouseEvent event) {
		ObservableList<String> list_gas;
		ArrayList<String> al = new ArrayList<String>();
		if (cmbxGasCompany1.getValue() == null && cmbxGasCompany2.getValue() == null) {
			for (int i = 0; i < gasStationArr.length; i++)
				al.add(gasStationArr[i].getName());
			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany3.setItems(list_gas);
		} else if (cmbxGasCompany1.getValue() == null && cmbxGasCompany2.getValue() != null) { // cmbx gas company 2 is
																								// not null
			for (int i = 0; i < gasStationArr.length; i++)
				if (!(cmbxGasCompany2.getValue().equals(gasStationArr[i].getName())))
					al.add(gasStationArr[i].getName());
			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany3.setItems(list_gas);

		} else if (cmbxGasCompany2.getValue() == null && cmbxGasCompany1.getValue() != null) {// cmbx gas company 1 is
																								// not null
			for (int i = 0; i < gasStationArr.length; i++)
				if (!(cmbxGasCompany1.getValue().equals(gasStationArr[i].getName())))
					al.add(gasStationArr[i].getName());
			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany3.setItems(list_gas);
		} else {// cmbx gas company 1 and 2 is not null
			for (int i = 0; i < gasStationArr.length; i++)
				if (!(cmbxGasCompany1.getValue().equals(gasStationArr[i].getName()))
						&& !(cmbxGasCompany2.getValue().equals(gasStationArr[i].getName())))
					al.add(gasStationArr[i].getName());
			list_gas = FXCollections.observableArrayList(al);
			cmbxGasCompany3.setItems(list_gas);
		}
	}

	/**
	 * The function sends to the server controller the updated purchase plan
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void updateFunction(MouseEvent event) {
		if (tablePlans.getSelectionModel().getSelectedItem() != null) {
			int customerId = tablePlans.getSelectionModel().getSelectedItem().getCustomerID();
			txtErrorGasCompany.setText("");
			ArrayList<ChainOfGas> arrGasStation = new ArrayList<ChainOfGas>();
			if (cmbxGasCompany1.getValue() != null)
				arrGasStation.add(new ChainOfGas(cmbxGasCompany1.getValue()));
			if (cmbxGasCompany2.getValue() != null)
				arrGasStation.add(new ChainOfGas(cmbxGasCompany2.getValue()));
			if (cmbxGasCompany3.getValue() != null)
				arrGasStation.add(new ChainOfGas(cmbxGasCompany3.getValue()));

			if (planTypeExclusive.isSelected()) {
				if (cmbxGasCompany1.getValue() == null)
					txtErrorGasCompany.setText("* You must choose a gas station");
				else {
					ClientUI.chat.accept(new PurchasePlan(customerId, "Exclusive", arrGasStation));
					popAlert("Success", "Purchase plan updated to exclusive successfuly");
					myClear();
				}

			} else if (planTypeMultiple.isSelected()) {
				if ((cmbxGasCompany1.getValue() == null && cmbxGasCompany2.getValue() == null)
						|| (cmbxGasCompany1.getValue() == null && cmbxGasCompany3.getValue() == null)
						|| (cmbxGasCompany2.getValue() == null && cmbxGasCompany3.getValue() == null))
					txtErrorGasCompany.setText("* You must choose atleast two gas stations");
				else {
					ClientUI.chat.accept(new PurchasePlan(customerId, "Multiple", arrGasStation));
					popAlert("Success", "Purchase plan updated to multiple successfuly");
					myClear();

				}
			} else {
				ClientUI.chat.accept(new PurchasePlan(customerId, "None", arrGasStation));
				popAlert("Success", "Purchase plan updated to none successfuly");
				myClear();
			}
		}

	}

	/**
	 * The function pops an alert to the user based on the case (success/error)
	 * 
	 * @param msg1 the windows title
	 * @param msg2 the windows content
	 */
	public void popAlert(String msg1, String msg2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(msg1);
		alert.setHeaderText(null);
		alert.setContentText(msg2);
		alert.show();
	}

	/**
	 * This method clears the form
	 * 
	 */
	public void myClear() {
		PlanType.selectToggle(planTypeNone);
		cmbxGasCompany1.setValue(null);
		cmbxGasCompany2.setValue(null);
		cmbxGasCompany3.setValue(null);
		cmbxGasCompany1.setDisable(true);
		cmbxGasCompany2.setDisable(true);
		cmbxGasCompany3.setDisable(true);
		txtErrorGasCompany.setText("");
		initTable();
	}

	/**
	 * 
	 * This method calls our clear function
	 * 
	 * @param event The event we handle
	 */

	@FXML
	public void clearAllFields(MouseEvent event) {
		myClear();
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
