package com.example.timemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanager.R;
import com.example.timemanager.bean.Schedule;

import java.util.List;

public class DailyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Schedule> mSchedule;

    public DailyAdapter(Context context, List<Schedule> schedule_list){
        mContext = context;
        mSchedule = schedule_list;
    }

    // 获取列表项的个数
    @Override
    public int getCount() {
        return mSchedule.size();
    }

    // 获取列表项的数据
    @Override
    public Object getItem(int i) {
        return mSchedule.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();// 创建一个新的视图持有者
            // 根据布局文件schedule_in_daily_plan生成转换视图对象
            view = LayoutInflater.from(mContext).inflate(R.layout.schedule_in_daily_plan, null);
            holder.timeAndPlace = view.findViewById(R.id.timeAndPlace);
            holder.subPlan = view.findViewById(R.id.subPlan);
            holder.finish = view.findViewById(R.id.finish);
            view.setTag(holder);// 将视图持有者保存到转换视图当中
        }
        else{// 转换视图非空
            // 从转换视图中获取之前保存的视图持有者
            holder = (ViewHolder) view.getTag();
        }

        //TODO:rewrite sources in String;see in HomePage -x... Adapter
        Schedule schedule = mSchedule.get(position);
        holder.timeAndPlace.setText(schedule.start_time+schedule.end_time+"\n"+schedule.position);
        holder.subPlan.setText(schedule.content);

        return view;
    }

    // 定义一个视图持有者，以便重用列表项的视图资源
    public final class ViewHolder{
        public TextView timeAndPlace;
        public TextView subPlan;
        public CheckBox finish;
    }
}
