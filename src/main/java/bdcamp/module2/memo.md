# bdcamp
大数据训练营

module2 第二周作业


# 拷贝jar包
pscp -pw  .\bdlearning-module2-1.0-jar-with-dependencies.jar root@192.168.47.66:/root/bdcamp/
pscp -pw  HTTP_20130313143750.dat root@192.168.47.66:/root/bdcamp/HTTP_20130313143750.dat
docker cp bdlearning-module2-1.0-jar-with-dependencies.jar 4c:/usr/local/hadoop/bdlearning/
docker cp HTTP_20130313143750.dat 4c:/usr/local/hadoop/bdlearning/HTTP_20130313143750.dat

# 删除old输入文件
../bin/hadoop fs -rm /user/joe/wordcount/input/*

#上传数据文件
../bin/hadoop fs -put HTTP_20130313143750.dat /user/joe/wordcount/input/

#删除历史输出，否则重复执行时会出错
../bin/hadoop fs -rm -r /user/joe/wordcount/output

#执行map-reduce
../bin/hadoop jar bdlearning-module2-1.0-jar-with-dependencies.jar /user/joe/wordcount/input /user/joe/wordcount/output

#查看输出结果
../bin/hadoop fs -cat /user/joe/wordcount/output/part-r-00000

#执行log
find / -path "*1647186214349_0016*"|grep std

cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0016/container_1647186214349_0016_01_000002/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0016/container_1647186214349_0016_01_000002/stdout
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0016/container_1647186214349_0016_01_000001/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0016/container_1647186214349_0016_01_000001/stdout
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0016/container_1647186214349_0016_01_000003/stderr
cat /usr/local/hadoop-2.7.0/logs/userlogs/application_1647186214349_0016/container_1647186214349_0016_01_000003/stdout
