package client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.Customer;
import entity.PeriodicCharacterizationReportForTable;
import entity.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

/**
 * This class PeriodicCharacterizationReportControllerClient defined how the
 * PeriodicCharacterizationReport window would work
 * 
 * @author gal
 *
 */
public class PeriodicCharacterizationReportControllerClient implements Initializable {
	PeriodicCharacterizationReportForTable[] arrPurchasesForTable;
	PeriodicCharacterizationReportForTable[] arrPurchasesForTableToRemove;
	PeriodicCharacterizationReportForTable[] arrForTable;
	ObservableList<PeriodicCharacterizationReportForTable> arrListPurchases = FXCollections.observableArrayList();
	Purchase[] arrPurchases;
	PeriodicCharacterizationReportForTable[] arrPurchasesForTableNoDuplicates;
	PeriodicCharacterizationReportForTable[] arrToShow;
	Customer[] arrCustomers;
	
	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private DatePicker dpStartDate;

	@FXML
	private DatePicker dpEndDate;

	@FXML
	private TableView<PeriodicCharacterizationReportForTable> tablePurchases;

	@FXML
	private TableColumn<PeriodicCharacterizationReportForTable, String> clmnFirstName;

	@FXML
	private TableColumn<PeriodicCharacterizationReportForTable, String> clmnLastName;

	@FXML
	private TableColumn<PeriodicCharacterizationReportForTable, Integer> clmnCustomerID;

	@FXML
	private TableColumn<PeriodicCharacterizationReportForTable, Integer> clmnNumOfPurchases;

	@FXML
	private TableColumn<PeriodicCharacterizationReportForTable, String> clmnChainName;

	@FXML
	private ComboBox<String> cmbChainName;

	@FXML
	private Label lblError;

	@FXML
	private Label lblClearFields;

	@FXML
	private Button Minus;
	@FXML
	private Button X;

