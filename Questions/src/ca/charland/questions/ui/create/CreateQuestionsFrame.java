package ca.charland.questions.ui.create;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ca.charland.questions.ui.mainWindow.MainWindowFrame;

/**
 * This creates the j frame used when creating questions.
 */
public final class CreateQuestionsFrame extends AbstractCreateQuestionsFrame {

	/**
	 * Used for threading.
	 */
	private static final long serialVersionUID = 4493326989745957481L;

	/**
	 * An instance of the main window frame.
	 */
	private final MainWindowFrame _mainWindowFrame;

	/**
	 * Creates a new Create Questions Frame.
	 * 
	 * @param mainWindowFrame
	 *            The main frame window.
	 */
	public CreateQuestionsFrame(final MainWindowFrame mainWindowFrame) {

		_mainWindowFrame = mainWindowFrame;

		// Set up the window
		this.setTitle("Create Questions");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(this);

		// Create and set up the content pane.
		this.setContentPane(new CreateQuestionPanel());

		// Display the window
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * What to do when the window closes.
	 * @param e Not used.
	 */
	@Override
	public void windowClosed(final WindowEvent e) {

		// show the main window
		_mainWindowFrame.setVisible(true);
	}

}
