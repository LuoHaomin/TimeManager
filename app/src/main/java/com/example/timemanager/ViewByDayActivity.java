package com.example.timemanager;

import static com.example.timemanager.R.layout.activity_view_by_day;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.ComponentDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.timemanager.adapter.DailyAdapter;
import com.example.timemanager.bean.Schedule;
import com.example.timemanager.database.DailySchedule;

import java.util.Calendar;
import java.util.List;

public class ViewByDayActivity extends AppCompatActivity {
    private final static String TAG = "ListViewByDayActivity";
    private ListView lv_daily_sub_plan;

    Bundle bundle;
    Calendar calendar=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_view_by_day);

        bundle = getIntent().getExtras();
        assert bundle != null;
        calendar.set(bundle.getInt("year"),
                bundle.getInt("monthOfYear"),
                bundle.getInt("dayOfMonth"));

        ListView daily_subplan=findViewById(R.id.daily_subplan);
        Toolbar title=findViewById(R.id.navigation_bar_in_Frag);
        setSupportActionBar(title);
        title.setBackgroundResource(R.color.gray);
        title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(view -> {
            finish();
        });
        //获得当日数据
        DailySchedule dailySchedule=new DailySchedule(this,1, calendar);
        List<Schedule> schedules = dailySchedule.getScheduleList();

        daily_subplan.setAdapter(new DailyAdapter(this, schedules));
        daily_subplan.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Schedule sch = schedules.get(i);
            Bundle bundle = new Bundle();
            bundle.putInt("entrance",1);
            bundle.putLong("id", sch.id);
            Intent intent = new Intent(ViewByDayActivity.this,
                    EditScheduleActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        });

        Button self_comment = findViewById(R.id.self_comment);
        self_comment.setOnClickListener(view -> {
            Intent intent = new Intent(ViewByDayActivity.this, SelfComment.class);
            startActivity(intent);
        });

        Button add_plan = findViewById(R.id.add_plan);
        add_plan.setOnClickListener(view -> {
            Intent intent = new Intent(ViewByDayActivity.this, EditScheduleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("entrance",0);
            bundle.putInt("year", calendar.get(Calendar.YEAR));
            bundle.putInt("monthOfYear", calendar.get(Calendar.MONTH));
            bundle.putInt("dayOfMonth",calendar.get(Calendar.DAY_OF_MONTH));
            intent.putExtras(bundle);
            startActivity(intent);
        });

        Button school_bus = findViewById(R.id.school_bus);
        school_bus.setOnClickListener(view -> {
//            ComponentDialog
        });
    }

}