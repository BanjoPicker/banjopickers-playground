/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tschumacher.playground.sorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
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
		for(int i=10000;i<50000;i += 1000) {
			int[] arr = randomArray(i);
			ArraySorter as = new TimedSorter(new InsertionSort());
			as.sort(arr);
			assertTrue(isSorted(arr));
		}
	}

	@Test
	public void testBestCase() {
		System.out.println("testBestCase");
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
		System.out.println("testWorstCase");
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

	private int[] randomArray(int length) {
		int[] result = new int[length];
		Random random = new Random();

		for(int i=0;i<result.length;i++) {
			result[i] = random.nextInt();
		}

		return result;
	}

	final static int MAX_SIZE = 100000;
}
