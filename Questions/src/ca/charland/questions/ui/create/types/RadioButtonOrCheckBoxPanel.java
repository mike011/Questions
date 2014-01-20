package ca.charland.questions.ui.create.types;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.types.CheckBox;
import ca.charland.questions.data.types.RadioButton;
import ca.charland.questions.database.QuestionsDatabase;
import ca.charland.questions.ui.create.AnswerLocationPanel;
import ca.charland.questions.ui.create.JTextFieldLimit; // import ca.charland.questions.utilities.WriteCheckBoxQuestions;
// import ca.charland.questions.utilities.WriteRadioButtonQuestions;


/**
 * To create a radio or check box panel.
 * 
 * @author Michael
 */
public final class RadioButtonOrCheckBoxPanel extends AbstractCreatePanel {

	/**
	 * The text fields.
	 */
	private JTextField _questionTextField;

	/**
	 * The options.
	 */
	private JTextField[] _optionTextFields;

	/**
	 * The options options.
	 */
	private JComboBox[] _optionComboBoxes;

	/**
	 * The check boxes.
	 */
	private JCheckBox _questionCheckBox;

	/**
	 * The options check box.
	 */
	private JCheckBox[] _optionCheckBoxes;

	/**
	 * Creates a new panel.
	 * 
	 * @param topPanel
	 *            The top part of the panel.
	 */
	public RadioButtonOrCheckBoxPanel(final AnswerLocationPanel topPanel) {
		setTopPanel(topPanel);
		createOptionsPanel();
	}

	/**
	 * Creates the options panel.
	 */
	protected void createOptionsPanel() {
		final SpringLayout optionsPanelSpringLayout = new SpringLayout();

		_optionsPanel = new JPanel(optionsPanelSpringLayout);

		final JLabel title = new JLabel("Create a Radio Button or Check Box Question.");
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, title, MINIMUM_SPACING, SpringLayout.WEST, _optionsPanel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, title, 0, SpringLayout.NORTH, _optionsPanel);
		_optionsPanel.add(title);

		// Options title
		final Border titled = BorderFactory.createTitledBorder("");
		_optionsPanel.setBorder(titled);

		final Font fieldFont = new Font("Courier", Font.PLAIN, 12);
		final String keepString = "Keep?";

