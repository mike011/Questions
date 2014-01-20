package ca.charland.questions.data.types;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;

/**
 * This is a fill in the blanks type of question.
 * 
 * @author Michael
 */
public class BlankAnswer extends AbstractQuestion {

	/**
	 * The correct answer.
	 */
	private final String _answer;

	/**
	 * Constructor.
	 * 
	 * @param questionString
	 *            The question to be asked.
	 * @param answerLocation
	 *            The location of the answer.
	 * @param answer
	 *            The correct answer.
	 */
	public BlankAnswer(final String questionString, final String answer, final Location answerLocation) {
		super(Type.BlankAnswer, answerLocation, questionString);
		_answer = answer;
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
	 * @param answer
	 *            The correct answer.
	 */
	public BlankAnswer(final boolean showQuestion, final int questionNumber, final Statistics statistics, final String question, final String answer,
			final Location answerLocation) {
		super(Type.BlankAnswer, showQuestion, questionNumber, statistics, answerLocation, question);
		_answer = answer;
	}

	/**
	 * Gets the correct answer.
	 * 
	 * @return The correct answer.
	 */
	public String getAnswer() {
		return _answer;
	}
}
