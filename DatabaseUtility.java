package application_group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * If this class causes an error, right click on project > Properties > Java Build Path
 * > remove Java System Library > add it back
 */
import com.sun.rowset.CachedRowSetImpl;

/**
 * DBUtil class is responsible for DB connection, DB disconnection, database
 * query and non-query operations (insert, update, and delete).
 * 
 * DBUtil class provides EmployeeDAO class with methods that perform
 * higher-level database operations.
 * 
 * dbConnect() method connects to DB. dbDisconnect() method closes DB
 * connection. dbExecuteQuery(String queryStmt) method executes given SQL
 * statement and returns cachedRowSet set. dbExecuteUpdate(String sqlStmt)
 * method executes given update, insert, or delete SQL operation.
 * 
 * To avoid the "java.sql.SQLRecoverableException: Closed Connection: next"
 * error, the dbExecuteQuery(String queryStmt) returns CachedRowSet instead of
 * ResultSet.
 *
 */

public class DatabaseUtility {

	// Declare JDBC Driver
	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	// Create a Connection
	private static Connection connection = null;

	// Create a connection string
	private static final String CONN_STRING = "jdbc:sqlserver://umd-fmis.d.umn.edu;databaseName=vwpteam60";

	// Set user credentials
	private static String uid = "vwp";
	private static String pwd = "vwpumd";

	// === Establish a connection to the database
	public static Connection dbConnect() throws SQLException, ClassNotFoundException {
		// Set SQL Server JDBC Driver
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// ======= testing =======
//          System.out.println("Where is your SQL Server JDBC Driver?");
			e.printStackTrace();
			throw e;
		}

		// ======= testing =======
//      System.out.println("SQL Server JDBC Driver Registered!");

		// Establish the Sql Server connection using the connection string
		try {
			connection = DriverManager.getConnection(CONN_STRING, uid, pwd);
		} catch (SQLException e) {
			// ======= testing =======
//          System.out.println("Connection Failed! Check output console" + e);
			e.printStackTrace();
			throw e;
		}
		return connection;
	}

	// === Close a connection to the database
	public static void dbDisconnect() throws SQLException {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// === Query (select) operation
	public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
		// The Statement class is used for executing a static SQL statement
		// and returning the results it produces.
		Statement statement = null;

		// The ResultSet represents a table of data from a query.
		// A ResultSet allows read-only and forward-only access to its data.
		// Used for short and simple operations that maintain connection
		// until the tasks are done. This is known as a data reader.
		ResultSet resultSet = null;

		// The CachedRowSetImpl is the standard implementation of the
		// CachedRowSet interface. A CachedRowSetImpl is a container
		// for rows of data that caches its rows in memory, which makes
		// it possible to operate without always being connected to
		// its data source. This is known as a dataset.
		// A dataset is scroll-able and update-able.
		// Once data is cached, disconnected from the source of its data.
		CachedRowSetImpl crs = null;

		try {
			// Connect to the database
			dbConnect();
			// ======= testing =======
//          System.out.println("Select statement: " + queryStmt + "\n");

			// Connection.createStatement() creates a Statement object
			// for sending SQL statements to the database.
			statement = connection.createStatement();

			// Execute select (query) operation
			resultSet = statement.executeQuery(queryStmt);

			// CachedRowSetImpl is a CachedRowSet interface implementation.
			// To avoid "java.sql.SQLRecoverableException: Closed Connection: next"
			// error, CachedRowSetImpl is used.
			crs = new CachedRowSetImpl();

			// Populates this CachedRowSetImpl object with data from
			// the given ResultSet object.
			crs.populate(resultSet);
		} catch (SQLException e) {
			// ======= testing =======
//          System.out.println("Error in executeQuery operation : " + e);
			throw e;
		} finally {
			if (resultSet != null) {
				// Close resultSet
				resultSet.close();
			}

			if (statement != null) {
				// Close Statement
				statement.close();
			}

			// Close connection
			dbDisconnect();
		}
		// Return CachedRowSet
		return crs;
	}

	// === Non-query (update, insert, delete) operations
	public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {

		Statement statement = null;

		try {
			// Connect to DB (Establish SQl Server Connection)
			dbConnect();

			// Create Statement
			statement = connection.createStatement();

			// Run executeUpdate operation with given sql statement
			statement.executeUpdate(sqlStmt);
		} catch (SQLException e) {
			// ======= testing =======
//          System.out.println("Problem occurred at executeUpdate operation : " + e);
			throw e;
		} finally {
			if (statement != null) {
				// Close statement
				statement.close();
			}

			// Close connection
			dbDisconnect();
		}
	}
}
