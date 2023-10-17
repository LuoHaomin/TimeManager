package com.example.timemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.timemanager.bean.Plan;

public class CreatePlanActivity extends AppCompatActivity {

    private Plan plan = new Plan();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_create_plan);
        Toolbar title=findViewById(R.id.title_in_Frag);
        setSupportActionBar(title);
        title.setBackgroundResource(R.color.gray);
        title.setTitle("新建计划");
        title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(view -> {
            Intent intent=new Intent(this, MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("PresentPage",1);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        //导航栏
        EditText name=findViewById(R.id.plan_name);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                plan.content=name.getText().toString();
            }
        });
        Button btn_tag = findViewById(R.id.add_tag);
        btn_tag.setOnClickListener(view -> {


        });
        Button btn_start =findViewById(R.id.btn_start_time);
        btn_start.setOnClickListener(view -> {

        });
        Button btn_end = findViewById(R.id.btn_end_time);
        btn_end.setOnClickListener(view -> {

        });
        RadioGroup radioGroup=findViewById(R.id.tag_radio);
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {

        });



        Button confirm_btn = findViewById(R.id.confirm_button);
        confirm_btn.setOnClickListener(view -> {

        });
    }
    private void dialog_tag(){
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_tag,null,false);
    }

}