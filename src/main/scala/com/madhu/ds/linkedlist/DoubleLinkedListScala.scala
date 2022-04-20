package com.madhu.ds.linkedlist


object DoubleLinkedListScala {
  def main(args: Array[String]): Unit = {
    val dl = new DoubleLinkedListScala
    dl.addNode(1)
    dl.addNode(2)
    dl.addNode(3)
    dl.display()
  }
}

class DoubleLinkedListScala {
  private[linkedlist] class Node(var data: Int) {
    this.previous = null
    this.next = null
    var previous: Node = null
    var next: Node = null
  }

  var head: Node = null
  var tail: Node = null

  def addNode(data: Int): Unit = {
    val newNode = new Node(data)
    if (head == null) {
      head = newNode
      tail = newNode
    }
    else {
      newNode.previous = tail
      tail.next = newNode
      tail = newNode
    }
  }

  def display(): Unit = {
    var current = head
    if (current == null) System.out.println("List is empty")
    else while ( {
      current != null
    }) {
      System.out.print(current.data + " ")
      current = current.next
    }
  }
}

