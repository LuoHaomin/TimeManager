package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

public class ViewByWeekActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_week);

        Toolbar title=findViewById(R.id.title_in_view);
        setSupportActionBar(title);
        title.setBackgroundResource(R.color.gray);
        title.setTitle("新建计划");
        title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(view -> {
            finish();
            Intent intent=new Intent(this, MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("PresentPage",1);
            intent.putExtras(bundle);
            startActivity(intent);

        });
    }
}