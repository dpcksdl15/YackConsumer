package com.yackconsumer.yackconsumer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewpager2Adapter extends RecyclerView.Adapter<Viewpager2Adapter.ViewHolder> {

    int image[];

    private int click_position = -1;

    private OnListItemSelectedInterface onListItemSelectedInterface = null;

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    public void setOnClickListener(OnListItemSelectedInterface listener){
        this.onListItemSelectedInterface = listener;
    }

    public Viewpager2Adapter(int[] image){
        this.image = image;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpage,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewpager2Adapter.ViewHolder holder, int position) {
        holder.imageView.setBackgroundResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.viewpager_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click_position = getAdapterPosition();

                    onListItemSelectedInterface.onItemSelected(view,click_position);
                }
            });

        }
    }
}
