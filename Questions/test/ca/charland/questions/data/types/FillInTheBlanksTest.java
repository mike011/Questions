/**
 * 
 */
package ca.charland.questions.data.types;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.test.util.Reflection;

/**
 * @author Michael
 */
public class FillInTheBlanksTest {

	/**
	 * Tests creating a basic statement.
	 */
	@Test
	public final void testCreateStatementBasic() {
		final ArrayList<String> blanks = new ArrayList<String>();
		blanks.add("Hello");
		final Object o = Reflection.runStaticMethod(FillInTheBlanks.class, "createStatement", "Hello?", blanks);
		final String out = (String) o;
		assertEquals("[0]?", out);
	}

	/**
	 * Creates a complicated top question.
	 */
	@Test
	public final void testCreateStatementComplex() {

		final ArrayList<String> blanks = new ArrayList<String>();
		blanks.add("Remember");
		blanks.add("complexity");
		blanks.add("number");
		blanks.add("enemy");
		blanks.add("maintainability");
		final Object o = Reflection.runStaticMethod(FillInTheBlanks.class, "createStatement",
				"# Remember, that complexity is the number one enemy of maintainability.", blanks);
		final String out = (String) o;

		assertEquals("# [0], that [1] is the [2] one [3] of [4].", out);
	}

	/**
	 * Test method for {@link ca.charland.questions.data.types.FillInTheBlanks#getStatement()} .
	 */
	@Test
	public final void testGetStatement() {

		final boolean showQuestion = false;
		final int questionNumber = 12345;
		final Statistics statistics = new Statistics(10, 20, 30);
		final Location locationOfAnswer = new Location("test fill in the blanks", "b", "d", "d", "e", "f");
		final String question = "Remember, that complexity is the number one enemy of maintainability.";
		final ArrayList<String> blanks = new ArrayList<String>();
		blanks.add("Remember");
		blanks.add("complexity");
		blanks.add("number");
		blanks.add("enemy");
		blanks.add("maintainability");

		final FillInTheBlanks fintb = new FillInTheBlanks(showQuestion, questionNumber, statistics, question, blanks, locationOfAnswer);
		final String result = fintb.getQuestionString();
		assertEquals("[0], that [1] is the [2] one [3] of [4].", result);
		final String answer = fintb.getAnswer();
		assertEquals(question, answer);
	}
}
