package bdcamp.module7.scala.org.apache.spark.examples


import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

//作业一：使用 RDD API 实现带词频的倒排索引
//倒排索引（Inverted index），也被称为反向索引。它是文档检索系统中最常用的数据结构。被广泛地应用于全文搜索引擎。
//
//例子如下，被索引的文件为（0，1，2 代表文件名）
//
//“it is what it is”
//“what is it”
//“it is a banana”
//我们就能得到下面的反向文件索引：
//“a”: {2}
//“banana”: {2}
//“is”: {0, 1, 2}
//“it”: {0, 1, 2}
//“what”: {0, 1}
//再加上词频为：
//“a”: {(2,1)}
//“banana”: {(2,1)}
//“is”: {(0,2), (1,1), (2,1)}
//“it”: {(0,2), (1,1), (2,1)}
//“what”: {(0,1), (1,1)}`

object module7homework1 {
  def main(args: Array[String]) = {

    val input = "src/main/java/bdcamp/module7/scala/org/apache/spark/examples/word/1.txt"
    /**
     * 首先获取路径下的文件列表，unionRDD 按照wordcount来构建
     */
    val sparkConf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local")
    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("WARN")
    //1.txt.获取hadoop操作文件的api
    val fs = FileSystem.get(sc.hadoopConfiguration)
    //2.读取目录下的文件，并生成列表
    val filelist = fs.listFiles(new Path(input), true)
    //3.遍历文件，并读取文件类容成成rdd，结构为（文件名，单词）
    var unionrdd = sc.emptyRDD[(String, String)] // rdd声明变量为 var

    //    while (filelist.hasNext) {
    //val abs_path = new Path(filelist.next().getPath.toString)
    val abs_path = new Path(input)
    val file_name = abs_path.getName //文件名称
    val rdd1 = sc.textFile(abs_path.toString).flatMap(_.split(" ").map((file_name, _)))
    //4.将遍历的多个rdd拼接成1个Rdd
    unionrdd = unionrdd.union(rdd1)
    //    }

    //5.构建词频（（文件名，单词），词频）
    val rdd2 = unionrdd.map(word => {
      (word, 1)
    }).reduceByKey(_ + _)
    //6.//调整输出格式,将（文件名，单词），词频）==》 （单词，（文件名，词频）） ==》 （单词，（文件名，词频））汇总
    val frdd1 = rdd2.map(word => {
      (word._1._2, String.format("(%s,%s)", word._1._1, word._2.toString))
    })
    val frdd2 = frdd1.reduceByKey(_ + "," + _)
    val frdd3 = frdd2.map(word => String.format("\"%s\",{%s}", word._1, word._2))
    frdd3.foreach(println)
  }
}