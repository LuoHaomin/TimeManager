package com.example.timemanager.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.timemanager.bean.Schedule;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter {
    private List<Schedule> mlist;
    Context mcontext;

    public ScheduleAdapter(Context context,List<Schedule> list){
        mcontext=context;
        mlist=list;
    }

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
        //没写完；
        return null;
    }

    //newly added


}
