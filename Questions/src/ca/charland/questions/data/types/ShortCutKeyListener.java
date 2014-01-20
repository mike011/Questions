package ca.charland.questions.data.types;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

/**
 * The key listener which is used by the short cut.
 * 
 * @author Michael
 */
public class ShortCutKeyListener implements KeyListener {

	/**
	 * The field to be updated.
	 */
	private JTextField _field;

	/**
	 * Creates a new key listener.
	 * 
	 * @param field
	 *            The field to be edited.
	 */
	public ShortCutKeyListener(final JTextField field) {
		_field = field;
	}

	/**
	 * Triggered when a key is pressed.
	 * 
	 * @param arg0
	 *            the key pressed.
	 */
	@Override
	public final void keyPressed(final KeyEvent arg0) {
		String current = _field.getText();
		String add = "";
		if (current.length() > 0) {
			add += " + ";
		}

		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_ALT:
			add += "ALT";
			break;
		case KeyEvent.VK_BACK_SPACE:
			add += "BACK_SPACE";
			break;
		case KeyEvent.VK_CONTROL:
			add += "CTRL";
			break;
		case KeyEvent.VK_DELETE:
			add += "DELETE";
			break;
		case KeyEvent.VK_END:
			add += "END";
			break;
		case KeyEvent.VK_ENTER:
			add += "ENTER";
			break;
		case KeyEvent.VK_HOME:
			add += "HOME";
			break;
		case KeyEvent.VK_INSERT:
			add += "INSERT";
			break;
		case KeyEvent.VK_SHIFT:
			add += "SHIFT";
			break;
		case KeyEvent.VK_DOWN:
			add += "DOWN";
			break;
		case KeyEvent.VK_UP:
			add += "UP";
			break;
		case KeyEvent.VK_LEFT:
			add += "LEFT";
			break;
		case KeyEvent.VK_RIGHT:
			add += "RIGHT";
			break;
		case KeyEvent.VK_SPACE:
			add += "SPACE";
			break;
		case KeyEvent.VK_F1:
			add += "F1";
			break;
		case KeyEvent.VK_F2:
			add += "F2";
			break;
		case KeyEvent.VK_F3:
			add += "F3";
			break;
		case KeyEvent.VK_F4:
			add += "F4";
			break;
		case KeyEvent.VK_F5:
			add += "F5";
			break;
		case KeyEvent.VK_F6:
			add += "F6";
			break;
		case KeyEvent.VK_F7:
			add += "F7";
			break;
		case KeyEvent.VK_F8:
			add += "F8";
			break;
		case KeyEvent.VK_F9:
			add += "F9";
			break;
		case KeyEvent.VK_F10:
			add += "F10";
			break;
		case KeyEvent.VK_F11:
			add += "F11";
			break;
		case KeyEvent.VK_F12:
			add += "F12";
			break;
		default:
		}

		if (Character.isLetter(arg0.getKeyChar())) {
			add += arg0.getKeyChar();
		}

		_field.setText(current + add);
	}

	/**
	 * Not used.
	 * 
	 * @param arg0
	 *            Not used.
	 */
	@Override
	public void keyReleased(final KeyEvent arg0) {
	}

	/**
	 * Not used.
	 * 
	 * @param arg0
	 *            Not used.
	 */
	@Override
	public void keyTyped(final KeyEvent arg0) {
	}
}
