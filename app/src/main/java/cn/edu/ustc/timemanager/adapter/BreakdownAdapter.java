package cn.edu.ustc.timemanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timemanager.R;

import cn.edu.ustc.timemanager.bean.Schedule;

import java.util.List;

public class BreakdownAdapter extends BaseAdapter {
    List<Schedule> list;
    Context mcontext;
    public BreakdownAdapter(Context context,List<Schedule> schedules){
        list=schedules;
        mcontext=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        ViewHolder holder;
        if(convertview==null){
            holder=new ViewHolder();
            convertview= LayoutInflater.from(mcontext).inflate(R.layout.breakdown_in_plan,null);
            holder.content=convertview.findViewById(R.id.content);
            holder.time=convertview.findViewById(R.id.time);
            convertview.setTag(holder);
        }
        else {
            holder=(ViewHolder) convertview.getTag();
        }
        Schedule schedule=list.get(position);

        holder.content.setText(schedule.content);
        if(schedule.finish!="Y")
            holder.content.setTextColor(Color.BLACK);
        else {
            holder.content.setTextColor(Color.GREEN);
        }
        holder.time.setText(schedule.end_time);
        return convertview;
    }
    public final class ViewHolder{

        public TextView content;
        public TextView time;
    }
}
