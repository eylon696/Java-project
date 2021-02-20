package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
	public static ClientConsole chat;

	public static void main(String args[]) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		chat = new ClientConsole("localhost", 5555);
		UserLoginController userLoginController = new UserLoginController(); // create EmployeeDataFrame
		userLoginController.start(primaryStage);
	}
}
