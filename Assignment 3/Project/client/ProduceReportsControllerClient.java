package client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * The class ProduceReportsControllerClient defined how the ProduceReports
 * window would work
 * 
 * @author gal
 *
 */
public class ProduceReportsControllerClient {

	@FXML
	private Pane MyPane;

	@FXML
	private AnchorPane MyAnchorPane;	

	@FXML
	private Button btnShowSaleCommentsReport;

	@FXML
	private Button btnCustomerPeriodicReport;

	@FXML
	private Button btnBack;

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
	 * The function goes to the MakingQuarterlyRevenueReportForm
	 * 
	 * @param event the even we handle
	 */
	@FXML
	private void minimize(ActionEvent event) {
		FunctionForGUI.minimizePane(event, MyPane);
	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clickBack(MouseEvent event) {
		changeScreen("/gui/SystemOperationsMarketingManager.fxml");
	}

	/**
	 * This method sends the user to chosen form
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clickCustomerPeriodicReport(MouseEvent event) {
		changeScreen("/gui/PeriodicCharacterizationReportForm.fxml");
	}

	/**
	 * This method sends the user to chosen form
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clickShowSaleCommentsReport(MouseEvent event) {
		changeScreen("/gui/SaleCommentsReport.fxml");
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
