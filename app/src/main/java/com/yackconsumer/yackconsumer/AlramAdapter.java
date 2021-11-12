package com.yackconsumer.yackconsumer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlramAdapter extends RecyclerView.Adapter<AlramAdapter.ViewHolder> {

    ArrayList<String> list1 = null;
    ArrayList<String> list2 = null;
    ArrayList<String> list3 = null;
    ArrayList<String> list4 = null;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.alram_main_recycler_list, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_alram_time, tv_alram_day, tv_alram_cycle, tv_alram_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_alram_time = itemView.findViewById(R.id.tv_alram_time);
            tv_alram_day = itemView.findViewById(R.id.tv_alram_day);
            tv_alram_cycle = itemView.findViewById(R.id.tv_alram_cycle);
            tv_alram_name = itemView.findViewById(R.id.tv_alram_name);

        }
    }

    AlramAdapter(ArrayList<String> list1,ArrayList<String> list2,ArrayList<String> list3,ArrayList<String> list4){

        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
        this.list4 = list4;

    }

}
