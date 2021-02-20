package client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import org.controlsfx.dialog.ProgressDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import client.ClientUI;
import client.MyFuelClient;
import entity.ChainOfGas;
import entity.Customer;
import entity.GasStation;
import entity.Purchase;
import entity.PurchasePlan;
import entity.Sale;
import entity.SalePattern;
import entity.Vehicle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sendEmail.SendEmail;

/**
 * This class FuelPurchaseControllerClient defined how the FuelPurchase window
 * would work
 * 
 * @author gal
 *
 */
public class FuelPurchaseControllerClient implements Initializable {

	boolean flag = false;

	Stage temp = new Stage();
	@FXML
	private Pane MyPane;

	@FXML
	private TextField txtCustomerID;

	@FXML
	private TextField txtCustomerName;

	@FXML
	private TextField txtChainName;

	@FXML
	private TextField txtStationName;

	@FXML
	private TextField txtLicensePlateNumber;

	@FXML
	private TextField txtPricePerLitre;

	@FXML
	private TextField txtQuantity;

	@FXML
	private TextField txtPrice;

	@FXML
	private RadioButton rbCreditCard;

	@FXML
	private ToggleGroup Payment;

	@FXML
	private RadioButton rbCash;

	@FXML
	private RadioButton rbYes;

	@FXML
	private ToggleGroup Receipt;

	@FXML
	private RadioButton rbNo;

	@FXML
	private Button btnFinish;

	@FXML
	private Button btnStartFueling;

	@FXML
	private Label lblClearFields;

	@FXML
	private Label lblErrorQuantity;

	@FXML
	private Label lblDiscountPrice;

	@FXML
	private Label lblNoPaymentForFull;

	@FXML
	private Label lblFuelType;

	@FXML
	private TextField txtPriceBeforeDiscount;

	@FXML
	private RadioButton rb1;

	@FXML
	private ToggleGroup Pump;

	@FXML
	private RadioButton rb2;

	@FXML
	private RadioButton rb3;

	@FXML
	private Label lblErrorPumpNumber;

	@FXML
	private Button Minus;
	@FXML
	private Button X;

	private Random random = new Random();