		// Question
		final JLabel questionLabel = new JLabel("Question: ");
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, questionLabel, 0, SpringLayout.WEST, title);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, questionLabel, 20, SpringLayout.NORTH, title);
		_optionsPanel.add(questionLabel);

		_questionTextField = new JTextField(FIELD_WIDTH);
		_questionTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_questionTextField.setFont(fieldFont);
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, _questionTextField, MINIMUM_SPACING, SpringLayout.EAST, questionLabel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, _questionTextField, 0, SpringLayout.NORTH, questionLabel);
		_optionsPanel.add(_questionTextField);

		_questionCheckBox = new JCheckBox(keepString);
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, _questionCheckBox, MINIMUM_SPACING, SpringLayout.EAST, _questionTextField);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, _questionCheckBox, -2, SpringLayout.NORTH, questionLabel);
		_optionsPanel.add(_questionCheckBox);

		final JLabel[] questionNumber = new JLabel[MAX_NUMBER_OF_ANSWERS];
		final String[] options = {"Answer", "Option" };
		_optionComboBoxes = new JComboBox[MAX_NUMBER_OF_ANSWERS];
		_optionTextFields = new JTextField[MAX_NUMBER_OF_ANSWERS];
		_optionCheckBoxes = new JCheckBox[MAX_NUMBER_OF_ANSWERS];

		// create all the options
		for (int x = 0; x < MAX_NUMBER_OF_ANSWERS; x++) {
			questionNumber[x] = new JLabel(x + ":");
			_optionComboBoxes[x] = new JComboBox(options);
			_optionTextFields[x] = new JTextField(FIELD_WIDTH);
			_optionTextFields[x].setDocument(new JTextFieldLimit(FIELD_WIDTH));
			_optionTextFields[x].setFont(fieldFont);
			_optionCheckBoxes[x] = new JCheckBox(keepString);
			if (x > 0) {
				_optionComboBoxes[x].setSelectedIndex(1);
			}
		}

		// setup up constraints
		final Component[] southConstraint = new Component[MAX_NUMBER_OF_ANSWERS + 1];
		southConstraint[0] = questionLabel;
		for (int x = 0; x < MAX_NUMBER_OF_ANSWERS; x++) {
			southConstraint[x + 1] = questionNumber[x];
		}

		for (int x = 0; x < MAX_NUMBER_OF_ANSWERS; x++) {
			// question number
			optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, questionNumber[x], MINIMUM_SPACING, SpringLayout.WEST, questionLabel);
			optionsPanelSpringLayout.putConstraint(SpringLayout.SOUTH, questionNumber[x], 30, SpringLayout.SOUTH, southConstraint[x]);
			_optionsPanel.add(questionNumber[x]);

			// answer or option combo box
			optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, _optionComboBoxes[x], MINIMUM_SPACING, SpringLayout.EAST, questionNumber[x]);
			optionsPanelSpringLayout.putConstraint(SpringLayout.SOUTH, _optionComboBoxes[x], 4, SpringLayout.SOUTH, questionNumber[x]);
			_optionsPanel.add(_optionComboBoxes[x]);

			// answer or option text field
			optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, _optionTextFields[x], MINIMUM_SPACING, SpringLayout.EAST, _optionComboBoxes[x]);
			optionsPanelSpringLayout.putConstraint(SpringLayout.SOUTH, _optionTextFields[x], 0, SpringLayout.SOUTH, _optionComboBoxes[x]);
			_optionsPanel.add(_optionCheckBoxes[x]);

			// keep string
			optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, _optionCheckBoxes[x], MINIMUM_SPACING, SpringLayout.EAST, _optionTextFields[x]);
			optionsPanelSpringLayout.putConstraint(SpringLayout.SOUTH, _optionCheckBoxes[x], 0, SpringLayout.SOUTH, _optionTextFields[x]);
			_optionsPanel.add(_optionTextFields[x]);
		}

		final JLabel temp = new JLabel();
		optionsPanelSpringLayout.putConstraint(SpringLayout.EAST, _optionsPanel, SCREEN_WIDTH - 20, SpringLayout.EAST, temp);
		optionsPanelSpringLayout.putConstraint(SpringLayout.SOUTH, _optionsPanel, 345, SpringLayout.SOUTH, temp);
	}

	/**
	 * Verifies all the fields that they contain valid data.
	 * @return Returns the result of the verify. 
	 */
	public String verifyAllFields() {
		String result = "";
		result += _answerLocationPanel.verifyFields();

		if (_questionTextField.getText().equals("")) {
			result += " Question field needs to be filled in.";
		}

		// find the number of options and answers
		int filledAnswers = 0;
		int filledOptions = 0;
		for (int x = 0; x < _optionTextFields.length; x++) {
			if (_optionTextFields[x].getText().length() > 0) {
				if ("Answer".equals(_optionComboBoxes[x].getSelectedItem())) {
					++filledAnswers;
				} else if ("Option".equals(_optionComboBoxes[x].getSelectedItem())) {
					++filledOptions;
				}
			}
		}

		// look to see if any answers or questions exist
		if (filledOptions == 0) {
			result += " At least one option is required.";
		}
		if (filledAnswers == 0) {
			result += " At least one answer is required.";
		}

		return result;
	}

	/**
	 * Gets all the fields marked as answer in the drop down.
	 * 
	 * @return All the fields marked as answer.
	 */
	private ArrayList<String> getAnswers() {
		final ArrayList<String> answersList = new ArrayList<String>();
		for (int x = 0; x < _optionTextFields.length; x++) {
			if ("Answer".equals(_optionComboBoxes[x].getSelectedItem())) {
				if (_optionTextFields[x].getText().length() > 0) {
					answersList.add(_optionTextFields[x].getText());
				}
			}
		}
		return answersList;
	}

	/**
	 * Gets all the fields marked as option in the drop down.
	 * 
	 * @return All the fields marked as option.
	 */
	private ArrayList<String> getOptions() {
		final ArrayList<String> optionsList = new ArrayList<String>();
		for (int x = 0; x < _optionTextFields.length; x++) {
			if ("Option".equals(_optionComboBoxes[x].getSelectedItem())) {
				if (_optionTextFields[x].getText().length() > 0) {
					optionsList.add(_optionTextFields[x].getText());
				}
			}
		}
		return optionsList;
	}

	/**
	 * Resets all the fields.
	 */
	private void resetFields() {
		// update values based on the keep string check box
		_answerLocationPanel.setFields();
		if (!_questionCheckBox.isSelected()) {
			_questionTextField.setText("");
		}
		for (int x = 0; x < _optionCheckBoxes.length; x++) {
			if (!_optionCheckBoxes[x].isSelected()) {
				_optionTextFields[x].setText("");
			}
		}
	}

	/**
	 * Inserts a question into the database.
	 * 
	 * @return the question number that was inserted.
	 */
	@Override
	public int insertQuestion() {

		// write the question;
		final Location answerLocation = _answerLocationPanel.getAnswerLocation();
		final String question = _questionTextField.getText();
		final ArrayList<String> answers = getAnswers();
		final ArrayList<String> options = getOptions();

		int questionNumber = -1;
		if (answers.size() == 1) { // radio button

			// create question
			final String answer = answers.get(0);
			final RadioButton questionData = new RadioButton(question, answer, answerLocation, options);

			final QuestionsDatabase database = new QuestionsDatabase();
			questionNumber = database.insertQuestion(questionData);

		} else { // check box

			// create question
			final CheckBox questionData = new CheckBox(question, answers, answerLocation, options);

			final QuestionsDatabase database = new QuestionsDatabase();
			questionNumber = database.insertQuestion(questionData);
		}

		// empty out the none keep string check text fields
		resetFields();

		return questionNumber;
	}
}
