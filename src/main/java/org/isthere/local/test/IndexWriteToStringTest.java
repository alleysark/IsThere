package org.isthere.local.test;

/**
 * 서버에서 사용될 IndexCreatorTest 클래스. 루씬 인덱스 파일이 아닌 일반적인 텍스트 형식으로 파일 생성.
 * User: Hyunje
 * Date: 12. 11. 12
 * Time: 오후 10:41
 *
 * Index의 형태를 바꾸고 싶다면 StringIndexCreator의 파라메터와, 각각 알고리즘의 StringRepresentation 에서 수정한다.
 */
public class IndexWriteToStringTest {
    static String inputPath;
    static String outputPath;

    public static void main(String[] args) {
        inputPath = "C:\\Dropbox\\이써\\Testset\\Flickr\\Test images";
        outputPath = "C:\\Dropbox\\이써\\Testset\\Flickr\\sampleindex.index";

        /*StringIndexCreator stringIndexCreator = new StringIndexCreator(inputPath,outputPath,"::","||");
        try {
            stringIndexCreator.create();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
