package client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.ProductsInGasStation;
import entity.QuantityOfItemsInStockReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class MakingQuantityOfItemsInStockReportControllerClient defined how the
 * report window would work
 * 
 * @author Eylon
 *
 */
public class MakingQuantityOfItemsInStockReportControllerClient implements Initializable {

	ProductsInGasStation[] arrProducts;
	QuantityOfItemsInStockReport[] arrQuantity;
	ObservableList<QuantityOfItemsInStockReport> arrListQuantity = FXCollections.observableArrayList();
	int quarter;

	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private TableView<QuantityOfItemsInStockReport> tableQuantity;

	@FXML
	private TableColumn<QuantityOfItemsInStockReport, String> clmnFuelType;

	@FXML
	private TableColumn<QuantityOfItemsInStockReport, Double> clmnQuantity;

	@FXML
	private TextField txtChainName;

	@FXML
	private TextField txtStationName;

	@FXML
	private DatePicker dpStartDate;

	@FXML
	private Label lblText;

	@FXML
	private DatePicker dpEndDate;

	@FXML
	private Button btnSave;
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
		changeScreen("/gui/MakingReportsForm.fxml");
	}

	/**
	 * Sends the report to the server controller
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void saveFunction(MouseEvent event) {
		ClientUI.chat.accept(arrQuantity);
		addReportAlert("Success", "The Report has been saved successfuly");

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

	/**
	 * The function shows the relevant data in a table view
	 */
	public void initTable() {
		int i;
		arrListQuantity.clear();
		ClientUI.chat
				.accept("showProductsInGasStationInDB:" + UserLoginController.employee.getOrganizationalAffiliation()
						+ ":" + UserLoginController.employee.getGasStation());
		arrProducts = (ProductsInGasStation[]) MyFuelClient.msgFromServer;
		arrQuantity = new QuantityOfItemsInStockReport[arrProducts.length];
		for (i = 0; i < arrProducts.length; i++) {
			arrListQuantity
					.add(new QuantityOfItemsInStockReport(UserLoginController.employee.getOrganizationalAffiliation(),
							UserLoginController.employee.getGasStation(), arrProducts[i].getFuelType(),
							arrProducts[i].getStockQuantity(), LocalDate.now().getYear(), quarter));
			arrQuantity[i] = new QuantityOfItemsInStockReport(
					UserLoginController.employee.getOrganizationalAffiliation(),
					UserLoginController.employee.getGasStation(), arrProducts[i].getFuelType(),
					arrProducts[i].getStockQuantity(), LocalDate.now().getYear(), quarter);
		}
		tableQuantity.setItems(arrListQuantity);
	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		checkQuarter();
		dpStartDate.setDisable(true);
		dpStartDate.setStyle("-fx-opacity: 1");
		dpStartDate.getEditor().setStyle("-fx-opacity: 1");
		dpEndDate.setDisable(true);
		dpEndDate.setStyle("-fx-opacity: 1");
		dpEndDate.getEditor().setStyle("-fx-opacity: 1");
		clmnFuelType.setCellValueFactory(new PropertyValueFactory<QuantityOfItemsInStockReport, String>("fuelType"));
		clmnQuantity.setCellValueFactory(new PropertyValueFactory<QuantityOfItemsInStockReport, Double>("quantity"));
		tableQuantity.setItems(arrListQuantity);
		initTable();
		txtChainName.setText(UserLoginController.employee.getOrganizationalAffiliation());
		txtStationName.setText(UserLoginController.employee.getGasStation());
		txtChainName.setEditable(false);
		txtStationName.setEditable(false);

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
			lblText.setText("Quantity of items in stock report for quarter 1, to save press Save");
			quarter = 1;
		} else if (((LocalDate.of(today.getYear(), 4, 1)).isBefore(today)
				|| (LocalDate.of(today.getYear(), 4, 1)).isEqual(today))
				&& ((LocalDate.of(today.getYear(), 6, 30)).isAfter(today)
						|| (LocalDate.of(today.getYear(), 6, 30).isEqual(today)))) {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 4, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 6, 30));
			quarter = 2;
			lblText.setText("Quantity of items in stock report for quarter 2, to save press Save");
		} else if (((LocalDate.of(today.getYear(), 7, 1)).isBefore(today)
				|| (LocalDate.of(today.getYear(), 7, 1)).isEqual(today))
				&& ((LocalDate.of(today.getYear(), 9, 30)).isAfter(today)
						|| (LocalDate.of(today.getYear(), 9, 30).isEqual(today)))) {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 7, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 9, 30));
			lblText.setText("Quantity of items in stock report for quarter 3, to save press Save");
			quarter = 3;
		} else {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 10, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 12, 31));
			lblText.setText("Quantity of items in stock report for quarter 4, to save press Save");
			quarter = 4;
		}
	}

	/**
	 * The function pops an alert to the user based on the case (success/error)
	 * 
	 * @param msg1 the windows title
	 * @param msg2 the windows content
	 */
	public void addReportAlert(String msg1, String msg2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(msg1);
		alert.setHeaderText(null);
		alert.setContentText(msg2);
		alert.show();
	}

}
