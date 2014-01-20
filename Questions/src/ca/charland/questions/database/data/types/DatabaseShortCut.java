package ca.charland.questions.database.data.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import ca.charland.questions.data.Type;
import ca.charland.questions.data.types.ShortCut;
import ca.charland.questions.utilities.Database;

/**
 * The database interaction for a short cut key type question.
 * 
 * @author Michael
 */
public class DatabaseShortCut {
	/**
	 * The name of MySQL database table.
	 */
	public static final String TABLE_NAME = Type.ShortCut.toString();

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
		SHORT_CUT;
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
	public DatabaseShortCut(final String databaseName) {
		_databaseName = databaseName;
	}

	/**
	 * Inserts the short cut question into the database.
	 * 
	 * @param shortCut
	 *            The data to add.
	 * @return The question number inserted.
	 */
	public final int set(final ShortCut shortCut) {

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		final int questionNumber = aq.set(shortCut);

		final Database connect = new Database(_databaseName);

		final String question = shortCut.getQuestionString().replaceAll("\\'", "\\\\\'");
		String shortCutString = shortCut.getShortCut().replaceAll("\\'", "\\\\\'");

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		vals.put(Column.QUESTION, question);
		vals.put(Column.SHORT_CUT, shortCutString);

		connect.insert(TABLE_NAME, vals);
		connect.disconnect();

		return questionNumber;
	}

	/**
	 * Gets the short cut question from the database.
	 * 
	 * @param questionNumber
	 *            The question number to get.
	 * @return The short cut question.
	 */
	public final ShortCut get(final int questionNumber) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		final ResultSet rs = connect.select(TABLE_NAME, vals);

		String question = "";
		String shortCut = "";
		try {
			question = rs.getString(Column.QUESTION.toString());
			shortCut = rs.getString(Column.SHORT_CUT.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		aq.get(questionNumber);

		connect.disconnect();
		return new ShortCut(aq.getShow(), questionNumber, aq.getStatistics(), question, shortCut, aq.getLocation());

	}

}
