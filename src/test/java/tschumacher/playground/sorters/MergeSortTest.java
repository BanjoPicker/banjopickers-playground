/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tschumacher.playground.sorters;

import java.util.Random;
import junit.framework.TestCase;
import org.junit.Test;
import tschumacher.playground.ArraySorter;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public class MergeSortTest extends TestCase {
	
	public MergeSortTest(String testName) {
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
	 * Test of sort method, of class MergeSort.
	 */
	public void testSort() {
		for(int i=10000;i<50000;i += 1000) {
			int[] arr = randomArray(i);
			ArraySorter as = new TimedSorter(new MergeSort());
			as.sort(arr);
			assertTrue(isSorted(arr));
		}
	}

	@Test
	public void testNullArray() {
		ArraySorter as = new MergeSort();
		as.sort(null);
	}
	
	@Test
	public void testEmptyArray() {
		int[] arr = new int[0];
		ArraySorter as = new MergeSort();
		as.sort(arr);
		assertTrue(isSorted(arr));
	}

	@Test
	public void testOneElement() {
		int[] arr = {2};
		ArraySorter as = new MergeSort();
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
