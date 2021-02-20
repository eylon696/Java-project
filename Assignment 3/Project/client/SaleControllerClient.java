package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientUI;
import client.MyFuelClient;
import entity.Sale;
import entity.SalePattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * This class SaleControllerClient defined how the Sale window would work
 * 
 * @author gal
 *
 */
public class SaleControllerClient implements Initializable {
	Sale[] arrSales;
	SalePattern[] arrPatterns;
	ObservableList<String> list;
	/**
	 * The array saves data that the user enterd(useful to transitions between
	 * forms)	
	 */
	public static String[] arrDataOfSaleForm = new String[3];

	@FXML
	private Label lblNavigationBar;
	@FXML
	private Button btnBack = null;

	@FXML
	private Label lblClear;

	@FXML
	private Button btnAddSale = null;

	@FXML
	private TextField txtSaleName;

	@FXML
	private ComboBox<String> cmbPatterns;

	@FXML
	private TextArea txtSaleReason;

	@FXML
	private Label lblAddNewSalePattern;

	@FXML
	private TextField txtDiscount;

	@FXML
	private Label lblErrorSaleName;

	@FXML
	private Label lblErrorDiscount;

	@FXML
	private Label lblErrorSalePattern;

	@FXML
	private Label lblErrorSaleReason;

	@FXML
	private Label lblNoSalePattern;

	@FXML
	private Pane MyPane;

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

	public static boolean flagIfComeFromSale = false;

	/**
	 * This method initialize the sale screen when it opened. Takes data of sales
	 * and sale patterns from DB
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblNavigationBar.setText("");
		if (SystemOperationsController.flagIfCameFromSystemOperationsMarketingManager) { /// check the flags
			txtSaleName.setText("");
			txtDiscount.setText("");
			txtSaleReason.setText("");
			lblNavigationBar.setText("System Operations -> Sales");
			flagIfComeFromSale = false;
			SystemOperationsController.flagIfCameFromSystemOperationsMarketingManager = false;
		} else if (OperationTrackingControllerClient.flagIfComeFromRankForm) {
			txtSaleName.setText("");
			txtDiscount.setText("");
			txtSaleReason.setText("");
			lblNavigationBar.setText("System Operations -> Operation Tracking -> Sales");
			flagIfComeFromSale = false;
		} else {
			lblNavigationBar.setText("System Operations -> Sales");
			arrDataOfSaleForm = DefinePatternControllerClient.arrSaveData;
			if (arrDataOfSaleForm != null) {
				txtSaleName.setText(arrDataOfSaleForm[0]);
				txtDiscount.setText(arrDataOfSaleForm[1]);
				txtSaleReason.setText(arrDataOfSaleForm[2]);
				flagIfComeFromSale = false;
			} else {
				lblNavigationBar.setText("System Operations -> Sales");
				txtSaleName.setText("");
				txtDiscount.setText("");
				txtSaleReason.setText("");
				flagIfComeFromSale = false;
			}
		}
		ClientUI.chat.accept("showSalesPatterns");
		arrPatterns = (SalePattern[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showSales");
		arrSales = (Sale[]) MyFuelClient.msgFromServer;
		setSalePatternsComboBox();
		LimitAreaAndTextFields.limitTxtField(txtSaleName, 45);
		LimitAreaAndTextFields.limitTxtArea(txtSaleReason, 500);

	}

	/**
	 * This method limit the user to enter only numbers in the discount field
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void limit(KeyEvent event) {
		String c = event.getCharacter();
		if (!c.matches("[0-9]+"))
			event.consume();
	}

	/**
	 * This method sets suitable sale pattern combo box. if the combo box is empty,
	 * the user must to add sale pattern.
	 */
	private void setSalePatternsComboBox() {
		int i, j;
		boolean flag = false;
		ArrayList<String> al = new ArrayList<String>();
		for (i = 0; i < arrPatterns.length; i++) {
			for (j = 0; j < arrSales.length; j++) {
				if (arrPatterns[i].getIdPattern() == arrSales[j].getPatternID())
					flag = true;
			}
			if (flag == false)
				al.add(arrPatterns[i].getIdPattern() + ": Pattern Description: " + arrPatterns[i].getDescription()
						+ ", From: " + arrPatterns[i].getStartDate() + " To: " + arrPatterns[i].getEndDate());
			flag = false;

		}
		if (al.size() == 0)
			lblNoSalePattern.setText("*There are no sale patterns exist,you must add one");
		list = FXCollections.observableArrayList(al);
		cmbPatterns.setItems(list);
	}

