package ca.charland.questions.ui.answer.types;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.types.BlankAnswer;
import ca.charland.questions.ui.create.JTextFieldLimit;

/**
 * This class provides the layout for when answering check box questions.
 * 
 * @author Michael
 */
public final class BlankAnswerPanel extends AbstractPanel {

	/**
	 * The width of the field.
	 */
	private static final int FIELD_WIDTH = 84;

	/**
	 * The data of the questions.
	 */
	private BlankAnswer _blankAnswer;

	/**
	 * The answer text field.
	 */
	private JTextField _answerTextField;

	/**
	 * Constructs a new instance.
	 * 
	 * @param blankAnswer
	 *            The data for the questions.
	 * @param session
	 *            The statistics for this session.
	 */
	public BlankAnswerPanel(final BlankAnswer blankAnswer, final Statistics session) {
		super(blankAnswer, session);
		_blankAnswer = blankAnswer;
		_springLayout = new SpringLayout();
		_questionPanel = new JPanel(_springLayout);
		this.addPanel();
	}

	/**
	 * Adds the middle panel which contains all the question data.
	 */
	@Override
	protected void addMiddlePanel() {
		_middle = createBlankAnswerQuestion();
		_springLayout.putConstraint(SpringLayout.NORTH, _middle, -25, SpringLayout.SOUTH, _top);
		_questionPanel.add(_middle);
	}

	/**
	 * Creates the panel for the check box question.
	 * 
	 * @return The panel of the check box question.
	 */
	private JPanel createBlankAnswerQuestion() {
		final SpringLayout springLayout = new SpringLayout();
		final JPanel jPanel = new JPanel(springLayout);

		_answerTextField = new JTextField(FIELD_WIDTH);
		_answerTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		final Font fieldFont = new Font("Courier", Font.PLAIN, 12);
		_answerTextField.setFont(fieldFont);
		springLayout.putConstraint(SpringLayout.EAST, _answerTextField, -3, SpringLayout.EAST, jPanel);
		springLayout.putConstraint(SpringLayout.NORTH, _answerTextField, 35, SpringLayout.NORTH, jPanel);
		jPanel.add(_answerTextField);

		// The whole panel.
		final JLabel temp = new JLabel();
		springLayout.putConstraint(SpringLayout.EAST, jPanel, 600, SpringLayout.WEST, temp);
		springLayout.putConstraint(SpringLayout.SOUTH, jPanel, 75, SpringLayout.SOUTH, temp);

		jPanel.setOpaque(true);

		return jPanel;
	}

	/**
	 * Checks if the question is correct or not.
	 * 
	 * @return If the question is correct or not.
	 */
	@Override
	public boolean isAnswerCorrect() {
		boolean result = false;

		final String answered = _answerTextField.getText();
		final String actual = _blankAnswer.getAnswer();
		if (answered.equalsIgnoreCase(actual)) {
			result = true;
			_blankAnswer.setCorrectlyAnswered();
			_session.setCorrectlyAnswered();
			setAnswerLabel("CORRECT");
		} else {
			_blankAnswer.setIncorrectlyAnswered();
			_session.setIncorrectlyAnswered();
			setAnswerLabel("INCORRECT");
		}

		this.updatePanel();

		return result;
	}
}
