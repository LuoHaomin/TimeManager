package com.example.timemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.timemanager.R;
import com.example.timemanager.bean.Schedule;

import java.util.List;

//TODO:delete this abstract
public abstract class WeeklyAdapter extends RecyclerView.Adapter<ViewHolder>
implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private final static String TAG = "RecyclerStagAdapter";
    private Context mContext;
    private List<Schedule> mSchedule;

    public WeeklyAdapter(Context context, List<Schedule> schedule){
        mContext = context;// 声明一个上下文对象
        mSchedule = schedule;
    }

    @Override
    // 获取列表项的个数
    public int getItemCount() {
        return mSchedule.size();
    }
//TODO：holder
//    public class ItemHolder extends RecyclerView.ViewHolder{
//        public LinearLayout
//    }
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType){
//        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_view_by_day, vg, false);
////        return new ItemHolder(v);
//    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

}
