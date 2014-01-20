package ca.charland.questions.ui.mainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import ca.charland.questions.ui.answer.Frame;
import ca.charland.questions.ui.create.CreateQuestionsFrame;

/**
 * This is the very first panel you see when starting the questiosn program.
 * 
 * @author Michael
 */
public class MainWindowPanel extends JPanel implements ActionListener {

	/**
	 * The frame of the panel.
	 */
	private final MainWindowFrame _mainWindowFrame;

	/**
	 * Used for threading. Currently not taken advantage of.
	 */
	private static final long serialVersionUID = 0;

	/**
	 * The default screen width.
	 */
	private static final int SCREEN_WIDTH = 10;

	/**
	 * The default screen height.
	 */
	private static final int SCREEN_HEIGHT = 180;

	/**
	 * The title of the panel.
	 */
	private JLabel _titleLabel;

	/**
	 * The Answer questions button.
	 */
	private JButton _answerQuestionsButton;

	/**
	 * The view results button.
	 */
	private JButton _viewResultsButton;

	/**
	 * The modify questions button.
	 */
	private JButton _modifyQuestionsButton;

	/**
	 * THe create questions button.
	 */
	private JButton _createQuestionsButton;

	/**
	 * THe main constructor for the panel.
	 * 
	 * @param mainWindowFrame
	 *            The frame for the panel.
	 */
	public MainWindowPanel(final MainWindowFrame mainWindowFrame) {

		_mainWindowFrame = mainWindowFrame;

		System.out.println("setup");

		SpringLayout springLayout = new SpringLayout();

		setLayout(springLayout);

		// the text of the category
		_titleLabel = new JLabel("Weclome to the question program of greatness!!");
		springLayout.putConstraint(SpringLayout.WEST, _titleLabel, 5, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, _titleLabel, 5, SpringLayout.NORTH, this);
		this.add(_titleLabel);

		// the Answer Question button
		_answerQuestionsButton = new JButton("Answer Questions");
		springLayout.putConstraint(SpringLayout.WEST, _answerQuestionsButton, 70, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, _answerQuestionsButton, 40, SpringLayout.NORTH, _titleLabel);
		_answerQuestionsButton.addActionListener(this);
		this.add(_answerQuestionsButton);

		// the View Results button
		_viewResultsButton = new JButton("View Results");
		springLayout.putConstraint(SpringLayout.WEST, _viewResultsButton, 80, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, _viewResultsButton, 40, SpringLayout.NORTH, _answerQuestionsButton);
		_viewResultsButton.addActionListener(this);
		this.add(_viewResultsButton);

		// the Modify Questions button
		_modifyQuestionsButton = new JButton("Modify Questions");
		springLayout.putConstraint(SpringLayout.WEST, _modifyQuestionsButton, 70, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, _modifyQuestionsButton, 40, SpringLayout.NORTH, _viewResultsButton);
		_modifyQuestionsButton.addActionListener(this);
		this.add(_modifyQuestionsButton);

		// the Create Questions button
		_createQuestionsButton = new JButton("Create Questions");
		springLayout.putConstraint(SpringLayout.WEST, _createQuestionsButton, 70, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, _createQuestionsButton, 40, SpringLayout.NORTH, _modifyQuestionsButton);
		_createQuestionsButton.addActionListener(this);
		this.add(_createQuestionsButton);

		// the whole pane
		// Adjust constraints for the content pane: Its right
		// edge should be 5 pixels beyond the text field's right
		// edge, and its bottom edge should be 5 pixels beyond
		// the bottom edge of the tallest component.
		springLayout.putConstraint(SpringLayout.EAST, this, SCREEN_WIDTH, SpringLayout.EAST, _titleLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, this, SCREEN_HEIGHT, SpringLayout.SOUTH, _titleLabel);

		// frame.add(this);
		setOpaque(true); // content panes must be opaque
	}

	/**
	 * Listens to the radio buttons.
	 * 
	 * @param e
	 *            The action that was performed.
	 */
	public final void actionPerformed(final ActionEvent e) {
		// check for correct answer
		if ("Answer Questions".equals(e.getActionCommand())) {

			// hide the main window
			_mainWindowFrame.setVisible(false);
			@SuppressWarnings("unused")
			final Frame qf = new Frame(_mainWindowFrame);

		} else if ("View Results".equals(e.getActionCommand())) {
			System.out.println("Not implemented yet");
		} else if ("Modify Questions".equals(e.getActionCommand())) {
			System.out.println("Not implemented yet");
		} else if ("Create Questions".equals(e.getActionCommand())) {
			_mainWindowFrame.setVisible(false);
			@SuppressWarnings("unused")
			final CreateQuestionsFrame cqf = new CreateQuestionsFrame(_mainWindowFrame);
		}
	}
}
