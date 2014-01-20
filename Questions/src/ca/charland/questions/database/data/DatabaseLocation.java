package ca.charland.questions.database.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import ca.charland.questions.data.Location;

import ca.charland.questions.database.data.types.DatabaseAbstractQuestion;
import ca.charland.questions.utilities.Database;

/**
 * The basic parts of a question.
 * 
 * @author Michael
 */
public final class DatabaseLocation {

	/**
	 * The name of MySQL database.
	 */
	public static final String DATABASE_NAME = "questions";

	/**
	 * The name of MySQL database table.
	 */
	public static final String TABLE_NAME = "location";

	/**
	 * The columns in the MySQL database table.
	 */
	public static enum Column {

		/**
		 * The name of the category column.
		 */
		CATEGORY,

		/**
		 * The name of the sub category column.
		 */
		SUB_CATEGORY,

		/**
		 * The name of the document column.
		 */
		DOCUMENT,

		/**
		 * The name of the chapter column.
		 */
		CHAPTER,

		/**
		 * The name of the section column.
		 */
		SECTION,

		/**
		 * The name of the page column.
		 */
		PAGE
	}

	/**
	 * The name of the database to connect too.
	 */
	private final String _databaseName;

	/**
	 * Creates a new connection to the database.
	 * 
	 * @param databaseName
	 *            The name of the database.
	 */
	public DatabaseLocation(final String databaseName) {
		_databaseName = databaseName;
	}

	/**
	 * Inserts the location into the database.
	 * 
	 * @param questionNumber
	 *            The question number to insert.
	 * @param ql
	 *            The location data to add.
	 */
	public void set(final int questionNumber, final Location ql) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		vals.put(Column.CATEGORY, ql.getCategory().replaceAll("\\'", "\\\\\'"));
		vals.put(Column.SUB_CATEGORY, ql.getSubCategory().replaceAll("\\'", "\\\\\'"));
		vals.put(Column.DOCUMENT, ql.getDocumentName().replaceAll("\\'", "\\\\\'"));
		vals.put(Column.CHAPTER, ql.getChapter().replaceAll("\\'", "\\\\\'"));
		vals.put(Column.SECTION, ql.getSection().replaceAll("\\'", "\\\\\'"));
		vals.put(Column.PAGE, ql.getPage().replaceAll("\\'", "\\\\\'"));

		connect.insert(TABLE_NAME, vals);
		connect.disconnect();
	}

	/**
	 * Gets the location from the database.
	 * 
	 * @param questionNumber
	 *            The question number to get.
	 * @return The location of question.
	 */
	public Location get(final int questionNumber) {

		final Database connect = new Database(_databaseName);

		final Hashtable<Enum<?>, Object> vals = new Hashtable<Enum<?>, Object>();
		vals.put(DatabaseAbstractQuestion.Column.QUESTION_NUMBER, "" + questionNumber);
		final ResultSet rs = connect.select(TABLE_NAME, vals);

		String category = "";
		String subCategory = "";
		String document = "";
		String chapter = "";
		String section = "";
		String page = "";
		try {
			category = rs.getString(Column.CATEGORY.toString());
			subCategory = rs.getString(Column.SUB_CATEGORY.toString());
			document = rs.getString(Column.DOCUMENT.toString());
			chapter = rs.getString(Column.CHAPTER.toString());
			section = rs.getString(Column.SECTION.toString());
			page = rs.getString(Column.PAGE.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		// remove escape characters
		category = category.replaceAll("\\\\\'", "\\'");

		connect.disconnect();

		return new Location(category, subCategory, document, chapter, section, page);
	}
}
