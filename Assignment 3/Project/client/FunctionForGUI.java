package client;

import org.controlsfx.control.Notifications;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * The class FunctionForGUI has functions we use often in our windows
 * 
 * @author Shoval David
 *
 */
public class FunctionForGUI {

	double xOffset = 0;
	double yOffset = 0;

	public FunctionForGUI() {
	}

	/**
	 * This function remove the tool bar on top of the screen
	 * 
	 * @param stage - the stage we handle
	 */
	@SuppressWarnings("static-access")
	public void removeDefaultBarMenu(Stage stage) // Remove the - í x tool bar.
	{
		stage.initStyle(StageStyle.DECORATED.UNDECORATED); // Remove the tool bar.
	}

	/**
	 * This function makes the screen draggable (when the mouse inside the window)
	 * 
	 * @param root  - the root we handle
	 * @param stage - the stage we handle
	 */
	public void MakeScreenDragble(Parent root, Stage stage) {
		root.setOnMousePressed(new EventHandler<MouseEvent>() { // This two function make us move the screen all around
																// by clicking it.
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
				stage.setOpacity(0.7f);
			}
		});
		root.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setOpacity(1.0f);
			}
		});
	}

	/**
	 * This function makes notification pop at the button right screen
	 * 
	 * @param PathImg - the image the notification is going to show
	 * @param title   - the title of the notification
	 * @param text    - the text inside the notification
	 */
	public static void makeNotification(String PathImg, String title, String text) {
		Image img = new Image(PathImg);
		Notifications notificationBuilder = Notifications.create()

				.title(title).text(text).graphic(new ImageView(img)).position(Pos.BOTTOM_RIGHT)
				.hideAfter(Duration.seconds(4)).onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						System.out.println("Click on something");
					}
				});
		notificationBuilder.show();
	}

	/**
	 * This function exit the current scene (exit the application)
	 * 
	 * @param event , the event we handle
	 */
	public static void exit(ActionEvent event) {

		if (UserLoginController.employee != null) {
			ClientUI.chat.accept("RemoveUserFromArrayList " + UserLoginController.employee.getEmployeeID());
			UserLoginController.employee = null;
			UserLoginController.customer = null;
			UserLoginController.employee = null;
		} else if (UserLoginController.customer != null) {

			ClientUI.chat.accept("RemoveUserFromArrayList " + UserLoginController.customer.getID());
			UserLoginController.customer = null;
			UserLoginController.customer = null;
		}

		System.exit(0);
	}

	/**
	 * This function minimize the window (Pane)
	 * 
	 * @param event , the even we handle
	 */
	public static void minimizePane(ActionEvent event, Pane myPane) {
		Stage stage = (Stage) myPane.getScene().getWindow();
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	/**
	 * This function minimize the window (AnchorPane)
	 * 
	 * @param event , the even we handle
	 */
	public static void minimizeAnchorPane(ActionEvent event, AnchorPane myAnchorPane) {
		Stage stage = (Stage) myAnchorPane.getScene().getWindow();
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	/**
	 * This function minimize the window (VBox)
	 * 
	 * @param event , the even we handle
	 */
	public static void minimizemyVBox(ActionEvent event, VBox myVBox) {
		Stage stage = (Stage) myVBox.getScene().getWindow();
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	/**
	 * This function pop and create a alert massage
	 * 
	 * @param title   - the title of the alert
	 * @param content - the content inside the alert
	 */
	public static void showAlert(String title, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.show();
	}

}
