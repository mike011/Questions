/**
 * 
 */
package ca.charland.questions.data;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.charland.test.util.Reflection;

/**
 * @author Michael
 */
public class StatisticsTest {

	/**
	 * Test method for {@link ca.charland.questions.data.Statistics#Statistics()}.
	 */
	@Test
	public final void testStatistics() {
		final Statistics statistics = new Statistics();
		assertEquals(Reflection.getInt(statistics, "START_DIFFICULTY"), Reflection.getInt(statistics, "_difficulty"));
		assertEquals(0, Reflection.getInt(statistics, "_correctlyAnswered"));
		assertEquals(0, Reflection.getInt(statistics, "_totalTimesAnswered"));
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Statistics#Statistics(int, int, int)}.
	 */
	@Test
	public final void testStatisticsIntIntInt() {
		final int difficultly = 25;
		final int correctlyAnswered = 200;
		final int totalTimesAnswered = 25;
		final Statistics statistics = new Statistics(difficultly, correctlyAnswered, totalTimesAnswered);
		assertEquals(difficultly, Reflection.getInt(statistics, "_difficulty"));
		assertEquals(correctlyAnswered, Reflection.getInt(statistics, "_correctlyAnswered"));
		assertEquals(totalTimesAnswered, Reflection.getInt(statistics, "_totalTimesAnswered"));
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Statistics#getDifficulty()}.
	 */
	@Test
	public final void testGetDifficulty() {
		final int difficultly = 25;
		final int correctlyAnswered = 200;
		final int totalTimesAnswered = 25;
		final Statistics statistics = new Statistics(difficultly, correctlyAnswered, totalTimesAnswered);
		assertEquals(difficultly, statistics.getDifficulty());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Statistics#getCorrectlyAnswered()}.
	 */
	@Test
	public final void testGetCorrectlyAnswered() {
		final int difficultly = 25;
		final int correctlyAnswered = 200;
		final int totalTimesAnswered = 25;
		final Statistics statistics = new Statistics(difficultly, correctlyAnswered, totalTimesAnswered);
		assertEquals(correctlyAnswered, statistics.getCorrectlyAnswered());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Statistics#getTotalTimesAnswered()}.
	 */
	@Test
	public final void testGetTotalTimesAnswered() {
		final int difficultly = 25;
		final int correctlyAnswered = 200;
		final int totalTimesAnswered = 25;
		final Statistics statistics = new Statistics(difficultly, correctlyAnswered, totalTimesAnswered);
		assertEquals(totalTimesAnswered, statistics.getTotalTimesAnswered());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Statistics#setCorrectlyAnswered()}.
	 */
	@Test
	public final void testSetCorrectlyAnswered() {
		final int difficultly = 10;
		final int correctlyAnswered = 200;
		final int totalTimesAnswered = 25;
		final Statistics statistics = new Statistics(difficultly, correctlyAnswered, totalTimesAnswered);

		assertEquals(difficultly, statistics.getDifficulty());
		assertEquals(correctlyAnswered, statistics.getCorrectlyAnswered());
		assertEquals(totalTimesAnswered, statistics.getTotalTimesAnswered());

		statistics.setCorrectlyAnswered();

		assertEquals(difficultly - 1, statistics.getDifficulty());
		assertEquals(correctlyAnswered + 1, statistics.getCorrectlyAnswered());
		assertEquals(totalTimesAnswered + 1, statistics.getTotalTimesAnswered());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Statistics#setIncorrectlyAnswered()}.
	 */
	@Test
	public final void testSetIncorrectlyAnswered() {
		final int difficultly = 25;
		final int correctlyAnswered = 200;
		final int totalTimesAnswered = 300;
		final Statistics statistics = new Statistics(difficultly, correctlyAnswered, totalTimesAnswered);

		assertEquals(difficultly, statistics.getDifficulty());
		assertEquals(correctlyAnswered, statistics.getCorrectlyAnswered());
		assertEquals(totalTimesAnswered, statistics.getTotalTimesAnswered());

		statistics.setIncorrectlyAnswered();

		assertEquals(difficultly + 1, statistics.getDifficulty());
		assertEquals(correctlyAnswered, statistics.getCorrectlyAnswered());
		assertEquals(totalTimesAnswered + 1, statistics.getTotalTimesAnswered());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Statistics#getPercentCorrect()}.
	 */
	@Test
	public final void testGetPercentCorrect() {
		final int difficultly = 25;
		final int correctlyAnswered = 200;
		final int totalTimesAnswered = 300;
		final Statistics statistics = new Statistics(difficultly, correctlyAnswered, totalTimesAnswered);

		final int percent = statistics.getPercentCorrect();
		final double expected = (double) correctlyAnswered / (double) totalTimesAnswered * 100d;
		assertEquals((int)expected, (int)percent);
	}
}
