package ca.charland.questions.database.data.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import ca.charland.questions.data.Type;
import ca.charland.questions.data.types.CheckBox;
import ca.charland.questions.utilities.Database;

/**
 * The check box question in the database.
 * 
 * @author Michael
 */
public final class DatabaseCheckBox {

	/**
	 * The name of MySQL database table.
	 */
	public static final String TABLE_NAME = Type.CheckBox.toString();

	/**
	 * The columns in the MySQL database table.
	 */
	public static enum Column {

		/**
		 * The name of the question column.
		 */
		QUESTION,

		/**
		 * The name of the answers column.
		 */
		ANSWERS,

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
	public DatabaseCheckBox(final String databaseName) {
		_databaseName = databaseName;
	}

	/**
	 * Inserts the check box question into the database.
	 * 
	 * @param checkBox
	 *            The check box data to insert.
	 * @return The question number inserted.
	 */
	public int set(final CheckBox checkBox) {

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		final int questionNumber = aq.set(checkBox);

		final Database connect = new Database(_databaseName);

		final String question = checkBox.getQuestionString().replaceAll("\\'", "\\\\\'");
		String answers = "";
		for (final String answer : checkBox.getAnswers()) {
			answers += answer.replaceAll("\\'", "\\\\\'") + ";";
		}

		String options = "";
		for (final String option : checkBox.getOptions()) {
			options += option.replaceAll("\\'", "\\\\\'") + ";";
		}

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		vals.put(Column.QUESTION, question);
		vals.put(Column.ANSWERS, answers);
		vals.put(Column.OPTIONS, options);

		connect.insert(TABLE_NAME, vals);
		connect.disconnect();

		return questionNumber;
	}

	/**
	 * Gets the check box question from the database.
	 * 
	 * @param questionNumber
	 *            The question number to insert.
	 * @return The check box question from the database.
	 */
	public CheckBox get(final int questionNumber) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		final ResultSet rs = connect.select(TABLE_NAME, vals);

		String question = "";
		String answers = "";
		String options = "";
		try {
			question = rs.getString(Column.QUESTION.toString());
			answers = rs.getString(Column.ANSWERS.toString());
			options = rs.getString(Column.OPTIONS.toString());
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

		final DatabaseAbstractQuestion aq = new DatabaseAbstractQuestion(_databaseName);
		aq.get(questionNumber);

		connect.disconnect();
		return new CheckBox(aq.getShow(), questionNumber, aq.getStatistics(), question, answersArrayList, aq.getLocation(), optionsArrayList);

	}
}
