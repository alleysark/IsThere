package sock;

import java.util.*;
import java.io.*;
import java.net.Socket;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import net.sf.json.*;

/**
 * 소켓을 통해 kipris로 상세정보를 요청 /
 * 받은 응답을 JSON 데이터로 처리
 */
public class InfoGetter {
    String infoString = null;

    public void RequestDetailInfo(String masterKey) {
        try {
            Socket s = new Socket("dets.kipris.or.kr", 80);

            BufferedWriter bufWriter
                    = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            BufferedReader bufReader
                    = new BufferedReader((new InputStreamReader(s.getInputStream())));

            bufWriter.write(
                    "POST /ndets/srbl1000a.do?method=bibliographyTM&masterKey=" + masterKey + "&index=-1 HTTP/1.1\r\n" +
                            "Host: dets.kipris.or.kr\r\n" +
                            "Connection: keep-alive\r\n" +
                            "Content-Length: 0\r\n" +
                            "Cache-Control: max-age=0\r\n" +
                            "Origin: http://kipris.or.kr\r\n" +
                            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11\r\n" +
                            "Content-Type: application/x-www-form-urlencoded\r\n" +
                            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
                            "Referer: http://localhost/getHtmldata/index2.html\r\n" +
                            "Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4\r\n" +
                            "Accept-Charset: windows-949,utf-8;q=0.7,*;q=0.3\r\n" +
                            "Cookie: JSESSIONID=0a856a4630df2655514b64ab444eaba2b5aa092688d5.e38NchqQb3eTe34Sah0LaxuRahj0; WMONID=DOOj6DGnK_N; __utma=114380957.1116794586.1349930434.1352730684.1352803987.13; __utmb=114380957.1.10.1352803987; __utmc=114380957; __utmz=114380957.1350085005.4.4.utmcsr=naver|utmccn=(organic)|utmcmd=organic|utmctr=%ED%82%A4%ED%94%84%EB%A6%AC%EC%8A%A4; NTM_CONFIG=G11111111111111111SX01111111111111; NKP_CONFIG=G1111111111111111111111S110001000000000000; KP_CONFIG=4111211177517311151711115111100000000000; NAB_CONFIG=G11001111111111111111111111110S10000010000000010000; NDG_CONFIG=G11111111111111111SX10000110011101011; NJM_CONFIG=G11111111111111SX011100000100011\r\n\r\n"
            );
            bufWriter.flush();

            infoString = "";
            String line;
            while ((line = bufReader.readLine()) != null) {
                infoString += line;
                //System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("err:" + e.getMessage());
        }
    }

    public String getInfoString() {
        return infoString;
    }

    public JSONObject parseDataFromHtmlString() {

        JSONObject jsonRoot = new JSONObject();
        JSONArray jsonElems = new JSONArray();

        //html validation check
        //(check infoString)
        if (infoString.isEmpty()) {
            try {
                jsonRoot.put("success", false);
                jsonRoot.put("total", 0);
                jsonRoot.put("detInfo", jsonElems);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return jsonRoot;
        }

        try {
            //parse html string to jsoup document
            Element container = Jsoup.parse(infoString).getElementById("container");

            JSONObject tmpObj = new JSONObject();

            // 명칭
            String title = container.getElementsByClass("summary_item_tit").get(0).text();
            if (null != title) {
                tmpObj.put(
                        "titleKr",
                        title.substring(title.indexOf(':')+1, title.indexOf('영')).trim()
                );
                tmpObj.put(
                        "titleEn",
                        title.substring(title.lastIndexOf(':')+1).trim()
                );
            }
            System.out.println("Title : \'" + tmpObj.get("titleKr") + "\' \'" + tmpObj.get("titleEn") + "\'");

            Elements trs = container.getElementsByTag("tr");
            Element tmp = null;

            // 상품분류
            tmp = trs.get(0).getElementsByTag("a").first();
            if (null != tmp) {
                tmpObj.put("classificDet", tmp.attr("title").trim());
                tmpObj.put("classificName", tmp.text().trim());
            } else {
                tmpObj.put("classificDet", ".");
                tmpObj.put("classificName", ".");
            }
            System.out.println("상품분류 : " + tmpObj.get("classificDet") + "\n" +
                    ", " + tmpObj.get("classificName")
            );

            // 구분
            tmp = trs.get(1).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("separation", tmp.text().replace("\u00a0", " ").trim());
            } else {
                tmpObj.put("separation", "");
            }
            System.out.println("구분 : " + tmpObj.get("separation"));

            // 출원번호(일자)
            tmp = trs.get(2).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("appNo", tmp.text().replace("\u00a0", "").trim());
            } else {
                tmpObj.put("appNo", "");
            }
            System.out.println("출원번호 : " + tmpObj.get("appNo"));

            // 등록번호(일자)
            // !등록번호가 있는 상세보기 페이지가 없음
            //tmp = trs.get(3).getElementsByTag("td").get(1);

            // 공고번호
            tmp = trs.get(4).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("announceNo", tmp.text().replace("\u00a0", "").trim());
            } else {
                tmpObj.put("announceNo", "");
            }
            System.out.println("공고번호 : " + tmpObj.get("announceNo"));

            // 원출원번호(일자)
            tmp = trs.get(5).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("originAppNo", tmp.text().replace("\u00a0", "").trim());
            } else {
                tmpObj.put("originAppNo", "");
            }
            System.out.println("원출원번호 : " + tmpObj.get("originAppNo"));

            // 관련출원번호
            // a 태그 텍스트로 데이터가 있을것으로 예상되는데, 확인할만한 서지가 없음
            tmp = trs.get(6).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("relAppNo", tmp.text().replace("\u00a0", "").trim());
            } else {
                tmpObj.put("relAppNo", "");
            }
            System.out.println("관련출원번호 : " + tmpObj.get("relAppNo"));

            // 우선권 주장번호(일자)
            tmp = trs.get(7).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("priorityAssertionNo", tmp.text().replace("\u00a0", "").trim());
            } else {
                tmpObj.put("priorityAssertionNo", "");
            }
            System.out.println("우선권주장번호 : " + tmpObj.get("priorityAssertionNo"));

            // 소급구분(일자)
            tmp = trs.get(8).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("retroactive", tmp.text().replace("\u00a0", "").trim());
            } else {
                tmpObj.put("retroactive", "");
            }
            System.out.println("소급구분 : " + tmpObj.get("retroactive"));

            // 최종처분(일자)
            tmp = trs.get(9).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("disposal", tmp.text().replace("\u00a0", "").trim());
            } else {
                tmpObj.put("disposal", "");
            }
            System.out.println("최종처분 : " + tmpObj.get("disposal"));

            // 심판사항
            tmp = trs.get(10).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("judge", tmp.text().replace("\u00a0", "").trim());
            } else {
                tmpObj.put("judge", "");
            }
            System.out.println("심판사항 : " + tmpObj.get("judge"));

            // 등록상태
            tmp = trs.get(11).getElementsByTag("td").get(1);
            if (null != tmp) {
                tmpObj.put("regState", tmp.text().replace("\u00a0", "").trim());
            } else {
                tmpObj.put("regState", "");
            }
            System.out.println("등록상태 : " + tmpObj.get("regState"));

            jsonElems.add(tmpObj);
            jsonRoot.put("success", true);
            jsonRoot.put("total", 1);
            jsonRoot.put("detInfo", jsonElems);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jsonRoot;
    }
}
