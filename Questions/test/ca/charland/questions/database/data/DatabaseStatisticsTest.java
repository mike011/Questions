/**
 * 
 */
package ca.charland.questions.database.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.Test;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.database.data.DatabaseStatistics.Column;
import ca.charland.questions.database.data.types.DatabaseAbstractQuestion;
import ca.charland.questions.database.data.types.DatabaseAbstractQuestionTest;
import ca.charland.questions.utilities.Database;

/**
 * @author Michael
 */
public class DatabaseStatisticsTest {

	/**
	 * Test method for {@link ca.charland.questions.database.data.DatabaseStatistics#set(int, ca.charland.questions.data.Location)}.
	 */
	@Test
	public final void testSet() {
		final Statistics orignal = new Statistics(100, 200, 300);

		final DatabaseStatistics databaseStatistics = new DatabaseStatistics(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = 256;
		databaseStatistics.set(questionNumber, orignal);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		final ResultSet rs = connect.select(DatabaseStatistics.TABLE_NAME, vals);

		int difficulty = -1;
		int correct = -1;
		int total = -1;
		try {
			difficulty = rs.getInt(Column.DIFFICULTY.toString());
			correct = rs.getInt(Column.CORRECTLY_ANSWERED.toString());
			total = rs.getInt(Column.TOTAL_ANSWERED.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Statistics after = new Statistics(difficulty, correct, total);

		assertEquals(orignal, after);

		connect.delete(DatabaseStatistics.TABLE_NAME, vals);
		connect.disconnect();
	}

	/**
	 * Test method for {@link ca.charland.questions.database.data.DatabaseStatistics#get(int)}.
	 */
	@Test
	public final void testGet() {
		final Statistics actual = new Statistics(400, 500, 600);

		final int questionNumber = 255;

		final DatabaseStatistics databaseStatistics = new DatabaseStatistics(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		databaseStatistics.set(questionNumber, actual);

		final Statistics after = databaseStatistics.get(questionNumber);
		assertEquals(actual, after);

		final Database connect = new Database(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		connect.delete(DatabaseStatistics.TABLE_NAME, vals);
		connect.disconnect();
	}

	/**
	 * Compares two Statistics to each other.
	 * 
	 * @param expected
	 *            The expected Statistics.
	 * @param actual
	 *            The actual Statistics.
	 */
	private static void assertEquals(final Statistics expected, final Statistics actual) {
		Assert.assertEquals(expected.getCorrectlyAnswered(), actual.getCorrectlyAnswered());
		Assert.assertEquals(expected.getDifficulty(), actual.getDifficulty());
		Assert.assertEquals(expected.getTotalTimesAnswered(), actual.getTotalTimesAnswered());

	}

}
