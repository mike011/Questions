package ca.charland.questions.database.data.types;

import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.Test;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;
import ca.charland.questions.database.data.DatabaseLocation;
import ca.charland.questions.database.data.DatabaseLocationTest;
import ca.charland.questions.database.data.DatabaseStatistics;
import ca.charland.questions.database.data.types.DatabaseAbstractQuestion.Column;
import ca.charland.questions.data.types.AbstractQuestion;
import ca.charland.questions.data.types.CheckBox;
import ca.charland.questions.utilities.Database;

/**
 * Tests getting the abstract question data to and from a database. 
 * @author Michael
 *
 */
public class DatabaseAbstractQuestionTest {

	/**
	 * The name of the test database. 
	 */
	public static final String TEST_DATABASE_NAME = "questions_test";

	/**
	 * Tests setting the abstract question data into a database.
	 */
	@Test
	public final void testSet() {

		final boolean originalShowQuestion = false;
		final int originalQuestionNumber = -1;
		final Statistics originalQestionStatistics = new Statistics();
		final Location originalLocationOfAnswer = new Location("testSet", "b", "c", "d", "e", "f");
		final String originalQuestion = "TEST Q";
		final ArrayList<String> originalAnswers = new ArrayList<String>();
		originalAnswers.add("A1");
		final ArrayList<String> originalOptions = new ArrayList<String>();
		originalOptions.add("O1");

		final AbstractQuestion original = new CheckBox(originalShowQuestion, originalQuestionNumber, originalQestionStatistics, originalQuestion,
				originalAnswers, originalLocationOfAnswer, originalOptions);

		final DatabaseAbstractQuestion databaseAbstractQuestion = new DatabaseAbstractQuestion(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseAbstractQuestion.set(original);

		final AbstractQuestion update = new CheckBox(originalShowQuestion, questionNumber, originalQestionStatistics, originalQuestion,
				originalAnswers, originalLocationOfAnswer, originalOptions);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(Column.QUESTION_NUMBER, "" + questionNumber);

		final ResultSet rs = connect.select(DatabaseAbstractQuestion.TABLE_NAME, vals);

		int afterQuestionNumber = -1;
		boolean afterShowQuestion = false;
		Type afterType = null;
		try {
			afterQuestionNumber = rs.getInt(Column.QUESTION_NUMBER.toString());
			afterShowQuestion = rs.getBoolean(Column.SHOW.toString());
			afterType = Type.valueOf(rs.getString(Column.TYPE.toString()));
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		Assert.assertEquals(Type.CheckBox, afterType);

		final DatabaseStatistics ds = new DatabaseStatistics(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Statistics afterStats = ds.get(questionNumber);

		final DatabaseLocation dl = new DatabaseLocation(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Location afterLocation = dl.get(questionNumber);

		final AbstractQuestion after = new CheckBox(afterShowQuestion, afterQuestionNumber, afterStats, originalQuestion, originalAnswers,
				afterLocation, originalOptions);
		assertEquals(update, after);
		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);
		connect.delete(DatabaseLocation.TABLE_NAME, vals);
		connect.delete(DatabaseStatistics.TABLE_NAME, vals);
	}

	/**
	 * Test getting the abstract question information from the database.
	 */
	@Test
	public final void testGet() {
		final boolean originalShowQuestion = false;
		final int originalQuestionNumber = -1;
		final Statistics originalQestionStatistics = new Statistics();
		final Location originalLocationOfAnswer = new Location("testSet", "b", "c", "d", "e", "f");
		final String originalQuestion = "TEST Q";
		final ArrayList<String> originalAnswers = new ArrayList<String>();
		originalAnswers.add("A1");
		final ArrayList<String> originalOptions = new ArrayList<String>();
		originalOptions.add("O1");

		final AbstractQuestion original = new CheckBox(originalShowQuestion, originalQuestionNumber, originalQestionStatistics, originalQuestion,
				originalAnswers, originalLocationOfAnswer, originalOptions);

		final DatabaseAbstractQuestion databaseAbstractQuestion = new DatabaseAbstractQuestion(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseAbstractQuestion.set(original);

		databaseAbstractQuestion.get(questionNumber);

		final AbstractQuestion update = new CheckBox(originalShowQuestion, questionNumber, originalQestionStatistics, originalQuestion,
				originalAnswers, originalLocationOfAnswer, originalOptions);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(Column.QUESTION_NUMBER, "" + questionNumber);

		final ResultSet rs = connect.select(DatabaseAbstractQuestion.TABLE_NAME, vals);

		int afterQuestionNumber = -1;
		boolean afterShowQuestion = false;
		Type afterType = null;
		try {
			afterQuestionNumber = rs.getInt(Column.QUESTION_NUMBER.toString());
			afterShowQuestion = databaseAbstractQuestion.getShow();
			afterType = databaseAbstractQuestion.getType();
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		Assert.assertEquals(Type.CheckBox, afterType);

		final DatabaseStatistics ds = new DatabaseStatistics(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Statistics afterStats = ds.get(questionNumber);

		final DatabaseLocation dl = new DatabaseLocation(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Location location = dl.get(questionNumber);

		final AbstractQuestion after = new CheckBox(afterShowQuestion, afterQuestionNumber, afterStats, originalQuestion, originalAnswers, location,
				originalOptions);
		assertEquals(update, after);
		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);
		connect.delete(DatabaseLocation.TABLE_NAME, vals);
		connect.delete(DatabaseStatistics.TABLE_NAME, vals);
	}

	/**
	 * Compares two Locations to each other.
	 * 
	 * @param expected
	 *            The expected location.
	 * @param actual
	 *            The actual location.
	 */
	public static void assertEquals(final AbstractQuestion expected, final AbstractQuestion actual) {

		Assert.assertEquals(expected.getShowQuestion(), actual.getShowQuestion());
		Assert.assertEquals(expected.getCorrectlyAnswered(), actual.getCorrectlyAnswered());
		Assert.assertEquals(expected.getDifficulty(), actual.getDifficulty());

		DatabaseLocationTest.assertEquals(expected.getLocation(), actual.getLocation());

		Assert.assertEquals(expected.getPercentCorrect(), actual.getPercentCorrect());
		Assert.assertEquals(expected.getQuestionNumber(), actual.getQuestionNumber());
		Assert.assertEquals(expected.getQuestionString(), actual.getQuestionString());
		Assert.assertEquals(expected.getTotalTimesAnswered(), actual.getTotalTimesAnswered());
		Assert.assertEquals(expected.getType(), actual.getType());

	}

}
