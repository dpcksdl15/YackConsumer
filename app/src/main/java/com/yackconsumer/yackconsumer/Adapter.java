package com.yackconsumer.yackconsumer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<String> mData1 = null ;
    private ArrayList<String> mData2 = null ;
    private ArrayList<String> mData3 = null ;
    private ArrayList<String> mData4 = null ;

    Context mContext;

    int point;


    private int click_position = -1;

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView et_md_name, tv_md_code, tv_md_date, tv_md_price;
        LinearLayout background;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            et_md_name = itemView.findViewById(R.id.et_md_name);
            tv_md_code = itemView.findViewById(R.id.tv_md_code);
            tv_md_date = itemView.findViewById(R.id.tv_md_date);
            tv_md_price = itemView.findViewById(R.id.tv_md_price);

            background = itemView.findViewById(R.id.background);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    click_position = getAdapterPosition();
                    mListener.onItemSelected(view,getAdapterPosition());
                    notifyDataSetChanged();

                }
            });

        }
    }

    Adapter(ArrayList<String> list, ArrayList<String> list2, ArrayList<String> list3, ArrayList<String> list4, Context context, OnListItemSelectedInterface listener, int point) {
        mData1 = list ;
        mData2 = list2 ;
        mData3 = list3 ;
        mData4 = list4 ;
        this.mContext = context;
        this.mListener = listener;
        this.point = point;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.sub_md_price_recycler_list, parent,false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = mData1.get(position);
        String text2 = mData2.get(position);
        String text3 = mData3.get(position);
        String text4 = mData4.get(position);

        holder.et_md_name.setText(text);
        holder.tv_md_code.setText(text2);
        holder.tv_md_date.setText(text3);
        holder.tv_md_price.setText(text4+"원");

        if (click_position == position){
            holder.background.setBackgroundResource(R.drawable.md_price_result_check_list);
        } else {
            holder.background.setBackgroundResource(R.drawable.md_price_result_list);
        }
    }

    @Override
    public int getItemCount() {
        return mData4.size();
    }
}
