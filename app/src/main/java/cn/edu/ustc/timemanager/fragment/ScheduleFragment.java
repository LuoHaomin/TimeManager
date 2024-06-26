package cn.edu.ustc.timemanager.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;

import cn.edu.ustc.timemanager.activity.EditScheduleActivity;
import cn.edu.ustc.timemanager.bean.Schedule;

import com.example.timemanager.R;

import cn.edu.ustc.timemanager.adapter.DDLAdapter;
import cn.edu.ustc.timemanager.adapter.HomePageScheduleAdapter;
import cn.edu.ustc.timemanager.database.DB_Schedule;
import cn.edu.ustc.timemanager.database.DailySchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class ScheduleFragment extends Fragment {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Calendar calendar = Calendar.getInstance();
    List<Schedule> schedules;
    ListView daily_agenda;
    ListView ddl;
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
        daily_agenda = view.findViewById(R.id.daily_agenda);
        ddl = view.findViewById(R.id.plan);
        try {
            paint();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendarView.setOnDateChangeListener((calendarView1, year, month, day) -> {
            calendar.set(year, month, day);
//            Toast.makeText(getActivity(), format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
//            schedules = new DailySchedule(ScheduleFragment.this.getActivity(), DB_Schedule.DB_VERSION, calendar).getScheduleList();
//            daily_agenda.setAdapter(new HomePageScheduleAdapter(getActivity(), schedules,this));
            try {
                paint();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });


        Button add_plan = view.findViewById(R.id.add_plan);
        add_plan.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), EditScheduleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("entrance", 0);
            bundle.putInt("year", calendar.get(Calendar.YEAR));
            bundle.putInt("month", calendar.get(Calendar.MONTH));
            bundle.putInt("dayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
            try {
                paint();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
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
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh".equals(msg)){
                    try {
                        paint();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
        return view;
    }


    public void paint() throws ParseException {
        DailySchedule dailySchedule_1 = new DailySchedule(getActivity(), DB_Schedule.DB_VERSION, calendar);
        schedules=dailySchedule_1.getScheduleList();
        daily_agenda.setAdapter(new HomePageScheduleAdapter(getActivity(), schedules,this));
        setListViewHeight(daily_agenda);

        ddl.setAdapter(new DDLAdapter(getActivity(),dailySchedule_1.getDDLsList()));
        setListViewHeight(ddl);
    }
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null ) {
            return ;
        }
        int totalHeight = 0 ;
        for ( int i = 0 ; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null , listView);
            listItem.measure( 1 , 1 );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1 )) + listView.getPaddingTop() + listView.getPaddingBottom();
        listView.setLayoutParams(params);
    }

//    public class MFragment extends Fragment {
//        Button button;
//        TextView text;
//        Bundle bundle;
//        String message;
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//            View contentView = inflater.inflate(R.layout.fragment_schedule, container, false);
////            bundle = this.getArguments();
////            设置布局文件
//            return contentView;
//        }
//    }
}