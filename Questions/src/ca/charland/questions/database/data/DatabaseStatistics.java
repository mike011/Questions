package ca.charland.questions.database.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.database.data.types.DatabaseAbstractQuestion;
import ca.charland.questions.utilities.Database;

/**
 * The basic parts of a question.
 * 
 * @author Michael
 */
public final class DatabaseStatistics {

	/**
	 * The name of MySQL database.
	 */
	public static final String DATABASE_NAME = "questions";

	/**
	 * The name of MySQL database table.
	 */
	public static final String TABLE_NAME = "statistics";

	/**
	 * The columns in the MySQL database table.
	 */
	public static enum Column {

		/**
		 * The name of the difficulty column.
		 */
		DIFFICULTY,

		/**
		 * The name of the correctly answered column.
		 */
		CORRECTLY_ANSWERED,

		/**
		 * The name of the total answered column.
		 */
		TOTAL_ANSWERED
	}

	/**
	 * The name of the database to connect too.
	 */
	private final String _databaseName;

	/**
	 * Creates a new connection to the database.
	 * 
	 * @param databaseName
	 *            The name of the database.
	 */
	public DatabaseStatistics(final String databaseName) {
		_databaseName = databaseName;
	}

	/**
	 * Inserts the statistics of a question into the database.
	 * 
	 * @param questionNumber
	 *            The question number to insert.
	 * @param qs
	 *            The statistics data to add.
	 */
	public void set(final int questionNumber, final Statistics qs) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		vals.put(Column.CORRECTLY_ANSWERED, "" + qs.getCorrectlyAnswered());
		vals.put(Column.TOTAL_ANSWERED, "" + qs.getTotalTimesAnswered());
		vals.put(Column.DIFFICULTY, "" + qs.getDifficulty());

		connect.insert(TABLE_NAME, vals);
		connect.disconnect();
	}

	/**
	 * Updates the statistics of a question.
	 * 
	 * @param questionNumber
	 *            The question number to insert.
	 * @param qs
	 *            The statistics data to add.
	 */
	public void update(final int questionNumber, final Statistics qs) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(Column.CORRECTLY_ANSWERED, "" + qs.getCorrectlyAnswered());
		vals.put(Column.TOTAL_ANSWERED, "" + qs.getTotalTimesAnswered());
		vals.put(Column.DIFFICULTY, "" + qs.getDifficulty());

		final Hashtable<Enum<?>, Object> compare = new Hashtable<Enum<?>, Object>();
		compare.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);

		connect.update(TABLE_NAME, vals, compare);
		connect.disconnect();
	}

	/**
	 * Gets the statistics of the question from the database.
	 * 
	 * @param questionNumber
	 *            The question number to insert.
	 * @return The statistics data of the question.
	 */
	public Statistics get(final int questionNumber) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		final ResultSet rs = connect.select(TABLE_NAME, vals);

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

		connect.disconnect();
		return new Statistics(difficulty, correct, total);
	}
}
