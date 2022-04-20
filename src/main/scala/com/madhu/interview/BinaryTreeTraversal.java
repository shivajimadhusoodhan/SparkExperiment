package com.madhu.interview;


class Node {
    int key;
    Node left, right;
    //int level;

    public Node(int key) {
        this.key = key;
        left = right = null;
    }
}

class BinaryTree {
    Node root;

    BinaryTree() { root = null;}

    public void postOrderTraverse(Node node, int level) {

        if(node == null)
            return;

        else {
            postOrderTraverse(node.left, level+1);
            postOrderTraverse(node.right, level+1);
            System.out.println(node.key + " | " + level );
        }
    }

    public void preOrderTraverse(Node node, int level) {
        if(node == null)
            return;
        else {
            System.out.println(node.key + " | " + level);
            preOrderTraverse(node.left, level + 1);
            preOrderTraverse(node.right, level + 1);
        }
    }

    public void inOrderTraverse(Node node, int level) {
        if(node == null)
            return;
        else {
            inOrderTraverse(node.left, level + 1);
            System.out.println(node.key + " | " + level);
            inOrderTraverse(node.right, level + 1);
        }
    }
}

public class BinaryTreeTraversal {

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);
        tree.root.left.right.left = new Node(6);
        tree.root.left.right.right = new Node(7);

        tree.postOrderTraverse(tree.root,0); //4675231
//        tree.preOrderTraverse(tree.root,0); //1245673
       // tree.inOrderTraverse(tree.root,0); //4265713
    }
}
