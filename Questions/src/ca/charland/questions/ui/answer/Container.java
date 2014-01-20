package ca.charland.questions.ui.answer;

import java.util.ArrayList;

import javax.swing.JPanel;

import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;
import ca.charland.questions.data.types.AbstractQuestion;
import ca.charland.questions.data.types.BlankAnswer;
import ca.charland.questions.data.types.CheckBox;
import ca.charland.questions.data.types.FillInTheBlanks;
import ca.charland.questions.data.types.RadioButton;
import ca.charland.questions.data.types.ShortCut;
import ca.charland.questions.database.QuestionsDatabase;
import ca.charland.questions.ui.answer.types.AbstractPanel;
import ca.charland.questions.ui.answer.types.BlankAnswerPanel;
import ca.charland.questions.ui.answer.types.CheckBoxPanel;
import ca.charland.questions.ui.answer.types.FillInTheBlanksPanel;
import ca.charland.questions.ui.answer.types.RadioButtonPanel;
import ca.charland.questions.ui.answer.types.ShortCutPanel;
import ca.charland.questions.utilities.MathUtilities;

/**
 * An abstract class that is the container for an answer.
 * 
 * @author Michael
 */
public final class Container {

	/**
	 * The current question.
	 */
	private int _currentQuestionNumber;

	/**
	 * A list of potential questions to be asked.
	 */
	private ArrayList<Integer> _questionNumbers;

	/**
	 * A list of the spots in the question numbers that will be accessed.
	 */
	private int[] _questionsList;

	/**
	 * Has the question been answered?
	 */
	private boolean _hasBeenAnswered;

	/**
	 * The data for the question.
	 */
	private AbstractQuestion _data;

	/**
	 * The panel of the question.
	 */
	private AbstractPanel _panel;

	/**
	 * The statistics for this session.
	 */
	private Statistics _session;

	/**
	 * Constructs a new answer container.
	 */
	public Container() {

		final QuestionsDatabase db = new QuestionsDatabase();

		_currentQuestionNumber = 0;
		_questionNumbers = db.getQuestionNumbers();
		if (_questionNumbers.size() != 0) {
			_questionsList = new int[_questionNumbers.size()];
			_questionsList = MathUtilities.generateRandomArray(_questionNumbers.size());
		}

		_hasBeenAnswered = false;

		_session = new Statistics();

		nextQuestion();
	}

	/**
	 * Proceeds to the next question.
	 */
	public void nextQuestion() {
		boolean exit = false;
		while (!exit) {
			++_currentQuestionNumber;
			if (_currentQuestionNumber >= _questionsList.length) {
				_currentQuestionNumber = 0;
			}

			final QuestionsDatabase questionDatabase = new QuestionsDatabase();

			int questionNumber = _questionNumbers.get(_questionsList[_currentQuestionNumber]);
			assert questionNumber >= 0 : "No question number returned";

			_data = questionDatabase.getQuestion(questionNumber);
			assert _data != null : "For the question number <" + questionNumber + "> No data returned from the database";

			exit = true;
			if (!_data.getShowQuestion()) {
				exit = false;
			}
		}

		if (_data.getType().equals(Type.RadioButton)) {
			_panel = new RadioButtonPanel((RadioButton) _data, _session);
		} else if (_data.getType().equals(Type.CheckBox)) {
			_panel = new CheckBoxPanel((CheckBox) _data, _session);
		} else if (_data.getType().equals(Type.BlankAnswer)) {
			_panel = new BlankAnswerPanel((BlankAnswer) _data, _session);
		} else if (_data.getType().equals(Type.BlankAnswer)) {
			_panel = new BlankAnswerPanel((BlankAnswer) _data , _session);
		} else if (_data.getType().equals(Type.FillInTheBlanks)) {
			_panel = new FillInTheBlanksPanel((FillInTheBlanks) _data, _session);
		} else if (_data.getType().equals(Type.ShortCut)) {
			_panel = new ShortCutPanel((ShortCut) _data, _session);
		} else {
			throw new RuntimeException("Type <" + _data.getType() + "> not supported.");
		}
	}

	/**
	 * Update the question.
	 */
	public void updateQuestion() {

		final Thread t = new Thread() {
			@Override
			public void run() {
				final QuestionsDatabase db = new QuestionsDatabase();
				db.updateQuestion(_data);
			}
		};
		t.start();

	}

	/**
	 * Returns the question data.
	 * 
	 * @return The question data.
	 */
	public AbstractQuestion getQuestionData() {
		return _data;
	}

	/**
	 * Returns the panel.
	 * 
	 * @return The panel.
	 */
	public JPanel getPanel() {
		assert _panel != null : "The panel is null!";
		return _panel.getQuestionPanel();
	}

	/**
	 * Returns if the answer is correct or not.
	 * 
	 * @return If the answer is correct or not.
	 */
	public boolean isAnswerCorrect() {
		return _panel.isAnswerCorrect();
	}

	/**
	 * Returns if to the show the question or not.
	 * 
	 * @return If to the show the question or not.
	 */
	public boolean getShowQuestion() {
		return _data.getShowQuestion();
	}

	/**
	 * Sets the question as being answered.
	 */
	public void setHasBeenAnswered() {
		_hasBeenAnswered = true;
	}

	/**
	 * Returns if the questions has been answered or not.
	 * 
	 * @return If the questions has been answered or not.
	 */
	public boolean getHasBeenAnswered() {
		return _hasBeenAnswered;
	}
}
