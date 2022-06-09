package com.yackconsumer.yackconsumer;

import static com.yackconsumer.yackconsumer.MdSearchActivity.SslConnect.postHttps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MdSearchActivity extends AppCompatActivity {


    EditText et_md_search_name;
    Button bt_search;

    ImageView bt_back_icon;

    static RecyclerView recyclerView;

    AsyncTask asyncTask;

    MdSearchAdpter adapter;

    TextView tv_count;

    int count =0;

    Bitmap img;

    //일련번호
    ArrayList<String> list = new ArrayList<>();
    //품목명
    ArrayList<String> list2 = new ArrayList<>();
    //업체명
    ArrayList<String> list3 = new ArrayList<>();
    //이미지 사진
    ArrayList<Bitmap> list4 = new ArrayList<>();
    //이미지 url
    ArrayList<String> list5 = new ArrayList<>();

    String key="%2FRj6PnwSChD5W2Md24QgSzON59%2FhVEEUaGNz6Wqatzinv3ynhtlm6Wj5ltMVE3pywr3aDojSz8%2BMNCTzeKbMzg%3D%3D";

    URL url;
    URL url2;

    String queryUrl;
    String queryUrl2;
    String queryUrl3;

    int position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_search);

        et_md_search_name = findViewById(R.id.et_md_search_name);
        bt_search = findViewById(R.id.bt_search);

        tv_count = findViewById(R.id.tv_count);

        bt_back_icon = findViewById(R.id.bt_back_icon);

        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MdSearchAdpter(list,list2,list3,list4);

        bt_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                asyncTask = new AsyncTask();

                asyncTask.execute();

                Log.d("실행확인", String.valueOf(list));

            }
        });

        adapter.setOnClickListener(new MdSearchAdpter.OnListItemSelectedInterface() {
            @Override
            public void onItemSelected(View v, int position) {
                Intent intent = new Intent(MdSearchActivity.this, MdSearchResultActivity.class);

                if (list4.size() != 0 && list4.get(position) != null) {
                    Bitmap img = list4.get(position);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    byte[] byteArray = stream.toByteArray();

                    intent.putExtra("image",byteArray);
                }


                intent.putExtra("name", list2.get(position));
                intent.putExtra("code", list.get(position));

                startActivity(intent);


            }
        });

    }


    public class AsyncTask extends android.os.AsyncTask<Void,Void,Void> {

        ProgressDialog mdialog = new ProgressDialog(MdSearchActivity.this);

        @Override
        protected void onPreExecute() {
            mdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mdialog.setMessage("검색중입니다");

            mdialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            list.clear();
            list2.clear();
            list3.clear();
            list4.clear();
            list5.clear();

            url = null;

            getXmlData();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
                    if (list.size() != 0) {
                        recyclerView.setBackgroundResource(R.drawable.background_mdprice_recyclerview);
                    } else if (list.size() == 0){
                        recyclerView.setBackgroundResource(R.drawable.background_mdprice_recyclerview_empty);
                    }
                    recyclerView.setAdapter(adapter);
                    Log.d("확인", "확인시간");
                    tv_count.setText("총 " + list.size() + "개");
                    adapter.notifyDataSetChanged();

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            mdialog.dismiss();
        }

        @Override
        protected void onCancelled() {

            super.onCancelled();
            mdialog.cancel();
        }
    }

    public void getXmlData(){

        //EditText에 작성된 Text얻어오기
        String str= et_md_search_name.getText().toString();


        boolean urlversion = str.matches("[+-]?\\d*(\\.\\d+)?");
        Log.d("확인", String.valueOf(urlversion) + str);
        try{

            if (urlversion == false) {

                //숫자열로 된 요청 url을 URL 객체로 생성.
                queryUrl  = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?serviceKey=" + key + "&numOfRows=10&pageNo=1&itemName=" + str;
                url= new URL(queryUrl);
                Log.d("확인", queryUrl);

            } else if (urlversion == true){

                //문자열로 된 요청 url을 URL 객체로 생성.
                queryUrl2 ="http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?serviceKey=" + key + "&numOfRows=10&pageNo=1&itemSeq=" + str;
                url= new URL(queryUrl2);
                Log.d("확인", queryUrl2);
            }

            //url위치로 입력스트림 연결
            InputStream is= url.openStream();

            //xml파싱
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );

            String tag;

            xpp.next();

            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){

                switch( eventType ){

                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();


                        if(tag.equals("item")) ;
                        else if(tag.equals("itemSeq")){

                            xpp.next();
                            Log.d("확인",xpp.getText());
                            list.add(xpp.getText());

                        }
                        else if(tag.equals("itemName")){

                            xpp.next();
                            Log.d("확인",xpp.getText());
                            list2.add(xpp.getText());

                        }
                        else if(tag.equals("entpName")){

                            xpp.next();
                            Log.d("확인",xpp.getText());
                            list3.add(xpp.getText());

                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:


                }

                eventType= xpp.next();
            }


            is.close();
            getXmlImage();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getXmlImage(){

        try {
            for (int i =0 ; list.size() > i ; i++) {
                queryUrl3 = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?serviceKey=" + key + "&itemSeq=" + list.get(i);
                url2 = new URL(queryUrl3);
                Log.d("확인", queryUrl3);

                //url위치로 입력스트림 연결
                InputStream is = url2.openStream();

                //xml파싱
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;

                xpp.next();

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    switch (eventType) {

                        case XmlPullParser.START_DOCUMENT:
                            break;

                        case XmlPullParser.START_TAG:
                            tag = xpp.getName();


                            if (tag.equals("item")) ;
                            else if (tag.equals("itemImage")) {
                                xpp.next();

                                if (xpp.getText() != null) {
                                    Log.d("확인", xpp.getText());
                                    list5.add(xpp.getText());
                                } else {
                                    list5.add(null);
                                }
                            }

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            tag = xpp.getName();

                    }

                    eventType = xpp.next();
                }

                is.close();
            }

            if (list5.size() != 0) {
                Log.d("확인", "실행");
                for (int i =0 ; list5.size() > i ; i++) {
                    img = getBitmap(list5.get(i));
                    list4.add(img);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private Bitmap getBitmap(String url) {

        HttpURLConnection connection = null;
        InputStream is = null;
        Bitmap retBitmap = null;

        if (url != null) {

            Log.d("확인url", url);
            URL imgUrl = null;

            try {
                postHttps(url, 100, 100);
                imgUrl = new URL(url);
                connection = (HttpURLConnection) imgUrl.openConnection();
                connection.setDoInput(true);

                connection.connect();

                is = connection.getInputStream();
                retBitmap = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                return retBitmap;

            }
        } else {

            return null;
        }

    }

    //웹서비스 요청시 SSL 인증서 에러 방지
    public static class SslConnect {

        // always verify the host - dont check for certificate
        final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        /**
         * Trust every server - don't check for any certificate
         */
        private static void trustAllHosts() {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            }};

            // Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static HttpsURLConnection postHttps(String url, int connTimeout, int readTimeout) {
            trustAllHosts();

            HttpsURLConnection https = null;
            try {
                https = (HttpsURLConnection) new URL(url).openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                https.setConnectTimeout(connTimeout);
                https.setReadTimeout(readTimeout);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return https;
        }
    }



}