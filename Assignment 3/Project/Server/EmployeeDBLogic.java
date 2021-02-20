package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import entity.Employee;

/**
 * This class defined the employee logic
 * 
 * @author Shoval David
 *
 */
public class EmployeeDBLogic {
	
	/**
	 * This method gets the number of employees inside the DB
	 * 
	 * @return number of employees
	 */
	public static int numOfRows() {
		Statement stmt;
		ResultSet res;
		int count = 0;
		try {
			stmt = EchoServer.con.createStatement();
			res = stmt.executeQuery("SELECT * FROM employee;");
			while (res.next()) {
				count++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	/**
	 * This method gets all the employees from the DB and puts it inside an array
	 * 
	 * @return array of employees
	 */
	public static Employee[] showEmployees() {
		Employee[] arrEmployee = new Employee[numOfRows()];
		Statement stmt;
		int i = 0;
		try {
			stmt = EchoServer.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM employee;");
			while (rs.next()) {
				arrEmployee[i] = (new Employee(rs));
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrEmployee;
	}

}
