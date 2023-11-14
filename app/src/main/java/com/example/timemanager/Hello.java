package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.timemanager.database.DB_Plan;


public class Hello extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        SharedPreferences tag_in_share = getSharedPreferences("tag", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =tag_in_share.edit();
        String s = tag_in_share.getString("tag","");
        int num = tag_in_share.getInt("num",0);
        if (num == 0){
            DB_Plan db_plan;
            db_plan=DB_Plan.getInstance(this,1);
            db_plan.openWriteLink();
            db_plan.deleteAll();
            db_plan.closeLink();
            editor.putString("tag","学习|工作|个人|二课|");
            editor.putInt("num",4);
            editor.commit();
        }

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
