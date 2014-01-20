package ca.charland.questions.database.data.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import ca.charland.questions.data.Type;
import ca.charland.questions.data.types.BlankAnswer;
import ca.charland.questions.utilities.Database;

/**
 * The blank answer question in the database.
 * 
 * @author Michael
 */
public final class DatabaseBlankAnswer {

	/**
	 * The name of MySQL database table.
	 */
	public static final String TABLE_NAME = Type.BlankAnswer.toString();

	/**
	 * The columns in the MySQL database table.
	 */
	public static enum Column {

		/**
		 * The name of the question column.
		 */
		QUESTION,

		/**
		 * The name of the answer column.
		 */
		ANSWER
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
	public DatabaseBlankAnswer(final String databaseName) {
		_databaseName = databaseName;
	}

	/**
	 * Inserts the check box question into the database.
	 * 
	 * @param blankAnswer
	 *            The data to add.
	 * @return The question number inserted.
	 */
	public int set(final BlankAnswer blankAnswer) {

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		final int questionNumber = aq.set(blankAnswer);

		final Database connect = new Database(_databaseName);

		final String question = blankAnswer.getQuestionString().replaceAll("\\'", "\\\\\'");
		String answer = blankAnswer.getAnswer().replaceAll("\\'", "\\\\\'");

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		vals.put(Column.QUESTION, question);
		vals.put(Column.ANSWER, answer);

		connect.insert(TABLE_NAME, vals);
		connect.disconnect();

		return questionNumber;
	}

	/**
	 * Gets the check box question from the database.
	 * 
	 * @param questionNumber
	 *            The question number to get.
	 * @return The blank answer question.
	 */
	public BlankAnswer get(final int questionNumber) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		final ResultSet rs = connect.select(TABLE_NAME, vals);

		String question = "";
		String answer = "";
		try {
			question = rs.getString(Column.QUESTION.toString());
			answer = rs.getString(Column.ANSWER.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		aq.get(questionNumber);

		connect.disconnect();
		return new BlankAnswer(aq.getShow(), questionNumber, aq.getStatistics(), question, answer, aq.getLocation());

	}
}
