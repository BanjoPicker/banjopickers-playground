package tschumacher.playground.sorters;

import tschumacher.playground.ArraySorter;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public class SelectionSort implements ArraySorter {

	public void sort(int[] arr) {
		if(arr != null && arr .length > 1) {
			for(int i=0;i<arr.length;i++) {
				SelectionSort.swap(arr, i, SelectionSort.findmin(arr, i, arr.length));
			}
		}
	}

	/**
	 * 	<p>Find the location of the minimum in the array in the interval [a,b)</p>
	 * 	
	 * 	@param arr
	 * 	@param a
	 * 	@param b
	 * 	@return The location (index) of the smallest element in the interval [a,b)
	 */
	private static int findmin(int[] arr, int a, int b) {
		int minimumIndex = a;
		int minimumValue = arr[a];
		for(int i=a;i<b;i++) {
			if(arr[i] < minimumValue) {
				minimumValue = arr[i];
				minimumIndex = i;
			}
		}
		return minimumIndex;
	}
	
	/**
	 * 	<p>Swap the values in the array at positions i and j.</p>
	 * @param arr
	 * @param i
	 * @param j 
	 */
	private static void swap(int[] arr, int i, int j) {
		final int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

}
