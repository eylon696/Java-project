package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.OrderHomeHeatingFuel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class ShowHomeHeatingFuelPurchasesControllerClient defined how the show
 * home heating fuel purchase window would work
 * 
 * @author Eylon
 *
 */
public class ShowHomeHeatingFuelPurchasesControllerClient implements Initializable {

	OrderHomeHeatingFuel[] arrPurchases;
	ObservableList<OrderHomeHeatingFuel> arrListPurchases = FXCollections.observableArrayList();
	
	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private TableView<OrderHomeHeatingFuel> tablePurchases;

	@FXML
	private TableColumn<OrderHomeHeatingFuel, Integer> clmnPurchaseID;

	@FXML
	private TableColumn<OrderHomeHeatingFuel, Integer> clmnCustomerID;

	@FXML
	private TableColumn<OrderHomeHeatingFuel, String> clmnPurchaseDate;

	@FXML
	private TableColumn<OrderHomeHeatingFuel, Double> clmnQuantity;

	@FXML
	private TableColumn<OrderHomeHeatingFuel, String> clmnDeliverTo;

	@FXML
	private TableColumn<OrderHomeHeatingFuel, String> clmnDeliveryType;

	@FXML
	private TableColumn<OrderHomeHeatingFuel, Float> clmnPrice;

	@FXML
	private TableColumn<OrderHomeHeatingFuel, String> clmnPayment;

	@FXML
	private TableColumn<OrderHomeHeatingFuel, String> clmnDelivered;

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
		changeScreen("/gui/SystemOperationsMarketingManager.fxml");
	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientUI.chat.accept("updateOrderDeliveredToYes");
		clmnPurchaseID.setCellValueFactory(new PropertyValueFactory<OrderHomeHeatingFuel, Integer>("id"));
		clmnCustomerID.setCellValueFactory(new PropertyValueFactory<OrderHomeHeatingFuel, Integer>("customerId"));
		clmnPurchaseDate.setCellValueFactory(new PropertyValueFactory<OrderHomeHeatingFuel, String>("date"));
		clmnQuantity.setCellValueFactory(new PropertyValueFactory<OrderHomeHeatingFuel, Double>("quantity"));
		clmnDeliverTo.setCellValueFactory(new PropertyValueFactory<OrderHomeHeatingFuel, String>("address"));
		clmnDeliveryType.setCellValueFactory(new PropertyValueFactory<OrderHomeHeatingFuel, String>("deliveryType"));
		clmnPrice.setCellValueFactory(new PropertyValueFactory<OrderHomeHeatingFuel, Float>("price"));
		clmnPayment.setCellValueFactory(new PropertyValueFactory<OrderHomeHeatingFuel, String>("payment"));
		clmnDelivered.setCellValueFactory(new PropertyValueFactory<OrderHomeHeatingFuel, String>("delivered"));
		tablePurchases.setItems(arrListPurchases);
		initTable();

	}

	/**
	 * The function shows the relevant data in a table view
	 */
	public void initTable() {
		int i;
		arrListPurchases.clear();
		ClientUI.chat.accept("showHomeHeatingPurchasesInDB");
		arrPurchases = (OrderHomeHeatingFuel[]) MyFuelClient.msgFromServer;
		for (i = 0; i < arrPurchases.length; i++) {
			arrListPurchases.add(arrPurchases[i]);
		}
		tablePurchases.setItems(arrListPurchases);
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
