package client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import client.ClientUI;
import client.MyFuelClient;
import entity.Purchase;
import entity.QuarterRevenueReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class MakingQuarterRevenueReportControllerClient defined how the report
 * window would work
 * 
 * @author Eylon
 *
 */
public class MakingQuarterRevenueReportControllerClient implements Initializable {

	Purchase[] purchase;
	QuarterRevenueReport quarterRevenueReport;
	int quarter;

	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private TextField txtRevenue;

	@FXML
	private Button btnSave;

	@FXML
	private DatePicker dpStartDate;

	@FXML
	private DatePicker dpEndDate;

	@FXML
	private TextField txtChainName;

	@FXML
	private TextField txtStationName;

	@FXML
	private Label lblText;

	@FXML
	private Label lblNis;

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
		quarterRevenueReport = new QuarterRevenueReport(UserLoginController.employee.getOrganizationalAffiliation(),
				UserLoginController.employee.getGasStation(), LocalDate.now().getYear(), quarter,
				Double.valueOf(txtRevenue.getText()));
		ClientUI.chat.accept(quarterRevenueReport);
		addReportAlert("Success", "The Report has been saved successfuly");

	}

	/**
	 * The function returns the updated revenue based on all the parameters
	 * 
	 * @param year        the chosen year
	 * @param quarter     the chosen quarter
	 * @param chainName   the employees chain
	 * @param stationName the employees station
	 * @return the calculated revenue
	 */
	public double updateRevenue(int year, int quarter, String chainName, String stationName) {
		double sumRevenue = 0.0;
		String[] tempArr = new String[3];
		for (int i = 0; i < purchase.length; i++) {
			tempArr = purchase[i].getDatePurchase().split("/");
			if (year == Integer.valueOf(tempArr[2]) && quarter * 3 >= Integer.valueOf(tempArr[1])
					&& (quarter * 3 - 2) <= Integer.valueOf(tempArr[1]) && chainName.equals(purchase[i].getChainName())
					&& stationName.equals(purchase[i].getStationName())) {
				sumRevenue += purchase[i].getPricePurchase();

			}
		}
		return sumRevenue;
	}

	/**
	 * The function goes to Show Purchases Report Form
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void goToPurchasesReport(MouseEvent event) {
		changeScreen("/gui/PurchasesReportForm.fxml");
	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblNis.setText("\u20AA");
		txtChainName.setText(UserLoginController.employee.getOrganizationalAffiliation());
		txtStationName.setText(UserLoginController.employee.getGasStation());
		txtChainName.setEditable(false);
		txtStationName.setEditable(false);
		checkQuarter();
		dpStartDate.setDisable(true);
		dpStartDate.setStyle("-fx-opacity: 1");
		dpStartDate.getEditor().setStyle("-fx-opacity: 1");
		dpEndDate.setDisable(true);
		dpEndDate.setStyle("-fx-opacity: 1");
		dpEndDate.getEditor().setStyle("-fx-opacity: 1");
		quarterRevenueReport = null;
		txtRevenue.setEditable(false);
		ClientUI.chat.accept("showPurchases");
		purchase = (Purchase[]) MyFuelClient.msgFromServer;
		txtRevenue.setText(String.valueOf(updateRevenue(LocalDate.now().getYear(), quarter,
				UserLoginController.employee.getOrganizationalAffiliation(),
				UserLoginController.employee.getGasStation())));

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
			lblText.setText("Quarterly revenue report for quarter 1, to save press Save");
			quarter = 1;
		} else if (((LocalDate.of(today.getYear(), 4, 1)).isBefore(today)
				|| (LocalDate.of(today.getYear(), 4, 1)).isEqual(today))
				&& ((LocalDate.of(today.getYear(), 6, 30)).isAfter(today)
						|| (LocalDate.of(today.getYear(), 6, 30).isEqual(today)))) {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 4, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 6, 30));
			quarter = 2;
			lblText.setText("Quarterly revenue report for quarter 2, to save press Save");
		} else if (((LocalDate.of(today.getYear(), 7, 1)).isBefore(today)
				|| (LocalDate.of(today.getYear(), 7, 1)).isEqual(today))
				&& ((LocalDate.of(today.getYear(), 9, 30)).isAfter(today)
						|| (LocalDate.of(today.getYear(), 9, 30).isEqual(today)))) {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 7, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 9, 30));
			lblText.setText("Quarterly revenue report for quarter 3, to save press Save");
			quarter = 3;
		} else {
			dpStartDate.setValue(LocalDate.of(today.getYear(), 10, 1));
			dpEndDate.setValue(LocalDate.of(today.getYear(), 12, 31));
			lblText.setText("Quarterly revenue report for quarter 4, to save press Save");
			quarter = 4;
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
			MyPane.getChildren().remove(MyPane);
			MyPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
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
