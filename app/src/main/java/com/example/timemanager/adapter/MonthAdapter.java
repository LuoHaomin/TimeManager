package com.example.timemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timemanager.R;
import com.example.timemanager.database.DB_Schedule;
import com.example.timemanager.database.DailySchedule;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class MonthAdapter extends BaseAdapter {
Context mcontext;
    Calendar month;
    DailySchedule dailySchedule;
    Map<Integer,String> event;
    Map<Integer,String> DDLs;

    public int FirstDay,LastDay,Today;
    Boolean thisMonth;
    public MonthAdapter (Context context, Calendar calendar,Boolean thismonth) throws ParseException {
        mcontext=context;
        month=(Calendar) calendar.clone();
        dailySchedule=new DailySchedule(context,DB_Schedule.DB_VERSION,(Calendar) month.clone());
        event=dailySchedule.getMonthEvent();
        DDLs = dailySchedule.getMonthDDLs();
//        Toast.makeText(context,String.format("%d月%d日",month.get(Calendar.MONTH)+1,month.get(Calendar.DAY_OF_MONTH)),Toast.LENGTH_SHORT).show();
        thisMonth=thismonth;
        Today=month.get(Calendar.DAY_OF_MONTH);
        month.set(Calendar.DAY_OF_MONTH,0);
        FirstDay = month.get(Calendar.DAY_OF_WEEK)%7;
        month.add(Calendar.MONTH,1);
        LastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    @Override
    public int getCount() {
        return 49;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view = LayoutInflater.from(mcontext).inflate(R.layout.month_item,null);
            holder= new ViewHolder();
            holder.date=view.findViewById(R.id.date);
            holder.item=view.findViewById(R.id.item);
            holder.ddl=view.findViewById(R.id.DDL);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        if(i>=FirstDay && i<FirstDay+LastDay){
            holder.date.setText(String.format(Locale.US,"%d",i-FirstDay+1));

            if(event.get(i-FirstDay+1)!=null){
                holder.item.setText(event.get(i-FirstDay+1));
            }

            else holder.item.setText("");

            if(DDLs.containsKey(i-FirstDay+1)){
                holder.ddl.setText(DDLs.get(i-FirstDay+1));
            }
            else
                holder.ddl.setText("");

            if(i-FirstDay+1==Today && thisMonth){
                holder.date.setTextColor(0xff11aa11);
            }
            else holder.date.setTextColor(mcontext.getColor(R.color.black));
        }
        else {
            holder.date.setText("");
            holder.item.setText("");
            holder.ddl.setText("");
        }

        return view;
    }

    static class ViewHolder{
        TextView date;
        TextView item;
        TextView ddl;
    }

}
