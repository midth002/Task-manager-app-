package application_group;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class userDataAccess {

	// INSERT a user
	public static void insertUser(String username, String firstname, String lastname, String email, String password)
			throws SQLException, ClassNotFoundException {

		// insert statement
		String updateStmt = "INSERT INTO Users " + "(userID, first_name, last_name, email, password) " + "VALUES "
				+ "('" + username + "', '" + firstname + "', '" + lastname + "','" + email + "', '" + password + "')";

		// insert operation
		try {
			DatabaseUtility.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			throw e;
		}

	}

	public static void insertTask(String username, String taskname, String details, String date, String category)
			throws SQLException, ClassNotFoundException {
		String insertTaskStmt = "INSERT INTO Task " + "(userID, name, description, dueDate, category_name) " + "VALUES "
				+ "('" + username + "', '" + taskname + "', '" + details + "', '" + date + "', '" + category + "')";

		// insert operation
		try {
			DatabaseUtility.dbExecuteUpdate(insertTaskStmt);
		} catch (SQLException e) {
			throw e;
		}

	}

	public static void insertTaskwithDateAndTime(String username, String taskname, String details, String subTask,
			String date_time) throws SQLException, ClassNotFoundException {
		String insertTaskStmt = "INSERT INTO Task " + "(userID, name, description, subTaskName, dueDate) " + "VALUES "
				+ "('" + username + "', '" + taskname + "', '" + details + "', '" + subTask + "', '" + date_time + "')";

		// insert operation
		try {
			DatabaseUtility.dbExecuteUpdate(insertTaskStmt);
		} catch (SQLException e) {
			throw e;
		}

	}

	public static void insertCompletedTask(String taskName, String userName)
			throws SQLException, ClassNotFoundException {
		String insertCompletedTaskStmt = "INSERT INTO Completed " + "(task_name, user_ID) " + "VALUES " + "('"
				+ taskName + "', '" + userName + "')";

		try {
			DatabaseUtility.dbExecuteUpdate(insertCompletedTaskStmt);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void insertSubTask(String subname, String tname, String username)
			throws SQLException, ClassNotFoundException {

		String insertSubTask = "INSERT INTO subTask " + "(name, taskName, user_name) " + "VALUES " + "('" + subname
				+ "', '" + tname + "', '" + username + "')";

		try {
			DatabaseUtility.dbExecuteUpdate(insertSubTask);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void insertTaskCategory(String category, String userName)
			throws SQLException, ClassNotFoundException {
		String insertTaskStmt = "INSERT INTO TaskCategory " + "(category_name, userID) " + "VALUES " + "('" + category
				+ "','" + userName + "')";

		// insert operation
		try {
			DatabaseUtility.dbExecuteUpdate(insertTaskStmt);
		} catch (SQLException e) {
			throw e;
		}

	}

	public static void insertDefaultCategory(String userName) throws SQLException, ClassNotFoundException {
		String insertdefaultStmt = "INSERT INTO TaskCategory " + "(category_name, userID) " + "VALUES " + "(' Default "
				+ "','" + userName + "')";

		// insert operation
		try {
			DatabaseUtility.dbExecuteUpdate(insertdefaultStmt);
		} catch (SQLException e) {
			throw e;
		}
	}

	private static userData getUserTaskFromResultSet(ResultSet rs) throws SQLException {

		userData ud = null;

		if (rs.next()) {
			ud = new userData();
			ud.setTaskName(rs.getString("Task_Name"));
		}
		return ud;
	}

	private static ObservableList<userData> getTaskList(ResultSet rs) throws SQLException, ClassNotFoundException {

		ObservableList<userData> taskList = FXCollections.observableArrayList();
		while (rs.next()) {
			userData ud = new userData();
			ud.setTaskName(rs.getString("Task_Name"));
		}
		return taskList;
	}

	public static void checkSignInForm(String userName, String password) throws SQLException, ClassNotFoundException {

		String signIn = "SELECT * FROM Users WHERE userID = " + "('" + userName + "')" + " AND password = " + "('"
				+ password + "')";

		try {

			DatabaseUtility.dbExecuteQuery(signIn);

		} catch (SQLException e) {
			throw e;
		}
	}

	public static void showTaskDetails(String task, String user) throws SQLException, ClassNotFoundException {
		String showDetails = "SELECT description FROM Task WHERE userID = " + "('" + user + "')" + " AND name = " + "('"
				+ task + "')";
		try {
			DatabaseUtility.dbExecuteQuery(showDetails);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void deleteTask(String taskname, String userName) throws SQLException, ClassNotFoundException {

		String DeleteTask = "DELETE FROM task WHERE name = " + "('" + taskname + "')" + "AND userID = " + "('"
				+ userName + "')";

		try {
			DatabaseUtility.dbExecuteUpdate(DeleteTask);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void deleteSubTask(String subname, String taskname, String userName)
			throws SQLException, ClassNotFoundException {

		String DeleteTask = "DELETE FROM subtask WHERE name = " + "('" + subname + "')" + "AND taskName = " + "('"
				+ taskname + "')" + "AND user_name = " + "('" + userName + "')";

		try {
			DatabaseUtility.dbExecuteUpdate(DeleteTask);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void showByCategory(String cat, String userName) throws SQLException, ClassNotFoundException {

		String filter = "Select FROM task WHERE category_name = " + "('" + cat + "')" + " AND userID = " + "'('"
				+ userName + "')";

		try {
			DatabaseUtility.dbExecuteQuery(filter);
		} catch (SQLException e) {
			throw e;
		}
	}

}
