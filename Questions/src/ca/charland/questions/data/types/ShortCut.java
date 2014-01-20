package ca.charland.questions.data.types;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;

/**
 * Creates a short cut key type question.
 * 
 * @author Michael
 */
public final class ShortCut extends AbstractQuestion {

	/**
	 * The answer to the question which is a short cut.
	 */
	private String _shortCut;

	/**
	 * Creates a new short cut key question.
	 * 
	 * @param showQuestion
	 *            Whether or not to show the question.
	 * @param questionNumber
	 *            The question number, that uniquely identifies this question.
	 * @param statistics
	 *            The statistics about the question.
	 * @param questionLocation
	 *            Where the question and answer came from.
	 * @param question
	 *            The question to be asked.
	 * @param shortCut
	 *            The answer to the question.
	 */
	public ShortCut(final boolean showQuestion, final int questionNumber, final Statistics statistics, final String question, final String shortCut,
			final Location questionLocation) {
		super(Type.ShortCut, showQuestion, questionNumber, statistics, questionLocation, question);
		_shortCut = shortCut;
	}

	/**
	 * Creates a new short cut key question using the defaults.
	 * 
	 * @param question
	 *            The question to be asked.
	 * @param shortCut
	 *            The answer to the question.
	 * @param answerLocation
	 *            Where the question and answer came from.
	 */
	public ShortCut(final String question, final String shortCut, final Location answerLocation) {
		super(Type.ShortCut, answerLocation, question);
		_shortCut = shortCut;
	}

	/**
	 * Returns the shortcut.
	 * 
	 * @return The shortcut.
	 */
	public String getShortCut() {
		return _shortCut;
	}
}
