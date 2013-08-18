/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tschumacher.playground.sorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;
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
		int[] arr = {2,3,1,5,5,4,-3,10,4,3};

		ArraySorter as = new TimedSorter(new InsertionSort());
		as.sort(arr);
		System.out.println(dumpArray(arr));
		assertTrue(isSorted(arr));
	}

	@Test
	public void testBestCase() {
		int[] arr = new int[MAX_SIZE];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		ArraySorter as = new TimedSorter(new InsertionSort());
		as.sort(arr);
		assertTrue(isSorted(arr));
	}

	@Test
	public void testWorstCase() {
		int[] arr = new int[MAX_SIZE];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = arr.length - i;
		}
		ArraySorter as = new TimedSorter(new InsertionSort());
		as.sort(arr);
		assertTrue(isSorted(arr));
	}

	@Test
	public void testNullArray() {
		ArraySorter as = new InsertionSort();
		as.sort(null);
	}
	
	@Test
	public void testEmptyArray() {
		int[] arr = new int[0];
		ArraySorter as = new InsertionSort();
		as.sort(arr);
		assertTrue(isSorted(arr));
	}

	@Test
	public void testOneElement() {
		int[] arr = {2};
		ArraySorter as = new InsertionSort();
		as.sort(arr);
		assertTrue(isSorted(arr));
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

	public String dumpArray(int[] arr) {
		if(arr==null) {
			return "null";
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(arr[0]);
			for(int i=1;i<arr.length;i++) {
				sb.append(",").append(arr[i]);
			}
			sb.append("]");
			return sb.toString();
		}
	}

	final static int MAX_SIZE = 100000;
}
