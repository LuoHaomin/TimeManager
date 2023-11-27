package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class ViewByWeekActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_week);

        Toolbar title=findViewById(R.id.title_in_view);
        setSupportActionBar(title);
        title.setBackgroundResource(R.color.gray);
        title.setTitle("新建计划");
        title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(view -> {
            finish();
            Intent intent=new Intent(this, MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("PresentPage",0);
            intent.putExtras(bundle);
            startActivity(intent);

        });
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