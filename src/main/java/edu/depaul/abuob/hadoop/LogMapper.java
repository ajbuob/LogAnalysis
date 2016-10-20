package edu.depaul.abuob.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static final String LOG_LEVEL_REG_EX = "\\[(.*?)\\]";
    private static final Pattern LOG_LEVEL_PATTERN = Pattern.compile(LOG_LEVEL_REG_EX);

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();
        String logLevel;

        //Check for a valid line and that it contains [LOG_LEVEL]
        if (line != null && !line.isEmpty()) {

            Matcher matcher = LOG_LEVEL_PATTERN.matcher(line);
            boolean isMatch = matcher.find();

            if(isMatch) {
                //Parse the LOG_LEVEL from the line
                logLevel = "[" + matcher.group(1) + "]";

                //Add instance of [LOG_LEVEL] to the map result
                context.write(new Text(logLevel), new IntWritable(1));
            }
        }
    }
}