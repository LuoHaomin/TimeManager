package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.timemanager.fragment.EvaluationFragment;

public class EditSelfCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_self_comment);

        Button commit_button = findViewById(R.id.commit_button);
        commit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                todo:接入数据库存储self_comment
//                Intent intent = new Intent(this, MainActivity.class);
//                Bundle bundle = new Bundle();
//                intent.putExtra("entrance", 4);
//                startActivity(intent);
                finish();
            }
        });

        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}