package com.yackconsumer.yackconsumer;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServerRegisterActivity extends AsyncTask<String, Void, String> {
    // vlaue값
    // 0:id확인, 1:가입중복확인, 2:가입, 3:로그인, 4:전자영수증, 5:전자영수증자세히보기

    String sendMsg, receiveMsg;
    URL url;
    HttpURLConnection conn;
    OutputStreamWriter osw;


    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;

            // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
            //url = new URL("http://eblue.iptime.org:3389/DbConn/Android/yackConsumer.jsp");
            url = new URL("http://123.143.216.253:3389/DbConn/Android/yackConsumer.jsp");
            // http://ip주소:포트번호/이클립스프로젝트명/WebContent아래폴더/androidDB.jsp

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

            // 전송할 데이터. GET 방식으로 작성
            if (strings[0].equals("0")) {
                sendMsg = "value=" + strings[0] + "&id=" + strings[1];
                Log.d("0번 ID중복확인 보내기", "확인");
            } else if (strings[0].equals("1")) {
                sendMsg = "value=" + strings[0] + "&user_hp=" + strings[1] + "&modifi_ymd=" + strings[2];
                Log.d("1번 중복가입확인 보내기", "확인");
            }else if (strings[0].equals("2")){
                sendMsg = "value=" + strings[0] + "&id=" + strings[1] + "&pw=" + strings[2] + "&user_nm=" + strings[3] + "&user_hp=" + strings[4] + "&city=" + strings[5]
                        + "&modifi_ymd=" + strings[6] + "&user_uqnum=" + strings[7] + "&sex=" + strings[8] + "&stipulation=" + strings[9] + "&pims=" + strings[10] + "&reporting=" + strings[11];
                Log.d("2번 가입 보내기", "확인");
            } else if (strings[0].equals("3")){
                sendMsg = "value=" + strings[0] + "&id=" + strings[1] + "&pw=" + strings[2];
                Log.d("3번 로그인 보내기", "확인");
            } else if (strings[0].equals("4")){
                sendMsg = "value=" + strings[0] + "&id=" + strings[1] + "&hp=" + strings[2] + "&st=" + strings[3] + "&ed=" + strings[4];
                Log.d("4번 전자영수증 보내기", sendMsg);
            } else if (strings[0].equals("5")){
                sendMsg = "value=" + strings[0] + "&saleno=" + strings[1];
                Log.d("5번 전자영수증자세히 보내기", sendMsg);
            }

            osw.write(sendMsg);
            osw.flush();

            //jsp와 통신 성공 시 수행
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                // jsp에서 보낸 값을 받는 부분
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                // 통신 실패
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //jsp로부터 받은 리턴 값
        return receiveMsg;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("RESULT", " <<<<<onPostExecute>>>> ");



    }
}

