docker run -it epahomov/docker-spark /spark/bin/spark-sql

```text
实现 Compact table command
要求：
添加 compact table 命令，用于合并小文件，例如表 test1 总共有 50000 个文件，每个 1MB，通过该命令，合成为 500 个文件，每个约 100MB。
语法：
COMPACT TABLE table_identify [partitionSpec] [INTO fileNum FILES]；
说明：
基本要求是完成以下功能：COMPACT TABLE test1 INTO 500 FILES；
如果添加 partitionSpec，则只合并指定的 partition 目录的文件；
如果不加 into fileNum files，则把表中的文件合并成 128MB 大小。

SqlBaseParser.g4
|COMPACT TABLE target=tableIdentifier partitionSpec?(INTO fileNum=INTEGER_VALUE identifier)? #compactTable
```


1、修改spark\sql\catalyst\src\main\antlr4\org\apache\spark\sql\catalyst\parser\SqlBaseParser.g4，添加内容
1)
statement
    |COMPACT TABLE target=tableIdentifier partitionSpec?(INTO fileNum=INTEGER_VALUE identifier)? #compactTable
2)
nonReserved
//--DEFAULT-NON-RESERVED-START
    : ADD
    |COMPACT
3) 
ansiNonReserved
//--ANSI-NON-RESERVED-START
    : ADD
    |COMPACT
4)
//============================
// Start of the keywords list
//============================
//--SPARK-KEYWORD-LIST-START
COMPACT: 'COMPACT';

2、执行 spark-catalyst maven module的anltr4:anltr4，修改SparkSqlParser.scala文件, 重写visitCompactTable方法
  override def visitCompactTable(ctx: compactTableContext): LogicalPlan = withOrigin(ctx) {
    ShowCompactCommand()
  }

在 commands.scala 类中增加实现类 ShowCompactCommand，代码如下，
case class ShowCompactCommand() extends LeafRunnableCommand {
  override val output: Seq[Attribute] = Seq(AttributeReference("COMPACT", StringType)())
  override def run(sparkSession: SparkSession): Seq[Row] = {
    spark.read.parquet("parquet_file_path").coalesce(1).write.parquet("new_path")
    spark\
        .read\
        .parquet('...'))\
        .repartition('key1', 'key2',...)\
        .write\
        .partitionBy('key1', 'key2',...)\
        .option('path', target_part)\
        .saveAsTable('partitioned')
  }
}

5. 编译 build/mvn clean package -DskipTests -Phive -Phive-thriftserver #编译整个v3.2.0源码，跳过测试
6. 运行 bin/spark-sql，COMPACT TABLE test INTO fileNum=100；


```text
https://www.jianshu.com/p/efa0ace796c0
coalesce用已有的partition去尽量减少数据shuffle。
repartition创建新的partition并且使用 full shuffle。
如果用coalesce：sql(select * from T).coalesce(1)，5个executor 有4个在空跑，只有1个在真正读取数据执行，这时候效率是极低的。所以coalesce要慎用，而且它还容易出oom问题，这个我们以后再说。
如果用repartition：sql(select * from T).repartition(1)，这样效率就会高很多，并行5个executor在跑（10个task）,然后shuffle到同一节点，最后写到一个文件中。
```






