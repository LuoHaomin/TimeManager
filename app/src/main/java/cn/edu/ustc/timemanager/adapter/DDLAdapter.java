package cn.edu.ustc.timemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.timemanager.R;

import cn.edu.ustc.timemanager.bean.Schedule;

import java.util.List;

public class DDLAdapter extends BaseAdapter {
    private final Context mContext;
    private List<Schedule> mSchedule;

    public DDLAdapter(Context context, List<Schedule> schedule_list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.ddl_item, null);
            holder.name = view.findViewById(R.id.name);
            holder.countdown = view.findViewById(R.id.count);
            view.setTag(holder);// 将视图持有者保存到转换视图当中
        }
        else{// 转换视图非空
            // 从转换视图中获取之前保存的视图持有者
            holder = (ViewHolder) view.getTag();
        }

        //TODO:rewrite sources in String;see in ？？Adapter
        Schedule schedule = mSchedule.get(position);
        holder.name.setText(schedule.root+">>"+schedule.content);
        holder.countdown.setText(schedule.code);

        //TODO:choose enough params to submit to edit_plan activity
//        holder.ddl_show = view.findViewById(R.id.ddl_show);
//        holder.ddl_show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, EditPlanActivity.class);
////                Bundle bundle = new Bundle();
//                //todo:params here
////                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//            }
//        });
        return view;
    }

    // 定义一个视图持有者，以便重用列表项的视图资源
    public final class ViewHolder{
        public TextView name;
        public TextView countdown;
        public LinearLayout ddl_show;
    }
}
