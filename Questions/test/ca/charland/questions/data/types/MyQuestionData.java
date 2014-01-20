package ca.charland.questions.data.types;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;

/**
 * Creates a dummy type of question data for testing.
 * 
 * @author Michael
 */
public class MyQuestionData extends AbstractQuestion {

	/**
	 * Constructor one.
	 * 
	 * @param questionLocation
	 *            The location of the question.
	 * @param questionString
	 *            The statistics of the question.
	 */
	public MyQuestionData(final Location questionLocation, final String questionString) {
		super(Type.BlankAnswer, questionLocation, questionString);
	}

	/**
	 * Constructor two.
	 * 
	 * @param showQuestion
	 *            Show the question.
	 * @param questionNumber
	 *            The question number.
	 * @param questionStatistics
	 *            The statistics of the question.
	 * @param questionLocation
	 *            The location of the question.
	 * @param question
	 *            The question string.
	 */
	public MyQuestionData(final boolean showQuestion, final int questionNumber, final Statistics questionStatistics, final Location questionLocation,
			final String question) {
		super(Type.BlankAnswer, showQuestion, questionNumber, questionStatistics, questionLocation, question);
	}

}
