package com.example.timemanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.timemanager.R;
import com.example.timemanager.adapter.MonthAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    View view;
    private Calendar month;


    public MonthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MonthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthFragment newInstance(String param1) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        month = Calendar.getInstance();
        view=inflater.inflate(R.layout.fragment_month, container, false);
        GridView hint=view.findViewById(R.id.星期);

        List<String> list = new ArrayList<>();
        list.add("星期日");
        list.add("星期一");
        list.add("星期二");
        list.add("星期三");
        list.add("星期四");
        list.add("星期五");
        list.add("星期六");
        hint.setAdapter(new ArrayAdapter<>(requireActivity(), R.layout.spinner_arraylist, list));

        try {
            Paint();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Button next = view.findViewById(R.id.next);
        next.setOnClickListener((view1 -> {
            month.add(Calendar.MONTH,1);
            try {
                Paint();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }));

        Button last = view.findViewById(R.id.last);
        last.setOnClickListener(view1 -> {
            month.add(Calendar.MONTH,-1);
            try {
                Paint();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        return view;
    }

    private void Paint() throws ParseException {
        TextView textView = view.findViewById(R.id.提示);
        textView.setText(String.format("%d年%d月",month.get(Calendar.YEAR),month.get(Calendar.MONTH)+1));
        GridView month_grid = view.findViewById(R.id.month_grid);
        MonthAdapter adapter = new MonthAdapter(getActivity(),month,month.get(Calendar.MONTH)==Calendar.getInstance().get(Calendar.MONTH));
        month_grid.setAdapter(adapter);
        month_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(requireContext(),String.format("%d-%d",month.get(Calendar.MONTH)+1,i-adapter.FirstDay+1),Toast.LENGTH_SHORT).show();
            }
        });
    }

}