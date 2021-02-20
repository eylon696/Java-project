package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import client.ClientUI;
import client.MyFuelClient;
import entity.PurchasesReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * This class ShowPurchasesReportControllerClient defined how the
 * ShowPurchasesReport window would work
 * 
 * @author gal
 *
 */
public class ShowPurchasesReportControllerClient implements Initializable {
	PurchasesReport[] arrPurchasesReport;
	ObservableList<PurchasesReport> arrListPurchasesReport = FXCollections.observableArrayList();
	public String employeeConnectChain = UserLoginController.employee.getOrganizationalAffiliation();
	ObservableList<String> list;
	String[] arrForComboBox;

	@FXML
	private Pane MyPane;

	@FXML	
	private Button btnBack;

	@FXML
	private TableView<PurchasesReport> tablePurchasesReport;

	@FXML
	private TableColumn<PurchasesReport, String> clmnStationName;

	@FXML
	private TableColumn<PurchasesReport, String> clmnFuelType;

	@FXML
	private TableColumn<PurchasesReport, Integer> clmnNumOfPurchases;

	@FXML
	private TableColumn<PurchasesReport, Double> clmnTotalSumOfPurchases;

	@FXML
	private TableColumn<PurchasesReport, Integer> clmnQuarter;

	@FXML
	private TableColumn<PurchasesReport, Integer> clmnYear;

	@FXML
	private TextField txtChainName;

	@FXML
	private ComboBox<String> cmbStationName;

	@FXML
	private ComboBox<String> cmbFuelType;
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

	/**
	 * This method initialize the show purchases report screen when it opened. Takes
	 * data of purchases report from DB and presents the data
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int i;
		arrListPurchasesReport.clear();
		txtChainName.setText(employeeConnectChain);
		txtChainName.setEditable(false);

		clmnStationName.setCellValueFactory(new PropertyValueFactory<PurchasesReport, String>("stationName"));
		clmnFuelType.setCellValueFactory(new PropertyValueFactory<PurchasesReport, String>("fuelType"));
		clmnNumOfPurchases.setCellValueFactory(new PropertyValueFactory<PurchasesReport, Integer>("numOfPurchases"));
		clmnTotalSumOfPurchases
				.setCellValueFactory(new PropertyValueFactory<PurchasesReport, Double>("totalSumOfPurchases"));
		clmnQuarter.setCellValueFactory(new PropertyValueFactory<PurchasesReport, Integer>("quarter"));
		clmnYear.setCellValueFactory(new PropertyValueFactory<PurchasesReport, Integer>("year"));
		tablePurchasesReport.setItems(arrListPurchasesReport);

		ClientUI.chat.accept("showPurchasesReport");
		arrPurchasesReport = (PurchasesReport[]) MyFuelClient.msgFromServer;

		cmbStationName.setValue("all Stations");
		cmbFuelType.setValue("All");

		for (i = 0; i < arrPurchasesReport.length; i++) {
			if (arrPurchasesReport[i].getChainName().equals(employeeConnectChain))
				arrListPurchasesReport.add(arrPurchasesReport[i]);
		}
		tablePurchasesReport.setItems(arrListPurchasesReport);
		setComboBoxStationName();
		setFuelTypeCmbx();
//		}
	}

	/**
	 * This method sets suitable station name combo box. Can to choose specific
	 * station name or data of all the stations
	 */
	private void setComboBoxStationName() {
		int i;
		ArrayList<String> al = new ArrayList<String>();
		for (i = 0; i < arrPurchasesReport.length; i++) {
			if (arrPurchasesReport[i].getChainName().equals(employeeConnectChain))
				al.add(arrPurchasesReport[i].getStationName());
		}
		ArrayList<String> alNoDuplicates = new ArrayList<String>(new HashSet<>(al));
		alNoDuplicates.add("all Stations");
		list = FXCollections.observableArrayList(alNoDuplicates);
		cmbStationName.setItems(list);
	}

	/**
	 * This method shows suitable data when chosen correct fuel type
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void cmbClickFuelType(ActionEvent event) {
		if (cmbFuelType.getValue() != null)
			checkCasesOfStationNameAndFuelType();
	}

	/**
	 * This method shows fuel types
	 */
	public void setFuelTypeCmbx() {
		ObservableList<String> list;
		int i;
		ArrayList<String> al = new ArrayList<String>();
		for (i = 0; i < arrPurchasesReport.length; i++) {
			if (!al.contains(arrPurchasesReport[i].getFuelType()))
				al.add(arrPurchasesReport[i].getFuelType());
		}
		al.add("All");
		list = FXCollections.observableArrayList(al);
		cmbFuelType.setItems(list);
	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backClick(MouseEvent event) {
		changeScreen("/gui/ShowReportsForChainManagerForm.fxml");
	}

	/**
	 * This method presents the suitable data by the users chose in the combo box
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void cmbClickStation(ActionEvent event) {
		if (cmbStationName.getValue() != null)
			checkCasesOfStationNameAndFuelType();
	}

	/**
	 * This method check cases of Station Name and Fuel Type and shows the relevant
	 * data in the table view
	 */
	private void checkCasesOfStationNameAndFuelType() {
		arrListPurchasesReport.clear();
		int i;
		if (cmbStationName.getValue().equals("all Stations")) {
			if (cmbFuelType.getValue().equals("All")) {
				for (i = 0; i < arrPurchasesReport.length; i++) {
					if (arrPurchasesReport[i].getChainName().equals(employeeConnectChain))
						arrListPurchasesReport.add(arrPurchasesReport[i]);
				}
				tablePurchasesReport.setItems(arrListPurchasesReport);
			} else {
				for (i = 0; i < arrPurchasesReport.length; i++) {
					if (arrPurchasesReport[i].getChainName().equals(employeeConnectChain)
							&& arrPurchasesReport[i].getFuelType().equals(cmbFuelType.getValue()))
						arrListPurchasesReport.add(arrPurchasesReport[i]);
				}
				tablePurchasesReport.setItems(arrListPurchasesReport);
			}
		} else {
			if (cmbFuelType.getValue().equals("All")) {
				for (i = 0; i < arrPurchasesReport.length; i++) {
					if (arrPurchasesReport[i].getChainName().equals(employeeConnectChain)
							&& arrPurchasesReport[i].getStationName().equals(cmbStationName.getValue()))
						arrListPurchasesReport.add(arrPurchasesReport[i]);
				}
				tablePurchasesReport.setItems(arrListPurchasesReport);
			} else {
				for (i = 0; i < arrPurchasesReport.length; i++) {
					if (arrPurchasesReport[i].getChainName().equals(employeeConnectChain)
							&& arrPurchasesReport[i].getFuelType().equals(cmbFuelType.getValue())
							&& arrPurchasesReport[i].getStationName().equals(cmbStationName.getValue()))
						arrListPurchasesReport.add(arrPurchasesReport[i]);
				}
				tablePurchasesReport.setItems(arrListPurchasesReport);
			}
			
		}
		tablePurchasesReport.setItems(arrListPurchasesReport);
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