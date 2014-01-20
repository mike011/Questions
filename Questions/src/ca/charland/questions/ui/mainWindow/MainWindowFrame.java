package ca.charland.questions.ui.mainWindow;

import javax.swing.JFrame;


/**
 * The main window frame.
 */
public class MainWindowFrame extends JFrame {

	/**
	 * Used for threading.
	 */
	static final long serialVersionUID = 1;

	/**
	 * Creates a new main window frame.
	 */
	MainWindowFrame() {

		// Set up the window
		setTitle("Main Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		setContentPane(new MainWindowPanel(this));

		// showing the window
		setResizable(false);
		pack();
		setVisible(true);
	}
}
