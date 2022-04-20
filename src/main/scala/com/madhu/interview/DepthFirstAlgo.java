package com.madhu.interview;

import java.util.Stack;

public class DepthFirstAlgo {

    Stack<Node> stack = new Stack<>();

    class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public void printInorderTraversal(Node root) {

        if(root.left != null) {
            stack.push(root);
            printInorderTraversal(root.left);
        } else {
            System.out.println(root.data);
        }

    }


}
