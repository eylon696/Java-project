package client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class MakingReportsControllerClient defined how the making reports segway
 * window would work
 * 
 * @author Eylon
 *
 */
public class MakingReportsControllerClient {

	@FXML
	private Pane MyPane;

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
	 * The function goes to the MakingQuarterlyRevenueReportForm
	 * 
	 * @param event the even we handle
	 */
	@FXML
	void goToQuarterRevenueReport(MouseEvent event) {
		changeScreen("/gui/MakingQuarterlyRevenueReportForm.fxml");

	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backFunction(MouseEvent event) {
		changeScreen("/gui/SystemOperationStationManager.fxml");
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
	 * The function goes to the MakingQuantityOfItemsInStockReportForm
	 * 
	 * @param event the even we handle
	 */
	@FXML
	void goToQuantityOfItemsInStockReport(MouseEvent event) {
		changeScreen("/gui/MakingQuantityOfItemsInStockReportForm.fxml");
	}

	/**
	 * The function goes to the PurchasesReportForm
	 * 
	 * @param event the even we handle
	 */
	@FXML
	void clickPurchasesReport(MouseEvent event) {
		changeScreen("/gui/PurchasesReportForm.fxml");
	}
}
