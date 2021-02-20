package client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class HomeHeatingFuelController defined how the segway screen to track
 * and purchase home heating fuel would work
 * 
 * @author Eylon
 *
 */
public class HomeHeatingFuelController {
	@FXML
	private Button Minus;
	@FXML
	private Button X;
	@FXML
	private Pane MyPane;
	@FXML
	private Button btnPurchse;

	@FXML
	private Button btnTrackOrder;

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
	 * The function goes to the PurchaseHomeHeatingFuelForm
	 * 
	 * @param event the even we handle
	 */
	@FXML
	void goToPurchaseHomeHeatingFuelForm(MouseEvent event) {
		changeScreen("/gui/PurchaseHomeHeatingFuelForm.fxml");
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
	 * This function take us to the relevant url
	 * 
	 * @param event the even we handle
	 */
	@FXML
	void goToUrl(MouseEvent event) {
		Desktop d = Desktop.getDesktop();
		try {
			d.browse(new URI("https://en.wikipedia.org/wiki/Heating_oil"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The function goes to the TrackOrderHomeHeatingFuelForm
	 * 
	 * @param event the even we handle
	 */
	@FXML
	void goToTrackOrderHomeHeatingFuelForm(MouseEvent event) {
		changeScreen("/gui/TrackOrderHomeHeatingFuelForm.fxml");
	}
}
