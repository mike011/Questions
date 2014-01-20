package ca.charland.questions.ui.create;

import javax.swing.JPanel;

import ca.charland.questions.ui.create.types.AbstractCreatePanel;
import ca.charland.questions.ui.create.types.RadioButtonOrCheckBoxPanel;

/**
 * Manages the creation of questions. This is used in swing.
 * 
 * @author Michael
 */
public final class CreateQuestionManager {

	/**
	 * The panel used to create questions.
	 */
	private AbstractCreatePanel _createQuestionPanel;

	/**
	 * Create a Radio Button or Check Box Question.
	 * 
	 * @param topPanel
	 *            The top generic panel.
	 */
	public void createRadioButtonOrCheckBoxQuestion(final AnswerLocationPanel topPanel) {
		_createQuestionPanel = new RadioButtonOrCheckBoxPanel(topPanel);
	}

	/**
	 * The panel.
	 * 
	 * @return The panel.
	 */
	public JPanel getPanel() {
		return _createQuestionPanel.getOptionsPanel();
	}

	/**
	 * Check all the fields.
	 * 
	 * @return If the fields passed the check or not.
	 */
	public String checkFields() {
		return _createQuestionPanel.verifyAllFields();
	}

	/**
	 * Goes on to the next question.
	 */
	public void nextQuestion() {
		_createQuestionPanel.insertQuestion();
	}
}
