package com.example.yackconsumer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MdSearchResultActivity extends AppCompatActivity {

    String key="%2FRj6PnwSChD5W2Md24QgSzON59%2FhVEEUaGNz6Wqatzinv3ynhtlm6Wj5ltMVE3pywr3aDojSz8%2BMNCTzeKbMzg%3D%3D";

    URL url;

    String queryUrl;

    Intent intent;

    ImageView imgv, bt_back_icon, img_icon6, img_icon7;
    TextView tv_search_name, tv_search_name2,tv_search_md_content1,tv_search_md_content2,tv_search_md_content3,tv_search_md_content4,tv_search_md_content5;
    TextView tv_search_md_content4_1,tv_search_md_content5_1;

    LinearLayout ll_content1, ll_content2;

    View.OnClickListener cl;

    String name,code, eat_plan, eat_buff, eat_warmming1, eat_debuff, md_leave_save;

    int content_check1 =0 , content_check2 =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_search_result);

        imgv = findViewById(R.id.imgv_md_img);
        bt_back_icon = findViewById(R.id.bt_back_icon);

        tv_search_name = findViewById(R.id.tv_search_name);
        tv_search_name2 = findViewById(R.id.tv_search_name2);
        tv_search_md_content1 = findViewById(R.id.tv_search_md_content1);
        tv_search_md_content2 = findViewById(R.id.tv_search_md_content2);
        tv_search_md_content3 = findViewById(R.id.tv_search_md_content3);
        tv_search_md_content4 = findViewById(R.id.tv_search_md_content4);
        tv_search_md_content5 = findViewById(R.id.tv_search_md_content5);

        tv_search_md_content4_1 = findViewById(R.id.tv_search_md_content4_1);
        tv_search_md_content5_1 = findViewById(R.id.tv_search_md_content5_1);

        ll_content1 = findViewById(R.id.ll_content1);
        ll_content2 = findViewById(R.id.ll_content2);

        img_icon6 = findViewById(R.id.img_icon6);
        img_icon7 = findViewById(R.id.img_icon7);

        tv_search_md_content1.setText("");
        tv_search_md_content2.setText("");
        tv_search_md_content3.setText("");


        cl = new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                switch (view.getId()){

                    case R.id.bt_back_icon:
                        finish();
                        break;

                    case R.id.tv_search_md_content4:
                        if (content_check1 == 0){
                            ll_content1.setVisibility(View.VISIBLE);
                            img_icon6.setImageResource(R.drawable.icon7);
                            content_check1 = 1;
                        } else if (content_check1 == 1){
                            ll_content1.setVisibility(View.GONE);
                            img_icon6.setImageResource(R.drawable.icon6);
                            content_check1 = 0;
                        }
                        break;

                    case R.id.tv_search_md_content5:
                        if (content_check2 == 0){
                            ll_content2.setVisibility(View.VISIBLE);
                            img_icon7.setImageResource(R.drawable.icon7);
                            content_check2 = 1;
                        } else if (content_check2 == 1){
                            ll_content2.setVisibility(View.GONE);
                            img_icon7.setImageResource(R.drawable.icon6);
                            content_check2 = 0;
                        }
                        break;

                }

            }
        };
        bt_back_icon.setOnClickListener(cl);
        tv_search_md_content4.setOnClickListener(cl);
        tv_search_md_content5.setOnClickListener(cl);


        intent = getIntent();

        code = intent.getExtras().getString("code");
        name = intent.getExtras().getString("name");
        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        new Thread(){
            @Override
            public void run() {

                search_md_content(code);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgv.setImageBitmap(image);
                        tv_search_name.setText(name);
                        tv_search_name2.setText(name);
                        tv_search_md_content1.setText(eat_plan);
                        tv_search_md_content2.setText(md_leave_save);
                        tv_search_md_content3.setText(eat_buff);
                        tv_search_md_content4_1.setText(eat_debuff);
                        tv_search_md_content5_1.setText(eat_warmming1);

                        if (eat_plan == null){
                            Toast.makeText(getApplicationContext(), "해당 의약품에 대한 정보가 없습니다.", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        }.start();

    }

    public void search_md_content(String code){

        //EditText에 작성된 Text얻어오기
        String str= code;

        try{
                queryUrl  = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?serviceKey=" + key + "&pageNo=1&numOfRows=3&type=xml&itemSeq=" + str;
                url= new URL(queryUrl);
                Log.d("확인", queryUrl);

            //url위치로 입력스트림 연결
            InputStream is= url.openStream();

            //xml파싱
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );

            String tag;

            xpp.next();

            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){

                switch( eventType ){

                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();


                        if(tag.equals("item")) ;
                        //효능
                        else if(tag.equals("efcyQesitm")){

                            xpp.next();
                            Log.d("확인",xpp.getText());

                            eat_buff = xpp.getText().replace("<p>","");
                            eat_buff = eat_buff.replace("</p>","");


                        }
                        //용법
                        else if(tag.equals("useMethodQesitm")){

                            xpp.next();
                            Log.d("확인",xpp.getText());
                            eat_plan = xpp.getText().replace("<p>","");
                            eat_plan = eat_plan.replace("</p>","");

                        }
                        //주의사항0
                        else if(tag.equals("atpnWarnQesitm")){

                            try {
                                xpp.next();
                                eat_warmming1 = eat_warmming1 + xpp.getText().replace("<p>", "");
                                eat_warmming1 = eat_warmming1.replace("</p>", "\n");
                            }catch (Exception e){

                            }

                        }
                        //주의사항1
                        else if(tag.equals("atpnQesitm")){

                            xpp.next();
                            Log.d("확인",xpp.getText());
                            eat_warmming1 = eat_warmming1 + xpp.getText().replace("<p>","");
                            eat_warmming1 = eat_warmming1.replace("</p>","\n");

                        }
                        //주의사항2
                        else if(tag.equals("intrcQesitm")){

                            xpp.next();
                            Log.d("확인",xpp.getText());
                            eat_warmming1 = eat_warmming1 + xpp.getText().replace("<p>","");
                            eat_warmming1 = eat_warmming1.replace("</p>","");

                        }
                        //부작용
                        else if(tag.equals("seQesitm")){

                            xpp.next();
                            Log.d("확인",xpp.getText());
                            eat_debuff = xpp.getText().replace("<p>","");
                            eat_debuff = eat_debuff.replace("</p>","");


                        }
                        //보관방법
                        else if(tag.equals("depositMethodQesitm")){

                            xpp.next();
                            Log.d("확인",xpp.getText());
                            md_leave_save = xpp.getText().replace("<p>","");
                            md_leave_save = md_leave_save.replace("</p>","");


                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();

                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }



}