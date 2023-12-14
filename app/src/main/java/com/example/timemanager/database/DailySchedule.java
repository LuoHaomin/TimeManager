package com.example.timemanager.database;


import android.content.Context;

import com.example.timemanager.bean.Plan;
import com.example.timemanager.bean.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 处理每天的数据
*
*
*
* */
public class DailySchedule {
    public Calendar date;
    public Context mcontext;
    int mversion=1;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date dates;
    List<Schedule> list;
    List<Plan> plans;
    List<Double> time_distribution;

    private DB_Schedule db_schedule;
    private DB_Plan db_plan;
    public DailySchedule(Context context,int version,Calendar date_){
        mcontext=context;
        mversion=version;
        date=date_;
        db_schedule=DB_Schedule.getInstance(context,version);
        db_plan=DB_Plan.getInstance(context,version);
    }

    public List<Schedule> getScheduleList() {

        db_schedule.openReadLink();
        list=db_schedule.query();
        db_schedule.closeLink();
        return list;
    }

    public List<Double> getTime_distribution() {

        return time_distribution;
    }
    public List<Schedule> getPast(){
        List<Schedule> past = new ArrayList<>();
        return past;
    }
    public Map<Integer,String> getMonthEvent(){
        Map<Integer,String> map=new HashMap<>();
        Schedule schedule;
        //重复日程
        Calendar date1 = (Calendar)date.clone();
        db_schedule.openReadLink();
        list=db_schedule.query("repeat_mode = 2");
        db_schedule.closeLink();
        date1.set(Calendar.DAY_OF_MONTH,1);
        List<Integer> repeat_days = new ArrayList<>();
        String s,st;

        for(int i=0; i< list.size();i++){
            schedule = list.get(i);
            st=schedule.repeat_time;
            repeat_days.clear();
            while (!st.matches("")){
                s=st.substring(0,st.indexOf(','));
                repeat_days.add(Integer.valueOf(s));
                st=st.substring(st.indexOf(',')+1);
            }
            date1.set(Calendar.DAY_OF_MONTH,1);
            for(int j=0;j<date1.getMaximum(Calendar.DAY_OF_MONTH);j++,date1.add(Calendar.DAY_OF_MONTH,1)){
                if (repeat_days.contains(date1.get(Calendar.DAY_OF_WEEK))){
                    if(map.containsKey(date1.get(Calendar.DAY_OF_MONTH))){
                        map.put(date1.get(Calendar.DAY_OF_MONTH),map.get(date1.get(Calendar.DAY_OF_MONTH))+"\n"+schedule.content);
                    }
                    else
                        map.put(date1.get(Calendar.DAY_OF_MONTH),schedule.content);

                }
            }
        }
        //TODO:mode0&1
        return map;
    }
    public Map<Integer,String> getMonthDDLs() throws ParseException {
        Map<Integer,String> map=new HashMap<>();
        Schedule schedule;
        Calendar ct=Calendar.getInstance();
        db_plan.openReadLink();
        plans = db_plan.query();
        db_plan.closeLink();
        for(int i=0;i< plans.size();i++){
            for(int j=0;j < plans.get(i).breakdowns.size();j++){
                schedule = plans.get(i).breakdowns.get(j);
                if(schedule.end_time.matches("YY-MM-DD hh:mm"))
                    continue;
                dates=format.parse(schedule.end_time);
                assert dates != null;
                ct.setTime(dates);
                if(ct.get(Calendar.YEAR)==date.get(Calendar.YEAR) && ct.get(Calendar.MONTH)==date.get(Calendar.MONTH)){
                    if(map.containsKey(ct.get(Calendar.DAY_OF_MONTH))){
                        map.put(ct.get(Calendar.DAY_OF_MONTH),map.get(ct.get(Calendar.DAY_OF_MONTH))+"\n"+schedule.content);
                    }
                    else
                        map.put(ct.get(Calendar.DAY_OF_MONTH),schedule.content);
                }
            }
        }
        return map;
    }
}
