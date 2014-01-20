package ca.charland.questions.database.data.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;
import ca.charland.questions.data.Location;
import ca.charland.questions.data.types.AbstractQuestion;
import ca.charland.questions.database.data.DatabaseLocation;
import ca.charland.questions.database.data.DatabaseStatistics;
import ca.charland.questions.utilities.Database;

/**
 * The basic parts of a question.
 * 
 * @author Michael
 */
public final class DatabaseAbstractQuestion {

	/**
	 * The name of MySQL database table.
	 */
	public static final String TABLE_NAME = "question";

	/**
	 * The columns in the MySQL database table.
	 */
	public static enum Column {

		/**
		 * The name of the question number column.
		 */
		QUESTION_NUMBER,

		/**
		 * The name of the show column.
		 */
		SHOW,

		/**
		 * The name of the type column.
		 */
		TYPE
	}

	/**
	 * The name of the database to connect too.
	 */
	private final String _databaseName;

	/**
	 * The question type.
	 */
	private Type _type;

	/**
	 * Whether to show the question or not.
	 */
	private boolean _show;

	/**
	 * The statistics of the question.
	 */
	private Statistics _statistics;

	/**
	 * The location of the question.
	 */
	private Location _location;

	/**
	 * Creates a new connection to the database.
	 * 
	 * @param databaseName
	 *            The name of the database.
	 */
	public DatabaseAbstractQuestion(final String databaseName) {
		_databaseName = databaseName;
		_type = null;
		_show = false;
	}

	/**
	 * Inserts the question information into the database.
	 * 
	 * @param aq
	 *            The abstract question data to add.
	 * @return Returns the question number of the question inserted.
	 */
	public int set(final AbstractQuestion aq) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(Column.TYPE, aq.getType().toString());
		vals.put(Column.SHOW, "" + aq.getShowQuestion());

		final ResultSet rs = connect.insert(TABLE_NAME, vals);
		int questionNumber = -1;
		try {
			questionNumber = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			final int error = -2;
			questionNumber = error;
		}
		connect.disconnect();

		final DatabaseLocation dl = new DatabaseLocation(_databaseName);
		dl.set(questionNumber, aq.getLocation());

		final DatabaseStatistics ds = new DatabaseStatistics(_databaseName);
		final int correct = aq.getCorrectlyAnswered();
		final int total = aq.getTotalTimesAnswered();
		final int difficulty = aq.getDifficulty();
		ds.set(questionNumber, new Statistics(difficulty, correct, total));

		return questionNumber;
	}

	/**
	 * Gets the abstract question information from the database.
	 * 
	 * @param questionNumber
	 *            The question number to insert.
	 */
	public void get(final int questionNumber) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(Column.QUESTION_NUMBER, "" + questionNumber);
		final ResultSet rs = connect.select(TABLE_NAME, vals);

		try {
			_type = Type.valueOf(rs.getString(Column.TYPE.toString()));
			_show = Boolean.parseBoolean(rs.getString(Column.SHOW.toString()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connect.disconnect();

		final DatabaseStatistics ds = new DatabaseStatistics(_databaseName);
		_statistics = ds.get(questionNumber);

		final DatabaseLocation dl = new DatabaseLocation(_databaseName);
		_location = dl.get(questionNumber);

	}

	/**
	 * Gets whether to show the question or not.
	 * 
	 * @return Whether to show the question or not.
	 */
	public boolean getShow() {
		return _show;
	}

	/**
	 * Gets the type of the question.
	 * 
	 * @return The type of the question.
	 */
	public Type getType() {
		return _type;
	}

	/**
	 * Gets the statistics of the question.
	 * @return The statistics of the question.
	 */
	public Statistics getStatistics() {
		return _statistics;
	}

	/**
	 * Gets the location of the question.
	 * @return The location of the question.
	 */
	public Location getLocation() {
		return _location;
	}
}
