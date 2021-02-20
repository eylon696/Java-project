package client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

import client.ClientUI;
import client.MyFuelClient;
import entity.OrderHomeHeatingFuel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sendEmail.SendEmail;

/**
 * The class PurchaseHomeHeatingFuelControllerClient defined how the purchase
 * home heating fuel window would work
 * 
 * @author Eylon
 *
 */
public class PurchaseHomeHeatingFuelControllerClient implements Initializable {

	@FXML	
	private Label lblIls2;

	@FXML
	private Label lblIls1;
	@FXML
	private Label lblErrorQuantity;

	@FXML
	private Label lblErrorAddress;

	@FXML
	private Tooltip toolTipDate;
	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnOrder;

	@FXML
	private Label btnClear;

	@FXML
	private TextField txtQuantity;

	@FXML
	private TextField txtCity;

	@FXML
	private TextField txtStreet;

	@FXML
	private TextField txtApt;

	@FXML
	private RadioButton rbOrdinary;

	@FXML
	private TextField txtPriceBeforeDiscount;

	@FXML
	private ToggleGroup DeliveryType;

	@FXML
	private RadioButton rbExpress;

	@FXML
	private TextField txtPrice;

	@FXML
	private TextField txtEstimatedDeliveryDate;

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
	private RadioButton rbNow;

	private float priceOfHomeHeatingFuel;

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
	 * The function clear all the data in the form
	 * 
	 * @param event the event we handle
	 * @throws ParseException
	 */
	@FXML
	void clearAllFields(MouseEvent event) throws ParseException {
		setEstimatedDateToOneWeekLater();
		lblErrorAddress.setText("");
		lblErrorQuantity.setText("");
		txtQuantity.setText("");
		txtCity.setText("");
		txtStreet.setText("");
		txtApt.setText("");
		txtPrice.setText("");
		txtPriceBeforeDiscount.setText("");
		DeliveryType.selectToggle(rbOrdinary);
		Receipt.selectToggle(rbYes);
		Payment.selectToggle(rbCreditCard);
	}

