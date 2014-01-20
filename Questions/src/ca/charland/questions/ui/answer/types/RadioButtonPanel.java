package ca.charland.questions.ui.answer.types;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ButtonGroup;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.types.AbstractQuestion;
import ca.charland.questions.data.types.RadioButton;
import ca.charland.questions.utilities.MathUtilities;

/**
 * This is the panel that is displayed to the screen when answering radio button questions.
 * 
 * @author Michael
 */
public final class RadioButtonPanel extends AbstractPanel {

	/**
	 * The possible answer options that are shown on the screen.
	 */
	private ArrayList<JRadioButton> _radioButtonOptions;

	/**
	 * Keeps the vertical parts of the screen aligned.
	 */
	private ArrayList<Component> _verticalConstraints;

	/**
	 * The button.
	 */
	private ButtonGroup _group;

	/**
	 * The data that will populate the options and the answer.
	 */
	private final RadioButton _radioButtonQuestionData;

	/**
	 * Constructor.
	 * 
	 * @param radioButtonQuestionData
	 *            The data for the question.
	 */
	public RadioButtonPanel(final RadioButton radioButtonQuestionData, final Statistics session) {
		super((AbstractQuestion) radioButtonQuestionData, session);
		_radioButtonQuestionData = radioButtonQuestionData;
		_springLayout = new SpringLayout();
		_questionPanel = new JPanel(_springLayout);
		this.addPanel();
	}

	/**
	 * The middle panel on the screen.
	 */
	@Override()
	protected void addMiddlePanel() {
		_middle = createRadioButtonQuestion();
		_springLayout.putConstraint(SpringLayout.NORTH, _middle, -25, SpringLayout.SOUTH, _top);
		_questionPanel.add(_middle);
	}

	/**
	 * Creates the radio button question.
	 * 
	 * @return The panel that was created.
	 */
	private JPanel createRadioButtonQuestion() {
		SpringLayout springLayout = new SpringLayout();
		JPanel jPanel = new JPanel(springLayout);

		// Create the option radio buttons.
		_radioButtonOptions = new ArrayList<JRadioButton>();
		_verticalConstraints = new ArrayList<Component>();
		_verticalConstraints.add(jPanel);
		// System.out.println("options: " + radioButtonQuestionData.getNumOfOptions());
		int[] randomList = MathUtilities.generateRandomArray(_radioButtonQuestionData.getNumOfAllOptions());
		for (int x = 0; x < _radioButtonQuestionData.getNumOfAllOptions(); x++) {
			JRadioButton option = new JRadioButton(_radioButtonQuestionData.getAllOption(randomList[x]));
			option.setActionCommand(_radioButtonQuestionData.getAllOption(randomList[x]));
			_radioButtonOptions.add(option);
			_verticalConstraints.add(option);
		}

		// Group, constrain, and add listeners for the radio buttons.
		_group = new ButtonGroup();
		for (int x = 0; x < _radioButtonQuestionData.getNumOfAllOptions(); x++) {
			_group.add(_radioButtonOptions.get(x)); // group the button
			_radioButtonOptions.get(x).addActionListener(this); // register
			// the listener
			springLayout.putConstraint(SpringLayout.WEST, _radioButtonOptions.get(x), MINIMUM_SPACING, SpringLayout.WEST, jPanel);
			springLayout.putConstraint(SpringLayout.NORTH, _radioButtonOptions.get(x), 25, SpringLayout.NORTH, (Component) _verticalConstraints
					.get(x));
			jPanel.add(_radioButtonOptions.get(x));
		}

		// the whole pane
		JLabel temp = new JLabel();
		springLayout.putConstraint(SpringLayout.EAST, jPanel, 600, SpringLayout.WEST, temp);
		springLayout.putConstraint(SpringLayout.SOUTH, jPanel, MINIMUM_SPACING, SpringLayout.SOUTH, _radioButtonOptions.get(_radioButtonOptions.size() - 1));

		jPanel.setOpaque(true);

		return jPanel;
	}

	/**
	 * Verifies if what the person has selected is correct.
	 * 
	 * @return If the answer is correct or not.
	 */
	@Override
	public boolean isAnswerCorrect() {

		// find the option currently selected
		int optionCurrentlySelected = -1;
		for (int x = 0; x < _radioButtonOptions.size(); x++) {
			if (_radioButtonOptions.get(x).isSelected()) {
				optionCurrentlySelected = x;
				break;
			}
		}

		// compare the option currently selected to the correct answer
		if (optionCurrentlySelected == -1) {
			setAnswerLabel("No option selected, please select option");
			return false;
		} else {
			this.updatePanel();
			String button = _radioButtonOptions.get(optionCurrentlySelected).getText().toString();
			String answer = _radioButtonQuestionData.getAnswer();

			if (button.equals(answer)) {
				setAnswerLabel("Correct");
				_radioButtonQuestionData.setCorrectlyAnswered();
				_session.setCorrectlyAnswered();
				_radioButtonOptions.get(optionCurrentlySelected).setBackground(Color.green);
				for (int x = 0; x < _radioButtonOptions.size(); x++) {
					_radioButtonOptions.get(x).setEnabled(false);
				}
				return true;
			} else {
				setAnswerLabel("Incorrect");
				_radioButtonQuestionData.setIncorrectlyAnswered();
				_session.setIncorrectlyAnswered();
				return false;
			}
		}
	}
}
