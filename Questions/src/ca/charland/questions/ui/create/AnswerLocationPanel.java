package ca.charland.questions.ui.create;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import ca.charland.questions.data.Location;



/**
 * The answer location panel that is shown on the screen.
 * 
 * @author Michael
 */
public final class AnswerLocationPanel extends JPanel {

	/**
	 * Used for threading.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The width of the screen.
	 */
	private static final int SCREEN_WIDTH = 855;

	/**
	 * The height of the screen.
	 */
	// private final int screenHeight = 555;
	/**
	 * The width of a field.
	 */
	private static final int FIELD_WIDTH = 90;

	/**
	 * To value of the label if whether to keep or not the current value.
	 */
	private static final String KEEP_STRING = "Keep?";

	/**
	 * The size of the font.
	 */
	private static final int FONT_SIZE = 12;

	/**
	 * The font used for the fields.
	 */
	private static final Font FIELD_FONT = new Font("Courier", Font.PLAIN, FONT_SIZE);

	/**
	 * The minimum spacing between components.
	 */
	private static final int MINIMUM_SPACING = 5;

	/**
	 * The check box to keep the directory text or not.
	 */
	private JCheckBox _documentCheckBox;

	/**
	 * The check box to keep the category text or not.
	 */
	private JCheckBox _categoryCheckBox;

	/**
	 * The check box to keep the sub category text or not.
	 */
	private JCheckBox _subcategoryCheckBox;

	/**
	 * The check box to keep the chapter text or not.
	 */
	private JCheckBox _chapterCheckBox;

	/**
	 * The check box to keep the section text or not.
	 */
	private JCheckBox _sectionCheckBox;

	/**
	 * The check box to keep the page text or not.
	 */
	private JCheckBox _pageCheckBox;

	/**
	 * The text field that store the text info for the directory.
	 */
	private JTextField _documentTextField;

	/**
	 * The text field that store the text info for the category.
	 */
	private JTextField _categoryTextField;

	/**
	 * The text field that store the text info for the sub category.
	 */
	private JTextField _subcategoryTextField;

	/**
	 * The text field that store the text info for the chapter.
	 */
	private JTextField _chapterTextField;

	/**
	 * The text field that store the text info for the section.
	 */
	private JTextField _sectionTextField;

	/**
	 * The text field that store the text info for the page.
	 */
	private JTextField _pageTextField;

	/**
	 * Creates the answer location panel that is shown on the screen.
	 */
	public AnswerLocationPanel() {

		final SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

		final JPanel locationPanel = createLocationPanel();
		springLayout.putConstraint(SpringLayout.NORTH, locationPanel, 1, SpringLayout.NORTH, this);
		this.add(locationPanel);

		JLabel temp = new JLabel();
		springLayout.putConstraint(SpringLayout.EAST, this, SCREEN_WIDTH, SpringLayout.EAST, temp);
		springLayout.putConstraint(SpringLayout.SOUTH, this, MINIMUM_SPACING, SpringLayout.SOUTH, locationPanel);
	}

	/**
	 * Creates the location panel that is shown on the screen.
	 * 
	 * @return The location panel that is shown on the screen.
	 */
	public JPanel createLocationPanel() {
		SpringLayout locationPanelSpringLayout = new SpringLayout();
		JPanel locationPanel = new JPanel(locationPanelSpringLayout);

		// location of question
		final Border titled = BorderFactory.createTitledBorder("Question Origination");
		locationPanel.setBorder(titled);

		// The category of question
		JLabel categoryLabel = new JLabel("    Category (required):");
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, categoryLabel, 15, SpringLayout.WEST, locationPanel);
		locationPanelSpringLayout.putConstraint(SpringLayout.SOUTH, categoryLabel, 15, SpringLayout.NORTH, locationPanel);
		locationPanel.add(categoryLabel);

