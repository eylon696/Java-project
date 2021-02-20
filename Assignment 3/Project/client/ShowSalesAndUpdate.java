package client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import client.ClientUI;
import client.MyFuelClient;
import entity.Sale;
import entity.SaleForTable;
import entity.SalePattern;
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
 * This class ShowSalesAndUpdate defined how the ShowSalesAndUpdate window would
 * work
 * 
 * @author gal
 *
 */
public class ShowSalesAndUpdate implements Initializable {
	SaleForTable[] arrSalesForTable;
	ObservableList<SaleForTable> arrListSales = FXCollections.observableArrayList();
	SalePattern[] arrSalePatterns;
	Sale[] arrSales;

	@FXML
	private Pane MyPane;
	
	@FXML
	private Button btnBack = null;

	@FXML
	private Button btnEnd = null;

	@FXML
	private TableView<SaleForTable> tableSales;

	@FXML
	private TableColumn<SaleForTable, String> clmnSaleName;

	@FXML
	private TableColumn<SaleForTable, String> clmnStartDate;

	@FXML
	private TableColumn<SaleForTable, String> clmnEndDate;

	@FXML
	private TableColumn<SaleForTable, Integer> clmnDiscount;

	@FXML
	private TableColumn<SaleForTable, String> clmnSaleReason;

	@FXML
	private Label lblError;

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
	 * This method initialize the show show sales and update screen when it opened.
	 * Takes data of sales and sale patterns from DB and presents the data.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblError.setText("");
		int i, j, k = 0;
		clmnSaleName.setCellValueFactory(new PropertyValueFactory<SaleForTable, String>("saleName"));
		clmnStartDate.setCellValueFactory(new PropertyValueFactory<SaleForTable, String>("startDate"));
		clmnEndDate.setCellValueFactory(new PropertyValueFactory<SaleForTable, String>("endDate"));
		clmnDiscount.setCellValueFactory(new PropertyValueFactory<SaleForTable, Integer>("discount"));
		clmnSaleReason.setCellValueFactory(new PropertyValueFactory<SaleForTable, String>("saleReason"));

		ClientUI.chat.accept("showSalesPatterns");
		arrSalePatterns = (SalePattern[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showSales");
		arrSales = (Sale[]) MyFuelClient.msgFromServer;
		arrSalesForTable = new SaleForTable[arrSales.length];
		for (i = 0; i < arrSales.length; i++) {
			for (j = 0; j < arrSalePatterns.length; j++) {
				if (arrSales[i].getPatternID() == arrSalePatterns[j].getIdPattern()) {
					arrSalesForTable[k] = new SaleForTable(arrSales[i].getSaleName(), arrSalePatterns[j].getStartDate(),
							arrSalePatterns[j].getEndDate(), arrSales[i].getDiscount(), arrSales[i].getSaleReason());
					k++;
				}
			}
		}
		System.out.println(arrSales.length);
		System.out.println(arrSalesForTable.length);
		for (i = 0; i < arrSalesForTable.length; i++) {
			arrListSales.add(arrSalesForTable[i]);
		}
		tableSales.setItems(arrListSales);
	}

	/**
	 * This method updates the end date of sale and set it in the DB
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void endClick(MouseEvent event) {
		lblError.setText("");
		int i = 0, idSalePattern = 0;
		// LocalDate for StartDate Selected
		String startDate = tableSales.getSelectionModel().getSelectedItem().getStartDate();
		String[] arrStartDate = startDate.split("[/]"); /// 05/04/2020
		int[] arrNumbersStartDate = new int[arrStartDate.length];
		for (i = 0; i < arrStartDate.length; i++)
			arrNumbersStartDate[i] = Integer.valueOf(arrStartDate[i]);
		LocalDate localStartDate = LocalDate.of(arrNumbersStartDate[2], arrNumbersStartDate[1], arrNumbersStartDate[0]);
		// LocalDate for EndDate Selected
		String endDate = tableSales.getSelectionModel().getSelectedItem().getEndDate();
		String[] arrEndDate = endDate.split("[/]"); /// 05/04/2020
		int[] arrNumbersEndDate = new int[arrEndDate.length];
		for (i = 0; i < arrEndDate.length; i++)
			arrNumbersEndDate[i] = Integer.valueOf(arrEndDate[i]);
		LocalDate localEndDate = LocalDate.of(arrNumbersEndDate[2], arrNumbersEndDate[1], arrNumbersEndDate[0]);
		// String format for today
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate today = LocalDate.now();
		String endDateToUpdate = dtf.format(today);
		if(localStartDate.isAfter(today))
			lblError.setText("* You can only end an active sale");
		else if (tableSales.getSelectionModel().getSelectedItem() != null && (localEndDate.isAfter(today) ||  localEndDate.isEqual(today))) {
			String saleName = tableSales.getSelectionModel().getSelectedItem().getSaleName();
			for (i = 0; i < arrSales.length; i++) {
				if (saleName.equals(arrSales[i].getSaleName()))
					idSalePattern = arrSales[i].getPatternID();
			}
			ClientUI.chat
					.accept("updateSalePatternWithIdPattern:" + String.valueOf(idSalePattern) + ":" + endDateToUpdate);
			if (((boolean) MyFuelClient.msgFromServer))
				changeScreen("/gui/ShowSalesAndUpdate.fxml");
			lblError.setText("");
		} else
			lblError.setText("* You tried to end an inactive sale");
	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backClick(MouseEvent event) {
		changeScreen("/gui/SystemOperationsMarketingManager.fxml");
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
