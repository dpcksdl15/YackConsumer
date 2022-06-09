package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class PmReceiveActivity extends AppCompatActivity {

    TextView tv_totalCount,tv_totalPrice,tv_dateReplace, tv_startDate1, tv_endDate1, tv_startDate2, tv_endDate2, tv_dateReplace2;
    ImageView back_icon;
    RecyclerView rv_pmReceive;
    LinearLayout ll_dateReplace;

    //날짜 선택
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
    Calendar mcalender = Calendar.getInstance();
    Calendar mcalender2 = Calendar.getInstance();


    String startday, endday;
    int startday_value, endday_value;

    View.OnClickListener cl;
    Intent intent;

    //약국 이름 리스트
    ArrayList<String> list1 = new ArrayList<>();

    //결제 날짜
    ArrayList<String> list2 = new ArrayList<>();

    //결제 금액
    ArrayList<Integer> list3 = new ArrayList<>();

    //판매번호
    ArrayList<Integer> list4 = new ArrayList<>();

    PmReceiveAdapter pmReceiveAdapter;

    String today;
    int state =0;

    //결과값 리턴
    String result, rs;
    String value = "3";
    String value_id = null, value_hp = null, value_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_receive);

        back_icon = findViewById(R.id.bt_back_icon);
        tv_totalCount = findViewById(R.id.tv_totalCount);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);
        tv_dateReplace = findViewById(R.id.tv_dateReplace);
        tv_startDate1 = findViewById(R.id.tv_startDate1);
        tv_startDate2 = findViewById(R.id.tv_startDate2);
        tv_endDate1 = findViewById(R.id.tv_endDate1);
        tv_endDate2 = findViewById(R.id.tv_endDate2);
        tv_dateReplace2 = findViewById(R.id.tv_dateReplace2);

        rv_pmReceive = findViewById(R.id.rv_pmReceive);
        rv_pmReceive.setLayoutManager(new LinearLayoutManager(this));

        ll_dateReplace = findViewById(R.id.ll_dateReplace);

        pmReceiveAdapter = new PmReceiveAdapter(list1, list2, list3, list4);

        //로그인 유무 불러오기
        SharedPreferences sharedPreferences = getSharedPreferences("LoginUserinfo",MODE_PRIVATE);
        value_login = sharedPreferences.getString("login", "");

        if (value_login.equals("") != true){
            value_hp = sharedPreferences.getString("hp", "");
            value_id = sharedPreferences.getString("id", "");
        }

        //오늘 날짜 구하기
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,0);
        today  = dateformat.format(calendar.getTime());

        //오늘 날짜 표시
        tv_startDate1.setText(today);
        tv_endDate1.setText(today);
        tv_startDate2.setText(today);
        tv_endDate2.setText(today);
        startday = today;
        endday = today;
        startday_value = Integer.parseInt(dateformat2.format(calendar.getTime()));
        endday_value = Integer.parseInt(dateformat2.format(calendar.getTime()));

        //결제목록 불러오기
        datasetting(today, today);

        //데이터 setting
        rv_pmReceive.setAdapter(pmReceiveAdapter);
        pmReceiveAdapter.notifyDataSetChanged();

        pmReceiveAdapter.setOnClickListener(new PmReceiveAdapter.OnListItemSelectedInterface() {
            @Override
            public void onItemSelected(View v, int position) {
                Intent intent = new Intent(PmReceiveActivity.this, PmReceiveDetailActivity.class);
                intent.putExtra("sale_no", list4.get(position));
                Log.d("디테일번호", String.valueOf(list4.get(position)));
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

        //날짜 변경 클릭리스너
        tv_dateReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state != 0) {
                    ll_dateReplace.setVisibility(View.GONE);
                    state = 0;
                } else if (state == 0){
                    ll_dateReplace.setVisibility(View.VISIBLE);
                    state = 1;
                    datasetting(startday, endday);
                }
            }
        });

        //날짜 변경 클릭 리스너
        tv_startDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daySetting("s");

            }
        });

        tv_endDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daySetting("e");
            }
        });

        //날짜 변경 확인 리스너
        tv_dateReplace2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startday_value < endday_value) {

                    datasetting(String.valueOf(startday), String.valueOf(endday));
                    tv_startDate1.setText(startday);
                    tv_endDate1.setText(endday);
                    ll_dateReplace.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getApplicationContext(), "날짜 설정을 다시 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void datasetting(String stday, String edday){
        //결제목록 불러오기
        ServerRegisterActivity task = new ServerRegisterActivity();
        try {
            Log.d("전자영수증 확인", "전화번호 " + value_hp);
            result = task.execute(value, value_id, value_hp, stday, edday).get();
            Log.d("전자영수증 리턴확인", "데이터 " + result);

            receivelist();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //리스트 생성
    public void receivelist(){

        try {

            list1.clear();
            list2.clear();
            list3.clear();
            list4.clear();

            JSONArray jsonArray = new JSONObject(result).getJSONArray(value_hp);

            if (jsonArray != null){

                Log.d("jsonArray", String.valueOf(jsonArray.length()));

                for (int i=0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("jsonArray", jsonObject.toString());

                    if (jsonObject.getString("CANCEL_GB").equals("1") != true) {
                        if (list2.size() != 0) {
                            for (int j = 0; j < list2.size(); j++) {

                                if (list1.get(j).equals(jsonObject.getString("PHARM_NM")) && list2.get(j).equals(jsonObject.getString("SALE_YMDHMS"))) {

                                    list3.set(j, list3.get(j) + Integer.parseInt(jsonObject.getString("TOT_PRICE")));
                                    Log.d("jsonArray", "둘다같을때");
                                    break;

                                } else if (j == list2.size()-1){
                                    list1.add(jsonObject.getString("PHARM_NM"));
                                    list2.add(jsonObject.getString("SALE_YMDHMS"));
                                    list3.add(Integer.parseInt(jsonObject.getString("TOT_PRICE")));
                                    list4.add(Integer.parseInt(jsonObject.getString("SALE_NO")));
                                    Log.d("jsonArray", "둘다같지않을때");
                                    break;
                                }
                            }
                        } else if (list2.size() == 0) {

                            list1.add(jsonObject.getString("PHARM_NM"));
                            list2.add(jsonObject.getString("SALE_YMDHMS"));
                            list3.add(Integer.parseInt(jsonObject.getString("TOT_PRICE")));
                            list4.add(Integer.parseInt(jsonObject.getString("SALE_NO")));
                            Log.d("jsonArray", "첫행실행");
                        }


                    }
                }

            }

            setTotalData();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //데이터 setting
        rv_pmReceive.setAdapter(pmReceiveAdapter);
        pmReceiveAdapter.notifyDataSetChanged();
    }

    //날짜 세팅
    public  void daySetting(String calendar){

        switch (calendar.toString()){

            case "s" :
                DatePickerDialog.OnDateSetListener mdatePicker = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mcalender.set(Calendar.YEAR, year);
                        mcalender.set(Calendar.MONTH, month);
                        mcalender.set(Calendar.DAY_OF_MONTH, day);
                        startday = dateformat.format(mcalender.getTime());
                        tv_startDate2.setText(startday);
                        startday_value = Integer.parseInt(dateformat2.format(mcalender.getTime()));

                    }
                };

                new DatePickerDialog(PmReceiveActivity.this, mdatePicker,mcalender.get(Calendar.YEAR),mcalender.get(Calendar.MONTH), mcalender.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case "e" :
                DatePickerDialog.OnDateSetListener mdatePicker2 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mcalender2.set(Calendar.YEAR, year);
                        mcalender2.set(Calendar.MONTH, month);
                        mcalender2.set(Calendar.DAY_OF_MONTH, day);
                        endday = dateformat.format(mcalender2.getTime());
                        tv_endDate2.setText(endday);
                        endday_value = Integer.parseInt(dateformat2.format(mcalender2.getTime()));
                    }
                };
                new DatePickerDialog(PmReceiveActivity.this, mdatePicker2,mcalender2.get(Calendar.YEAR),mcalender2.get(Calendar.MONTH), mcalender2.get(Calendar.DAY_OF_MONTH)).show();
                break;

        }

    }

//     총합 부분 데이터 합산
    public void setTotalData(){
        int totalprice =0;
        tv_totalCount.setText( list1.size() + "건");

        for (int i =0 ; i < list3.size() ; i++){
            totalprice = totalprice + list3.get(i);
        }
        String total = NumberFormat.getInstance(Locale.getDefault()).format(totalprice);
        tv_totalPrice.setText(total+"원");

    }
}