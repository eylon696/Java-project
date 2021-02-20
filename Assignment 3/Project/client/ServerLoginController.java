package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import Server.ServerUI;

//New imports

import javafx.scene.layout.BorderPane;

/**
 * The class ServerLoginController defined how the server login GUI would work
 * 
 * @author Shoval David
 * 
 */
public class ServerLoginController {
	private int alreadyConnectedFlag = 0;

	@FXML
	private Button buttonExit = null;
	@FXML
	private Button buttonMinimize = null;
	@FXML
	private Button buttonConnect = null;
	@FXML
	private TextField portTxt;
	@FXML
	private BorderPane myPane;

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
	public void minimize(ActionEvent event) {
		FunctionForGUI.minimizePane(event, myPane);
	}

	/**
	 * This function is making connection to the server, if the user already logged
	 * in the function will notify him In inserting wrong port the function will
	 * notify the user and let him insert a new port again
	 * 
	 * @param event - the event we handle
	 * @throws Exception
	 */
	public void Connect(ActionEvent event) throws Exception {
		String port = portTxt.getText(); // Get port
		String defaultPort = String.valueOf(ServerUI.DEFAULT_PORT); // Get the default port.

		if (port.trim().isEmpty()) // Nothing entered as port.
			FunctionForGUI.makeNotification("/Images/XNotification.png", "Error", "You must enter a port number.");
		else if (!port.equals(defaultPort))
			FunctionForGUI.makeNotification("/Images/XNotification.png", "Error", "Wrong port, try again.");
		else if (port.equals(defaultPort) && alreadyConnectedFlag == 0) {
			FunctionForGUI.makeNotification("/Images/Tick.png", "Connected",
					"You have been connected to port: " + port + ".");
			ServerUI.runServer(port);
			alreadyConnectedFlag = 1;
		} else // We are already connected.
			FunctionForGUI.makeNotification("/Images/infoIcon.png", "Information",
					"You are already connected to port: " + port + ".");
	}

	/**
	 * This function opens the GUI of the server
	 * 
	 * @param primaryStage - our primary stage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerLogin.fxml"));
		Scene scene = new Scene(root);
		FunctionForGUI functionForGUI = new FunctionForGUI();
		functionForGUI.removeDefaultBarMenu(primaryStage);
		functionForGUI.MakeScreenDragble(root, primaryStage);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

}