	/**
	 * This method saves the enterd data by the user in the static array and sends
	 * the user to chosen form.
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void addNewSalePatternClick(MouseEvent event) {
		arrDataOfSaleForm[0] = txtSaleName.getText();
		arrDataOfSaleForm[1] = txtDiscount.getText();
		arrDataOfSaleForm[2] = txtSaleReason.getText();
		flagIfComeFromSale = true;
		changeScreen("/gui/SalePattern.fxml");
	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backClick(MouseEvent event) {
		if (OperationTrackingControllerClient.flagIfComeFromRankForm == true) {
			changeScreen("/gui/OperationTracking.fxml");
		} else
			changeScreen("/gui/SystemOperationsMarketingManager.fxml");
	}

	/**
	 * This method clears the form
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clearClick(MouseEvent event) {
		txtSaleName.clear();
		txtDiscount.clear();
		if (cmbPatterns.getValue() != null)
			cmbPatterns.setDisable(false);
		cmbPatterns.setValue(null);
		txtSaleReason.clear();
		lblErrorSaleName.setText("");
		lblErrorDiscount.setText("");
		lblErrorSalePattern.setText("");
		lblErrorSaleReason.setText("");
	}

	/**
	 * This method adds sale to DB when this button pressed
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void createNewSaleClick(MouseEvent event) {
		lblErrorSaleName.setText("");
		lblErrorDiscount.setText("");
		lblErrorSalePattern.setText("");
		lblErrorSaleReason.setText("");
		if (txtSaleName.getText() == null || txtDiscount.getText() == null || txtSaleReason.getText() == null
				|| txtSaleName.getText().equals("") || txtDiscount.getText().equals("")
				|| txtSaleReason.getText().equals("") || cmbPatterns.getValue() == null) {
			if (txtSaleName.getText() == null || txtSaleName.getText().equals(""))
				lblErrorSaleName.setText("* You must fill sale name");
			if (txtDiscount.getText() == null || txtDiscount.getText().equals(""))
				lblErrorDiscount.setText("* You must fill discount");
			if (cmbPatterns.getValue() == null)
				lblErrorSalePattern.setText("* You must choose sale pattern");
			if (txtSaleReason.getText() == null || txtSaleReason.getText().equals(""))
				lblErrorSaleReason.setText("* You must fill sale reason");
		} else {
			if (Integer.valueOf(txtDiscount.getText()) >= 0 && Integer.valueOf(txtDiscount.getText()) <= 90) {
				String[] arrStr = cmbPatterns.getValue().toString().split(":");
				Sale newSale = new Sale(txtSaleName.getText(), Integer.valueOf(arrStr[0]), txtSaleReason.getText(),
						Integer.valueOf(txtDiscount.getText()), 0, 0);
				ClientUI.chat.accept("checkIfSaleNameExistsSaleNameStartsHere" + txtSaleName.getText());
				if ((boolean) MyFuelClient.msgFromServer)
					lblErrorSaleName.setText("* Sale name already exists");
				else { // SaleNameNotExists
					ClientUI.chat.accept(newSale);
					if (((boolean) MyFuelClient.msgFromServer)) {
						showAlert("Success", "The sale has been created successfuly"); // Maybe we don't need that
						changeScreen("/gui/SystemOperationsMarketingManager.fxml");
					}
				}
			} else
				lblErrorDiscount.setText("* Discount values: 0-90");

		}
	}

	/**
	 * This method shows pop up alert with suitable message to the user
	 * 
	 * @param msg1 The title of the alert
	 * @param msg2 The content text of the alert
	 */
	public void showAlert(String msg1, String msg2) { // Maybe we don't need that
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(msg1);
		alert.setHeaderText(null);
		alert.setContentText(msg2);
		alert.show();
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
