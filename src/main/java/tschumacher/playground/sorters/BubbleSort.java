package tschumacher.playground.sorters;

import tschumacher.playground.ArraySorter;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public class BubbleSort implements ArraySorter {
	
	@Override
	public void sort(int[] arr) {
		if(arr!=null && arr.length>1) {
			boolean sorted = false;
			while(!sorted) {
				sorted = true;
				for(int i=0;i<arr.length-1;i++) {
					if(arr[i]>arr[i+1]) {
						sorted = false;
						int t = arr[i];
						arr[i] = arr[i+1];
						arr[i+1] = t;
					}
				}
			}
		}
	}
}
