package javaProjects;

import java.util.Arrays;

public class lowerupper {

    public int numFriendRequests(int[] ages) {
       if (ages == null || ages.length == 0) {
           return 0;
       }
       int res = 0;
       Arrays.sort(ages);
       for (int i : ages) {

           int lowerBound = (int )(0.5*i + 8);
           int upperBound = i > 100 ? i :  Math.min(100, i);
           int index = lower(ages, lowerBound);
           int index2 = upper(ages, upperBound);
           if (index != -1 && index2 != -1 && index2 >= index) {
               res += index2 - index + 1;
               if ( i <= upperBound && i >= lowerBound) {
                   res--;
               }
           }

       }

       return res;

   }

   public int lower(int[] ages, int target) {
       if (ages == null || ages.length == 0) {
           return 0;
       }
       int l = 0;
       int r = ages.length - 1;
       if (target <= ages[0]) {
           return 0;
       }
       if (target > ages[r]) {
           return -1;
       }
       while (l  <  r) {
           int m = l + (r - l ) / 2 ;

           if (ages[m] >= target) {
               r = m;
           }else {
               l = m + 1;
           }
       }
       return r;
   }

   public int upper(int[] ages, int target) {
       if (ages == null || ages.length == 0) {
           return 0;
       }
       int l = 0;
       int r = ages.length - 1;
       if (target < ages[0]) {
           return -1;
       }
       if (target >= ages[r]) {
           return r;
       }
       while (l  <  r - 1) {
           int m = l + (r - l ) / 2 ;
           if (ages[m] <= target) {
               l = m;
           }else {
               r = m - 1;
           }
       }
       return ages[r] <= target ? r : l;
   }

}
