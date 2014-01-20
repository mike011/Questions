package ca.charland.questions.database;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import org.junit.Test;

import ca.charland.questions.database.data.types.DatabaseAbstractQuestion;
import ca.charland.questions.utilities.Database;
import ca.charland.test.util.Reflection;

import com.mysql.jdbc.Connection;

/**
 * Tests various methods in the database.
 * @author Michael
 *
 */
public final class DatabaseTest {

	/**
	 * Test connecting to the database.
	 */
	@Test
	public void testConnect() {

		final Database connect = new Database("questions_test");
		final Connection connection = (Connection) Reflection.getObject(connect, "_connection");

		assertFalse("You are not connected", connection.isClosed());

		try {
			connection.close();
		} catch (SQLException e) {
			fail("Connection closing failed");
			e.printStackTrace();
		}

		assertTrue("You are connected", connection.isClosed());

	}

	/**
	 * Test disconnectng from the database.
	 */
	@Test
	public void testDisconnect() {
		final Database connect = new Database("questions_test");
		final Connection connectionBefore = (Connection) Reflection.getObject(connect, "_connection");

		assertFalse("You are not connected", connectionBefore.isClosed());

		connect.disconnect();

		final Connection connectionAfter = (Connection) Reflection.getObject(connect, "_connection");
		assertTrue("You are connected", connectionAfter.isClosed());
	}

	/**
	 * Test executing an insert.
	 */
	@Test
	public void executeTest() {
		final Database connect = new Database("questions_test");

		final String type = "ABCDEFGH";

		String insert = "INSERT INTO " + DatabaseAbstractQuestion.TABLE_NAME + " (" + DatabaseAbstractQuestion.Column.TYPE + ") " + " VALUES ('" + type + "')";

		assertNotNull("Insert failed", connect.execute(insert));

		final String delete = "DELETE FROM " + DatabaseAbstractQuestion.TABLE_NAME + " WHERE `" + DatabaseAbstractQuestion.Column.TYPE + "` = '" + type + "'";

		assertNull("Delete failed", connect.execute(delete));

		connect.disconnect();
	}

	/**
	 * Test executing a query test.
	 */
	@Test
	public void executeQueryTest() {
		final Database connect = new Database("questions_test");

		final String type = "executeQueryTest";

		final String insert = "INSERT INTO " + DatabaseAbstractQuestion.TABLE_NAME + " (" + DatabaseAbstractQuestion.Column.TYPE + ") " + " VALUES ('" + type + "')";
		assertNotNull("Insert failed", connect.execute(insert));

		final String query = "SELECT * FROM " + DatabaseAbstractQuestion.TABLE_NAME + " WHERE `" + DatabaseAbstractQuestion.Column.TYPE + "`='" + type + "'";
		ResultSet rs = connect.executeQuery(query);

		try {
			assertEquals(type, rs.getString(DatabaseAbstractQuestion.Column.TYPE.toString()));
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL Exception thrown.");
		}

		final String delete = "DELETE FROM " + DatabaseAbstractQuestion.TABLE_NAME + " WHERE `" + DatabaseAbstractQuestion.Column.TYPE + "` = '" + type + "'";
		assertNull("Delete failed", connect.execute(delete));

		connect.disconnect();
	}

	/**
	 * Test insert into the database.
	 */
	@Test
	public void insertTest() {

		final String type = "executeQueryTest";

		final Database connect = new Database("questions_test");

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.TYPE, type);
		vals.put(DatabaseAbstractQuestion.Column.SHOW, "TRUE");

		connect.insert(DatabaseAbstractQuestion.TABLE_NAME, vals);

		assertNotNull(connect.select(DatabaseAbstractQuestion.TABLE_NAME, vals));

		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);

		connect.disconnect();
	}

	/**
	 * Test selecting data from the database.
	 */
	@Test
	public void selectTest() {

		final String expectedType = "selectTest";

		final Database connect = new Database("questions_test");

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.TYPE, expectedType);
		vals.put(DatabaseAbstractQuestion.Column.SHOW, "TRUE");

		connect.insert(DatabaseAbstractQuestion.TABLE_NAME, vals);

		final ResultSet rs = connect.select(DatabaseAbstractQuestion.TABLE_NAME, vals);

		String type = "";
		try {
			type = rs.getString(DatabaseAbstractQuestion.Column.TYPE.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(expectedType, type);

		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);

		connect.disconnect();
	}

	/**
	 * Test removing from the database.
	 */
	@Test
	public void removeTest() {
		final String expectedType = "removeTest";

		final Database connect = new Database("questions_test");

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.TYPE, expectedType);
		vals.put(DatabaseAbstractQuestion.Column.SHOW, "TRUE");

		connect.insert(DatabaseAbstractQuestion.TABLE_NAME, vals);

		final ResultSet rs = connect.select(DatabaseAbstractQuestion.TABLE_NAME, vals);

		String type = "";
		try {
			type = rs.getString(DatabaseAbstractQuestion.Column.TYPE.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(expectedType, type);

		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);

		assertNull(connect.select(DatabaseAbstractQuestion.TABLE_NAME, vals));

		connect.disconnect();
	}

	/**
	 * Test a simple query.
	 */
	@Test
	public void queryTest() {
		final Database connect = new Database("questions_test");

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.TYPE, "queryTest");
		vals.put(DatabaseAbstractQuestion.Column.SHOW, "TRUE");

		int questionNumber = -1;
		final ResultSet rs = connect.insert(DatabaseAbstractQuestion.TABLE_NAME, vals);
		try {
			final int questionNumberColumn = 1;
			questionNumber = rs.getInt(questionNumberColumn);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}

		assertTrue(questionNumber > 0);

		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);

		assertNull(connect.select(DatabaseAbstractQuestion.TABLE_NAME, vals));

		connect.disconnect();

	}
}
