package com.madhu.ds.arrays;

import scala.Int;

import java.util.HashMap;
import java.util.Map;

public class NextGreaterElement {

    public static void main(String[] args) {

        int[] arr = new int[]{13, 7, 6, 12};

        Map<Integer, Integer> resultMap = getNextGreaterElement(arr);
        resultMap.entrySet().stream().forEach(System.out::println);

    }

    public static Map<Integer, Integer> getNextGreaterElement(int[] arr) {
        Map<Integer, Integer> ngeMap = new HashMap<>();

        for(int i=0; i < arr.length-1; i++) {

            int key = arr[i];

            for(int j=i+1; j<arr.length; j++) {
                if(arr[j] > key) {
                    ngeMap.put(key, arr[j]);
                    break;
                }
               ngeMap.put(key, -1);
            }
        }
        ngeMap.put(arr[arr.length-1], -1);
        return ngeMap;
    }
}
