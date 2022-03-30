


-bash-4.2$ hadoop fs -ls /data/hive
Found 5 items
drwxr-x--x   - hdfs     hadoop          0 2022-03-29 00:32 /data/hive/movies
drwxr-x--x   - student5 hadoop          0 2022-03-29 23:11 /data/hive/rating
drwxr-x--x   - student  hadoop          0 2022-03-28 20:52 /data/hive/ratings
drwxr-x--x   - student  hadoop          0 2022-03-28 23:08 /data/hive/users
drwxr-x--x   - student5 hadoop          0 2022-03-28 22:10 /data/hive/users.dat
-bash-4.2$ hadoop fs -ls /data/hive/movies
Found 1 items
-rw-r-----   2 student hadoop     171432 2022-03-29 00:32 /data/hive/movies/movies.dat

#把文件拿下来
pscp -r -pw GeekIs@Hero2022 student1@114.55.52.33:/home/student1/xuzhigang/hive .
#把文件放到docker中
pscp -r -pw xxx hive root@192.168.47.66:/root/bdcamp/
root@k8s66mgr:~/bdcamp# docker ps |grep hive-ser
48a9fb06bcc3   bde2020/hive:2.3.2-postgresql-metastore           "entrypoint.sh /bin/…"   9 hours ago   Up 9 hours             0.0.0.0:10000->10000/tcp, :::10000->10000/tcp, 10002/tcp          docker-hive_hive-server_1
#拷贝
root@k8s66mgr:~/bdcamp# docker cp hive/ 48a9fb06bcc3:/opt/module5/

docker-compose exec hive-server bash
root@48a9fb06bcc3:/opt/module5# ls
movies	rating	ratings  users	users.dat




