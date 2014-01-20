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
import ca.charland.questions.data.types.CheckBox;
import ca.charland.questions.database.data.DatabaseLocation;
import ca.charland.questions.database.data.DatabaseStatistics;
import ca.charland.questions.utilities.Database;

/**
 * @author Michael
 */
public class DatabaseCheckBoxTest {

	/**
	 * Test method for {@link ca.charland.questions.database.data.types.DatabaseCheckBox#set(int, ca.charland.questions.data.types.CheckBox)}. Sets
	 * data just into the check box table.
	 */
	@Test
	public final void testSet() {

		final CheckBox original = createCheckBox(-1, "DatabaseCheckBoxTest.testSet");

		final DatabaseCheckBox databaseCheckBox = new DatabaseCheckBox(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseCheckBox.set(original);

		final CheckBox update = createCheckBox(questionNumber, "DatabaseCheckBoxTest.testSet");
		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final ResultSet rs = connect.select(DatabaseCheckBox.TABLE_NAME, vals);
		String question = "";
		String answers = "";
		String options = "";
		try {
			question = rs.getString(DatabaseCheckBox.Column.QUESTION.toString());
			answers = rs.getString(DatabaseCheckBox.Column.ANSWERS.toString());
			options = rs.getString(DatabaseCheckBox.Column.OPTIONS.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		final StringTokenizer st = new StringTokenizer(answers, ";");
		final ArrayList<String> answersArrayList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			answersArrayList.add(st.nextToken());
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

		final CheckBox after = new CheckBox(aq.getShow(), questionNumber, afterStats, question, answersArrayList, afterLocation, optionsArrayList);
		assertEquals(update, after);
		connect.disconnect();
		deleteCheckBox(questionNumber);
	}

	/**
	 * Test method for {@link ca.charland.questions.database.data.types.DatabaseCheckBox#get()}.
	 */
	@Test
	public final void testGet() {

		final CheckBox original = insertCheckBox("DatabaseCheckBox.testGet");

		final DatabaseCheckBox databaseCheckBox = new DatabaseCheckBox(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final CheckBox after = databaseCheckBox.get(original.getQuestionNumber());

		assertEquals(original, after);

		deleteCheckBox(original.getQuestionNumber());
	}

	/**
	 * Compares two check box questions to each other.
	 * 
	 * @param expected
	 *            The expected location.
	 * @param actual
	 *            The actual location.
	 */
	public static void assertEquals(final CheckBox expected, final CheckBox actual) {
		DatabaseAbstractQuestionTest.assertEquals(expected, actual);

		Assert.assertEquals(expected.getQuestionString(), actual.getQuestionString());
		Assert.assertEquals(expected.getAnswers(), actual.getAnswers());
		Assert.assertEquals(expected.getOptions(), actual.getOptions());
	}

	/**
	 * Helper method to create a check box question.
	 * 
	 * @param questionNumber
	 *            The question number for the check box question.
	 * @param unique
	 *            A unique identifier to make it easier to debug.
	 * @return A newly created check box.
	 */
	public static CheckBox createCheckBox(final int questionNumber, final String unique) {
		final boolean showQuestion = false;
		final Statistics statistics = new Statistics(10, 20, 30);
		final Location locationOfAnswer = new Location("test check box Set", "b", unique, "d", "e", unique);
		final String quetion = unique;
		final ArrayList<String> answers = new ArrayList<String>();
		answers.add("A1");
		answers.add(unique);
		final ArrayList<String> options = new ArrayList<String>();
		options.add(unique);
		options.add("O2");

		return new CheckBox(showQuestion, questionNumber, statistics, quetion, answers, locationOfAnswer, options);
	}

	/**
	 * Helper method to create and insert a check box question.
	 * 
	 * @param unique
	 *            A unique identifier to make it easier to debug.
	 * @return The check box question after insertion.
	 */
	public static final CheckBox insertCheckBox(final String unique) {

		final CheckBox original = createCheckBox(-1, unique);

		final DatabaseCheckBox databaseCheckBox = new DatabaseCheckBox(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseCheckBox.set(original);

		return createCheckBox(questionNumber, unique);
	}

	/**
	 * Helper method to delete a check box question.
	 * 
	 * @param questionNumber
	 *            The question number of the data to delete.
	 */
	public static final void deleteCheckBox(final int questionNumber) {
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);
		connect.delete(DatabaseLocation.TABLE_NAME, vals);
		connect.delete(DatabaseStatistics.TABLE_NAME, vals);
		connect.delete(DatabaseCheckBox.TABLE_NAME, vals);
		connect.disconnect();
	}
}
