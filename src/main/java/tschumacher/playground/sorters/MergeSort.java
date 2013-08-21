package tschumacher.playground.sorters;

import tschumacher.playground.ArraySorter;

/**
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public class MergeSort implements ArraySorter {

	public void sort(int[] arr) {
		if(arr != null) {
			sort(arr, 0, arr.length);
		}
	}

	/**
	 * 	<p>Sort of piece of the array that lives in the interval [a,c).</p>
	 * 
	 * 	@param arr The array.
	 * 	@param a The left end point of the subarray to sort (inclusive)
	 * 	@param c The right endpoint of the subarray to sort (exclusive) 
	 */
	private void sort(int[] arr, final int a, final int c) {
		if(arr!=null && c>a+1) {
			final int b = a + (c-a)/2;  // note: (a+b)/2 may cause overflow for large a and b.
			sort(arr,a,b);  // recurse and sort the left "half".
			sort(arr,b,c);  // recurse and sort the right "half".
			merge(arr,a,b,c);  // Now merge the results back into the original array.
		}
	}

	/**
	 * 	<p>Combine the sorted sub arrays [a,b) and [b,c).  This requires a<b<c to do anything interesting.</p>
	 * @param arr
	 * @param a
	 * @param b
	 * @param c 
	 */
	private void merge(int[] arr, final int a, final int b, final int c) {
		
		final int[] buffer = new int[c-a];
		int put=0, i=a, j=b;

		while(put<buffer.length && i<b && j<c) {
			if(arr[i]<=arr[j]) { buffer[put++] = arr[i++]; } 
			else { buffer[put++] = arr[j++]; }
		}

		// Now get the remainder from whichever subarray still has elements left:

		while(put<buffer.length && i<b) {
			buffer[put++] = arr[i++];
		}
		
		while(put<buffer.length && j<c) {
			buffer[put++] = arr[j++];
		}

		System.arraycopy(buffer, 0, arr, a, buffer.length);
	}
}
