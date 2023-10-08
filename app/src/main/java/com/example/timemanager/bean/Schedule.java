package com.example.timemanager.bean;

import java.util.Calendar;

public class Schedule {
    public long id;

    public String start_time;
    public String end_time;
    public String content;

    public Schedule(){
        start_time = "YY-MM-DD hh:mm";
        end_time = "YY-MM-DD hh:mm";
        content="No Content";
    }
}
