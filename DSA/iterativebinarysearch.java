package javaProjects;

public class iterativebinarysearch {
	
	static boolean checkelement(int[] array, int low, int high, int element)
{     while (low <= high){
		   
        int middle = (int) (low + Math.floor((high-low)/2));

        if(array[middle] == element) {return true;}

        if(array[middle] < element)low = middle + 1;

        else high = middle - 1;

    }
       

    return false;

}
    public static void main (String[] args) {
    	int [] array= {11, 34, 50, 55, 800};
    	int k= 800;
    	boolean ans = checkelement(array, 0, array.length-1, k);
    	System.out.println(ans);
    	
    	
    	
    }
}
