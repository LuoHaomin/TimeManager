package com.example.timemanager.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.timemanager.CreatePlanActivity;
import com.example.timemanager.R;
import com.example.timemanager.adapter.PlanAdapter;
import com.example.timemanager.bean.Plan;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment {

    List<String> tagss;
    List<List<Plan>> planList;

    public PlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentA.
     */

    public static PlanFragment newInstance() {
        PlanFragment fragment = new PlanFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_plan,container,false);
        Button create_btn =view.findViewById(R.id.CreatePlan);
        create_btn.setOnClickListener(view1 -> {
            Intent intent=new Intent(getActivity(), CreatePlanActivity.class);
            startActivity(intent);
        });


        Button download_btn = view.findViewById(R.id.dragDown);
        download_btn.setOnClickListener(view12 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("<开发中>");
            builder.setMessage("这个软件计划支持同步课表、二课项目、运动场馆等信息，敬请期待！");
            builder.setPositiveButton("我知道了", (dialogInterface, i) -> {

            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        });

        ExpandableListView expandableListView =view.findViewById(R.id.plans);
        expandableListView.setAdapter(new PlanAdapter(getActivity(),tagss,planList));
        return view;
    }

}