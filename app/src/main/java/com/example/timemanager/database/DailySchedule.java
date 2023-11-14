package com.example.timemanager.database;


import java.util.List;

/*
* 处理每天的数据
*
*
*
* */
public class DailySchedule {
    public List<Act> acts;
    
    class Act{
        public String content;
        public String start;
        public String end;

    }
}
