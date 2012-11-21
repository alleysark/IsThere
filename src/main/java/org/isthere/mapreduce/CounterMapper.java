package org.isthere.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 이미지의 갯수를 세는 Mapper. Indexing 작업 이전에 수행된다.
 * User: Hyunje
 * Date: 12. 11. 17
 * Time: 오전 2:56
 * To change this template use File | Settings | File Templates.
 */
public class CounterMapper extends Mapper<LongWritable, Text, NullWritable, NullWritable> {
    private Counter globalCounter;

    protected void setup(Context context) {
        globalCounter = context.getCounter(MainDriver.ISTHERE_COUNTER.NUM_OF_IMAGES);
    }

    protected void map(LongWritable key, Text value, Context context) {
        globalCounter.increment(1);
    }
}
