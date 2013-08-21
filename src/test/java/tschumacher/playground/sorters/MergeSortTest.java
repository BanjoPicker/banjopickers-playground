package tschumacher.playground.sorters;

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
			int[] arr = SortTestUtils.randomArray(i);
			ArraySorter as = new TimedSorter(new MergeSort());
			as.sort(arr);
			assertTrue(SortTestUtils.isSorted(arr));
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
		assertTrue(SortTestUtils.isSorted(arr));
	}

	@Test
	public void testOneElement() {
		int[] arr = {2};
		ArraySorter as = new MergeSort();
		as.sort(arr);
		assertTrue(SortTestUtils.isSorted(arr));
	}

	final static int MAX_SIZE = 100000;
}
