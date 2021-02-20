package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.xml.internal.ws.util.StringUtils;

import client.ClientUI;
import client.MyFuelClient;
import entity.Employee;
import entity.EmployeeForTable;
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
 * The class EmployeeDataControllerClient defined how the employees window would
 * work
 * 
 * @author Eylon
 *
 */
public class EmployeeDataControllerClient implements Initializable {
	Employee[] arrEmployee;
	ObservableList<EmployeeForTable> arrListEmployee = FXCollections.observableArrayList();

	@FXML
	private Pane MyPane;

	@FXML
	private Button btnBack;

	@FXML
	private TableView<EmployeeForTable> tableEmployees;

	@FXML
	private TableColumn<EmployeeForTable, String> clmnFirstName;

	@FXML
	private TableColumn<EmployeeForTable, String> clmnLastName;

	@FXML
	private TableColumn<EmployeeForTable, Integer> clmnID;

	@FXML
	private TableColumn<EmployeeForTable, String> clmnRole;

	@FXML
	private TableColumn<EmployeeForTable, String> clmnEmail;

	@FXML
	private TableColumn<EmployeeForTable, String> clmnGasStation;

	@FXML
	private TableColumn<EmployeeForTable, String> clmnOrganization;

	@FXML
	private Button Minus;
	@FXML
	private Button X;

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
	 * This function initialize the screen when it opens
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		clmnFirstName.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, String>("firstName"));
		clmnLastName.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, String>("lastName"));
		clmnID.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, Integer>("EmployeeID"));
		clmnEmail.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, String>("email"));
		clmnRole.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, String>("role"));
		clmnOrganization
				.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, String>("organizationalAffiliation"));
		clmnGasStation.setCellValueFactory(new PropertyValueFactory<EmployeeForTable, String>("gasStation"));
		tableEmployees.setItems(arrListEmployee);
		initTable();

	}

	/**
	 * The function shows the relevant data in a table view
	 */
	public void initTable() {
		int i;
		arrListEmployee.clear();
		ClientUI.chat.accept("showEmployeesInDB");
		arrEmployee = (Employee[]) MyFuelClient.msgFromServer;
		for (i = 0; i < arrEmployee.length; i++) {
			arrListEmployee.add(new EmployeeForTable(StringUtils.capitalize(arrEmployee[i].getFirstName()),
					StringUtils.capitalize(arrEmployee[i].getLastName()), arrEmployee[i].getEmployeeID(),
					arrEmployee[i].getEmail(), arrEmployee[i].getRole(), arrEmployee[i].getOrganizationalAffiliation(),
					arrEmployee[i].getGasStation()));
		}
		tableEmployees.setItems(arrListEmployee);

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
