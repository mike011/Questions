package ca.charland.questions.data.types;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;

/**
 * Tests the question data.
 * 
 * @author Michael
 */
public final class QuestionDataTest {

	/**
	 * The location of the test question.
	 */
	private Location _questionLocation;

	/**
	 * The abstract question.
	 */
	private AbstractQuestion _qd;

	/**
	 * The category name.
	 */
	private String _categoryName;

	/**
	 * The sub category name.
	 */
	private String _subCategory;

	/**
	 * The document name.
	 */
	private String _documentName;

	/**
	 * The chapter name.
	 */
	private String _chapter;

	/**
	 * The section name.
	 */
	private String _section;

	/**
	 * THe page number.
	 */
	private String _page;

	/**
	 * The question number.
	 */
	private int _questionNumber;

	/**
	 * The statistics of the question.
	 */
	private Statistics _questionStatistics;

	/**
	 * To show the question or not.
	 */
	private boolean _showQuestion;

	/**
	 * THe string of the question.
	 */
	private String _questionString;

	/**
	 * Run before any tests runs.
	 */
	@Before
	public void setup() {
		_categoryName = "dir";
		_documentName = "doc";
		_subCategory = "sub";
		_chapter = "chapt";
		_section = "1.3";
		_page = "341";
		_questionLocation = new Location(_categoryName, _subCategory, _documentName, _chapter, _section, _page);
		_questionStatistics = new Statistics();
		_showQuestion = true;
		_questionString = "question string";
	}

	/**
	 * Tests the question constructor.
	 */
	@Test
	public void testQuestionDataQuestionLocationString() {
		_qd = new MyQuestionData(_questionLocation, _questionString);
		assertEquals(_questionLocation, _qd.getLocation());
		assertEquals(_questionString, _qd.getQuestionString());
	}

	/**
	 * Tests another question constructor.
	 */
	@Test
	public void testQuestionDataBooleanIntQuestionStatisticsQuestionLocationString() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		assertEquals(_showQuestion, _qd.getShowQuestion());
		assertEquals(_questionNumber, _qd.getQuestionNumber());
		assertEquals(_questionStatistics.getCorrectlyAnswered(), _qd.getCorrectlyAnswered());
		assertEquals(_questionStatistics.getPercentCorrect(), _qd.getPercentCorrect());
		assertEquals(_questionStatistics.getTotalTimesAnswered(), _qd.getTotalTimesAnswered());
		assertEquals(_questionLocation, _qd.getLocation());
		assertEquals(_questionString, _qd.getQuestionString());
	}

	/**
	 * Test to get the question string.
	 */
	@Test
	public void testGetQuestionString() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		assertEquals(_questionString, _qd.getQuestionString());
	}

	/**
	 * Test to get showing the question. 
	 */
	@Test
	public void testGetShowQuestion() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		assertEquals(_showQuestion, _qd.getShowQuestion());
	}

	/**
	 * Test setting showing the question.
	 */
	@Test
	public void testSetShowQuestion() {
		_qd = new MyQuestionData(false, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		assertFalse(_qd.getShowQuestion());
		_qd.setShowQuestion();
		assertTrue(_qd.getShowQuestion());
	}

	/**
	 * Test getting the question number.
	 */
	@Test
	public void testGetQuestionNumber() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		assertEquals(_questionNumber, _qd.getQuestionNumber());
	}

	/**
	 * Test getting the difficulty of the question.
	 */
	@Test
	public void testGetDifficulty() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		final int startDifficulty = 20;
		assertEquals(startDifficulty, _qd.getDifficulty());
	}

	/**
	 * Test setting the question to being correctly answered.
	 */
	@Test
	public void testGetCorrectlyAnswered() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		assertEquals(0, _qd.getCorrectlyAnswered());
	}

	/**
	 * Test getting the total number of times the question was answered. 
	 */
	@Test
	public void testGetTotalTimesAnswered() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		assertEquals(0, _qd.getTotalTimesAnswered());
	}

	/**
	 * Test setting the question to be correctly answered. 
	 */
	@Test
	public void testSetCorrectlyAnswered() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);

		final int startDifficulty = 20;
		final int oneHundred = 100;
		assertEquals(startDifficulty, _qd.getDifficulty());
		assertEquals(0, _qd.getCorrectlyAnswered());
		assertEquals(0, _qd.getTotalTimesAnswered());
		assertEquals(0, _qd.getPercentCorrect());
		_qd.setCorrectlyAnswered();
		assertEquals(startDifficulty - 1, _qd.getDifficulty());
		assertEquals(1, _qd.getCorrectlyAnswered());
		assertEquals(1, _qd.getTotalTimesAnswered());
		assertEquals(oneHundred, _qd.getPercentCorrect());
	}

	/**
	 * Test setting the question to be incorrectly answered.
	 */
	@Test
	public void testSetIncorrectlyAnswered() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);

		final int startDifficulty = 20;
		assertEquals(startDifficulty, _qd.getDifficulty());
		assertEquals(0, _qd.getCorrectlyAnswered());
		assertEquals(0, _qd.getTotalTimesAnswered());
		assertEquals(0, _qd.getPercentCorrect());
		_qd.setIncorrectlyAnswered();
		assertEquals(startDifficulty + 1, _qd.getDifficulty());
		assertEquals(0, _qd.getCorrectlyAnswered());
		assertEquals(1, _qd.getTotalTimesAnswered());
		assertEquals(0, _qd.getPercentCorrect());
	}

	/**
	 * Test getting the correct percent.
	 */
	@Test
	public void testGetPercentCorrect() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		assertEquals(0, _qd.getPercentCorrect());
	}

	/**
	 * Test getting the question location.
	 */
	@Test
	public void testGetQuestionLocation() {
		_qd = new MyQuestionData(_showQuestion, _questionNumber, _questionStatistics, _questionLocation, _questionString);
		assertEquals(_questionLocation, _qd.getLocation());
	}
}
