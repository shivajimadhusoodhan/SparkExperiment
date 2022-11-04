package com.leetcode.arraystring;

public class OneEditDistance {

    public static void main(String[] args) {

        // TEST CASE 1 :
        String s = "abcde";
        String t = "abcdx";
        System.out.println("abcde and abcdx is oneEditDistance : " + isOneEditDistance(s, t));

        // TEST CASE 2 :
        String s1 = "abcde";
        String t1 = "abcdex";
        System.out.println("abcde and abcdex is oneEditDistance : " + isOneEditDistance(s1, t1));
    }

    public static boolean isOneEditDistance(String s, String t) {

        int m = s.length();
        int n = t.length();

        if (m > n) return isOneEditDistance(t,s);

        int i = 0;
        int shift = n-m;

        if (shift > 1) return false;

        while(i<m && s.charAt(i) == t.charAt(i)) i++;

        if(i==m) return shift > 0;

        if(shift == 0) i++;

        while(i<m && s.charAt(i) == t.charAt(i + shift)) i++;

        return i == m;

    }
}
