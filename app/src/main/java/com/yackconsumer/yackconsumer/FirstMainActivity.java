package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class FirstMainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    ImageView bt_button1, bt_button2;

    TextView tv_service, tv_data;

    View.OnClickListener cl;

    int viewcount = 0;

    Timer timer;

    private long backbuttonpress = 0;

    Intent intent;



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

        viewPager = findViewById(R.id.viewpager_first_main);

        viewpager2Adapter = new Viewpager2Adapter(image);

        viewPager.setAdapter(viewpager2Adapter);

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

    public void onBackpressde(){
        onBackPressed();
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
        bennertimer();
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
                        handler.post(update);
                        Log.d("실행","스케줄러");
                    }
                }, 500, 4000);
    }

}
