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
import ca.charland.questions.data.types.FillInTheBlanks;
import ca.charland.questions.database.data.DatabaseLocation;
import ca.charland.questions.database.data.DatabaseStatistics;
import ca.charland.questions.utilities.Database;

/**
 * @author Michael
 */
public class DatabaseFillInTheBlanksTest {

	/**
	 * Test method for {@link ca.charland.questions.database.data.types.DatabaseFillInTheBlanks#set(ca.charland.questions.data.types.FillInTheBlanks)}.
	 */
	@Test
	public final void testSet() {

		final FillInTheBlanks original = createFillInTheBlanks(-1, "DatabaseFillInTheBlanksTest.testSet");

		final DatabaseFillInTheBlanks databaseCheckBox = new DatabaseFillInTheBlanks(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = databaseCheckBox.set(original);

		final FillInTheBlanks update = createFillInTheBlanks(questionNumber, "DatabaseFillInTheBlanksTest.testSet");
		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final ResultSet rs = connect.select(DatabaseFillInTheBlanks.TABLE_NAME, vals);
		String question = "";
		String answers = "";
		try {
			question = rs.getString(DatabaseFillInTheBlanks.Column.QUESTION.toString());
			answers = rs.getString(DatabaseFillInTheBlanks.Column.BLANKS.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		final StringTokenizer st = new StringTokenizer(answers, ";");
		final ArrayList<String> answersArrayList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			answersArrayList.add(st.nextToken());
		}

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		aq.get(questionNumber);

		final DatabaseStatistics ds = new DatabaseStatistics(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Statistics afterStats = ds.get(questionNumber);

		final DatabaseLocation dl = new DatabaseLocation(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Location afterLocation = dl.get(questionNumber);

		final FillInTheBlanks after = new FillInTheBlanks(aq.getShow(), questionNumber, afterStats, question, answersArrayList, afterLocation);
		assertEquals(update, after);
		connect.disconnect();
		deleteFillInTheBlanks(questionNumber);
	}

	/**
	 * Test method for {@link ca.charland.questions.database.data.types.DatabaseFillInTheBlanks#get(int)}.
	 */
	@Test
	public final void testGet() {

		final FillInTheBlanks original = insertFillInTheBlanks("DatabaseFillInTheBlanksTest.testGet");

		final DatabaseFillInTheBlanks databaseFillInTheBlanks = new DatabaseFillInTheBlanks(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final FillInTheBlanks after = databaseFillInTheBlanks.get(original.getQuestionNumber());

		assertEquals(original, after);

		deleteFillInTheBlanks(original.getQuestionNumber());
	}

	/**
	 * Compares two fill in the blanks questions to each other.
	 * 
	 * @param expected
	 *            The expected fill in the blanks question.
	 * @param actual
	 *            The actual fill in the blanks question.
	 */
	public static void assertEquals(final FillInTheBlanks expected, final FillInTheBlanks actual) {
		DatabaseAbstractQuestionTest.assertEquals(expected, actual);
		Assert.assertEquals(expected.getQuestionString(), actual.getQuestionString());
		Assert.assertEquals(expected.getBlanks(), actual.getBlanks());
	}

	/**
	 * Helper method to create a fill in the blanks question.
	 * 
	 * @param questionNumber
	 *            The question number for the check box question.
	 * @param unique
	 *            A unique identifier to make it easier to debug.
	 * @return A newly created check box.
	 */
	public static FillInTheBlanks createFillInTheBlanks(final int questionNumber, final String unique) {
		final boolean showQuestion = false;
		final Statistics statistics = new Statistics(10, 20, 30);
		final Location locationOfAnswer = new Location("test fill in the blanks", "b", unique, "d", "e", unique);
		final String quetion = unique;
		final ArrayList<String> blanks = new ArrayList<String>();
		blanks.add("A1");
		blanks.add(unique);

		return new FillInTheBlanks(showQuestion, questionNumber, statistics, quetion, blanks, locationOfAnswer);
	}

	/**
	 * Helper method to create and insert a fill in the blanks question.
	 * 
	 * @param unique
	 *            A unique identifier to make it easier to debug.
	 * @return A newly created question.
	 */
	public static FillInTheBlanks insertFillInTheBlanks(final String unique) {

		final FillInTheBlanks original = createFillInTheBlanks(-1, unique);

		final DatabaseFillInTheBlanks database = new DatabaseFillInTheBlanks(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = database.set(original);

		return createFillInTheBlanks(questionNumber, unique);
	}

	/**
	 * Helper method to delete a check box question.
	 * 
	 * @param questionNumber
	 *            The question number of the fill in the blanks data to delete.
	 */
	public static void deleteFillInTheBlanks(final int questionNumber) {
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		connect.delete(DatabaseAbstractQuestion.TABLE_NAME, vals);
		connect.delete(DatabaseLocation.TABLE_NAME, vals);
		connect.delete(DatabaseStatistics.TABLE_NAME, vals);
		connect.delete(DatabaseFillInTheBlanks.TABLE_NAME, vals);
		connect.disconnect();
	}

}
