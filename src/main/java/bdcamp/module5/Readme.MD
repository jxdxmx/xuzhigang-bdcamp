
#作业
create table movies(id int,movie_name string,movie_type string) ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' WITH SERDEPROPERTIES ('field.delim'='::');
create external table users(id int,sex string,age int,occupation string,zip_code string) 
create table ratings(user_id int,movie_id int,rate int,rating_at bigint) ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' WITH SERDEPROPERTIES ('field.delim'='::');

#题目一（简单）
##展示电影 ID 为 2116 这部电影各年龄段的平均影评分。
select u.age,avg(r.rate)
from ratings r join users u on r.user_id = u.id 
where r.movie_id = 2116
group by u.age;
+--------+---------------------+
| u.age  |         _c1         |
+--------+---------------------+
| 1      | 3.2941176470588234  |
| 18     | 3.3580246913580245  |
| 25     | 3.436548223350254   |
| 35     | 3.2278481012658227  |
| 45     | 2.8275862068965516  |
| 50     | 3.32                |
| 56     | 3.5                 |
+--------+---------------------+
7 rows selected (9.674 seconds)

#题目二（中等）
##找出男性评分最高且评分次数超过 50 次的 10 部电影，展示电影名，平均影评分和评分次数。
select movie_name,avg_rate,rate_times
from (select r.movie_id,count(*) rate_times,avg(r.rate) avg_rate
from ratings r join users u on r.user_id = u.id 
 where u.sex='M'
group by r.movie_id
having count(*)>50) as m50 join movies m on m50.movie_id = m.id
order by avg_rate desc
limit 10;
+----------------------------------------------------+--------------------+-------------+
|                     movie_name                     |      avg_rate      | rate_times  |
+----------------------------------------------------+--------------------+-------------+
| Sanjuro (1962)                                     | 4.639344262295082  | 61          |
| Godfather, The (1972)                              | 4.583333333333333  | 1740        |
| Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954) | 4.576628352490421  | 522         |
| Shawshank Redemption, The (1994)                   | 4.560625           | 1600        |
| Raiders of the Lost Ark (1981)                     | 4.520597322348094  | 1942        |
| Usual Suspects, The (1995)                         | 4.518248175182482  | 1370        |
| Star Wars: Episode IV - A New Hope (1977)          | 4.495307167235495  | 2344        |
| Schindler's List (1993)                            | 4.49141503848431   | 1689        |
| Paths of Glory (1957)                              | 4.485148514851486  | 202         |
| Wrong Trousers, The (1993)                         | 4.478260869565218  | 644         |
+----------------------------------------------------+--------------------+-------------+

create table movies(id int,movie_name string,movie_type string) ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' WITH SERDEPROPERTIES ('field.delim'='::');
create external table users(id int,sex string,age int,occupation string,zip_code string) 
create table ratings(user_id int,movie_id int,rate int,rating_at bigint) ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' WITH SERDEPROPERTIES ('field.delim'='::');

#题目三（选做）
##找出影评次数最多的女士所给出最高分的 10 部电影的平均影评分，展示电影名和平均影评分（可使用多行 SQL）。
#感觉各种歧义。。。没断句。。。。下面的sql是：找出影评次数最多的女士，她所给出最高分的 10 部电影，这10部电影的平均影评分，展示电影名和平均影评分
select id movie_id,m.movie_name,avg_rate
from movies m join (
    select movie_id, avg(r.rate) avg_rate
    from ratings r join (
        select rate,movie_id
        from ratings r join (
            select user_id,rate_times
            from (select r.user_id,count(*) rate_times
                from ratings r join users u on r.user_id = u.id 
                 where u.sex='F'
                group by r.user_id) user_rate_times
            order by rate_times desc
            limit 1
        ) lady on r.user_id = lady.user_id
        order by rate desc
        limit 10
    ) lady_movie on r.movie_id = lady_movie.movie_id
    group by r.movie_id
) top10 on m.id = top10.movie_id;
+-----------+------------------------------+---------------------+
| movie_id  |         m.movie_name         |      avg_rate       |
+-----------+------------------------------+---------------------+
| 162       | Crumb (1994)                 | 4.063136456211812   |
| 904       | Rear Window (1954)           | 4.476190476190476   |
| 951       | His Girl Friday (1940)       | 4.249370277078086   |
| 1230      | Annie Hall (1977)            | 4.14167916041979    |
| 1966      | Metropolitan (1990)          | 3.6464646464646466  |
| 2330      | Hands on a Hard Body (1996)  | 4.163043478260869   |
| 3163      | Topsy-Turvy (1999)           | 3.7039473684210527  |
| 3307      | City Lights (1931)           | 4.387453874538745   |
| 3671      | Blazing Saddles (1974)       | 4.047363717605005   |
| 3675      | White Christmas (1954)       | 3.8265682656826567  |
+-----------+------------------------------+---------------------+
10 rows selected (32.7 seconds)



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
movies.dat  ratings.dat  users.dat

#建表-movies.dat
id  movie_name  movie_type
1::Toy Story (1995)::Animation|Children's|Comedy

docker-compose up -d #启动环境
docker-compose exec hive-server bash #进入hive-server bash。后续就可以执行HQL。
/opt/hive/bin/beeline -u jdbc:hive2://localhost:10000
create database module5;
use module5;
####create table movies(id int,movie_name string,movie_type string) row format delimited fields terminated by '::';
create table movies(id int,movie_name string,movie_type string) ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' WITH SERDEPROPERTIES ('field.delim'='::');
load data local inpath '/opt/module5/movies.dat' overwrite into table movies;
select * from movies limit 5;

#建表-users.dat
id,sex,age,occupation,zip_code
字段为：UserID, Sex, Age, Occupation, Zipcode
字段中文解释：用户id，性别，年龄，职业，邮编
1::F::1::10::48067
2::M::56::16::70072

create external table users(id int,sex string,age int,occupation string,zip_code string) 
ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' WITH SERDEPROPERTIES ('field.delim'='::')
STORED AS TEXTFILE
LOCATION '/data/hive/users/';
load data local inpath '/opt/module5/users/users.dat' overwrite into table users;
select * from users limit 5;

root@48a9fb06bcc3:/opt/module5# ls
movies.dat  ratings.dat  users.dat
root@48a9fb06bcc3:/opt/module5# mkdir users
root@48a9fb06bcc3:/opt/module5# mv users.dat  users
root@48a9fb06bcc3:/opt/module5# 

#建表-ratings.dat
user_id,movie_id,rate,rating_at
字段为：UserID, MovieID, Rate, Times
字段中文解释：用户 ID，电影 ID，评分，评分时间
1::1193::5::978300760
1::661::3::978302109

create table ratings(user_id int,movie_id int,rate int,rating_at bigint) ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' WITH SERDEPROPERTIES ('field.delim'='::');
load data local inpath '/opt/module5/ratings.dat' overwrite into table ratings;
select * from ratings limit 5;





















