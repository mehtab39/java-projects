package javaProjects;

import java.util.Arrays;

public class binarysearchrecursive {
	
	 int binarysearchrecursive(int arr[], int l, int r, int x)
	    {
	        if (r >= l) {
	            int mid = l + (r - l) / 2;
	 
	            // If the element is present at the
	            // middle itself
	            if (arr[mid] == x)
	                return mid;
	 
	            // If element is smaller than mid, then
	            // it can only be present in left subarray
	            if (arr[mid] > x)
	                return binarysearchrecursive(arr, l, mid - 1, x);
	 
	            // Else the element can only be present
	            // in right subarray
	            return binarysearchrecursive(arr, mid + 1, r, x);
	        }
	 
	        // We reach here when element is not present
	        // in array
	        return -1;
	    }
	 
	    // Driver method to test above
	    public static void main(String args[])
	    {
	    	binarysearchrecursive ob = new binarysearchrecursive();
	        int arr[] = { 2, 3, 900, 4, 10, 40, 8 };
	        Arrays.sort(arr);
	        int n = arr.length;
	        int x = 10;
	        int result = ob.binarysearchrecursive(arr, 0, n - 1, x);
	        if (result == -1)
	            System.out.println("Element not present");
	        else
	            System.out.println("Element found at index "
	                               + result);
	    }
	}

