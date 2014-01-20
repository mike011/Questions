/**
 * 
 */
package ca.charland.questions.database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ca.charland.questions.data.types.CheckBox;
import ca.charland.questions.database.data.types.DatabaseAbstractQuestionTest;
import ca.charland.questions.database.data.types.DatabaseCheckBoxTest;

/**
 * @author Michael
 */
public final class QuestionsDatabaseTestCheckBox {

	/**
	 * Test method for {@link ca.charland.questions.database.QuestionsDatabase#getQuestionNumbers()}.
	 */
	@Test
	public void testGetQuestionNumbers() {

		final int questionNumber = DatabaseCheckBoxTest.insertCheckBox("DatabaseTestCheckBox.testGetQuestionNumbers").getQuestionNumber();

		final QuestionsDatabase db = new QuestionsDatabase(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);

		final ArrayList<Integer> questionNumbers = db.getQuestionNumbers();
		assertTrue("No data returned.", questionNumbers.size() > 0);
		assertTrue(questionNumbers.contains(questionNumber));

		DatabaseCheckBoxTest.deleteCheckBox(questionNumber);
	}

	/**
	 * Test getting the next question.
	 */
	@Test
	public void testGetQuestion() {

		final CheckBox expected = DatabaseCheckBoxTest.insertCheckBox("DatabaseTestCheckBox.testGetQuestion");

		final QuestionsDatabase db = new QuestionsDatabase(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final CheckBox actual = (CheckBox) db.getQuestion(expected.getQuestionNumber());

		DatabaseCheckBoxTest.assertEquals(expected, actual);

		DatabaseCheckBoxTest.deleteCheckBox(expected.getQuestionNumber());
	}

	/**
	 * Test inserting a question.
	 */
	@Test
	public void testInsertQuestion() {

		final CheckBox expected = DatabaseCheckBoxTest.createCheckBox(-1, "DatabaseTestCheckBox.testInsertQuestion");

		final QuestionsDatabase db = new QuestionsDatabase(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		final int questionNumber = db.insertQuestion(expected);

		final CheckBox updated = DatabaseCheckBoxTest.createCheckBox(questionNumber, "DatabaseTestCheckBox.testInsertQuestion");
		final CheckBox actual = (CheckBox) db.getQuestion(updated.getQuestionNumber());

		DatabaseCheckBoxTest.assertEquals(updated, actual);

		DatabaseCheckBoxTest.deleteCheckBox(actual.getQuestionNumber());
	}

	/**
	 * Test updating a question.
	 */
	@Test
	public void testUpdateQuestion() {

		final CheckBox expected = DatabaseCheckBoxTest.insertCheckBox("DatabaseTestCheckBox.testUpdateQuestion");
		expected.setCorrectlyAnswered();

		final QuestionsDatabase db = new QuestionsDatabase(DatabaseAbstractQuestionTest.TEST_DATABASE_NAME);
		db.updateQuestion(expected);

		final CheckBox actual = (CheckBox) db.getQuestion(expected.getQuestionNumber());

		DatabaseCheckBoxTest.assertEquals(expected, actual);

		DatabaseCheckBoxTest.deleteCheckBox(actual.getQuestionNumber());
	}
}
