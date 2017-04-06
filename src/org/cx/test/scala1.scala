package org.cx.test

object scala1 {

  def onprocess(callback: () => Unit) {
    while (true) { callback(); Thread sleep 1000 }
  }

  def printTime() {
    println(111)
  }

  def sum_of_square(x: Int, y: Int): Int = {
    def square(x: Int) =
      x * x
    square(x) + square(y)
  }

  def abs(n: Int): Int = if (n > 0) n else -n
  
  def sum(xs: List[Int]) = { 
    var total = 0 
    var index = 0 
    while (index < xs.size) { 
      total += xs(index) 
      index += 1 
    } 
    total 
  }
  //sum 可写成sum1或sum2
  def sum1(xs: List[Int]) : Int = if (xs.isEmpty) 0 else xs.head + sum1(xs.tail)
  def sum2(xs: List[Int]) ={
    xs.foldLeft(0)((x0, x) => x0 + x)
  }
  
  def main(args: Array[String]): Unit = {
    //传递方法
    //    onprocess { printTime}
    //传递匿名方法
    //    onprocess { () => println(22) }

    println(sum_of_square(3, 5))

    println(abs(8))
    
    println(sum(List(1,2,3,4,5)))
    println(sum1(List(1,2,3,4,5)))
    println(sum2(List(1,2,3,4,5)))
    
  }
}