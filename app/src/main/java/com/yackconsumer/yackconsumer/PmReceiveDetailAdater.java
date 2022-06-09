package com.yackconsumer.yackconsumer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PmReceiveDetailAdater extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<PmReceiveDetailData> prd;
    DecimalFormat format = new DecimalFormat("###,###");

    public PmReceiveDetailAdater (Context context, ArrayList<PmReceiveDetailData> prd){
        mContext = context;
        this.prd = prd;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return prd.size();
    }

    @Override
    public PmReceiveDetailData getItem(int i) {
        return prd.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.pmrecive_detail_list, null);

        TextView tv_med_nm = view.findViewById(R.id.tv_med_nm);
        TextView tv_med_price = view.findViewById(R.id.tv_med_price);
        TextView tv_med_count = view.findViewById(R.id.tv_med_count);
        TextView tv_med_tot_price = view.findViewById(R.id.tv_med_tot_price);

        tv_med_nm.setText(prd.get(position).getMed_nm());
        tv_med_price.setText(format.format(Integer.parseInt(prd.get(position).getMed_price())));
        tv_med_count.setText(format.format(Integer.parseInt(prd.get(position).getMed_count())));
        tv_med_tot_price.setText(format.format(Integer.parseInt(prd.get(position).getMed_tot_price())));

        return view;
    }
}
