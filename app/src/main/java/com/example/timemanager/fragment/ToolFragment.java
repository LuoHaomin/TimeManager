package com.example.timemanager.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.timemanager.R;


public class ToolFragment extends Fragment {





    public ToolFragment() {
        // Required empty public constructor
    }


    public static ToolFragment newInstance() {
        ToolFragment fragment = new ToolFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tool, container, false);
        ImageButton tomato=view.findViewById(R.id.tomato_clock);
        tomato.setOnClickListener(view1 -> {
            AlertDialog dialog=new AlertDialog.Builder(getActivity())
                    .setTitle("<开发中>")
                    .setMessage("基于番茄工作法。\n" +
                            "给任务设定一个执行时间，专注任务，直到时间结束。")
                    .setPositiveButton(R.string.IKnow,(dialogInterface, i) -> {})
                    .create();
            dialog.show();
        });
        ImageButton filter = view.findViewById(R.id.filter);
        filter.setOnClickListener(view1 -> {
            AlertDialog dialog=new AlertDialog.Builder(getActivity())
                    .setTitle("<开发中>")
                    .setMessage("管理你收到的消息，将对你来说重要的消息过滤出来，减少打扰。")
                    .setPositiveButton(R.string.IKnow,(dialogInterface, i) -> {})
                    .create();
            dialog.show();
        });
        return view;
    }
}