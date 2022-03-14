# bdcamp
大数据训练营

module2 第二周作业

# 官网Hello world的执行语句
$ bin/hadoop jar wc.jar WordCount2 -Dwordcount.case.sensitive=true /user/joe/wordcount/input /user/joe/wordcount/output -skip /user/joe/wordcount/patterns.txt

# 拷贝jar包
pscp -pw  .\bdlearning-module2-1.0-jar-with-dependencies.jar root@192.168.47.66:/root/bdcamp/
docker cp bdlearning-module2-1.0-jar-with-dependencies.jar 4c:/usr/local/hadoop/bdlearning/

# 删除old输入文件
../bin/hadoop fs -rm /user/joe/wordcount/input/*

#上传数据文件
../bin/hadoop fs -put file01 /user/joe/wordcount/input/
../bin/hadoop fs -put file02 /user/joe/wordcount/input/
../bin/hadoop fs -put patterns.txt /user/joe/wordcount/

#删除历史输出，否则重复执行时会出错
../bin/hadoop fs -rm -r /user/joe/wordcount/output

#执行map-reduce
../bin/hadoop jar bdlearning-module2-1.0-jar-with-dependencies.jar /user/joe/wordcount/input /user/joe/wordcount/output -skip /user/joe/wordcount/patterns.txt

#查看输出结果
../bin/hadoop fs -cat /user/joe/wordcount/output/part-r-00000

# 找log ==> 执行在同一“节点”上、log会是同一个。
# 可以理解为，同一台节点会跑map+reduce，只不过这时的reduce的数据源是它自己，相当于是combiner。后期会再reduce，数据源是所有节点combiner后的数据。
22/03/13 13:12:07 INFO mapreduce.Job: Running job: job_1647186214349_0011
find / -path "*1647186214349_0011*"|grep std
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0011/container_1647186214349_0011_01_000001/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0011/container_1647186214349_0011_01_000001/stdout
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0011/container_1647186214349_0011_01_000003/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0011/container_1647186214349_0011_01_000003/stdout
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0011/container_1647186214349_0011_01_000004/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0011/container_1647186214349_0011_01_000004/stdout
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0011/container_1647186214349_0011_01_000002/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0011/container_1647186214349_0011_01_000002/stdout


# from 官网hello world
Sample Runs
Sample text-files as input:

$ ../bin/hadoop fs -ls /user/joe/wordcount/input/
/user/joe/wordcount/input/file01
/user/joe/wordcount/input/file02

$ ../bin/hadoop fs -cat /user/joe/wordcount/input/file01
Hello World, Bye World!

$ ../bin/hadoop fs -cat /user/joe/wordcount/input/file02
Hello Hadoop, Goodbye to hadoop.
Run the application:

$ ../bin/hadoop jar wc.jar WordCount2 /user/joe/wordcount/input /user/joe/wordcount/output
Output:

$ ../bin/hadoop fs -cat /user/joe/wordcount/output/part-r-00000
Bye 1
Goodbye 1
Hadoop, 1
Hello 2
World! 1
World, 1
hadoop. 1
to 1
s


#文件数据
/user/joe/wordcount/input/file1
Hello World, Bye World!
Hello hive, Goodbye to hive.

/user/joe/wordcount/input/file2
Hello Hadoop, Goodbye to hadoop.

/user/joe/wordcount/patterns.txt   运行时加参数-skip xxx  replaceAll是使用正则表达式去替换，所以不能用.，否则一替换就铁定成空字符串了。
,
!
\.
#执行log
bash-4.1# ../bin/hadoop fs -cat /user/joe/wordcount/output/part-r-00000
Bye	1
Goodbye	2
Hadoop	1
Hello	3
World	2
hadoop	1
hive	2
to	2

bash-4.1# find / -path "*1647186214349_0013*"|grep std
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000002/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000002/stdout
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000003/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000003/stdout
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000001/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000001/stdout
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000004/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000004/stdout

bash-4.1# cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000002/stderr
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/local/hadoop-2.7.0/share/hadoop/common/lib/slf4j-log4j12-1.7.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/tmp/hadoop-root/nm-local-dir/usercache/root/appcache/application_1647186214349_0013/filecache/10/job.jar/job.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
bash-4.1# cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000002/stdout
map --- key:0,value:Hello World, Bye World!
map debug,pattern:\.
map debug,pattern:!
map debug,pattern:,
map debug,nextToken:Hello
map debug,nextToken:World
map debug,nextToken:Bye
map debug,nextToken:World
map --- key:24,value:Hello hive, Goodbye to hive.
map debug,pattern:\.
map debug,pattern:!
map debug,pattern:,
map debug,nextToken:Hello
map debug,nextToken:hive
map debug,nextToken:Goodbye
map debug,nextToken:to
map debug,nextToken:hive
reduce --- key:Bye,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@68d0db31
reduce --- key:Goodbye,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@68d0db31
reduce --- key:Hello,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@68d0db31
reduce --- key:World,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@68d0db31
reduce --- key:hive,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@68d0db31
reduce --- key:to,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@68d0db31
bash-4.1# cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000003/stderr
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/local/hadoop-2.7.0/share/hadoop/common/lib/slf4j-log4j12-1.7.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/tmp/hadoop-root/nm-local-dir/usercache/root/appcache/application_1647186214349_0013/filecache/10/job.jar/job.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
bash-4.1# cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000003/stdout
map --- key:0,value:Hello Hadoop, Goodbye to hadoop.
map debug,pattern:\.
map debug,pattern:!
map debug,pattern:,
map debug,nextToken:Hello
map debug,nextToken:Hadoop
map debug,nextToken:Goodbye
map debug,nextToken:to
map debug,nextToken:hadoop
reduce --- key:Goodbye,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@1e13dc0e
reduce --- key:Hadoop,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@1e13dc0e
reduce --- key:Hello,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@1e13dc0e
reduce --- key:hadoop,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@1e13dc0e
reduce --- key:to,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@1e13dc0e
bash-4.1# cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000001/stderr
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/local/hadoop-2.7.0/share/hadoop/common/lib/slf4j-log4j12-1.7.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/tmp/hadoop-root/nm-local-dir/usercache/root/appcache/application_1647186214349_0013/filecache/10/job.jar/job.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
log4j:WARN No appenders could be found for logger (org.apache.hadoop.ipc.Server).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
bash-4.1# cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000001/stdout
bash-4.1# cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000004/stderr
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/local/hadoop-2.7.0/share/hadoop/common/lib/slf4j-log4j12-1.7.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/tmp/hadoop-root/nm-local-dir/usercache/root/appcache/application_1647186214349_0013/filecache/10/job.jar/job.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
log4j:WARN No appenders could be found for logger (org.apache.hadoop.metrics2.impl.MetricsSystemImpl).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
bash-4.1# cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0013/container_1647186214349_0013_01_000004/stdout
reduce --- key:Bye,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@784d0d4b
reduce --- key:Goodbye,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@784d0d4b
reduce --- key:Hadoop,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@784d0d4b
reduce --- key:Hello,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@784d0d4b
reduce --- key:World,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@784d0d4b
reduce --- key:hadoop,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@784d0d4b
reduce --- key:hive,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@784d0d4b
reduce --- key:to,values:org.apache.hadoop.mapreduce.task.ReduceContextImpl$ValueIterable@784d0d4b
bash-4.1# 
bash-4.1# 

