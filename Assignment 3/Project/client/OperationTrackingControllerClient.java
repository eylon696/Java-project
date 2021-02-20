package client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.ClientConsole;
import client.ClientUI;
import client.MyFuelClient;
import entity.AnalyticSystemRank;
import entity.Customer;
import entity.Purchase;
import entity.RankForTable;
import entity.Vehicle;
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
 * The class OperationTrackingControllerClient defined how the OperationTracking
 * window would work
 * 
 * @author gal
 *
 */
public class OperationTrackingControllerClient implements Initializable {
	AnalyticSystemRank[] arrRanks;
	RankForTable[] arrRanksForList;
	RankForTable[] arrRanksForListToShow;
	ObservableList<RankForTable> arrListRanks = FXCollections.observableArrayList();
	Purchase[] arrPurchasesUntilDate;
	Purchase[] arrPurchases;
	Vehicle[] arrVehicles;
	Customer[] arrCustomers;

	@FXML	
	private Label lblNavigationBar;
	@FXML
	private Button btnBack = null;

	@FXML
	private Pane MyPane;

	@FXML
	private Button btnAddSale = null;

	@FXML
	private Label lblDate;

	@FXML
	private Label lblHere;

	@FXML
	private Label lblAlg;

	@FXML
	private TableView<RankForTable> ranksTable;

	@FXML
	private TableColumn<RankForTable, String> clmnFirstName;

	@FXML
	private TableColumn<RankForTable, String> clmnLastName;

	@FXML
	private TableColumn<RankForTable, Integer> clmnCustomerID;

	@FXML
	private TableColumn<RankForTable, Double> clmnRank;

	public ClientConsole chat = new ClientConsole("localhost", 5555);
	public static boolean flagIfComeFromRankForm = false;
	LocalDate lastDateToCheck;

	@FXML
	private Button Minus;
	@FXML
	private Button X;

