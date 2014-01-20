package ca.charland.questions.ui.answer.types;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.types.AbstractQuestion;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

/**
 * The panel that questions are answered in.
 * 
 * @author Michael
 */
public abstract class AbstractPanel implements ActionListener {

	/**
	 * Handles mouse events. Radio Button panel uses it.
	 * 
	 * @param e
	 *            Does not do anything.
	 */
	public void actionPerformed(final ActionEvent e) {
	}

	/**
	 * The width of the screen.
	 */
	static final int SCREEN_WIDTH = 600;

	/**
	 * The height of the screen.
	 */
	static final int SCREEN_HEIGHT = 375;

	/**
	 * The amount to shift the text by.
	 */
	static final double TEXT_ADJUST = 0.15;

	/**
	 * The minimum spacing between components.
	 */
	static final int MINIMUM_SPACING = 5;

	/**
	 * The answer label.
	 */
	private JLabel _answerLabel = new JLabel();

	/**
	 * The text beside the lab.
	 */
	private JLabel _answerTextLabel = new JLabel();

	/**
	 * The text for the section.
	 */
	private JLabel _sectionTextLabel = new JLabel();

	/**
	 * The label for the section.
	 */
	private JLabel _sectionLabel = new JLabel();

	/**
	 * The text for the question number.
	 */
	private JLabel _questionNumberTextLabel = new JLabel();

	/**
	 * The label for the difficulty.
	 */
	protected JLabel _difficultyLabel = new JLabel();

	/**
	 * The label for the total answered.
	 */
	protected JLabel _totalAnsweredLabel = new JLabel();

	/**
	 * The label for the question.
	 */
	protected JLabel _questionLabel = new JLabel();

	/**
	 * The data of the question.
	 */
	protected AbstractQuestion _questionData;

	/**
	 * The layout of this class which is spring.
	 */
	protected SpringLayout _springLayout;

	/**
	 * The panel.
	 */
	protected JPanel _questionPanel = new JPanel();

	/**
	 * The top panel.
	 */
	protected JPanel _top = new JPanel();

	/**
	 * The middle panel.
	 */
	protected JPanel _middle = new JPanel();

	/**
	 * The bottom panel.
	 */
	private JPanel _bottom = new JPanel();

	/**
	 * Holds all the statistics.
	 */
	private JTable _statisticsTable;

	/**
	 * Statistics about this session.
	 */
	protected Statistics _session;

	/**
	 * The first part of the question label.
	 */
	protected JLabel _beginLabel;

	/**
	 * The second part (if necessary) of the question label.
	 */
	protected JLabel _endLabel;

	/**
	 * Set the data for the answer label.
	 * 
	 * @param text
	 *            Sets the text of the answer label.
	 */
	public final void setAnswerLabel(final String text) {
		_answerLabel.setText(text);
	}

	/**
	 * Creates a new answer panel.
	 * 
	 * @param questionData
	 *            The data of the panel.
	 * @param session
	 *            Statistics for the session.
	 */
	public AbstractPanel(final AbstractQuestion questionData, final Statistics session) {
		_questionData = questionData;
		_session = session;
	}

	/**
	 * Gets the JPanel.
	 * 
	 * @return The panel.
	 */
	public final JPanel getQuestionPanel() {
		assert _questionPanel != null : "The panel of the question is null!";
		return _questionPanel;
	}

	/**
	 * Create a new panel.
	 */
	protected final void addPanel() {
		_questionPanel = new JPanel();
		_springLayout = new SpringLayout();
		_questionPanel.setLayout(_springLayout);

		this.addTopPanel();
		this.addMiddlePanel();
		this.addBottomPanel();

		final JLabel temp = new JLabel();
		_springLayout.putConstraint(SpringLayout.EAST, _questionPanel, SCREEN_WIDTH, SpringLayout.WEST, temp);
		_springLayout.putConstraint(SpringLayout.SOUTH, _questionPanel, SCREEN_HEIGHT, SpringLayout.SOUTH, temp);
	}

	/**
	 * Adds the top panel.
	 */
	protected final void addTopPanel() {
		_top = createTopPartOfQuestion();
		_springLayout.putConstraint(SpringLayout.NORTH, _top, 10, SpringLayout.NORTH, _questionPanel);
		_questionPanel.add(_top);
	}

	/**
	 * Adds the middle panel.
	 */
	protected abstract void addMiddlePanel();

	/**
	 * Creates the bottom panel.
	 */
	protected final void addBottomPanel() {
		_bottom = createBottomPartOfQuestion();

		final JLabel temp = new JLabel();
		_springLayout.putConstraint(SpringLayout.WEST, _bottom, MINIMUM_SPACING, SpringLayout.WEST, temp);
		_springLayout.putConstraint(SpringLayout.NORTH, _bottom, 340, SpringLayout.NORTH, _top);
		_questionPanel.add(_bottom);
	}

