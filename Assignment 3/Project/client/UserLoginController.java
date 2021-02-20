package client;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

import org.controlsfx.dialog.ProgressDialog;

import client.ClientUI;
import client.MyFuelClient;
import entity.Customer;
import entity.Employee;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * The class UserLoginController defined how the application login GUI would
 * work
 * 
 * @author Shoval David
 *
 */
public class UserLoginController {
	public static Stage stage = null;
	public static Customer customer = null; // Static variable for knowing which user are entered
	public static Employee employee = null;
	public static String homePageFxmlName = null;
	public static int licesnsePlateNumber = 0;

	@FXML
	TextField ID;
	@FXML	
	PasswordField Password;
	@FXML
	Button ForgotPassword;
	@FXML
	Button Login;
	@FXML
	Button Minus;
	@FXML
	TextField txtLiscensePlateNumber;
	@FXML
	Button X;
	@FXML
	AnchorPane MyAnchorPane;
	@FXML
	Pane MyPane;

	/**
	 * This function exit the current scene (exit the application)
	 * 
	 * @param event , the event we handle
	 */
	@FXML
	private void exit(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * This function minimize the window
	 * 
	 * @param event , the even we handle
	 */
	@FXML
	private void minimize(ActionEvent event) {
		FunctionForGUI.minimizeAnchorPane(event, MyAnchorPane);
	}

	/**
	 * This function opened a forgot password form
	 * 
	 * @throws Exception
	 */
	@FXML
	private void openForgotPasswordForm() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ForgotPassword.fxml"));
		Scene scene = ForgotPassword.getScene();
		root.translateYProperty().set(scene.getHeight());
		Pane parentContainer = (Pane) scene.getRoot();
		parentContainer.getChildren().add(root);

		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(event -> {
			parentContainer.getChildren().remove(MyPane);
		});
		timeline.play();
	}

	/**
	 * This function in the main function inside the login form, it checks if the
	 * user exists, if he entered the right password and what type of user he is
	 * (etc marketing manager, supplier , customer), by this information it opened
	 * the right GUI for the user.
	 * 
	 * @param event - the event we handle
	 */
	public void Login(ActionEvent event) {
		String IDtxt = ID.getText();
		String Passwordtxt = Password.getText();
		boolean customerExist;
		boolean employeeExist;
		boolean loggedInAlready;
		Customer customer = null;
		Employee employee = null;
		if (IDtxt.isEmpty() || Passwordtxt.isEmpty()) // If the user did not insert one of the fields or both.
			FunctionForGUI.makeNotification("/Images/XNotification.png", "Error", "You must fill all the fields.");
		else {
			ClientUI.chat.accept("LoginQueryUserExist customer " + IDtxt); // Can only send string to this function ,
																			// split happen on echoServer.
			customerExist = (boolean) MyFuelClient.msgFromServer;
			ClientUI.chat.accept("LoginQueryUserExist employee " + IDtxt);
			employeeExist = (boolean) MyFuelClient.msgFromServer;
			if (employeeExist) // need to separate the employee from the user.
			{
				ClientUI.chat.accept("EmployeeLoginQueryPasswordCorrect " + IDtxt + " " + Passwordtxt);
				employee = (Employee) MyFuelClient.msgFromServer;
				if (employee != null) {
					if (customerExist) {
						ClientUI.chat.accept("UserLoginQueryPasswordCorrect " + IDtxt + " " + Passwordtxt);
						UserLoginController.customer = (Customer) MyFuelClient.msgFromServer;
					}
					UserLoginController.employee = employee;
					ClientUI.chat.accept("AddUserToArrayList " + employee.getEmployeeID());
					loggedInAlready = (boolean) MyFuelClient.msgFromServer;
					if (loggedInAlready)// If the user already inside the application.
						FunctionForGUI.makeNotification("/Images/infoIcon.png", "Information",
								"The user: " + employee.getFirstName() + " " + employee.getLastName()
										+ " already connected to the application.");
					else {
						setNotification(true, employee.getFirstName(), employee.getLastName());
						if (customerExist) // If he both Employee and Customer , NEW
							switchScreens(event, "/gui/HomePageEmployeeAndCustomer.fxml");
						else
							switchScreens(event, "/gui/HomePageEmployee.fxml");
					}
				} else
					setNotification(false, null, null);
			} else if (customerExist) {
				ClientUI.chat.accept("UserLoginQueryPasswordCorrect " + IDtxt + " " + Passwordtxt);
				customer = (Customer) MyFuelClient.msgFromServer;

				if (customer != null) {
					UserLoginController.customer = customer;
					ClientUI.chat.accept("AddUserToArrayList " + customer.getID());
					loggedInAlready = (boolean) MyFuelClient.msgFromServer;
					if (loggedInAlready) // If the user already inside the application.
						FunctionForGUI.makeNotification("/Images/infoIcon.png", "Information",
								"The user: " + customer.getFirstName() + " " + customer.getLastName()
										+ " already connected to the application.");
					else {
						setNotification(true, customer.getFirstName(), customer.getLastName());
						switchScreens(event, "/gui/HomePageCustomer.fxml");
					}
				} else
					setNotification(false, null, null);
			} else
				FunctionForGUI.makeNotification("/Images/infoIcon.png", "Information", "The user name does not exist.");
		}

	}

