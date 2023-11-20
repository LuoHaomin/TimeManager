package com.example.timemanager.database;


import android.content.Context;

import com.example.timemanager.bean.Schedule;

import java.util.Calendar;
import java.util.List;

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
    List<Schedule> list;
    List<Double> time_distribution;

    private DB_Schedule db_schedule;
    public DailySchedule(Context context,int version,Calendar date_){
        mcontext=context;
        mversion=version;
        date=date_;
        db_schedule=DB_Schedule.getInstance(context,version);
    }

    public List<Schedule> getScheduleList() {
        db_schedule.openReadLink();
        list=db_schedule.query("");
        db_schedule.closeLink();
        return list;
    }

    public List<Double> getTime_distribution() {

        return time_distribution;
    }

}
