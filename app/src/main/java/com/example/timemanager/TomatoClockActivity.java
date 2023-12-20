package com.example.timemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.timemanager.fragment.ScheduleFragment;

public class TomatoClockActivity extends AppCompatActivity {
//    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato_clock);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        final MFragment mfragment = new MFragment();
//        fragmentTransaction.add(R.id.container, mFragment);
        //todo:tomato clock image

        //返回按钮
        //todo:below is wrong!!!
        Button escape = findViewById(R.id.escape);
        escape.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示：");
            builder.setMessage("确认结束该番茄？");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNegativeButton("取消",null);
            builder.create().show();
        });
    }
}