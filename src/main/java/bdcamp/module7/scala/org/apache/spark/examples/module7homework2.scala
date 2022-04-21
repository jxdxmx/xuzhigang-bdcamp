package bdcamp.module7.scala.org.apache.spark.examples

//作业二：Distcp 的 Spark 实现
//使用 Spark 实现 Hadoop 分布式数据传输工具 DistCp (distributed copy)，只要求实现最基础的 copy 功能，对于 -update、-diff、-p 不做要求。
//
//对于 HadoopDistCp 的功能与实现，可以参考：
//https://hadoop.apache.org/docs/current/hadoop-distcp/DistCp.html
//
//https://github.com/apache/hadoop/tree/release-2.7.1/hadoop-tools/hadoop-distcp
//
//Hadoop 使用 MapReduce 框架来实现分布式 copy，在 Spark 中应使用 RDD 来实现分布式 copy，应实现的功能为：
//
//sparkDistCp hdfs://xxx/source hdfs://xxx/target
//得到的结果为：启动多个 task/executor，将 hdfs://xxx/source 目录复制到 hdfs://xxx/target，得到 hdfs://xxx/target/source
//需要支持 source 下存在多级子目录
//需支持 -i Ignore failures 参数
//需支持 -m max concurrence 参数，控制同时 copy 的最大并发 task 数

object module7homework2 {
  def main(args: Array[String]) = {
    // 1.解析参数 sourcePath targetPath options
    // 2.检查参数，生成文件列表 checkDir函数
    // 3.循环、copy函数
  }

  //
  //  // 检查参数，生成文件列表
  //  def checkDir(sparkSession: SparkSession, sourcePath: Path, targetPath: Path, fileList: ArrayBuffer[(Path, Path)], options: SparDistCPOptions): Unit = {
  //    val fs = FileSystem.get(sparkSession.sparkContext.hadoopConfiguration)
  //    fs.listStatus(sourcePath).foreach(currPath => {
  //      if (currPath.isDirectory) {
  //        val subPath = currPath.getPath.toString.split(sourcePath.toString)(1)
  //        val nextTargetPath = new Path(targetPath + subPath)
  //        try {
  //          // 创建目标文件夹/子文件夹
  //          fs.mkdirs(nextTargetPath)
  //        } catch {
  //          case ex: Exception => if (!options.ignoreFailures) throw ex else logWarning(ex.getMessage)
  //        }
  //        // 如果是目录，递归检查
  //        checkDir(sparkSession, currPath.getPath, nextTargetPath, fileList, options)
  //      } else {
  //        // 将文件加入文件列表
  //        fileList.append((currPath.getPath, targetPath))
  //      }
  //    })
  //  }
  //
  //  def copy(sparkSession: SparkSession, fileList: ArrayBuffer[(Path, Path)], options: SparDistCPOptions): Unit = {
  //    val sc = sparkSession.sparkContext
  //    val maxConcurrenceTsk = Some(options.maxConcurrenceTask).getOrElse(5)
  //    val rdd = sc.makeRDD(fileList, maxConcurrenceTsk)
  //    rdd.mapPartitions(ite => {
  //      val hadoopConf = new Configuration
  //      ite.foreach(tup => {
  //        try {
  //          FileUtil.copy(tup._1.getFileSystem(hadoopConf), tup._1, tup._2.getFileSystem(hadoopConf))
  //          ,
  //          tup._2
  //          , deleteSource = false
  //          , hadoopConf
  //          )
  //        } catch {
  //          case ex: Exception => if (!options.ignoreFailures) throw ex else logWarning(ex.getMessage)
  //        }
  //      })
  //      ite
  //    })
  //  }.collect()
}