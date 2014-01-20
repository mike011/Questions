package ca.charland.questions.data.types;

import java.util.ArrayList;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;

/**
 * The data for a check box question.
 * 
 * @author Michael
 */
public class CheckBox extends AbstractRadioOrCheckBox {

	/**
	 * The answers of the check box question.
	 */
	private final ArrayList<String> _answers;

	/**
	 * Creates a new check box question.
	 * 
	 * @param question
	 *            The question to ask.
	 * @param answers
	 *            The answers to the question.
	 * @param locationOfAnswer
	 *            The location of the answer.
	 * @param options
	 *            The options to look at.
	 */
	public CheckBox(final String question, final ArrayList<String> answers, final Location locationOfAnswer, final ArrayList<String> options) {
		super(Type.CheckBox, locationOfAnswer, question, options);

		_answers = answers;
	}

	/**
	 * Creates a new check box question.
	 * 
	 * @param showQuestion
	 *            To show the the question or not.
	 * @param questionNumber
	 *            The question number too look at.
	 * @param questionStatistics
	 *            The question statistics.
	 * @param question
	 *            The question to ask.
	 * @param answers
	 *            The answers to the question.
	 * @param locationOfAnswer
	 *            The location of the answer.
	 * @param options
	 *            The options to look at.
	 */
	public CheckBox(final boolean showQuestion, final int questionNumber, final Statistics questionStatistics, final String question,
			final ArrayList<String> answers, final Location locationOfAnswer, final ArrayList<String> options) {
		super(Type.CheckBox, showQuestion, questionNumber, questionStatistics, locationOfAnswer, question, options);
		_answers = answers;
	}

	/**
	 * Gets all the answers.
	 * 
	 * @return All the answers.
	 */
	public final ArrayList<String> getAnswers() {
		return this._answers;
	}

	/**
	 * Gets a specific answer.
	 * 
	 * @param x
	 *            The answer to get.
	 * @return The answer.
	 */
	public final String getAnswer(final int x) {
		return this._answers.get(x).toString();
	}

	/**
	 * Gets the number of answers.
	 * 
	 * @return The number of answers.
	 */
	public final int getNumOfAnswers() {
		return _answers.size();
	}

	/**
	 * Gets a new string value.
	 * 
	 * @return A nicely formatted string.
	 */
	@Override
	public final String toString() {
		// String r ="";
		// for(String s: getQuestionDataProperties()) {
		// r+=s+"\n";
		// }
		// return r;
		//		

		String r = super.toString();
		for (final String answer : _answers) {
			r += "Answer = " + answer + "\n";
		}
		return r;
	}

	/**
	 * Gets all the options.
	 * @return All the options.
	 */
	@Override
	final ArrayList<String> getAllOptions() {
		final ArrayList<String> all = new ArrayList<String>();
		all.addAll(getOptions());
		all.addAll(_answers);
		return all;
	}
}
