/**
 * 
 */
package ca.charland.questions.database.data.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import junit.framework.Assert;

import org.junit.Test;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.types.RadioButton;
import ca.charland.questions.database.data.DatabaseLocation;
import ca.charland.questions.database.data.DatabaseStatistics;
import ca.charland.questions.utilities.Database;

/**
 * Tests inserting and getting data from the radio button table.
 * 
 * @author Michael
 */
public class DatabaseRadioButtonTest {

	/**
	 * Test method for {@link ca.charland.questions.database.data.types.DatabaseRadioButton#set(ca.charland.questions.data.types.RadioButton)}.
	 */
	@Test
	public final void testSet() {

		final RadioButton original = createRadioButton(-1, "unique");

		final DatabaseRadioButton databaseRadioButton = new DatabaseRadioButton(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseRadioButton.set(original);

		final RadioButton update = createRadioButton(questionNumber, "unique");

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final ResultSet rs = connect.select(DatabaseRadioButton.TABLE_NAME, vals);
		String question = "";
		String answer = "";
		String options = "";
		try {
			question = rs.getString(DatabaseRadioButton.Column.QUESTION.toString());
			answer = rs.getString(DatabaseRadioButton.Column.ANSWER.toString());
			options = rs.getString(DatabaseRadioButton.Column.OPTIONS.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		final StringTokenizer st2 = new StringTokenizer(options, ";");
		final ArrayList<String> optionsArrayList = new ArrayList<String>();
		while (st2.hasMoreTokens()) {
			optionsArrayList.add(st2.nextToken());
		}

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		aq.get(questionNumber);

		final DatabaseStatistics ds = new DatabaseStatistics(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Statistics afterStats = ds.get(questionNumber);

		final DatabaseLocation dl = new DatabaseLocation(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Location afterLocation = dl.get(questionNumber);

		final RadioButton after = new RadioButton(aq.getShow(), questionNumber, afterStats, question, answer, afterLocation, optionsArrayList);
		assertEquals(update, after);
		connect.disconnect();

		deleteRadioButton(questionNumber);
	}

	/**
	 * Test method for {@link ca.charland.questions.database.data.types.DatabaseRadioButton#get(}.
	 */
	@Test
	public final void testGet() {

		final RadioButton original = insertRadioButton("DatabaseRadioButtonTest.testGet");

		final DatabaseRadioButton database = new DatabaseRadioButton(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final RadioButton after = database.get(original.getQuestionNumber());

		assertEquals(original, after);

		deleteRadioButton(original.getQuestionNumber());
	}

	/**
	 * Compares two radio button questions to each other.
	 * 
	 * @param expected
	 *            The expected location.
	 * @param actual
	 *            The actual location.
	 */
	public static void assertEquals(final RadioButton expected, final RadioButton actual) {
		DatabaseAbstractQuestionTest.assertEquals(expected, actual);

		Assert.assertEquals(expected.getQuestionString(), actual.getQuestionString());
		Assert.assertEquals(expected.getAnswer(), actual.getAnswer());
		Assert.assertEquals(expected.getOptions(), actual.getOptions());
	}

	/**
	 * Helper method that creates a radio button question.
	 * 
	 * @param questionNumber
	 *            The question number of the radio button to create.
	 * @param unique
	 *            A string to uniquely ID the question.
	 * @return The newly created radio button data.
	 */
	public static RadioButton createRadioButton(final int questionNumber, final String unique) {
		final boolean show = false;
		final Statistics statistics = new Statistics(10, 20, 30);
		final Location locationOfAnswer = new Location("unique", "b", "c", "d", "e", "f");
		final String question = "unique";
		final String answer = "answering";
		final ArrayList<String> options = new ArrayList<String>();
		options.add("O1");
		options.add("O2");

		return new RadioButton(show, questionNumber, statistics, question, answer, locationOfAnswer, options);
	}

	/**
	 * Helper method to create and insert a radio button question.
	 * 
	 * @param unique
	 *            A string to uniquely ID the question.
	 * @return The radio button created after insertion.
	 */
	public static RadioButton insertRadioButton(final String unique) {

		final RadioButton original = createRadioButton(-1, "unique");

		final DatabaseRadioButton database = new DatabaseRadioButton(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = database.set(original);

		return createRadioButton(questionNumber, "unique");
	}

	/**
	 * Helper method to delete a check box question.
	 * 
	 * @param questionNumber
	 *            The question number of the check box to delete.
	 */
	public static void deleteRadioButton(final int questionNumber) {
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);
		connect.delete(DatabaseLocation.TABLE_NAME, vals);
		connect.delete(DatabaseStatistics.TABLE_NAME, vals);
		connect.delete(DatabaseRadioButton.TABLE_NAME, vals);
		connect.disconnect();
	}
}
