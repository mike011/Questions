package ca.charland.questions.utilities;

import java.util.ArrayList;

/**
 * Helpful math utilities.
 * 
 * @author Michael
 */
public final class MathUtilities {

	/**
	 * Private constructor to stop instances from being created.
	 */
	private MathUtilities() {
	}

	/**
	 * Creates a random array of numbers.
	 * 
	 * @param size
	 *            The amount of random numbers to create.
	 * @return A random array of numbers.
	 */
	public static int[] generateRandomArray(final int size) {

		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int x = 0; x < size; x++) {
			Integer i = new Integer(x);
			al.add(i);
		}

		int[] result = new int[size];
		for (int x = 0; x < size; x++) {
			int randomSpotInList = (int) (Math.random() * al.size());
			Integer i = (Integer) al.get(randomSpotInList);
			result[x] = (int) i.intValue();
			al.remove(randomSpotInList);
		}
		return result;
	}

}
