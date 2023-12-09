package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ListView;

public class SelfComment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_comment);

        Toolbar title=findViewById(R.id.navigation_bar_in_Frag);
        setSupportActionBar(title);
        title.setBackgroundResource(R.color.gray);
        title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(view -> {
            finish();
        });
    }
}