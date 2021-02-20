package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.xml.internal.ws.util.StringUtils;

import client.ClientUI;
import client.MyFuelClient;
import entity.ProductsInGasStation;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class ProductsInGasStationControllerClient defined how the threshold
 * level and product in gas station window would work
 * 
 * @author Eylon
 *
 */
public class ProductsInGasStationControllerClient implements Initializable {
	ProductsInGasStation[] arrProducts;
	ObservableList<ProductsInGasStation> arrListProducts = FXCollections.observableArrayList();

	@FXML
	private Pane MyPane;	

	@FXML
	private Button btnBack;

	@FXML
	private TableView<ProductsInGasStation> tbaleProducts;

	@FXML
	private TableColumn<ProductsInGasStation, String> clmnChainName;

	@FXML
	private TableColumn<ProductsInGasStation, String> clmnStationName;

	@FXML
	private TableColumn<ProductsInGasStation, String> clmnFuelType;

	@FXML
	private TableColumn<ProductsInGasStation, Double> clmnStockQuantity;

	@FXML
	private TableColumn<ProductsInGasStation, Double> clmnThresholdLevel;

	@FXML
	private TextField txtThresholdLevel;

	@FXML
	private Button btnUpdate;

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
		changeScreen("/gui/SystemOperationStationManager.fxml");

	}

	/**
	 * The function sends to the server controller the threshold level updated that
	 * the user wants to update
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void updateFunction(MouseEvent event) {
		if (tbaleProducts.getSelectionModel().getSelectedItem() != null) {
			ProductsInGasStation temp = tbaleProducts.getSelectionModel().getSelectedItem();
			if (txtThresholdLevel.getText() != null && !txtThresholdLevel.getText().equals("")) {
				temp.setThresholdLevel(Double.valueOf(txtThresholdLevel.getText()));
				ClientUI.chat.accept(temp);
				initTable();
				txtThresholdLevel.setText("");
				;
			}
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
	 * The function shows the relevant data in a table view
	 */
	public void initTable() {
		int i;
		arrListProducts.clear();
		ClientUI.chat
				.accept("showProductsInGasStationInDB:" + UserLoginController.employee.getOrganizationalAffiliation()
						+ ":" + UserLoginController.employee.getGasStation());
		arrProducts = (ProductsInGasStation[]) MyFuelClient.msgFromServer;
		for (i = 0; i < arrProducts.length; i++) {
			arrListProducts.add(new ProductsInGasStation(StringUtils.capitalize(arrProducts[i].getChain()),
					StringUtils.capitalize(arrProducts[i].getStation()), arrProducts[i].getFuelType(),
					arrProducts[i].getStockQuantity(), arrProducts[i].getThresholdLevel()));
		}
		tbaleProducts.setItems(arrListProducts);
	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clmnChainName.setCellValueFactory(new PropertyValueFactory<ProductsInGasStation, String>("chain"));
		clmnStationName.setCellValueFactory(new PropertyValueFactory<ProductsInGasStation, String>("station"));
		clmnFuelType.setCellValueFactory(new PropertyValueFactory<ProductsInGasStation, String>("fuelType"));
		clmnStockQuantity.setCellValueFactory(new PropertyValueFactory<ProductsInGasStation, Double>("stockQuantity"));
		clmnThresholdLevel
				.setCellValueFactory(new PropertyValueFactory<ProductsInGasStation, Double>("thresholdLevel"));
		tbaleProducts.setItems(arrListProducts);
		initTable();

	}

	/**
	 * The function limits the text field only to double type
	 * 
	 * @param e the event we handle
	 */
	@FXML
	public void onlyDouble(KeyEvent e) {
		String c = e.getCharacter();
		if (!c.matches("[0-9]+") && !c.equals("."))
			e.consume();
		else if (txtThresholdLevel.getText().isEmpty() && c.equals("."))
			e.consume();
		else if (c.equals(".") && txtThresholdLevel.getText().contains("."))
			e.consume();

	}
}
