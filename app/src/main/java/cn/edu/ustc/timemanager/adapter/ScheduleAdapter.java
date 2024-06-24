package cn.edu.ustc.timemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timemanager.R;

import cn.edu.ustc.timemanager.bean.Schedule;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter {
    private List<Schedule> mlist;
    Context mcontext;

    public ScheduleAdapter(Context context,List<Schedule> list){
        mcontext=context;
        mlist=list;
    }

    //get num from database and rewrite it to list
    @Override
    public int getCount() {
        return mlist.size();
    }
    @Override
    public Object getItem(int i){
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        ViewHolder holder;
        if (convertview==null){
            holder=new ViewHolder();
            convertview = LayoutInflater.from(mcontext).inflate(R.layout.schedule_in_plan,null);
            holder.content=convertview.findViewById(R.id.content);
            holder.mode=convertview.findViewById(R.id.mode);
            convertview.setTag(holder);
        }
        else {
            holder=(ViewHolder) convertview.getTag();
        }
        Schedule schedule = mlist.get(position);

        holder.content.setText(schedule.content);
        //TODO:字符串处理
        holder.mode.setText(schedule.repeat_mode+" "+schedule.repeat_time);
        return convertview;
    }

    public final class ViewHolder{
        public TextView content;
        public TextView mode;
    }


}
