package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PmReceiveActivity extends AppCompatActivity {

    TextView tv_totalCount,tv_totalPrice,tv_dateReplace;
    ImageView back_icon;
    RecyclerView rv_pmReceive;

    View.OnClickListener cl;
    Intent intent;

    //약국 이름 리스트
    ArrayList<String> list1 = new ArrayList<>();

    //결제 날짜
    ArrayList<String> list2 = new ArrayList<>();

    //결제 금액
    ArrayList<Integer> list3 = new ArrayList<>();

    PmReceiveAdapter pmReceiveAdapter;

    //테스트 데이터
    String[] name = {"이블루약국", "코끼리약국", "주오약국"};
    String[] date = {"2022.02.21 10.38","2022.02.22 10.42", "2022.02.22 15.22"};
    int[] price = {8000,400,5500};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_receive);

        back_icon = findViewById(R.id.bt_back_icon);
        tv_totalCount = findViewById(R.id.tv_totalCount);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);
        tv_dateReplace = findViewById(R.id.tv_dateReplace);

        rv_pmReceive = findViewById(R.id.rv_pmReceive);
        rv_pmReceive.setLayoutManager(new LinearLayoutManager(this));

        pmReceiveAdapter = new PmReceiveAdapter(list1, list2, list3);

        getData();

        rv_pmReceive.setAdapter(pmReceiveAdapter);

        pmReceiveAdapter.notifyDataSetChanged();



        pmReceiveAdapter.setOnClickListener(new PmReceiveAdapter.OnListItemSelectedInterface() {
            @Override
            public void onItemSelected(View v, int position) {
                Intent intent = new Intent(PmReceiveActivity.this, PmReceiveDetailActivity.class);
                startActivity(intent);
            }
        });

        //뒤로가기
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //기본 데이터 가져오기
    public void getData(){

        for (int i =0 ; i < name.length ; i++){
            list1.add(name[i]);
            list2.add(date[i]);
            list3.add(price[i]);
            Log.d("데이터확인", list1.get(i));
        }

        setTotalData();
    }

    // 총합 부분 데이터 합산
    public void setTotalData(){
        int totalprice =0;
        tv_totalCount.setText(name.length+"건");

        for (int i =0 ; i < list3.size() ; i++){
            totalprice = totalprice + list3.get(i);
        }
        String total = NumberFormat.getInstance(Locale.getDefault()).format(totalprice);
        tv_totalPrice.setText(total+"원");

    }
}