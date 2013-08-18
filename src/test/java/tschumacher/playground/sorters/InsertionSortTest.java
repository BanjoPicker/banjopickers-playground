/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tschumacher.playground.sorters;

import junit.framework.Test;
import junit.framework.TestCase;
import tschumacher.playground.ArraySorter;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public class InsertionSortTest extends TestCase {
	
	public InsertionSortTest(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test of sort method, of class InsertionSort.
	 */
	public void testSort() {
		int[] arr = {2,3,1,5,5,4};

		ArraySorter as = new InsertionSort();
		as.sort(arr);
		System.out.println(arr);
		assertTrue(isSorted(arr));
		
	}


	@org.junit.Test(expected=java.lang.NullPointerException.class)
	public void testNullArray() {
		ArraySorter as = new InsertionSort();
		as.sort(null);
	}

	public boolean isSorted(int[] arr) {
		if(arr == null) {
			return true;
		} else {
			for(int i=0;i<arr.length-1;i++) {
				if(arr[i] > arr[i+1]) {
					return false;
				}
			}
			return true;
		}
	}
}
