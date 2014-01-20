package ca.charland.questions.utilities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Tests the string utilities.
 * 
 * @author Michael
 *
 */
public class StringUtilitiesTest {

	/**
	 * There are no special characters.
	 */
	@Test
	public final void testSplitBasic() {
		final ArrayList<?> splits = StringUtilities.split("test a");
		assertEquals("test", splits.get(0));
		assertEquals(" ", splits.get(1));
		assertEquals("a", splits.get(2));	
		assertEquals(3, splits.size());
		
	}

	/**
	 * Lots of special characters.
	 */
	@Test
	public final void testSplitSpecial() {
		final ArrayList<String> splits = StringUtilities.split("# Remember, that complexity is the number one enemy of maintainability.");
		int x = 0;		
		assertEquals("#", splits.get(x));
		assertEquals(" ", splits.get(++x));
		assertEquals("Remember", splits.get(++x));
		assertEquals(",", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("that", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("complexity", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("is", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("the", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("number", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("one", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("enemy", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("of", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("maintainability", splits.get(++x));
		assertEquals(".", splits.get(++x));
		assertEquals(23, splits.size());		
	}

	/**
	 * Lots of special characters.
	 */
	@Test
	public final void testSplitLotsOfSpecial() {
		final ArrayList<String> splits = StringUtilities.split("Hello? Hello! HI! What, test: !@#$%^&*()-=");

		int x = 0;
		assertEquals("Hello", splits.get(x));
		assertEquals("?", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("Hello", splits.get(++x));
		assertEquals("!", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("HI", splits.get(++x));
		assertEquals("!", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("What", splits.get(++x));
		assertEquals(",", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("test", splits.get(++x));
		assertEquals(":", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("!", splits.get(++x));
		assertEquals("@", splits.get(++x));
		assertEquals("#", splits.get(++x));
		assertEquals("$", splits.get(++x));
		assertEquals("%", splits.get(++x));
		assertEquals("^", splits.get(++x));
		assertEquals("&", splits.get(++x));
		assertEquals("*", splits.get(++x));
		assertEquals("(", splits.get(++x));
		assertEquals(")", splits.get(++x));
		assertEquals("-", splits.get(++x));
		assertEquals("=", splits.get(++x));
		assertEquals(27, splits.size());
	}
	
	/**
	 * Test a sentence with contractions.
	 */
	@Test
	public final void testSplitContractions() {
		final ArrayList<String> splits = StringUtilities.split("don't can't it's start's a-d");
		int x = 0;
		assertEquals("don't", splits.get(x));
		assertEquals(" ", splits.get(++x));
		assertEquals("can't", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("it's", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("start's", splits.get(++x));
		assertEquals(" ", splits.get(++x));
		assertEquals("a-d", splits.get(++x));
		assertEquals(9, splits.size());
	}

	/**
	 * Simple test to make sure the words are being generated.
	 */
	@Test
	public final void testGenerate() {
		final ArrayList<String> result = StringUtilities.generate("aaaa bbb cc d.");
		assertEquals("aaaa", result.get(0));
		assertEquals("bbb", result.get(1));
		assertEquals("cc", result.get(2));
		assertEquals(3, result.size());
	}
	
	/**
	 * A test to make sure a repeated word is not sent back as a possible blank.
	 */
	@Test
	public final void testGenerateRepeated() {
		final ArrayList<String> result = StringUtilities.generate("aaaa aaaa bbb cc d.");		
		assertEquals("aaaa", result.get(0));		
		assertEquals("bbb", result.get(1));
		assertEquals(2, result.size());
	}
	
	/**
	 * A test to make sure a repeated word in a different case is not sent back as a possible blank.
	 */
	@Test
	public final void testGenerateRepeatedDifferentCase() {
		final ArrayList<String> result = StringUtilities.generate("aaaa aaaa Aaaa aAaa aaAa aaaA aAAa AAAA AaAa bbb cc d.");
		assertEquals("aaaa", result.get(0));
		assertEquals(1, result.size());
	}
	
	/**
	 * A test to make sure that when many words are added. The max result is only five.
	 */
	@Test
	public final void testGenerateMany() {
		ArrayList<String> result = StringUtilities.generate("aaaa bbbb cccc dddd eeee ffff gggg hhh iii jjj kkk lll mmm nnn ooo.");
		assertEquals("aaaa", result.get(0));
		assertEquals("bbbb", result.get(1));
		assertEquals("cccc", result.get(2));
		assertEquals("dddd", result.get(3));
		assertEquals("eeee", result.get(4));
		assertEquals(5, result.size());
	}	
}
