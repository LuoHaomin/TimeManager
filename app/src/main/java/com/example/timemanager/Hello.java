package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Hello extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
    }
    @Override
    protected void onResume(){
        super.onResume();
        new Handler().postDelayed(Next,1000);
    }
    private Runnable Next= new Runnable() {
        @Override
        public void run() {
            Intent intent= new Intent(Hello.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };
}
