package com.example.timemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.timemanager.bean.Plan;

import java.util.Calendar;

public class CreatePlanActivity extends AppCompatActivity {

    private Plan plan = new Plan();
    Calendar calendar=Calendar.getInstance(),start=Calendar.getInstance(),end=Calendar.getInstance();

    String time;
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
            finish();
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
            dialog_tag();

        });

        time= String.format("%d年%d月%d日 %d: %d",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));

        Button btn_start =findViewById(R.id.btn_start_time);
        btn_start.setText(time);
        btn_start.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog =new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    start.set(Calendar.HOUR_OF_DAY,i);
                    start.set(Calendar.MINUTE,i1);
                    btn_start.setText(String.format("%d年%d月%d日 %d: %d",start.get(Calendar.YEAR),start.get(Calendar.MONTH)+1,start.get(Calendar.DAY_OF_MONTH),start.get(Calendar.HOUR_OF_DAY),start.get(Calendar.MINUTE)));
                }
            },start.get(Calendar.HOUR_OF_DAY),start.get(Calendar.MINUTE),true);
            timePickerDialog.show();
            DatePickerDialog dialog =new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    start.set(i,i1,i2);

                }
            },
                    start.get(Calendar.YEAR),
                    start.get(Calendar.MONTH),
                    start.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        });

        Button btn_end = findViewById(R.id.btn_end_time);
        btn_end.setText(time);
        btn_end.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog =new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    end.set(Calendar.HOUR_OF_DAY,i);
                    end.set(Calendar.MINUTE,i1);
                    btn_end.setText(String.format("%d年%d月%d日 %d: %d",end.get(Calendar.YEAR),end.get(Calendar.MONTH)+1,end.get(Calendar.DAY_OF_MONTH),end.get(Calendar.HOUR_OF_DAY),end.get(Calendar.MINUTE)));
                }
            },end.get(Calendar.HOUR_OF_DAY),end.get(Calendar.MINUTE),true);
            timePickerDialog.show();
            DatePickerDialog dialog =new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    end.set(i,i1,i2);

                }
            },
                    end.get(Calendar.YEAR),
                    end.get(Calendar.MONTH),
                    end.get(Calendar.DAY_OF_MONTH));

            dialog.show();



        });
        RadioGroup radioGroup=findViewById(R.id.tag_radio);
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {

        });



        Button confirm_btn = findViewById(R.id.confirm_button);
        confirm_btn.setOnClickListener(view -> {

            finish();
        });
    }
    private void dialog_tag(){
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_tag,null,false);
        final AlertDialog dialog=new AlertDialog.Builder(this).setView(view).create();
        EditText text=view.findViewById(R.id.et_in_dialog_tag);
        Button button=view.findViewById(R.id.btn_in_dialog_tag);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        button.setOnClickListener(view1 -> {
            dialog.dismiss();
        });
        dialog.show();
        //dialog.getWindow().setLayout((ScreenU));
    }

}