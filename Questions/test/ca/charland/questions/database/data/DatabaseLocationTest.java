package ca.charland.questions.database.data;

import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.Test;

import ca.charland.questions.data.Location;
import ca.charland.questions.database.data.DatabaseLocation.Column;
import ca.charland.questions.database.data.types.DatabaseAbstractQuestion;
import ca.charland.questions.database.data.types.DatabaseAbstractQuestionTest;
import ca.charland.questions.utilities.Database;

/**
 * Test getting the location from the database.
 * 
 * @author Michael
 */
public class DatabaseLocationTest {

	/**
	 * Tests setting the location data into the database.
	 */
	@Test
	public final void testSet() {
		final Location original = new Location("testSet", "b", "c", "d", "e", "f");

		final int questionNumber = 250;

		final DatabaseLocation databaseLocation = new DatabaseLocation(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		databaseLocation.set(questionNumber, original);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final ResultSet rs = connect.select(DatabaseLocation.TABLE_NAME, vals);

		String category = "";
		String subCategory = "";
		String document = "";
		String chapter = "";
		String section = "";
		String page = "";
		try {
			category = rs.getString(Column.CATEGORY.toString());
			subCategory = rs.getString(Column.SUB_CATEGORY.toString());
			document = rs.getString(Column.DOCUMENT.toString());
			chapter = rs.getString(Column.CHAPTER.toString());
			section = rs.getString(Column.SECTION.toString());
			page = rs.getString(Column.PAGE.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		// remove escape characters
		category = category.replaceAll("\\\\\'", "\\'");

		final Location after = new Location(category, subCategory, document, chapter, section, page);

		assertEquals(original, after);
		connect.delete(DatabaseLocation.TABLE_NAME, vals);
	}

	/**
	 * Tests getting the location information from the database.
	 */
	@Test
	public final void testGet() {
		final Location actual = new Location("testGet", "b", "c", "d", "e", "f");

		final int questionNumber = 255;

		final DatabaseLocation databaseLocation = new DatabaseLocation(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		databaseLocation.set(questionNumber, actual);

		final Location after = databaseLocation.get(255);
		assertEquals(actual, after);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		connect.delete(DatabaseLocation.TABLE_NAME, vals);
	}

	/**
	 * Compares two Locations to each other.
	 * 
	 * @param expected
	 *            The expected location.
	 * @param actual
	 *            The actual location.
	 */
	public static void assertEquals(final Location expected, final Location actual) {
		Assert.assertEquals(expected.getCategory(), actual.getCategory());
		Assert.assertEquals(expected.getChapter(), actual.getChapter());
		Assert.assertEquals(expected.getDocumentName(), actual.getDocumentName());
		Assert.assertEquals(expected.getPage(), actual.getPage());
		Assert.assertEquals(expected.getSection(), actual.getSection());
	}

}
