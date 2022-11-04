package com.leetcode.arraystring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum1 {

    public static void main(String[] args) {

        // TEST CASE 1
        int[] result = getTwoSumIndices(new int[]{2,5,1,8}, 9);
        Arrays.stream(result).forEach(System.out::println);
    }

    public static int[] getTwoSumIndices(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();

        for(int i=0; i<nums.length; i++) {
            int x = target - nums[i];

            if(numMap.containsKey(x))
                return new int[]{numMap.get(x)+1 , i+1 };
            else
                numMap.put(nums[i], i);

        }
        throw new IllegalArgumentException("no nums found");
    }
}
