package org.cx.test

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating

object RecommendExample {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("CollaborativeFilteringExample")
    val sc = new SparkContext(conf)
    val data = sc.textFile("/Users/grass/Downloads/spark/spark-1.6.1/data/mllib/als/temp.data")
    val ratings = data.map(_.split(',') match { case Array(user, item, rate) =>
      Rating(user.toInt, item.toInt, rate.toDouble)
    })

    //设置潜在因子个数为10
    val rank = 10
    //要迭代计算30次 
    val numIterations = 10
    //进行模型训练
    val model = ALS.train(ratings, rank, numIterations, 0.01)

    val usersProducts = ratings.map { case Rating(user, product, rate) => (user, product) }
    //预测后的用户，电影，评分  
    val predictions = model.predict(usersProducts).map { case Rating(user, product, rate) => ((user, product), rate) }
    /*
     * 原始{(用户，电影)，评分} join  预测后的{(用户，电影)，评分}
     * join后的结果，就是每个用户对电影的实际打分和预测打分的一个对比 
     * (用户，电影)，(原始评分，预测的评分） 
     */
    val ratesAndPreds = ratings.map { case Rating(user, product, rate) => ((user, product), rate) }.join(predictions)
    
    val MSE = ratesAndPreds.map { case ((user, product), (r1, r2)) =>
      val err = (r1 - r2)
      err * err
    }.mean()
    
    println("Mean Squared Error = " + MSE)

    val topKRecs = model.recommendProducts(2, 10)
    println(topKRecs.mkString("\n")) //topKRecs.foreach { println }
    
    println("\n\n\n")
    ratesAndPreds.foreach(println)
    
    // Save and load model
//    model.save(sc, "target/tmp/myCollaborativeFilter")
//    val sameModel = MatrixFactorizationModel.load(sc, "target/tmp/myCollaborativeFilter")
  }
}