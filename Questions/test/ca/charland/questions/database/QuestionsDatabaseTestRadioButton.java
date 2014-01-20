package ca.charland.questions.database;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import ca.charland.questions.data.types.RadioButton;
import ca.charland.questions.database.data.types.DatabaseRadioButtonTest;

/***
 * Tests a radio button question.
 * @author Michael
 *
 */
public class QuestionsDatabaseTestRadioButton {

	/**
	 * The name of the test database.
	 */
	public static final String NAME = "questions_test";

	/**
	 * Test method for {@link ca.charland.questions.database.QuestionsDatabase#getQuestionNumbers()}.
	 */
	@Test
	public final void testGetQuestionNumbers() {

		final int questionNumber = DatabaseRadioButtonTest.insertRadioButton("DatabaseTestRadioButton.testGetQuestionNumbers").getQuestionNumber();

		final QuestionsDatabase db = new QuestionsDatabase(NAME);

		final ArrayList<Integer> questionNumbers = db.getQuestionNumbers();
		assertTrue("No data returned.", questionNumbers.size() > 0);
		assertTrue(questionNumbers.contains(questionNumber));

		DatabaseRadioButtonTest.deleteRadioButton(questionNumber);
	}

	/**
	 * Test getting a question.
	 */
	@Test
	public final void testGetQuestion() {

		final RadioButton expected = DatabaseRadioButtonTest.insertRadioButton("DatabaseTestRadioButton.testGetQuestion");

		final QuestionsDatabase db = new QuestionsDatabase(NAME);
		final RadioButton actual = (RadioButton) db.getQuestion(expected.getQuestionNumber());

		DatabaseRadioButtonTest.assertEquals(expected, actual);

		DatabaseRadioButtonTest.deleteRadioButton(actual.getQuestionNumber());
	}

	/**
	 * Test inserting a question.
	 */
	@Test
	public final void testInsertQuestion() {

		final RadioButton expected = DatabaseRadioButtonTest.createRadioButton(-1, "DatabaseTestRadioButton.testInsertQuestion");

		final QuestionsDatabase db = new QuestionsDatabase(NAME);
		final int questionNumber = db.insertQuestion(expected);

		final RadioButton updated = DatabaseRadioButtonTest.createRadioButton(questionNumber, "DatabaseTestRadioButton.testInsertQuestion");
		final RadioButton actual = (RadioButton) db.getQuestion(updated.getQuestionNumber());

		DatabaseRadioButtonTest.assertEquals(updated, actual);

		DatabaseRadioButtonTest.deleteRadioButton(actual.getQuestionNumber());

	}

	/**
	 * Test updating a question.
	 */
	@Test
	public final void testUpdateQuestion() {

		final RadioButton expected = DatabaseRadioButtonTest.insertRadioButton("DatabaseTestRadioButton.testUpdateQuestion");
		expected.setCorrectlyAnswered();

		final QuestionsDatabase db = new QuestionsDatabase(NAME);
		db.updateQuestion(expected);

		final RadioButton actual = (RadioButton) db.getQuestion(expected.getQuestionNumber());

		DatabaseRadioButtonTest.assertEquals(expected, actual);

		DatabaseRadioButtonTest.deleteRadioButton(actual.getQuestionNumber());
	}
}
