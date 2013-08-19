package tschumacher.playground.sorters;

import tschumacher.playground.ArraySorter;

/**
 * 	<p>An implementation of insertion sort.</p>
 * 	<p>
 * 		The basic idea is to logically partition the array into two subarrays:  
 * 		One that is sorted and the other that is yet to be sorted.  Then for each element of the unsorted array, 
 * 		we insert it into the sorted array by moving it one by one until it is in order.
 * 	</p>
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */

public class InsertionSort implements ArraySorter {

	@Override
	public void sort(int[] arr) {
		if(arr == null) {
			return;  // ok, empty array is already sorted!
		}

		// The array has elements in [0,arr.length).  
		// We partition this into [0,b) U [b,arr.length) where [0,b) contains sorted elements, 
		// starting with b=1.
		// Finally, we "insert" the bth element into the sorted array by exchanging it with it's 
		// neighbor until it is in the correct place.

		int b = 1;  // Initially, the sorted array lies in [0,1), i.e. the zeroth element.
		while(b < arr.length) {
			int k = ++b;
			while(--k > 0 && arr[k-1] > arr[k]) {
				swap(arr, k-1, k);
			}
		}
	}

	/**
	 * <p>Helper that swaps the ith element with the jth element.</p>
	 * @param arr The array.
	 * @param i	
	 * @param j 
	 */
	private void swap(int[] arr, int i, int j) {
		
		//arr[i] ^= arr[j];
		//arr[j] ^= arr[i];
		//arr[i] ^= arr[j];

		// This is much faster than the xor way?

		final int x = arr[i];
		arr[i] = arr[j];
		arr[j] = x;
	}
}
