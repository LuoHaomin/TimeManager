package com.example.timemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.timemanager.R;
import com.example.timemanager.bean.Schedule;
import com.example.timemanager.database.DB_Schedule;
//import com.google.api.Context;

import java.util.List;

public class HomePageScheduleAdapter extends BaseAdapter {
    private Context mContext;
    private List<Schedule> mSchedule;
    DB_Schedule dbSchedule;

    public HomePageScheduleAdapter(Context context, List<Schedule> schedule_List){
        mContext = context;
        mSchedule = schedule_List;
        dbSchedule = DB_Schedule.getInstance(context,DB_Schedule.DB_VERSION);
    }
    @Override
    public int getCount() {return mSchedule.size();}

    @Override
    public Object getItem(int i) {return mSchedule.get(i);}

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.home_page_fragment_schedule, null);
            holder.checkbox = view.findViewById(R.id.checkbox);
            holder.place = view.findViewById(R.id.place);
            holder.start_time = view.findViewById(R.id.start_time);
            holder.end_time = view.findViewById(R.id.end_time);
            holder.tomato = view.findViewById(R.id.tomato);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Schedule schedule = mSchedule.get(i);
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    mSchedule.get(i).finish="1";
                else mSchedule.get(i).finish="0";
                dbSchedule.openWriteLink();
                dbSchedule.update(mSchedule.get(i));
                dbSchedule.closeLink();
            }
        });
        holder.name.setText(schedule.content);
        holder.place.setText(schedule.position);
        holder.start_time.setText(schedule.start_time);
        holder.end_time.setText(schedule.end_time);

        return view;
    }

    public final class ViewHolder{
        public CheckBox checkbox;
        public TextView name;
        public TextView place;
        public TextView start_time;
        public TextView end_time;
        public ImageButton tomato;
    }
}
