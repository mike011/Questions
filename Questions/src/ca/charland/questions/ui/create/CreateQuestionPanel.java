package ca.charland.questions.ui.create;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import ca.charland.questions.ui.create.types.AbstractCreatePanel;
import ca.charland.questions.ui.create.types.BlankAnswerPanel;
import ca.charland.questions.ui.create.types.FillInTheBlanksPanel;
import ca.charland.questions.ui.create.types.RadioButtonOrCheckBoxPanel;
import ca.charland.questions.ui.create.types.ShortCutPanel;

/**
 * Creates a question panel that is used when creating fill in the blank style questions.
 * 
 * @author Michael
 */
public final class CreateQuestionPanel extends JPanel implements ActionListener, CreateQuestionPanelInterface {

	/**
	 * Used for threading.
	 */
	private static final long serialVersionUID = -838607655700023213L;

	/**
	 * The type of layout used with the screen.
	 */
	private SpringLayout _springLayout;

	/**
	 * The label for the status.
	 */
	private JLabel _statusLabel;

	/**
	 * The panel that covers the top part of the screen.
	 */
	private AnswerLocationPanel _topPanel;

	/**
	 * The label that allows you too choose the question type you want to create.
	 */
	private JLabel _chooseLabel;

	/**
	 * A button to pick that you want to create a radio or check box question type.
	 */
	private JButton _radioOrCheckBoxButton;

	/**
	 * A button to pick that you want to create a blank answer question type.
	 */
	private JButton _blankAnswerButton;

	/**
	 * A button to pick that you want to create fill in the blanks question type.
	 */
	private JButton _fillInTheBlanksButton;

	/**
	 * A button to pick that you want to create a short cut question type.
	 */
	private JButton _shortCutButton;

	/**
	 * Used when a creating a specific type of questions.
	 */
	private AbstractCreatePanel _answerCreatePanel;

	/**
	 * Create a new instance.
	 */
	public CreateQuestionPanel() {
		this.createQuestions();
	}

	/**
	 * Create a new question form that will be used to fill in the data for new questions.
	 */
	protected void createQuestions() {
		_springLayout = new SpringLayout();
		this.setLayout(_springLayout);

		_topPanel = new AnswerLocationPanel();
		_springLayout.putConstraint(SpringLayout.WEST, _topPanel, MINIMUM_SPACING, SpringLayout.WEST, this);
		_springLayout.putConstraint(SpringLayout.NORTH, _topPanel, 15, SpringLayout.NORTH, this);
		this.add(_topPanel);

		_chooseLabel = new JLabel(LABEL_CHOOSE_TYPE);
		_springLayout.putConstraint(SpringLayout.WEST, _chooseLabel, MINIMUM_SPACING, SpringLayout.WEST, this);
		_springLayout.putConstraint(SpringLayout.NORTH, _chooseLabel, MINIMUM_SPACING, SpringLayout.SOUTH, _topPanel);
		this.add(_chooseLabel);

		_radioOrCheckBoxButton = new JButton(LABEL_RADIO_BUTTON_OR_CHECK_BOX);
		_springLayout.putConstraint(SpringLayout.WEST, _radioOrCheckBoxButton, MINIMUM_SPACING, SpringLayout.EAST, _chooseLabel);
		_springLayout.putConstraint(SpringLayout.NORTH, _radioOrCheckBoxButton, 2, SpringLayout.SOUTH, _topPanel);
		_radioOrCheckBoxButton.addActionListener(this);
		this.add(_radioOrCheckBoxButton);

		_blankAnswerButton = new JButton(LABEL_BLANK_ANSWER);
		_springLayout.putConstraint(SpringLayout.WEST, _blankAnswerButton, MINIMUM_SPACING, SpringLayout.EAST, _radioOrCheckBoxButton);
		_springLayout.putConstraint(SpringLayout.NORTH, _blankAnswerButton, 2, SpringLayout.SOUTH, _topPanel);
		_blankAnswerButton.addActionListener(this);
		this.add(_blankAnswerButton);

		_fillInTheBlanksButton = new JButton(LABEL_FILL_IN_THE_BLANKS);
		_springLayout.putConstraint(SpringLayout.WEST, _fillInTheBlanksButton, MINIMUM_SPACING, SpringLayout.EAST, _blankAnswerButton);
		_springLayout.putConstraint(SpringLayout.NORTH, _fillInTheBlanksButton, 2, SpringLayout.SOUTH, _topPanel);
		_fillInTheBlanksButton.addActionListener(this);
		this.add(_fillInTheBlanksButton);

		_shortCutButton = new JButton(LABEL_SHORT_CUT);
		_springLayout.putConstraint(SpringLayout.WEST, _shortCutButton, MINIMUM_SPACING, SpringLayout.EAST, _fillInTheBlanksButton);
		_springLayout.putConstraint(SpringLayout.NORTH, _shortCutButton, 2, SpringLayout.SOUTH, _topPanel);
		_shortCutButton.addActionListener(this);
		this.add(_shortCutButton);

		// bottom part of the panel;
		final JButton submitButton = new JButton(LABEL_SUBMIT);
		_springLayout.putConstraint(SpringLayout.WEST, submitButton, MINIMUM_SPACING, SpringLayout.WEST, this);
		_springLayout.putConstraint(SpringLayout.SOUTH, submitButton, SCREEN_HEIGHT - MINIMUM_SPACING, SpringLayout.NORTH, this);
		submitButton.addActionListener(this);
		this.add(submitButton);

		_statusLabel = new JLabel("");
		_springLayout.putConstraint(SpringLayout.WEST, _statusLabel, MINIMUM_SPACING, SpringLayout.EAST, submitButton);
		_springLayout.putConstraint(SpringLayout.SOUTH, _statusLabel, SCREEN_HEIGHT - 7, SpringLayout.NORTH, this);
		this.add(_statusLabel);

		final JLabel temp = new JLabel();
		_springLayout.putConstraint(SpringLayout.EAST, this, SCREEN_WIDTH, SpringLayout.EAST, temp);
		_springLayout.putConstraint(SpringLayout.SOUTH, this, SCREEN_HEIGHT, SpringLayout.SOUTH, temp);

	}

