package bdcamp.module2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

class PhoneTraffic implements Writable {
    public String Phone; // 手机号码
    public long Up; // 上行流量
    public long Down;// 下行流量
    public long Total;// 总流量

    @Override
    public String toString() {
        return String.format("%s %d %d %d", Phone, Up, Down, Total);
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(Phone);
        out.writeLong(Up);
        out.writeLong(Down);
        out.writeLong(Total);
    }

    public void readFields(DataInput in) throws IOException {
        Phone = in.readUTF();
        Up = in.readLong();
        Down = in.readLong();
        Total = in.readLong();
    }
}

public class Traffic {

    public static class TrafficMapper extends Mapper<Object, Text, Text, PhoneTraffic> {

        @Override
        public void setup(Context context) {
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            System.out.println(String.format("map --- key:%s,value:%s", key, value)); // debug # 这里的key，居然是同文件的前一行的末尾index！！即：key就是字符index！！

            String[] arr = value.toString().split("[ \t\n]");
            if (arr.length != 12) {
                System.err.println(String.format("map --- data err:%s", value));
                return;
            }

            PhoneTraffic pt = new PhoneTraffic();
            pt.Phone = arr[1];
            pt.Up = Long.parseLong(arr[9]);
            pt.Down = Long.parseLong(arr[10]);
            pt.Total = pt.Up + pt.Down;
            context.write(new Text(pt.Phone), pt);
        }
    }

    public static class TrafficReducer extends Reducer<Text, PhoneTraffic, Text, PhoneTraffic> {
        private PhoneTraffic result = new PhoneTraffic();

        public void reduce(Text key, Iterable<PhoneTraffic> values, Context context) throws IOException, InterruptedException {
            System.out.println(String.format("reduce --- key:%s,values:%s", key, values)); // debug

            for (PhoneTraffic pt : values) {
                result.Up += pt.Up;
                result.Down += pt.Down;
                result.Total += pt.Total;
            }
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        GenericOptionsParser optionParser = new GenericOptionsParser(conf, args);
        String[] remainingArgs = optionParser.getRemainingArgs();
        if ((remainingArgs.length != 2)) {
            System.err.println("Usage: traffic <in> <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "traffic sum");
        job.setJarByClass(Traffic.class);
        job.setMapperClass(TrafficMapper.class); // MapperClass
        job.setCombinerClass(TrafficReducer.class);  // CombinerClass
        job.setReducerClass(TrafficReducer.class);// ReducerClass
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(PhoneTraffic.class);

        FileInputFormat.addInputPath(job, new Path(remainingArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(remainingArgs[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
