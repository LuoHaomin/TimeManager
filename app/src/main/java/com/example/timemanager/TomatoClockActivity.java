package com.example.timemanager;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TomatoClockActivity extends AppCompatActivity {
    private Context mcontext;
    CountDownTimer countdown_timer;
    TextView timer;
    ImageView tomato_clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato_clock);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        final MFragment mfragment = new MFragment();
//        fragmentTransaction.add(R.id.container, mFragment);

//        tomato_clock 点击开始番茄
        timer = findViewById(R.id.timer);
        tomato_clock = findViewById(R.id.tomato_clock);
        tomato_clock.setOnClickListener(view -> startTime());

        //返回按钮
        Button escape = findViewById(R.id.escape);
        escape.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示：");
            builder.setMessage("确认结束该番茄？");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    destroyTimer();
                    finish();
                }
            });
            builder.setNegativeButton("取消",null);
            builder.create().show();
        });
    }

    private void startTime(){
        tomato_clock.setClickable(false);
        countdown_timer = new CountDownTimer(25 * 60 * 1000, 1000){
            @Override
            public void onTick(long l) {
//                long hours = (l / 1000) / 3600;
                long minutes = ((l / 1000) % 3600) / 60;
                long seconds = (l / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timer.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                timer.setText("00:00");
//                timer.setTextColor();
                Toast.makeText(TomatoClockActivity.this, "一个番茄结束啦 休息5分钟 再接再厉", Toast.LENGTH_SHORT).show();
                MediaPlayer alarm = MediaPlayer.create(TomatoClockActivity.this, R.raw.tomato_clock_ring);
                alarm.start();
                tomato_clock.setClickable(true);
            }
        }.start();
    }

    private void destroyTimer(){
        if (countdown_timer != null){
            countdown_timer.cancel();
        }
    }
}