	/**
	 * The top part of the question.
	 * 
	 * @return The top part of the question.
	 */
	private JPanel createTopPartOfQuestion() {

		final SpringLayout springLayout = new SpringLayout();
		final JPanel jPanel = new JPanel(springLayout);

		// the text of the section
		_sectionTextLabel = new JLabel("Section:  ");
		springLayout.putConstraint(SpringLayout.WEST, _sectionTextLabel, MINIMUM_SPACING, SpringLayout.WEST, jPanel);
		springLayout.putConstraint(SpringLayout.NORTH, _sectionTextLabel, MINIMUM_SPACING, SpringLayout.NORTH, jPanel);
		jPanel.add(_sectionTextLabel);

		// the value of the section
		String sectionString = _questionData.getLocation().getCategory();
		sectionString += " - " + _questionData.getLocation().getSubCategory();
		_sectionLabel = new JLabel(sectionString);
		springLayout.putConstraint(SpringLayout.WEST, _sectionLabel, MINIMUM_SPACING, SpringLayout.EAST, _sectionTextLabel);
		springLayout.putConstraint(SpringLayout.NORTH, _sectionLabel, MINIMUM_SPACING, SpringLayout.NORTH, jPanel);
		jPanel.add(_sectionLabel);

		final JPanel statistics = new JPanel(false);
		statistics.setLayout(new BoxLayout(statistics, BoxLayout.X_AXIS));
		springLayout.putConstraint(SpringLayout.WEST, statistics, MINIMUM_SPACING, SpringLayout.WEST, jPanel);
		springLayout.putConstraint(SpringLayout.NORTH, statistics, MINIMUM_SPACING * 3, SpringLayout.SOUTH, _sectionTextLabel);

		statistics.add(new JLabel("Statistics: "));
		final Object[] columnNames = { "", "Difficulty", "% Correct", "Total" };
		final Object[][] data = { { "", "Difficulty", "% Correct", "Total" },
				{ "Question", _questionData.getDifficulty(), _questionData.getPercentCorrect(), _questionData.getTotalTimesAnswered() },
				{ "Session", _session.getDifficulty(), _session.getPercentCorrect(), _session.getTotalTimesAnswered() } };
		_statisticsTable = new JTable(data, columnNames);
		_statisticsTable.setEnabled(false);
		JLabel renderer = ((JLabel) _statisticsTable.getDefaultRenderer(Object.class));
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		final int colorCode = 240;
		_statisticsTable.setBackground(new Color(colorCode, colorCode, colorCode));
		statistics.add(_statisticsTable);
		jPanel.add(statistics);

		// The value for the question.
		String begin = "Question #" + new Integer(_questionData.getQuestionNumber()).toString() + ": ";
		String middle = "";
		String end = "";
		if (_questionData.getQuestionString().length() > (int) (TEXT_ADJUST * SCREEN_WIDTH)) {
			begin += _questionData.getQuestionString().substring(0, (int) (TEXT_ADJUST * SCREEN_WIDTH - 15));
			middle =_questionData.getQuestionString().substring((int) (TEXT_ADJUST * SCREEN_WIDTH - 15), (int) (TEXT_ADJUST * SCREEN_WIDTH));
			end = _questionData.getQuestionString().substring((int) (TEXT_ADJUST * SCREEN_WIDTH));
			int splitPoint = middle.indexOf(' ');
			begin = begin + middle.substring(0, splitPoint);
			end = middle.substring(splitPoint + 1) + end;
		} else {
			begin += _questionData.getQuestionString().substring(0);
			middle = "";
			end = "";
		}

		int spacing = MINIMUM_SPACING;
		_beginLabel = new JLabel(begin);
		springLayout.putConstraint(SpringLayout.WEST, _beginLabel, MINIMUM_SPACING, SpringLayout.WEST, jPanel);
		springLayout.putConstraint(SpringLayout.NORTH, _beginLabel, MINIMUM_SPACING * 3, SpringLayout.SOUTH, statistics);
		jPanel.add(_beginLabel);

		_endLabel = new JLabel(end);
		if (!end.equals("")) {			
			springLayout.putConstraint(SpringLayout.WEST, _endLabel, MINIMUM_SPACING, SpringLayout.WEST, jPanel);
			springLayout.putConstraint(SpringLayout.NORTH, _endLabel, MINIMUM_SPACING, SpringLayout.SOUTH, _beginLabel);
			jPanel.add(_endLabel);
			spacing *= 5;
		}

		springLayout.putConstraint(SpringLayout.EAST, jPanel, SCREEN_WIDTH, SpringLayout.EAST, _questionNumberTextLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, jPanel, spacing, SpringLayout.SOUTH, _beginLabel);

		jPanel.setOpaque(true);

		return jPanel;
	}

	/**
	 * The bottom part of the question.
	 * 
	 * @return The bottom part of the question panel.
	 */
	private JPanel createBottomPartOfQuestion() {

		final JPanel jPanel = new JPanel();

		// the text for the answer
		_answerTextLabel = new JLabel("Answer: ");
		jPanel.add(_answerTextLabel);

		// the value of the answer
		_answerLabel = new JLabel("");
		jPanel.add(_answerLabel);

		jPanel.setOpaque(true);

		return jPanel;
	}

	/**
	 * Is the answer chosen correct?
	 * 
	 * @return Is the answer chosen correct?
	 */
	public abstract boolean isAnswerCorrect();

	/**
	 * Updates the fields of the panel.
	 */
	public final void updatePanel() {
		_statisticsTable.setValueAt(new Integer(_questionData.getDifficulty()).toString(), 1, 1);
		_statisticsTable.setValueAt(new Integer(_questionData.getPercentCorrect()).toString(), 1, 2);
		_statisticsTable.setValueAt(new Integer(_questionData.getTotalTimesAnswered()).toString(), 1, 3);
		_totalAnsweredLabel = new JLabel();

		_statisticsTable.setValueAt(new Integer(_session.getDifficulty()).toString(), 2, 1);
		_statisticsTable.setValueAt(new Integer(_session.getPercentCorrect()).toString(), 2, 2);
		_statisticsTable.setValueAt(new Integer(_session.getTotalTimesAnswered()).toString(), 2, 3);
	}
}
