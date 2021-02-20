package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * The class AboutController defined how the about window would work	
 * @author Shoval David
 *
 */
public class AboutController 
{
	
	@FXML
	Button Minus;
	@FXML
	Button X;
	@FXML
	AnchorPane MyAnchorPane;
	
	/**
	 * This function exit the current scene (exit the application)
	 * @param event , the event we handle
	 */
	@FXML	
	private void exit(ActionEvent event) {FunctionForGUI.exit(event);}
	/**
	 * This function minimize the window
	 * @param event , the even we handle
	 */
	@FXML
	private void minimize(ActionEvent event){FunctionForGUI.minimizeAnchorPane(event, MyAnchorPane);}	
		
}
	
	