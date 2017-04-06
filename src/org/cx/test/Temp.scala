package org.cx.test

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Temp {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.parallelize(List(1,2,3,4,5,6,7,8))
    println(rdd)
    
    var s ="http://api.iever.com.cn/v300/article/queryById/8467"
    println(s.substring(s.lastIndexOf("/")))  
  }
}