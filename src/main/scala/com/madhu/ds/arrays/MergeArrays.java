package com.madhu.ds.arrays;

import java.util.stream.Stream;

public class MergeArrays {

    public static void main(String[] args) {
        int a[] = {1,2,3,4,5};
        int b[] = {9,8,7,6};

        mergeArrays(a, b);
    }

    public static void mergeArrays(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];

        for(int i=0; i<a.length; i++) {
            c[i] = a[i];
        }

        for(int i=0; i< b.length; i++)
            c[a.length + i] = b[i];

        Stream.of(c).forEach(System.out::print);
    }
}
