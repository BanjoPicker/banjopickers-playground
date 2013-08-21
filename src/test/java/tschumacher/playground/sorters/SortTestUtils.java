package tschumacher.playground.sorters;

import java.util.Random;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public final class SortTestUtils {

	public static String dumpArray(int[] arr) {
		if (arr == null) {
			return "null";
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(arr[0]);
			for (int i = 1; i < arr.length; i++) {
				sb.append(",").append(arr[i]);
			}
			sb.append("]");
			return sb.toString();
		}
	}

	public static boolean isSorted(int[] arr) {
		if (arr == null) {
			return true;
		} else {
			for (int i = 0; i < arr.length - 1; i++) {
				if (arr[i] > arr[i + 1]) {
					return false;
				}
			}
			return true;
		}
	}

	public static int[] randomArray(int length) {
		int[] result = new int[length];
		Random random = new Random();
		for (int i = 0; i < result.length; i++) {
			result[i] = random.nextInt();
		}
		return result;
	}

	private SortTestUtils() {/* no instances of this thing */}
}
