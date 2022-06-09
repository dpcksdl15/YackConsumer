package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MdPriceActivity extends AppCompatActivity {

    static RecyclerView recyclerView;

    private PriceAdapter priceAdapter;

    private RecyclerView.LayoutManager mlayoutManager;

    Button bt_search;

    Button bt_sum;

    EditText et_md_search_name;

    TextView rerset;

    Menu menu;

    SQLiteDatabase sqLiteDatabase;

    DBHelper dbHelper;

    static TextView tv_count;
    static TextView tv_total;
    static TextView tv_all_total;

    static int before_count = 0;
    int count = 0;
    int on_off = 0;

    int remove_conut = 0;

    //name
    private ArrayList<String> list = new ArrayList<>();
    //code
    private ArrayList<String> list2 = new ArrayList<>();
    //stdday
    private ArrayList<String> list3 = new ArrayList<>();
    //price
    private ArrayList<String> list4 = new ArrayList<>();
    //day count
    private ArrayList<String> list5 = new ArrayList<>();
    //1day time count
    private ArrayList<String> list6 = new ArrayList<>();
    //1time unit count
    private ArrayList<String> list7 = new ArrayList<>();
    //Insurance or not Insurance
    private ArrayList<Integer> list8 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_price);

        bt_search = findViewById(R.id.bt_search);
        et_md_search_name = findViewById(R.id.et_md_search_name);
        tv_total = findViewById(R.id.tv_total);
        tv_all_total = findViewById(R.id.tv_alltotal);
        rerset = findViewById(R.id.reset);
        bt_sum = findViewById(R.id.bt_sum);

        tv_count = findViewById(R.id.tv_count);

        recyclerView = findViewById(R.id.recyclerview2);

        dbHelper = new DBHelper(getApplicationContext(), "MdDB", null, 1);

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_md_search_name.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "검색어를 입력해 주세요", Toast.LENGTH_LONG).show();

                } else {

                    Intent intent = new Intent(getApplicationContext(), MdPriceResultActivity.class);

                    intent.putExtra("md_name", et_md_search_name.getText().toString());

                    et_md_search_name.setText("");

                    startActivity(intent);
                }
            }
        });

        bt_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int total = 0;
                int total2 = 0;

                sqLiteDatabase = dbHelper.getReadableDatabase();


                String sql, sql2;

                sql = "SELECT * FROM searchdata;";
                Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

                while (cursor.moveToNext()) {
                    if (cursor.getString(5).equals("0") || cursor.getString(6).equals("0") || cursor.getString(7).equals("0")) {
                        Toast.makeText(getApplicationContext(), "복용관련 설정을 완료해주세요", Toast.LENGTH_SHORT).show();
                        break;
                    } else if (cursor.getString(5).equals("0") == false && cursor.getString(6).equals("0") == false && cursor.getString(7).equals("0") == false && cursor.getInt(8) == 1) {

                        total = total + (Integer.parseInt(cursor.getString(3)) * Integer.parseInt(cursor.getString(5)) * Integer.parseInt(cursor.getString(6)) * Integer.parseInt(cursor.getString(7)));

                    } else if (cursor.getString(5).equals("0") == false && cursor.getString(6).equals("0") == false && cursor.getString(7).equals("0") == false && cursor.getInt(8) == 2) {
                        total2 = total2 + (Integer.parseInt(cursor.getString(3)) * Integer.parseInt(cursor.getString(5)) * Integer.parseInt(cursor.getString(6)) * Integer.parseInt(cursor.getString(7)));
                    }
                }

                sql2 = "SELECT * FROM searchdata order by count_day desc limit 1";

                Cursor cursor1 = sqLiteDatabase.rawQuery(sql2, null);

                while (cursor1.moveToNext()) {

                    if (Integer.parseInt(cursor1.getString(5)) == 1) {

                        total_sum(total, total2, 5480);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 2) {

                        total_sum(total, total2, 5690);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 3) {

                        total_sum(total, total2, 6260);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 4) {

                        total_sum(total, total2, 6950);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 5) {

                        total_sum(total, total2, 6950);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 6) {

                        total_sum(total, total2, 7260);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 7) {

                        total_sum(total, total2, 7720);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 8) {

                        total_sum(total, total2, 7920);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 9) {

                        total_sum(total, total2, 8150);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 10) {

                        total_sum(total, total2, 8520);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 11) {

                        total_sum(total, total2, 8790);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 12) {

                        total_sum(total, total2, 9060);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 13) {

                        total_sum(total, total2, 9330);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 14) {

                        total_sum(total, total2, 10210);

                    } else if (Integer.parseInt(cursor1.getString(5)) == 15) {

                        total_sum(total, total2, 10340);

                    } else if (15 < Integer.parseInt(cursor1.getString(5)) && Integer.parseInt(cursor1.getString(5)) < 21) {

                        total_sum(total, total2, 11080);

                    } else if (20 < Integer.parseInt(cursor1.getString(5)) && Integer.parseInt(cursor1.getString(5)) < 26) {

                        total_sum(total, total2, 11440);

                    } else if (25 < Integer.parseInt(cursor1.getString(5)) && Integer.parseInt(cursor1.getString(5)) < 31) {

                        total_sum(total, total2, 128700);

                    } else if (30 < Integer.parseInt(cursor1.getString(5)) && Integer.parseInt(cursor1.getString(5)) < 41) {

                        total_sum(total, total2, 13940);

                    } else if (40 < Integer.parseInt(cursor1.getString(5)) && Integer.parseInt(cursor1.getString(5)) < 51) {

                        total_sum(total, total2, 14780);

                    } else if (50 < Integer.parseInt(cursor1.getString(5)) && Integer.parseInt(cursor1.getString(5)) < 61) {

                        total_sum(total, total2, 16990);

                    } else if (60 < Integer.parseInt(cursor1.getString(5)) && Integer.parseInt(cursor1.getString(5)) < 71) {

                        total_sum(total, total2, 17450);

                    } else if (70 < Integer.parseInt(cursor1.getString(5)) && Integer.parseInt(cursor1.getString(5)) < 81) {

                        total_sum(total, total2, 17860);

                    } else if (80 < Integer.parseInt(cursor1.getString(5)) && Integer.parseInt(cursor1.getString(5)) < 91) {

                        total_sum(total, total2, 18260);

                    } else if (90 < Integer.parseInt(cursor1.getString(5))) {

                        total_sum(total, total2, 180730);

                    }

                }

            }

            public void total_sum(int to, int to2, int jojebi) {
                int all_total = 0;
                int total = to;
                int total2 = to2;
                int rest = 0;
                int rest1 = 0;
                int joje = jojebi;

                if (total != 0) {
                    total = total + joje;

                    all_total = all_total + total;

                    rest = total % 10;

                    total = total - rest;

                    total = (int) (total * 0.3);

                    rest1 = total % 100;

                    total = total - rest1 + total2;

                    all_total = all_total + total2;
                } else if (total == 0) {
                    total = total + total2 + joje;
                    all_total = all_total + total;
                }

                tv_total.setText(String.valueOf(total));
                tv_all_total.setText(String.valueOf(all_total));
            }
        });

        rerset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    sqLiteDatabase = dbHelper.getWritableDatabase();

                    String resetsql = "DELETE FROM searchdata;";

                    sqLiteDatabase.execSQL(resetsql);

                    list.clear();
                    list2.clear();
                    list3.clear();
                    list4.clear();
                    list5.clear();
                    list6.clear();
                    list7.clear();
                    list8.clear();

                    count = 0;
                    priceAdapter = new PriceAdapter(list, list2, list3, list4, list5, list6, list7, list8);

                    recyclerView.setAdapter(priceAdapter);

                    priceAdapter.notifyDataSetChanged();

                    tv_count.setText("총 " + list.size() +"개");

                } catch (Exception e) {

                }


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("다시 실행", "싫행");
        IntentFilter filter = new IntentFilter();
        DataRefresh();
    }

    public BroadcastReceiver brod(){
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    on_off = 1;
                }
            }
        };
        return br;
    }

    public void DataRefresh(){

        Log.d("다시 실행","DB받기");


        try {

            sqLiteDatabase = dbHelper.getReadableDatabase();

            String sql, sql2;

            sql = "SELECT * FROM searchdata;";
            Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

            if(cursor.getCount() != 0){
                recyclerView.setBackgroundResource(R.drawable.background_mdprice_recyclerview);
            } else if (cursor.getCount() == 0){
                recyclerView.setBackgroundResource(R.drawable.background_mdprice_recyclerview_empty);
            }

            tv_count.setText("총 " + cursor.getCount() +"개");

            Log.d("다시 실행2 c", String.valueOf(cursor.getCount()));
            Log.d("다시 실행2 b", String.valueOf(before_count));
            Log.d("다시 실행2", String.valueOf(list));

            if (cursor.getCount() > 0 && count == 0){

                Log.d("DataRefresh1","실행");

                before_count = cursor.getCount();

                while (cursor.moveToNext()) {
                    list.add(cursor.getString(0));
                    list2.add(cursor.getString(1));
                    list3.add(cursor.getString(2));
                    list4.add(cursor.getString(3));
                    list5.add(cursor.getString(5));
                    list6.add(cursor.getString(6));
                    list7.add(cursor.getString(7));
                    list8.add(cursor.getInt(8));
                }
                count = 1;

                mlayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mlayoutManager);

                priceAdapter = new PriceAdapter(list, list2, list3, list4, list5, list6, list7, list8);

                Log.d("DataRefresh1",String.valueOf(list.get(0)));
                Log.d("DataRefresh1",String.valueOf(before_count));

                recyclerView.setAdapter(priceAdapter);
                priceAdapter.notifyDataSetChanged();

            } else if(cursor.getCount() == before_count){

                recyclerView.removeAllViewsInLayout();

                mlayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mlayoutManager);

                priceAdapter = new PriceAdapter(list, list2, list3, list4, list5, list6, list7, list8);

                Log.d("DataRefresh2", String.valueOf(list.get(0)));
                Log.d("DataRefresh2", String.valueOf(before_count));

                recyclerView.setAdapter(priceAdapter);
                priceAdapter.notifyDataSetChanged();

            } else if (cursor.getCount() > before_count && count == 1 || cursor.getCount() < before_count && count == 1){

                Log.d("DataRefresh3","실행");

                sql2 = "SELECT * FROM searchdata ORDER BY save_time DESC LIMIT 1;";
                Cursor cursor2 = sqLiteDatabase.rawQuery(sql2,null);

                before_count = cursor.getCount();

                while (cursor2.moveToNext()) {
                    list.add(cursor2.getString(0));
                    list2.add(cursor2.getString(1));
                    list3.add(cursor2.getString(2));
                    list4.add(cursor2.getString(3));
                    list5.add(cursor2.getString(5));
                    list6.add(cursor2.getString(6));
                    list7.add(cursor2.getString(7));
                    list8.add(cursor2.getInt(8));
                }

                mlayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mlayoutManager);

                priceAdapter = new PriceAdapter(list, list2, list3, list4, list5, list6, list7, list8);

                Log.d("DataRefresh3",String.valueOf(cursor2.getString(0)));
                Log.d("DataRefresh3",String.valueOf(before_count));

                recyclerView.setAdapter(priceAdapter);
                priceAdapter.notifyItemChanged(before_count);

                remove_conut = 0;

                priceAdapter.notifyDataSetChanged();
            }

            Log.d("다시 실행", String.valueOf(before_count));
            Log.d("다시 실행", String.valueOf(cursor.getCount()));
            Log.d("다시 실행", String.valueOf(count));
            Log.d("다시 실행", String.valueOf(list));
            Log.d("다시 실행","싫행 종료");


        } catch (Exception e){

        }

    }

    public void remove(int a){
        before_count = a;
        Log.d("다시 실행", String.valueOf(before_count));

        tv_count.setText("총 " + before_count +"개");

        if(before_count != 0){
            recyclerView.setBackgroundResource(R.drawable.background_mdprice_recyclerview);
        } else if (before_count == 0){
            recyclerView.setBackgroundResource(R.drawable.empty_background);
        }

        tv_total.setText(String.valueOf(0));
        tv_all_total.setText(String.valueOf(0));
    }
}