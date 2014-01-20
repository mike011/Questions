package ca.charland.questions.database.data.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import ca.charland.questions.data.Type;
import ca.charland.questions.data.types.FillInTheBlanks;
import ca.charland.questions.utilities.Database;

/**
 * The check box question in the database.
 * 
 * @author Michael
 * 
 */
public final class DatabaseFillInTheBlanks {

	/**
	 * The name of MySQL database table.
	 */
	public static final String TABLE_NAME = Type.FillInTheBlanks.toString();

	/**
	 * The columns in the MySQL database table.
	 */
	public static enum Column {

		/**
		 * The name of the question column.
		 */
		QUESTION,

		/**
		 * The name of the blanks column.
		 */
		BLANKS
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
	public DatabaseFillInTheBlanks(final String databaseName) {
		_databaseName = databaseName;
	}

	/**
	 * Inserts the fill in the blanks question into the database.
	 * 
	 * @param fillInTheBlanks
	 *            The question data to insert.
	 * @return THe number of the question.
	 */
	public int set(final FillInTheBlanks fillInTheBlanks) {

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		final int questionNumber = aq.set(fillInTheBlanks);

		final Database connect = new Database(_databaseName);

		final String question = fillInTheBlanks.getAnswer().replaceAll("\\'", "\\\\\'");
		String blanks = "";
		for (final String blank : fillInTheBlanks.getBlanks()) {
			blanks += blank.replaceAll("\\'", "\\\\\'") + ";";
		}

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		vals.put(Column.QUESTION, question);
		vals.put(Column.BLANKS, blanks);
		connect.insert(TABLE_NAME, vals);
		connect.disconnect();

		return questionNumber;
	}

	/**
	 * Gets the fill in the blanks question from the database.
	 * 
	 * @param questionNumber
	 *            The question number to retrieve.
	 * @return The question data from the database.
	 */
	public FillInTheBlanks get(final int questionNumber) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		final ResultSet rs = connect.select(TABLE_NAME, vals);

		String question = "";
		String blanks = "";
		try {
			question = rs.getString(Column.QUESTION.toString());
			blanks = rs.getString(Column.BLANKS.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		final StringTokenizer st = new StringTokenizer(blanks, ";");
		final ArrayList<String> blanksArrayList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			blanksArrayList.add(st.nextToken());
		}

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		aq.get(questionNumber);

		connect.disconnect();

		return new FillInTheBlanks(aq.getShow(), questionNumber, aq.getStatistics(), question, blanksArrayList, aq.getLocation());
	}
}
