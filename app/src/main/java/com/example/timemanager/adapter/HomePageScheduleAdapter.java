package com.example.timemanager.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timemanager.EditScheduleActivity;
import com.example.timemanager.R;
import com.example.timemanager.bean.Schedule;
import com.example.timemanager.database.DB_Schedule;
import com.example.timemanager.fragment.ScheduleFragment;

import java.text.ParseException;
import java.util.List;

public class HomePageScheduleAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Schedule> mSchedule;
    DB_Schedule dbSchedule;
    ScheduleFragment mfragment;

    public HomePageScheduleAdapter(Context context, List<Schedule> schedule_List,ScheduleFragment fragment){
        mContext = context;
        mSchedule = schedule_List;
        dbSchedule = DB_Schedule.getInstance(context,DB_Schedule.DB_VERSION);
        mfragment = fragment;
    }
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
            holder.name = view.findViewById(R.id.name);
            holder.checkbox = view.findViewById(R.id.checkbox);
            holder.place = view.findViewById(R.id.place);
            holder.start_time = view.findViewById(R.id.start_time);
            holder.end_time = view.findViewById(R.id.end_time);
            holder.tomato = view.findViewById(R.id.tomato);
            holder.daily_agenda_content = view.findViewById(R.id.daily_agenda_content);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Schedule schedule = mSchedule.get(i);
        if (schedule.finish.matches("1"))
            holder.checkbox.setChecked(true);
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mSchedule.get(i).finish="1";
                    Toast.makeText(mContext, "checked", Toast.LENGTH_SHORT).show();
                }

                else {
                    mSchedule.get(i).finish="0";
                    Toast.makeText(mContext, "unchecked", Toast.LENGTH_SHORT).show();
                }

                dbSchedule.openWriteLink();
                dbSchedule.update(mSchedule.get(i));
                dbSchedule.closeLink();

            }
        });
        holder.daily_agenda_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditScheduleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("entrance", 1);
                bundle.putLong("id", schedule.id);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        holder.daily_agenda_content.setOnLongClickListener(new View.OnLongClickListener() {
            DB_Schedule db_schedule = DB_Schedule.getInstance(mContext,DB_Schedule.DB_VERSION);
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提示：");
                builder.setMessage("确认删除？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db_schedule.openWriteLink();
                        db_schedule.delete("_id = " + schedule.id);
                        db_schedule.closeLink();
                        if (mfragment != null) {
                            try {
                                mfragment.paint();
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                return false;
            }
        });
        holder.name.setText(schedule.content);
        holder.place.setText(schedule.position);
        holder.start_time.setText(schedule.start_time);
        holder.end_time.setText(schedule.end_time);

        return view;
    }

    public static final class ViewHolder{
        public CheckBox checkbox;
        public TextView name;
        public TextView place;
        public TextView start_time;
        public TextView end_time;
        public ImageButton tomato;
        public LinearLayout daily_agenda_content;
    }
}
