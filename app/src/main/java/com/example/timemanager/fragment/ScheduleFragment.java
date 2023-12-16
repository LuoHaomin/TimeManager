package com.example.timemanager.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.timemanager.CreatePlanActivity;
import com.example.timemanager.EditScheduleActivity;
import com.example.timemanager.R;
import com.example.timemanager.ViewByDayActivity;
import com.example.timemanager.ViewByMonthActivity;
import com.example.timemanager.ViewByWeekActivity;
import com.example.timemanager.adapter.HomePageScheduleAdapter;
import com.example.timemanager.bean.Schedule;
import com.example.timemanager.database.DB_Schedule;
import com.example.timemanager.database.DailySchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ScheduleFragment extends Fragment {

    public ListView lv_in_schedule_fragment;
    Calendar calendar = Calendar.getInstance();
    List<Schedule> schedules;
    public ScheduleFragment() {
        // Required empty public constructor
    }


    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_schedule, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendar);
        ListView daily_agenda = view.findViewById(R.id.daily_agenda);

        DailySchedule dailySchedule = new DailySchedule(this.getActivity(), 1, calendar);
        schedules=dailySchedule.getScheduleList();

        calendarView.setOnDateChangeListener((calendarView1, year, month, day) -> {
            calendar.set(year, month, day);
            schedules = new DailySchedule(ScheduleFragment.this.getActivity(), DB_Schedule.DB_VERSION, calendar).getScheduleList();
            daily_agenda.setAdapter(new HomePageScheduleAdapter(this.getActivity(), schedules));
        });

        Button add_plan = view.findViewById(R.id.add_plan);
        add_plan.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), EditScheduleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("year", calendar.get(Calendar.YEAR));
            bundle.putInt("month", calendar.get(Calendar.MONTH));
            bundle.putInt("dayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        });

//        Button toViewByWeek = view.findViewById(R.id.to_weekly_view);
//        toViewByWeek.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), ViewByWeekActivity.class));
//            }
//        });
//        Button toViewByMonth = view.findViewById(R.id.to_monthly_view);
//        toViewByMonth.setOnClickListener(view1 -> {
//            startActivity(new Intent(getActivity(), ViewByMonthActivity.class));
//        });
//
//        Button toViewByDay = view.findViewById(R.id.to_daily_view);
//        toViewByDay.setOnClickListener(view1 -> {
//            Calendar calendar = Calendar.getInstance();
//            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), null,
//                    calendar.get(Calendar.YEAR),
//                    calendar.get(Calendar.MONTH),
//                    calendar.get(Calendar.DAY_OF_MONTH));
//            datePickerDialog.show();
//
//            //确认按钮
//            datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view2 -> {
//                Calendar data=Calendar.getInstance();
//                //确认年月日
//                int year = datePickerDialog.getDatePicker().getYear();
//                int monthOfYear = datePickerDialog.getDatePicker().getMonth() + 1;
//                int dayOfMonth = datePickerDialog.getDatePicker().getDayOfMonth();
//                data.set(year,monthOfYear,dayOfMonth);
//                Bundle bundle = new Bundle();
//                bundle.putInt("year", year);
//                bundle.putInt("monthOfYear", monthOfYear);
//                bundle.putInt("dayOfMonth",dayOfMonth);
//                Intent intent = new Intent(getActivity(), ViewByDayActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                datePickerDialog.dismiss();
//            });
//        });
//        List<String> dayInWeek = new ArrayList<>();
//        dayInWeek.add("按日查看");
//        dayInWeek.add("周日");
//        dayInWeek.add("周一");
//        dayInWeek.add("周二");
//        dayInWeek.add("周三");
//        dayInWeek.add("周四");
//        dayInWeek.add("周五");
//        dayInWeek.add("周六");

//        Spinner dailyBar = view.findViewById(R.id.dailyBar);
//        dailyBar.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_arraylist, dayInWeek));

//        RecyclerView.LayoutManager layoutManager = new RecyclerView.LayoutManager() {
//            @Override
//            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
//                return null;
//            }
//        };
        return view;
    }


//    private void onClick(View view1) {
//        calendar.get();
//    }
}