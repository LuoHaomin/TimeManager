package com.example.timemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timemanager.bean.Plan;
import com.example.timemanager.bean.Schedule;
import com.example.timemanager.database.DB_Plan;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreatePlanActivity extends AppCompatActivity {

    private Plan plan = new Plan();
    List<String> tagss=new ArrayList<>();
    String newTag="";
    DB_Plan db_plan=DB_Plan.getInstance(this,1);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date date;
    Calendar calendar=Calendar.getInstance(),start=Calendar.getInstance(),end=Calendar.getInstance();
    Schedule newSchedule ;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        //导航栏
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

        //编辑框
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

        //添加标签
        setTag();

        Button btn_tag = findViewById(R.id.add_tag);
        btn_tag.setOnClickListener(view -> {
            dialog_tag();
        });


        //选标签
        RadioGroup radioGroup=findViewById(R.id.tag_radio);
        plan.tag=tagss.get(0);
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            for(int k=0;k<radioGroup.getChildCount();k++){
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(k);
                if(radioButton.isChecked()){
                    plan.tag=tagss.get(k);
                }
            }
        });

        //定时间
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
                    date=start.getTime();
                    plan.start_time=format.format(date);
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
                    date=end.getTime();
                    plan.end_time=format.format(date);
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

        //加子计划
        Button addBreakdown = findViewById(R.id.add_breakdown);
        addBreakdown.setOnClickListener(view -> {
            dialog_breakdown();
        });
        //显示子计划
        ListView breakdownList = findViewById(R.id.breakdown_list);
        breakdownList.setAdapter(new ArrayAdapter<String>(this,R.layout.arraylist,plan.nameOfB()));
        //加日程
        Button addSchedule  =findViewById(R.id.add_schedule);
        addSchedule.setOnClickListener(view -> {
            dialog_schedule();
        });

        //显示日程
        ListView schList = findViewById(R.id.schedule_list);
        schList.setAdapter(new ArrayAdapter<String>(this,R.layout.arraylist,plan.nameOfSch()));
        //确认
        Button confirm_btn = findViewById(R.id.confirm_button);
        confirm_btn.setOnClickListener(view -> {
            db_plan.openWriteLink();
            db_plan.insert(plan);
            db_plan.closeLink();
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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                newTag=text.getText().toString();
            }
        });

        //create a new tag
        button.setOnClickListener(view1 -> {
            SharedPreferences tag_in_share = getSharedPreferences("tag",MODE_PRIVATE);
            SharedPreferences.Editor editor =tag_in_share.edit();
            String s = tag_in_share.getString("tag","");
            int num = tag_in_share.getInt("num",0);
            editor.putInt("num",num+1);
            editor.putString("tag",s+newTag+"|");
            editor.commit();
            setTag();
            dialog.dismiss();
        });

        dialog.show();
    }
    private void dialog_breakdown(){
        newSchedule =new Schedule();

        View view = LayoutInflater.from(this).inflate(R.layout.create_breakdonw,null,false);
        final AlertDialog dialogB = new AlertDialog.Builder(this).setView(view).create();
        EditText editText = view.findViewById(R.id.d1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                newSchedule.content=editText.getText().toString();
            }
        });


        time= String.format("%d年%d月%d日 %d: %d",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
        Button btn_end =view.findViewById(R.id.ddl);
        btn_end.setText(time);
        btn_end.setOnClickListener(view0 -> {
            TimePickerDialog timePickerDialog =new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    end.set(Calendar.HOUR_OF_DAY,i);
                    end.set(Calendar.MINUTE,i1);
                    btn_end.setText(String.format("%d年%d月%d日 %d: %d",end.get(Calendar.YEAR),end.get(Calendar.MONTH)+1,end.get(Calendar.DAY_OF_MONTH),end.get(Calendar.HOUR_OF_DAY),end.get(Calendar.MINUTE)));
                    date=end.getTime();
                    newSchedule.end_time=format.format(date);
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

        Button confirm = view.findViewById(R.id.d2);
        confirm.setOnClickListener(view1 -> {
            if (newSchedule.content.length()>0){
                plan.breakdowns.add(newSchedule);
                ListView breakdownList = findViewById(R.id.breakdown_list);
                breakdownList.setAdapter(new ArrayAdapter<String>(this,R.layout.arraylist,plan.nameOfB()));
                dialogB.dismiss();
            }
            else {
                Toast.makeText(this,"名称不为空哦^_^",Toast.LENGTH_SHORT).show();
            }
        });
        dialogB.show();
    }

    private void dialog_schedule(){
        View view = LayoutInflater.from(this).inflate(R.layout.create_schedule,null,false);
        final AlertDialog dialog= new AlertDialog.Builder(this).setView(view).create();
        newSchedule=new Schedule();
        EditText editText = view.findViewById(R.id.dc1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                newSchedule.content=editText.getText().toString();
            }
        });

        Button last = view.findViewById(R.id.dc6);
        last.setOnClickListener(view1 -> {
            TimePickerDialog timePickerDialog =new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    end.set(Calendar.HOUR_OF_DAY,i);
                    end.set(Calendar.MINUTE,i1);
                    last.setText(String.format("%d时%d分",end.get(Calendar.HOUR_OF_DAY),end.get(Calendar.MINUTE)));
                    date=end.getTime();
                    newSchedule.end_time=format.format(date);
                }
            },0,0,false);
            timePickerDialog.show();
        });
        //TODO:好多没写！
        Button confirm = view.findViewById(R.id.dc5);
        confirm.setOnClickListener(view1 -> {
            plan.schedules.add(newSchedule);
            ListView schList = findViewById(R.id.schedule_list);
            schList.setAdapter(new ArrayAdapter<String>(this,R.layout.arraylist,plan.nameOfSch()));
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setTag(){
        SharedPreferences tag_in_share = getSharedPreferences("tag",MODE_PRIVATE);
        String st =tag_in_share.getString("tag",""),s="";
        int num=tag_in_share.getInt("num",0);
        tagss.clear();
        for(int i=0;i<num;i++){
            s=st.substring(0,st.indexOf('|'));
            tagss.add(s);
            st=st.substring(st.indexOf('|')+1);
        }
        RadioGroup tagRadio = findViewById(R.id.tag_radio);
        tagRadio.removeAllViews();

        for(int i=0;i<num;i++){
            RadioButton tagButton = new RadioButton(this);
            RadioGroup.LayoutParams lp =new RadioGroup.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1);
            tagButton.setText(tagss.get(i));
            tagRadio.addView(tagButton,lp);
        }
        tagRadio.check(tagRadio.getChildAt(0).getId());
    }

}