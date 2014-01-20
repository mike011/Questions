package ca.charland.questions.ui.create;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


/**
 * Creates a J Text Field Limit.
 * 
 * @author Michael
 */
public final class JTextFieldLimit extends PlainDocument {

	/**
	 * Used for threading.
	 */
	static final long serialVersionUID = 1;

	/**
	 * The limit of the field.
	 */
	private int _limit;

	/**
	 * If to convert the data to upper case or not.
	 */
	private boolean _toUppercase = false;

	/**
	 * Creates a new J Text Field Limit.
	 * 
	 * @param limit
	 *            The limit of the field.
	 */
	public JTextFieldLimit(final int limit) {
		super();
		this._limit = limit;
	}

	/**
	 * Creates a new J Text Field Limit.
	 * 
	 * @param limit
	 *            The limit of the field.
	 * @param upper
	 *            Convert the text to upper case.
	 */
	public JTextFieldLimit(final int limit, final boolean upper) {
		super();
		this._limit = limit;
		_toUppercase = upper;
	}

	/**
	 * The insert string.
	 * 
	 * @param offset
	 *            The offset of the string.
	 * @param str
	 *            The string.
	 * @param attr
	 *            The attributes of the string.
	 * @throws BadLocationException
	 *             Bad spot in a document model.
	 */
	public void insertString(final int offset, final String str, final AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}

		String string = str;
		if ((getLength() + str.length()) <= _limit) {
			if (_toUppercase) {
				string = str.toUpperCase();
			}
			super.insertString(offset, string, attr);
		}
	}
}
