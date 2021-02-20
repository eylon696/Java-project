package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.QuantityOfItemsInStockReport;
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
 * The class ShowQuantityInStockReportControllerClient defined how the show
 * quantity report window would work
 * 
 * @author Eylon
 *
 */
public class ShowQuantityInStockReportControllerClient implements Initializable {

	QuantityOfItemsInStockReport[] quantityOfItemsInStockReport;
	ObservableList<QuantityOfItemsInStockReport> arrListQuantityOfItemsInStockReport = FXCollections
			.observableArrayList();
	String[] arrForComboBox;
	ObservableList<String> list;

	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private TableView<QuantityOfItemsInStockReport> tableQuantity;

	@FXML
	private TableColumn<QuantityOfItemsInStockReport, String> clmnStationName;

	@FXML
	private TableColumn<QuantityOfItemsInStockReport, String> clmnFuelType;

	@FXML
	private TableColumn<QuantityOfItemsInStockReport, Double> clmnQuantity;
	@FXML
	private TableColumn<QuantityOfItemsInStockReport, Integer> clmnQuarter;

	@FXML
	private TableColumn<QuantityOfItemsInStockReport, Integer> clmnYear;

	@FXML
	private TextField txtChainName;

	@FXML
	private ComboBox<String> cmbStationName;

	@FXML
	private ComboBox<String> cmbxYear;

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
	 * The function brings the user back to the previous window
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void backFunction(MouseEvent event) {
		changeScreen("/gui/ShowReportsForChainManagerForm.fxml");
	}

	/**
	 * This method presents the suitable data by the users chose in the combo box
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void cmbClickStation(ActionEvent event) {
		int i;
		arrListQuantityOfItemsInStockReport = FXCollections.observableArrayList();
		tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
		if (cmbStationName.getValue() != null) {
			if (cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quantityOfItemsInStockReport.length; i++) {
					if (quantityOfItemsInStockReport[i].getChainName()
							.equals(UserLoginController.employee.getOrganizationalAffiliation()))
						arrListQuantityOfItemsInStockReport.add(quantityOfItemsInStockReport[i]);
				}
				tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
			} else {
				for (i = 0; i < quantityOfItemsInStockReport.length; i++) {
					if (quantityOfItemsInStockReport[i].getChainName()
							.equals(UserLoginController.employee.getOrganizationalAffiliation()))
						if (quantityOfItemsInStockReport[i].getStationName().equals(cmbStationName.getValue()))
							arrListQuantityOfItemsInStockReport.add(quantityOfItemsInStockReport[i]);
				}
				tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
			}
		}

	}

	/**
	 * The function updates the table view and the total revenue based on the year
	 * and station selected
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void clickedYearCmbx(ActionEvent event) {
		int i;
		arrListQuantityOfItemsInStockReport = FXCollections.observableArrayList();
		tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
		if (cmbxYear.getValue() != null && cmbStationName.getValue() != null) {
			if (cmbxYear.getValue().equals("All") && cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quantityOfItemsInStockReport.length; i++) {
					arrListQuantityOfItemsInStockReport.add(quantityOfItemsInStockReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableQuantity.getSortOrder().add(clmnYear);
				tableQuantity.sort();
				tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
			} else if (cmbxYear.getValue().equals("All") && !cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quantityOfItemsInStockReport.length; i++) {
					if (quantityOfItemsInStockReport[i].getStationName().equals(cmbStationName.getValue()))
						arrListQuantityOfItemsInStockReport.add(quantityOfItemsInStockReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableQuantity.getSortOrder().add(clmnYear);
				tableQuantity.sort();
				tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
			} else if (!cmbxYear.getValue().equals("All") && cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quantityOfItemsInStockReport.length; i++) {
					if (quantityOfItemsInStockReport[i].getYear() == Integer.valueOf((cmbxYear.getValue())))
						arrListQuantityOfItemsInStockReport.add(quantityOfItemsInStockReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableQuantity.getSortOrder().add(clmnYear);
				tableQuantity.sort();
				tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
			} else if (!cmbxYear.getValue().equals("All") && !cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quantityOfItemsInStockReport.length; i++) {
					if (quantityOfItemsInStockReport[i].getStationName().equals(cmbStationName.getValue()))
						if (quantityOfItemsInStockReport[i].getYear() == Integer.valueOf((cmbxYear.getValue())))
							arrListQuantityOfItemsInStockReport.add(quantityOfItemsInStockReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableQuantity.getSortOrder().add(clmnYear);
				tableQuantity.sort();
				tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
			}
		}

	}

	/**
	 * The function sets the values inside the year cmbx
	 */

	public void setYearCmbx() {
		int i;
		ArrayList<String> al = new ArrayList<String>();
		for (i = 0; i < quantityOfItemsInStockReport.length; i++) {
			if (!al.contains(String.valueOf(quantityOfItemsInStockReport[i].getYear())))
				al.add(String.valueOf(quantityOfItemsInStockReport[i].getYear()));
		}
		al.add("All");
		list = FXCollections.observableArrayList(al);
		cmbxYear.setItems(list);
	}

	/**
	 * This method sets suitable station name combo box. Can to choose specific
	 * station name or data of all the stations
	 */
	private void setComboBoxStationName() {
		int i;
		ArrayList<String> al = new ArrayList<String>();
		for (i = 0; i < quantityOfItemsInStockReport.length; i++) {
			if (quantityOfItemsInStockReport[i].getChainName()
					.equals(UserLoginController.employee.getOrganizationalAffiliation()))
				al.add(quantityOfItemsInStockReport[i].getStationName());
		}
		ArrayList<String> alNoDuplicates = new ArrayList<String>(new HashSet<>(al));
		alNoDuplicates.add("all Stations");
		list = FXCollections.observableArrayList(alNoDuplicates);
		cmbStationName.setItems(list);
	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		txtChainName.setText(UserLoginController.employee.getOrganizationalAffiliation());
		txtChainName.setEditable(false);
		ClientUI.chat.accept(
				"getQuantityOfItemsInStockReport " + UserLoginController.employee.getOrganizationalAffiliation());
		quantityOfItemsInStockReport = (QuantityOfItemsInStockReport[]) MyFuelClient.msgFromServer;
		clmnStationName
				.setCellValueFactory(new PropertyValueFactory<QuantityOfItemsInStockReport, String>("stationName"));
		clmnFuelType.setCellValueFactory(new PropertyValueFactory<QuantityOfItemsInStockReport, String>("fuelType"));
		clmnQuantity.setCellValueFactory(new PropertyValueFactory<QuantityOfItemsInStockReport, Double>("quantity"));
		clmnQuarter.setCellValueFactory(new PropertyValueFactory<QuantityOfItemsInStockReport, Integer>("quarter"));
		clmnYear.setCellValueFactory(new PropertyValueFactory<QuantityOfItemsInStockReport, Integer>("year"));
		tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
		setComboBoxStationName();
		setYearCmbx();
		cmbxYear.setValue("All");
		cmbStationName.setValue("all Stations");
		for (int i = 0; i < quantityOfItemsInStockReport.length; i++) {
			arrListQuantityOfItemsInStockReport.add(quantityOfItemsInStockReport[i]);
		}
		clmnYear.setSortType(TableColumn.SortType.DESCENDING);
		tableQuantity.getSortOrder().add(clmnYear);
		tableQuantity.sort();
		tableQuantity.setItems(arrListQuantityOfItemsInStockReport);
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
