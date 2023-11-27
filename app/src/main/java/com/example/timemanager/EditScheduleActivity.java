package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditScheduleActivity extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        bundle = getIntent().getExtras();
        assert bundle != null;
        int entrance = bundle.getInt("entrance");

        switch (entrance){
            case 0:
                Long id = bundle.getLong("id");
                break;
            case 1:
                int year = bundle.getInt("year");
                int monthOfYear = bundle.getInt("monthOfYear");
                int dayOfMonth = bundle.getInt("dayOfMonth");
        }

    }
}