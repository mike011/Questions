package ca.charland.questions.data.types;

import java.util.ArrayList;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;
import ca.charland.questions.utilities.StringUtilities;

/**
 * This is a fill in the blanks type of question.
 * 
 * @author Michael
 */
public final class FillInTheBlanks extends AbstractQuestion {

	/**
	 * The correct answer.
	 */
	private final ArrayList<String> _blanks;

	/**
	 * Constructor.
	 * 
	 * @param questionString
	 *            The question to be asked.
	 * @param answerLocation
	 *            The location of the answer.
	 * @param blanks
	 *            The correct answers.
	 */
	public FillInTheBlanks(final String questionString, final ArrayList<String> blanks, final Location answerLocation) {
		super(Type.FillInTheBlanks, answerLocation, questionString);
		_blanks = blanks;
	}

	/**
	 * Constructor.
	 * 
	 * @param showQuestion
	 *            Should the question be shown or not.
	 * @param questionNumber
	 *            What is the number of the question.
	 * @param statistics
	 *            The statistics about the question.
	 * @param question
	 *            The question to be asked.
	 * @param answerLocation
	 *            The location of the answer.
	 * @param blanks
	 *            The correct answers.
	 */
	public FillInTheBlanks(final boolean showQuestion, final int questionNumber, final Statistics statistics, final String question,
			final ArrayList<String> blanks, final Location answerLocation) {
		super(Type.FillInTheBlanks, showQuestion, questionNumber, statistics, answerLocation, question);
		_blanks = blanks;
	}

	/**
	 * Gets the correct answers.
	 * 
	 * @return The correct answers.
	 */
	public ArrayList<String> getBlanks() {
		return _blanks;
	}

	/**
	 * Takes an array list of blanks and makes a statement from it.
	 * 
	 * @param statement
	 *            The statement
	 * @param blanks
	 *            The blanks to replace the values in the statement.
	 * @return The statement.
	 */
	private static String createStatement(final String statement, final ArrayList<String> blanks) {
		final ArrayList<String> splits = StringUtilities.split(statement);
		String result = "";
		for (int x = 0; x < splits.size(); x++) {

			boolean found = false;
			for (final String word : blanks) {
				if (splits.get(x).trim().equalsIgnoreCase(word.trim())) {
					result += "[" + blanks.indexOf(word) + "]";
					found = true;
				}
			}

			if (!found) {
				result += splits.get(x);
			}
		}
		return result;
	}

	/**
	 * Returns the statement of the question.
	 * 
	 * @return The statement of the question.
	 */
	public String getQuestionString() {
		return createStatement(super.getQuestionString(), _blanks);
	}

	/**
	 * Gets the answer to the question.
	 * @return The answer to the question.
	 */
	public String getAnswer() {
		return super.getQuestionString();
	}
}