	/**
	 * This function exit the current scene (exit the application)
	 * 
	 * @param event
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
	 * This method initialize the operation tracking screen when it opened. Takes
	 * Data of customer from the DB and calculate his rank by the written algorithm.
	 * The ranks are between 1 to 9.5 and calculated by fuel type,purchase time and
	 * customer type.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblNavigationBar.setText("System Operations -> Operation Tracking ");
		int count = 0, t = 0;
		int i, j, k, index, s = 0;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		if (LocalDate.now().getDayOfWeek().getValue() == 7)
			lblDate.setText(dtf.format(now));
		else {
			if (LocalDate.now().getDayOfWeek().getValue() == 1)
				lblDate.setText(dtf.format(LocalDate.now().minusDays(1)));
			else if (LocalDate.now().getDayOfWeek().getValue() == 2)
				lblDate.setText(dtf.format(LocalDate.now().minusDays(2)));
			else if (LocalDate.now().getDayOfWeek().getValue() == 3)
				lblDate.setText(dtf.format(LocalDate.now().minusDays(3)));
			else if (LocalDate.now().getDayOfWeek().getValue() == 4)
				lblDate.setText(dtf.format(LocalDate.now().minusDays(4)));
			else if (LocalDate.now().getDayOfWeek().getValue() == 5)
				lblDate.setText(dtf.format(LocalDate.now().minusDays(5)));
			else if (LocalDate.now().getDayOfWeek().getValue() == 6)
				lblDate.setText(dtf.format(LocalDate.now().minusDays(6)));
		}
		lastDateToCheck = fromStringDBToLocalDate(lblDate.getText());

		clmnFirstName.setCellValueFactory(new PropertyValueFactory<RankForTable, String>("firstName"));
		clmnLastName.setCellValueFactory(new PropertyValueFactory<RankForTable, String>("lastName"));
		clmnCustomerID.setCellValueFactory(new PropertyValueFactory<RankForTable, Integer>("customerId"));
		clmnRank.setCellValueFactory(new PropertyValueFactory<RankForTable, Double>("rank"));
		flagIfComeFromRankForm = false;

		ClientUI.chat.accept("showPurchases");
		arrPurchases = (Purchase[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showVehicles");
		arrVehicles = (Vehicle[]) MyFuelClient.msgFromServer;
		ClientUI.chat.accept("showCustomers");
		arrCustomers = (Customer[]) MyFuelClient.msgFromServer;

		for (i = 0; i < arrPurchases.length; i++) {
			if (fromStringDBToLocalDate(arrPurchases[i].getDatePurchase()).isBefore(lastDateToCheck)
					|| fromStringDBToLocalDate(arrPurchases[i].getDatePurchase()).isEqual(lastDateToCheck))
				count++;
		}
		arrPurchasesUntilDate = new Purchase[count];
		for (i = 0; i < arrPurchases.length; i++) {
			if (fromStringDBToLocalDate(arrPurchases[i].getDatePurchase()).isBefore(lastDateToCheck)
					|| fromStringDBToLocalDate(arrPurchases[i].getDatePurchase()).isEqual(lastDateToCheck)) {
				arrPurchasesUntilDate[t] = arrPurchases[i];
				t++;
			}
		}

		index = 0;
		for (i = 0; i < arrCustomers.length; i++) {
			for (j = 0; j < arrPurchasesUntilDate.length; j++) {
				for (k = 0; k < arrVehicles.length; k++) {
					if (arrCustomers[i].getID() == arrPurchasesUntilDate[j].getCustomerId()
							&& arrCustomers[i].getID() == arrVehicles[k].getCustomerId()
							&& arrPurchasesUntilDate[j].getCustomerId() == arrVehicles[k].getCustomerId()
							&& arrPurchasesUntilDate[j].getLicenseNum() == arrVehicles[k].getLicensePlateNumber())
						index++;
				}
			}
		}
		arrRanks = new AnalyticSystemRank[index];
		index = 0;
		for (i = 0; i < arrCustomers.length; i++) {
			for (j = 0; j < arrPurchasesUntilDate.length; j++) {
				for (k = 0; k < arrVehicles.length; k++) {
					if (arrCustomers[i].getID() == arrPurchasesUntilDate[j].getCustomerId()
							&& arrCustomers[i].getID() == arrVehicles[k].getCustomerId()
							&& arrPurchasesUntilDate[j].getCustomerId() == arrVehicles[k].getCustomerId()
							&& arrPurchasesUntilDate[j].getLicenseNum() == arrVehicles[k].getLicensePlateNumber()) {
						arrRanks[index] = new AnalyticSystemRank(arrCustomers[i].getID(), arrCustomers[i].getType(),
								arrPurchasesUntilDate[j].getTimePurchase(), arrVehicles[k].getFuelType(), 0);
						index++;
					}

				}
			}
		}

		String str;
		for (i = 0; i < arrRanks.length; i++) {
			if (arrRanks[i].getCustomerType().equals("private")) {
				if (arrRanks[i].getVehicleFuelType().equals("benzene")) {
					str = timeCalculate(arrRanks[i].getTimePurchase());
					if (str.equals("Morning"))
						arrRanks[i].setRank(1); // private benzene Morning = 1
					else if (str.equals("Afternoon"))
						arrRanks[i].setRank(1.5); // private benzene Afternoon = 1.5
					else
						arrRanks[i].setRank(2); // private benzene Night = 2
				} else if (arrRanks[i].getVehicleFuelType().equals("diesel")) {
					str = timeCalculate(arrRanks[i].getTimePurchase());
					if (str.equals("Morning"))
						arrRanks[i].setRank(2.5); // private diesel Morning = 2.5
					else if (str.equals("Afternoon"))
						arrRanks[i].setRank(3); // private diesel Afternoon = 3
					else
						arrRanks[i].setRank(3.5); // private diesel Night = 3.5
				} else { // scooter's fuel
					str = timeCalculate(arrRanks[i].getTimePurchase());
					if (str.equals("Morning"))
						arrRanks[i].setRank(4); // private scooter's fuel Morning = 4
					else if (str.equals("Afternoon"))
						arrRanks[i].setRank(4.5); // private scooter's fuel Afternoon = 4.5
					else
						arrRanks[i].setRank(5); // private scooter's fuel Night = 5
				}
			} else { // company
				if (arrRanks[i].getVehicleFuelType().equals("benzene")) {
					str = timeCalculate(arrRanks[i].getTimePurchase());
					if (str.equals("Morning"))
						arrRanks[i].setRank(5.5); // company benzene Morning = 5.5
					else if (str.equals("Afternoon"))
						arrRanks[i].setRank(6); // company benzene Afternoon = 6
					else
						arrRanks[i].setRank(6.5); // company benzene Night = 6.5
				} else if (arrRanks[i].getVehicleFuelType().equals("diesel")) {
					str = timeCalculate(arrRanks[i].getTimePurchase());
					if (str.equals("Morning"))
						arrRanks[i].setRank(7); // company diesel Morning = 7
					else if (str.equals("Afternoon"))
						arrRanks[i].setRank(7.5); // company diesel Afternoon = 7.5
					else
						arrRanks[i].setRank(8); // company diesel Night = 8
				} else { // scooter's fuel
					str = timeCalculate(arrRanks[i].getTimePurchase());
					if (str.equals("Morning"))
						arrRanks[i].setRank(8.5); // company scooter's fuel Morning = 8.5
					else if (str.equals("Afternoon"))
						arrRanks[i].setRank(9); // company scooter's fuel Afternoon = 9
					else
						arrRanks[i].setRank(9.5); // company scooter's fuel Night = 9.5
				}
			}
		}
		arrRanksForList = new RankForTable[arrRanks.length];
		for (i = 0; i < arrRanks.length; i++)
			arrRanksForList[i] = new RankForTable(arrRanks[i].getCustomerId(), arrRanks[i].getRank());

		double max = 0;
		for (i = 0; i < arrRanks.length; i++) {
			for (j = 0; j < arrRanks.length; j++) {
				if (arrRanksForList[i].getCustomerId() == arrRanksForList[j].getCustomerId()
						&& arrRanksForList[j].getRank() > max)
					max = arrRanksForList[j].getRank();
			}
			arrRanksForList[i].setRank(max);
			max = 0;
		}

		RankForTable[] arrRanksForListNoDuplicates = removeDuplicates(arrRanksForList);

		ClientUI.chat.accept(arrRanksForListNoDuplicates);

		lblAlg.setText("The customer's rank calculated by customer type, fuel type\nand time of purchase.\n\n"
				+ "The Algorithem to calculate the ranks: \nPrivate, Benzene, Morning = 1\nPrivate, Benzene, Afternoon = 1.5\nPrivate, Benzene, Night = 2\nPrivate, Diesel, Morning = 2.5\nPrivate, Diesel, Afternoon = 3\n"
				+ "Private, Diesel, Night = 3.5\nPrivate, Scooter, Morning = 4\nPrivate, Scooter, Afternoon = 4.5\nPrivate, Scooter, Night = 5\n"
				+ "Company, Benzene, Morning = 5.5\nCompany, Benzene, Afternoon = 6\nCompany, Benzene, Night = 6.5\nCompany, Diesel, Morning = 7\n"
				+ "Company, Diesel, Afternoon = 7.5\nCompany, Diesel, Night = 8\nCompany, Scooter, Morning = 8.5\nCompany, Scooter, Afternoon = 9\n"
				+ "Company, Scooter, Night = 9.5\n\n* If the customer has more than one rank,\nthe highest will be taken.");
		arrRanksForListToShow = new RankForTable[arrRanksForListNoDuplicates.length];
		for (j = 0; j < arrCustomers.length; j++) {
			for (i = 0; i < arrRanksForListNoDuplicates.length; i++) {
				if (arrCustomers[j].getID() == arrRanksForListNoDuplicates[i].getCustomerId()) {
					arrRanksForListToShow[s] = new RankForTable(arrRanksForListNoDuplicates[i].getCustomerId(),
							arrRanksForListNoDuplicates[i].getRank(), arrCustomers[j].getFirstName(),
							arrCustomers[j].getLastName());
					s++;
				}
			}
		}
		for (i = 0; i < arrRanksForListToShow.length; i++) {
			arrListRanks.add(arrRanksForListToShow[i]);
		}
		ranksTable.setItems(arrListRanks);

	}

	/**
	 * This method takes the date from the DB and converts him from Strig value to
	 * LocalDate value
	 * 
	 * @param str The String value of the date from the DB
	 * @return LocalDate value of this date
	 */
	private LocalDate fromStringDBToLocalDate(String str) {
		int j;
		// LocalDate for DatePurchase index
		String[] arrDateForIndex = str.split("[/]"); /// 05/04/2020
		int[] arrNumbersDateForIndex = new int[arrDateForIndex.length];
		for (j = 0; j < arrDateForIndex.length; j++)
			arrNumbersDateForIndex[j] = Integer.valueOf(arrDateForIndex[j]);
		LocalDate localDateForIndex = LocalDate.of(arrNumbersDateForIndex[2], arrNumbersDateForIndex[1],
				arrNumbersDateForIndex[0]);
		return localDateForIndex;
	}