	/**
	 * The function sends to the server controller the order that the user wants to
	 * do
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void orderFunction(MouseEvent event) {
		lblErrorAddress.setText("");
		lblErrorQuantity.setText("");
		if (txtQuantity.getText().equals("") || txtCity.getText().equals("") || txtStreet.getText().equals("")
				|| txtApt.getText().equals("")) {

			if (txtQuantity.getText().equals(""))
				lblErrorQuantity.setText("* You must insert quantity");
			if (txtCity.getText().equals("") || txtStreet.getText().equals("") || txtApt.getText().equals(""))
				lblErrorAddress.setText("* You must insert a full address");
		} else {
			RadioButton selectedRadioButtonDeliveryType = (RadioButton) DeliveryType.getSelectedToggle();
			RadioButton selectedRadioButtonPayment = (RadioButton) Payment.getSelectedToggle();
			RadioButton selectedRadioButtonEmail = (RadioButton) Receipt.getSelectedToggle();
			LocalDateTime myDateObj = LocalDateTime.now();
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String formattedDate = myDateObj.format(myFormatObj);
			ClientUI.chat.accept("findTheNextIDInPurchaseHomeHeatingFuel");
			OrderHomeHeatingFuel newOrder = new OrderHomeHeatingFuel(((Integer) MyFuelClient.msgFromServer) + 1,
					UserLoginController.customer.getID(), formattedDate, Double.valueOf(txtQuantity.getText()),
					txtCity.getText().toLowerCase() + " " + txtStreet.getText().toLowerCase() + " "
							+ txtApt.getText().toLowerCase(),
					txtEstimatedDeliveryDate.getText(), selectedRadioButtonDeliveryType.getText().toLowerCase(),
					Float.valueOf(txtPrice.getText()), selectedRadioButtonPayment.getText().toLowerCase(), "no");
			ClientUI.chat.accept(newOrder);
			if (selectedRadioButtonEmail.getText().toLowerCase().equals("yes")) {
				popAlert("Success", "Your order has been made and a receipt will be sent to your email");
				changeScreen("/gui/HomeHeatingFuel.fxml");
				Thread t = new Thread(new SendEmail(UserLoginController.customer.getEmail(), "Receipt for order",
						"Order Id: " + newOrder.getId() + "\nCustomer Id: " + newOrder.getCustomerId() + "\n"
								+ "Date Of Purchase: " + newOrder.getDate() + "\nQuantity: " + newOrder.getQuantity()
								+ "\n" + "Address: " + newOrder.getAddress() + "\nEstimated Date: "
								+ newOrder.getEstimatedDate() + "\nDelivery Type: " + newOrder.getDeliveryType()
								+ "\nPrice: " + newOrder.getPrice() + "\u20AA" + "\nPayment: "
								+ newOrder.getPayment()));
				t.start();
			} else {
				popAlert("Success", "Your order has been made");
				changeScreen("/gui/HomeHeatingFuel.fxml");
			}

		}

	}

	/**
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblIls1.setText("\u20AA");
		lblIls2.setText("\u20AA");
		toolTipDate.setText("The estimated date is calculated based on the chosen delivery type");
		limitTxt(txtQuantity, 45);
		ClientUI.chat.accept("priceOfHomeHeatingFuel");
		priceOfHomeHeatingFuel = (Float) MyFuelClient.msgFromServer;

		try {
			setEstimatedDateToOneWeekLater();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtPrice.setEditable(false);
		txtPriceBeforeDiscount.setEditable(false);
		txtEstimatedDeliveryDate.setEditable(false);
	}

	/**
	 * The function limits the text field to amount of wanted chars and adds a
	 * listener to the price text field
	 * 
	 * @param txtField the text field we want to limit
	 * @param limit    the number of chars we agree to accept
	 */
	public void limitTxt(TextField txtField, int limit) {
		txtField.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				RadioButton selectedRadioButton = (RadioButton) DeliveryType.getSelectedToggle();
				if (!txtQuantity.getText().equals("")) {
					txtPrice.setText(String.valueOf(
							updatePrice(Double.valueOf(txtQuantity.getText()), selectedRadioButton.getText())));
					txtPriceBeforeDiscount
							.setText(String.valueOf(priceOfHomeHeatingFuel * Double.valueOf(txtQuantity.getText())));
				} else {
					txtPrice.setText("");
					txtPriceBeforeDiscount.setText("");
				}
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (txtField.getText().length() >= limit) {

						// if it's 11th character then just setText to previous
						// one
						txtField.setText(txtField.getText().substring(0, limit));

					}

				}

			}
		});
	}

	/**
	 * The function limits the text field only to numbers
	 * 
	 * @param event the event we handle
	 */
	@FXML
	void limit(KeyEvent event) {
		String c = event.getCharacter();
		if (!c.matches("[0-9]+"))
			event.consume();
	}

	/**
	 * The function returns the calculated price
	 * 
	 * @param quantity the quantity of fuel the user wants to purchase
	 * @param delivery the delivery type the user chooses
	 * @return The new price
	 */
	public double updatePrice(double quantity, String delivery) {
		double newPrice = quantity * priceOfHomeHeatingFuel;
		if (delivery.equals("Express"))
			newPrice = (double) (newPrice * 1.02);
		if (quantity >= 600 && quantity <= 800)
			newPrice = (double) (newPrice - (newPrice * 0.03));
		else if (quantity > 800)
			newPrice = (double) (newPrice - (newPrice * 0.04));

		return newPrice;

	}

	/**
	 * The function shows the price according to the delivery type and quantity
	 */
	void changePriceAccordingToRB() {
		RadioButton selectedRadioButton = (RadioButton) DeliveryType.getSelectedToggle();
		if (!txtQuantity.getText().equals(""))
			txtPrice.setText(
					String.valueOf(updatePrice(Double.valueOf(txtQuantity.getText()), selectedRadioButton.getText())));
	}

	/**
	 * The function shows the estimated delivery date if express delivery was chosen
	 * 
	 * @param event the event we handle
	 * @throws ParseException
	 */
	@FXML
	void rbExpressFunction(MouseEvent event) throws ParseException {
		changePriceAccordingToRB();

		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = myDateObj.format(myFormatObj);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(formattedDate));
		if (myDateObj.getHour() < 18)
			c.add(Calendar.DATE, 0);
		else
			c.add(Calendar.DATE, 1); // number of days to add
		formattedDate = sdf.format(c.getTime()); // formattedDate is now the new date

		txtEstimatedDeliveryDate.setText(formattedDate);

	}

	/**
	 * The function sets the estimated delivery date if ordinary delivery was chosen
	 * 
	 * @param event the event we handle
	 * @throws ParseException
	 */
	@FXML
	void rbOrdinaryFunction(MouseEvent event) throws ParseException {
		changePriceAccordingToRB();
		setEstimatedDateToOneWeekLater();

	}

	/**
	 * The function sets the estimated delivery date to one week from the present
	 * day
	 * 
	 * @throws ParseException
	 */
	public void setEstimatedDateToOneWeekLater() throws ParseException {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = myDateObj.format(myFormatObj);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(formattedDate));
		c.add(Calendar.DATE, 7); // number of days to add
		formattedDate = sdf.format(c.getTime()); // formattedDate is now the new date

		txtEstimatedDeliveryDate.setText(formattedDate);
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
	 * The function limits the text field to only double type
	 * 
	 * @param e the event we handle
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
}
