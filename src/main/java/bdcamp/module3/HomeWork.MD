docker cp bdlearning-module3-1.0-jar-with-dependencies.jar 40:/
# 在容器跑！
java -jar bdlearning-module3-1.0-jar-with-dependencies.jar


bash-4.4# java -jar bdlearning-module3-1.0-jar-with-dependencies.jar 
log4j:WARN No appenders could be found for logger (org.apache.hadoop.util.Shell).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
Creating table.  Done.
success insert data :Tom,20210000000001,1,75,82
success insert data :Jerry,20210000000002,1,75,82
success insert data :Jack,20210000000003,2,75,82
success insert data :Rose,20210000000004,2,75,82
success insert data :xuzhigang,G20210607020609,aaa,bbb,ccc
bash-4.4# 
bash-4.4# hbase shell
2022-03-16 14:13:41,569 WARN  [main] util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
HBase Shell
Use "help" to get list of supported commands.
Use "exit" to quit this interactive shell.
For Reference, please visit: http://hbase.apache.org/2.0/book.html#shell
Version 2.1.3, rda5ec9e4c06c537213883cca8f3cc9a7c19daf67, Mon Feb 11 15:45:33 CST 2019
Took 0.0045 seconds                                                                                                                                                         
hbase(main):001:0> list
TABLE                                                                                                                                                                       
xuzhigang:student                                                                                                                                                           
1 row(s)
Took 0.4214 seconds                                                                                                                                                         
=> ["xuzhigang:student"]
hbase(main):002:0> scan 'xuzhigang:student'
ROW                                          COLUMN+CELL                                                                                                                    
 Jack                                        column=info:class, timestamp=1647439990515, value=2                                                                            
 Jack                                        column=info:student_id, timestamp=1647439990515, value=20210000000003                                                          
 Jack                                        column=score:programming, timestamp=1647439990515, value=82                                                                    
 Jack                                        column=score:understanding, timestamp=1647439990515, value=75                                                                  
 Jerry                                       column=info:class, timestamp=1647439990477, value=1                                                                            
 Jerry                                       column=info:student_id, timestamp=1647439990477, value=20210000000002                                                          
 Jerry                                       column=score:programming, timestamp=1647439990477, value=82                                                                    
 Jerry                                       column=score:understanding, timestamp=1647439990477, value=75                                                                  
 Rose                                        column=info:class, timestamp=1647439990552, value=2                                                                            
 Rose                                        column=info:student_id, timestamp=1647439990552, value=20210000000004                                                          
 Rose                                        column=score:programming, timestamp=1647439990552, value=82                                                                    
 Rose                                        column=score:understanding, timestamp=1647439990552, value=75                                                                  
 Tom                                         column=info:class, timestamp=1647439990431, value=1                                                                            
 Tom                                         column=info:student_id, timestamp=1647439990431, value=20210000000001                                                          
 Tom                                         column=score:programming, timestamp=1647439990431, value=82                                                                    
 Tom                                         column=score:understanding, timestamp=1647439990431, value=75                                                                  
 xuzhigang                                   column=info:class, timestamp=1647439990581, value=aaa                                                                          
 xuzhigang                                   column=info:student_id, timestamp=1647439990581, value=G20210607020609                                                         
 xuzhigang                                   column=score:programming, timestamp=1647439990581, value=ccc                                                                   
 xuzhigang                                   column=score:understanding, timestamp=1647439990581, value=bbb                                                                 
5 row(s)
Took 0.1783 seconds                                                                                                                                                         
hbase(main):003:0> get 'xuzhigang:student','xuzhigang'
COLUMN                                       CELL                                                                                                                           
 info:class                                  timestamp=1647439990581, value=aaa                                                                                             
 info:student_id                             timestamp=1647439990581, value=G20210607020609                                                                                 
 score:programming                           timestamp=1647439990581, value=ccc                                                                                             
 score:understanding                         timestamp=1647439990581, value=bbb                                                                                             
1 row(s)
Took 0.0920 seconds                                                                                                                                                         
hbase(main):004:0> 
