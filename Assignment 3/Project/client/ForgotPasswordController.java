package client;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.util.Duration;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Server.EchoServer;
import client.ClientUI;
import client.MyFuelClient;
import entity.Customer;
import entity.Employee;
import javafx.scene.layout.AnchorPane;

/**
 * The class ForgotPasswordController defined how the forgot password form would
 * work
 * 
 * @author Shoval David
 *
 */
public class ForgotPasswordController {
	public static Parent parent = null;

	@FXML
	TextField ID;	
	@FXML
	TextField Email;
	@FXML
	Button GetPassword;
	@FXML
	Button Minus;
	@FXML
	Button X;
	@FXML
	Button Back;
	@FXML
	AnchorPane myAnchorPane;

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
		FunctionForGUI.minimizeAnchorPane(event, myAnchorPane);
	}

	/**
	 * This function sends the user to the previous page
	 * 
	 * @throws IOException
	 */
	@FXML
	private void Back() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/UserLogin.fxml"));
		Scene scene = UserLoginController.stage.getScene();
		root.translateYProperty().set(scene.getHeight());
		AnchorPane parentContainer = (AnchorPane) scene.getRoot();
		parentContainer.getChildren().add(root);

		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.8), kv);
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(event -> {
			parentContainer.getChildren().remove(myAnchorPane);
		});
		timeline.play();
	}

	/**
	 * This function checks if the ID and the Email of the user are correct, if yes
	 * it will show him his password on the button right screen otherwise it will
	 * show and error massage
	 */
	@FXML
	public void getPassword() {
		String IDtxt = ID.getText();
		String Emailtxt = Email.getText();
		boolean customerExist;
		boolean employeeExist;
		Customer customer;
		Employee employee;

		if (IDtxt.isEmpty() || Emailtxt.isEmpty()) // If the user did not insert one of the fields or both.
			FunctionForGUI.makeNotification("/Images/XNotification.png", "Error", "You must fill all the fields.");
		else {
			ClientUI.chat.accept("LoginQueryUserExist customer " + IDtxt); // Can only send string to this function ,
																			// split happen on echoServer.
			customerExist = (boolean) MyFuelClient.msgFromServer;
			ClientUI.chat.accept("LoginQueryUserExist employee " + IDtxt);
			employeeExist = (boolean) MyFuelClient.msgFromServer;

			if (customerExist) {
				ClientUI.chat.accept("UserLoginQueryEmailCorrect " + IDtxt + " " + Emailtxt);
				customer = (Customer) MyFuelClient.msgFromServer;
				if (customer != null)
					setNotification(1, customer.getPassword());
				else
					setNotification(2, null);
			}

			else if (employeeExist) // need to separate the employee from the user.
			{
				ClientUI.chat.accept("EmployeeLoginQueryEmailCorrect " + IDtxt + " " + Emailtxt);
				employee = (Employee) MyFuelClient.msgFromServer;
				if (employee != null)
					setNotification(1, employee.getPassword());
				else
					setNotification(2, null);
			} else
				setNotification(3, null);
		}
	}

	/**
	 * This function check if the email the customer inserted is correct
	 * 
	 * @param ID    - The ID of the customer
	 * @param Email - The Email of the customer
	 * @return The customer entity if exist otherwise null
	 */
	public static Customer UserLoginQueryEmailCorrect(String ID, String Email) {
		try {
			Customer customer = null;
			String query = "SELECT * FROM customer WHERE ID = '" + ID + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				customer = new Customer(resultSet);
			if (customer != null && customer.getEmail().equals(Email))
				return customer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function check if the email the employee inserted is correct
	 * 
	 * @param ID    - The ID of the customer
	 * @param Email - The Email of the customer
	 * @return The employee entity if exist otherwise null
	 */
	public static Employee EmployeeLoginQueryEmailCorrect(String ID, String Email) {
		try {
			Employee employee = null;
			String query = "SELECT * FROM employee WHERE ID = '" + ID + "'";
			Statement statement = EchoServer.con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next())
				employee = new Employee(resultSet);
			if (employee != null && employee.getEmail().equals(Email))
				return employee;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Notification for the user.
	/**
	 * This function sends a notification to the user in particular situations
	 * 
	 * @param option   - what case inside the switch case is going to be activated
	 * @param password - the password of the user
	 */
	public static void setNotification(int option, String password) {
		switch (option) {
		case 1:
			FunctionForGUI.makeNotification("/Images/Tick.png", "Your password is: " + password,
					"Login again with the password above.");
			break;
		case 2:
			FunctionForGUI.makeNotification("/Images/XNotification.png", "Error", "The email is incorrect.");
			break;
		case 3:
			FunctionForGUI.makeNotification("/Images/infoIcon.png", "Information", "The user name does not exist.");
			break;
		}
	}

}
