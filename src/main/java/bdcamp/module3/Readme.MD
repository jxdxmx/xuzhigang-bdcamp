# 下载docker镜像
docker pull harisekhon/hbase

#启动
docker run -itd -p 2181:2181 -p 8080:8080 -p 8085:8085 -p 9090:9090 \
    -p 16000:16000 -p 16010:16010 -p 16201:16201 -p 16030:16030 \
    -p 16020:16020 --name jikeshijian_hbase harisekhon/hbase
    
# 测试，打开UI
192.168.47.66:16010

# 进入hbase容器
docker exec -it jikeshijian_hbase bash
# 进入hbase shell
hbase shell

HBase（HBase shell 进入）：
（1）创建命名空间：create_namespace 'jinlantao_test'
（3）查询命名空间：list_namespace
（4）列出表：list
（5）创建表：create 'jinlantao_test:test1', 'cf1'
（6）描述表：describe 'jinlantao_test:test1'                          # 单引号必须有，不能省略
（7）插入数据：put 'jinlantao_test:test1', 'r1', 'cf1:c1', 'value'
（8）获取数据：get 'jinlantao_test:test1', 'r1'            # 也可以使用get 'jinlantao_test:test1', 'r1','cf1'   、get 'jinlantao_test:test1', 'r1','cf1:c1'
（9）扫描表：scan 'jinlantao_test:test1'
（10）统计总数：count 'jinlantao_test:test1'
（11）删除数据：delete 'jinlantao_test:test1', 'r1', 'cf1:c1'
（12）禁止表：disable 'jinlantao_test:test1'
（13）删除表：drop 'jinlantao_test:test1'

删除命名空间：drop_namespace 'jinlantao_test'


hbase(main):028:0* get 'jinlantao_test:test1', 'r1','cf1'
COLUMN                                       CELL                                                                                                                           
 cf1:c1                                      timestamp=1647427317201, value=value                                                                                           
1 row(s)
Took 0.0132 seconds                                                                                                                                                         
hbase(main):029:0> get 'jinlantao_test:test1', 'r1','cf1:c1'
COLUMN                                       CELL                                                                                                                           
 cf1:c1                                      timestamp=1647427317201, value=value                                                                                           
1 row(s)
Took 0.0076 seconds                                 


    private static final String TABLE_NAME = "xuzhigang:student";  // 必须先建命名空间徐志刚  hbase shell  / create_namespace 'xuzhigang'
        config.set("hbase.master", "172.17.0.2:16000");  // 这里必须使用docker 容器地址！！！否则无法联通。
bash-4.4# netstat -nltp|grep 6
tcp        0      0 0.0.0.0:16010           0.0.0.0:*               LISTEN      48/java
tcp        0      0 172.17.0.2:16020        0.0.0.0:*               LISTEN      117/java
tcp        0      0 0.0.0.0:16030           0.0.0.0:*               LISTEN      117/java
tcp        0      0 172.17.0.2:16000        0.0.0.0:*               LISTEN      48/java     
bash-4.4# 
    # 这里关联的地址是172.17.0.2/容器地址！！！所以，无法在主机上跑~~~ 可以在docker中跑。注：虚拟机中还是不通！
-------------------------------------
Ubuntu虚拟机中安装java，直接运行jar包 ！！ 不行。。
root@k8s66mgr:~/bdcamp# apt install openjdk-8-jre-headless -y
root@k8s66mgr:~/bdcamp# java -jar bdlearning-module3-1.0-jar-with-dependencies.jar 
--------------------------------------
原因分析：
bash-4.4# /hbase-2.1.3/conf/hbase-site.xml 中使用了localhoost 2181.怪不得不通！！！  ===== 也不对，改成127.0.0.1之后，在虚拟机中跑、还是不通！！！
  <property>
    <name>hbase.zookeeper.quorum</name>
    <value>localhost:2181</value>
  </property>
修改一下，改成0.0.0.0
bash-4.4# netstat nltp|grep 2181
tcp        0      0 localhost:2181          localhost:48556         ESTABLISHED 
tcp        0      0 localhost:2181          localhost:48562         ESTABLISHED 
tcp        0      0 localhost:2181          localhost:48554         ESTABLISHED 
tcp        0      0 localhost:48554         localhost:2181          ESTABLISHED 
tcp        0      0 localhost:48556         localhost:2181          ESTABLISHED 
tcp        0      0 localhost:48562         localhost:2181          ESTABLISHED 
bash-4.4# 


root@k8s66mgr:~/bdcamp# docker cp bdlearning-module3-1.0-jar-with-dependencies.jar 40:/
# 在容器跑！！！终于通了！！！
bash-4.4# java -jar bdlearning-module3-1.0-jar-with-dependencies.jar
	at org.apache.hadoop.hbase.master.MasterRpcServices.createTable(MasterRpcServices.java:630)
	at org.apache.hadoop.hbase.shaded.protobuf.generated.MasterProtos$MasterService$2.callBlockingMethod(MasterProtos.java)
	at org.apache.hadoop.hbase.ipc.RpcServer.call(RpcServer.java:413)
	at org.apache.hadoop.hbase.ipc.CallRunner.run(CallRunner.java:130)
	at org.apache.hadoop.hbase.ipc.RpcExecutor$Handler.run(RpcExecutor.java:324)
	at org.apache.hadoop.hbase.ipc.RpcExecutor$Handler.run(RpcExecutor.java:304)
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient.onCallFinished(AbstractRpcClient.java:387)
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient.access$100(AbstractRpcClient.java:95)
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient$3.run(AbstractRpcClient.java:410)
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient$3.run(AbstractRpcClient.java:406)
	at org.apache.hadoop.hbase.ipc.Call.callComplete(Call.java:103)
	at org.apache.hadoop.hbase.ipc.Call.setException(Call.java:118)
	at org.apache.hadoop.hbase.ipc.NettyRpcDuplexHandler.readResponse(NettyRpcDuplexHandler.java:162)
	at org.apache.hadoop.hbase.ipc.NettyRpcDuplexHandler.channelRead(NettyRpcDuplexHandler.java:192)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)
	at org.apache.hbase.thirdparty.io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:310)
	at org.apache.hbase.thirdparty.io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:284)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)
	at org.apache.hbase.thirdparty.io.netty.handler.timeout.IdleStateHandler.channelRead(IdleStateHandler.java:286)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)
	at org.apache.hbase.thirdparty.io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1359)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)
	at org.apache.hbase.thirdparty.io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:935)
	at org.apache.hbase.thirdparty.io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:138)
	at org.apache.hbase.thirdparty.io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:645)
	at org.apache.hbase.thirdparty.io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:580)
	at org.apache.hbase.thirdparty.io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:497)
	at org.apache.hbase.thirdparty.io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:459)
	at org.apache.hbase.thirdparty.io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:858)
	at org.apache.hbase.thirdparty.io.netty.util.concurrent.DefaultThreadFactory$DefaultRunnableDecorator.run(DefaultThreadFactory.java:138)
	at java.lang.Thread.run(Thread.java:748)
bash-4.4# 






                                                                                                                        