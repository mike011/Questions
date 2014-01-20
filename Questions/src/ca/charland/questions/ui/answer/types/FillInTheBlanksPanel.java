package ca.charland.questions.ui.answer.types;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.types.FillInTheBlanks;
import ca.charland.questions.utilities.MathUtilities;

/**
 * The panel for the fill in the blank questions.
 * 
 * @author Michael
 */
public final class FillInTheBlanksPanel extends AbstractPanel {

	/**
	 * The data of the question.
	 */
	private final FillInTheBlanks _fillInTheBlanks;

	/**
	 * The options in a combination box.
	 */
	private ArrayList<JComboBox> _optionComboBoxes;

	/**
	 * The options that are beside a combination box.
	 */
	private ArrayList<String> _optionsStrings;

	/**
	 * The answers.
	 */
	private ArrayList<String> _answers;

	/**
	 * These are the choices that have been made that effect the question.
	 */
	private final Hashtable<String, String> _choicesMade;

	/**
	 * Creates a new fill in the blanks panel.
	 * 
	 * @param questionData
	 *            The data to use for the question.
	 * @param session
	 *            The statistics for this session.
	 */
	public FillInTheBlanksPanel(final FillInTheBlanks questionData, final Statistics session) {
		super(questionData, session);
		_fillInTheBlanks = questionData;
		_choicesMade = new Hashtable<String, String>();
		this.addPanel();
	}

	/**
	 * Adds the middle panel. Might be able to move this method to abstract panel.
	 */
	@Override
	protected void addMiddlePanel() {
		_middle = createFillInTheBlanksQuestion();
		_springLayout.putConstraint(SpringLayout.NORTH, _middle, -25, SpringLayout.SOUTH, _top);
		_questionPanel.add(_middle);
	}

	/**
	 * Creates a new fill in the blanks panel.
	 * 
	 * @return The new fill in the blanks panel.
	 */
	private JPanel createFillInTheBlanksQuestion() {

		_questionLabel.setText(_fillInTheBlanks.getAnswer());
		final SpringLayout springLayout = new SpringLayout();
		final JPanel jPanel = new JPanel(springLayout);

		_optionsStrings = _fillInTheBlanks.getBlanks();
		final int[] randomList = MathUtilities.generateRandomArray(_optionsStrings.size());

		_answers = new ArrayList<String>();
		for (int x = 0; x < _optionsStrings.size(); x++) {
			final String value = _optionsStrings.get(randomList[x]);
			_answers.add(_optionsStrings.get(_optionsStrings.indexOf(value)));
		}

		_optionComboBoxes = new ArrayList<JComboBox>();
		for (int x = 0; x < _optionsStrings.size(); x++) {
			final JLabel label = new JLabel("Choose #");
			final String choice = _optionsStrings.get(randomList[x]).toLowerCase();
			springLayout.putConstraint(SpringLayout.WEST, label, 15, SpringLayout.WEST, jPanel);
			springLayout.putConstraint(SpringLayout.NORTH, label, 45 + x * 35, SpringLayout.NORTH, jPanel);
			jPanel.add(label);

			Object[] choices = new Object[_optionsStrings.size() + 1];
			// Make the first choice a symbol so it's easier to differentiate which one has been picked as zero.
			choices[0] = "--";
			for (int y = 0; y < choices.length - 1; y++) {
				choices[y + 1] = "[" + y + "]";

			}
			final JComboBox combobox = new JComboBox(choices);
			springLayout.putConstraint(SpringLayout.WEST, combobox, 10, SpringLayout.EAST, label);
			springLayout.putConstraint(SpringLayout.NORTH, combobox, -20, SpringLayout.SOUTH, label);
			_optionComboBoxes.add(combobox);
			combobox.addActionListener(new ActionListener() {


				/**
				 * Handle when a drop down option is picked.
				 * @arg0 Not used.
				 */
				@Override
				public void actionPerformed(final ActionEvent arg0) {

					// Look too see if a choice has been made for this combination box;
					String question = "";
					if (_choicesMade.containsKey(choice)) {
						// Yes, so remove the choice.
						_choicesMade.remove(choice);
					}

					String begin = "Question #" + new Integer(_questionData.getQuestionNumber()).toString() + ": ";
					question = begin + _questionData.getQuestionString();
					final Set<String> keys = _choicesMade.keySet();
					for (final String key : keys) {
						question = replace(_choicesMade.get(key), question, key);
					}

					final int indexToLookFor = combobox.getSelectedIndex() - 1;
					if (indexToLookFor >= 0) {
						// Add that this choice has now been picked for this question.
						_choicesMade.put(choice, "[" + indexToLookFor + "]");
						question = replace("[" + indexToLookFor + "]", question, choice);
					}

					// Set all instances to what you picked.
					_beginLabel.setText(question);
				}

				/**
				 * Replaces the needle with the newNeedle.
				 * 
				 * @param needle
				 *            The string too look for.
				 * @param haystack
				 *            The string too look in.
				 * @param newNeedle
				 *            The string to replace the needle with.
				 * @return The resultant string.
				 */
				private String replace(final String needle, final String haystack, final String newNeedle) {
					String result = haystack;
					while (result.indexOf(needle) != -1) {
						result = result.replace(needle, newNeedle);
					}
					return result;
				}

			});
			jPanel.add(combobox);
			final JLabel option = new JLabel(choice);
			springLayout.putConstraint(SpringLayout.WEST, option, 10, SpringLayout.EAST, combobox);
			springLayout.putConstraint(SpringLayout.NORTH, option, 1, SpringLayout.NORTH, combobox);
			jPanel.add(option);

		}
		// the whole pane
		final JLabel temp = new JLabel();
		springLayout.putConstraint(SpringLayout.EAST, jPanel, 600, SpringLayout.WEST, temp);
		springLayout.putConstraint(SpringLayout.SOUTH, jPanel, 250, SpringLayout.SOUTH, temp);

		jPanel.setOpaque(true);

		return jPanel;
	}

	/**
	 * Verifies if the answer is correct or not.
	 * 
	 * @return If the answer is correct or not.
	 */
	@Override
	public boolean isAnswerCorrect() {
		boolean passed = false;
		final String correctAnswer = "Question #" + _questionData.getQuestionNumber() + ": " +_fillInTheBlanks.getAnswer();
		final String answered = _beginLabel.getText() + _endLabel.getText();
		
		System.out.println("correctAnswer=<" + correctAnswer + ">");
		System.out.println("     answered=<" + answered + ">");
		if (correctAnswer.trim().equalsIgnoreCase(answered.trim())) {
			passed = true;
			_fillInTheBlanks.setCorrectlyAnswered();
			_session.setCorrectlyAnswered();
			setAnswerLabel("CORRECT");
		} else {
			passed = false;
			_fillInTheBlanks.setIncorrectlyAnswered();
			_session.setIncorrectlyAnswered();
			setAnswerLabel("INCORRECT");
		}

		this.updatePanel();

		return passed;
	}
}
