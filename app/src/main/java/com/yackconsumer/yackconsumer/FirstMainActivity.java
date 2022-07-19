package com.yackconsumer.yackconsumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class FirstMainActivity extends AppCompatActivity {

    public static Activity firstmain;

    ViewPager2 viewPager;

    ImageView bt_button1, bt_button2, bt_button3, bt_mypage;

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    TextView tv_service, tv_data;

    View.OnClickListener cl;

    AsyncTask asyncTask;

    SharedPreferences sharedPreferences;

    String auto_login_info;
    String auto_login_nm;

    int viewcount = 0;

    Timer timer;

    int today_count =0;

    private long backbuttonpress = 0;

    Intent intent;

    int image[] = {R.drawable.main_baaner_1, R.drawable.main_banner_2, R.drawable.main_banner_3};

    Viewpager2Adapter viewpager2Adapter;

    //코로나 현황판
    TextView covid_time, covid_dayCount, covid_sumCount, covid_dayDeath,covid_sumDeath;

    static String key = "sprTWrIlSrb7BWTxTvDTN%2Bi42nZGHioaXh31AageXSjEZVzttt8QTNKBRnjLYgvLSFVn66xOPQ2dkVwFjZu%2Bbw%3D%3D";

    String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=";
    String url2 = "&pageNo=1&numOfRows=10&startCreateDt=";
    String url3 = "&endCreateDt=";

    URL covid_url;

    int login_value;


    //감염자 리스트
    ArrayList<Integer> decideCntLsit = new ArrayList<>();
    //사망자 리스트
    ArrayList<Integer> deathCntList = new ArrayList<>();

    int startDt, endDt, startDeathDt, endDeathDt;
    String nowDate, beforeDate, toDate;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);

        firstmain = FirstMainActivity.this;

        bt_button1 = findViewById(R.id.bt_first_main_button_1);
        bt_button2 = findViewById(R.id.bt_first_main_button_2);
        bt_button3 = findViewById(R.id.bt_first_main_button_3);
        bt_mypage = findViewById(R.id.imgv_main_mypage);

        tv_service = findViewById(R.id.tv_service);
        tv_data = findViewById(R.id.tv_data);
        covid_time = findViewById(R.id.covid_time);
        covid_dayCount = findViewById(R.id.covid_dayCount);
        covid_sumCount = findViewById(R.id.covid_sumConut);
        covid_sumDeath = findViewById(R.id.covid_sumDeath);
        covid_dayDeath = findViewById(R.id.covid_dayDeath);

        drawerLayout = findViewById(R.id.main_drawLayout);

        navigationView = findViewById(R.id.nav_view);

        viewPager = findViewById(R.id.viewpager_first_main);

        viewpager2Adapter = new Viewpager2Adapter(image);

        viewPager.setAdapter(viewpager2Adapter);

        asyncTask = new AsyncTask();
        asyncTask.execute();

        sharedPreferences = getSharedPreferences("LoginUserinfo",MODE_PRIVATE);
        auto_login_info = sharedPreferences.getString("login", "");

        try {
            intent = getIntent();
            login_value = intent.getExtras().getInt("loginvalue");
        } catch (Exception e){
            login_value = 0;
        }



        if (auto_login_info.equals("")) {

            navigationView.inflateHeaderView(R.layout.main_navi_header);
            navigationView.inflateMenu(R.menu.main_navi_menu);
            navigationView1();
        } else{
            navigationView.inflateHeaderView(R.layout.main_navi_header2);
            navigationView.inflateMenu(R.menu.main_navi_menu2);
            navigationView2();
        }

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

                    case R.id.bt_first_main_button_3:
                        timer.cancel();
                        intent = new Intent(getApplicationContext(), PmReceiveActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.imgv_main_mypage:
                        drawerLayout.openDrawer(GravityCompat.END);
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
        bt_button3.setOnClickListener(cl);
        bt_mypage.setOnClickListener(cl);
        tv_service.setOnClickListener(cl);
        tv_data.setOnClickListener(cl);


        // 네비게이션 뷰(DrawLayout) 내 메뉴 클릭리스너
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.pmrecevie:

                        break;

                    case R.id.logout:
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), FirstMainActivity.class);
                        startActivity(intent);
                        finish();

                        break;
                    case R.id.account:

                        if (login_value == 0){

                            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent1);


                        } else if (login_value == 1){

                        }

                        break;
                }

                drawerLayout.closeDrawers();
                return false;
            }
        });


        // 베너 클릭 리스너
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


    //코로나 현황판 업데이트
    public class AsyncTask extends android.os.AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    today_date(today_count);
                }
            });

            return null;
        }
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
                            Log.d("decideCnt", xpp.getText());

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

            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            String covid_dC = decimalFormat.format(startDt-endDt);
            String covid_sc = decimalFormat.format(startDt);
            String covid_dd = decimalFormat.format(startDeathDt-endDeathDt);
            String covid_sd = decimalFormat.format(startDeathDt);

            covid_dayCount.setText(covid_dC);
            covid_sumCount.setText(covid_sc);
            covid_dayDeath.setText(covid_dd);
            covid_sumDeath.setText(covid_sd);

            if (startDt-endDt == 0){
                decideCntLsit.clear();
                deathCntList.clear();
                today_date(3);
            }

        } catch (Exception e){

            if (today_count < 2) {
                today_count++;
                today_date(today_count);
            }
            Log.d("확인", "리턴 "+ e);
        }


    }


    public void onBackpressde(){
        onBackPressed();
    }


    //뒤로가기 인식
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

    public void navigationView1(){

        View view = navigationView.getHeaderView(0);

        LinearLayout ll_navi_header = view.findViewById(R.id.ll_navi_login);

        ll_navi_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    public void navigationView2(){

        View view = navigationView.getHeaderView(0);

        TextView tv_user_nm = view.findViewById(R.id.tv_user_nm);

        auto_login_nm = sharedPreferences.getString("user_nm", "");
        tv_user_nm.setText(auto_login_nm+"님");

    }


    @Override
    protected void onResume() {
        super.onResume();
        bennertimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        asyncTask.cancel(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void bennertimer(){
        handler = new Handler();

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
