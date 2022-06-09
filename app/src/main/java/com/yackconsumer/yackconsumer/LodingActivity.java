package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class LodingActivity extends AppCompatActivity {

    String value_login;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

        sharedPreferences = getSharedPreferences("LoginUserinfo",MODE_PRIVATE);

        value_login = sharedPreferences.getString("login", "");

        if (value_login.equals("0")) {

            Log.d("로그인정보삭제", "삭제");
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.clear();

            editor.commit();

        }

        startLoding();

    }
    public void startLoding(){

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    //자동로그인 유무 불러오기


                                        Intent intent = new Intent(getApplicationContext(), FirstMainActivity.class);
                                        startActivity(intent);
                                        finish();

                                }
                            }
                ,3000);



    }
}