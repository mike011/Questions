package ca.charland.questions.ui.answer.types;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.SpringLayout;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.types.CheckBox;
import ca.charland.questions.utilities.MathUtilities;

/**
 * This class provides the layout for when answering check box questions.
 * 
 * @author Michael
 */
public final class CheckBoxPanel extends AbstractPanel {

	/**
	 * The options for the check box questions.
	 */
	private ArrayList<JCheckBox> _checkBoxOptions;

	/**
	 * The vertical constraints for the questions.
	 */
	private ArrayList<Component> _verticalConstraints;

	/**
	 * The data of the questions.
	 */
	private CheckBox _checkBoxQuestionData;

	/**
	 * Constructs a new instance.
	 * 
	 * @param checkBoxQuestionData
	 *            The data for the questions.
	 * @param session
	 *            The statistics for this session.
	 */
	public CheckBoxPanel(final CheckBox checkBoxQuestionData, final Statistics session) {
		super(checkBoxQuestionData, session);
		_checkBoxQuestionData = checkBoxQuestionData;
		_springLayout = new SpringLayout();
		_questionPanel = new JPanel(_springLayout);
		this.addPanel();
	}

	/**
	 * Adds the middle panel which contains all the question data.
	 */
	@Override
	protected void addMiddlePanel() {
		_middle = createCheckBoxQuestion();
		_springLayout.putConstraint(SpringLayout.NORTH, _middle, -25, SpringLayout.SOUTH, _top);
		_questionPanel.add(_middle);
	}

	/**
	 * Creates the panel for the check box question.
	 * 
	 * @return The panel of the check box question.
	 */
	private JPanel createCheckBoxQuestion() {
		final SpringLayout springLayout = new SpringLayout();
		final JPanel jPanel = new JPanel(springLayout);

		// Create the option radio buttons.
		_checkBoxOptions = new ArrayList<JCheckBox>();
		_verticalConstraints = new ArrayList<Component>();
		_verticalConstraints.add(jPanel);

		final int[] randomList = MathUtilities.generateRandomArray(_checkBoxQuestionData.getNumOfAllOptions());
		for (int x = 0; x < _checkBoxQuestionData.getNumOfAllOptions(); x++) {
			final JCheckBox option = new JCheckBox(_checkBoxQuestionData.getAllOption(randomList[x]));
			option.setActionCommand(_checkBoxQuestionData.getAllOption(randomList[x]));
			_checkBoxOptions.add(option);
			_verticalConstraints.add(option);
		}

		// Group, constrain, and add listeners for the radio buttons.
		for (int x = 0; x < _checkBoxQuestionData.getNumOfAllOptions(); x++) {

			_checkBoxOptions.get(x).addActionListener(this); // Register the listener.
			springLayout.putConstraint(SpringLayout.WEST, _checkBoxOptions.get(x), MINIMUM_SPACING, SpringLayout.WEST, jPanel);
			springLayout.putConstraint(SpringLayout.NORTH, _checkBoxOptions.get(x), 25, SpringLayout.NORTH, (Component) _verticalConstraints.get(x));
			jPanel.add(_checkBoxOptions.get(x));
		}

		// the whole pane
		final JLabel temp = new JLabel();
		springLayout.putConstraint(SpringLayout.EAST, jPanel, 600, SpringLayout.WEST, temp);
		springLayout
				.putConstraint(SpringLayout.SOUTH, jPanel, MINIMUM_SPACING, SpringLayout.SOUTH, _checkBoxOptions.get(_checkBoxOptions.size() - 1));

		jPanel.setOpaque(true);

		return jPanel;
	}

	/**
	 * Checks if the question is correct or not.
	 * 
	 * @return If the question is correct or not.
	 */
	public boolean isAnswerCorrect() {
		boolean result = false;
		final ArrayList<Integer> currentlySelected = new ArrayList<Integer>();

		// find the option selected
		for (int x = 0; x < _checkBoxOptions.size(); x++) {
			if (_checkBoxOptions.get(x).isSelected()) {
				currentlySelected.add(x);
			}
		}

		if (currentlySelected.size() == 0) {
			setAnswerLabel("No option selected, please select option");
			result = false;
		} else {
			int numCorrects = 0;
			// act on the option selected
			for (int x = 0; x < currentlySelected.size(); x++) {
				for (int y = 0; y < _checkBoxQuestionData.getNumOfAnswers(); y++) {
					if (_checkBoxOptions.get(currentlySelected.get(x)).getText().toString().equals(_checkBoxQuestionData.getAnswer(y).toString())) {
						_checkBoxOptions.get(currentlySelected.get(x)).setBackground(Color.green);
						++numCorrects;
						break;
					}
				}
			}

			_difficultyLabel.setText(new Integer(_checkBoxQuestionData.getDifficulty()).toString());
			_totalAnsweredLabel.setText(new Integer(_checkBoxQuestionData.getTotalTimesAnswered()).toString());
			if (numCorrects == _checkBoxQuestionData.getNumOfAnswers() && numCorrects == currentlySelected.size()) {
				setAnswerLabel("CORRECT");
				_checkBoxQuestionData.setCorrectlyAnswered();
				_session.setCorrectlyAnswered();
				for (int x = 0; x < _checkBoxOptions.size(); x++) {
					_checkBoxOptions.get(x).setEnabled(false);
				}
				result = true;
			} else {
				setAnswerLabel("INCORRECT");
				_checkBoxQuestionData.setIncorrectlyAnswered();
				_session.setIncorrectlyAnswered();
				result = false;
			}
		}

		this.updatePanel();

		return result;
	}
}
