package cn.edu.ustc.timemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timemanager.R;

import cn.edu.ustc.timemanager.bean.Schedule;

import java.util.List;

public class SelfCommentAdapter extends BaseAdapter {

    private Context mContext;

    private List<Schedule> mSchedule;

    public SelfCommentAdapter(Context context, List<Schedule> scheduleList){
        mContext = context;
        mSchedule = scheduleList;
    }

    @Override
    public int getCount() {
        return mSchedule.size();
    }

    @Override
    public Object getItem(int i) {
        return mSchedule.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public  final class ViewHolder{
        public TextView tags;
        public EditText content;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.content_in_self_comment, null);
            holder.tags = view.findViewById(R.id.tag);
            holder.content = view.findViewById(R.id.tag_content);
            view.setTag(holder);//TODO:什么是将视图持有者保存到转换视图当中？
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Schedule schedule = mSchedule.get(position);
        holder.tags.setText(schedule.content);
        //TODO:这里的tags展示的是已有的所有标签

        return view;
    }
}
