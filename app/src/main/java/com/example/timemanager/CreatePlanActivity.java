package com.example.timemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timemanager.adapter.BreakdownAdapter;
import com.example.timemanager.adapter.ScheduleAdapter;
import com.example.timemanager.bean.Plan;
import com.example.timemanager.bean.Schedule;
import com.example.timemanager.database.DB_Plan;
import com.example.timemanager.database.DB_Schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CreatePlanActivity extends AppCompatActivity {

    private Plan plan = new Plan();
    List<String> tagss=new ArrayList<>();
    String newTag="";
    DB_Plan db_plan=DB_Plan.getInstance(this,1);
    DB_Schedule db_schedule=DB_Schedule.getInstance(this,1);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date date;
    Calendar calendar=Calendar.getInstance(),start=Calendar.getInstance(),end=Calendar.getInstance();
    Schedule newSchedule ;
    String time;
    Bundle bundle;
    Boolean is_creating;
    long plan_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        bundle=getIntent().getExtras();
        assert bundle != null;
        is_creating=bundle.getBoolean("is_creating");
        if(!is_creating){
            plan_id=bundle.getLong("id");
            db_plan.openReadLink();
            plan=db_plan.query(String.format("_id = %d",plan_id)).get(0);
            db_plan.closeLink();
        }
        //导航栏
        Toolbar title=findViewById(R.id.title_in_c);
        setSupportActionBar(title);
        title.setBackgroundResource(R.color.gray);
        if(is_creating) title.setSubtitle("创建计划");
        else title.setSubtitle("编辑计划");
        //title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(view -> {
            Toast.makeText(this,"you touch it",Toast.LENGTH_SHORT).show();
            finish();
        });

        //编辑框
        EditText name=findViewById(R.id.plan_name);
        if(!is_creating)
            name.setText(plan.content);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        if(is_creating) plan.tag=tagss.get(0);
        else {
            for (int i=0;i<tagss.size();i++){
                if(plan.tag.equals(tagss.get(i)))
                    radioGroup.check(radioGroup.getChildAt(i).getId());
            }
        }
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            for(int k=0;k<radioGroup.getChildCount();k++){
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(k);
                if(radioButton.isChecked()){
                    plan.tag=tagss.get(k);
                }
            }
        });

        //定时间

