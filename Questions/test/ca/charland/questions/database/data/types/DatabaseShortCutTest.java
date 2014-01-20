package ca.charland.questions.database.data.types;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.Test;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.types.ShortCut;
import ca.charland.questions.database.data.DatabaseLocation;
import ca.charland.questions.database.data.DatabaseStatistics;
import ca.charland.questions.utilities.Database;

/**
 * Tests getting and setting short cut keys.
 * 
 * @author Michael
 */
public class DatabaseShortCutTest {

	/**
	 * Tests setting the short cut data into the database.
	 */
	@Test
	public final void testSet() {
		final ShortCut original = createShortCut(-1, "DatabaseShortCutKeyTest.testSet");

		final DatabaseShortCut databaseShortCut = new DatabaseShortCut(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseShortCut.set(original);

		final ShortCut update = createShortCut(questionNumber, "DatabaseShortCutKeyTest.testSet");
		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final ResultSet rs = connect.select(DatabaseShortCut.TABLE_NAME, vals);
		String question = "";
		String answer = "";
		try {
			question = rs.getString(DatabaseShortCut.Column.QUESTION.toString());
			answer = rs.getString(DatabaseShortCut.Column.SHORT_CUT.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		aq.get(questionNumber);

		final DatabaseStatistics ds = new DatabaseStatistics(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Statistics afterStats = ds.get(questionNumber);

		final DatabaseLocation dl = new DatabaseLocation(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Location afterLocation = dl.get(questionNumber);

		final ShortCut after = new ShortCut(aq.getShow(), questionNumber, afterStats, question, answer, afterLocation);
		assertEquals(update, after);
		connect.disconnect();
		deleteShortCut(questionNumber);
	}

	/**
	 * Tests getting the short cut data from the database.
	 */
	@Test
	public final void testGet() {
		final ShortCut original = insertShortCut("DatabaseShortCutKeyTest.testGet");

		final DatabaseShortCut databaseShortCut = new DatabaseShortCut(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final ShortCut after = databaseShortCut.get(original.getQuestionNumber());

		assertEquals(original, after);

		deleteShortCut(original.getQuestionNumber());
	}

	/**
	 * Compares two short cut questions to each other.
	 * 
	 * @param expected
	 *            The expected short cut answer.
	 * @param actual
	 *            The actual short cut answer.
	 */
	public static void assertEquals(final ShortCut expected, final ShortCut actual) {
		DatabaseAbstractQuestionTest.assertEquals(expected, actual);
		Assert.assertEquals(expected.getQuestionString(), actual.getQuestionString());
		Assert.assertEquals(expected.getShortCut(), actual.getShortCut());
	}

	/**
	 * Helper method to create a short cut question.
	 * 
	 * @param questionNumber
	 *            The question number for the question.
	 * @param unique
	 *            A unique identifier to make it easier to debug.
	 * @return A newly created question.
	 */
	public static ShortCut createShortCut(final int questionNumber, final String unique) {
		final boolean showQuestion = false;
		final Statistics statistics = new Statistics(10, 20, 30);
		final Location locationOfAnswer = new Location("test short cut stuff", "b", unique, "d", "e", unique);
		final String quetion = unique;
		final String shortcut = "shift + ctrl + b";

		return new ShortCut(showQuestion, questionNumber, statistics, quetion, shortcut, locationOfAnswer);
	}

	/**
	 * Helper method to create and insert a short cut question.
	 * 
	 * @param unique
	 *            A unique identifier to make it easier to debug.
	 * @return The short cut answer that was created upon insertion.
	 */
	public static final ShortCut insertShortCut(final String unique) {

		final ShortCut original = createShortCut(-1, unique);

		final DatabaseShortCut databaseShortCut = new DatabaseShortCut(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseShortCut.set(original);

		return createShortCut(questionNumber, unique);
	}

	/**
	 * Helper method to delete a short cut question.
	 * 
	 * @param questionNumber
	 *            The question number to delete.
	 */
	public static void deleteShortCut(final int questionNumber) {
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);
		connect.delete(DatabaseLocation.TABLE_NAME, vals);
		connect.delete(DatabaseStatistics.TABLE_NAME, vals);
		connect.delete(DatabaseShortCut.TABLE_NAME, vals);
		connect.disconnect();
	}

}
