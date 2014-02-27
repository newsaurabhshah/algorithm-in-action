package com.newsaurabhshah.demo.algorithm.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This project is aimed to split array in to two array with equal sum and no common member in the solution
 * 
 * Please check the main method
 * 
 * @author Saurabh S Shah
 * @version 1
 * @build Feb 27, 2014
 */
public class SplitArrayInTwoArrayWithEqualSum {

	/**
	 * <b> Replica of ArrayUtils from Apache Commons Lang 2.6
	 * 
	 * Adds all the elements of the given arrays into a new array. The new array contains all of the element of array1
	 * followed by all of the elements array2. When an array is returned, it is always a new array.
	 * 
	 * @param array1 the first array whose elements are added to the new array.
	 * @param array2 the second array whose elements are added to the new array.
	 * @return The new long[] array.
	 */
	public static int[] addAll(int[] array1, int[] array2) {
		if (array1 == null)
			return array2.clone();
		if (array2 == null) {
			return array1.clone();
		}
		int[] joinedArray = new int[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * This method is used to find first combination of array where the 1's array set element is not present in 2nd
	 * array set
	 * 
	 * @param reducedArray the array in which all rows have equal sum
	 */
	private static void findFirstCombinationSolution(int[][] reducedArray) {
		int j;
		int[] temp;
		boolean found = false;
		for (int i = 0; i < reducedArray.length - 1 && !found; i++) {
			temp = reducedArray[i];
			for (j = i + 1; j < reducedArray.length; j++) {
				if (isCombinationPossible(temp, reducedArray[j])) {
					System.out.println("Solution");
					System.out.println(Arrays.toString(temp));
					System.out.println(" and ");
					System.out.println(Arrays.toString(reducedArray[j]));
					System.out.println(">>>>>");
					found = true;
					break;
				}
			}
		}
		if (!found) {
			System.out.println("No Solution Possible without repetition");
		}
	}

	/**
	 * This method is used to find all possible combination of the array
	 * 
	 * <b> Uses recursion. May Lead to Java Heap overflow if the array is too large
	 * 
	 * @param myarray the single dimension array containing the set of number for which all combination have to be found
	 * @return all possible combinations in a 2d array
	 */
	private static int[][] generateAllPossibleCombination(int[] myarray) {
		if (myarray.length == 0) {
			int[][] result = { { 0 } };
			return result;
		}
		if (myarray.length == 1) {
			int[][] result = { { myarray[0] } };
			return result;
		}
		// Get all combination of Spliced array except the first number
		int[][] result = generateAllPossibleCombination(Arrays.copyOfRange(
				myarray, 1, myarray.length)); // Splice
		int[][] result2 = new int[(result.length * 2) + 1][maxColLenght(result) + 1];
		int i;
		// To the solution add this number
		for (i = 0; i < result.length; i++) {
			result2[i] = addAll(new int[] { myarray[0] }, result[i]);
		}
		// Single entry for this number
		result2[i] = new int[] { myarray[0] };
		i++;
		int j;
		// Copy Original Combination
		for (j = 0; j < result.length; j++) {
			result2[i] = result[j];
			i++;
		}
		return result2;
	}

	/**
	 * This method is used to find in any element of array a is present in b
	 * 
	 * @param a the array element of which have to be searched
	 * @param b the array in which the elements have to be searched
	 * @return true if no element of a is in b
	 */
	private static boolean isCombinationPossible(int a[], int b[]) {
		List<Integer> secondary = new ArrayList<Integer>();
		for (int index = 0; index < b.length; index++) {
			secondary.add(b[index]);
		}
		// System.out.println("Comparing "+Arrays.toString(a)+" "+secondary);
		for (int i : a) {
			// System.out.println("Is "+i+" in "+secondary+" "+secondary.contains(i));
			if (secondary.contains(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This is the entry point for the demo console application
	 * 
	 * @param args command line args
	 */
	public static void main(String[] args) {
		int a[] = { 11, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int sumA = sum(a);
		if (sumA % 2 != 0) {
			System.out.println("Not Possible!");
			return;
		}
		// Map: Form All Combination Tree
		int[][] combination = generateAllPossibleCombination(a);
		// Reduce: Reduce Tree to Possible Solution
		int[][] reducedCombination = reduceAsPerTarget(combination, sumA / 2);
		// Query: Find First Solution
		findFirstCombinationSolution(reducedCombination);
	}

	/**
	 * This method is used to find the max column length in an 2d array
	 * 
	 * @param array the 2d Array for which the max column size has to be found
	 * @return the max column length in an array 0 if the array is empty
	 */
	private static int maxColLenght(int[][] array) {
		int max = 0;
		for (int[] j : array) {
			if (max <= j.length) {
				max = j.length;
			}
		}
		return max;
	}

	/**
	 * This method reduces the all possible combination to only those combination which have sum equal to target
	 * 
	 * @param allCombination expected all possible combination of an array in a 2d form
	 * @param target the value equal to which a row's sum should match in order to be part of reduced solution
	 * @return the reduced 2d array where each row's sum is equal to target
	 */
	private static int[][] reduceAsPerTarget(int[][] allCombination, int target) {
		int[][] reducedArray = new int[allCombination.length][maxColLenght(allCombination)];
		int j = 0;
		for (int[] i : allCombination) {
			if (sum(i) == target) {
				reducedArray[j] = i;
				j++;
			}
		}
		return Arrays.copyOfRange(reducedArray, 0, j); // Splice
	}

	/**
	 * This method is used to calculate the sum of all value in an integer
	 * 
	 * @param a the array of integer for which sum has to be found
	 * @return the sum of the array
	 */
	private static int sum(int a[]) {
		int sum = 0;
		for (int k : a) {
			sum += k;
		}
		return sum;
	}

}
