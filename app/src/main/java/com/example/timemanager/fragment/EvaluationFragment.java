package com.example.timemanager.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.timemanager.EditSelfCommentActivity;
import com.example.timemanager.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EvaluationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluationFragment extends Fragment {
    Calendar calendar = Calendar.getInstance();


    public EvaluationFragment() {
        // Required empty public constructor
    }

    public static EvaluationFragment newInstance(String param1, String param2) {
        EvaluationFragment fragment = new EvaluationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);

//        TextView Jan = view.findViewById(R.id.Jan);
//        Jan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), EditSelfCommentActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt( "WeekInYear", calendar.getWeekYear());
//                intent.putExtras(bundle);
//                //todo:extra params are needed here
//                getActivity().startActivity(intent);
//            }
//        });

        return view;
    }
}