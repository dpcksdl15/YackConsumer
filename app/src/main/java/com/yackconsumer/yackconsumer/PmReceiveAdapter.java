package com.yackconsumer.yackconsumer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PmReceiveAdapter extends RecyclerView.Adapter<PmReceiveAdapter.ViewHolder> {

    private ArrayList<String> list1 = null;
    private ArrayList<String> list2 = null;
    private ArrayList<Integer> list3 = null;


    private OnListItemSelectedInterface mListener = null;

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_pmName, tv_date, tv_price;
        ImageView imv_receive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_pmName = itemView.findViewById(R.id.tv_pmName);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_price = itemView.findViewById(R.id.tv_price);
            imv_receive = itemView.findViewById(R.id.imv_receive);

            imv_receive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());
                }
            });

        }
    }

    PmReceiveAdapter(ArrayList<String> list1, ArrayList<String> list2, ArrayList<Integer> list3){
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.pm_recevie_list, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String pmName = list1.get(position);
        String date = list2.get(position);
        String price = NumberFormat.getInstance(Locale.getDefault()).format(list3.get(position));

        holder.tv_pmName.setText(pmName);
        holder.tv_date.setText(date);
        holder.tv_price.setText(price+"원");

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }


    public  void  setOnClickListener(OnListItemSelectedInterface listener){
        this.mListener = listener;
    }
}
