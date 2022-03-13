# bdcamp
大数据训练营

module2 第二周作业

$ bin/hadoop jar wc.jar WordCount2 -Dwordcount.case.sensitive=true /user/joe/wordcount/input /user/joe/wordcount/output -skip /user/joe/wordcount/patterns.txt

../bin/hadoop fs -rm -r /user/joe/wordcount/output
../bin/hadoop jar bdlearning-module2-1.0-jar-with-dependencies.jar /user/joe/wordcount/input /user/joe/wordcount/output
../bin/hadoop fs -rm -r /user/joe/wordcount/output
../bin/hadoop fs -cat /user/joe/wordcount/output/part-r-00000


pscp -pw xzgXZG123 .\bdlearning-module2-1.0-jar-with-dependencies.jar root@192.168.47.66:/root/bdcamp/
docker cp bdlearning-module2-1.0-jar-with-dependencies.jar 4c:/usr/local/hadoop/bdlearning/





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

