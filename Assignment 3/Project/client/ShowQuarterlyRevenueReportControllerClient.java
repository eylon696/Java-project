package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.QuarterRevenueReport;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class ShowQuarterlyRevenueReportControllerClient defined how the show
 * quarterly revenue report window would work
 * 
 * @author Eylon
 *
 */	
public class ShowQuarterlyRevenueReportControllerClient implements Initializable {

	QuarterRevenueReport[] quarterRevenueReport;
	ObservableList<QuarterRevenueReport> arrListQuarterRevenueReport = FXCollections.observableArrayList();
	ObservableList<String> list;
	String[] arrForComboBox;

	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private TableView<QuarterRevenueReport> tableReports;

	@FXML
	private TableColumn<QuarterRevenueReport, Integer> clmnYear;

	@FXML
	private TableColumn<QuarterRevenueReport, Integer> clmnQuarter;

	@FXML
	private TableColumn<QuarterRevenueReport, String> clmnStationName;

	@FXML
	private TableColumn<QuarterRevenueReport, Double> clmnRevenue;

	@FXML
	private TextField txtTotalRevenue;

	@FXML
	private TextField txtChainName;

	@FXML
	private ComboBox<String> cmbStationName;

	@FXML
	private ComboBox<String> cmbxYear;

	@FXML
	private Label lblNis;

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
	 * The function goes to Show Purchases Report Form
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void goToPurchasesReport(MouseEvent event) {
		changeScreen("/gui/ShowPurchasesReportForm.fxml");
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
		arrListQuarterRevenueReport = FXCollections.observableArrayList();
		tableReports.setItems(arrListQuarterRevenueReport);
		if (cmbxYear.getValue() != null && cmbStationName.getValue() != null) {
			if (cmbxYear.getValue().equals("All") && cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quarterRevenueReport.length; i++) {
					arrListQuarterRevenueReport.add(quarterRevenueReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableReports.getSortOrder().add(clmnYear);
				tableReports.sort();
				tableReports.setItems(arrListQuarterRevenueReport);
				totalRevenue(-1, null);
			} else if (cmbxYear.getValue().equals("All") && !cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quarterRevenueReport.length; i++) {
					if (quarterRevenueReport[i].getStationName().equals(cmbStationName.getValue()))
						arrListQuarterRevenueReport.add(quarterRevenueReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableReports.getSortOrder().add(clmnYear);
				tableReports.sort();
				tableReports.setItems(arrListQuarterRevenueReport);
				totalRevenue(-1, cmbStationName.getValue());
			} else if (!cmbxYear.getValue().equals("All") && cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quarterRevenueReport.length; i++) {
					if (quarterRevenueReport[i].getYear() == Integer.valueOf((cmbxYear.getValue())))
						arrListQuarterRevenueReport.add(quarterRevenueReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableReports.getSortOrder().add(clmnYear);
				tableReports.sort();
				tableReports.setItems(arrListQuarterRevenueReport);
				totalRevenue(Integer.valueOf(cmbxYear.getValue()), null);
			} else if (!cmbxYear.getValue().equals("All") && !cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quarterRevenueReport.length; i++) {
					if (quarterRevenueReport[i].getStationName().equals(cmbStationName.getValue()))
						if (quarterRevenueReport[i].getYear() == Integer.valueOf((cmbxYear.getValue())))
							arrListQuarterRevenueReport.add(quarterRevenueReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableReports.getSortOrder().add(clmnYear);
				tableReports.sort();
				tableReports.setItems(arrListQuarterRevenueReport);
				totalRevenue(Integer.valueOf(cmbxYear.getValue()), cmbStationName.getValue());
			}
		}

	}

	/**
	 * The function sets the values inside the year cmbx
	 */

	public void setYearCmbx() {
		int i;
		ArrayList<String> al = new ArrayList<String>();
		for (i = 0; i < quarterRevenueReport.length; i++) {
			if (!al.contains(String.valueOf(quarterRevenueReport[i].getYear())))
				al.add(String.valueOf(quarterRevenueReport[i].getYear()));
		}
		al.add("All");
		list = FXCollections.observableArrayList(al);
		cmbxYear.setItems(list);
	}

	/**
	 * This method presents the suitable data by the users chose in the combo box
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void cmbClickStation(ActionEvent event) {
		int i;
		arrListQuarterRevenueReport = FXCollections.observableArrayList();
		tableReports.setItems(arrListQuarterRevenueReport);
		if (cmbxYear.getValue() != null && cmbStationName.getValue() != null) {
			if (cmbxYear.getValue().equals("All") && cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quarterRevenueReport.length; i++) {
					arrListQuarterRevenueReport.add(quarterRevenueReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableReports.getSortOrder().add(clmnYear);
				tableReports.sort();
				tableReports.setItems(arrListQuarterRevenueReport);
				totalRevenue(-1, null);
			} else if (cmbxYear.getValue().equals("All") && !cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quarterRevenueReport.length; i++) {
					if (quarterRevenueReport[i].getStationName().equals(cmbStationName.getValue()))
						arrListQuarterRevenueReport.add(quarterRevenueReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableReports.getSortOrder().add(clmnYear);
				tableReports.sort();
				tableReports.setItems(arrListQuarterRevenueReport);
				totalRevenue(-1, cmbStationName.getValue());
			} else if (!cmbxYear.getValue().equals("All") && cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quarterRevenueReport.length; i++) {
					if (quarterRevenueReport[i].getYear() == Integer.valueOf((cmbxYear.getValue())))
						arrListQuarterRevenueReport.add(quarterRevenueReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableReports.getSortOrder().add(clmnYear);
				tableReports.sort();
				tableReports.setItems(arrListQuarterRevenueReport);
				totalRevenue(Integer.valueOf(cmbxYear.getValue()), null);
			} else if (!cmbxYear.getValue().equals("All") && !cmbStationName.getValue().equals("all Stations")) {
				for (i = 0; i < quarterRevenueReport.length; i++) {
					if (quarterRevenueReport[i].getStationName().equals(cmbStationName.getValue()))
						if (quarterRevenueReport[i].getYear() == Integer.valueOf((cmbxYear.getValue())))
							arrListQuarterRevenueReport.add(quarterRevenueReport[i]);
				}
				clmnYear.setSortType(TableColumn.SortType.DESCENDING);
				tableReports.getSortOrder().add(clmnYear);
				tableReports.sort();
				tableReports.setItems(arrListQuarterRevenueReport);
				totalRevenue(Integer.valueOf(cmbxYear.getValue()), cmbStationName.getValue());
			}
		}

	}

	/**
	 * This method sets suitable station name combo box. Can to choose specific
	 * station name or data of all the stations
	 */
	private void setComboBoxStationName() {
		int i;
		ArrayList<String> al = new ArrayList<String>();
		for (i = 0; i < quarterRevenueReport.length; i++) {
			if (quarterRevenueReport[i].getChainName()
					.equals(UserLoginController.employee.getOrganizationalAffiliation()))
				al.add(quarterRevenueReport[i].getStationName());
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

		lblNis.setText("\u20AA");
		txtChainName.setText(UserLoginController.employee.getOrganizationalAffiliation());
		txtChainName.setEditable(false);
		ClientUI.chat
				.accept("getQuarterRevenueForChain " + UserLoginController.employee.getOrganizationalAffiliation());
		quarterRevenueReport = (QuarterRevenueReport[]) MyFuelClient.msgFromServer;
		txtTotalRevenue.setEditable(false);
		clmnYear.setCellValueFactory(new PropertyValueFactory<QuarterRevenueReport, Integer>("year"));
		clmnQuarter.setCellValueFactory(new PropertyValueFactory<QuarterRevenueReport, Integer>("quarter"));
		clmnStationName.setCellValueFactory(new PropertyValueFactory<QuarterRevenueReport, String>("stationName"));
		clmnRevenue.setCellValueFactory(new PropertyValueFactory<QuarterRevenueReport, Double>("revenue"));
		tableReports.setItems(arrListQuarterRevenueReport);
		setComboBoxStationName();
		setYearCmbx();
		cmbStationName.setValue("all Stations");
		cmbxYear.setValue("All");
		for (int i = 0; i < quarterRevenueReport.length; i++) {
			arrListQuarterRevenueReport.add(quarterRevenueReport[i]);
		}
		clmnYear.setSortType(TableColumn.SortType.DESCENDING);
		tableReports.getSortOrder().add(clmnYear);
		tableReports.sort();
		tableReports.setItems(arrListQuarterRevenueReport);
		totalRevenue(-1, null);
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

	/**
	 * The function shows the relevant data in a table view
	 */
	public void totalRevenue(int year, String station) {
		double sumOfRevenue = 0;
		// arrListQuarterRevenueReport.clear();
		if (year == -1 && station == null) {
			for (int i = 0; i < quarterRevenueReport.length; i++) {
				sumOfRevenue += quarterRevenueReport[i].getRevenue();
			}
		} else if (year == -1 && station != null) {
			for (int i = 0; i < quarterRevenueReport.length; i++) {
				if (quarterRevenueReport[i].getStationName().equals(station))
					sumOfRevenue += quarterRevenueReport[i].getRevenue();
			}
		} else if (year != -1 && station == null) {
			for (int i = 0; i < quarterRevenueReport.length; i++) {
				if (quarterRevenueReport[i].getYear() == year)
					sumOfRevenue += quarterRevenueReport[i].getRevenue();
			}
		} else if (year != -1 && station != null) {
			for (int i = 0; i < quarterRevenueReport.length; i++) {
				if (quarterRevenueReport[i].getStationName().equals(station)
						&& quarterRevenueReport[i].getYear() == year)
					sumOfRevenue += quarterRevenueReport[i].getRevenue();
			}
		}
		txtTotalRevenue.setText(String.valueOf(sumOfRevenue));
	}

}
