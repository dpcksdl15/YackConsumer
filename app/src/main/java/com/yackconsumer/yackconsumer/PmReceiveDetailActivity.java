package com.yackconsumer.yackconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class PmReceiveDetailActivity extends AppCompatActivity {

    // 0:id확인, 1:가입중복확인, 2:가입, 3:로그인, 4:전자영수증, 5:전자영수증자세히보기

    TextView tv_pharm_nm, tv_pharm_lisence, tv_pharm_addr, tv_pharm_tel, tv_pharm_ceo, tv_sale_date, tv_sale_card, tv_sale_cardnm, tv_joje_price, tv_nomal_price;
    TextView tv_sale_price, tv_tot_price;
    ListView lv_med_list;
    LinearLayout ll_card;
    ImageView img_qr;


    //결과값 리턴
    String result, rs;
    String value = "5";
    int sale_no;

    //리턴값 정제
    int nomal_price =0;
    int tot_price = 0;
    int discount = 0;
    int joje_price = 0;

    ArrayList<PmReceiveDetailData> arrayList;

    // ftp image
    private static final String HOST = "52.78.149.155";
    private static final int PORT = 21;
    private String USERNAME = "eunsung-ftp";
    private String PASSWORD = "!eunsung2018";

    private FTPClient client;

    File file = null;
    String filename;
    String res = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_receive_detail);

        tv_pharm_nm = findViewById(R.id.pharm_nm);
        tv_pharm_lisence = findViewById(R.id.pharm_lisence);
        tv_pharm_addr = findViewById(R.id.pharm_addr);
        tv_pharm_tel = findViewById(R.id.pharm_tel);
        tv_pharm_ceo = findViewById(R.id.pharm_ceo);
        tv_sale_date = findViewById(R.id.sale_date);
        tv_sale_card = findViewById(R.id.sale_card);
        tv_sale_cardnm = findViewById(R.id.sale_cardnm);
        tv_joje_price = findViewById(R.id.joje_price);
        tv_nomal_price = findViewById(R.id.nomal_price);
        tv_sale_price = findViewById(R.id.sale_price);
        tv_tot_price = findViewById(R.id.tot_price);
        lv_med_list = findViewById(R.id.med_list);
        ll_card = findViewById(R.id.ll_card);
        img_qr = findViewById(R.id.img_qr);

        Intent intent = getIntent();

        sale_no = intent.getExtras().getInt("sale_no");


        if (sale_no != 0){

            //결제목록 불러오기
            ServerRegisterActivity task = new ServerRegisterActivity();
            try {
                Log.d("전자영수증 확인", "결제번호 " + sale_no);
                result = task.execute(value, String.valueOf(sale_no)).get();
                Log.d("전자영수증 리턴확인", "데이터 " + result);


                //리턴받은 json 분석
                JSONArray jsonArray = new JSONObject(result).getJSONArray(String.valueOf(sale_no));
                JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                if (jsonArray != null){

                    arrayList = new ArrayList<PmReceiveDetailData>();
                    for (int i=0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("jsonArray", jsonObject.toString());


                        arrayList.add(new PmReceiveDetailData(jsonObject.getString("MED_NM"), jsonObject.getString("MED_PRICE"), jsonObject.getString("MED_TOT"),jsonObject.getString("TOT_PRICE")));

                        if (jsonObject.getString("MED_GB").equals("일반의약품")){
                        nomal_price = nomal_price + Integer.parseInt(jsonObject.getString("TOT_PRICE"));
                        } else {
                            joje_price = joje_price + Integer.parseInt(jsonObject.getString("TOT_PRICE"));
                        }
                        if (jsonObject.getString("DISCOUNT").equals("0") != true){
                            discount = discount + Integer.parseInt(jsonObject.getString("DISCOUNT"));
                        }

                        }

                    final PmReceiveDetailAdater pmReceiveDetailAdater = new PmReceiveDetailAdater(this, arrayList);

                    lv_med_list.setAdapter(pmReceiveDetailAdater);
                    setListViewHeightBasedOnItems(lv_med_list);

                    filename = jsonObject2.getString("QR") + ".png";

                    tv_pharm_nm.setText(jsonObject2.getString("MEM_NM"));
                    tv_pharm_lisence.setText(jsonObject2.getString("BIZ_NO"));
                    tv_pharm_addr.setText("");

                    if (jsonObject2.getString("TEL_NO").equals("null")){
                        tv_pharm_tel.setText("");
                    } else {
                        tv_pharm_tel.setText(jsonObject2.getString("TEL_NO"));
                    }

                    tv_pharm_ceo.setText(jsonObject2.getString("DGATE_NM"));
                    tv_sale_date.setText(jsonObject2.getString("SALE_YMDHMS"));
                    if (jsonObject2.getString("CARDCOM_NM").equals("null") != true) {
                        tv_sale_cardnm.setText(jsonObject2.getString("CARDCOM_NM"));
                        tv_sale_card.setText(jsonObject2.getString("CARD_NO"));
                    } else {
                        tv_sale_cardnm.setText("");
                        tv_sale_card.setText("");
                        ll_card.setVisibility(View.GONE);
                    }

                    tv_joje_price.setText(String.valueOf(joje_price)+"원");
                    tv_nomal_price.setText(String.valueOf(nomal_price)+"원");
                    tv_sale_price.setText(String.valueOf(discount)+"원");
                    tv_tot_price.setText(joje_price + nomal_price - discount + "원");

                            qrThread qrthread = new qrThread();
                            qrthread.start();

                }



            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    class qrThread extends Thread{
        public qrThread(){}

        @Override
        public void run() {
            qrimg();
        }

        public void qrimg(){

            if (!ftpconnect(HOST)){
                Log.d("client", "client연결실패");
                return;
            }
            if (!ftplogin(USERNAME, PASSWORD)){
                Log.d("client", "client로그인실패");
                return;
            }
            Log.d("client", "client" + client);

            try {

                client.changeWorkingDirectory("pharmQR");

                FTPFile files[] = client.listFiles();
                File DIR= getCacheDir();
                Log.d("client", "client리스트" + files.length);


                for (int i=0; i< files.length; i++){
                    if (files[i].getName().equals(filename)){

                        Log.d("client", "client주소확인" + client.getReplyString());
                        Log.d("client", "client주소확인" + client.getReplyCode());

                        file = new File(DIR,files[i].getName());
                        file.createNewFile();

                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));

                        client.setFileType(FTP.BINARY_FILE_TYPE);
                        client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                        boolean tf2 = client.retrieveFile(files[i].getName(),outputStream);

                        Log.d("client", "client주소확인" + client.getReplyString());
                        Log.d("client", "client주소확인" + client.getReplyCode());
                        Log.d("client", "client다운확인" + tf2);

                        res = DIR+"/"+files[i].getName();

                        new Thread(){

                            @Override
                            public void run() {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bitmap bm = BitmapFactory.decodeFile(res);
                                        img_qr.setImageBitmap(bm);
                                    }
                                });

                            }
                        }.start();

                        break;
                    }
                }

                client.disconnect();

            } catch (Exception e){
                Log.d("client", e.toString());
                try {
                    client.disconnect();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    //ListView 크기 지정
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            //setDynamicHeight(listView);
            return true;

        } else {
            return false;
        }
    }



    public boolean ftpconnect(String host){

        try {
            client = new FTPClient();
            client.setControlEncoding("UTF-8");
            client.connect(host,21);
            client.enterLocalPassiveMode();
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean ftplogin(String id, String pw){

        try {
            client.login(USERNAME,PASSWORD);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}