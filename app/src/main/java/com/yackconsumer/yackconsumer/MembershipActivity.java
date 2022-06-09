package com.yackconsumer.yackconsumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MembershipActivity extends AppCompatActivity {

    // vlaue값
    // 0:가입, 1:id확인, 2:로그인, 3:id전자영수증, 4:전자영수증자세히보기
    // 자동 회원 번호은 13자리 EX) 고유값(7) + 가입시간(초)(11) + 가입날짜(220325) + 핸드폰번호 뒷4자리(3408)
    // 22 년 03월 25일 11시 28분 11초

    Button bt_memberinfo_enter, bt_idCheck;
    TextView select_do, select_si, tv_provision, tv_provision2;
    EditText et_id, et_pw, et_repw, et_name, et_y, et_m, et_d, et_phnf, et_phnm, et_phnl;
    CheckBox ckb_all, ckb1, ckb2;
    String value = "0", today, day_yymmdd, day_ss, value_uqnum ,value_adrr ,value_do, value_si, value_id, value_pw, value_repw, value_name, value_Bday, value_phn;
    static int check[] = {0,0,0,0,0,0,0};

    //결과값 리턴
    String result, rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_repw = findViewById(R.id.et_repw);
        et_name = findViewById(R.id.et_name);
        et_y = findViewById(R.id.et_y);
        et_m = findViewById(R.id.et_m);
        et_d = findViewById(R.id.et_d);
        et_phnf = findViewById(R.id.et_phnf);
        et_phnm = findViewById(R.id.et_phnm);
        et_phnl = findViewById(R.id.et_phnl);
        select_do = findViewById(R.id.select_do);
        select_si = findViewById(R.id.select_si);
        bt_memberinfo_enter = findViewById(R.id.bt_memberinfo_enter);
        bt_idCheck = findViewById(R.id.bt_idCheck);
        ckb_all = findViewById(R.id.ckb_all);
        ckb1 = findViewById(R.id.ckb1);
        ckb2 = findViewById(R.id.ckb2);
        tv_provision = findViewById(R.id.tv_provision);
        tv_provision2 = findViewById(R.id.tv_provision2);


        //가입버튼
        bt_memberinfo_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //id 중복확인 체크
                if (check[0] == 1) {
                    value_id = et_id.getText().toString();
                } else if (check[0] == 0){
                    Toast.makeText(getApplicationContext(),"아이디 중복확인을 해주세요.",Toast.LENGTH_SHORT).show();
                }

                //pw 확인 체크
                if (et_pw.getText().toString().length() >= 8){
                    value_pw = et_pw.getText().toString();
                    value_repw = et_repw.getText().toString();
                    if (value_pw.equals(value_repw)){
                        check[1] = 1;
                    } else {
                        Toast.makeText(getApplicationContext(),"비밀번호가 서로 다릅니다.",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"비밀번호가 너무 짧습니다.",Toast.LENGTH_SHORT).show();
                }

                //이름 확인
                if (et_name.getText().toString().length() > 0){
                    value_name = et_name.getText().toString();
                    check[2]=1;
                } else {
                    Toast.makeText(getApplicationContext(),"이름을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }

                //생년월일
                if (et_y.getText().toString().equals("") == false && et_m.getText().toString().equals("") == false && et_d.getText().toString().equals("") == false) {
                    if (Integer.parseInt(et_y.getText().toString()) > 1900 && Integer.parseInt(et_m.getText().toString()) > 0 && Integer.parseInt(et_d.getText().toString()) <= 12 && Integer.parseInt(et_d.getText().toString()) > 0 && Integer.parseInt(et_d.getText().toString()) <= 31) {
                        if (et_m.getText().toString().length() > 2){
                            value_Bday = et_y.getText().toString() + "-" + "0" + et_m.getText().toString() + "-" + et_d.getText().toString();
                        } else if (et_d.getText().toString().length() > 2){
                            value_Bday = et_y.getText().toString() + "-" + et_m.getText().toString() + "-" + "0" + et_d.getText().toString();
                        }
                        value_Bday = et_y.getText().toString() + "-" + et_m.getText().toString() + "-" + et_d.getText().toString();
                        check[3] = 1;
                    }else {
                        Toast.makeText(getApplicationContext(),"생년월일을 정확히 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"생년월일을 빠짐없이 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }

                //전화번호
                if (et_phnf.getText().toString().equals("") == false && et_phnm.getText().toString().equals("") == false && et_phnl.getText().toString().equals("") == false) {
                    if (Integer.parseInt(et_phnf.getText().toString()) < 20 && et_phnf.getText().toString().length() == 3 && Integer.parseInt(et_phnm.getText().toString()) <= 9999 && et_phnm.getText().toString().length() >=3 && Integer.parseInt(et_phnl.getText().toString()) <= 9999 && et_phnl.getText().toString().length() >= 4) {
                        value_phn = et_phnf.getText().toString() + et_phnm.getText().toString() + et_phnl.getText().toString();
                        check[4] = 1;
                    } else {
                        Toast.makeText(getApplicationContext(),"전화번호를 올바르게 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"전화번호를 빠짐없이 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }

                if (value_do != ""){
                    check[5] =1;
                    value_adrr = value_do;
                }else {
                    Toast.makeText(getApplicationContext(),"지역을 선택하여 주세요.",Toast.LENGTH_SHORT).show();
                }

                if (value_si != ""){
                    check[6] =1;
                    value_adrr = value_adrr + " " + value_si;
                }else {
                    Toast.makeText(getApplicationContext(),"지역을 선택하여 주세요.",Toast.LENGTH_SHORT).show();
                }


                if (check[0] != 0 && check[1] != 0 && check[2] != 0 && check[3] != 0 && check[4] != 0 && check[5] != 0 && check[6] != 0) {

                    value = "0";
                    try {

                        //오늘 날짜 구하기
                        SimpleDateFormat dateformat = new SimpleDateFormat("yyMMddss");
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DATE,0);
                        today  = dateformat.format(calendar.getTime());
                        day_yymmdd = today.substring(0,6);
                        day_ss = today.substring(6);

                        value_uqnum = "7" + day_ss + day_yymmdd + et_phnl.getText().toString();


                        ServerRegisterActivity task = new ServerRegisterActivity();
                        result = task.execute(value, value_id, value_pw, value_name, value_phn, value_adrr, value_Bday, value_uqnum).get();
                        rs = result.trim();
                        if (rs.equals("1")){

                            Intent intent = new Intent(getApplication(), MembershipFinshActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(getApplicationContext(),"오류가 발생했습니다 잠시후에 다시 시도해주세요.",Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e){
                        Log.d("MemberInfoDBUplode", ".....ERROR.....!");
                    }

                } else {

                    for (int i =1; i< check.length ; i++){
                        check[i] =0;
                    }
                }
            }
        });

        //ID 중복확인
        bt_idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String id = et_id.getText().toString();
                    value = "1";

                    if (id.length() >= 6) {
                        ServerRegisterActivity task = new ServerRegisterActivity();
                        result = task.execute(value, id).get();
                        rs = result.trim();
                        if (rs.equals("1")){
                            check[0] =1;
                            Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"중복되는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            check[0] =0;
                        }
                    } else if (id.length() < 6){
                        Toast.makeText(getApplicationContext(),"아이디가 너무 짧습니다. 6자 이상",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }
        });

        // 시도 클릭시 설정
        select_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForContextMenu(select_do);
                openContextMenu(select_do);
            }
        });

        select_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value_do == null){
                    Toast.makeText(getApplicationContext(),"도 지역을 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    registerForContextMenu(select_si);
                    openContextMenu(select_si);
                }
            }
        });

        // CheckBox 설정
        ckb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ckb_all.isChecked() == true) {
                    ckb1.setChecked(true);
                    ckb2.setChecked(true);
                } else if (ckb_all.isChecked() != true){
                    ckb1.setChecked(false);
                    ckb2.setChecked(false);
                }
            }
        });
        ckb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ckb1.isChecked() && ckb2.isChecked()){
                    ckb_all.setChecked(true);
                } else {
                    ckb_all.setChecked(false);
                }
            }
        });
        ckb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ckb1.isChecked() && ckb2.isChecked()){
                    ckb_all.setChecked(true);
                }else {
                    ckb_all.setChecked(false);
                }
            }
        });

        //약관 보기
        tv_provision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProvisionActivity.class);
                startActivity(intent);
            }
        });
        tv_provision2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Provision2Activity.class);
                startActivity(intent);
            }
        });
    }

