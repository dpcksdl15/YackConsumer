package com.example.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class FirstMainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    ImageView bt_button1, bt_button2;

    TextView tv_service, tv_data;

    View.OnClickListener cl;

    int viewcount = 0;

    Timer timer;


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
                        intent = new Intent(getApplicationContext(), MdPriceActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.tv_service:
                        intent = new Intent(getApplicationContext(), ProvisionActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.tv_data:
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
