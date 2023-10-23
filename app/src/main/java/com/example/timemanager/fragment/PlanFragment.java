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
import android.widget.Toast;

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
    View view;

    List<List<Plan>> planList=new ArrayList<>();

    public PlanFragment() {
        // Required empty public constructor
    }



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
        view=inflater.inflate(R.layout.fragment_plan,container,false);
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

        repaint();

        Button refresh = view.findViewById(R.id.update);
        refresh.setOnClickListener(view1 -> {
            repaint();
            Toast.makeText(getActivity(),"已更新",Toast.LENGTH_SHORT).show();
        });

        Button remove = view.findViewById(R.id.remove);
        remove.setOnClickListener(view1 -> {
            db_plan=DB_Plan.getInstance(getActivity(),1);
            db_plan.openWriteLink();
            db_plan.deleteAll();
            db_plan.closeLink();
            SharedPreferences share =getActivity().getSharedPreferences("tag",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();
            editor.putString("tag","学习|工作|个人|");
            editor.putInt("num",3);
            editor.commit();
            repaint();
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        repaint();
    }

    private void repaint(){

        int num =0;
        tagss=new ArrayList<>();
        planList=new ArrayList<>();

        SharedPreferences tags= getActivity().getSharedPreferences("tag", Context.MODE_PRIVATE);

        num = tags.getInt("num",0);
        String st = tags.getString("tag",""), s="";

        for(int i=0;i<num;i++){
            s=st.substring(0,st.indexOf('|'));
            tagss.add(s);
            st=st.substring(st.indexOf('|')+1);

        }
        db_plan =DB_Plan.getInstance(getActivity(),1);
        db_plan.openReadLink();

        for(int i=0;i<num;i++){
            planList.add(db_plan.query(String.format("tag = '%s'",tagss.get(i))));
        }
        db_plan.closeLink();


        if(tagss.size()!=0 && planList.size()!=0){
            ExpandableListView expandableListView =view.findViewById(R.id.plans);
            expandableListView.setAdapter(new PlanAdapter(getActivity(),tagss,planList));
        }
    }

}