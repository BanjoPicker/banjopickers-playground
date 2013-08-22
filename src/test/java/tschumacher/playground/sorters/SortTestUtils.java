package tschumacher.playground.sorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import junit.framework.TestCase;
import tschumacher.playground.ArraySorter;

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

	/**
	 * 	<p>Element-wise compare of two arrays.  Returns true if, and only if the two arrays have the same elements in the same order.</p>
	 * @param a
	 * @param b
	 * @return 
	 */
	public static boolean compareArrays(int[] a, int[] b) {
		for(int i=0;i<a.length;i++) {
			if(a[i]!=b[i]) {
				System.out.println(String.format("NOT EQUAL a[%d] = %d, b[%d] = %d", i, a[i], i, b[i]));
				return false;
			}
		}
		return true;
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
		Arrays.sort(result);
		return result;
	}

	public static int[] permute(int[] arr) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i : arr) {
			list.add(i);
		}
	 	Collections.shuffle(list);
		int[] result = new int[arr.length];
		for(int i=0;i<result.length;i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	public static void testSort(ArraySorter as) {
		for(int i=10000;i<50000;i += 1000) {
			int[] arr = SortTestUtils.randomArray(i);
			int[] prm = SortTestUtils.permute(arr);
			as.sort(prm);
			TestCase.assertTrue(SortTestUtils.isSorted(prm));
			TestCase.assertTrue(SortTestUtils.compareArrays(arr, prm));
		}
	}


	private SortTestUtils() {/* no instances of this thing */}
}
