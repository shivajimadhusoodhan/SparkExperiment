package com.madhu.ds.linkedlist;

public class DoubleLinkedList {

    class Node {

        public int data;
        public Node previous;
        public Node next;

        public Node(int data) {
            this.data = data;
            this.previous = null;
            this.next = null;
        }
    }

    public Node head = null;
    public Node tail = null;

    public void addNode(int data) {
        Node newNode = new Node(data);

        if(head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void display() {
        Node current = head;

        if(current == null) {
            System.out.println("List is empty");
        } else {
            while(current != null) {
                System.out.print(current.data + " ");
                current = current.next;
            }
        }
    }

    public static void main(String[] args) {
          DoubleLinkedList dl = new DoubleLinkedList();

          dl.addNode(1);
          dl.addNode(2);
          dl.addNode(3);

          dl.display();
    }

}
