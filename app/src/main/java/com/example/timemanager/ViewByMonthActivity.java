package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

public class ViewByMonthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_month);
        Toolbar title=findViewById(R.id.title_in_view);
        setSupportActionBar(title);
        title.setBackgroundResource(R.color.gray);
        title.setTitle("新建计划");
        title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(view -> {
            finish();
            Intent intent=new Intent(this, MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("PresentPage",0);
            intent.putExtras(bundle);
            startActivity(intent);

        });
        CalendarView calendarView=findViewById(R.id.calendar);

    }
}