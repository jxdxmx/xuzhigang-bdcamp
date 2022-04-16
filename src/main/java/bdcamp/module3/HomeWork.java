package bdcamp.module3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HomeWork {

    private static final String TABLE_NAME = "xuzhigang:student";  // 必须先建命名空间徐志刚  hbase shell  / create_namespace 'xuzhigang'
    private static final String cf_info = "info";
    private static final String cf_score = "score";

    public static void createOrOverwrite(Admin admin, TableDescriptor descriptor) throws IOException {
        if (admin.tableExists(descriptor.getTableName())) {
            admin.disableTable(descriptor.getTableName());
            admin.deleteTable(descriptor.getTableName());
        }
        admin.createTable(descriptor);
    }

    public static void createSchemaTables(Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {
            TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(TableName.valueOf(TABLE_NAME));
            builder.setColumnFamily(ColumnFamilyDescriptorBuilder.of(cf_info));
            builder.setColumnFamily(ColumnFamilyDescriptorBuilder.of(cf_score));
            TableDescriptor descriptor = builder.build();

            System.out.print("Creating table. ");
            createOrOverwrite(admin, descriptor);
            System.out.println(" Done.");
        }
    }

    public static void insertData(Configuration config, String name, String student_id, String sClass, String understanding, String programming) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config)) {
            Put put = new Put(name.getBytes(StandardCharsets.UTF_8));
            put.addColumn(cf_info.getBytes(StandardCharsets.UTF_8), "student_id".getBytes(StandardCharsets.UTF_8), student_id.getBytes(StandardCharsets.UTF_8));
            put.addColumn(cf_info.getBytes(StandardCharsets.UTF_8), "class".getBytes(StandardCharsets.UTF_8), sClass.getBytes(StandardCharsets.UTF_8));
            put.addColumn(cf_score.getBytes(StandardCharsets.UTF_8), "understanding".getBytes(StandardCharsets.UTF_8), understanding.getBytes(StandardCharsets.UTF_8));
            put.addColumn(cf_score.getBytes(StandardCharsets.UTF_8), "programming".getBytes(StandardCharsets.UTF_8), programming.getBytes(StandardCharsets.UTF_8));

            connection.getTable(TableName.valueOf(TABLE_NAME)).put(put);
            System.out.println(String.format("success insert data :%s,%s,%s,%s,%s", name, student_id, sClass, understanding, programming));
        }
    }

    public static void main(String... args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.47.66");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        config.set("hbase.master", "172.17.0.2:16000");  // 这里必须使用docker 容器地址！！！否则无法联通。

        createSchemaTables(config);  // 建表
        insertData(config, "Tom", "20210000000001", "1.txt", "75", "82");
        insertData(config, "Jerry", "20210000000002", "1.txt", "75", "82");
        insertData(config, "Jack", "20210000000003", "2", "75", "82");
        insertData(config, "Rose", "20210000000004", "2", "75", "82");
        insertData(config, "xuzhigang", "G20210607020609", "aaa", "bbb", "ccc");
    }
}
