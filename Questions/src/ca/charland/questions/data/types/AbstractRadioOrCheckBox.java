package ca.charland.questions.data.types;

import java.util.ArrayList;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;

/**
 * The abstract radio or check box question.
 * 
 * @author Michael
 */
public abstract class AbstractRadioOrCheckBox extends AbstractQuestion {

	/**
	 * The options for the questions.
	 */
	private final ArrayList<String> _options;

	/**
	 * Simple Constructor.
	 * 
	 * @param type
	 *            The type of question to create.
	 * @param locationOfAnswer
	 *            The location of where the answer can be found.
	 * @param question
	 *            The question to be asked.
	 * @param options
	 *            The options for the question including teh answer.
	 */
	public AbstractRadioOrCheckBox(final Type type, final Location locationOfAnswer, final String question, final ArrayList<String> options) {
		super(type, locationOfAnswer, question);
		_options = options;
	}

	/**
	 * All out constructor.
	 * 
	 * @param type
	 *            The type of question to create.
	 * @param showQuestion
	 *            To display the question or not.
	 * @param questionNumber
	 *            The number of the question.
	 * @param questionStatistics
	 *            How well you've done on the question.
	 * @param locationOfAnswer
	 *            Where the answer can be found.
	 * @param question
	 *            The question to be asked.
	 * @param options
	 *            The options for the question including teh answer.
	 */
	public AbstractRadioOrCheckBox(final Type type, final boolean showQuestion, final int questionNumber, final Statistics questionStatistics,
			final Location locationOfAnswer, final String question, final ArrayList<String> options) {
		super(type, showQuestion, questionNumber, questionStatistics, locationOfAnswer, question);
		_options = options;
	}

	/**
	 * Get the options.
	 * 
	 * @return The options.
	 */
	public final ArrayList<String> getOptions() {
		return _options;
	}

	/**
	 * Get all the options of the question including answer.
	 * 
	 * @return All the options of the question including answer.
	 */
	abstract ArrayList<String> getAllOptions();

	/**
	 * Get a specific option.
	 * 
	 * @param x
	 *            The option to get.
	 * @return The option.
	 */
	public final String getAllOption(final int x) {
		return getAllOptions().get(x).toString();
	}

	/**
	 * The amount of options.
	 * 
	 * @return The amount of options.
	 */
	public final int getNumOfAllOptions() {
		return getAllOptions().size();
	}

	/**
	 * Gets a nice string value.
	 * @return A nice string value.
	 */
	@Override
	public String toString() {
		String r = super.toString();
		for (final String option : _options) {
			r += "Option = " + option + "\n";
		}
		return r;
	}
}
