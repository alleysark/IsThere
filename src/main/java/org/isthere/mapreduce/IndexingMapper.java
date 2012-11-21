package org.isthere.mapreduce;

import org.isthere.IsThereDocument;
import org.isthere.IsThereDocumentBuilder;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Indexing 을 진행 할 때 한 List 파일의 한 라인씩 읽어서 파일 이름으로
 * HDFS의 url에 접근하여 이미지를 읽은 후에
 * User: Hyunje
 * Date: 12. 11. 17
 * Time: 오전 12:10
 */
public class IndexingMapper extends Mapper<LongWritable, Text, IntWritable, Text> {
    int count;
    int numOfIndex;
    String imagePath;
    String mainDelim;
    String semiDelim;
    IsThereDocumentBuilder docBuilder;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        try {
            numOfIndex = Integer.parseInt(context.getConfiguration().get("numberOfIndex".toUpperCase()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        count = 1;
        imagePath = context.getConfiguration().get("imagePath".toUpperCase());
        mainDelim = context.getConfiguration().get("mainDelim".toUpperCase());
        semiDelim = context.getConfiguration().get("semiDelim".toUpperCase());
        docBuilder = new IsThereDocumentBuilder(mainDelim, semiDelim);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String imageURL = imagePath + value.toString();
        BufferedImage img = ImageIO.read(new URL(imageURL.trim()));
        IsThereDocument doc = new IsThereDocument();
        doc.setValues(docBuilder.createDocument(img, value.toString()));

        context.write(new IntWritable(count), new Text(doc.getValues()));
        count++;
        count = count % numOfIndex;
    }
}

