package client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import client.ClientUI;
import client.MyFuelClient;
import entity.Purchase;
import entity.PurchasesReport;
import entity.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * This class PurchasesReportControllerClient defined how the PurchasesReport
 * window would work
 * 
 * @author gal
 *
 */
public class PurchasesReportControllerClient implements Initializable {
	PurchasesReport[] arrPurchasesReport;
	ObservableList<PurchasesReport> arrListPurchasesReport = FXCollections.observableArrayList();
	Purchase[] arrPurchases;
	Vehicle[] arrVehicles;
	public String employeeConnectChain = UserLoginController.employee.getOrganizationalAffiliation();
	public String employeeConnectStation = UserLoginController.employee.getGasStation();
	int quarter;
	
	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnSave;

	@FXML
	private DatePicker dpStartDate;

	@FXML
	private DatePicker dpEndDate;

	@FXML
	private TableView<PurchasesReport> tablePurchases;

	@FXML
	private TableColumn<PurchasesReport, String> clmnFuelType;

	@FXML
	private TableColumn<PurchasesReport, Integer> clmnNumOfPurchases;

	@FXML
	private TableColumn<PurchasesReport, Double> clmnTotalSumOfPurchases;

	@FXML
	private TextField txtChainName;

	@FXML
	private TextField txtStationName;

	@FXML
	private Label lblText;

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
	 * This method initialize the purchases report screen when it opened. Takes data
	 * of purchases from DB and presents the data by the suitable quarter
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtChainName.setText(employeeConnectChain);
		txtStationName.setText(employeeConnectStation);
		txtChainName.setEditable(false);
		txtStationName.setEditable(false);
		checkQuarter();
		dpStartDate.setDisable(true);
		dpStartDate.setStyle("-fx-opacity: 1");
		dpStartDate.getEditor().setStyle("-fx-opacity: 1");
		dpEndDate.setDisable(true);
		dpEndDate.setStyle("-fx-opacity: 1");
		dpEndDate.getEditor().setStyle("-fx-opacity: 1");

		clmnFuelType.setCellValueFactory(new PropertyValueFactory<PurchasesReport, String>("fuelType"));
		clmnNumOfPurchases.setCellValueFactory(new PropertyValueFactory<PurchasesReport, Integer>("numOfPurchases"));
		clmnTotalSumOfPurchases
				.setCellValueFactory(new PropertyValueFactory<PurchasesReport, Double>("totalSumOfPurchases"));
		tablePurchases.setItems(arrListPurchasesReport);

		ClientUI.chat.accept("showPurchases");
		arrPurchases = (Purchase[]) MyFuelClient.msgFromServer;

		ClientUI.chat.accept("showVehicles");
		arrVehicles = (Vehicle[]) MyFuelClient.msgFromServer;

		setTable();
	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backClick(MouseEvent event) {
		changeScreen("/gui/MakingReportsForm.fxml");
	}

