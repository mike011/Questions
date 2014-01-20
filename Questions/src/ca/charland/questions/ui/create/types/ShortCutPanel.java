package ca.charland.questions.ui.create.types;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.types.ShortCut;
import ca.charland.questions.data.types.ShortCutKeyListener;
import ca.charland.questions.database.QuestionsDatabase;
import ca.charland.questions.ui.create.AnswerLocationPanel;
import ca.charland.questions.ui.create.JTextFieldLimit;

/**
 * Creates a new blank answer panel.
 * 
 * @author Michael
 */
public final class ShortCutPanel extends AbstractCreatePanel {

	/**
	 * The question test field.
	 */
	private JTextField _questionTextField;

	/**
	 * The answer text field.
	 */
	private JTextField _shortCutTextField;

	/**
	 * Creates a new blank answer panel.
	 * 
	 * @param topPanel
	 *            The top panel.
	 */
	public ShortCutPanel(final AnswerLocationPanel topPanel) {
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

		final JLabel title = new JLabel("Create a Short Cut Question.");
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

		// Short Cut
		final JLabel shortCutLabel = new JLabel("Short Cut: ");
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, shortCutLabel, 0, SpringLayout.WEST, questionLabel);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, shortCutLabel, 25, SpringLayout.SOUTH, questionLabel);
		_optionsPanel.add(shortCutLabel);

		_shortCutTextField = new JTextField(FIELD_WIDTH);
		_shortCutTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_shortCutTextField.setFont(fieldFont);
		_shortCutTextField.setEditable(false);
		_shortCutTextField.addKeyListener(new ShortCutKeyListener(_shortCutTextField));

		JButton clear = new JButton("Clear");
		optionsPanelSpringLayout.putConstraint(SpringLayout.WEST, clear, 0, SpringLayout.EAST, _shortCutTextField);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, clear, 25, SpringLayout.SOUTH, questionLabel);
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				_shortCutTextField.setText("");
			}
		});
		_optionsPanel.add(clear);

		optionsPanelSpringLayout.putConstraint(SpringLayout.EAST, _shortCutTextField, 0, SpringLayout.EAST, _questionTextField);
		optionsPanelSpringLayout.putConstraint(SpringLayout.NORTH, _shortCutTextField, 0, SpringLayout.NORTH, shortCutLabel);
		_optionsPanel.add(_shortCutTextField);

		final JLabel temp = new JLabel();
		optionsPanelSpringLayout.putConstraint(SpringLayout.EAST, _optionsPanel, SCREEN_WIDTH - 20, SpringLayout.EAST, temp);
		optionsPanelSpringLayout.putConstraint(SpringLayout.SOUTH, _optionsPanel, 330, SpringLayout.SOUTH, temp);
	}

	/**
	 * Verifies that fields are correct.
	 * 
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
		_shortCutTextField.setText("");
	}

	/**
	 * Inserts a question into the database.
	 * 
	 * @return The question number.
	 */
	@Override
	public int insertQuestion() {

		// write the question;
		final Location answerLocation = _answerLocationPanel.getAnswerLocation();
		final String question = _questionTextField.getText();
		final String shortCut = _shortCutTextField.getText();

		// create question
		final ShortCut questionData = new ShortCut(question, shortCut, answerLocation);

		final QuestionsDatabase database = new QuestionsDatabase();
		final int questionNumber = database.insertQuestion(questionData);

		// empty out the none keep string check text fields
		this.setFields();

		return questionNumber;
	}
}
