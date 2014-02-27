package com.newsaurabhshah.demo.algorithm.arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This project is aimed to split array in to two array with equal sum and no common member in the solution
 * 
 * Please check the main method
 * 
 * Updates Removed the combination and check approach. The algo now searches for first combination and then it's anti
 * 
 * 
 * 
 * @author Saurabh S Shah
 * @reference Vikram Jit Singh
 * @version 2
 * @build Feb 28, 2014
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
	 * This method generates the anti set of the selected array from source.
	 * 
	 * Antiset is the set where numbers present in selected are removed from source.
	 * 
	 * @param source the array from which selected array's number have to be removed
	 * @param selected the array which has to be removed from source
	 */
	private static void _generateAnti(int[] source, int[] selected) {
		Set<Integer> sourceSet = new HashSet<>();
		for (int a : source) {
			sourceSet.add(a);
		}
		for (int a : selected) {
			sourceSet.remove(a);
		}
		System.out.println("Anti Set :" + sourceSet.toString());
	}

	/**
	 * This method is used to find the first possible combination of the array which has sum as target othher wise it
	 * sends back all possible combination
	 * 
	 * <b> Uses recursion. May Lead to Java Heap overflow if the array is too large
	 * 
	 * @param myarray the single dimension array containing the set of number for which all combination have to be found
	 * @param startingPoint
	 * @param target the sum that has to be achieved for the array to be valid. If null it is disregarded and all the
	 *            possible combination of the array is added to result
	 * @return all possible combination if target is null or if target is not met by ant combination else sends back the
	 *         first solution
	 */
	private static int[][] _generateFirstCombination(int[] myarray, int startingPoint, Integer target) {
		if (myarray.length - startingPoint == 1) {
			int[][] result = { { myarray[startingPoint] } };
			return result;
		}
		// Get all combination of Spliced array except the first number
		int[][] result = _generateFirstCombination(myarray, startingPoint + 1, null); // Splice
		int[][] result2 = new int[(result.length * 2) + 1][_maxColLenght(result) + 1];
		int i, k;
		int temp[];
		// To the solution add this number
		k = 0;
		for (i = 0; i < result.length; i++) {
			temp = _addAll(new int[] { myarray[startingPoint] }, result[i]);
			if (target != null && _sum(temp) == target) {
				result2 = new int[1][temp.length];
				result2[0] = temp;
				return result2;
			}
			result2[k] = temp;
			k++;
		}
		// Single entry for this number
		temp = new int[] { myarray[startingPoint] };
		if (target != null && _sum(temp) == target) {
			result2 = new int[1][temp.length];
			result2[0] = temp;
			return result2;
		}
		result2[k] = temp;
		k++;
		int j;
		// Copy Original Combination
		for (j = 0; j < result.length; j++) {
			temp = result[j];
			if (target != null && _sum(temp) == target) {
				result2 = new int[1][temp.length];
				result2[0] = temp;
				return result2;
			}
			result2[k] = temp;
			k++;
		}
		return result2;
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
	 * This is the entry point for the demo console application
	 * 
	 * @param args command line args
	 */
	public static void main(String[] args) {
		// limit is the the size of the array to be split

		/*
		 * int limit = 2; int a[] = new int[limit]; for (int i = 0; i < limit; i++) { a[i] = (i + 1) * 2; }
		 */

		// Standard Test Array Used
		int a[] = { 11, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		long startTime = System.nanoTime();
		int sumA = _sum(a);
		if (sumA % 2 != 0 || a.length == 1) {
			System.out.println("Not Possible!");
			return;
		}

		int[][] combination = _generateFirstCombination(a, 0, sumA / 2);

		if (combination.length > 1) {
			System.out.println("Not Possible!");
		} else {
			System.out.println("Solution 1 :" + Arrays.toString(combination[0]));
			_generateAnti(a, combination[0]);
		}
		long endTime = System.nanoTime();
		long timeTaken = (endTime - startTime);
		System.out.println("Took " + timeTaken + " ns or " + (double) timeTaken / 1000000000 + " sec");
	}
}
