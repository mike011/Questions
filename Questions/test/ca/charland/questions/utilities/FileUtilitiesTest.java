package ca.charland.questions.utilities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Properties;

import org.junit.Test;

/**
 * Tests the file utilities.
 * 
 * @author Michael
 */
public final class FileUtilitiesTest {

	/**
	 * Tests loading a properties file that does not exist.
	 */
	@Test (expected = java.lang.RuntimeException.class)
	public void testLoadPropertiesFileNotExist() {
		FileUtilities.loadPropertiesFile("TEST");
	}

	/**
	 * Tests loading a properties file.
	 */
	@Test
	public void testLoadPropertiesFile() {
		Properties props = FileUtilities.loadPropertiesFile("H:\\p4\\dev\\java\\Questions\\Current\\test\\ca\\charland\\questions\\utilities\\test.properties");
		assertEquals("1234", props.getProperty("test"));
		assertEquals("another", props.getProperty("test2"));
		assertEquals(null, props.getProperty("bad"));
	}

	/**
	 * Tests loading properties files but specifying the wrong folder.
	 */
	@Test (expected = java.lang.RuntimeException.class)
	public void testLoadPropertyFilesNoExist() {
		FileUtilities.loadPropertyFiles("TEST");
	}

	/**
	 * Tests loading properties files but specifying the wrong folder.
	 */
	@Test (expected = java.lang.RuntimeException.class)
	public void testLoadPropertyFilesLoadAFile() {
		final String filename = "H:\\p4\\dev\\java\\Questions\\Current\\test\\ca\\charland\\questions\\utilities\\test.properties";
		FileUtilities.loadPropertyFiles(filename);
	}

	/**
	 * Tests loading properties files but specifying a folder that doesn't have any property files in it.
	 */
	@Test (expected = java.lang.RuntimeException.class)
	public void testLoadPropertyFilesNoProperties() {
		final String folder = "H:\\p4\\dev\\java\\Questions\\Current\\test\\ca\\charland\\questions\\";
		FileUtilities.loadPropertyFiles(folder);
	}

	/**
	 * Tests loading properties files.
	 */
	@Test
	public void testLoadPropertyFiles() {
		final String folder = "H:\\p4\\dev\\java\\Questions\\Current\\test\\ca\\charland\\questions\\utilities";
		ArrayList<Properties> props = FileUtilities.loadPropertyFiles(folder);
		assertEquals(2, props.size());
	}
}
