package com.example.timemanager.bean;

import java.util.ArrayList;
import java.util.List;

public class Plan {
    public long id;

    public String start_time;
    public String end_time;
    public String content="No Content";
    public List<Schedule> breakdowns=new ArrayList<>();
    public String code_bd;
    public List<Schedule> schedules=new ArrayList<>();
    public String code_sch;



    public String code_b(){
        String txt="("+breakdowns.size()+")";
        for (int i=0;i<breakdowns.size();i++){
            txt+=breakdowns.get(i).code();
        }
        code_bd=txt;
        return code_bd;
    }
    public String code_s(){
        String txt="("+schedules.size()+")";
        for (int i=0;i<schedules.size();i++){
            txt+=schedules.get(i).code();
        }
        code_sch=txt;
        return code_sch;
    }
    public void decode_b(){
        String txt=code_bd;
        Schedule schedule=new Schedule();
        int size;
        size=Integer.parseInt(txt.substring(1,txt.indexOf(')')));
        for(int i=0;i<size;i++){
            schedule.decode(txt.substring(txt.indexOf(')')+1));
            breakdowns.add(schedule);
            txt=txt.substring(txt.indexOf('/')+1);
        }
    }
    public void decode_s(){
        String txt=code_sch;
        Schedule schedule=new Schedule();
        int size;
        size=Integer.parseInt(txt.substring(1,txt.indexOf(')')));
        for(int i=0;i<size;i++){
            schedule.decode(txt.substring(txt.indexOf(')')+1));
            schedules.add(schedule);
            txt=txt.substring(txt.indexOf('/')+1);
        }
    }
}
