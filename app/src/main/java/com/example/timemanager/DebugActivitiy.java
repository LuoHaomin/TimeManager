package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.timemanager.bean.Plan;
import com.example.timemanager.bean.Schedule;
import com.example.timemanager.database.DB_Plan;

import java.util.List;

public class DebugActivitiy extends AppCompatActivity {
    private DB_Plan db_plan=DB_Plan.getInstance(this,1);
    private List<Plan> planList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        String s="";

        /*
        Calendar calendar=Calendar.getInstance();
        Date date=calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
*/
//        Set<String> tags=new HashSet<>();
//        Set<String> set=new HashSet<>();
//        set.add("123");
//        tags.add("tag1");
//        tags.add("tag2");
//        SharedPreferences share =getSharedPreferences("tag",MODE_PRIVATE);
//        SharedPreferences.Editor editor = share.edit();
//        editor.putString("tag","学习|工作|个人|");
//        editor.putInt("num",3);
//        editor.commit();
//        s=share.getString("tag","");
//
//        int num = share.getInt("num",0);
//        String st = share.getString("tag",""), ss="";

        TextView tv =findViewById(R.id.debug);
//        for(int i=0;i<num;i++){
//
//            //tagss.add(s);
//
//        }


        Plan plan=new Plan(),planA=new Plan();
        plan.content="示例计划";
        plan.start_time="time_start";
        plan.end_time="time";


//        schedule1.code=schedule.code();
//        schedule1.decode(schedule.code());
        for(int i=0;i<=3;i++){
            Schedule schedule =new Schedule(),schedule1=new Schedule();
            schedule.id=i;
            schedule.content="示例日程";
            schedule.repeat_mode="mode1";
            plan.schedules.add(schedule);
        }


        planA.code_sch=plan.code_s();
        planA.decode_s();

//        tv.setText(planA.schedules.get(1).finish);


        db_plan.openWriteLink();
        db_plan.deleteAll();
        long i=db_plan.insert(plan);
        db_plan.closeLink();
        db_plan.openReadLink();
        planList=db_plan.query("_id is not null");
        tv.setText(String.valueOf(planList.get(0).start_time));
    }
}