package ca.charland.questions.ui.answer.types;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.types.ShortCut;
import ca.charland.questions.data.types.ShortCutKeyListener;
import ca.charland.questions.ui.create.JTextFieldLimit;

/**
 * This class provides the layout for when answering short cut questions.
 * 
 * @author Michael
 * 
 */
public final class ShortCutPanel extends AbstractPanel {

	/**
	 * The width of the field.
	 */
	private static final int FIELD_WIDTH = 64;

	/**
	 * The data of the questions.
	 */
	private ShortCut _shortCut;

	/**
	 * The answer text field.
	 */
	private JTextField _answerTextField;

	/**
	 * Constructs a new instance.
	 * 
	 * @param shortCut
	 *            The data for the questions.
	 *            @param session
	 *            The statistics for this session.
	 */
	public ShortCutPanel(final ShortCut shortCut, final Statistics session) {
		super(shortCut, session);
		_shortCut = shortCut;
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
		_answerTextField.setEditable(false);
		_answerTextField.addKeyListener(new ShortCutKeyListener(_answerTextField));
		springLayout.putConstraint(SpringLayout.WEST, _answerTextField, 5, SpringLayout.WEST, jPanel);
		springLayout.putConstraint(SpringLayout.NORTH, _answerTextField, 35, SpringLayout.NORTH, jPanel);
		jPanel.add(_answerTextField);

		JButton clear = new JButton("Clear");
		springLayout.putConstraint(SpringLayout.WEST, clear, 5, SpringLayout.EAST, _answerTextField);
		springLayout.putConstraint(SpringLayout.NORTH, clear, -1, SpringLayout.NORTH, _answerTextField);
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				_answerTextField.setText("");
			}
		});
		jPanel.add(clear);

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
		final String actual = _shortCut.getShortCut();
		if (answered.equalsIgnoreCase(actual)) {
			result = true;
			_shortCut.setCorrectlyAnswered();
			_session.setCorrectlyAnswered();
			setAnswerLabel("CORRECT");
		} else {
			_shortCut.setIncorrectlyAnswered();
			_session.setIncorrectlyAnswered();
			setAnswerLabel("INCORRECT");
		}

		this.updatePanel();

		return result;
	}
}
