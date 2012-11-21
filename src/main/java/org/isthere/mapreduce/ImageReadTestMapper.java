package org.isthere.mapreduce;

import org.isthere.IsThereDocument;
import org.isthere.IsThereDocumentBuilder;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * HDFS의 image 를 URL로 불러들이는 작업이 가능한지 테슽트 하는 Mapper.
 * User: Hyunje
 * Date: 12. 11. 17
 * Time: 오전 6:20
 */
public class ImageReadTestMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
    String imagePath;
    IsThereDocumentBuilder builder;
    String HDFS_PROTOCOL = "HDFS:/";
    Path pathOfImg;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        imagePath = context.getConfiguration().get("imagePath".toUpperCase());
        builder = new IsThereDocumentBuilder("::", "||");
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//            context.write(NullWritable.get(),new Text(value));
        String imageURL = HDFS_PROTOCOL + imagePath + value.toString();
//        context.write(NullWritable.get(),new Text(imageURL));
        pathOfImg = new Path(HDFS_PROTOCOL + imagePath);
        FileSystem fs = pathOfImg.getFileSystem(context.getConfiguration());
//        fs.
        FSDataInputStream stream = fs.open(pathOfImg);
        BufferedImage bimg = ImageIO.read(new File(pathOfImg.toString() + value.toString()));
//        context.write(NullWritable.get(),new Text(pathOfImg.toString()+value.toString()));
//        BufferedImage img = ImageIO.read(new URL(imageURL.trim()));
        IsThereDocument doc = new IsThereDocument();
        doc.setValues(builder.createDocument(bimg, value.toString()));
        context.write(NullWritable.get(), new Text(doc.getValues()));
    }
}
