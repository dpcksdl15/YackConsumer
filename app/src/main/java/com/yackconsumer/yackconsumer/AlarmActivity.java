package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AlarmActivity extends AppCompatActivity {

    ImageView bt_back_icon, bt_plus_icon;
    RecyclerView rc_alarmView;
    View.OnClickListener cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        bt_back_icon = findViewById(R.id.bt_back_icon);
        bt_plus_icon = findViewById(R.id.bt_plus_icon);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){

                    case R.id.bt_back_icon:
                        finish();
                        break;

                    case R.id.bt_plus_icon:
                        Intent intent = new Intent(getApplicationContext(),AlarmAddActivity.class);
                        startActivity(intent);
                        break;

                }

            }
        };
        bt_back_icon.setOnClickListener(cl);
        bt_plus_icon.setOnClickListener(cl);


    }
}