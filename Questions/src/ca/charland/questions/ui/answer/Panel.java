package ca.charland.questions.ui.answer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


/**
 * This panel is used to answer questions.
 * 
 * @author Michael
 */
public final class Panel extends JPanel implements ActionListener {

	/**
	 * A uniquely generated long for threading purposes.
	 */
	private static final long serialVersionUID = -5218971542510993453L;

	/**
	 * The default width.
	 */
	private static final int SCREEN_WIDTH = 900;

	/**
	 * The default height.
	 */
	private static final int SCREEN_HEIGHT = 425;

	/**
	 * The spacing to use between components.
	 */
	private static final int SPACING = 10;

	/**
	 * The button to submit a question.
	 */
	private JButton _submitButton;

	/**
	 * The button to advance to the next question.
	 */
	private JButton _nextButton;

	/**
	 * Holds the ui and data of the question.
	 */
	private Container _container;

	/**
	 * The layout this panel uses.
	 */
	private SpringLayout _springLayout;

	/**
	 * No argument constructor.
	 */
	public Panel() {

		_springLayout = new SpringLayout();
		this.setLayout(_springLayout);

		// Creates the container that will hold all the questions.
		_container = new Container();

		// create the first question
		this.add(_container.getPanel());

		JPanel buttons = createButtons();
		_springLayout.putConstraint(SpringLayout.NORTH, buttons, SCREEN_HEIGHT - 45, SpringLayout.NORTH, this);
		this.add(buttons);

		final JLabel temp = new JLabel();
		_springLayout.putConstraint(SpringLayout.EAST, this, SCREEN_WIDTH, SpringLayout.WEST, temp);
		_springLayout.putConstraint(SpringLayout.SOUTH, this, SCREEN_HEIGHT, SpringLayout.SOUTH, temp);
	}

	/**
	 * Go to the next question.
	 */
	public void nextQuestion() {
		_container.nextQuestion();

		// Want to be able to answer questions multiple times.
		// //disable the buttons if the question has been answered
		// if(_container.getHasBeenAnswered() == true) {
		// _submitButton.setEnabled(false);
		// _nextButton.setEnabled(true);
		// } else {
		// _submitButton.setEnabled(true);
		// _nextButton.setEnabled(false);
		// }

		this.add(_container.getPanel());
	}

	/**
	 * Create the submit and next button and add them to the screen.
	 * 
	 * @return The panel with the submit and next buttons.
	 */
	private JPanel createButtons() {
		final SpringLayout springLayout = new SpringLayout();
		final JPanel jPanel = new JPanel(springLayout);

		// the submit button
		_submitButton = new JButton("Submit");
		springLayout.putConstraint(SpringLayout.WEST, _submitButton, SPACING, SpringLayout.WEST, jPanel);
		springLayout.putConstraint(SpringLayout.NORTH, _submitButton, 0, SpringLayout.NORTH, jPanel);
		_submitButton.addActionListener(this);
		_submitButton.setEnabled(true);
		jPanel.add(_submitButton);

		// the next button
		_nextButton = new JButton("Next");
		springLayout.putConstraint(SpringLayout.WEST, _nextButton, SPACING, SpringLayout.EAST, _submitButton);
		springLayout.putConstraint(SpringLayout.NORTH, _nextButton, 0, SpringLayout.NORTH, jPanel);
		_nextButton.addActionListener(this);
		_nextButton.setEnabled(false);
		jPanel.add(_nextButton);

		springLayout.putConstraint(SpringLayout.EAST, jPanel, SPACING, SpringLayout.EAST, _nextButton);
		springLayout.putConstraint(SpringLayout.SOUTH, jPanel, SPACING, SpringLayout.SOUTH, _nextButton);

		return jPanel;
	}

	/**
	 * Listens submit and next buttons.
	 * 
	 * @param e
	 *            The event that was triggered.
	 */
	public void actionPerformed(final ActionEvent e) {
		if ("Submit".equals(e.getActionCommand())) {
			if (_container.isAnswerCorrect()) {
				_submitButton.setEnabled(false);
				_nextButton.setEnabled(true);
			}
			_container.updateQuestion();
		} else if ("Next".equals(e.getActionCommand())) {
			_container.setHasBeenAnswered();
			_submitButton.setEnabled(true);
			_nextButton.setEnabled(false);
			this.remove(_container.getPanel());
			this.updateUI();
			this.nextQuestion();
		}
	}

}
