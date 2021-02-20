package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * The class HomeController defined how the home would work
 * 
 * @author Shoval David
 *
 */
public class HomeController {
	@FXML
	Button Minus;
	@FXML
	Button X;
	@FXML
	VBox MyVBox;

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
	 * This function minimize the window(VBox)
	 * 
	 * @param event , the even we handle
	 */
	@FXML
	private void minimize(ActionEvent event) {
		FunctionForGUI.minimizemyVBox(event, MyVBox);
	}
}