		_categoryTextField = new JTextField(FIELD_WIDTH);
		_categoryTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_categoryTextField.setFont(FIELD_FONT);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _categoryTextField, MINIMUM_SPACING, SpringLayout.EAST, categoryLabel);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _categoryTextField, 0, SpringLayout.NORTH, categoryLabel);
		locationPanel.add(_categoryTextField);

		_categoryCheckBox = new JCheckBox(KEEP_STRING);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _categoryCheckBox, MINIMUM_SPACING, SpringLayout.EAST, _categoryTextField);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _categoryCheckBox, -2, SpringLayout.NORTH, categoryLabel);
		locationPanel.add(_categoryCheckBox);

		// the sub category of the question
		JLabel subcategoryLabel = new JLabel("Subcategory (required):");
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, subcategoryLabel, -MINIMUM_SPACING, SpringLayout.WEST, categoryLabel);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, subcategoryLabel, MINIMUM_SPACING, SpringLayout.SOUTH, categoryLabel);
		locationPanel.add(subcategoryLabel);

		_subcategoryTextField = new JTextField(FIELD_WIDTH);
		_subcategoryTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_subcategoryTextField.setFont(FIELD_FONT);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _subcategoryTextField, MINIMUM_SPACING, SpringLayout.EAST, categoryLabel);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _subcategoryTextField, MINIMUM_SPACING, SpringLayout.SOUTH, categoryLabel);
		locationPanel.add(_subcategoryTextField);

		_subcategoryCheckBox = new JCheckBox(KEEP_STRING);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _subcategoryCheckBox, MINIMUM_SPACING, SpringLayout.EAST, _subcategoryTextField);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _subcategoryCheckBox, 3, SpringLayout.SOUTH, categoryLabel);
		locationPanel.add(_subcategoryCheckBox);

		// the document of the question
		JLabel documentLabel = new JLabel("Document (required):");
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, documentLabel, MINIMUM_SPACING, SpringLayout.WEST, categoryLabel);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, documentLabel, MINIMUM_SPACING, SpringLayout.SOUTH, subcategoryLabel);
		locationPanel.add(documentLabel);

		_documentTextField = new JTextField(FIELD_WIDTH);
		_documentTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_documentTextField.setFont(FIELD_FONT);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _documentTextField, MINIMUM_SPACING, SpringLayout.EAST, categoryLabel);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _documentTextField, MINIMUM_SPACING, SpringLayout.SOUTH, subcategoryLabel);
		locationPanel.add(_documentTextField);

		_documentCheckBox = new JCheckBox(KEEP_STRING);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _documentCheckBox, MINIMUM_SPACING, SpringLayout.EAST, _documentTextField);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _documentCheckBox, 3, SpringLayout.SOUTH, subcategoryLabel);
		locationPanel.add(_documentCheckBox);

		// chapter
		JLabel chapterLabel = new JLabel("Chapter:");
		locationPanelSpringLayout.putConstraint(SpringLayout.EAST, chapterLabel, -MINIMUM_SPACING, SpringLayout.WEST, _categoryTextField);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, chapterLabel, MINIMUM_SPACING, SpringLayout.SOUTH, documentLabel);
		locationPanel.add(chapterLabel);

		_chapterTextField = new JTextField(FIELD_WIDTH);
		_chapterTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_chapterTextField.setFont(FIELD_FONT);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _chapterTextField, MINIMUM_SPACING, SpringLayout.EAST, categoryLabel);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _chapterTextField, MINIMUM_SPACING, SpringLayout.SOUTH, documentLabel);
		locationPanel.add(_chapterTextField);

		_chapterCheckBox = new JCheckBox(KEEP_STRING);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _chapterCheckBox, MINIMUM_SPACING, SpringLayout.EAST, _chapterTextField);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _chapterCheckBox, 3, SpringLayout.SOUTH, documentLabel);
		locationPanel.add(_chapterCheckBox);

		// section
		JLabel sectionLabel = new JLabel("Section:");
		locationPanelSpringLayout.putConstraint(SpringLayout.EAST, sectionLabel, -MINIMUM_SPACING, SpringLayout.WEST, _categoryTextField);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, sectionLabel, MINIMUM_SPACING, SpringLayout.SOUTH, chapterLabel);
		locationPanel.add(sectionLabel);

		_sectionTextField = new JTextField(FIELD_WIDTH);
		_sectionTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_sectionTextField.setFont(FIELD_FONT);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _sectionTextField, MINIMUM_SPACING, SpringLayout.EAST, categoryLabel);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _sectionTextField, MINIMUM_SPACING, SpringLayout.SOUTH, chapterLabel);
		locationPanel.add(_sectionTextField);

		_sectionCheckBox = new JCheckBox(KEEP_STRING);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _sectionCheckBox, MINIMUM_SPACING, SpringLayout.EAST, _sectionTextField);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _sectionCheckBox, 3, SpringLayout.SOUTH, chapterLabel);
		locationPanel.add(_sectionCheckBox);

		// page number
		JLabel pageLabel = new JLabel("Page Number:");
		locationPanelSpringLayout.putConstraint(SpringLayout.EAST, pageLabel, -MINIMUM_SPACING, SpringLayout.WEST, _categoryTextField);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, pageLabel, MINIMUM_SPACING, SpringLayout.SOUTH, sectionLabel);
		locationPanel.add(pageLabel);

		_pageTextField = new JTextField(FIELD_WIDTH);
		_pageTextField.setDocument(new JTextFieldLimit(FIELD_WIDTH));
		_pageTextField.setFont(FIELD_FONT);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _pageTextField, MINIMUM_SPACING, SpringLayout.EAST, categoryLabel);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _pageTextField, MINIMUM_SPACING, SpringLayout.SOUTH, sectionLabel);
		locationPanel.add(_pageTextField);

		_pageCheckBox = new JCheckBox(KEEP_STRING);
		locationPanelSpringLayout.putConstraint(SpringLayout.WEST, _pageCheckBox, MINIMUM_SPACING, SpringLayout.EAST, _pageTextField);
		locationPanelSpringLayout.putConstraint(SpringLayout.NORTH, _pageCheckBox, 3, SpringLayout.SOUTH, sectionLabel);
		locationPanel.add(_pageCheckBox);

		JLabel temp = new JLabel();
		locationPanelSpringLayout.putConstraint(SpringLayout.EAST, locationPanel, SCREEN_WIDTH - 10, SpringLayout.EAST, temp);
		locationPanelSpringLayout.putConstraint(SpringLayout.SOUTH, locationPanel, 130, SpringLayout.SOUTH, temp);

		return locationPanel;
	}

	/**
	 * Checks the fields.
	 * 
	 * @return If the the check passed or not. Blank is a pass.
	 */
	public String verifyFields() {
		String result = "";

		if (_documentTextField.getText().isEmpty()) {
			result += " Dococument needs to be filled in.";
		}
		if (_categoryTextField.getText().isEmpty()) {
			result += " Category needs to be filled in.";
		}

		if (_subcategoryTextField.getText().isEmpty()) {
			result += " Subcategory needs to be filled in.";
		}

		return result;
	}

	/**
	 * Sets the fields based on if they are selected or not.
	 */
	public void setFields() {
		if (!_documentCheckBox.isSelected()) {
			_documentTextField.setText("");
		}
		if (!_categoryCheckBox.isSelected()) {
			_categoryTextField.setText("");
		}
		if (!_subcategoryCheckBox.isSelected()) {
			_subcategoryTextField.setText("");
		}
		if (!_chapterCheckBox.isSelected()) {
			_chapterTextField.setText("");
		}
		if (!_sectionCheckBox.isSelected()) {
			_sectionTextField.setText("");
		}
		if (!_pageCheckBox.isSelected()) {
			_pageTextField.setText("");
		}

	}

	/**
	 * Returns the answer location question location.
	 * 
	 * @return The answer location question location.
	 */
	public Location getAnswerLocation() {
		final String category = _categoryTextField.getText();
		final String subcat = _subcategoryTextField.getText();
		final String document = _documentTextField.getText();
		final String chapter = _chapterTextField.getText();
		final String section = _sectionTextField.getText();
		final String page = _pageTextField.getText();
		return new Location(category, subcat, document, chapter, section, page);
	}
}