	/**
	 * This function exit the current scene (exit the application)
	 * 
	 * @param event
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
	 * This method initialize the PeriodicCharacterizationReport screen when it
	 * opened. Takes data of purchases report from DB and presents the suitable data
	 * by the chosen dates.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setDatePickerFormat(dpStartDate);
		setDatePickerFormat(dpEndDate);
		dpStartDate.setEditable(false);
		dpEndDate.setEditable(false);
		clmnFirstName.setCellValueFactory(
				new PropertyValueFactory<PeriodicCharacterizationReportForTable, String>("firstName"));
		clmnLastName.setCellValueFactory(
				new PropertyValueFactory<PeriodicCharacterizationReportForTable, String>("lastName"));
		clmnCustomerID.setCellValueFactory(
				new PropertyValueFactory<PeriodicCharacterizationReportForTable, Integer>("customerID"));
		clmnNumOfPurchases.setCellValueFactory(
				new PropertyValueFactory<PeriodicCharacterizationReportForTable, Integer>("numOfPurchases"));
		clmnChainName.setCellValueFactory(
				new PropertyValueFactory<PeriodicCharacterizationReportForTable, String>("chainName"));
		tablePurchases.setItems(arrListPurchases);

		ClientUI.chat.accept("showPurchases");
		arrPurchases = (Purchase[]) MyFuelClient.msgFromServer;

		ClientUI.chat.accept("showCustomers");
		arrCustomers = (Customer[]) MyFuelClient.msgFromServer;
	}

	/**
	 * This method sets the date in the date picker by suitable format
	 * 
	 * @param dp Date of date picker
	 */
	public void setDatePickerFormat(DatePicker dp) {
		String pattern = "dd/MM/yyyy";

		dp.setPromptText(pattern.toLowerCase());
		dp.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backClick(MouseEvent event) {
		changeScreen("/gui/ProduceReports.fxml");
	}

	/**
	 * This method shows suitable data when chosen correct chain name
	 * 
	 * @param event The event we handle
	 */

	@FXML
	void cmbClickChain(ActionEvent event) {
		if (cmbChainName.getValue() != null) {
			arrListPurchases.clear();
			if (cmbChainName.getValue().equals("All")) {
				for (int i = 0; i < arrToShow.length; i++) {
					arrListPurchases.add(arrToShow[i]);
				}
				clmnNumOfPurchases.setSortType(TableColumn.SortType.DESCENDING);
				tablePurchases.getSortOrder().add(clmnNumOfPurchases);
				tablePurchases.sort();
				tablePurchases.setItems(arrListPurchases);
			} else {

				for (int i = 0; i < arrToShow.length; i++) {
					if (arrToShow[i].getChainName().equals(cmbChainName.getValue()))
						arrListPurchases.add(arrToShow[i]);
				}
				clmnNumOfPurchases.setSortType(TableColumn.SortType.DESCENDING);
				tablePurchases.getSortOrder().add(clmnNumOfPurchases);
				tablePurchases.sort();
				tablePurchases.setItems(arrListPurchases);
			}
		}
	}

	/**
	 * This method shows chain names
	 */
	public void setChainCmbx() {
		ObservableList<String> list;
		int i;
		ArrayList<String> al = new ArrayList<String>();
		for (i = 0; i < arrToShow.length; i++) {
			if (!al.contains(arrToShow[i].getChainName()))
				al.add(arrToShow[i].getChainName());
		}
		al.add("All");
		list = FXCollections.observableArrayList(al);
		cmbChainName.setItems(list);
		cmbChainName.setValue("All");
	}

	/**
	 * This method clears the form
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clearClick(MouseEvent event) {
		changeScreen("/gui/PeriodicCharacterizationReportForm.fxml");
	}

	/**
	 * This method shows suitable data when chosen correct start and end dates
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clickFromDate(ActionEvent event) {
		lblError.setText("");
		arrListPurchases = FXCollections.observableArrayList();
		tablePurchases.setItems(arrListPurchases);
		if (dpStartDate.getValue() != null && dpEndDate.getValue() != null) {
			if (fromStringDatePickerToLocalDate(String.valueOf(dpStartDate.getValue()))
					.isAfter(fromStringDatePickerToLocalDate(String.valueOf(dpEndDate.getValue()))))
				lblError.setText("* You must choose a valid date");
			else
				setTable();
		}
	}

	/**
	 * This method shows suitable data when chosen correct start and end dates
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clickToDate(ActionEvent event) {
		lblError.setText("");
		arrListPurchases = FXCollections.observableArrayList();
		tablePurchases.setItems(arrListPurchases);
		if (dpStartDate.getValue() != null && dpEndDate.getValue() != null) {
			if (fromStringDatePickerToLocalDate(String.valueOf(dpStartDate.getValue()))
					.isAfter(fromStringDatePickerToLocalDate(String.valueOf(dpEndDate.getValue()))))
				lblError.setText("* You must choose a valid date");
			else
				setTable();
		}
	}

	/**
	 * This method presents the suitable data in sort order(by the number of
	 * purchases) in the chosen dates
	 */
	private void setTable() {
		int i, j = 0;
		int count = 0, k = 0;
		LocalDate localDateForIndex;

		for (i = 0; i < arrPurchases.length; i++) {
			// LocalDate for DatePurchase index
			localDateForIndex = fromStringDBToLocalDate(arrPurchases[i].getDatePurchase());
			if (checkIfPurchaseExistsOnChosenDates(localDateForIndex))
				count++;
		}
		arrPurchasesForTable = new PeriodicCharacterizationReportForTable[count];
		arrForTable = new PeriodicCharacterizationReportForTable[count];
		for (i = 0; i < arrPurchases.length; i++) {
			// LocalDate for DatePurchase index
			localDateForIndex = fromStringDBToLocalDate(arrPurchases[i].getDatePurchase());
			if (checkIfPurchaseExistsOnChosenDates(localDateForIndex)) {
				arrPurchasesForTable[j] = new PeriodicCharacterizationReportForTable(arrPurchases[i].getCustomerId(), 0,
						arrPurchases[i].getChainName());
				j++;
			}
		}
		arrPurchasesForTableToRemove = new PeriodicCharacterizationReportForTable[arrPurchasesForTable.length];
		for (i = 0; i < arrPurchasesForTableToRemove.length; i++)
			arrPurchasesForTableToRemove[i] = arrPurchasesForTable[i];
		arrPurchasesForTableNoDuplicates = removeDuplicates(arrPurchasesForTableToRemove);
		count = 0;
		for (i = 0; i < arrPurchasesForTableNoDuplicates.length; i++) {
			for (j = 0; j < arrPurchasesForTable.length; j++) {
				if (arrPurchasesForTableNoDuplicates[i].getCustomerID() == arrPurchasesForTable[j].getCustomerID()) {
					if (arrPurchasesForTable[j].getChainName()
							.equals(arrPurchasesForTableNoDuplicates[i].getChainName())) {

						arrPurchasesForTableNoDuplicates[i]
								.setNumOfPurchases(arrPurchasesForTableNoDuplicates[i].getNumOfPurchases() + 1);
					} else if (arrPurchasesForTable[j].getChainName()
							.equals(arrPurchasesForTableNoDuplicates[i].getChainName())) {

						arrPurchasesForTableNoDuplicates[i]
								.setNumOfPurchases(arrPurchasesForTableNoDuplicates[i].getNumOfPurchases() + 1);
					} else if (arrPurchasesForTable[j].getChainName()
							.equals(arrPurchasesForTableNoDuplicates[i].getChainName())) {

						arrPurchasesForTableNoDuplicates[i]
								.setNumOfPurchases(arrPurchasesForTableNoDuplicates[i].getNumOfPurchases() + 1);

					}
				}
			}

		}

		arrToShow = new PeriodicCharacterizationReportForTable[arrPurchasesForTableNoDuplicates.length];
		for (i = 0; i < arrPurchasesForTableNoDuplicates.length; i++) {
			for (j = 0; j < arrCustomers.length; j++) {
				if (arrCustomers[j].getID() == arrPurchasesForTableNoDuplicates[i].getCustomerID()) {
					arrToShow[k] = new PeriodicCharacterizationReportForTable(
							arrPurchasesForTableNoDuplicates[i].getCustomerID(),
							arrPurchasesForTableNoDuplicates[i].getNumOfPurchases(),
							arrPurchasesForTableNoDuplicates[i].getChainName(), arrCustomers[j].getFirstName(),
							arrCustomers[j].getLastName());
					k++;
				}
			}
		}
		for (i = 0; i < arrToShow.length; i++) {
			arrListPurchases.add(arrToShow[i]);
		}
		clmnNumOfPurchases.setSortType(TableColumn.SortType.DESCENDING);
		tablePurchases.getSortOrder().add(clmnNumOfPurchases);
		tablePurchases.sort();
		tablePurchases.setItems(arrListPurchases);
		setChainCmbx();
	}

	/**
	 * This method gets array and removes the duplicate values inside him by the
	 * chosen key to remove
	 * 
	 * @param arr The chosen array to remove duplicates from him
	 * @return The array without the duplicates
	 */
	public static PeriodicCharacterizationReportForTable[] removeDuplicates(
			PeriodicCharacterizationReportForTable[] arr) {
		int end = arr.length;
		for (int i = 0; i < end; i++) {
			for (int j = i + 1; j < end; j++) {
				if (arr[i].getCustomerID() == arr[j].getCustomerID()
						&& arr[i].getChainName().equals(arr[j].getChainName())) {
					int shiftLeft = j;
					for (int k = j + 1; k < end; k++, shiftLeft++) {
						arr[shiftLeft] = arr[k];
					}
					end--;
					j--;
				}
			}
		}
		PeriodicCharacterizationReportForTable[] whitelist = new PeriodicCharacterizationReportForTable[end];
		for (int i = 0; i < end; i++) {
			whitelist[i] = arr[i];
		}
		return whitelist;
	}

	/**
	 * This method checks if purchase exists on the chosen dates
	 * 
	 * @return true or false
	 */
	private boolean checkIfPurchaseExistsOnChosenDates(LocalDate date) {
		// StartDate for LocalDate:
		LocalDate localStartDate = fromStringDatePickerToLocalDate(String.valueOf(dpStartDate.getValue()));
		// EndDate for LocalDate:
		LocalDate localEndDate = fromStringDatePickerToLocalDate(String.valueOf(dpEndDate.getValue()));
		//
		if ((localStartDate.isBefore(date) || localStartDate.isEqual(date))
				&& (localEndDate.isAfter(date) || localEndDate.isEqual(date)))
			return true;
		return false;
	}

	/**
	 * This method takes the date from the date picker in the form and converts him
	 * from StrigDatePicker value to LocalDate value
	 * 
	 * @param str The StringDatePicker value of the date from the form
	 * @return LocalDate value of this date
	 */
	private LocalDate fromStringDatePickerToLocalDate(String str) {
		int i;
		// Date for LocalDate:
		String[] arrDate = str.split("-"); /// 2020-05-14
		int[] arrNumbersDate = new int[arrDate.length];
		for (i = 0; i < arrDate.length; i++)
			arrNumbersDate[i] = Integer.valueOf(arrDate[i]);
		LocalDate localDate = LocalDate.of(arrNumbersDate[0], arrNumbersDate[1], arrNumbersDate[2]);
		return localDate;
	}

	/**
	 * This method takes the date from the DB and converts him from Strig value to
	 * LocalDate value
	 * 
	 * @param str The String value of the date from the DB
	 * @return LocalDate value of this date
	 */
	private LocalDate fromStringDBToLocalDate(String str) {
		int j;
		// LocalDate for DatePurchase index
		String[] arrDateForIndex = str.split("[/]"); /// 05/04/2020
		int[] arrNumbersDateForIndex = new int[arrDateForIndex.length];
		for (j = 0; j < arrDateForIndex.length; j++)
			arrNumbersDateForIndex[j] = Integer.valueOf(arrDateForIndex[j]);
		LocalDate localDateForIndex = LocalDate.of(arrNumbersDateForIndex[2], arrNumbersDateForIndex[1],
				arrNumbersDateForIndex[0]);
		return localDateForIndex;
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