	/**
	 * This method gets array and removes the duplicate values inside him by the
	 * chosen key to remove
	 * 
	 * @param arr The chosen array to remove duplicates from him
	 * @return The array without the duplicates
	 */
	public static RankForTable[] removeDuplicates(RankForTable[] arr) {
		int end = arr.length;
		for (int i = 0; i < end; i++) {
			for (int j = i + 1; j < end; j++) {
				if (arr[i].getCustomerId() == arr[j].getCustomerId()) {
					int shiftLeft = j;
					for (int k = j + 1; k < end; k++, shiftLeft++) {
						arr[shiftLeft] = arr[k];
					}
					end--;
					j--;
				}
			}
		}
		RankForTable[] whitelist = new RankForTable[end];
		for (int i = 0; i < end; i++) {
			whitelist[i] = arr[i];
		}
		return whitelist;
	}

	/**
	 * This method calculates the time by the given purchase time from DB
	 * 
	 * @param timeString The String value of purchase time from the DB
	 * @return String value: "Morning" or "Afternoon" or "Night" by the given
	 *         purchase time
	 */
	// Morning:06:00-12:00 , Afternoon:12:01-19:00 , Night:19:01-05:59
	String timeCalculate(String timeString) {
		int i;
		String[] arr = timeString.split(":");
		int[] arrNumbers = new int[arr.length];
		for (i = 0; i < arr.length; i++)
			arrNumbers[i] = Integer.valueOf(arr[i]);
		LocalTime time = LocalTime.of(arrNumbers[0], arrNumbers[1]);
		LocalTime startMorning = LocalTime.of(6, 0);
		LocalTime endMorning = LocalTime.of(12, 0);
		LocalTime startAfternoon = LocalTime.of(12, 0);
		LocalTime endAfternoon = LocalTime.of(19, 0);
		if (time.isBefore(endMorning) && time.isAfter(startMorning))
			return "Morning";
		if (time.isBefore(endAfternoon) && time.isAfter(startAfternoon))
			return "Afternoon";
		return "Night";
	}

	/**
	 * This method returns the user to previous form(the form when he comes from)
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void backClick(MouseEvent event) { /// return to HomePage form
		changeScreen("/gui/SystemOperationsMarketingManager.fxml");
	}

	/**
	 * This method sends the user to chosen form
	 * 
	 * @param event The event we handle
	 */
	@FXML
	void clickHere(MouseEvent event) {
		flagIfComeFromRankForm = true;
		changeScreen("/gui/SalesForm.fxml");
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