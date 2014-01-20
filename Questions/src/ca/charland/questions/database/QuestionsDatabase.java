package ca.charland.questions.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;
import ca.charland.questions.data.types.AbstractQuestion;
import ca.charland.questions.data.types.BlankAnswer;
import ca.charland.questions.data.types.CheckBox;
import ca.charland.questions.data.types.FillInTheBlanks;
import ca.charland.questions.data.types.RadioButton;
import ca.charland.questions.data.types.ShortCut;
import ca.charland.questions.database.data.DatabaseStatistics;
import ca.charland.questions.database.data.types.DatabaseAbstractQuestion;
import ca.charland.questions.database.data.types.DatabaseBlankAnswer;
import ca.charland.questions.database.data.types.DatabaseCheckBox;
import ca.charland.questions.database.data.types.DatabaseFillInTheBlanks;
import ca.charland.questions.database.data.types.DatabaseRadioButton;
import ca.charland.questions.database.data.types.DatabaseShortCut;
import ca.charland.questions.utilities.Database;

/**
 * Creates a question database.
 * 
 * @author Michael
 */
public final class QuestionsDatabase {

	/**
	 * The string name of the question.
	 */
	public static final String NAME = "questions";

	/**
	 * The name of the database to connect too.
	 */
	private final String _databaseName;

	/**
	 * Constructor which uses this classes database name to connect.
	 */
	public QuestionsDatabase() {
		this(NAME);
	}

	/**
	 * Constructor with the name of the database to connect.
	 * 
	 * @param databaseName
	 *            The name of the database.
	 */
	public QuestionsDatabase(final String databaseName) {
		_databaseName = databaseName;
	}

	/**
	 * Gets the number of questions in the database.
	 * 
	 * @return The number of questions in the database.
	 */
	public ArrayList<Integer> getQuestionNumbers() {

		final ArrayList<Integer> questionNumbers = new ArrayList<Integer>();

		final Database connect = new Database(_databaseName);
		final ResultSet rs = connect.select(DatabaseAbstractQuestion.TABLE_NAME, null);

		try {
			rs.first();
			while (!rs.isAfterLast()) {
				questionNumbers.add(rs.getInt(DatabaseAbstractQuestion.Column.QUESTION_NUMBER.toString()));
				rs.next();
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}

		return questionNumbers;
	}

	/**
	 * Retrieves a question from the database.
	 * 
	 * @param questionNumber
	 *            The question number to get.
	 * @return A question from the database.
	 */
	public AbstractQuestion getQuestion(final int questionNumber) {

		// First find out the type of the question.
		Type type = null;
		final Database connect = new Database(_databaseName);
		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		final ResultSet rs = connect.select(DatabaseAbstractQuestion.TABLE_NAME, vals);

		try {
			rs.first();
			type = Type.valueOf(rs.getString(DatabaseAbstractQuestion.Column.TYPE.toString()));
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
		if (type.equals(Type.RadioButton)) {
			final DatabaseRadioButton drb = new DatabaseRadioButton(_databaseName);
			return drb.get(questionNumber);
		} else if (type.equals(Type.CheckBox)) {
			final DatabaseCheckBox drb = new DatabaseCheckBox(_databaseName);
			return drb.get(questionNumber);
		} else if (type.equals(Type.BlankAnswer)) {
			final DatabaseBlankAnswer dba = new DatabaseBlankAnswer(_databaseName);
			return dba.get(questionNumber);
		} else if (type.equals(Type.FillInTheBlanks)) {
			final DatabaseFillInTheBlanks dba = new DatabaseFillInTheBlanks(_databaseName);
			return dba.get(questionNumber);
		} else if (type.equals(Type.ShortCut)) {
			final DatabaseShortCut dba = new DatabaseShortCut(_databaseName);
			return dba.get(questionNumber);
		}
		throw new RuntimeException("TYPE NOT SUPPORTED");
	}

	/**
	 * Inserts a question into the database.
	 * 
	 * @param aq
	 *            The question to insert into the database.
	 * @return The question number.
	 */
	public int insertQuestion(final AbstractQuestion aq) {

		final Type type = aq.getType();
		if (type.equals(Type.RadioButton)) {
			final DatabaseRadioButton drb = new DatabaseRadioButton(_databaseName);
			return drb.set((RadioButton) aq);
		} else if (type.equals(Type.CheckBox)) {
			final DatabaseCheckBox dcb = new DatabaseCheckBox(_databaseName);
			return dcb.set((CheckBox) aq);
		} else if (type.equals(Type.BlankAnswer)) {
			final DatabaseBlankAnswer dba = new DatabaseBlankAnswer(_databaseName);
			return dba.set((BlankAnswer) aq);
		} else if (type.equals(Type.FillInTheBlanks)) {
			final DatabaseFillInTheBlanks dba = new DatabaseFillInTheBlanks(_databaseName);
			return dba.set((FillInTheBlanks) aq);
		} else if (type.equals(Type.ShortCut)) {
			final DatabaseShortCut dba = new DatabaseShortCut(_databaseName);
			return dba.set((ShortCut) aq);
		}
		throw new RuntimeException("TYPE NOT SUPPORTED");
	}

	/**
	 * Updates ONLY the statistics of the question.
	 * 
	 * @param aq
	 *            The question to update.
	 */
	public void updateQuestion(final AbstractQuestion aq) {
		final DatabaseStatistics ds = new DatabaseStatistics(_databaseName);
		final int questionNumber = aq.getQuestionNumber();

		final int difficulty = aq.getDifficulty();
		final int correct = aq.getCorrectlyAnswered();
		final int total = aq.getTotalTimesAnswered();
		ds.update(questionNumber, new Statistics(difficulty, correct, total));
	}
}
