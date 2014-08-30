package tschumacher.playground;

import java.util.Arrays;

/**
 * 	<p>
 * 		Problem is to write a program to print out all permutations 
 * 		of a given string.  Do not treat duplicates in any special way. 
 * 	</p>
 * 
 * 	<p>
 * 		This was for an interview problem that didn't get right away!
 * 	</p>
 * 	@author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */


public class PermutationPrinter {

  /**
   * <p>Print out all permutations of the string s.</p>
   * <p>Sample usage: <br/>
   *    PrintPermutations("", "hello world!");
   * </p>
   */
  private static void PrintPermutations(String acc, String s) {
    if(s.length() <= 1) {
      System.out.println(acc + s);
    } else {
      for(int i=0;i<s.length();i++) {
        String c = s.substring(i,i+1);
        String t = s.substring(0,i) + s.substring(i+1);
        PrintPermutations(acc + c, t);
      }
   }
 }

  /**
   * <p>Helper to swap postiion i with position j in the array.
   */
  private static void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  /**
   * <p>Print all permutations of the sub-array contained in [a,b).</p>
   */
  public static void PrintPermutations(int[] arr, int a, int b) {
    if (a >= b) {
      System.out.println(Arrays.toString(arr));
      return;
    }
    for (int i = a; i < b; ++i) {
      swap(arr, a, i);
      PrintPermutations(arr, a+1, b);
      swap(arr, i, a);
    }
  }

  /**
   * <p>Print all permutations of the array arr.</p>
   * 
   * <p>
   *   The array will be modified in place, but will be restored upon exit.
   * </p>
   */
  public static void PrintPermutations(int[] arr) {
    PrintPermutations(arr, 0, arr.length);
  }

  /**
   * <p>Simple main to test things out.</p>
   */
  public static void main(String args[]) {
    int[] testarr = {1,7,2,3};
    PrintPermutations(testarr);
    System.out.println(Arrays.toString(testarr));
  }
};
