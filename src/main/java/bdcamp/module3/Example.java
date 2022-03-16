package bdcamp.module3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Example {

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

    public static void modifySchema(Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {

            TableName tableName = TableName.valueOf(TABLE_NAME);
            if (!admin.tableExists(tableName)) {
                System.out.println("Table does not exist.");
                System.exit(-1);
            }

            TableDescriptor descriptor = admin.getDescriptor(tableName);

            // Update existing table
            ColumnFamilyDescriptor cf = ColumnFamilyDescriptorBuilder.newBuilder("NEWCF".getBytes(StandardCharsets.UTF_8)).
                    setCompactionCompressionType(Algorithm.GZ).
                    setMaxVersions(HConstants.ALL_VERSIONS).build();
            admin.addColumnFamily(tableName, cf);

            // Update existing column family
            ColumnFamilyDescriptor cf_exists = ColumnFamilyDescriptorBuilder.newBuilder(cf_info.getBytes(StandardCharsets.UTF_8)).
                    setCompactionCompressionType(Algorithm.GZ).
                    setMaxVersions(HConstants.ALL_VERSIONS).build();
            admin.modifyColumnFamily(tableName, cf_exists);

            admin.modifyTable(descriptor);

            // Disable an existing table
            admin.disableTable(tableName);

            // Delete an existing column family
            admin.deleteColumnFamily(tableName, cf_info.getBytes(StandardCharsets.UTF_8));

            // Delete a table (Need to be disabled first)
            admin.deleteTable(tableName);
        }
    }

    public static void main(String... args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.47.66");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        config.set("hbase.master", "172.17.0.2:16000");  // 这里必须使用docker 容器地址！！！否则无法联通。

        //Add any necessary configuration files (hbase-site.xml, core-site.xml)
//        config.addResource(new Path(System.getenv("HBASE_CONF_DIR"), "hbase-site.xml"));
//        config.addResource(new Path(System.getenv("HADOOP_CONF_DIR"), "core-site.xml"));
        createSchemaTables(config);
        modifySchema(config);
    }
}
