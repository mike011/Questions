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
public class LocationTest {

	/**
	 * Test method for {@link ca.charland.questions.data.Location#Location(java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testLocationStringStringStringStringStringString() {
		final String category = "category test";
		final String subCategory = "sub category test";
		final String documentName = "doc name test";
		final String chapter = "chapter test";
		final String section = "section test";
		final String page = "page test";
		final Location location = new Location(category, subCategory, documentName, chapter, section, page);

		assertEquals(category, Reflection.getString(location, "_category"));
		assertEquals(subCategory, Reflection.getString(location, "_subCategory"));
		assertEquals(documentName, Reflection.getString(location, "_documentName"));
		assertEquals(chapter, Reflection.getString(location, "_chapter"));
		assertEquals(section, Reflection.getString(location, "_section"));
		assertEquals(page, Reflection.getString(location, "_page"));
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Location#getCategory()}.
	 */
	@Test
	public final void testGetCategory() {
		final String category = "category test";
		final String subCategory = "sub category test";
		final String documentName = "doc name test";
		final String chapter = "chapter test";
		final String section = "section test";
		final String page = "page test";
		final Location location = new Location(category, subCategory, documentName, chapter, section, page);

		assertEquals(category, location.getCategory());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Location#getSubCategory()}.
	 */
	@Test
	public final void testGetSubCategory() {
		final String category = "category test";
		final String subCategory = "sub category test";
		final String documentName = "doc name test";
		final String chapter = "chapter test";
		final String section = "section test";
		final String page = "page test";
		final Location location = new Location(category, subCategory, documentName, chapter, section, page);

		assertEquals(subCategory, location.getSubCategory());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Location#getDocumentName()}.
	 */
	@Test
	public final void testGetDocumentName() {
		final String category = "category test";
		final String subCategory = "sub category test";
		final String documentName = "doc name test";
		final String chapter = "chapter test";
		final String section = "section test";
		final String page = "page test";
		final Location location = new Location(category, subCategory, documentName, chapter, section, page);

		assertEquals(documentName, location.getDocumentName());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Location#getChapter()}.
	 */
	@Test
	public final void testGetChapter() {
		final String category = "category test";
		final String subCategory = "sub category test";
		final String documentName = "doc name test";
		final String chapter = "chapter test";
		final String section = "section test";
		final String page = "page test";
		final Location location = new Location(category, subCategory, documentName, chapter, section, page);

		assertEquals(chapter, location.getChapter());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Location#getSection()}.
	 */
	@Test
	public final void testGetSection() {
		final String category = "category test";
		final String subCategory = "sub category test";
		final String documentName = "doc name test";
		final String chapter = "chapter test";
		final String section = "section test";
		final String page = "page test";
		final Location location = new Location(category, subCategory, documentName, chapter, section, page);

		assertEquals(section, location.getSection());
	}

	/**
	 * Test method for {@link ca.charland.questions.data.Location#getPage()}.
	 */
	@Test
	public final void testGetPage() {
		final String category = "category test";
		final String subCategory = "sub category test";
		final String documentName = "doc name test";
		final String chapter = "chapter test";
		final String section = "section test";
		final String page = "page test";
		final Location location = new Location(category, subCategory, documentName, chapter, section, page);

		assertEquals(page, location.getPage());
	}
}
