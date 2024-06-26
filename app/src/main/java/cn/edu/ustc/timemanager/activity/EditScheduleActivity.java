package cn.edu.ustc.timemanager.activity;

import static android.app.PendingIntent.getActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.timemanager.R;

import cn.edu.ustc.timemanager.bean.Schedule;
import cn.edu.ustc.timemanager.database.DB_Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditScheduleActivity extends AppCompatActivity {

    private Schedule schedule;
    public List<String> tagss = new ArrayList<>();
    Bundle bundle;
    public Long id;
    String newTag="";
    //time
    Date date;
    Calendar calendar = Calendar.getInstance(),start = Calendar.getInstance(), end = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    DB_Schedule db_schedule = DB_Schedule.getInstance(this,DB_Schedule.DB_VERSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        bundle = getIntent().getExtras();
        int entrance;
        if(bundle == null){
            entrance=0;
        }

        else entrance = bundle.getInt("entrance");

        switch (entrance){
            case 0://新建
                schedule=new Schedule();
                int year = bundle.getInt("year");
                int monthOfYear = bundle.getInt("month");
                int dayOfMonth = bundle.getInt("dayOfMonth");
                start.set(year,monthOfYear,dayOfMonth);
                end.set(year,monthOfYear,dayOfMonth);
//                Toast.makeText(this,entrance,Toast.LENGTH_SHORT).show();
                break;
            case 1://修改
                id = bundle.getLong("id");
                db_schedule.openReadLink();
                schedule=db_schedule.query("_id =" + id).get(0);
                db_schedule.closeLink();
                break;
        }

        //显示标签
        setTag();
        //添加标签
        Button btn_tag = findViewById(R.id.add_plan_in_edit_schedule);
        btn_tag.setOnClickListener(view -> dialog_tag());
        //选标签
        RadioGroup radioGroup = findViewById(R.id.tag_radio);
        if(entrance == 1)
            schedule.root = tagss.get(0);
        else{
            for (int i = 0; i < tagss.size(); i++){
                if(schedule.root.equals(tagss.get(i)))
                    radioGroup.check(radioGroup.getChildAt(i).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            for(int k = 0; k < radioGroup.getChildCount(); k++){
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(k);
                if(radioButton.isChecked())
                    schedule.root = tagss.get(k);
            }
        });

//        写入日程
        EditText schedule_name = findViewById(R.id.edit_schedule_name);
        if(entrance==1) schedule_name.setText(schedule.content);
        schedule_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                schedule.content = schedule_name.getText().toString();
            }
        });


        //写入地点
        EditText place = findViewById(R.id.edit_place);
        if (entrance == 1) {
            place.setText(schedule.position);
        }
        place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                schedule.position = place.getText().toString();
            }
        });
        //定时间

        Button start_btn = findViewById(R.id.edit_start_time);
        if(entrance==0){
            start_btn.setText(getTimeString(start));
            schedule.start_time=format.format(start.getTime());
        }
        else {
            start_btn.setText(schedule.start_time);
        }

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
        if(entrance==0){
            end_btn.setText(getTimeString(end));
            schedule.end_time=format.format(end.getTime());
        }else {
            end_btn.setText(schedule.end_time);
        }

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

        //重复模式
        RadioGroup radioGroup1 = findViewById(R.id.rg_repeat_pattern);
        Button repeat_val = findViewById(R.id.repeat_select);
        if(entrance==0){
            radioGroup1.check(radioGroup1.getChildAt(0).getId());
            schedule.repeat_mode = "0";
        }
        else {
            radioGroup1.check((radioGroup1.getChildAt(Integer.valueOf(schedule.repeat_mode)).getId()));
        }
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                for (int j = 0; j < radioGroup.getChildCount(); j++){
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(j);
                    if (radioButton.isChecked()){
                        schedule.repeat_mode = String.valueOf(j);
                        repeat_val.setText(radioButton.getText());
                    }
                }
            }
        });
        repeat_val.setText(schedule.repeat_time);
        //TODO:button优化显示
        repeat_val.setOnClickListener(view -> {
            switch (Integer.parseInt(schedule.repeat_mode)){
                case 0:
                    break;
                case 1:
                    mode1(repeat_val);
                    break;
                case 2:
                    mode2(repeat_val);
                    break;
            }
        });
        //备注
        EditText edit_remark=findViewById(R.id.edit_remarks);
        edit_remark.setText(schedule.stuff);
        edit_remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                db_schedule.openWriteLink();
                //todo:quick finish this
//                db_schedule.insert("stuff", schedule);
                db_schedule.closeLink();
                schedule.stuff = edit_remark.getText().toString();
            }
        });


//        删除日程
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(view -> finish());
//        else if (entrance==1) {
//            cancel.setOnClickListener(view -> {
//                cancel.setText("删除日程");
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("提示：");
//                builder.setMessage("确认删除？");
//                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        db_schedule.openWriteLink();
//                        db_schedule.delete("_id = " + id);
//                        db_schedule.closeLink();
//                        finish();
//                    }
//                });
//                builder.setNegativeButton("取消",null);
//                builder.create().show();
//            });
//        }
        Button confirm = findViewById(R.id.confirm_button);
        if(entrance==0){
            confirm.setOnClickListener(view -> {
                db_schedule.openWriteLink();
                db_schedule.insert(schedule);
                db_schedule.closeLink();
//                Toast.makeText(this,schedule.code(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("data","refresh");
                LocalBroadcastManager.getInstance(EditScheduleActivity.this).sendBroadcast(intent);
                sendBroadcast(intent);
                finish();
            });
        }
        else if (entrance==1){
            confirm.setOnClickListener(view -> {
                db_schedule.openWriteLink();
                db_schedule.update(schedule);
                db_schedule.closeLink();
//                Toast.makeText(this,schedule.code(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("data","refresh");
                LocalBroadcastManager.getInstance(EditScheduleActivity.this).sendBroadcast(intent);
                sendBroadcast(intent);
                finish();
            });
        }
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
            editor.apply();//必须commit||apply
            setTag();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setTag(){
        SharedPreferences tag_in_share = getSharedPreferences("tag", MODE_PRIVATE);//获取数据
        String st = tag_in_share.getString("tag", ""), s;//读取数据
        int num = tag_in_share.getInt("num", 0);
        tagss.clear();  //清空已有标签

        //刷新现有（包括新建）标签
        for(int i = 0; i < num; i++){
            s = st.substring(0, st.indexOf('|'));
            tagss.add(s);
            st = st.substring(st.indexOf('|') + 1);
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

    public void mode1(Button button){
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

                                schedule.repeat_time=editText.getText().toString();
                                if(schedule.repeat_time.matches(""))
                                    schedule.repeat_time="0";
                                if(Integer.valueOf(schedule.repeat_time)==0)
                                    button.setText("一直重复");
                                else {
                                    button.setText(schedule.repeat_time+" 次");
                                    schedule.repeat_time = "0/"+schedule.repeat_time;
                                }
                            }
                        }
                )
                .create().show();
    }

    private void mode2(Button button){
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
//                    Toast.makeText(EditPlanActivity.this,format.format(date),Toast.LENGTH_SHORT);
                    schedule.start_time=format.format(date);
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
            schedule.repeat_time=data;
            button.setText(result);
            dialog.dismiss();
        });
        dialog.show();
    }

}