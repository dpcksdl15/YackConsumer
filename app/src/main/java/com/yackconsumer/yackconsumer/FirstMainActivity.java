package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FirstMainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    ImageView bt_button1, bt_button2;

    TextView tv_service, tv_data;

    AsyncTask asyncTask;

    View.OnClickListener cl;

    int viewcount = 0;

    Timer timer;

    private long backbuttonpress = 0;

    Intent intent;


    //코로나 현황판
    TextView covid_time, covid_dayCount, covid_sumCount, covid_dayDeath,covid_sumDeath;

    static String key = "sprTWrIlSrb7BWTxTvDTN%2Bi42nZGHioaXh31AageXSjEZVzttt8QTNKBRnjLYgvLSFVn66xOPQ2dkVwFjZu%2Bbw%3D%3D";

    String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=";
    String url2 = "&pageNo=1&numOfRows=10&startCreateDt=";
    String url3 = "&endCreateDt=";

    URL covid_url;

    ArrayList<Integer> decideCntLsit = new ArrayList<>();
    ArrayList<Integer> deathCntList = new ArrayList<>();

    int startDt, endDt, startDeathDt, endDeathDt;
    String nowDate, beforeDate, toDate;


    int image[] = {R.drawable.main_baaner_1, R.drawable.main_banner_2, R.drawable.main_banner_3};

    Viewpager2Adapter viewpager2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);

        bt_button1 = findViewById(R.id.bt_first_main_button_1);
        bt_button2 = findViewById(R.id.bt_first_main_button_2);

        tv_service = findViewById(R.id.tv_service);
        tv_data = findViewById(R.id.tv_data);
        covid_time = findViewById(R.id.covid_time);
        covid_dayCount = findViewById(R.id.covid_dayCount);
        covid_sumCount = findViewById(R.id.covid_sumConut);
        covid_sumDeath = findViewById(R.id.covid_sumDeath);
        covid_dayDeath = findViewById(R.id.covid_dayDeath);

        viewPager = findViewById(R.id.viewpager_first_main);

        viewpager2Adapter = new Viewpager2Adapter(image);

        viewPager.setAdapter(viewpager2Adapter);

        asyncTask = new AsyncTask();
        asyncTask.execute();

        bennertimer();

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.bt_first_main_button_1:
                        timer.cancel();
                        intent = new Intent(getApplicationContext(), MdSearchActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.bt_first_main_button_2:
                        timer.cancel();
                        intent = new Intent(getApplicationContext(), MdPriceActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.tv_service:
                        timer.cancel();
                        intent = new Intent(getApplicationContext(), ProvisionActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.tv_data:
                        timer.cancel();
                        intent = new Intent(getApplicationContext(), Provision2Activity.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        bt_button1.setOnClickListener(cl);
        bt_button2.setOnClickListener(cl);
        tv_service.setOnClickListener(cl);
        tv_data.setOnClickListener(cl);


        viewpager2Adapter.setOnClickListener(new Viewpager2Adapter.OnListItemSelectedInterface() {
            @Override
            public void onItemSelected(View v, int position) {

                switch (position) {

                    case 0 :
                        timer.cancel();
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCTMBBX1JVT--XQR_peFbRpQ/"));
                        startActivity(intent);
                        break;

                    case 1:
                        timer.cancel();
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://eblue.co.kr/"));
                        startActivity(intent);
                        break;

                    case 2:
                        timer.cancel();
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/eblue_perp_company/?hl=ko/"));
                        startActivity(intent);
                        break;

                }
            }
        });

    }

    public class AsyncTask extends android.os.AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    today_date(0);

                }
            });

            return null;
        }
    }

    public void onBackpressde(){
        onBackPressed();
    }

    public void today_date(int i){
        //오늘 날짜 구하기
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-(i));
        nowDate  = dateformat.format(calendar.getTime());
        Log.d("today", "covid_today: " + nowDate);

        //전 날짜 구하기
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE,-(i+1));
        beforeDate  = dateformat.format(calendar2.getTime());
        Log.d("beforeday", "covid_beforeday: " + beforeDate);

        //날짜 표기
        SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy년-MM월-dd일");
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DATE,-(i));
        toDate  = dateformat2.format(calendar3.getTime());
        covid_time.setText(toDate);

        covid_count();
    }

    //코로나 현황
    public void covid_count(){

        String total_url = url + key + url2 + beforeDate + url3 + nowDate;

        Log.d("dk", "url : " + total_url);

        new Thread() {
            @Override
            public void run() {

        try {
            covid_url = new URL(total_url);

            InputStream is = covid_url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            xpp.next();

            String tagName;
            int count = 0;

            int eventType= xpp.getEventType();

            while ( eventType != XmlPullParser.END_DOCUMENT){

                switch (eventType){

                    case XmlPullParser.START_TAG :
                        tagName = xpp.getName();

                        if (tagName.equals("item")) ;
                            else if (tagName.equals("deathCnt")) {
                            xpp.next();
                            deathCntList.add(Integer.valueOf(xpp.getText()));
                            Log.d("deathCnt", xpp.getText());

                        }
                            else if (tagName.equals("decideCnt")){
                            xpp.next();
                            decideCntLsit.add(Integer.parseInt(xpp.getText()));
                            Log.d("tagname", xpp.getText());

                        }

                        break;

                    case XmlPullParser.END_TAG :
                        tagName= xpp.getName();
                        break;
                }
                eventType= xpp.next();

            }
            is.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

                covid_input();
            }
        } .start();



    }


    public void covid_input() {

        try {
            startDt = decideCntLsit.get(0);
            endDt = decideCntLsit.get(1);
            startDeathDt = deathCntList.get(0);
            endDeathDt = deathCntList.get(1);

            Log.d("확인", String.valueOf(startDt));


            covid_dayCount.setText(String.valueOf(startDt-endDt));
            covid_sumCount.setText(String.valueOf(startDt));
            covid_dayDeath.setText(String.valueOf(startDeathDt-endDeathDt));
            covid_sumDeath.setText(String.valueOf(startDeathDt));
        } catch (Exception e){
            today_date(1);
        }


    }




    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backbuttonpress + 2000 ){

            backbuttonpress = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르시면 종료합니다", Toast.LENGTH_SHORT).show();
            return;

        } else if (System.currentTimeMillis() <= backbuttonpress + 2000){

            finish();

        }
//        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void bennertimer(){
        final Handler handler = new Handler();

    final Runnable update = new Runnable() {
        @Override
        public void run() {
            if (viewcount == 3) {
                viewcount = 0;
            }
            Log.d("실행", String.valueOf(viewcount));
            viewPager.setCurrentItem(viewcount++, true);
        }
    };

        timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.removeMessages(0);
                        handler.post(update);
                        Log.d("실행","스케줄러");
                    }
                }, 500, 4000);
    }



}
