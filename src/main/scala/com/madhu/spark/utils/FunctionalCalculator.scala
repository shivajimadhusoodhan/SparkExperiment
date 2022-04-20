package com.madhu.spark.utils

object FunctionalCalculator {

  def add(a: Int, b: Int): Int ={
    a+b
  }

  def subtract(a: Int, b: Int): Int ={
    a-b
  }

  def multiply(a: Int, b: Int): Int ={
    a*b
  }

  def divide(a: Int, b: Int): Int ={
    if(b==0) throw new Exception("b is 0")
    else a/b
  }

  def main(args: Array[String]): Unit = {
    println(performOperation(add, 2, 3))
    println(performOperation(subtract, 2, 3))
    println(performOperation(multiply, 2, 3))
    println(performOperation(divide, 2, 3))
  }

  def performOperation(f: (Int, Int) => Int, a: Int, b: Int) = {
    f(a,b)
  }
}
