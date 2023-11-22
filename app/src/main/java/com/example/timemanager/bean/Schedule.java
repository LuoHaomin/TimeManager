package com.example.timemanager.bean;

public class Schedule {
    public long id=0;

    public String start_time;
    public String end_time;
    public String finish;

    public String content;
    public String position;
    public String repeat_mode;
    public String repeat_time;
    public String stuff;

    public String root;
    public String code;

    public Schedule(){
        start_time = "YY-MM-DD hh:mm";
        end_time = "YY-MM-DD hh:mm";
        content="No Content";
        finish = "N";
        position="none";
        repeat_mode= "mode0";
        root ="nowhere";
        repeat_time="none";
        stuff="none";
    }
    public String code(){
        String txt;
        txt=String.valueOf(id)+"`"+content+":"+finish+"*"
            +start_time+"!" +end_time+"#" +position+"^"
            +repeat_mode+"|"+repeat_time+"%"+ root +";"
            +stuff+"\n";
        code=txt;
        return code;
    }
    public void decode(String Code){
        code=Code.substring(0,Code.indexOf('\n'));
        id=Integer.valueOf(code.substring(0,code.indexOf('`')));
        content=code.substring(code.indexOf('`')+1,code.indexOf(':'));
        finish=code.substring(code.indexOf(':')+1,code.indexOf('*'));
        start_time=code.substring(code.indexOf('*')+1,code.indexOf('!'));
        end_time=code.substring(code.indexOf('!')+1,code.indexOf('#'));
        position=code.substring(code.indexOf('#')+1,code.indexOf('^'));
        repeat_mode=code.substring(code.indexOf('^')+1,code.indexOf('|'));
        repeat_time=code.substring(code.indexOf('|')+1,code.indexOf('%'));
        root =code.substring(code.indexOf('%')+1,code.indexOf(';'));
        stuff=code.substring(code.indexOf(';')+1);
    }

}
