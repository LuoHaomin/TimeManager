package com.example.timemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;


import com.example.timemanager.bean.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditScheduleActivity extends AppCompatActivity {

    private Schedule schedule = new Schedule();
    List<String> tagss = new ArrayList<>();
    Bundle bundle;
    String newTag="";
    //time
    Date date;
    Calendar calendar = Calendar.getInstance(),start = Calendar.getInstance(), end = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");


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

        //显示标签
        setTag();
        //添加标签
        Button btn_tag = findViewById(R.id.add_plan_in_edit_schedule);
        btn_tag.setOnClickListener(view -> {
            dialog_tag();
        });
        //选标签
        RadioGroup radioGroup = findViewById(R.id.tag_radio);
        if(entrance == 0)
            schedule.stuff = tagss.get(0);
        else{
            for (int i = 0; i < tagss.size(); i++){
                if(schedule.stuff.equals(tagss.get(i)))
                    radioGroup.check(radioGroup.getChildAt(i).getId());
            }
        }
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            for(int k = 0; k < radioGroup.getChildCount(); k++){
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(k);
                if(radioButton.isChecked())
                    schedule.stuff = tagss.get(k);
            }
        });

        //写入地点
        EditText place = findViewById(R.id.edit_place);
        place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                schedule.content = place.getText().toString();
            }
        });

        //定时间
        Button start_btn = findViewById(R.id.edit_start_time);
        start_btn.setText(schedule.start_time);
        start_btn.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    start.set(Calendar.HOUR_OF_DAY, i);
                    start.set(Calendar.MINUTE,i1);
                    start_btn.setText(getTimeString(start));
                    date = start.getTime();
                    schedule.start_time = format.format(date);
                }
            },start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE), true);
            timePickerDialog.show();
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    start.set(i, i1, i2);
                }
            },
                    start.get(Calendar.YEAR),
                    start.get(Calendar.MONTH),
                    start.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        Button end_btn = findViewById(R.id.edit_end_time);
        end_btn.setText(schedule.end_time);
        end_btn.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    end.set(Calendar.HOUR_OF_DAY, i);
                    end.set(Calendar.MINUTE, i1);
                    end_btn.setText(String.format("%d年%d月%d日 %d: %d",end.get(Calendar.YEAR),
                            end.get(Calendar.MONTH)+1,end.get(Calendar.DAY_OF_MONTH),end.get(Calendar.HOUR_OF_DAY),
                            end.get(Calendar.MINUTE)));
                    date = end.getTime();
                    schedule.end_time = format.format(date);
                }
            }, end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE), true);
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
    }

    private void dialog_tag(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_tag, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        EditText text = view.findViewById(R.id.et_in_dialog_tag);
        Button button = view.findViewById(R.id.btn_in_dialog_tag);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {newTag = text.getText().toString();}
        });

        button.setOnClickListener(view1 -> {
            SharedPreferences tag_in_share = getSharedPreferences("tag", MODE_PRIVATE);
            SharedPreferences.Editor editor = tag_in_share.edit();
            String s = tag_in_share.getString("tag", "");
            int num = tag_in_share.getInt("num", 0);
            editor.putInt("num", num + 1);
            editor.putString("tag", s + newTag + "|");
            editor.apply();//必须commit/apply
            setTag();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setTag(){
        SharedPreferences tag_in_share = getSharedPreferences("tag", MODE_PRIVATE);//获取数据
        String st = tag_in_share.getString("tag", ""), s = "";//读取数据
        int num = tag_in_share.getInt("num", 0);
        tagss.clear();  //清空已有标签

        //刷新现有（包括新建）标签
        for(int i = 0; i < num; i++){
            s = st.substring(0, st.indexOf('|'));
            tagss.add(s);
            st = st.substring(st.indexOf('|' + 1));
        }
        RadioGroup tagRadio = findViewById(R.id.tag_radio);
        tagRadio.removeAllViews();//清空先前所有标签

        for(int i = 0; i < num; i++){
            RadioButton tagButton = new RadioButton(this);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1);//设置显示格式
            tagButton.setText(tagss.get(i));
            tagRadio.addView(tagButton, lp);
        }
        tagRadio.check(tagRadio.getChildAt(0).getId());
    }

    private String getTimeString(Calendar calendar){
        return String.format("%d年%d月%d日 %d : %d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

}