// 도시 ContextMenu설정
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();

        switch (v.getId()){
           case R.id.select_do :
            inflater.inflate(R.menu.do_context_menu, menu);
            break;

            case R.id.select_si:
                Log.d("value_do", value_do);
                if (value_do.equals("서울특별시")){
                    inflater.inflate(R.menu.seoul_gu_context_menu, menu);
                } else if (value_do.equals("부산광역시")){
                    inflater.inflate(R.menu.busan_context_menu, menu);
                } else if (value_do.equals("대구광역시")){
                    inflater.inflate(R.menu.deagu_context_menu, menu);
                } else if (value_do.equals("인천광역시")){
                    inflater.inflate(R.menu.incheon_context_menu, menu);
                } else if (value_do.equals("광주광역시")){
                    inflater.inflate(R.menu.gwangju_context_menu, menu);
                } else if (value_do.equals("대전광역시")){
                    inflater.inflate(R.menu.deajen_context_menu, menu);
                } else if (value_do.equals("울산광역시")){
                    inflater.inflate(R.menu.ulsan_context_menu, menu);
                } else if (value_do.equals("세종시")){
                    inflater.inflate(R.menu.sejoeng_context_menu, menu);
                } else if (value_do.equals("경기도")){
                    inflater.inflate(R.menu.gyeonggi_gu_context_menu, menu);
                } else if (value_do.equals("강원도")){
                    inflater.inflate(R.menu.gangwon_context_menu, menu);
                } else if (value_do.equals("충청북도")){
                    inflater.inflate(R.menu.chungbuk_context_menu, menu);
                } else if (value_do.equals("충청남도")){
                    inflater.inflate(R.menu.chungnam_context_menu, menu);
                } else if (value_do.equals("전라북도")){
                    inflater.inflate(R.menu.jeonbuk_context_menu, menu);
                } else if (value_do.equals("전라남도")){
                    inflater.inflate(R.menu.jeonnam_context_menu, menu);
                } else if (value_do.equals("경상북도")){
                    inflater.inflate(R.menu.gyeongbuk_context_menu, menu);
                } else if (value_do.equals("경상남도")){
                    inflater.inflate(R.menu.gyeongnam_context_menu, menu);
                } else if (value_do.equals("제주도")){
                    inflater.inflate(R.menu.jeju_context_menu, menu);
                }

        }

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.do1 :
            case R.id.do3 :
            case R.id.do2 :
            case R.id.do4 :
            case R.id.do5 :
            case R.id.do6 :
            case R.id.do7 :
            case R.id.do8 :
            case R.id.do9 :
            case R.id.do10 :
            case R.id.do11 :
            case R.id.do12 :
            case R.id.do13 :
            case R.id.do14 :
            case R.id.do15 :
            case R.id.do16 :
            case R.id.do17 :
                select_do.setText(item.getTitle());
                value_do = item.getTitle().toString();
                return true;
            default:
                select_si.setText(item.getTitle());
                value_si = item.getTitle().toString();
                return true;
        }
    }
}