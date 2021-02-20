package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.Purchase;
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
 * This class ShowFuelingPurchases defined how the ShowFuelingPurchases window
 * would work
 * 
 * @author gal
 *
 */
public class ShowFuelingPurchases implements Initializable {

	Purchase[] arrFuelingPurchases;
	ObservableList<Purchase> arrListFuelingPurchases = FXCollections.observableArrayList();

	@FXML
	private Pane MyPane;
	
	@FXML
	private Button btnBack;

	@FXML
	private TableView<Purchase> tablePurchases;

	@FXML
	private TableColumn<Purchase, Integer> clmnIDPurchase;

	@FXML
	private TableColumn<Purchase, Integer> clmnIDCustomer;

	@FXML
	private TableColumn<Purchase, Integer> clmnLicenseNumber;

	@FXML
	private TableColumn<Purchase, String> clmnDate;

	@FXML
	private TableColumn<Purchase, String> clmnTime;

	@FXML
	private TableColumn<Purchase, Double> quantityClmn;

	@FXML
	private TableColumn<Purchase, Double> priceClmn;

	@FXML
	private TableColumn<Purchase, String> paymentClmn;

	@FXML
	private TableColumn<Purchase, String> clmnChainName;

	@FXML
	private TableColumn<Purchase, String> clmnStationName;

	@FXML
	private TableColumn<Purchase, String> clmnSaleName;

	@FXML
	private Label lblCurrentChain;

	@FXML
	private Label lblEnterChain;

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
	 * This method initialize the show fueling purchases screen when it opened.
	 * Takes data of purchases from DB and presents the data by the suitable chain
	 * of the logged in user
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clmnIDPurchase.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("idPurchase"));
		clmnIDCustomer.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("customerId"));
		clmnLicenseNumber.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("licenseNum"));
		clmnDate.setCellValueFactory(new PropertyValueFactory<Purchase, String>("datePurchase"));
		clmnTime.setCellValueFactory(new PropertyValueFactory<Purchase, String>("timePurchase"));
		quantityClmn.setCellValueFactory(new PropertyValueFactory<Purchase, Double>("quantityPurchase"));
		priceClmn.setCellValueFactory(new PropertyValueFactory<Purchase, Double>("pricePurchase"));
		paymentClmn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("paymentPurchase"));
		clmnChainName.setCellValueFactory(new PropertyValueFactory<Purchase, String>("chainName"));
		clmnStationName.setCellValueFactory(new PropertyValueFactory<Purchase, String>("stationName"));
		clmnSaleName.setCellValueFactory(new PropertyValueFactory<Purchase, String>("saleName"));
		tablePurchases.setItems(arrListFuelingPurchases);
		lblEnterChain.setText(UserLoginController.employee.getOrganizationalAffiliation());
		initTable();
	}

	/**
	 * This method presents the data by the suitable chain of the logged in use
	 */
	public void initTable() {
		int i;
		ClientUI.chat.accept("showPurchases");
		arrFuelingPurchases = (Purchase[]) MyFuelClient.msgFromServer;
		for (i = 0; i < arrFuelingPurchases.length; i++) {
			if (UserLoginController.employee.getOrganizationalAffiliation()
					.equals(arrFuelingPurchases[i].getChainName()))
				arrListFuelingPurchases.add(arrFuelingPurchases[i]);
		}
		tablePurchases.setItems(arrListFuelingPurchases);
	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clickBack(MouseEvent event) {
		changeScreen("/gui/SystemOperationsChainManager.fxml");
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
			MyPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
