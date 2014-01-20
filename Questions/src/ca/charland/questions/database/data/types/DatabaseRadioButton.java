/**
 * 
 */
package ca.charland.questions.database.data.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import ca.charland.questions.data.Type;
import ca.charland.questions.data.types.RadioButton;
import ca.charland.questions.utilities.Database;

/**
 * The ability to get and insert radio button database in to a database.
 * 
 * @author Michael
 */
public final class DatabaseRadioButton {

	/**
	 * The name of MySQL database table.
	 */
	public static final String TABLE_NAME = Type.RadioButton.toString();

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
		ANSWER,

		/**
		 * The name of the options column.
		 */
		OPTIONS
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
	public DatabaseRadioButton(final String databaseName) {
		_databaseName = databaseName;
	}

	/**
	 * Inserts the radio button question into the database.
	 * 
	 * @param radioButton
	 *            The radio Button data to add.
	 * @return The question number of the radio button.
	 */
	public int set(final RadioButton radioButton) {

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		final int questionNumber = aq.set(radioButton);

		final Database connect = new Database(_databaseName);

		final String question = radioButton.getQuestionString().replaceAll("\\'", "\\\\\'");
		final String answer = radioButton.getAnswer().replaceAll("\\'", "\\\\\'");

		String options = "";
		for (final String option : radioButton.getOptions()) {
			options += option.replaceAll("\\'", "\\\\\'") + ";";
		}

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		vals.put(Column.QUESTION, question);
		vals.put(Column.ANSWER, answer);
		vals.put(Column.OPTIONS, options);

		connect.insert(TABLE_NAME, vals);
		connect.disconnect();

		return questionNumber;
	}

	/**
	 * Gets the radio button from the database.
	 * 
	 * @param questionNumber
	 *            The question number to insert.
	 * @return The radio button data from the database. 
	 */
	public RadioButton get(final int questionNumber) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		final ResultSet rs = connect.select(TABLE_NAME, vals);

		String question = "";
		String answer = "";
		String options = "";
		try {
			question = rs.getString(Column.QUESTION.toString());
			answer = rs.getString(Column.ANSWER.toString());
			options = rs.getString(Column.OPTIONS.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		final StringTokenizer st2 = new StringTokenizer(options, ";");
		final ArrayList<String> optionsArrayList = new ArrayList<String>();
		while (st2.hasMoreTokens()) {
			optionsArrayList.add(st2.nextToken());
		}

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		aq.get(questionNumber);

		connect.disconnect();
		return new RadioButton(aq.getShow(), questionNumber, aq.getStatistics(), question, answer, aq.getLocation(), optionsArrayList);

	}
}
