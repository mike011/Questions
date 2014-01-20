package ca.charland.questions.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Holds the connection to the database. Runs basic commands to the database.
 * 
 * @author Michael
 */
public final class Database {

	/**
	 * The connection to the database.
	 */
	private final Connection _connection;

	/**
	 * Connect to a database.
	 * 
	 * @param databaseName
	 *            The name of database to connect too.
	 */
	public Database(final String databaseName) {
		Connection connection = null;
		try {
			final String userName = "root";
			final String password = "";
			final String url = "jdbc:mysql://localhost/" + databaseName;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			System.err.println("Cannot connect to database server.");
			e.printStackTrace();
			disconnect();
		}
		_connection = connection;

		try {
			assert !_connection.isClosed();
		} catch (SQLException e) {
			System.err.println("Not connected to database server.");
			e.printStackTrace();
		}
	}

	/**
	 * Disconnects from a database.
	 */
	public void disconnect() {
		if (_connection != null) {
			try {
				while (!_connection.isClosed()) {
					_connection.close();
				}
			} catch (Exception e) { /* ignore close errors */
				e.printStackTrace();
			}
		}
	}

	/**
	 * Executes an SQL statement.
	 * 
	 * @param sql
	 *            the string of the statement to execute.
	 * @return Any auto generated keys.
	 */
	public ResultSet execute(final String sql) {

		try {
			assert !_connection.isClosed() : "The connection is not open.";
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}

		Statement statement = null;
		try {
			statement = _connection.createStatement();
		} catch (SQLException e) {
			System.err.println("Statement Failed");
			e.printStackTrace();
			return null;
		}

		try {
			statement.execute(sql);
		} catch (SQLException e) {
			System.err.println("RS Failed");
			e.printStackTrace();
			return null;
		}

		try {
			final ResultSet rs = statement.getGeneratedKeys();
			rs.first();

			final int nowDataReturned = 0;
			if (rs.getRow() == nowDataReturned) {
				return null;
			}
			return rs;
		} catch (SQLException e) {
			System.err.println("RS Failed");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Insert data into the database.
	 * 
	 * @param tableName
	 *            The name of the table too look into.
	 * @param vals
	 *            The values to look for.
	 * @return Any auto generated keys
	 */
	public ResultSet insert(final String tableName, final Hashtable<Enum<?>, Object> vals) {

		String keys = "";
		for (Enum<?> key : vals.keySet()) {
			keys += "`" + key.toString() + "`, ";

		}
		keys = keys.substring(0, keys.lastIndexOf(","));

		String values = "";
		for (Object val : vals.values()) {
			values += "'" + (String) val + "', ";

		}
		values = values.substring(0, values.lastIndexOf(","));

		// Insert the data into the database.
		final String insert = "INSERT INTO `" + tableName + "` (" + keys.trim() + ") VALUES (" + values.trim() + ");";
		return execute(insert);
	}

	/**
	 * Remove data from the database.
	 * 
	 * @param tableName
	 *            The name of the table too look into.
	 * @param vals
	 *            The values to look for.
	 */
	public void delete(final String tableName, final Hashtable<Enum<?>, Object> vals) {

		String compare = "";
		final Enumeration<?> keys = vals.keys();
		final Object[] values = vals.values().toArray();
		for (int x = 0; x < values.length; x++) {
			compare += "`" + keys.nextElement() + "`='" + values[x] + "' AND ";
		}
		compare = compare.substring(0, compare.lastIndexOf("AND "));

		// Remove the data into the database.
		final String remove = "DELETE FROM `" + tableName + "` WHERE " + compare + ";";
		execute(remove);

	}

	/**
	 * Select data from the database.
	 * 
	 * @param tableName
	 *            The name of the table too look into.
	 * @param vals
	 *            The values to look for.
	 * @return The data from database.
	 */
	public ResultSet select(final String tableName, final Hashtable<Enum<?>, Object> vals) {

		String select = "SELECT * FROM `" + tableName + "`";

		if (vals != null) {
			String compare = "";
			final Enumeration<?> keys = vals.keys();
			final Object[] values = vals.values().toArray();
			for (int x = 0; x < values.length; x++) {
				compare += "`" + keys.nextElement() + "`='" + values[x] + "' AND ";
			}
			compare = compare.substring(0, compare.lastIndexOf("AND "));

			select += " WHERE " + compare;
		}

		select += ";";

		return executeQuery(select);
	}

	/**
	 * Updates data from the database.
	 * 
	 * @param tableName
	 *            The name of the table too look into.
	 * @param updateVals
	 *            The values to look for.
	 * @param compareVals
	 *            What to do the comparison with.
	 * @return The data from database.
	 */
	public ResultSet update(final String tableName, final Hashtable<Enum<?>, Object> updateVals, final Hashtable<Enum<?>, Object> compareVals) {

		String update = "UPDATE " + tableName + " SET ";

		String setToUPdate = "";
		final Enumeration<?> keys = updateVals.keys();
		final Object[] values = updateVals.values().toArray();
		for (int x = 0; x < values.length; x++) {
			setToUPdate += "`" + keys.nextElement() + "`='" + values[x] + "' , ";
		}
		setToUPdate = setToUPdate.substring(0, setToUPdate.lastIndexOf(" , "));

		update += setToUPdate;

		String compare = "";
		final Enumeration<?> compareKeys = compareVals.keys();
		final Object[] compareValues = compareVals.values().toArray();
		for (int x = 0; x < compareValues.length; x++) {
			compare += "`" + compareKeys.nextElement() + "`='" + compareValues[x] + "' AND ";
		}
		compare = compare.substring(0, compare.lastIndexOf("AND "));

		// Get the question number.
		update += " WHERE " + compare + ";";

		return execute(update);
	}

	/**
	 * Executes a query that gets data back.
	 * 
	 * @param sql
	 *            The query to search for.
	 * @return The result of the query.
	 */
	public ResultSet executeQuery(final String sql) {
		try {
			assert !_connection.isClosed() : "The connection is not open.";
		} catch (SQLException e1) {
			System.err.println("The connection is not open.");
			e1.printStackTrace();
			return null;
		}

		Statement statement = null;
		try {
			statement = _connection.createStatement();
		} catch (SQLException e) {
			System.err.println("Statement <" + statement + "> Failed.");
			e.printStackTrace();
			return null;
		}

		try {
			final ResultSet rs = statement.executeQuery(sql);

			rs.last();
			final int noDataReturned = 0;
			if (rs.getRow() == noDataReturned) {
				return null;
			}
			rs.first();
			return rs;
		} catch (SQLException e) {
			System.err.println("RS Failed.");
			e.printStackTrace();
			return null;
		}
	}
}
