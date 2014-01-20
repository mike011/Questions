package ca.charland.questions.data;

/**
 * Holds location information about the question such as what the document is named that the question came from.
 * 
 * @author Michael
 */
public class Location {

	/**
	 * The name of the category the question is stored in.
	 */
	private final String _category;

	/**
	 * The name of the sub category the question is stored in.
	 */
	private final String _subCategory;

	/**
	 * The name of the document the question came from.
	 */
	private final String _documentName;

	/**
	 * The name of the chapter the question came from.
	 */
	private final String _chapter;

	/**
	 * The name of the section the question came from.
	 */
	private final String _section;

	/**
	 * The page the question came from.
	 */
	private final String _page;

	/**
	 * Creates a new QuestionLocation.
	 * 
	 * @param category
	 *            The type of the question.
	 * @param subCategory
	 *            The sub type of the question.
	 * @param documentName
	 *            The name of the document the question came from.
	 * @param chapter
	 *            The name of the chapter the question came from.
	 * @param section
	 *            The name of the section the question came from.
	 * @param page
	 *            The page the question came from.
	 */
	public Location(final String category, final String subCategory, final String documentName, final String chapter, final String section,
			final String page) {
		_category = category;
		_subCategory = subCategory;
		_documentName = documentName;
		_chapter = chapter;
		_section = section;
		_page = page;
	}

	/**
	 * Gets the name of the category the question is in.
	 * 
	 * @return The name of the category the question is in.
	 */
	public final String getCategory() {
		return _category;
	}

	/**
	 * Gets the name of the sub category the question is in.
	 * 
	 * @return The name of the sub category the question is in.
	 */
	public final String getSubCategory() {
		return _subCategory;
	}

	/**
	 * Gets the name of the document the question came from.
	 * 
	 * @return The name of the document the question came from.
	 */
	public final String getDocumentName() {
		return _documentName;
	}

	/**
	 * Gets the name of the chapter the question came from.
	 * 
	 * @return The name of the chapter the question came from.
	 */
	public final String getChapter() {
		return _chapter;
	}

	/**
	 * Gets the name of the section the question came from.
	 * 
	 * @return The name of the section the question came from.
	 */
	public final String getSection() {
		return _section;
	}

	/**
	 * Gets the page the question came from.
	 * 
	 * @return The page the question came from.
	 */
	public final String getPage() {
		return _page;
	}

	/**
	 * Returns a nicely formated string of the Question Location.
	 * 
	 * @return A nicely formated string of the Question Location.
	 */
	@Override
	public final String toString() {
		String r = "";
		r += "Type = " + getCategory() + "\n";
		r += "Document Name = " + getDocumentName() + "\n";
		r += "Chapter = " + getChapter() + "\n";
		r += "Section = " + getSection() + "\n";
		r += "Page = " + getPage();

		return r;
	}
}
