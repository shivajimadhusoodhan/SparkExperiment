package com.leetcode.arraystring;

import java.util.Arrays;

public class TwoSum2 {

    public static void main(String[] args) {

        int[] input = new int[]{1,3,5,6,8,9};
        int target = 16;

        int[] result = getTwoSumSortedArray(input, target);

        Arrays.stream(result).forEach(System.out::println);

    }

    public static int[] getTwoSumSortedArray(int[] input, int target) {

        for(int i=0 ; i<input.length ; i++){
            int findNum = target - input[i];

            int result = binarySearch(input, findNum, i+1); // YOU CAN MAKE IT i IF YOU WANT ADDITION WITH SAME NUMBER

            if(result != -1){
                return new int[]{i+1, result+1};
            }
        }
        throw new IllegalArgumentException("No results found");
    }

    public static int binarySearch(int[] input, int findNum, int start) {
        int L = start;
        int R = input.length - 1;


        while(L < R){
            int M = (L+R)/2;

            if(input[M] < findNum)
                L = M + 1;
            else
                R = M;
        }

        return ( L == R && input[L] == findNum) ? L : -1;
    }
}
