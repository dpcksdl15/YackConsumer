package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MembershipFinshActivity extends AppCompatActivity {

    Button bt_gomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_finsh);

        bt_gomain = findViewById(R.id.bt_gomain);

        bt_gomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}