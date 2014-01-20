package ca.charland.questions.ui.answer;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import ca.charland.questions.ui.mainWindow.MainWindowFrame;

/**
 * The JFrame used to answer questions.
 * 
 * @author Michael
 */
public final class Frame extends JFrame implements WindowListener {

	/**
	 * A unique number used when threading.
	 */
	private static final long serialVersionUID = -5501164294112781871L;

	/**
	 * The JFrame of the main window.
	 */
	private MainWindowFrame _mainWindowFrame;

	/**
	 * Constructor.
	 * 
	 * @param mainWindowFrame
	 *            The JFrame of the main window.
	 */
	public Frame(final MainWindowFrame mainWindowFrame) {

		_mainWindowFrame = mainWindowFrame;

		// Set up the window
		setTitle("Answer Questions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(this);

		// Create and set up the content pane.
		setContentPane(new Panel());

		// showing the window
		setResizable(false);
		pack();
		setVisible(true);
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowClosing(final WindowEvent e) {
		// System.out.println("WindowListener method called: windowClosing.");
	}

	/**
	 * Makes the main window visible on closing.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowClosed(final WindowEvent e) {

		// show the main window
		_mainWindowFrame.setVisible(true);
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowOpened(final WindowEvent e) {
		// System.out.println("WindowListener method called: windowOpened.");
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowIconified(final WindowEvent e) {
		// System.out.println("WindowListener method called: windowIconified.");
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowDeiconified(final WindowEvent e) {
		// System.out.println("WindowListener method called:
		// windowDeiconified.");
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowActivated(final WindowEvent e) {
		// System.out.println("WindowListener method called: windowActivated.");
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowDeactivated(final WindowEvent e) {
		// System.out.println("WindowListener method called:
		// windowDeactivated.");
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowGainedFocus(final WindowEvent e) {
		/*
		 * System.out .println("WindowFocusListener method called: windowGainedFocus.");
		 */
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowLostFocus(final WindowEvent e) {
		/*
		 * System.out .println("WindowFocusListener method called: windowLostFocus.");
		 */
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowStateChanged(final WindowEvent e) {
		/*
		 * System.out .println("WindowStateListener method called: windowStateChanged." + e);
		 */
	}
}
