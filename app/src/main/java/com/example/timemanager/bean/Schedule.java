package com.example.timemanager.bean;

import java.util.Calendar;

public class Schedule {
    public long id;

    public String start_time;
    public String end_time;
    public String content;
    public String position;
    public String repeat_mode;
    public String repeat_time;
    public String stuff;
    public String code;

    public Schedule(){
        start_time = "YY-MM-DD hh:mm";
        end_time = "YY-MM-DD hh:mm";
        content="No Content";
        position="";
    }
    public String code(){
        String txt;
        txt=content+"@"
            +start_time+"!" +end_time+"#" +position+"$"
            +repeat_mode+":"+repeat_time+"%"
            +stuff+"/";
        code=txt;
        return code;
    }
    public void decode(String Code){
        code=Code.substring(0,Code.indexOf('/'));

        content=code.substring(0,code.indexOf('@'));
        start_time=code.substring(code.indexOf('@'),code.indexOf('!'));
        end_time=code.substring(code.indexOf('!'),code.indexOf('#'));
        position=code.substring(code.indexOf('#'),code.indexOf('$'));
        repeat_mode=code.substring(code.indexOf('$'),code.indexOf(':'));
        repeat_time=code.substring(code.indexOf(':'),code.indexOf('%'));
        stuff=code.substring(code.indexOf('%'),code.indexOf('/'));


    }
    //TODO: code and decode!
}
