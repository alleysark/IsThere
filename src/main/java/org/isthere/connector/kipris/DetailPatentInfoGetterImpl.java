package org.isthere.connector.kipris;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Service
public class DetailPatentInfoGetterImpl implements DetailPatentInfoGetter {

    @Override
    public Map<String, String> requestDetail(String masterKey) {
        Map<String,String> parseResult = null;
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

            String rawHtmlResult = "";
            String line = "";

            while ((line = bufReader.readLine()) != null) {
                rawHtmlResult += line;
            }

            parseResult = parseDataFromHtmlString(rawHtmlResult);
        } catch (Exception e) {
            throw new RuntimeException("Kipris 데이터에 접근할 수 없습니다.", e);
        }
        return parseResult;
    }

    private Map<String, String> parseDataFromHtmlString(String  rawHtmlResult) {
        Map<String, String> responseMap = new HashMap<String, String>();

        //html validation check
        //(check infoString)

        try {
            //parse html string to jsoup document
            Element elemContainer = Jsoup.parse(rawHtmlResult).getElementById("container");

            // 명칭
            String title = elemContainer.getElementsByClass("summary_item_tit").get(0).text();
            if (null != title) {
                responseMap.put(
                        "titleKr",
                        title.substring(title.indexOf(':')+1, title.indexOf('영')).trim()
                );
                responseMap.put(
                        "titleEn",
                        title.substring(title.lastIndexOf(':')+1).trim()
                );
            }

            Elements elemTrs = elemContainer.getElementsByTag("tr");
            Element elemTempTr = null;

            // 상품분류
            elemTempTr = elemTrs.get(0).getElementsByTag("a").first();
            if (null != elemTempTr) {
                responseMap.put("classificDet", elemTempTr.attr("title").trim());
                responseMap.put("classificName", elemTempTr.text().trim());
            } else {
                responseMap.put("classificDet", ".");
                responseMap.put("classificName", ".");
            }

            // 구분
            elemTempTr = elemTrs.get(1).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("separation", elemTempTr.text().replace("\u00a0", " ").trim());
            } else {
                responseMap.put("separation", "");
            }

            // 출원번호(일자)
            elemTempTr = elemTrs.get(2).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("appNo", elemTempTr.text().replace("\u00a0", "").trim());
            } else {
                responseMap.put("appNo", "");
            }

            // 등록번호(일자)
            // !등록번호가 있는 상세보기 페이지가 없음
            //elemTempTr = elemTrs.get(3).getElementsByTag("td").get(1);

            // 공고번호
            elemTempTr = elemTrs.get(4).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("announceNo", elemTempTr.text().replace("\u00a0", "").trim());
            } else {
                responseMap.put("announceNo", "");
            }

            // 원출원번호(일자)
            elemTempTr = elemTrs.get(5).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("originAppNo", elemTempTr.text().replace("\u00a0", "").trim());
            } else {
                responseMap.put("originAppNo", "");
            }

            // 관련출원번호
            // a 태그 텍스트로 데이터가 있을것으로 예상되는데, 확인할만한 서지가 없음
            elemTempTr = elemTrs.get(6).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("relAppNo", elemTempTr.text().replace("\u00a0", "").trim());
            } else {
                responseMap.put("relAppNo", "");
            }

            // 우선권 주장번호(일자)
            elemTempTr = elemTrs.get(7).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("priorityAssertionNo", elemTempTr.text().replace("\u00a0", "").trim());
            } else {
                responseMap.put("priorityAssertionNo", "");
            }

            // 소급구분(일자)
            elemTempTr = elemTrs.get(8).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("retroactive", elemTempTr.text().replace("\u00a0", "").trim());
            } else {
                responseMap.put("retroactive", "");
            }

            // 최종처분(일자)
            elemTempTr = elemTrs.get(9).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("disposal", elemTempTr.text().replace("\u00a0", "").trim());
            } else {
                responseMap.put("disposal", "");
            }

            // 심판사항
            elemTempTr = elemTrs.get(10).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("judge", elemTempTr.text().replace("\u00a0", "").trim());
            } else {
                responseMap.put("judge", "");
            }

            // 등록상태
            elemTempTr = elemTrs.get(11).getElementsByTag("td").get(1);
            if (null != elemTempTr) {
                responseMap.put("regState", elemTempTr.text().replace("\u00a0", "").trim());
            } else {
                responseMap.put("regState", "");
            }
        } catch (Exception e) {
           throw new RuntimeException("특허 정보를 가져올 수 없습니다.", e);
        }
        return responseMap;
    }
}
