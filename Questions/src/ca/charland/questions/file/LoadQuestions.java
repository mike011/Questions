package ca.charland.questions.file;

import java.util.ArrayList;
import java.util.Properties;

import ca.charland.questions.data.Location;
import ca.charland.questions.data.Type;
import ca.charland.questions.data.types.AbstractQuestion;
import ca.charland.questions.data.types.FillInTheBlanks;
import ca.charland.questions.database.QuestionsDatabase;
import ca.charland.questions.database.data.DatabaseLocation;
import ca.charland.questions.database.data.types.DatabaseAbstractQuestion;
import ca.charland.questions.database.data.types.DatabaseFillInTheBlanks;
import ca.charland.questions.utilities.FileUtilities;
import ca.charland.questions.utilities.StringUtilities;

/**
 * Loads the question contents from a properties file into the database.
 * 
 * @author Michael
 */
public final class LoadQuestions {

	/**
	 * Prevents anybody from creating an instance of this class.
	 */
	private LoadQuestions() {

	}

	/**
	 * Loads the property files.
	 * 
	 * @param folder
	 *            Where to find the property files.
	 */
	public static void load(final String folder) {
		ArrayList<Properties> props = FileUtilities.loadPropertyFiles(folder);
		for (final Properties p : props) {
			// First load the common properties
			final String category = p.getProperty(DatabaseLocation.Column.CATEGORY.toString());
			final String subCategory = p.getProperty(DatabaseLocation.Column.SUB_CATEGORY.toString());
			final String documentName = p.getProperty(DatabaseLocation.Column.DOCUMENT.toString());
			final String chapter = p.getProperty(DatabaseLocation.Column.CHAPTER.toString());
			final String section = p.getProperty(DatabaseLocation.Column.SECTION.toString());
			final String page = p.getProperty(DatabaseLocation.Column.PAGE.toString());
			final Location answerLocation = new Location(category, subCategory, documentName, chapter, section, page);

			AbstractQuestion questionData = null;
			if (p.getProperty(DatabaseAbstractQuestion.Column.TYPE.toString()).equals(Type.FillInTheBlanks.toString())) {
				final String question = p.getProperty(DatabaseFillInTheBlanks.Column.QUESTION.toString());

				// Add the blanks to the properties.
				final ArrayList<String> blanks = StringUtilities.generate(question);				
				String blanksString = "";
				for (String blank : blanks) {
					blanksString += blank + " ";
				}
				System.out.println(blanksString);
				p.setProperty(DatabaseFillInTheBlanks.Column.BLANKS.toString(), blanksString);

				questionData = new FillInTheBlanks(question, blanks, answerLocation);
			}

			// Update the database.
			if (p.getProperty(DatabaseAbstractQuestion.Column.QUESTION_NUMBER.toString()) == null) {
				int questionNumber = new QuestionsDatabase().insertQuestion(questionData);
				System.out.println("Added question number <" + questionNumber + ">.");
				p.setProperty(DatabaseAbstractQuestion.Column.QUESTION_NUMBER.toString(), "" + questionNumber);

				// Finally update the properties file to indicate what was done.
				FileUtilities.updatePropertyFiles(p);
			}
		}
	}

	/**
	 * Used to load the properties.
	 * 
	 * @param args
	 *            The name of the properties file.
	 */
	public static void main(final String[] args) {
		LoadQuestions.load(args[0]);
	}
}
