package org.cx.test

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

object JdbcTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("MySQL-Demo")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

//    val df = sqlContext.read.format("jdbc").options(Map("url" -> "jdbc:mysql://localhost:3306/iever", "driver" -> "com.mysql.jdbc.Driver", "dbtable" -> "article_cover", "user" -> "root", "password" -> "123123")).load()
    val reader = sqlContext.read.format("jdbc")
    reader.option("url", "jdbc:mysql://localhost:3306/iever")        
    reader.option("driver", "com.mysql.jdbc.Driver")        
    reader.option("dbtable", "article_cover")        
    reader.option("user", "root")        
    reader.option("password", "123123")   
    //load方法即将返回一个DataFrame对象
    val df = reader.load() 
    

//    df.show(10)

    //显示表结构
//    df.printSchema()
   
    // 选择所有年龄大于21岁的人，只保留name字段
//    df.filter(df("id") > 20).select("article_title").show()

    // 按作者分组统计 
//    df.groupBy("author_user_id").count().show()
    //agg中能有的方法有: avg/max/min/sum/count
//    df.groupBy("author_user_id").agg("author_user_id"->"count").show()
//    df.groupBy("author_user_id").agg("id"->"sum").show()
    
    
//     左联表（注意是3个等号！）
//    df.join(df2, df("name") === df2("name"), "left").show()
    
    //把DataFrame对象转化为一个虚拟的表，然后用SQL语句查询，比如下面的命令就等同于df.groupBy("age").count().show()
    df.registerTempTable("article_cover")
    sqlContext.sql("select author_user_id, count(*) from article_cover group by author_user_id").show(10000)
    sqlContext.sql("select author_user_id, count(*) from article_cover group by author_user_id").agg("author_user_id"->"count").show()    
//    sqlContext.sql("select * from article_cover").collect().foreach { println }
    
  }
}