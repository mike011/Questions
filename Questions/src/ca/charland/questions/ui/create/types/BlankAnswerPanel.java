package ca.charland.questions.ui.create.types;

import java.awt.Font;
import javax.swing.BorderFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.types.BlankAnswer;
import ca.charland.questions.database.QuestionsDatabase;
import ca.charland.questions.ui.create.AnswerLocationPanel;
import ca.charland.questions.ui.create.JTextFieldLimit;

/**
 * Creates a new blank answer panel.
 * 
 * @author Michael
 *
 */
public final class BlankAnswerPanel extends AbstractCreatePanel {

	/**
	 * The question test field.
	 */
	private JTextField _questionTextField;

	/**
	 * The answer text field.
	 */
	private JTextField _answerTextField;

	/**
	 * Creates a new blank answer panel.
	 * @param topPanel The top panel.
	 */
	public BlankAnswerPanel(final AnswerLocationPanel topPanel) {
		this.setTopPanel(topPanel);
		this.createOptionsPanel();
	}

	/**
	 * Creates the options panel.
	 */
	protected void createOptionsPanel() {
		SpringLayout optionsPanelSpringLayout = new SpringLayout();
		_optionsPanel = new JPanel(optionsPanelSpringLayout);

		// Options title
		Border titled = BorderFactory.createTitledBorder("");
		_optionsPanel.setBorder(titled);

		final Font fieldFont = new Font("Courier", Font.PLAIN, 12);

		final JLabel title = new JLabel("Create a Blank Answer Question.");
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, title, MINIMUM_SPACING, SpringLayout.WEST, _optionsPanel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, title, 0, SpringLayout.NORTH, _optionsPanel);
		_optionsPanel.add(title);

		// Question
		final JLabel questionLabel = new JLabel("Question: ");
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, questionLabel, MINIMUM_SPACING, SpringLayout.WEST, _optionsPanel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, questionLabel, 15, SpringLayout.SOUTH, title);
		_optionsPanel.add(questionLabel);

		_questionTextField = new JTextField(FIELD_WIDTH);
		_questionTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_questionTextField.setFont(fieldFont);
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, _questionTextField, MINIMUM_SPACING, SpringLayout.EAST, questionLabel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, _questionTextField, 0, SpringLayout.NORTH, questionLabel);
		_optionsPanel.add(_questionTextField);

		// Answer
		final JLabel answerLabel = new JLabel("Answer: ");
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, answerLabel, 0, SpringLayout.WEST, questionLabel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, answerLabel, 25, SpringLayout.SOUTH, questionLabel);
		_optionsPanel.add(answerLabel);

		_answerTextField = new JTextField(FIELD_WIDTH);
		_answerTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_answerTextField.setFont(fieldFont);
		optionsPanelSpringLayout.putConstraint(SpringLayout.EAST, _answerTextField, 0, SpringLayout.EAST, _questionTextField);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, _answerTextField, 0, SpringLayout.NORTH, answerLabel);
		_optionsPanel.add(_answerTextField);

		final JLabel temp = new JLabel();
		optionsPanelSpringLayout.putConstraint(SpringLayout.EAST, _optionsPanel, SCREEN_WIDTH - 20, SpringLayout.EAST, temp);
		optionsPanelSpringLayout.putConstraint(SpringLayout.SOUTH, _optionsPanel, 330, SpringLayout.SOUTH, temp);
	}

	/**
	 * Verifies that fields are correct.
	 * @return If they are valid or not.
	 */
	public String verifyAllFields() {
		String result = "";
		result += _answerLocationPanel.verifyFields();

		if (_questionTextField.getText().equals("")) {
			result += " Question field needs to be filled in.";
		}

		return result;
	}

	/**
	 * Resets the fields to blanks.
	 */
	private void setFields() {
		// update values based on the keep string check box
		_answerLocationPanel.setFields();
		_questionTextField.setText("");
		_answerTextField.setText("");
	}

	/**
	 * Inserts a question into the database.
	 * @return The question number.
	 */
	@Override
	public int insertQuestion() {

		// write the question;
		final Location answerLocation = _answerLocationPanel.getAnswerLocation();
		final String question = _questionTextField.getText();
		final String answer = _answerTextField.getText();

		// create question
		final BlankAnswer questionData = new BlankAnswer(question, answer, answerLocation);

		final QuestionsDatabase database = new QuestionsDatabase();
		final int questionNumber = database.insertQuestion(questionData);

		// empty out the none keep string check text fields
		this.setFields();

		return questionNumber;
	}
}
