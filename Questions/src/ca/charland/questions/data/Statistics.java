package ca.charland.questions.data;

/**
 * This class holds the statistics about the question such as how many times it's been answered.
 * 
 * @author Michael
 */
public final class Statistics {

	/**
	 * The minimum difficulty for a question.
	 */
	private static final int MINIMUM_DIFFICULTY = 0;

	/**
	 * For a new question the starting difficulty.
	 */
	public static final int START_DIFFICULTY = 20;

	/**
	 * The maximum difficulty of a question.
	 */
	private static final int MAXIMUM_DIFFICULTY = 30;

	/**
	 * The actual difficulty of the question.
	 */
	private int _difficulty;

	/**
	 * How many times the question has been answered correctly.
	 */
	private int _correctlyAnswered;

	/**
	 * How many times the question has been answered.
	 */
	private int _totalTimesAnswered;

	/**
	 * Creates a new Question Statistics. Sets the difficulty to the <code> startDifficulty </code>, <code> correctlyAnswered </code> to zero,
	 * <code> totalTimesAnswered </code> to zero.
	 */
	public Statistics() {
		_difficulty = START_DIFFICULTY;
		_correctlyAnswered = 0;
		_totalTimesAnswered = 0;
	}

	/**
	 * Creates a new Question Statistics which non default values.
	 * 
	 * @param difficulty
	 *            The difficulty of the question.
	 * @param correctlyAnswered
	 *            The amount of times the question has been correctly answered.
	 * @param totalTimesAnswered
	 *            The amount of times the question has been answered.
	 */
	public Statistics(final int difficulty, final int correctlyAnswered, final int totalTimesAnswered) {
		_difficulty = difficulty;
		_correctlyAnswered = correctlyAnswered;
		_totalTimesAnswered = totalTimesAnswered;
	}

	/**
	 * Gets the difficulty of the question.
	 * 
	 * @return The difficulty of the question.
	 */
	public int getDifficulty() {
		return _difficulty;
	}

	/**
	 * Gets the amount of times the question has been answered correctly.
	 * 
	 * @return The amount of times the question has been correctly answered.
	 */
	public int getCorrectlyAnswered() {
		return _correctlyAnswered;
	}

	/**
	 * Gets the amount of times the question has been answered.
	 * 
	 * @return The amount of times the question has been answered.
	 */
	public int getTotalTimesAnswered() {
		return _totalTimesAnswered;
	}

	/**
	 * Sets the question as being answered correctly.
	 */
	public void setCorrectlyAnswered() {
		++_correctlyAnswered;
		++_totalTimesAnswered;
		--_difficulty;
		if (_difficulty < MINIMUM_DIFFICULTY) {
			_difficulty = MINIMUM_DIFFICULTY;
		}
	}

	/**
	 * Sets the question as being answered incorrectly.
	 */
	public void setIncorrectlyAnswered() {
		++_totalTimesAnswered;
		++_difficulty;
		if (_difficulty > MAXIMUM_DIFFICULTY) {
			_difficulty = MAXIMUM_DIFFICULTY;
		}
	}

	/**
	 * Get how well in percent you've don on this question.
	 * 
	 * @return How well in percent you've don on this question.
	 */
	public int getPercentCorrect() {
		final double correct = _correctlyAnswered;
		final double total = _totalTimesAnswered;
		final double oneHundred = 100d;
		double percent = correct / total * oneHundred;
		if (total == 0d) {
			percent = 0d;
		}
		return (int) percent;
	}

	/**
	 * Returns a nicely formated string of the Question Statistics.
	 * 
	 * @return A nicely formated string of the Question Statistics.
	 */
	@Override
	public String toString() {
		String r = "";
		r += "Difficulty = " + String.valueOf(_difficulty) + "\n";
		r += "Correctly Answered = " + String.valueOf(_correctlyAnswered) + "\n";
		r += "Total Times Answered = " + String.valueOf(_totalTimesAnswered);
		return r;
	}
}
