package cocoatalk.login;

// 네이버 검색 API 예제 - 블로그검색 

///파싱해줄 성민오빠 구함~~~

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//import org.json.simple.JSONArray;

public class NaverConnect {

  JSONParser parser = new JSONParser();
  JSONObject jsonObj = null;
  JSONArray jsonArray = null;
  String clientId = "8MVmqhDdydQc37JR89Zm"; // 애플리케이션 클라이언트 아이디
  String clientSecret = "lTO9ptwKR8"; // 애플리케이션 클라이언트 시크릿
  String text = null;
  int display = 5;
  ArrayList<String> titlelist = null;
  ArrayList<String> catelist = null;
  ArrayList<String> addlist = null;

  private static String get(String apiUrl, Map<String, String> requestHeaders) {
    HttpURLConnection con = connect(apiUrl);
    try {
      con.setRequestMethod("GET");
      for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
        con.setRequestProperty(header.getKey(), header.getValue());
      }

      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
        return readBody(con.getInputStream());
      } else { // 오류 발생
        return readBody(con.getErrorStream());
      }
    } catch (IOException e) {
      throw new RuntimeException("API 요청과 응답 실패", e);
    } finally {
      con.disconnect();
    }
  }

  private static HttpURLConnection connect(String apiUrl) {
    try {
      URL url = new URL(apiUrl);
      return (HttpURLConnection) url.openConnection();
    } catch (MalformedURLException e) {
      throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
    } catch (IOException e) {
      throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
    }
  }

  private static String readBody(InputStream body) throws UnsupportedEncodingException {
    // InputStreamReader streamReader = new InputStreamReader(body);
    InputStreamReader streamReader = new InputStreamReader(body, "UTF-8");

    try (BufferedReader lineReader = new BufferedReader(streamReader)) {
      StringBuilder responseBody = new StringBuilder();

      String line;
      while ((line = lineReader.readLine()) != null) {
        responseBody.append(line);
      }

      return responseBody.toString();
    } catch (IOException e) {
      throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
    }

  }

  public void search(String msg) {

    try {
      text = URLEncoder.encode(msg, "UTF-8");

    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("검색어 인코딩 실패", e);
    }

    String apiURL = "https://openapi.naver.com/v1/search/local.json?display=5&query=" + text; // JSON 결과

    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("X-Naver-Client-Id", clientId);
    requestHeaders.put("X-Naver-Client-Secret", clientSecret);
    // String responseBody = get(apiURL, requestHeaders);
    String responseBody = get(apiURL, requestHeaders);

    titlelist = new ArrayList<>();
    catelist = new ArrayList<>();
    addlist = new ArrayList<>();

    // json parser로 title, category, address 추출
    try {
      jsonObj = (JSONObject) parser.parse(responseBody);
      jsonArray = (JSONArray) jsonObj.get("items");
      for (int i = 0; i < jsonArray.size(); i++) {
        JSONObject getObj = (JSONObject) jsonArray.get(i);
        titlelist.add((String) getObj.get("title"));
        catelist.add((String) getObj.get("category"));
        addlist.add((String) getObj.get("address"));
      }

    } catch (Exception e) {

    }
  }

  public static void main(String[] args) {
    NaverConnect nc = new NaverConnect();
    nc.search("역삼 맛집");
    System.out.println(nc.titlelist.get(0));
  }

}
