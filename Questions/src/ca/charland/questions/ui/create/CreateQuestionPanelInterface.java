package ca.charland.questions.ui.create;

import java.awt.Color;

/**
 * Creates a question panel interface.
 * 
 * @author Michael
 */
public interface CreateQuestionPanelInterface {

	/**
	 * The width of the screen. Note this is not adjustable.
	 */
	int SCREEN_WIDTH = 865;

	/**
	 * The height of the screen. Note this is not adjustable.
	 */
	int SCREEN_HEIGHT = 600;

	/**
	 * Submit label.
	 */
	String LABEL_SUBMIT = "Submit";

	/**
	 * Choose type label.
	 */
	String LABEL_CHOOSE_TYPE = "Choose Type: ";

	/**
	 * Radio Button or Check Box label.
	 */
	String LABEL_RADIO_BUTTON_OR_CHECK_BOX = "Radio Button or Check Box";

	/**
	 * Blank answer label.
	 */
	String LABEL_BLANK_ANSWER = "Blank Answer";

	/**
	 * Fill in the blanks label.
	 */
	String LABEL_FILL_IN_THE_BLANKS = "Fill in the Blanks";

	/**
	 * Short Cut label. 
	 */
	String LABEL_SHORT_CUT = "Short Cut";

	/**
	 * The value of the color.
	 */
	int GREY_VALUE = 238;

	/**
	 * The color grey of the background.
	 */
	Color BACKGROUND_GREY = new Color(GREY_VALUE, GREY_VALUE, GREY_VALUE);

	/**
	 * The spacing between the components.
	 */
	int MINIMUM_SPACING = 5;

}
