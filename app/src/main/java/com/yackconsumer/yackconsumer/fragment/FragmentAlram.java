package com.yackconsumer.yackconsumer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yackconsumer.yackconsumer.MainActivity;
import com.yackconsumer.yackconsumer.R;

public class FragmentAlram extends Fragment {

    MainActivity mainActivity;
    ImageView bt_alram_add_icon;
    RecyclerView alram_recyclerview;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //현재 소속된 액티비티를 메인 액티비티로 한다.
        mainActivity = (MainActivity) getActivity();


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alram, null);

        bt_alram_add_icon = view.findViewById(R.id.bt_alram_add_icon);
        alram_recyclerview = view.findViewById(R.id.alram_recyclerview);

        bt_alram_add_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
