package ca.charland.questions.ui.create.types;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.types.FillInTheBlanks;
import ca.charland.questions.database.QuestionsDatabase;
import ca.charland.questions.ui.create.AnswerLocationPanel;
import ca.charland.questions.ui.create.JTextFieldLimit;
import ca.charland.questions.utilities.StringUtilities;

/**
 * Creates a fill in the blanks panel.
 * 
 * @author Michael
 */
public final class FillInTheBlanksPanel extends AbstractCreatePanel implements ActionListener {

	/**
	 * Generate label.
	 */
	static final String LABEL_GENERATE = "Generate";

	/**
	 * The text field of the statement.
	 */
	private JTextField _statementTextField;

	/**
	 * The key words of the question.
	 */
	private ArrayList<JTextField> _blankTextFields;

	/**
	 * The output of when generating a question.
	 */
	private JLabel _generateOutput;

	/**
	 * Creates a new fill in the blank panel.
	 * 
	 * @param topPanel
	 *            The top part of the panel.
	 */
	public FillInTheBlanksPanel(final AnswerLocationPanel topPanel) {
		this.setTopPanel(topPanel);
		this.createOptionsPanel();
	}

	/**
	 * Creates a new panel to add the questions.
	 */
	protected void createOptionsPanel() {
		SpringLayout optionsPanelSpringLayout = new SpringLayout();
		_optionsPanel = new JPanel(optionsPanelSpringLayout);

		// Title
		Border titled = BorderFactory.createTitledBorder("");
		_optionsPanel.setBorder(titled);

		Font fieldFont = new Font("Courier", Font.PLAIN, 12);

		final JLabel title = new JLabel("Create a Fill In The Blanks Question.");
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, title, MINIMUM_SPACING, SpringLayout.WEST, _optionsPanel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, title, 0, SpringLayout.NORTH, _optionsPanel);
		_optionsPanel.add(title);

