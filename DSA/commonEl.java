package javaProjects;

import java.util.HashSet;
import java.util.Set;

public class commonEl {
	private static void commonIndex(int[] arr1,
			int[] arr2)
{

// Check if length of arr1 is greater than 0
// and length of arr2 is greater than 0
if (arr1.length > 0 && arr2.length > 0) {
Set<Integer> firstSet = new HashSet<Integer>();
for (int i = 0; i < arr1.length; i++) {
firstSet.add(arr1[i]);
}

// Iterate the elements of the arr2
for (int j = 0; j < arr2.length; j++) {
if (firstSet.contains(arr2[j])) {
System.out.println(arr2[j]);
}
}
}
}

// Driver Code
public static void main(String[] args)
{
int[] arr1 = new int[] { 1, 2, 3, 4, 5, 6, 7 };
int[] arr2 = new int[] { 1, 3, 4, 5, 6, 9, 8 };

// Function Call
commonIndex(arr1, arr2);
}
}
