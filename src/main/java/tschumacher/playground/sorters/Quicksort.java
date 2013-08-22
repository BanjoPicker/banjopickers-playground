package tschumacher.playground.sorters;

import java.util.Random;
import tschumacher.playground.ArraySorter;

/**
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public class Quicksort implements ArraySorter {

	final private Random random;

	public Quicksort() {
		random = new Random();
	}

	@Override
	public void sort(int[] arr) {
		if (arr != null) {
			sort(arr, 0, arr.length);
		}
	}

	private void sort(int[] arr, int a, int c) {
		if (arr != null && c > a + 1) {
			final int pivot = partition(arr, a, c);
			sort(arr, a, pivot);
			sort(arr, pivot + 1, c);
		}
	}

	/**
	 * <p>Partition the given subarray and return the pivot location.</p>
	 *
	 * @param arr
	 * @param a
	 * @param c
	 * @return The pivot location (index) used for the partition.
	 */
	private int partition(int[] arr, final int a, final int c) {
		final int pivotIndex = a + random.nextInt(c - a);
		final int pivotValue = arr[pivotIndex];

		swap(arr, pivotIndex, c - 1);
		int result = a;

		for (int i = a; i < c; i++) {
			if (arr[i] < pivotValue) {
				swap(arr, i, result++);
			}
		}

		swap(arr, result, c - 1);
		return result;
	}

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

	private void swap(int[] arr, int i, int j) {
		final int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
}