	/**
	 * Handles when the buttons are clicked such as adding a radio or check box and submit buttons.
	 * 
	 * @param e
	 *            The action that was performed. Triggered by the mouse.
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		if (LABEL_RADIO_BUTTON_OR_CHECK_BOX.equals(e.getActionCommand())) {
			setEnable(false);

			_answerCreatePanel = new RadioButtonOrCheckBoxPanel(_topPanel);
			final JPanel panel = _answerCreatePanel.getOptionsPanel();
			_springLayout.putConstraint(SpringLayout.WEST, panel, MINIMUM_SPACING, SpringLayout.WEST, this);
			_springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.SOUTH, _chooseLabel);
			this.add(panel);
			this.updateUI();

		} else if (LABEL_BLANK_ANSWER.equals(e.getActionCommand())) {
			setEnable(false);

			_answerCreatePanel = new BlankAnswerPanel(_topPanel);
			final JPanel panel = _answerCreatePanel.getOptionsPanel();
			_springLayout.putConstraint(SpringLayout.WEST, panel, MINIMUM_SPACING, SpringLayout.WEST, this);
			_springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.SOUTH, _chooseLabel);
			this.add(panel);
			this.updateUI();

		} else if (LABEL_FILL_IN_THE_BLANKS.equals(e.getActionCommand())) {
			setEnable(false);

			_answerCreatePanel = new FillInTheBlanksPanel(_topPanel);
			final JPanel panel = _answerCreatePanel.getOptionsPanel();
			_springLayout.putConstraint(SpringLayout.WEST, panel, MINIMUM_SPACING, SpringLayout.WEST, this);
			_springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.SOUTH, _chooseLabel);
			this.add(panel);
			this.updateUI();

		} else if (LABEL_SHORT_CUT.equals(e.getActionCommand())) {
			setEnable(false);
			_answerCreatePanel = new ShortCutPanel(_topPanel);
			final JPanel panel = _answerCreatePanel.getOptionsPanel();
			_springLayout.putConstraint(SpringLayout.WEST, panel, MINIMUM_SPACING, SpringLayout.WEST, this);
			_springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.SOUTH, _chooseLabel);
			this.add(panel);
			this.updateUI();
		} else if (LABEL_SUBMIT.equals(e.getActionCommand())) {
			final String error = _answerCreatePanel.verifyAllFields();

			if (error.length() > 0) {
				_statusLabel.setForeground(Color.RED);
				_statusLabel.setText(error);
			} else {
				// Inserts the question into storage.
				final int questionNumber = _answerCreatePanel.insertQuestion();

				_statusLabel.setForeground(Color.BLACK);
				_statusLabel.setText("Successfully inserted question number " + questionNumber);

				this.remove(_answerCreatePanel.getOptionsPanel());
				this.updateUI();
				setEnable(true);
			}
		}
	}

	/**
	 * Disable all the buttons and resets the status label if the buttons have been disabled.
	 * 
	 * @param enable
	 *            or disable all the buttons.
	 */
	private void setEnable(final boolean enable) {
		_radioOrCheckBoxButton.setEnabled(enable);
		_blankAnswerButton.setEnabled(enable);
		_fillInTheBlanksButton.setEnabled(enable);
		_shortCutButton.setEnabled(enable);
		if (!enable) {
			_statusLabel.setText("");
		}
	}
}
