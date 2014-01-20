package ca.charland.questions.database.data.types;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.Test;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.types.BlankAnswer;
import ca.charland.questions.database.data.DatabaseLocation;
import ca.charland.questions.database.data.DatabaseStatistics;
import ca.charland.questions.utilities.Database;

/**
 * Tests getting and setting blank answer data.
 * 
 * @author Michael
 */
public class DatabaseBlankAnswerTest {

	/**
	 * Tests setting blank answer data into the database.
	 */
	@Test
	public final void testSet() {
		final BlankAnswer original = createBlankAnswer(-1, "DatabaseBlankAnswerTest.testSet");

		final DatabaseBlankAnswer databaseBlankAnswer = new DatabaseBlankAnswer(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseBlankAnswer.set(original);

		final BlankAnswer update = createBlankAnswer(questionNumber, "DatabaseBlankAnswerTest.testSet");
		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final ResultSet rs = connect.select(DatabaseBlankAnswer.TABLE_NAME, vals);
		String question = "";
		String answer = "";
		try {
			question = rs.getString(DatabaseBlankAnswer.Column.QUESTION.toString());
			answer = rs.getString(DatabaseBlankAnswer.Column.ANSWER.toString());
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

		final BlankAnswer after = new BlankAnswer(aq.getShow(), questionNumber, afterStats, question, answer, afterLocation);
		assertEquals(update, after);
		connect.disconnect();
		deleteBlankAnswer(questionNumber);
	}

	/**
	 * Tests getting blank answer data from the database.
	 */
	@Test
	public final void testGet() {
		final BlankAnswer original = insertBlankAnswer("DatabaseBlankAnswerTest.testGet");

		final DatabaseBlankAnswer databaseBlankAnswer = new DatabaseBlankAnswer(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final BlankAnswer after = databaseBlankAnswer.get(original.getQuestionNumber());

		assertEquals(original, after);

		deleteBlankAnswer(original.getQuestionNumber());
	}

	/**
	 * Compares two blank answer questions to each other.
	 * 
	 * @param expected
	 *            The expected blank answer.
	 * @param actual
	 *            The actual blank answer.
	 */
	public static void assertEquals(final BlankAnswer expected, final BlankAnswer actual) {
		DatabaseAbstractQuestionTest.assertEquals(expected, actual);
		Assert.assertEquals(expected.getQuestionString(), actual.getQuestionString());
		Assert.assertEquals(expected.getAnswer(), actual.getAnswer());
	}

	/**
	 * Helper method to create a blank answer question.
	 * 
	 * @param questionNumber
	 *            The question number for the question.
	 * @param unique
	 *            A unique identifier to make it easier to debug.
	 * @return A newly created question.
	 */
	public static BlankAnswer createBlankAnswer(final int questionNumber, final String unique) {
		final boolean showQuestion = false;
		final Statistics statistics = new Statistics(10, 20, 30);
		final Location locationOfAnswer = new Location("test check box Set", "b", unique, "d", "e", unique);
		final String quetion = unique;
		final String answer = "answer b";

		return new BlankAnswer(showQuestion, questionNumber, statistics, quetion, answer, locationOfAnswer);
	}

	/**
	 * Helper method to create and insert a check box question.
	 * 
	 * @param unique
	 *            A unique identifier to make it easier to debug.
	 * @return The blank answer that was created upon insertion.
	 */
	public static final BlankAnswer insertBlankAnswer(final String unique) {

		final BlankAnswer original = createBlankAnswer(-1, unique);

		final DatabaseBlankAnswer databaseBlankAnswer = new DatabaseBlankAnswer(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseBlankAnswer.set(original);

		return createBlankAnswer(questionNumber, unique);
	}

	/**
	 * Helper method to delete a check box question.
	 * 
	 * @param questionNumber
	 *            The question number to delete.
	 */
	public static void deleteBlankAnswer(final int questionNumber) {
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);
		connect.delete(DatabaseLocation.TABLE_NAME, vals);
		connect.delete(DatabaseStatistics.TABLE_NAME, vals);
		connect.delete(DatabaseBlankAnswer.TABLE_NAME, vals);
		connect.disconnect();
	}

}
