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

import com.example.timemanager.adapter.DDLAdapter;
import com.example.timemanager.adapter.DailyAdapter;
import com.example.timemanager.adapter.HomePageScheduleAdapter;
import com.example.timemanager.bean.Schedule;
import com.example.timemanager.database.DB_Schedule;
import com.example.timemanager.database.DailySchedule;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public class ViewByDayActivity extends AppCompatActivity {

    Bundle bundle;
    Calendar calendar=Calendar.getInstance();
    ListView daily_subplan;
    ListView daily_ddls;
    DailySchedule dailySchedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_view_by_day);

        bundle = getIntent().getExtras();
        assert bundle != null;
        calendar.set(bundle.getInt("year"),
                bundle.getInt("month"),
                bundle.getInt("dayOfMonth"));

        daily_subplan=findViewById(R.id.daily_subplan);
        Toolbar title=findViewById(R.id.navigation_bar_in_Frag);
        setSupportActionBar(title);
        title.setBackgroundResource(R.color.gray);
        title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(view -> {
            finish();
        });
        //获得当日数据
        dailySchedule=new DailySchedule(this,DB_Schedule.DB_VERSION, calendar);
        List<Schedule> schedules = dailySchedule.getScheduleList();

        daily_subplan.setAdapter(new HomePageScheduleAdapter(this, schedules));
        daily_subplan.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Schedule sch = schedules.get(i);
            Bundle bundle = new Bundle();
            bundle.putInt("entrance", 1);
            bundle.putLong("id", sch.id);
            Intent intent = new Intent(ViewByDayActivity.this,
                    EditScheduleActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        });
        daily_ddls = findViewById(R.id.daily_ddls);
        try {
            daily_ddls.setAdapter(new DDLAdapter(this,dailySchedule.getDDLsList()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Button self_comment = findViewById(R.id.self_comment);
        self_comment.setOnClickListener(view -> {
            Intent intent = new Intent(ViewByDayActivity.this, CheckSelfCommentActivity.class);
            startActivity(intent);
        });

        Button add_plan = findViewById(R.id.add_plan);
        add_plan.setOnClickListener(view -> {
            Intent intent = new Intent(ViewByDayActivity.this, EditScheduleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("entrance",0);
            bundle.putInt("year", calendar.get(Calendar.YEAR));
            bundle.putInt("monthOfYear", calendar.get(Calendar.MONTH));
            bundle.putInt("dayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));
            intent.putExtras(bundle);
            ViewByDayActivity.this.startActivity(intent);
        });

        Button school_bus = findViewById(R.id.school_bus);
        school_bus.setOnClickListener(view -> {
//            ComponentDialog
        });
    }
    @Override
    public void onResume() {

        super.onResume();
        List<Schedule> schedules = dailySchedule.getScheduleList();

        daily_subplan.setAdapter(new HomePageScheduleAdapter(this, schedules));
        daily_subplan.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Schedule sch = schedules.get(i);
            Bundle bundle = new Bundle();
            bundle.putInt("entrance", 1);
            bundle.putLong("id", sch.id);
            Intent intent = new Intent(ViewByDayActivity.this,
                    EditScheduleActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        });
        try {
            daily_ddls.setAdapter(new DDLAdapter(this,dailySchedule.getDDLsList()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}