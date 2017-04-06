package org.cx.test

import scala.collection.mutable.Map

/*
 *map相关 
 */
object scala2 {
  def mapTest1() {
    val colors = Map("red" -> "#FF0000", "azure" -> "#F0FFFF", "peru" -> "#CD853F")

    val nums: Map[Int, Int] = Map()

    println("colors 中的键为 : " + colors.keys)
    println("colors 中的值为 : " + colors.values)
    println("检测 colors 是否为空 : " + colors.isEmpty)
    println("检测 nums 是否为空 : " + nums.isEmpty)
  }

  //合并map
  def mergeMap() {
    val colors1 = Map("red" -> "#FF0000",
      "azure" -> "#F0FFFF",
      "peru" -> "#CD853F")
    val colors2 = Map("blue" -> "#0033FF",
      "yellow" -> "#FFFF00",
      "red" -> "#FF0000")

    //  ++ 作为运算符
    var colors = colors1 ++ colors2
    println("colors1 ++ colors2 : " + colors)

    //  ++ 作为方法
    colors = colors1.++(colors2)
    println("colors1.++(colors2)) : " + colors)
  }

  def printMap() {
    val sites = Map("runoob" -> "http://www.runoob.com",
      "baidu" -> "http://www.baidu.com",
      "taobao" -> "http://www.taobao.com")

    sites.keys.foreach { i =>
      print("Key = " + i)
      println(" Value = " + sites(i))
    }
  }
  def main(args: Array[String]): Unit = {
    //    mapTest1()
    //    mergeMap()
    printMap()
    
    //可变map
    val sites = Map[String,String]()
    sites += ("runoob" -> "http://www.runoob.com")
    sites += ("baidu" -> "http://www.baidu.com")
    sites += ("taobao" -> "http://www.taobao.com")
    println(sites.contains("baidu"))
    println(sites("taobao"))
    
  }
}