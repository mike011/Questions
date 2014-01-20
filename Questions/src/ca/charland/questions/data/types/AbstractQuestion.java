package ca.charland.questions.data.types;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;

/**
 * This is the base class for the information about the question.
 * @author Michael
 *
 */
public abstract class AbstractQuestion {

	/**
	 * The type of question.
	 */
	private Type _type;

	/**
	 * To show the questions or not.
	 */
	private boolean _showQuestion;

	/**
	 * Where the question came from.
	 */
	private final Location _questionLocation;

	/**
	 * The statistics of the questions.
	 */
	private final Statistics _statistics;

	/**
	 * Which question in the list of questions.
	 */
	private final int _questionNumber;

	/**
	 * The string of the question.
	 */
	private String _questionToAsk;

	/**
	 * Constructor used for create questions.
	 * @param type The type of question to create.
	 * @param questionLocation Where the question came from.
	 * @param questionString The question.
	 */
	public AbstractQuestion(final Type type, final Location questionLocation, final String questionString) {
		_type = type;
		_showQuestion = true;
		_statistics = new Statistics();
		_questionNumber = 0;
		_questionToAsk = questionString;
		_questionLocation = questionLocation;
	}

	/**
	 * Constructor.
	 * @param showQuestion To show the question or not.
	 * @param type The type of question to create.
	 * @param questionNumber Which question in the list of the questions.
	 * @param statistics The statistics of the questions.
	 * @param questionLocation Where the question came from.
	 * @param question The question.
	 */
	public AbstractQuestion(final Type type, final boolean showQuestion, final int questionNumber,
			final Statistics statistics,
			final Location questionLocation, final String question) {
		_type = type;
		_showQuestion = true;
		_showQuestion = showQuestion;
		_questionNumber = questionNumber;
		_statistics = statistics;
		_questionToAsk = question;
		_questionLocation = questionLocation;
	}

	/**
	 * Gets the type of question.
	 * @return The type of the question.
	 */
	public final Type getType() {
		return _type;
	}

	/**
	 * Gets the question.
	 * @return The question.
	 */
	public String getQuestionString() {
		return _questionToAsk;
	}

	/**
	 * Gets if to show the question or not.
	 * @return To show the question or not.
	 */
	public final boolean getShowQuestion() {
		return _showQuestion;
	}

	/**
	 * Sets to show the question.
	 *
	 */
	public final void setShowQuestion() {
		_showQuestion = true;
	}

	/**
	 * Gets the question number.
	 * @return The number of the question.
	 */
	public final int getQuestionNumber() {
		return _questionNumber;
	}

	/**
	 * Gets the difficulty of the question.
	 * @return the difficulty of the question.
	 */
	public final int getDifficulty() {
		assert _statistics != null : "Question statistics is null";
		return _statistics.getDifficulty();
	}

	/**
	 * Gets the amount of times the question has been answered correctly. 
	 * @return The amount of times the question has been answered correctly.
	 */
	public final int getCorrectlyAnswered() {
		assert _statistics != null : "Question statistics is null";
		return _statistics.getCorrectlyAnswered();
	}

	/**
	 * Gets the total amount of times the question has been answered.
	 * @return The total amount of times the question has been answered.
	 */
	public final int getTotalTimesAnswered() {
		assert _statistics != null : "Question statistics is null";
		return _statistics.getTotalTimesAnswered();
	}

	/**
	 * Sets the amount of times the question has been correctly answered.
	 *
	*/
	public final void setCorrectlyAnswered() {
		assert _statistics != null : "Question statistics is null";
		_statistics.setCorrectlyAnswered();
	}

	/**
	 * Sets the amount of times the question has been incorrectly answered.
	 */
	public final void setIncorrectlyAnswered() {
		assert _statistics != null : "Question statistics is null";
		_statistics.setIncorrectlyAnswered();
	}

	/**
	 * Gets the how well you've done on the question in percent form.
	 * @return Gets the how well you've done on the question in percent form.
	 */
	public final int getPercentCorrect() {
		assert _statistics != null : "Question statistics is null";
		return _statistics.getPercentCorrect();
	}

	/**
	 * Gets the location of the question.
	 * @return Gets the location of the question.
	 */
	public final Location getLocation() {
		assert _questionLocation != null : "Question location is null";
		return _questionLocation;
	}
}