	/**
	 * This function exit the current scene (exit the application)
	 * 
	 * @param event , the event we handle
	 */
	@FXML
	private void exit(ActionEvent event) {
		Stage stage = (Stage) X.getScene().getWindow();
		// do what you have to do
		stage.close();

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

	public int idConnect;
	Customer customerConnect;
	Vehicle[] vehicleArrForIdConnect;
	Vehicle[] arrVehicles;
	int licenseNumberFromLogin = UserLoginController.licesnsePlateNumber;
	PurchasePlan purchasePlanForId;
	Customer[] arrCustomer;
	ChainOfGas[] chainArr;
	GasStation[] gasStationArr;
	GasStation[] allGasStationArr;
	GasStation[] gasStationArrNoDuplicates;
	String fuelTypeForVehicle;
	Sale[] arrSales;
	SalePattern[] arrSalePatterns;
	String idSaleExists;
	Purchase[] arrExistsPurchases;
	double stockInStation = 0;
	double priceForLitre = 0;
	boolean flagIfPressedStartFueling = false;
	boolean flagDBSuccedd;
	boolean flagUpdatePriceBySubscription = false;
	boolean flagUpdatePriceBySaleExists = false;
	boolean flagUpdatePriceByPurchasePlan = false;
	String chainNameChose = "";
	String stationNameChose = "";;

	/**
	 * This method clears the form
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clearClick(MouseEvent event) {
		txtQuantity.clear();
		txtPrice.clear();
		txtPriceBeforeDiscount.clear();
		Payment.selectToggle(rbCreditCard);
		Receipt.selectToggle(rbYes);
		// Pump.selectToggle(null);
		txtPrice.setEditable(false);
		txtPriceBeforeDiscount.setEditable(false);
		lblErrorQuantity.setText("");
		lblErrorPumpNumber.setText("");
		lblDiscountPrice.setText("");
		btnFinish.setDisable(false);
		btnStartFueling.setDisable(false);
	}

	/**
	 * This method adds purchase to DB when this button pressed. If exist
	 * sale,update sale in DB also also update stock at gasstions in DB
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void finishClick(MouseEvent event) {
		int i, j;
		if (flagIfPressedStartFueling == true) {
			String strPayment = "";
			if (Payment.getSelectedToggle().equals(rbCash))
				strPayment = "cash";
			else
				strPayment = "credit card";
			ClientUI.chat.accept("checkNumOfRowsOfPurchases");
			int indexPurchase = (int) MyFuelClient.msgFromServer;
			String saleName = "";
			boolean ifInSale = checkIfSalePatternAlreadyExistsOnChosenDate(LocalDate.now());
			if (ifInSale == true) {
				saleName = idSaleExists;
				Sale updateSale = null;
				boolean flagIfTheCustomerPurchasedSale = false;
				for (i = 0; i < arrSales.length; i++) {
					if (arrSales[i].getSaleName().equals(saleName)) {
						updateSale = arrSales[i];
						updateSale.setSumOfAllPurchases(
								arrSales[i].getSumOfAllPurchases() + Double.valueOf(txtPrice.getText()));
						for (j = 0; j < arrExistsPurchases.length; j++) {
							if (arrExistsPurchases[j].getCustomerId() == idConnect
									&& arrExistsPurchases[j].getSaleName() != null
									&& arrExistsPurchases[j].getSaleName().equals(saleName))
								flagIfTheCustomerPurchasedSale = true;
						}
						if (flagIfTheCustomerPurchasedSale == false)
							updateSale.setNumOfCustomersPurchased(arrSales[i].getNumOfCustomersPurchased() + 1);
					}
				}
				ClientUI.chat.accept(updateSale);
			}
			Purchase newPurchase = new Purchase(indexPurchase + 1, idConnect, licenseNumberFromLogin,
					Double.valueOf(txtQuantity.getText()), Double.valueOf(txtPrice.getText()), strPayment,
					chainNameChose, stationNameChose, saleName);
			ClientUI.chat.accept(newPurchase);
			flagDBSuccedd = (boolean) MyFuelClient.msgFromServer;
			if (flagDBSuccedd && Receipt.getSelectedToggle().equals(rbYes)) {
				showAlert("success", "Thank you for fueling at MyFuel \nReceipt will be sent to your mail");
				Thread t = new Thread(new SendEmail(customerConnect.getEmail(), "Receipt for fueling",
						"Customer Id: " + newPurchase.getCustomerId() + "\nCustomer Name: " + txtCustomerName.getText()
								+ "\nChain Name: " + newPurchase.getChainName() + "\n" + "Station Name: "
								+ newPurchase.getStationName() + "\nLicense Number: " + licenseNumberFromLogin
								+ "\nQuantity: " + newPurchase.getQuantityPurchase() + "\n" + "Price: "
								+ Double.valueOf(txtPriceBeforeDiscount.getText()) + "\u20AA"
								+ "\nPrice After Discount: " + newPurchase.getPricePurchase() + "\u20AA" + "\nPayment: "
								+ newPurchase.getPaymentPurchase()));
				t.start();
				Stage stage = (Stage) X.getScene().getWindow();
				// do what you have to do
				stage.close();
			} else if (flagDBSuccedd && Receipt.getSelectedToggle().equals(rbNo)) {
				showAlert("success", "Thank you for fueling at MyFuel");
				Stage stage = (Stage) X.getScene().getWindow();
				// do what you have to do
				stage.close();
			}
			GasStation updateGasStation = null;
			for (i = 0; i < allGasStationArr.length; i++) {
				if (allGasStationArr[i].getChainName().equals(chainNameChose)) {
					if (allGasStationArr[i].getStationName().equals(stationNameChose)) {
						if (fuelTypeForVehicle.contains(allGasStationArr[i].getFuelType())) {
							updateGasStation = allGasStationArr[i];
							updateGasStation.setStockQuantity(
									allGasStationArr[i].getStockQuantity() - Double.valueOf(txtQuantity.getText()));
						}
					}
				}
			}
			ClientUI.chat.accept(updateGasStation);
			if ((boolean) MyFuelClient.msgFromServer)
				System.out.println("");
		} else
			showAlert("Error", "You must first press start fueling");
	}

	/**
	 * This method checks if sale pattern exists in the purchase date
	 * 
	 * @return true or false
	 */
	boolean checkIfSalePatternAlreadyExistsOnChosenDate(LocalDate date) {
		int i, j;
		int idSaleExistsNum = 0;
		boolean[] arrFlag = new boolean[arrSalePatterns.length];
		String[] arrStartDateForIndex;
		int[] arrNumbersStartDateForIndex;
		LocalDate localStartDateForIndex;
		String[] arrEndDateForIndex;
		int[] arrNumbersEndDateForIndex;
		LocalDate localEndDateForIndex;
		for (i = 0; i < arrSalePatterns.length; i++) {
			// LocalDate for StartDate index
			arrStartDateForIndex = arrSalePatterns[i].getStartDate().split("[/]"); /// 05/04/2020
			arrNumbersStartDateForIndex = new int[arrStartDateForIndex.length];
			for (j = 0; j < arrStartDateForIndex.length; j++)
				arrNumbersStartDateForIndex[j] = Integer.valueOf(arrStartDateForIndex[j]);
			localStartDateForIndex = LocalDate.of(arrNumbersStartDateForIndex[2], arrNumbersStartDateForIndex[1],
					arrNumbersStartDateForIndex[0]);
			// LocalDate for EndDate index
			arrEndDateForIndex = arrSalePatterns[i].getEndDate().split("[/]"); /// 05/04/2020
			arrNumbersEndDateForIndex = new int[arrEndDateForIndex.length];
			for (j = 0; j < arrEndDateForIndex.length; j++)
				arrNumbersEndDateForIndex[j] = Integer.valueOf(arrEndDateForIndex[j]);
			localEndDateForIndex = LocalDate.of(arrNumbersEndDateForIndex[2], arrNumbersEndDateForIndex[1],
					arrNumbersEndDateForIndex[0]);
			if (date.isAfter(localStartDateForIndex)
					&& (date.isBefore(localEndDateForIndex) || date.isEqual(localEndDateForIndex)))
				arrFlag[i] = true;
			else
				arrFlag[i] = false;
		}
		for (i = 0; i < arrFlag.length; i++)
			if (arrFlag[i] == true)
				idSaleExistsNum = i + 1;
		for (i = 0; i < arrSales.length; i++) {
			if (arrSales[i].getPatternID() == idSaleExistsNum) {
				idSaleExists = arrSales[i].getSaleName();
				return true;
			}
		}
		return false;
	}

	/**
	 * This method limit the user to enter only numbers in the quantity field
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
	 * This method demonstrate a fueling(by a progress bar)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void startFuelingClick(MouseEvent event) {
		lblErrorQuantity.setText("");
		lblErrorPumpNumber.setText("");
		if (txtQuantity.getText() == null || txtQuantity.getText().equals("")) {
			lblErrorQuantity.setText("* You must fill quantity");
		} else {
			X.setDisable(true);
			flagIfPressedStartFueling = true;
			txtCustomerID.setDisable(true);
			txtCustomerName.setDisable(true);
			txtChainName.setDisable(true);
			txtStationName.setDisable(true);
			txtLicensePlateNumber.setDisable(true);
			txtPrice.setDisable(true);
			txtPriceBeforeDiscount.setDisable(true);
			txtPricePerLitre.setDisable(true);
			txtQuantity.setDisable(true);
			rbCash.setDisable(true);
			rbCreditCard.setDisable(true);
			rbNo.setDisable(true);
			rbYes.setDisable(true);
			rb1.setDisable(true);
			rb2.setDisable(true);
			rb3.setDisable(true);
			rb1.setStyle("-fx-opacity: 0.5");
			rb2.setStyle("-fx-opacity: 0.5");
			rb3.setStyle("-fx-opacity: 0.5");
			btnFinish.setDisable(true);
			btnStartFueling.setDisable(true);
			lblClearFields.setDisable(true);
			lblDiscountPrice.setDisable(true);
			lblNoPaymentForFull.setDisable(true);
			try {
				progressBar();
			} catch (Exception e) {
				e.printStackTrace();
			}

			btnFinish.setDisable(false);
		}
	}

	/**
	 * This method initialize progress bar and present him
	 */
	@SuppressWarnings("static-access")
	private void progressBar() {
		@SuppressWarnings("rawtypes")
		Task copyWorker = createWorker();
		ProgressDialog dialog = new ProgressDialog(copyWorker);
		dialog.initStyle(StageStyle.DECORATED.UNDECORATED);
		dialog.setGraphic(null);
		dialog.setTitle("Fueling Progress");
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
		new Thread(copyWorker).start();
		dialog.showAndWait();
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
					updateMessage("Fueling... please wait ");
					updateProgress(i + 1, 50);
				}
				return true;
			}
		};
	}

	/**
	 * This method shows pop up alert with suitable message to the user
	 * 
	 * @param msg1 The title of the alert
	 * @param msg2 The content text of the alert
	 */
	public void showAlert(String msg1, String msg2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		stage.toFront();
		alert.setTitle(msg1);
		alert.setHeaderText(null);
		alert.setContentText(msg2);

		alert.show();

	}

	/**
	 * This method gets array and removes the duplicate values inside him by the
	 * chosen key to remove
	 * 
	 * @param arr The chosen array to remove duplicates from him
	 * @return The array without the duplicates
	 */
	public static GasStation[] removeDuplicates(GasStation[] arr) {
		int end = arr.length;
		for (int i = 0; i < end; i++) {
			for (int j = i + 1; j < end; j++) {
				if (arr[i].getStationName().equals(arr[j].getStationName())) {
					int shiftLeft = j;
					for (int k = j + 1; k < end; k++, shiftLeft++) {
						arr[shiftLeft] = arr[k];
					}
					end--;
					j--;
				}
			}
		}
		GasStation[] whitelist = new GasStation[end];
		for (int i = 0; i < end; i++) {
			whitelist[i] = arr[i];
		}
		return whitelist;
	}

	/**
	 * This method checks the amount of the stock by the chain name,station name and
	 * the fuel type.
	 */
	public void checkStock() {
		ClientUI.chat.accept("getStockOfGasStationByChainStationFuelType:" + chainNameChose + ":" + stationNameChose
				+ ":" + fuelTypeForVehicle);
		stockInStation = (Double) MyFuelClient.msgFromServer;
		if (stockInStation == 0) {
			flag = true;
			txtCustomerID.setDisable(true);
			txtCustomerName.setDisable(true);
			txtChainName.setDisable(true);
			txtStationName.setDisable(true);
			txtLicensePlateNumber.setDisable(true);
			txtPrice.setDisable(true);
			txtPriceBeforeDiscount.setDisable(true);
			txtPricePerLitre.setDisable(true);
			txtQuantity.setDisable(true);
			rbCash.setDisable(true);
			rbCreditCard.setDisable(true);
			rbNo.setDisable(true);
			rbYes.setDisable(true);
			rb1.setDisable(true);
			rb2.setDisable(true);
			rb3.setDisable(true);
			rb1.setStyle("-fx-opacity: 0.5");
			rb2.setStyle("-fx-opacity: 0.5");
			rb3.setStyle("-fx-opacity: 0.5");
			btnFinish.setDisable(true);
			btnStartFueling.setDisable(true);
			lblClearFields.setDisable(true);
			lblDiscountPrice.setDisable(true);
			lblNoPaymentForFull.setDisable(true);
		}
	}

	/**
	 * This method calculate the price by the 3 sub methods.calculate price by
	 * purchase plan,subscription type and exist sale.
	 * 
	 * @return The total price
	 */
	public double updateMainPrice() {
		lblErrorQuantity.setText("");
		lblErrorPumpNumber.setText("");
		btnFinish.setDisable(false);
		btnStartFueling.setDisable(false);
		double price = 0, totalPrice = 0;
		if (txtQuantity.getText().equals("")) {
			txtPrice.setText("");
			txtPriceBeforeDiscount.setText("");
		} else {
			double quantity = Double.valueOf(txtQuantity.getText());
			if (quantity > stockInStation) {
				lblErrorQuantity.setText("* You can refuel until " + String.valueOf(stockInStation) + " liters");
				txtPrice.setText("");
				txtPriceBeforeDiscount.setText("");
				btnFinish.setDisable(true);
				btnStartFueling.setDisable(true);
			} else {
				price = (double) quantity * priceForLitre;
				if (price % 10 == 0)
					txtPriceBeforeDiscount.setText(String.valueOf((int) price));
				else
					txtPriceBeforeDiscount.setText(String.valueOf(price));
				totalPrice = updatePriceBySaleExists(price);
				totalPrice = updatePriceByPurchasePlan(totalPrice);
				totalPrice = updatePriceBySubscription(totalPrice);
			}
		}
		return totalPrice;
	}

	/**
	 * This sub method calculate price by subscription type.
	 * 
	 * @return The price calculated by subscription type
	 */
	public double updatePriceBySubscription(double num) {
		flagUpdatePriceBySubscription = false;
		double price = num;
		if (customerConnect.getSubscriptionType().contains("single")) { /////////
			price = price - ((price * 4.0) / 100);
			flagUpdatePriceBySubscription = true;
		} else if (customerConnect.getSubscriptionType().contains("multiple")) {
			price = price - ((price * 4.0 * (double) vehicleArrForIdConnect.length) / 100);
			price = price - ((price * 10.0) / 100);
			flagUpdatePriceBySubscription = true;
		} else if (customerConnect.getSubscriptionType().contains("full")) {
			price = price - ((price * 4.0 * (double) vehicleArrForIdConnect.length) / 100);
			price = price - ((price * 10.0) / 100);
			price = price - ((price * 3.0) / 100);
			flagUpdatePriceBySubscription = true;
		}
		// if subscription="casual" ---->no discount
		return price;
	}

	/**
	 * This sub method calculate price by exist sale.
	 * 
	 * @return The price calculated by exist sale
	 */
	public double updatePriceBySaleExists(double num) {
		flagUpdatePriceBySaleExists = false;
		int i;
		double price = num;
		boolean ifThePurchaseInSaleExists = checkIfSalePatternAlreadyExistsOnChosenDate(LocalDate.now());
		if (ifThePurchaseInSaleExists == true) {
			for (i = 0; i < arrSales.length; i++) {
				if (arrSales[i].getSaleName().equals(idSaleExists)) {
					price = price - (((double) arrSales[i].getDiscount() * price) / 100);
					flagUpdatePriceBySaleExists = true;
				}
			}
		}
		return price;
	}

	/**
	 * This sub method calculate price by purchase plan.
	 * 
	 * @return The price calculated by purchase plan
	 */
	public double updatePriceByPurchasePlan(double num) {
		flagUpdatePriceByPurchasePlan = false;
		double price = num;
		if (purchasePlanForId.getPlanType().equals("multiple")) {
			price = price - ((price * 2.0) / 100);
			flagUpdatePriceByPurchasePlan = true;
		} else if (purchasePlanForId.getPlanType().equals("exclusive")) {
			price = price - ((price * 5.0) / 100);
			flagUpdatePriceByPurchasePlan = true;
		}
		// if planType= "none" ----> no discount
		return price;
	}

	/**
	 * This method calls to updateMainPrice method at the moment the quantity
	 * entered by the user. The method infered the user when discount occurred.
	 * 
	 * @param txtField The quantity that entered by the user
	 */
	public void calculatePrice(TextField txtField) {
		btnFinish.setDisable(false);
		btnStartFueling.setDisable(false);
		lblDiscountPrice.setText("");
		txtField.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double price = updateMainPrice();
				if (price == 0) {
					lblDiscountPrice.setText("");
					txtPrice.setText("");
				} else if (price % 10 == 0)
					txtPrice.setText(String.valueOf((int) price));
				else
					txtPrice.setText(String.valueOf(price));
				txtPrice.setEditable(false);
				txtPriceBeforeDiscount.setEditable(false);
				if (price == 0) {
					lblDiscountPrice.setText("");
					btnFinish.setDisable(true);
					btnStartFueling.setDisable(true);
				} else if (flagUpdatePriceByPurchasePlan && flagUpdatePriceBySaleExists
						&& flagUpdatePriceBySubscription)
					lblDiscountPrice.setText("* Discount by sale,purchase plan and subscription");
				else if (flagUpdatePriceByPurchasePlan && flagUpdatePriceBySaleExists && !flagUpdatePriceBySubscription)
					lblDiscountPrice.setText("* Discount by sale and purchase plan");
				else if (flagUpdatePriceByPurchasePlan && !flagUpdatePriceBySaleExists && flagUpdatePriceBySubscription)
					lblDiscountPrice.setText("* Discount by purchase plan and subscription");
				else if (!flagUpdatePriceByPurchasePlan && flagUpdatePriceBySaleExists && flagUpdatePriceBySubscription)
					lblDiscountPrice.setText("* Discount by sale and subscription");
				else if (!flagUpdatePriceByPurchasePlan && !flagUpdatePriceBySaleExists
						&& flagUpdatePriceBySubscription)
					lblDiscountPrice.setText("* Discount by subscription");
				else if (flagUpdatePriceByPurchasePlan && !flagUpdatePriceBySaleExists
						&& !flagUpdatePriceBySubscription)
					lblDiscountPrice.setText("* Discount by purchase plan");
				else if (!flagUpdatePriceByPurchasePlan && flagUpdatePriceBySaleExists
						&& !flagUpdatePriceBySubscription)
					lblDiscountPrice.setText("* Discount by sale");
				else if (!flagUpdatePriceByPurchasePlan && !flagUpdatePriceBySaleExists
						&& !flagUpdatePriceBySubscription)
					lblDiscountPrice.setText("* No discount");
			}
		});
	}

	/**
	 * This method initialize the fuel purchase screen when it opened. Takes data of
	 * purchase,sale,vehicle,sale pattern,gasstions,customer,purchase plan from the
	 * DB.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int i;
		ClientUI.chat.accept("showSales");
		arrSales = (Sale[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showSalesPatterns");
		arrSalePatterns = (SalePattern[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showPurchases");
		arrExistsPurchases = (Purchase[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showAllGasStations");
		allGasStationArr = (GasStation[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showCustomers");
		arrCustomer = (Customer[]) MyFuelClient.msgFromServer;

		int numPump = random.nextInt(3);
		if (numPump == 0)
			Pump.selectToggle(rb1);
		else if (numPump == 1)
			Pump.selectToggle(rb2);
		else if (numPump == 2)
			Pump.selectToggle(rb3);
		rb1.setDisable(true);
		rb1.setStyle("-fx-opacity: 1");
		rb2.setDisable(true);
		rb2.setStyle("-fx-opacity: 1");
		rb3.setDisable(true);
		rb3.setStyle("-fx-opacity: 1");
		ClientUI.chat.accept("showVehicles");
		arrVehicles = (Vehicle[]) MyFuelClient.msgFromServer;
		for (i = 0; i < arrVehicles.length; i++) {
			if (arrVehicles[i].getLicensePlateNumber() == licenseNumberFromLogin)
				idConnect = arrVehicles[i].getCustomerId();
		}
		for (i = 0; i < arrCustomer.length; i++) {
			if (arrCustomer[i].getID() == idConnect)
				customerConnect = arrCustomer[i];
		}
		txtCustomerID.setText(String.valueOf(idConnect));
		txtCustomerName.setText(customerConnect.getFirstName() + " " + customerConnect.getLastName());
		txtCustomerName.setEditable(false);
		txtCustomerID.setEditable(false);
		txtPricePerLitre.setEditable(false);

		ClientUI.chat.accept("getDetailsOfVehiclesForCustomerIdConnecnt:" + idConnect);
		vehicleArrForIdConnect = (Vehicle[]) MyFuelClient.msgFromServer;

		if (customerConnect.getSubscriptionType().contains("full")) {
			rbCash.setDisable(true);
			rbCreditCard.setDisable(true);
			lblNoPaymentForFull.setText("* Prepayment for a full monthly subscription");
		}

		txtLicensePlateNumber.setText(String.valueOf(licenseNumberFromLogin));
		txtLicensePlateNumber.setEditable(false);

		ClientUI.chat.accept("getDetailsOfPurchasePlanForCustomerIdConnecnt:" + idConnect);
		purchasePlanForId = (PurchasePlan) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("getDetailsOfChain");
		chainArr = (ChainOfGas[]) MyFuelClient.msgFromServer;

		int j;
		ArrayList<String> alChainName = new ArrayList<String>();
		if (purchasePlanForId.getPlanType().equals("multiple")) { // 2/3
			for (ChainOfGas chain : purchasePlanForId.getArrGasStation()) {
				if (chain.getName() != null)
					alChainName.add(chain.getName());
			}
		} else if (purchasePlanForId.getPlanType().equals("exclusive")) { // 1
			for (ChainOfGas chain : purchasePlanForId.getArrGasStation()) {
				if (chain.getName() != null)
					alChainName.add(chain.getName());
			}
		} else { // none
			for (j = 0; j < chainArr.length; j++)
				alChainName.add(chainArr[j].getName());
		}
		int num2 = random.nextInt(alChainName.size());
		txtChainName.setText(alChainName.get(num2));
		txtChainName.setEditable(false);
		chainNameChose = alChainName.get(num2);

		fuelTypeForVehicle = "";
		for (i = 0; i < arrVehicles.length; i++) {
			if (arrVehicles[i].getLicensePlateNumber() == licenseNumberFromLogin
					&& arrVehicles[i].getCustomerId() == idConnect)
				fuelTypeForVehicle = arrVehicles[i].getFuelType();
		}

		ClientUI.chat.accept("getPriceForLitreForChoosenFuelType:" + fuelTypeForVehicle + ":" + chainNameChose);
		priceForLitre = (Double) MyFuelClient.msgFromServer;

		if (priceForLitre % 10 == 0)
			txtPricePerLitre.setText(String.valueOf((int) priceForLitre));
		else
			txtPricePerLitre.setText(String.valueOf(priceForLitre));
		txtPricePerLitre.setEditable(false);
		if ("scooter's fuel".contains(fuelTypeForVehicle))
			lblFuelType.setText("(scooter):");
		else
			lblFuelType.setText("(" + fuelTypeForVehicle + "):");

		calculatePrice(txtQuantity);

		ClientUI.chat.accept("getDetailsOfGasStationForChain:" + chainNameChose);
		gasStationArr = (GasStation[]) MyFuelClient.msgFromServer;
		gasStationArrNoDuplicates = removeDuplicates(gasStationArr);

		ArrayList<String> alStationName = new ArrayList<String>();
		for (i = 0; i < gasStationArrNoDuplicates.length; i++) {
			alStationName.add(gasStationArrNoDuplicates[i].getStationName());
		}

		int num3 = random.nextInt(alStationName.size());
		txtStationName.setText(alStationName.get(num3));
		txtStationName.setEditable(false);
		stationNameChose = gasStationArrNoDuplicates[num3].getStationName();

		// after all random

		txtPricePerLitre.setEditable(false);
		txtPrice.setEditable(false);
		txtPriceBeforeDiscount.setEditable(false);
		txtQuantity.setEditable(true);
		checkStock();
		if (flag) {
			showAlert("No stock",
					"Unfortunately, the stock in the station is over, unable to refuel now\nPlease try another time");

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
			MyPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method limit the user to enter only double values in the quantity field
	 * 
	 * @param e The event we handle
	 */
	@FXML
	public void onlyDouble(KeyEvent e) {
		String c = e.getCharacter();
		if (!c.matches("[0-9]+") && !c.equals("."))
			e.consume();
		else if (txtQuantity.getText().isEmpty() && c.equals("."))
			e.consume();
		else if (c.equals(".") && txtQuantity.getText().contains("."))
			e.consume();

	}

	/**
	 * This function opens the GUI of the Login
	 * 
	 * @param primaryStage - our primary stage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/FuelingForm.fxml"));
		Scene scene = new Scene(root);
		FunctionForGUI functionForGUI = new FunctionForGUI();
		functionForGUI.removeDefaultBarMenu(primaryStage);
		functionForGUI.MakeScreenDragble(root, primaryStage);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}