	/**
	 * This method presents the data by the suitable quarter(by the calculation in
	 * the method)
	 */
	private void setTable() {
		arrListPurchasesReport = FXCollections.observableArrayList();
		tablePurchases.setItems(arrListPurchasesReport);
		int i, j;
		int countBenzene = 0, countDiesel = 0, countScooter = 0;
		double sumBenzene = 0, sumDiesel = 0, sumScooter = 0;
		LocalDate localDateForIndex;
		arrPurchasesReport = new PurchasesReport[3];
		for (i = 0; i < arrPurchases.length; i++) {
			// LocalDate for DatePurchase index
			localDateForIndex = fromStringDBToLocalDate(arrPurchases[i].getDatePurchase());
			//
			for (j = 0; j < arrVehicles.length; j++) {
				if (checkIfPurchaseExistsOnChosenDates(localDateForIndex)) {
					if (arrPurchases[i].getChainName().equals(employeeConnectChain)
							&& arrPurchases[i].getStationName().equals(employeeConnectStation)) {
						if (arrPurchases[i].getCustomerId() == arrVehicles[j].getCustomerId()
								&& arrPurchases[i].getLicenseNum() == arrVehicles[j].getLicensePlateNumber()) {
							if (arrVehicles[j].getFuelType().equals("benzene")) {
								countBenzene++;
								sumBenzene = sumBenzene + arrPurchases[i].getPricePurchase();
							} else if (arrVehicles[j].getFuelType().equals("diesel")) {
								countDiesel++;
								sumDiesel = sumDiesel + arrPurchases[i].getPricePurchase();
							} else if (arrVehicles[j].getFuelType().contains("scooter")) { // scooter
								countScooter++;
								sumScooter = sumScooter + arrPurchases[i].getPricePurchase();
							}
						}
					}
				}
			}
		}
		arrPurchasesReport[0] = new PurchasesReport("benzene", countBenzene, sumBenzene, employeeConnectChain,
				employeeConnectStation, quarter, LocalDate.now().getYear());
		arrPurchasesReport[1] = new PurchasesReport("diesel", countDiesel, sumDiesel, employeeConnectChain,
				employeeConnectStation, quarter, LocalDate.now().getYear());
		arrPurchasesReport[2] = new PurchasesReport("scooter", countScooter, sumScooter, employeeConnectChain,
				employeeConnectStation, quarter, LocalDate.now().getYear());

		for (i = 0; i < arrPurchasesReport.length; i++) {
			arrListPurchasesReport.add(arrPurchasesReport[i]);
		}
		tablePurchases.setItems(arrListPurchasesReport);
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
	 * This method checks the suitable quarter for presenting the data by the
	 * current date
	 */
	private void checkQuarter() {
		LocalDate today = LocalDate.now();
		if (((LocalDate.of(today.getYear(), 1, 1)).isBefore(today)
				|| (LocalDate.of(today.getYear(), 1, 1)).isEqual(today))
				&& ((LocalDate.of(today.getYear(), 3, 31)).isAfter(today)
						|| (LocalDate.of(today.getYear(), 3, 31).isEqual(today)))) {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 1, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 3, 31));
			lblText.setText("Purchase report for quarter 1, to save press Save");
			quarter = 1;
		} else if (((LocalDate.of(today.getYear(), 4, 1)).isBefore(today)
				|| (LocalDate.of(today.getYear(), 4, 1)).isEqual(today))
				&& ((LocalDate.of(today.getYear(), 6, 30)).isAfter(today)
						|| (LocalDate.of(today.getYear(), 6, 30).isEqual(today)))) {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 4, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 6, 30));
			quarter = 2;
			lblText.setText("Purchase report for quarter 2, to save press Save");
		} else if (((LocalDate.of(today.getYear(), 7, 1)).isBefore(today)
				|| (LocalDate.of(today.getYear(), 7, 1)).isEqual(today))
				&& ((LocalDate.of(today.getYear(), 9, 30)).isAfter(today)
						|| (LocalDate.of(today.getYear(), 9, 30).isEqual(today)))) {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 7, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 9, 30));
			lblText.setText("Purchase report for quarter 3, to save press Save");
			quarter = 3;
		} else {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 10, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 12, 31));
			lblText.setText("Purchase report for quarter 4, to save press Save");
			quarter = 4;
		}
	}

	/**
	 * This method checks if the purchase date exists on the chosen dates from the
	 * date picker
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
	 * This method saves the data in the DB and presents message of successful to
	 * the user
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void saveClick(MouseEvent event) {
		ClientUI.chat.accept(arrPurchasesReport);
		if ((boolean) MyFuelClient.msgFromServer)
			showAlert("Success", "The report has been saved");
	}

	/**
	 * This method shows pop up alert with suitable message to the user
	 * 
	 * @param msg1 The title of the alert
	 * @param msg2 The content text of the alert
	 */
	public void showAlert(String msg1, String msg2) { // Maybe we don't need that
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(msg1);
		alert.setHeaderText(null);
		alert.setContentText(msg2);
		alert.show();
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
