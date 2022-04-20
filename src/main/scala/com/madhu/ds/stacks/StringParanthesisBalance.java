package com.madhu.ds.stacks;

import scala.Char;

import java.util.Stack;

public class StringParanthesisBalance {

    public static void main(String[] args) {
        String checkString = ")";

        if (checkParanthesisBalance(checkString)) {
            System.out.println("Balanced");
        } else {
            System.out.println(" not balanced");
        }
    }

    public static boolean checkParanthesisBalance(String expr) {

        Stack<Character> stack = new Stack<>();


        for(int i=0; i<expr.length(); i++ ) {
            char x = expr.charAt(i);

            if(x == '(' || x =='{' || x == '[') {
                stack.push(x);
            } else {
                if(stack.isEmpty()) return false;

                char check = stack.pop();

                switch (x) {
                    case ')' :
                        if(check == '}' || check == ']') return false;
                        break;

                    case '}' :
                        if(check == ')' || check == ']') return false;
                        break;

                    case ']' :
                        if(check == ')' || check == '}') return false;
                        break;
                }
            }
        }

        return stack.isEmpty();

    }
}
