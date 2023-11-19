package com.example.timemanager.database;


import android.content.Context;

import java.util.List;

/*
* 处理每天的数据
*
*
*
* */
public class DailySchedule {


    DailySchedule(Context context,int version){
        DB_Schedule db_schedule=DB_Schedule.getInstance(context,version);
        

    }


}
