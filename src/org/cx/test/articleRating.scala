package org.cx.test

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import java.util.Properties
import scala.collection.mutable.StringBuilder
import org.apache.spark.sql.SaveMode


object articleRating {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("MySQL-Demo")
//    conf.set("spark.sql.shuffle.partitions","20") //默认partition是200个
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val reader = sqlContext.read.format("jdbc")
    reader.option("url", "jdbc:mysql://localhost:3306/iever-log")        
    reader.option("driver", "com.mysql.jdbc.Driver")        
    reader.option("dbtable", "article_browse_log")        
    reader.option("user", "root")        
    reader.option("password", "123123")   
    val df = reader.load() 
    
    val deviceDF = sqlContext.read.format("jdbc").options(Map("url" -> "jdbc:mysql://localhost:3306/iever-log", "driver" -> "com.mysql.jdbc.Driver", "dbtable" -> "device_user", "user" -> "root", "password" -> "123123")).load()
    
//    val df1 = df.withColumn("article_id", df.col("api_path").substr(48,8))
//    df1.show()
    
    
    df.registerTempTable("article_log")
//    val rs = sqlContext.sql("select device_id, api_path, count(*) as browser_count from article_browse_log group by device_id,api_path")
    val alDF = sqlContext.sql("select device_id, SUBSTRING_INDEX(api_path,'/',-1) AS article_id, req_time from article_log")
    alDF.registerTempTable("article_browse");
    val arDF = sqlContext.sql("select device_id, article_id, count(article_id) as browse_count, max(req_time)as release_time from article_browse group by device_id, article_id")
//    arDF.show()
    
    var rs = arDF.join(deviceDF, arDF("device_id")===deviceDF("device_id"))
//    rs.select(rs("uu_id"), rs("article_id"),rs("browse_count"))
    rs.show()
    
    
    
    val prop = new Properties();
    prop.setProperty("driver", "com.mysql.jdbc.Driver")
    prop.setProperty("user", "root") 
    prop.setProperty("password", "123123")
    rs.select(rs("uu_id"), rs("article_id"),rs("browse_count")).write.mode(SaveMode.Overwrite).jdbc("jdbc:mysql://localhost:3306/iever-log", "test", prop)
    
  }
}