package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.Customer;
import entity.Purchase;
import entity.Sale;
import entity.SaleCommentsReportForTable;
import entity.SalePattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * This class SaleCommentsReportControllerClient defined how the
 * SaleCommentsReport window would work
 * 
 * @author gal
 * 
 */
public class SaleCommentsReportControllerClient implements Initializable {
	Purchase[] arrPurchases;
	Purchase[] arrPurchasesWithSale;
	Purchase[] arrPurchasesNoDuplicates;
	SaleCommentsReportForTable[] arrCustomerPrice;
	SaleCommentsReportForTable[] arrToShow;
	ObservableList<SaleCommentsReportForTable> arrListOfCustomerPrice = FXCollections.observableArrayList();
	ObservableList<Purchase> arrListOfPurchases = FXCollections.observableArrayList();
	Sale[] arrSales;
	ObservableList<String> list;
	Customer[] arrCustomers;
	SalePattern[] arrPatterns;

	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private Label lblClear;

	@FXML
	private TableView<SaleCommentsReportForTable> tableAmount;

	@FXML
	private TableColumn<SaleCommentsReportForTable, String> clmnFirstName;

	@FXML
	private TableColumn<SaleCommentsReportForTable, String> clmnLastName;

	@FXML
	private TableColumn<SaleCommentsReportForTable, Integer> clmnCustomerID;

	@FXML
	private TableColumn<SaleCommentsReportForTable, Double> clmnPrice;

	@FXML
	private ComboBox<String> cmbSaleName;

	@FXML
	private Label lblNumOfCustomers;

	@FXML
	private Label lblSumOfPurchases;

	@FXML
	private Button Minus;
	@FXML
	private Button X;

	@FXML
	private Label lblDates;

	@FXML
	private TableView<Purchase> tablePurchases;

	@FXML
	private TableColumn<Purchase, Integer> clmnCustomerID1;

	@FXML
	private TableColumn<Purchase, Integer> clmnLicenseNumber1;

	@FXML
	private TableColumn<Purchase, Double> clmnQuantity1;

	@FXML
	private TableColumn<Purchase, Double> clmnPrice1;

	@FXML
	private Label lblchoose;

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
	 * This method initialize the sale comments report screen when it opened. Takes
	 * data of sales from DB and presents the suitable data.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clmnFirstName.setCellValueFactory(new PropertyValueFactory<SaleCommentsReportForTable, String>("firstName"));
		clmnLastName.setCellValueFactory(new PropertyValueFactory<SaleCommentsReportForTable, String>("lastName"));
		clmnCustomerID.setCellValueFactory(new PropertyValueFactory<SaleCommentsReportForTable, Integer>("customerId"));
		clmnPrice.setCellValueFactory(new PropertyValueFactory<SaleCommentsReportForTable, Double>("totalPrice"));
		tableAmount.setItems(arrListOfCustomerPrice);

