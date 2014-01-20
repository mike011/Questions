package ca.charland.questions.data.comparison;

import java.util.Comparator;

import ca.charland.questions.ui.answer.Container;

/**
 * Sorts the questions by easiest to hardest.
 * 
 * @author Michael
 */
public final class EasiestFirstDifficulty implements Comparator<Object> {

	/**
	 * Compare two questions.
	 * @param obj1 The first question to compare.
	 * @param obj2 The second question to compare.
	 * @return Is the question before, the same, or after.
	 */
	public int compare(final Object obj1, final Object obj2) {
		int result = 0;

		int i1 = ((Container) obj1).getQuestionData().getDifficulty();

		int i2 = ((Container) obj2).getQuestionData().getDifficulty();

		if (i1 > i2) {
			result = 1;
		}

		if (i1 < i2) {
			result = -1;
		}

		if (i1 == i2) {
			result = 0;
		}

		return result;
	}

}
