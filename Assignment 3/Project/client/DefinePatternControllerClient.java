package client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import client.ClientUI;
import client.MyFuelClient;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The class DefinePatternControllerClient defined how the DefinePattern window
 * would work
 * 
 * @author gal
 *
 */	
public class DefinePatternControllerClient implements Initializable {
	SalePattern[] arrSalesPatterns;
	ObservableList<SalePattern> arrListPatterns = FXCollections.observableArrayList();
	public static String[] arrSaveData = new String[2];

	@FXML
	private Label lblNavigationBar;
	@FXML
	private Button btnBack = null;
	@FXML
	private Pane MyPane;

	@FXML
	private Label lblClear;

	@FXML
	private Button btnAddPattern = null;

	@FXML
	private TableView<SalePattern> tablePatterns;

	@FXML
	private TableColumn<SalePattern, String> clmnStartDate;

	@FXML
	private TableColumn<SalePattern, String> clmnEndDate;

	@FXML
	private TableColumn<SalePattern, String> clmnDescription;

	@FXML
	private DatePicker dpStartDate;

	@FXML
	private DatePicker dpEndDate;

	@FXML
	private TextArea txtDescription;

	@FXML
	private Label lblErrorStartDate;

	@FXML
	private Label lblErrorEndDate;

	@FXML
	private Label lblErrorDescription;

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

	boolean flag;
	String startDate;
	LocalDate localStartDate;
	String endDate;
	LocalDate localEndDate;
	int idPattern;