		// Statement
		final JLabel statementLabel = new JLabel("Statement:");
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, statementLabel, MINIMUM_SPACING, SpringLayout.WEST, _optionsPanel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, statementLabel, 15, SpringLayout.SOUTH, title);
		_optionsPanel.add(statementLabel);

		_statementTextField = new JTextField(FIELD_WIDTH);
		_statementTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_statementTextField.setFont(fieldFont);
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, _statementTextField, 15, SpringLayout.EAST, statementLabel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, _statementTextField, 0, SpringLayout.NORTH, statementLabel);
		_optionsPanel.add(_statementTextField);

		_blankTextFields = new ArrayList<JTextField>();

		final int maxKeys = 5;
		for (int x = 0; x < maxKeys; ++x) {
			final JLabel keyWordLabel = new JLabel("Blank #" + x + ":");
			optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, keyWordLabel, 0, SpringLayout.WEST, statementLabel);
			optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, keyWordLabel, (x + 1) * 25, SpringLayout.SOUTH, statementLabel);
			_optionsPanel.add(keyWordLabel);

			JTextField keyWordTextField = new JTextField(FIELD_WIDTH);
			keyWordTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
			keyWordTextField.setFont(fieldFont);
			optionsPanelSpringLayout.putConstraint(SpringLayout.EAST, keyWordTextField, 0, SpringLayout.EAST, _statementTextField);
			optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, keyWordTextField, 0, SpringLayout.NORTH, keyWordLabel);
			_optionsPanel.add(keyWordTextField);
			_blankTextFields.add(keyWordTextField);
		}

		final JButton generate = new JButton(LABEL_GENERATE);
		generate.addActionListener(this);
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, generate, 15, SpringLayout.EAST, statementLabel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, generate, 10, SpringLayout.SOUTH, _blankTextFields
				.get(_blankTextFields.size() - 1));
		_optionsPanel.add(generate);

		_generateOutput = new JLabel();
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, _generateOutput, 15, SpringLayout.EAST, statementLabel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, _generateOutput, 10, SpringLayout.SOUTH, generate);
		_optionsPanel.add(_generateOutput);

		JLabel temp = new JLabel();
		optionsPanelSpringLayout.putConstraint(SpringLayout.EAST, _optionsPanel, SCREEN_WIDTH - 20, SpringLayout.EAST, temp);
		optionsPanelSpringLayout.putConstraint(SpringLayout.SOUTH, _optionsPanel, 330, SpringLayout.SOUTH, temp);
	}

	/**
	 * Verifies that all the fields inserted are correct.
	 * 
	 * @return If the test passed or not.
	 */
	@Override
	public String verifyAllFields() {
		String result = "";
		result += _answerLocationPanel.verifyFields();
		result += verifyFields();
		return result;
	}

	/**
	 * Verifies the fields of this question are correct.
	 * 
	 * @return If the data is corrected or not.
	 */
	public String verifyFields() {
		String result = "";

		final String statement = _statementTextField.getText();
		boolean valid = true;
		if (statement.isEmpty()) {
			result += " No statement was entered.";
			valid = false;
		}

		if (getBlanks(_blankTextFields).size() == 0) {
			result += " Blank field needs to be filled in.";
			valid = false;
		}

		if (valid) {

			final ArrayList<String> splits = new ArrayList<String>();
			for (String split : statement.split(" ")) {
				splits.add(split);
			}

			final ArrayList<String> cleanedWords = StringUtilities.checkFields(splits);

			for (final String blank : getBlanks(_blankTextFields)) {
				int found = 0;

				for (final String cleanedWord : cleanedWords) {
					if (cleanedWord.equalsIgnoreCase(blank)) {
						++found;
					}
				}
				if (found == 0) {
					result += " " + blank + " not found.";
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Gets the blanks from the text fields.
	 * 
	 * @param textFields
	 *            The text fields to look at.
	 * @return The options.
	 */
	private static ArrayList<String> getBlanks(final ArrayList<JTextField> textFields) {
		final ArrayList<String> optionsList = new ArrayList<String>();
		for (JTextField textField : textFields) {
			final String text = textField.getText();
			if (!text.isEmpty()) {
				optionsList.add(textField.getText());
			}
		}
		return StringUtilities.checkFields(optionsList);
	}

	/**
	 * Sets the fields to being blank.
	 */
	private void resetFields() {
		_answerLocationPanel.setFields();
		_statementTextField.setText("");
		for (int x = 0; x < _blankTextFields.size(); x++) {
			_blankTextFields.get(x).setText("");
		}
	}

	/**
	 * Goes to the next question to insert.
	 * 
	 * @return The question number.
	 */
	@Override
	public int insertQuestion() {

		// Gets the question information.
		final Location answerLocation = _answerLocationPanel.getAnswerLocation();
		final String question = _statementTextField.getText();
		final ArrayList<String> blanks = getBlanks(_blankTextFields);

		// Creates the question.
		final FillInTheBlanks questionData = new FillInTheBlanks(question, blanks, answerLocation);

		final QuestionsDatabase database = new QuestionsDatabase();
		final int questionNumber = database.insertQuestion(questionData);

		// Empty out the none keep string check text fields.
		resetFields();

		return questionNumber;
	}

	/**
	 * What to do when the generate button is pressed.
	 * 
	 * @param e
	 *            The action.
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		if (LABEL_GENERATE.equals(e.getActionCommand())) {
			ArrayList<String> possibleWords = StringUtilities.generate(_statementTextField.getText());

			final int newBlanks = Math.min(possibleWords.size(), _blankTextFields.size());
			final ArrayList<String> blanks = new ArrayList<String>();
			for (int x = 0; x < newBlanks; x++) {
				blanks.add(possibleWords.get(x));
				_blankTextFields.get(x).setText(possibleWords.get(x));
			}

			_generateOutput.setText(verifyFields());
		}
	}
}
