# bdcamp
大数据训练营

module2 第二周作业

# 官网Hello world的执行语句
$ bin/hadoop jar wc.jar WordCount2 -Dwordcount.case.sensitive=true /user/joe/wordcount/input /user/joe/wordcount/output -skip /user/joe/wordcount/patterns.txt

#删除历史输出，否则会出错
../bin/hadoop fs -rm -r /user/joe/wordcount/output
#执行map-reduce
../bin/hadoop jar bdlearning-module2-1.0-jar-with-dependencies.jar /user/joe/wordcount/input /user/joe/wordcount/output
#查看输出结果
../bin/hadoop fs -cat /user/joe/wordcount/output/part-r-00000

# 拷贝jar包
pscp -pw xzgXZG123 .\bdlearning-module2-1.0-jar-with-dependencies.jar root@192.168.47.66:/root/bdcamp/
docker cp bdlearning-module2-1.0-jar-with-dependencies.jar 4c:/usr/local/hadoop/bdlearning/

# 找log ==> 执行在同一“节点”上、log会是同一个。
22/03/13 13:12:07 INFO mapreduce.Job: Running job: job_1647186214349_0008
find / -path "*1647186214349_0008*"|grep std
/usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0008/container_1647186214349_0008_01_000003/stderr
/usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0008/container_1647186214349_0008_01_000003/stdout


# 删除文件
../bin/hadoop fs -rm /user/joe/wordcount/input/*

#上传数据文件
../bin/hadoop fs -put file01 /user/joe/wordcount/input/
../bin/hadoop fs -put file02 /user/joe/wordcount/input/




Sample Runs
Sample text-files as input:

$ bin/hadoop fs -ls /user/joe/wordcount/input/
/user/joe/wordcount/input/file01
/user/joe/wordcount/input/file02

$ bin/hadoop fs -cat /user/joe/wordcount/input/file01
Hello World, Bye World!

$ bin/hadoop fs -cat /user/joe/wordcount/input/file02
Hello Hadoop, Goodbye to hadoop.
Run the application:

$ bin/hadoop jar wc.jar WordCount2 /user/joe/wordcount/input /user/joe/wordcount/output
Output:

$ bin/hadoop fs -cat /user/joe/wordcount/output/part-r-00000
Bye 1
Goodbye 1
Hadoop, 1
Hello 2
World! 1
World, 1
hadoop. 1
to 1
s

