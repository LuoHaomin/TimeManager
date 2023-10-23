package com.example.timemanager.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.timemanager.bean.Schedule;
import com.example.timemanager.database.DB_Plan;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment {

    List<String> tagss=new ArrayList<>();
    private DB_Plan db_plan;

    //SharedPreferences tags= getActivity().getSharedPreferences("tags", Context.MODE_PRIVATE);
    //SharedPreferences.Editor editor=tags.edit();
    List<List<Plan>> planList=new ArrayList<>();

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

        return new PlanFragment();
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


/*

        *
        *
        *db_plan=DB_Plan.getInstance(getActivity(),1);
        Plan example=new Plan();
        db_plan.insert(example);
db_plan.openWriteLink();
        planList.add(db_plan.query("_id=*"));
        tagss.add("未分类");
        *
        **/



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
        /*
        tagss.add("none");tagss.add("none");tagss.add("none");
        Schedule ex=new Schedule();
        Plan example=new Plan();
        example.schedules.add(ex);example.schedules.add(ex);example.schedules.add(ex);
        example.breakdowns.add(ex);example.breakdowns.add(ex);example.breakdowns.add(ex);
        List<Plan> exp= new ArrayList<>();
        exp.add(example);exp.add(example);exp.add(example);
        planList.add(exp);planList.add(exp);planList.add(exp);planList.add(exp);*/
        if(tagss.size()!=0 && planList.size()!=0){
            ExpandableListView expandableListView =view.findViewById(R.id.plans);
            expandableListView.setAdapter(new PlanAdapter(getActivity(),tagss,planList));
        }

        return view;
    }


}