	/**
	 * This method initialize the define pattern screen when it opened. Takes sale
	 * pattern from the DB to present and there is a option to add new sale pattern.
	 * New sale pattern contains start date,end date and description.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int i;
		lblNavigationBar.setText("");
		if(SaleControllerClient.flagIfComeFromSale)
			lblNavigationBar.setText("System Operations -> Sales -> Sale Pattern");
		else lblNavigationBar.setText("System Operations -> Sale Pattern");
		clmnStartDate.setCellValueFactory(new PropertyValueFactory<SalePattern, String>("StartDate"));
		clmnEndDate.setCellValueFactory(new PropertyValueFactory<SalePattern, String>("EndDate"));
		clmnDescription.setCellValueFactory(new PropertyValueFactory<SalePattern, String>("Description"));
		ClientUI.chat.accept("showSalesPatterns");
		arrSalesPatterns = (SalePattern[]) MyFuelClient.msgFromServer;
		for (i = 0; i < arrSalesPatterns.length; i++) {
			arrListPatterns.add(arrSalesPatterns[i]);
		}
		tablePatterns.setItems(arrListPatterns);
		LimitAreaAndTextFields.limitTxtArea(txtDescription, 500);

		dpStartDate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) < 0);
			}
		});
		dpEndDate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) < 0);
			}
		});
	}

	/**
	 * This method adds sale pattern to DB when this button pressed
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void addPatternClick(MouseEvent event) {
		lblErrorStartDate.setText("");
		lblErrorEndDate.setText("");
		lblErrorDescription.setText("");
		int i = 0;
		if (dpStartDate.getValue() == null || dpEndDate.getValue() == null || txtDescription.getText() == null
				|| txtDescription.getText().equals("")) {
			if (dpStartDate.getValue() == null)
				lblErrorStartDate.setText("* You must choose start date");
			if (dpEndDate.getValue() == null)
				lblErrorEndDate.setText("* You must choose end date");
			if (txtDescription.getText() == null || txtDescription.getText().equals(""))
				lblErrorDescription.setText("* You must fill discription");
		} else {
			// StartDate with formt dd/MM/yyyy:
			String[] arrStartDate = String.valueOf(dpStartDate.getValue()).split("-"); /// 2020,05,14
			int[] arrNumbersStartDate = new int[arrStartDate.length];
			for (i = 0; i < arrStartDate.length; i++)
				arrNumbersStartDate[i] = Integer.valueOf(arrStartDate[i]);
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			localStartDate = LocalDate.of(arrNumbersStartDate[0], arrNumbersStartDate[1], arrNumbersStartDate[2]);
			startDate = dtf1.format(localStartDate);
			//
			// EndDate with formt dd/MM/yyyy:
			String[] arrEndDate = String.valueOf(dpEndDate.getValue()).split("-");
			int[] arrNumbersEndDate = new int[arrEndDate.length];
			for (i = 0; i < arrEndDate.length; i++)
				arrNumbersEndDate[i] = Integer.valueOf(arrEndDate[i]);
			DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			localEndDate = LocalDate.of(arrNumbersEndDate[0], arrNumbersEndDate[1], arrNumbersEndDate[2]);
			endDate = dtf2.format(localEndDate);
			//
			long date1 = dpStartDate.getValue().toEpochDay();
			long date2 = dpEndDate.getValue().toEpochDay();
			LocalDate today = LocalDate.now();
			long days = (int) (date1 - date2);
			if (localStartDate.isBefore(today))
				lblErrorStartDate.setText("* You must choose start date from the present date forward");
			else if (days > 0) // StartDate>EndDate
				lblErrorEndDate.setText("* You must choose an end date that comes after the start date");
			else {
				if (checkIfSalePatternAlreadyExistsOnChosenDates() == true)
					lblErrorStartDate.setText("* Sale pattern already exists on the chosen dates, pick other ones");
				else {
					ClientUI.chat.accept("checkIdPatternToInsert");
					idPattern = (int) MyFuelClient.msgFromServer;
					idPattern++;
					SalePattern newSalePattern = new SalePattern(idPattern, startDate, endDate,
							txtDescription.getText());
					ClientUI.chat.accept(newSalePattern);
					flag = (boolean) MyFuelClient.msgFromServer;
					if (((boolean) MyFuelClient.msgFromServer))
						showAlert("Success", "The sale pattern has been added successfuly");

					returnToOtherForm(event);
				}
			}
		}
	}

	/**
	 * This method checks if sale pattern is already exists on the chosen dates
	 * 
	 * @return true or false
	 */
	boolean checkIfSalePatternAlreadyExistsOnChosenDates() {
		int i, j;
		boolean[] arrFlag = new boolean[arrSalesPatterns.length];
		String[] arrStartDateForIndex;
		int[] arrNumbersStartDateForIndex;
		LocalDate localStartDateForIndex;
		String[] arrEndDateForIndex;
		int[] arrNumbersEndDateForIndex;
		LocalDate localEndDateForIndex;
		for (i = 0; i < arrSalesPatterns.length; i++) {
			// LocalDate for StartDate index
			arrStartDateForIndex = arrSalesPatterns[i].getStartDate().split("[/]"); /// 05/04/2020
			arrNumbersStartDateForIndex = new int[arrStartDateForIndex.length];
			for (j = 0; j < arrStartDateForIndex.length; j++)
				arrNumbersStartDateForIndex[j] = Integer.valueOf(arrStartDateForIndex[j]);
			localStartDateForIndex = LocalDate.of(arrNumbersStartDateForIndex[2], arrNumbersStartDateForIndex[1],
					arrNumbersStartDateForIndex[0]);
			//
			// LocalDate for EndDate index
			arrEndDateForIndex = arrSalesPatterns[i].getEndDate().split("[/]"); /// 05/04/2020
			arrNumbersEndDateForIndex = new int[arrEndDateForIndex.length];
			for (j = 0; j < arrEndDateForIndex.length; j++)
				arrNumbersEndDateForIndex[j] = Integer.valueOf(arrEndDateForIndex[j]);
			localEndDateForIndex = LocalDate.of(arrNumbersEndDateForIndex[2], arrNumbersEndDateForIndex[1],
					arrNumbersEndDateForIndex[0]);
			//
			if ((localEndDate.isBefore(localStartDateForIndex) && localStartDate.isBefore(localStartDateForIndex))
					|| (localStartDate.isAfter(localEndDateForIndex) && localEndDate.isAfter(localEndDateForIndex)))
				arrFlag[i] = false;
			else
				arrFlag[i] = true;
		}
		for (i = 0; i < arrFlag.length; i++)
			if (arrFlag[i] == true)
				return true;
		return false;
	}

	/**
	 * This method shows pop up alert with suitable message to the user
	 * 
	 * @param msg1 The title of the alert
	 * @param msg2 The content text of the alert
	 */
	public void showAlert(String msg1, String msg2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(msg1);
		alert.setHeaderText(null);
		alert.setContentText(msg2);
		alert.show();
	}

	/**
	 * This method changes screen to the chosen screen
	 * 
	 * @param event The event we handle
	 */
	void returnToOtherForm(MouseEvent event) {
		if (SaleControllerClient.flagIfComeFromSale == true) {
			arrSaveData = SaleControllerClient.arrDataOfSaleForm;
			changeScreen("/gui/SalesForm.fxml");
		} else
			changeScreen("/gui/SystemOperationsMarketingManager.fxml");

	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backClick(MouseEvent event) {
		returnToOtherForm(event);
	}

	/**
	 * This method clears the form
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clearClick(MouseEvent event) {
		dpStartDate.setValue(null);
		dpEndDate.setValue(null);
		txtDescription.clear();
		lblErrorStartDate.setText("");
		lblErrorEndDate.setText("");
		lblErrorDescription.setText("");
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