package ca.charland.questions.utilities;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Helpful string utilities.
 * 
 * @author Michael
 */
public final class StringUtilities {

	/**
	 * Prevents an instance from created.
	 */
	private StringUtilities() {
	}

	/**
	 * These are the maximum amount of words that can be generated.
	 */
	private static final int MAX_WORDS = 5;

	/**
	 * Splits a statement up into an array list of words.
	 * 
	 * @param statement
	 *            The statement to split
	 * @return The array list of words.
	 */
	public static ArrayList<String> split(final String statement) {

		final ArrayList<String> splits = new ArrayList<String>();
		StringBuffer cleanedWord = new StringBuffer();
		final char[] chars = statement.trim().toCharArray();
		for (char ch : chars) {
			
			if (Character.isLetter(ch) || ch == '\'' || ch == '-') {
				cleanedWord.append(ch);
			} else {
				if (cleanedWord.length() > 0) {
					splits.add(cleanedWord.toString());
				}
				cleanedWord.delete(0, cleanedWord.length());
				splits.add("" + ch);
			}
		}
		
		// Adds the last word.
		if (Character.isLetter(chars[chars.length - 1])) {
			splits.add(cleanedWord.toString());
		}
		
		return splits;
	}

	/**
	 * Gets on the character from the data passed in.
	 * 
	 * @param strings
	 *            The strings too look at.
	 * @return An array list of blanks.
	 */
	public static ArrayList<String> checkFields(final ArrayList<String> strings) {

		final ArrayList<String> optionsList = new ArrayList<String>();

		for (final String string : strings) {

			// Remove commas and periods and question marks.
			final StringBuffer cleanedWord = new StringBuffer();
			for (char c : string.trim().toCharArray()) {
				if (Character.isLetter(c)) {
					cleanedWord.append(c);
				}
			}
			optionsList.add(cleanedWord.toString());
		}

		return optionsList;
	}

	/**
	 * Generates a list of blanks.
	 * 
	 * @param statement
	 *            The statement to slice and dice.
	 * @return A list of blanks.
	 */
	public static ArrayList<String> generate(final String statement) {

		final ArrayList<String> noSpaces = new ArrayList<String>();
		for (String split : StringUtilities.split(statement)) {
			if (!split.equals(" ")) {
				noSpaces.add(split);
			}
		}

		final ArrayList<String> cleaned = checkFields(noSpaces);

		double averageWordLength = 0;
		for (final String split : cleaned) {
			averageWordLength += split.length();
		}
		averageWordLength = averageWordLength / cleaned.size();
		averageWordLength = Math.ceil(averageWordLength);

		final ArrayList<String> possibleWords = new ArrayList<String>();
		for (final String split : cleaned) {
			if (split.length() >= averageWordLength) {
				boolean found = false;
				for (final String possibleWord : possibleWords) {
					if (split.equalsIgnoreCase(possibleWord)) {
						found = true;
						break;
					}
				}

				if (!found) {
					possibleWords.add(split.toLowerCase());
				}
			}

			// Only add up to a possibility of max words.
			if (possibleWords.size() == MAX_WORDS) {
				break;
			}
		}

		Collections.sort(possibleWords);

		return possibleWords;
	}
}
