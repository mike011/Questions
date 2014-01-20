/**
 * 
 */
package ca.charland.questions.data.types;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Statistics;
import ca.charland.questions.data.Type;
import ca.charland.test.util.Reflection;

/**
 * Runs tests against the short cut keys class.
 * 
 * @author Michael
 */
public class ShortCutTest {

	/**
	 * Test method for {@link ca.charland.questions.data.types.ShortCutKey#ShortCutKey(boolean, int, Statistics, Location, String, String)()}.
	 */
	@Test
	public final void testShortCutKeyBooleanIntStatisticsLocationStringString() {
		Location questionLocation = new Location("", "", "", "", "", "");
		final int qNumber = 1234;
		final String shortCut = "CTRL + SHIFT + u";
		ShortCut sck = new ShortCut(false, qNumber, new Statistics(), "ABC", shortCut, questionLocation);
		assertEquals(shortCut, Reflection.getString(sck, "_shortCut"));
		assertEquals(Type.ShortCut, (Type)Reflection.getObject(sck, "_type"));
	}

	/**
	 * Test method for {@link ca.charland.questions.data.types.ShortCut#getShortCut()}.
	 */
	@Test
	public final void testGetShortCut() {
		Location questionLocation = new Location("", "", "", "", "", "");
		final int qNumber = 1234;
		final String shortCut = "CTRL + x";
		ShortCut sck = new ShortCut(false, qNumber, new Statistics(), "ABC", shortCut, questionLocation);
		assertEquals(shortCut, sck.getShortCut());
	}
}
