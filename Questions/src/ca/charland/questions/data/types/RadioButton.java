package ca.charland.questions.data.types;

import java.util.ArrayList;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;

/**
 * Creates the data for a radio button type question.
 * 
 * @author Michael
 */
public class RadioButton extends AbstractRadioOrCheckBox {

	/**
	 * The answer to the radio or check box question.
	 */
	private final String _answer;

	/**
	 * Simple Constructor.
	 * 
	 * @param question
	 *            The question to be asked.
	 * @param answer
	 *            The answer to the question.
	 * @param locationOfAnswer
	 *            Where the answer to the question could be found.
	 * @param options
	 *            Options for the question including the answer for the question.
	 */
	public RadioButton(final String question, final String answer, final Location locationOfAnswer, final ArrayList<String> options) {
		super(Type.RadioButton, locationOfAnswer, question, options);
		_answer = answer;
	}

	/**
	 * Full blown constructor.
	 * 
	 * @param showQuestion
	 *            To show the question or not.
	 * @param questionNumber
	 *            The number of the question.
	 * @param questionStatistics
	 *            How well you've done on the question.
	 * @param question
	 *            The question to be asked.
	 * @param answer
	 *            The answer of the question.
	 * @param locationOfAnswer
	 *            Where the answer to the question could be found.
	 * @param options
	 *            Options for the question including the answer for the question.
	 */
	public RadioButton(final boolean showQuestion, final int questionNumber, final Statistics questionStatistics, final String question,
			final String answer, final Location locationOfAnswer, final ArrayList<String> options) {
		super(Type.RadioButton, showQuestion, questionNumber, questionStatistics, locationOfAnswer, question, options);
		_answer = answer;
	}

	/**
	 * Returns the answer to the Question.
	 * 
	 * @return The answer to the Question.
	 */
	public final String getAnswer() {
		return _answer;
	}

	/**
	 * Prints the question.
	 * 
	 * @return A nicely formatted string.
	 */
	@Override
	public final String toString() {
		String result = super.toString();
		result += "Answer = " + _answer;
		return result;
	}

	/**
	 * Returns all the questions and answers in one list.
	 * 
	 * @return All the questions and answers in one list.
	 */
	@Override
	public final ArrayList<String> getAllOptions() {
		final ArrayList<String> all = new ArrayList<String>();
		all.addAll(getOptions());
		all.add(_answer);
		return all;
	}
}
