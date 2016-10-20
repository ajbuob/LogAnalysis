package edu.depaul.abuob.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class LogReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int logLevelCount = 0;

        //Loop over each [LOG_LEVEL] and add all instances in input file
        for (IntWritable value : values) {
            logLevelCount += value.get();
        }

        context.write(key, new IntWritable(logLevelCount));
    }
}