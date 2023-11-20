package com.example.timemanager;

import static com.example.timemanager.R.layout.activity_view_by_day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.util.List;

public class ViewByDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_view_by_day);
        initRecyclerGrid();
    }

    private void initRecyclerGrid(){
        // 从布局文件中获取名叫daily_time_display的循环视图
        RecyclerView rv_staggered = findViewById(R.id.daily_time_display);
        // 创建一个垂直方向的瀑布流布局管理器（每行2列）
        GridLayoutManager manager = new GridLayoutManager(this,2);
        rv_staggered.setLayoutManager(manager);

        //RecyclerStagAdapter
    }
}