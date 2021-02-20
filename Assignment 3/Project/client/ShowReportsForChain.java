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
 * 
 * The class ShowReportsForChain defined how the show reports segway window
 * would work
 * 
 * @author Eylon
 *
 */
public class ShowReportsForChain {

	@FXML
	private Pane MyPane;
	@FXML
	private Button ShowPurchasesReport;/// d

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
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backFunction(MouseEvent event) {
		changeScreen("/gui/SystemOperationsChainManager.fxml");
	}

	/**
	 * The function goes to the ShowQuarterlyRevenueReportForm
	 * 
	 * @param event the even we handle
	 */
	@FXML
	void goToQuarterRevenueReportChainManager(MouseEvent event) {
		changeScreen("/gui/ShowQuarterlyRevenueReportForm.fxml");
	}

	/**
	 * The function goes to the ShowPurchasesReportForm
	 * 
	 * @param event the even we handle
	 */
	@FXML
	void clickShowPurchasesReport(MouseEvent event) {
		changeScreen("/gui/ShowPurchasesReportForm.fxml");
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
	 * The function goes to the ShowQuantityInStockReportForm
	 * 
	 * @param event the even we handle
	 */
	@FXML
	void goToQuantityInStockReportChainManager(MouseEvent event) {
		changeScreen("/gui/ShowQuantityInStockReportForm.fxml");
	}
}