	/**
	 * This function switch between screens in given the FXML name
	 * 
	 * @param event        - the event we handle
	 * @param fxmlFileName - the FXML file we want to switch to
	 */
	// Switch between screens if you click the button Login.
	void switchScreens(ActionEvent event, String fxmlFileName) {
		try {
			homePageFxmlName = fxmlFileName;
			Parent root = FXMLLoader.load(getClass().getResource(fxmlFileName));
			Scene scene = new Scene(root);
			FunctionForGUI functionForGUI = new FunctionForGUI();
			functionForGUI.MakeScreenDragble(root, stage);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
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
	 * This function switches to the Fueling Form
	 */
	@SuppressWarnings("static-access")
	@FXML
	private void goToFastFuelingForm(MouseEvent event) {
		if (!txtLiscensePlateNumber.getText().equals("") && !(txtLiscensePlateNumber.getText().length() < 5)
				&& !(txtLiscensePlateNumber.getText().length() > 8)) {
			ClientUI.chat.accept("checkIfLicensePlateNumberExists " + txtLiscensePlateNumber.getText());
			if ((boolean) MyFuelClient.msgFromServer) {
				licesnsePlateNumber = Integer.valueOf(txtLiscensePlateNumber.getText());
				@SuppressWarnings("rawtypes")
				Task copyWorker = createWorker();
				ProgressDialog dialog = new ProgressDialog(copyWorker);
				dialog.initStyle(StageStyle.DECORATED.UNDECORATED);
				dialog.setGraphic(null);
				dialog.setTitle("NFC identification");
				dialog.setHeaderText(null);
				new Thread(copyWorker).start();
				dialog.showAndWait();
				FuelPurchaseControllerClient temp = new FuelPurchaseControllerClient();
				try {
					temp.start(new Stage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				popAlert("Error", "The licsense plate number doesn't exist");
		} else
			popAlert("Error", "You must insert a valid licsense plate number");
	}

	/**
	 * This function sends a notification to the user in particular situations
	 * 
	 * @param passwordCurrect - boolean parameter that define if the password is
	 *                        correct
	 * @param firstName       - the first name of the user
	 * @param lastName        - the last name of the user
	 */
	// Notification for the user.
	public static void setNotification(boolean passwordCurrect, String firstName, String lastName) {
		if (passwordCurrect)
			FunctionForGUI.makeNotification("/Images/Tick.png", "Hi " + firstName + " " + lastName,
					"You are connecting to My Fule System. ");
		else
			FunctionForGUI.makeNotification("/Images/XNotification.png", "Error", "Wrong password, try again.");
	}

	/**
	 * This function opens the GUI of the Login
	 * 
	 * @param primaryStage - our primary stage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/UserLogin.fxml"));
		Scene scene = new Scene(root);
		FunctionForGUI functionForGUI = new FunctionForGUI();
		functionForGUI.removeDefaultBarMenu(primaryStage);
		functionForGUI.MakeScreenDragble(root, primaryStage);
		primaryStage.setScene(scene);
		UserLoginController.stage = primaryStage;
		primaryStage.show();
	}

	/**
	 * This is a sub method for progressBar method
	 * 
	 * @return a task object
	 */
	@SuppressWarnings("rawtypes")
	public Task createWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				for (int i = 0; i < 50; i++) {
					Thread.sleep(100);
					updateMessage("NFC identifies your vehicle..");
				}
				return true;
			}
		};
	}

	/**
	 * The function pops an alert to the user based on the case (success/error)
	 * 
	 * @param msg1 the windows title
	 * @param msg2 the windows content
	 */
	public void popAlert(String msg1, String msg2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(msg1);
		alert.setHeaderText(null);
		alert.setContentText(msg2);
		alert.show();
	}

	/**
	 * The function limits the text field only to numbers
	 * 
	 */
	@FXML
	void limit(KeyEvent event) {
		String c = event.getCharacter();
		if (!c.matches("[0-9]+"))
			event.consume();
	}

}
