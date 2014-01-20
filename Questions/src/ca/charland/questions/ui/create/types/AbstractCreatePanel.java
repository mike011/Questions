package ca.charland.questions.ui.create.types;

import javax.swing.JPanel;

import ca.charland.questions.ui.create.AnswerLocationPanel;

/**
 * Create a panel.
 * @author Michael
 *
 */
public abstract class AbstractCreatePanel {

	/**
	 * The default screen width.
	 */
	protected static final int SCREEN_WIDTH = 855;

	/**
	 * The default screen height.
	 */
	protected static final int SCREEN_HEIGHT = 555;

	/**
	 * The maximum number of answers that are possible.
	 */
	protected static final int MAX_NUMBER_OF_ANSWERS = 10;

	/**
	 * The spacing between the components.
	 */
	protected static final int MINIMUM_SPACING = 5;

	/**
	 * The width of the field.
	 */
	protected static final int FIELD_WIDTH = 95;

	/**
	 * The where the answer is coming from panel.
	 */
	protected AnswerLocationPanel _answerLocationPanel;

	/**
	 * The options panel.
	 */
	protected JPanel _optionsPanel;

	/**
	 * Abstract method used to create options panels.
	 */
	protected abstract void createOptionsPanel();

	/**
	 * Sets the top panel.
	 * @param answerLocationPanel The panel to be set.
	 */
	public final void setTopPanel(final AnswerLocationPanel answerLocationPanel) {
		this._answerLocationPanel = answerLocationPanel;
	}

	/**
	 * Gets the options panel.
	 * @return The options panel.
	 */
	public final JPanel getOptionsPanel() {
		return _optionsPanel;
	}

	/**
	 * Verifies if the fields are correct.
	 * @return No clue what this is.
	 */
	public abstract String verifyAllFields();

	/**
	 * Inserts the question into the storage.
	 * @return the question number.
	 */
	public abstract int insertQuestion();
}