		clmnCustomerID1.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("customerId"));
		clmnLicenseNumber1.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("licenseNum"));
		clmnQuantity1.setCellValueFactory(new PropertyValueFactory<Purchase, Double>("quantityPurchase"));
		clmnPrice1.setCellValueFactory(new PropertyValueFactory<Purchase, Double>("pricePurchase"));
		tablePurchases.setItems(arrListOfPurchases);

		ClientUI.chat.accept("showSales");
		arrSales = (Sale[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showSalesPatterns");
		arrPatterns = (SalePattern[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showCustomers");
		arrCustomers = (Customer[]) MyFuelClient.msgFromServer;
		setComboBoxSaleName();
	}

	/**
	 * This method sets suitable sale combo box.
	 */
	private void setComboBoxSaleName() {
		int i;
		ArrayList<String> al = new ArrayList<String>();
		for (i = 0; i < arrSales.length; i++) {
			al.add(String.valueOf(arrSales[i].getSaleName()));
		}
		list = FXCollections.observableArrayList(al);
		cmbSaleName.setItems(list);

	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backClick(MouseEvent event) {
		changeScreen("/gui/ProduceReports.fxml");
	}

	/**
	 * This method clears the form
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clearClick(MouseEvent event) {
		changeScreen("/gui/SaleCommentsReport.fxml");
	}

	/**
	 * This method presents the suitable data by the users chose in the combo box
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void cmbClickSaleName(ActionEvent event) {
		lblDates.setText("");
		lblchoose.setText("");
		int i, j, k = 0, count = 0;
		double sum = 0;
		if (cmbSaleName.getValue() != null) {
			for (j = 0; j < arrPatterns.length; j++) {
				for (i = 0; i < arrSales.length; i++) {
					if (arrPatterns[j].getIdPattern() == arrSales[i].getPatternID()) {
						if (arrSales[i].getSaleName().equals(cmbSaleName.getValue()))
							lblDates.setText("The sale active during: " + arrPatterns[j].getStartDate() + " - "
									+ arrPatterns[j].getEndDate() + " with discount " + arrSales[i].getDiscount()
									+ "%");
					}
				}
			}
			ClientUI.chat.accept("showPurchases");
			arrPurchases = (Purchase[]) MyFuelClient.msgFromServer;
			for (i = 0; i < arrSales.length; i++) {
				if (arrSales[i].getSaleName().equals(cmbSaleName.getValue())) {
					lblNumOfCustomers.setText(String.valueOf(arrSales[i].getNumOfCustomersPurchased()));
					lblSumOfPurchases.setText(String.valueOf(arrSales[i].getSumOfAllPurchases()));
				}
			}
			lblchoose.setText("Choose a row from the table for more detailes");
			for (i = 0; i < arrPurchases.length; i++) {
				if (arrPurchases[i].getSaleName() != null
						&& arrPurchases[i].getSaleName().equals(cmbSaleName.getValue())) {
					count++;
				}
			}
			arrPurchasesWithSale = new Purchase[count];
			for (i = 0; i < arrPurchases.length; i++) {
				if (arrPurchases[i].getSaleName() != null
						&& arrPurchases[i].getSaleName().equals(cmbSaleName.getValue())) {
					arrPurchasesWithSale[k] = arrPurchases[i];
					k++;
				}
			}

			for (i = 0; i < arrSales.length; i++) {
				if (arrSales[i].getSaleName().equals(cmbSaleName.getValue())) {
					lblNumOfCustomers.setText(String.valueOf(arrSales[i].getNumOfCustomersPurchased()));
					lblSumOfPurchases.setText(String.valueOf(arrSales[i].getSumOfAllPurchases()));
				}
			}
			k = 0;
			arrPurchasesNoDuplicates = removeDuplicates(arrPurchases);
			arrCustomerPrice = new SaleCommentsReportForTable[arrPurchasesNoDuplicates.length];
			for (j = 0; j < arrPurchasesNoDuplicates.length; j++) {
				for (i = 0; i < arrPurchasesWithSale.length; i++) {
					if (arrPurchasesNoDuplicates[j].getCustomerId() == arrPurchasesWithSale[i].getCustomerId()) {
						if (arrPurchasesWithSale[i].getSaleName().equals(cmbSaleName.getValue())) {
							sum = sum + arrPurchasesWithSale[i].getPricePurchase();
						}
					}
				}
				arrCustomerPrice[k] = new SaleCommentsReportForTable(arrPurchasesNoDuplicates[j].getCustomerId(), sum);
				k++;
				sum = 0;
			}
			k = 0;
			arrToShow = new SaleCommentsReportForTable[arrCustomerPrice.length];
			for (j = 0; j < arrCustomers.length; j++) {
				for (i = 0; i < arrCustomerPrice.length; i++) {
					if (arrCustomers[j].getID() == arrCustomerPrice[i].getCustomerId()) {
						arrToShow[k] = new SaleCommentsReportForTable(arrCustomerPrice[i].getCustomerId(),
								arrCustomerPrice[i].getTotalPrice(), arrCustomers[j].getFirstName(),
								arrCustomers[j].getLastName());
						k++;
					}
				}
			}
			for (i = 0; i < arrToShow.length; i++) {
				if (arrToShow[i].getTotalPrice() != 0)
					arrListOfCustomerPrice.add(arrToShow[i]);
			}
			tableAmount.setItems(arrListOfCustomerPrice);
			arrListOfCustomerPrice = FXCollections.observableArrayList();
		}
	}

	/**
	 * This method presents the suitable data by the users chose in the table
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clickOnTable(MouseEvent event) {
		int i;
		// tablePurchses:
		if (tableAmount.getSelectionModel().getSelectedItem() != null) {
			int idOfTable = tableAmount.getSelectionModel().getSelectedItem().getCustomerId();
			for (i = 0; i < arrPurchasesWithSale.length; i++) {
				if (arrPurchasesWithSale[i].getCustomerId() == idOfTable)
					arrListOfPurchases.add(arrPurchasesWithSale[i]);
			}
			tablePurchases.setItems(arrListOfPurchases);
			arrListOfPurchases = FXCollections.observableArrayList();
		}
	}

	/**
	 * This method gets array and removes the duplicate values inside him by the
	 * chosen key to remove
	 * 
	 * @param arr The chosen array to remove duplicates from him
	 * @return The array without the duplicates
	 */
	public static Purchase[] removeDuplicates(Purchase[] arr) {
		int end = arr.length;
		for (int i = 0; i < end; i++) {
			for (int j = i + 1; j < end; j++) {
				if (arr[i].getCustomerId() == (arr[j].getCustomerId())) {
					int shiftLeft = j;
					for (int k = j + 1; k < end; k++, shiftLeft++) {
						arr[shiftLeft] = arr[k];
					}
					end--;
					j--;
				}
			}
		}
		Purchase[] whitelist = new Purchase[end];
		for (int i = 0; i < end; i++) {
			whitelist[i] = arr[i];
		}
		return whitelist;
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
