docker run -it epahomov/docker-spark /spark/bin/spark-sql

## 作业一：为 Spark SQL 添加一条自定义命令
SHOW VERSION；
显示当前 Spark 版本和 Java 版本。

#安装sbt
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | sudo tee /etc/apt/sources.list.d/sbt_old.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo apt-key add
sudo apt-get update
sudo apt-get install sbt

1、修改spark\sql\catalyst\src\main\antlr4\org\apache\spark\sql\catalyst\parser\SqlBaseParser.g4，添加内容
1)
statement
    : query    
    | SHOW VERSION  #showVersion

2)
nonReserved
//--DEFAULT-NON-RESERVED-START
    : ADD
    | VERSION
3) 
ansiNonReserved
//--ANSI-NON-RESERVED-START
    : ADD
    | VERSION
4)
//============================
// Start of the keywords list
//============================
//--SPARK-KEYWORD-LIST-START
VERSION: 'VERSION';

2、执行 spark-catalyst maven module的anltr4:anltr4，修改SparkSqlParser.scala文件, 重写visitShowVersion方法
  override def visitShowVersion(ctx: ShowVersionContext): LogicalPlan = withOrigin(ctx) {
    ShowVersionCommand()
  }
  
在 commands.scala 类中增加实现类 ShowVersionCommand，代码如下，
case class ShowVersionCommand() extends LeafRunnableCommand {
  override val output: Seq[Attribute] = Seq(AttributeReference("version", StringType)()) // 这条代码超级关键！！！如果没有这条代码，是会index out of bound的！！！

  override def run(sparkSession: SparkSession): Seq[Row] = {
    val sparkVersion = sparkSession.version
    val javaVersion = System.getProperty("java.version")
    val output = "Spark Version : %s, Java Version: %s".format(sparkVersion, javaVersion)
    Seq(Row(output))
  }
}

`###在 spark 源码根目录下执行命令"./build/sbt package -DskipTests -Phive -Phive-thriftserver"
在 spark 源码根目录下执行命令"sbt package -DskipTests -Phive -Phive-thriftserver"
将SPARK_HOME环境变量设置成 spark 源码根目录，然后执行./bin/spark-sql，进入 spark-sql 控制台之后，执行 show version 命令`

5. 编译 build/mvn clean package -DskipTests -Phive -Phive-thriftserver #编译整个v3.2.0源码，跳过测试
6. 运行 bin/spark-sql，输入 show version;
spark-sql> show version;
2.12.15 1.8.0_312



## 作业二：构建 SQL 满足如下要求
通过 set spark.sql.planChangeLog.level=WARN，查看：
1.构建一条 SQL，同时 apply 下面三条优化规则：
CombineFilters  #合并两个相邻的filter
CollapseProject #合并相邻的repartition
BooleanSimplification #简化Boolean表达式，主要是针对Where语句中的And/Or组合逻辑进行优化


CREATE TABLE student (id INT, name STRING, age INT) STORED AS ORC;
insert into student values(1,"s1",1);
insert into student values(2,"s2",2);

select name from (select id,name from student where id>1) where id>1 and (true and true);
#### log看1.txt文件


2.构建一条 SQL，同时 apply 下面五条优化规则：
ConstantFolding                 #常量合并
PushDownPredicates              #谓词下推
ReplaceDistinctWithAggregate  #使用Aggregate，重写Distinct
ReplaceExceptWithAntiJoin     #用AntiJoin操作来替换“except distinct”操作，注意不针对"except all"
FoldablePropagation             #可折叠字段、属性传播

## https://lanechen.gitbooks.io/spark-dig-and-buried/content/spark/spark-catalyst-optimizer.html
## https://hyperj.net/note.arts/share/2019/spark-sql-rules/ 
select name,id,name from (select * from student ) temp where (5-3-2+id)>0 and age>0  except DISTINCT select name,id,name from student where id=2;
#### log看2.txt文件



## 作业三：实现自定义优化规则（静默规则）
第一步：实现自定义规则 (静默规则，通过 set spark.sql.planChangeLog.level=WARN，确认执行到就行)
复制代码
case class MyPushDown(spark: SparkSession) extends Rule[LogicalPlan] {
 def apply(plan: LogicalPlan): LogicalPlan = plan transform { .... }
}
第二步：创建自己的 Extension 并注入
复制代码
class MySparkSessionExtension extends (SparkSessionExtensions => Unit) {
 override def apply(extensions: SparkSessionExtensions): Unit = { 
  extensions.injectOptimizerRule { session =>
   new MyPushDown(session) 
  }
 } 
}
第三步：通过 spark.sql.extensions 提交
bin/spark-sql --jars my.jar --conf spark.sql.extensions=com.jikeshijian.MySparkSessionExtension



