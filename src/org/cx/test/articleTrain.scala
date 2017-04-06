package org.cx.test

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import java.util.Properties
import scala.collection.mutable.StringBuilder
import org.apache.spark.sql.SaveMode
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.{ ALS, Rating }
import org.apache.spark.sql.Row

object articleTrain {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("MySQL-Demo")
    //    conf.set("spark.sql.shuffle.partitions","20") //默认partition是200个
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read.format("jdbc").options(Map("url" -> "jdbc:mysql://localhost:3306/iever-log", "driver" -> "com.mysql.jdbc.Driver", "dbtable" -> "article_rating", "user" -> "root", "password" -> "123123")).load()
    
    val ratings = df.map { r => Rating(r.getInt(0), r.getInt(1), r.getLong(2).toDouble) }    
    
     //设置潜在因子个数为10
    val rank = 10
    //要迭代计算30次 
    val numIterations = 15
    //进行模型训练
    val model = ALS.train(ratings, rank, numIterations, 0.01)

    val topKRecs = model.recommendProducts(60362, 10)
    topKRecs.mkString("\n")
    
    println("----")
//    val t = model.recommendProductsForUsers(93508)
//    t.foreach(f=>println())
    
  }
}