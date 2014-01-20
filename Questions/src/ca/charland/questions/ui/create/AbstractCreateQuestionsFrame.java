package ca.charland.questions.ui.create;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * An abstract create question frame.
 * 
 * @author Michael
 */
public abstract class AbstractCreateQuestionsFrame extends JFrame implements WindowListener {

	/**
	 * Randomly generated serial.
	 */
	static final long serialVersionUID = 1123480129L;

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowClosing(final WindowEvent e) {
	}

	/**
	 * Sets the main window frame as visible when closing.
	 * 
	 * @param e
	 *            Not used.
	 */
	public abstract void windowClosed(final WindowEvent e);

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowOpened(final WindowEvent e) {
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowIconified(final WindowEvent e) {
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowDeiconified(final WindowEvent e) {
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowActivated(final WindowEvent e) {
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowDeactivated(final WindowEvent e) {
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowGainedFocus(final WindowEvent e) {
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowLostFocus(final WindowEvent e) {
	}

	/**
	 * Not used.
	 * 
	 * @param e
	 *            Not used.
	 */
	public void windowStateChanged(final WindowEvent e) {
	}

}
