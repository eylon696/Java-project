package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.TrackOrderForTable;
import entity.OrderHomeHeatingFuel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class TrackOrderHomeHeatingFuelControllerClient defined how the track
 * order window would work
 * 
 * @author Eylon
 *
 */
public class TrackOrderHomeHeatingFuelControllerClient implements Initializable {

	OrderHomeHeatingFuel[] arrOrders;
	ObservableList<TrackOrderForTable> arrListOrders = FXCollections.observableArrayList();

	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnTrack;

	@FXML
	private TableView<TrackOrderForTable> tableOrders;

	@FXML
	private TableColumn<TrackOrderForTable, Integer> clmnOrderId;

	@FXML
	private TableColumn<TrackOrderForTable, String> clmnOrderPlaced;

	@FXML
	private TableColumn<TrackOrderForTable, Float> clmnPrice;

	@FXML
	private Label lblAddress;

	@FXML
	private Label lblEstimatedDate;

	@FXML
	private Label lblQuantity;

	@FXML
	private Label lblOrderStatus;
	@FXML
	private Label lblDeliveryType;

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
	 * The function brings the user back to the previous window
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void backFunction(MouseEvent event) {
		changeScreen("/gui/HomeHeatingFuel.fxml");
	}

	/**
	 * The function shows the user the relevant information about the chosen order
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void trackOrder(MouseEvent event) {
		if (tableOrders.getSelectionModel().getSelectedItem() != null) {
			TrackOrderForTable temp = tableOrders.getSelectionModel().getSelectedItem();
			OrderHomeHeatingFuel orderToTrack = null;
			for (int i = 0; i < arrOrders.length; i++) {
				if (temp.getId() == arrOrders[i].getId()) {
					orderToTrack = arrOrders[i];
					break;
				}
			}
			lblDeliveryType.setText("Delivery type: " + orderToTrack.getDeliveryType());
			lblQuantity.setText("Quantity: " + orderToTrack.getQuantity() + " liters");
			if (orderToTrack.getDelivered().equals("yes")) {
				lblOrderStatus.setText("Order status: your order has been supplied");
				lblEstimatedDate.setText("");
				lblAddress.setText("Delivered to: " + orderToTrack.getAddress());
			}

			else {
				lblOrderStatus.setText("Order status: your order is on the way");
				lblEstimatedDate.setText("Estimated Date: " + orderToTrack.getEstimatedDate());
				lblAddress.setText("Deliver to: " + orderToTrack.getAddress());
			}
		}
	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientUI.chat.accept("updateOrderDeliveredToYes");
		int i;
		clmnOrderId.setCellValueFactory(new PropertyValueFactory<TrackOrderForTable, Integer>("id"));
		clmnOrderPlaced.setCellValueFactory(new PropertyValueFactory<TrackOrderForTable, String>("date"));
		clmnPrice.setCellValueFactory(new PropertyValueFactory<TrackOrderForTable, Float>("price"));
		tableOrders.setItems(arrListOrders);
		arrListOrders.clear();
		ClientUI.chat.accept("showOrdersHomeHeatingFuelForCustomerID " + UserLoginController.customer.getID());
		arrOrders = (OrderHomeHeatingFuel[]) MyFuelClient.msgFromServer;
		TrackOrderForTable[] arrForTrack = new TrackOrderForTable[arrOrders.length];
		for (i = 0; i < arrOrders.length; i++) {
			arrForTrack[i] = new TrackOrderForTable(arrOrders[i].getId(), arrOrders[i].getDate(),
					arrOrders[i].getPrice());
		}
		for (i = 0; i < arrForTrack.length; i++) {
			arrListOrders.add(arrForTrack[i]);
		}
		tableOrders.setItems(arrListOrders);
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

}
