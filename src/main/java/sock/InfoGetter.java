package sock;

import java.io.*;
import java.net.Socket;

/**
 * 소켓을 통해 kipris로 상세정보를 요청 /
 * 받은 응답을 JSON 데이터로 처리
 */
public class InfoGetter {
	String infoString = null;

	public void RequestDetailInfo(String masterKey){
		try{
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

			String line;
			while( (line = bufReader.readLine()) != null ){
				infoString += line;
				//System.out.println(line);
			}
		}catch(Exception e){
			System.out.println("err:" + e.getMessage());
		}
	}

	public String getInfoString(){ return infoString; }

	public void Parsing(){
		if( infoString == null)
			return;


	}
}