//        if(plan.start_time.length()==0)
//            plan.start_time=format.format(start.getTime());
        Button btn_start =findViewById(R.id.btn_start_time);
        btn_start.setText(plan.start_time);
        btn_start.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog =new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {

                    start.set(Calendar.HOUR_OF_DAY,i);
                    start.set(Calendar.MINUTE,i1);
                    btn_start.setText(getTimeString(start));
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

//        if(is_creating==false){
//            try {
//                Date d =format.parse(plan.end_time);
//                end.setTime(d);
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//        }


        //time= getTimeString(end);
//        if(plan.end_time.length()==0)
//            plan.end_time=format.format(end.getTime());
        Button btn_end = findViewById(R.id.btn_end_time);
        btn_end.setText(plan.end_time);
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
        breakdownList.setAdapter(new BreakdownAdapter(this,plan.breakdowns));




        breakdownList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatePlanActivity.this);
                builder.setTitle("提示：");
                builder.setMessage("确认删除？");
                int t=pos;
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        plan.breakdowns.remove(t);
                        breakdownList.setAdapter(new BreakdownAdapter(CreatePlanActivity.this,plan.breakdowns));
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                return true;
            }
        });
        //加日程
        Button addSchedule  =findViewById(R.id.add_schedule);
        addSchedule.setOnClickListener(view -> {
            newSchedule=new Schedule();
            dialog_schedule();
        });

        //显示日程
        ListView schList = findViewById(R.id.schedule_list);
        schList.setAdapter(new ScheduleAdapter(this,plan.schedules));
        schList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatePlanActivity.this);
                builder.setTitle("提示：");
                builder.setMessage("确认删除？");
                int t=pos;
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db_schedule.openWriteLink();
                        //TODO:数据！！
                        db_schedule.delete(String.format("content = '%s' AND root ='%s'",plan.schedules.get(t).content,plan.content));
                        db_schedule.closeLink();
                        plan.schedules.remove(t);
                        schList.setAdapter(new ScheduleAdapter(CreatePlanActivity.this,plan.schedules));
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                return true;
            }
        });
        //确认
        Button confirm_btn = findViewById(R.id.confirm_button);
        if(is_creating){
            confirm_btn.setOnClickListener(view -> {
                db_schedule.openWriteLink();
                db_schedule.insert(plan.schedules);
                db_schedule.closeLink();

                db_plan.openWriteLink();
                db_plan.insert(plan);
                db_plan.closeLink();
                finish();
            });
        }
        else {
            confirm_btn.setOnClickListener(view -> {
                db_schedule.openWriteLink();
                for (int i=0;i<plan.schedules.size();i++){
                    if(plan.schedules.get(i).id==0){
                        db_schedule.insert(plan.schedules.get(i));
                    }
                    else {
                        db_schedule.update(plan.schedules.get(i));
                    }
                }
                db_schedule.closeLink();
                db_plan.openWriteLink();
                db_plan.update(plan,String.format("_id = %d",plan_id));
                db_plan.closeLink();
                finish();
            });
        }



        Button cancel_btn = findViewById(R.id.cancel);
        if(is_creating){
            cancel_btn.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示：");
                builder.setMessage("确认取消？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("返回", null);
                builder.create().show();
            });
        }
        else {
            cancel_btn.setText("删除计划");
            cancel_btn.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示：");
                builder.setMessage("确认删除？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db_plan.openWriteLink();
                        db_plan.delete(String.format("_id = %d",plan_id));
                        db_plan.closeLink();

                        db_schedule.openWriteLink();
                        db_schedule.delete(String.format("root = '%s'",plan.content));
                        db_schedule.closeLink();
                        finish();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();

            });
        }

    }
    private void dialog_tag(){
        View view= LayoutInflater.from(this)
                .inflate(R.layout.dialog_tag,null,false);
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

        View view = LayoutInflater.from(this).inflate(R.layout.create_breakdown,null,false);
        final AlertDialog dialogB = new AlertDialog.Builder(this).setView(view).create();
        EditText editText = view.findViewById(R.id.d1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                newSchedule.content=editText.getText().toString();
            }
        });


        time= String.format("%d年%d月%d日 %d: %d",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
        date=Calendar.getInstance().getTime();
        Button btn_end =view.findViewById(R.id.ddl);
        btn_end.setText(time);
        newSchedule.end_time=format.format(date);
        btn_end.setOnClickListener(view0 -> {
            TimePickerDialog timePickerDialog =new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    end.set(Calendar.HOUR_OF_DAY,i);
                    end.set(Calendar.MINUTE,i1);
                    btn_end.setText(String.format("%d年%d月%d日 %d: %d",end.get(Calendar.YEAR),end.get(Calendar.MONTH)+1,end.get(Calendar.DAY_OF_MONTH),end.get(Calendar.HOUR_OF_DAY),end.get(Calendar.MINUTE)));
                    date=end.getTime();
//                    Toast.makeText(CreatePlanActivity.this,format.format(date),Toast.LENGTH_SHORT);
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
                breakdownList.setAdapter(new BreakdownAdapter(this,plan.breakdowns));
                dialogB.dismiss();
            }
            else {
                Toast.makeText(this,"名称不为空哦^_^",Toast.LENGTH_SHORT).show();
            }
        });
        dialogB.show();
    }

    private void dialog_schedule(){
        AtomicReference<Boolean> b= new AtomicReference<>(false);
        View view = LayoutInflater.from(this).inflate(R.layout.create_schedule,null,false);
        final AlertDialog dialog= new AlertDialog.Builder(this).setView(view).create();
        newSchedule=new Schedule();

        //日程名称
        EditText editText = view.findViewById(R.id.dc1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                newSchedule.content=editText.getText().toString();
            }
        });

        //repeat mode
        Button repeat_val=view.findViewById(R.id.dc3);
        RadioGroup radioGroup=view.findViewById(R.id.dc2);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        newSchedule.repeat_mode="0";
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                for(int k=0;k<radioGroup.getChildCount();k++){
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(k);
                    if(radioButton.isChecked()){
                        newSchedule.repeat_mode=String.valueOf(k);
                        b.set(false);
                        repeat_val.setText(radioButton.getText());
                    }
                }
            }
        });

        repeat_val.setOnClickListener(view1 -> {
            switch (Integer.valueOf(newSchedule.repeat_mode)){
                case 0:
                    start=Calendar.getInstance();
                    TimePickerDialog timePickerDialog =new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {

                            start.set(Calendar.HOUR_OF_DAY,i);
                            start.set(Calendar.MINUTE,i1);
                            repeat_val.setText(getTimeString(start));
                            date=start.getTime();
                            newSchedule.start_time=format.format(date);
                        }
                    },start.get(Calendar.HOUR_OF_DAY),start.get(Calendar.MINUTE),true);
                    timePickerDialog.show();
                    DatePickerDialog pickerDialog =new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            start.set(i,i1,i2);
                        }
                    },
                            start.get(Calendar.YEAR),
                            start.get(Calendar.MONTH),
                            start.get(Calendar.DAY_OF_MONTH));
                    pickerDialog.show();
                    break;
                case 1:
                    dialog_mode1(repeat_val);
                    b.set(true);
                    break;
                case 2:
                    dialog_mode2(repeat_val);
                    b.set(true);
                    break;
            }
        });

        Button confirm = view.findViewById(R.id.dc5);
        confirm.setOnClickListener(view1 -> {
            newSchedule.root=plan.content;
            plan.schedules.add(newSchedule);
            ListView schList = findViewById(R.id.schedule_list);
            schList.setAdapter(new ScheduleAdapter(this,plan.schedules));
            dialog.dismiss();
        });

        dialog.show();
    }
    private void dialog_mode1(Button button){
        final EditText editText = new EditText(this);

        editText.setHint("次数（若为0，则无限重复）");
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder input = new AlertDialog.Builder(this);
        input.setTitle("重复次数")
                .setView(editText)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                newSchedule.repeat_time=editText.getText().toString();
                                if(newSchedule.repeat_time.matches(""))
                                    newSchedule.repeat_time="0";
                                if(Integer.valueOf(newSchedule.repeat_time)==0)
                                    button.setText("一直重复");
                                else {
                                    button.setText(newSchedule.repeat_time+" 次");
                                    newSchedule.repeat_time = "0/"+newSchedule.repeat_time;
                                }
                            }
                        }
                )
                .create().show();
    }
    private void dialog_mode2(Button button){
        CheckBox[] ch=new CheckBox[8];
        Calendar cld=Calendar.getInstance();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_mode2,null,false);

        //选时间
        final AlertDialog dialog= new AlertDialog.Builder(this).setView(view).create();
        Button btn_time = view.findViewById(R.id.btn_time);
        btn_time.setText("时间");
        btn_time.setOnClickListener(view1 -> {
            TimePickerDialog dialog1=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    cld.set(Calendar.HOUR_OF_DAY,i);
                    cld.set(Calendar.MINUTE,i1);
                    btn_time.setText(String.format("%d年%d月%d日 %d: %d",end.get(Calendar.YEAR),end.get(Calendar.MONTH)+1,end.get(Calendar.DAY_OF_MONTH),end.get(Calendar.HOUR_OF_DAY),end.get(Calendar.MINUTE)));
                    date=cld.getTime();
//                    Toast.makeText(CreatePlanActivity.this,format.format(date),Toast.LENGTH_SHORT);
                    newSchedule.start_time=format.format(date);
                }
            },cld.get(Calendar.HOUR_OF_DAY),cld.get(Calendar.MINUTE),true);
            dialog1.show();
        });
        //选日期
        ch[1]=view.findViewById(R.id.w1);
        ch[2]=view.findViewById(R.id.w2);
        ch[3]=view.findViewById(R.id.w3);
        ch[4]=view.findViewById(R.id.w4);
        ch[5]=view.findViewById(R.id.w5);
        ch[6]=view.findViewById(R.id.w6);
        ch[7]=view.findViewById(R.id.w7);

        //存数据
        Button confirm = view.findViewById(R.id.confirm_button);
        confirm.setOnClickListener(view1 -> {
            String result="";
            String data="";
            for(int i=1;i<=7;i++){
                if(ch[i].isChecked()){
                    result+=String.format("%d,",i);
                    data+=String.format("%d,",i==7 ? 1:i+1);
                }

            }
            newSchedule.repeat_time=data;
            button.setText(result);
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
    @NonNull
    private String getTimeString(Calendar calendar){
        return String.format("%d年%d月%d日 %d: %d",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
    }

}