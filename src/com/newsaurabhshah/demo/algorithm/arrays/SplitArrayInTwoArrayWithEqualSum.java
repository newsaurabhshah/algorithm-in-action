package com.newsaurabhshah.demo.algorithm.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This project is aimed to split array in to two array with equal sum and no common member in the solution
 * 
 * Please check the main method
 * 
 * Updates Removed Reduce Method and integrated with Map Method to improve efficiency
 * 
 * @author Saurabh S Shah
 * @version 1.1
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
	public static int[] _addAll(int[] array1, int[] array2) {
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
	private static void _findFirstCombinationSolution(int[][] reducedArray) {
		int j;
		int[] temp;
		boolean found = false;
		for (int i = 0; i < reducedArray.length - 1 && !found; i++) {
			temp = reducedArray[i];
			for (j = i + 1; j < reducedArray.length; j++) {
				if (_isCombinationPossible(temp, reducedArray[j])) {
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
	 * @param target the sum that has to be achieved for the array to be valid. If null it is disregarded and all the
	 *            possible combination of the array is added to result
	 * @return all possible combinations in a 2d array
	 */
	private static int[][] _generateAllPossibleValidCombination(int[] myarray, Integer target) {
		if (myarray.length == 0) {
			int[][] result = { { 0 } };
			return result;
		}
		if (myarray.length == 1) {
			int[][] result = { { myarray[0] } };
			return result;
		}
		// Get all combination of Spliced array except the first number
		int[][] result = _generateAllPossibleValidCombination(Arrays.copyOfRange(
				myarray, 1, myarray.length), null); // Splice
		int[][] result2 = new int[(result.length * 2) + 1][_maxColLenght(result) + 1];
		int i, k;
		int temp[];
		// To the solution add this number
		k = 0;
		for (i = 0; i < result.length; i++) {
			temp = _addAll(new int[] { myarray[0] }, result[i]);
			if (_verifyTarget(target, temp)) {
				result2[k] = temp;
				k++;
			}
		}
		// Single entry for this number
		temp = new int[] { myarray[0] };
		if (_verifyTarget(target, temp)) {
			result2[k] = temp;
			k++;
		}
		int j;
		// Copy Original Combination
		for (j = 0; j < result.length; j++) {
			temp = result[j];
			if (_verifyTarget(target, temp)) {
				result2[k] = temp;
				k++;
			}
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
	private static boolean _isCombinationPossible(int a[], int b[]) {
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
	 * This method is used to find the max column length in an 2d array
	 * 
	 * @param array the 2d Array for which the max column size has to be found
	 * @return the max column length in an array 0 if the array is empty
	 */
	private static int _maxColLenght(int[][] array) {
		int max = 0;
		for (int[] j : array) {
			if (max <= j.length) {
				max = j.length;
			}
		}
		return max;
	}

	/**
	 * This method is used to calculate the sum of all value in an integer array
	 * 
	 * @param a the array of integer for which sum has to be found
	 * @return the sum of the array
	 */
	private static int _sum(int a[]) {
		int sum = 0;
		for (int k : a) {
			sum += k;
		}
		return sum;
	}

	/**
	 * 
	 * This method is used to verify that the sum of array is equal to target or not.
	 * 
	 * To disregard send target as null.
	 * 
	 * @param target the expected sum of the temp array to be a vaild case. if it is null the case is expected as a
	 *            valid case.
	 * @param temp the array to be verified
	 * @return true if target is null or if target is not null then sum of temp should be equal to target
	 */
	private static boolean _verifyTarget(Integer target, int[] temp) {
		if (target != null) {
			if (_sum(temp) != target) {
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
		long startTime = System.nanoTime();
		int sumA = _sum(a);
		if (sumA % 2 != 0) {
			System.out.println("Not Possible!");
			return;
		}
		// Map: Form All the valid Combination Tree
		int[][] combination = _generateAllPossibleValidCombination(a, sumA / 2);
		// Query: Find First Solution
		_findFirstCombinationSolution(combination);
		long endTime = System.nanoTime();
		long timeTaken = (endTime - startTime);
		System.out.println("Took " + timeTaken + " ns or " + (double) timeTaken / 1000000000 + " sec");
	}

}
