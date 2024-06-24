package cn.edu.ustc.timemanager.fragment;

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

import cn.edu.ustc.timemanager.activity.CreatePlanActivity;
import cn.edu.ustc.timemanager.bean.Plan;

import com.example.timemanager.R;
import cn.edu.ustc.timemanager.adapter.PlanAdapter;
import cn.edu.ustc.timemanager.database.DB_Plan;

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

    //start of a fragment's lifcycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_plan,container,false);

        //click the button and jump
        Button create_btn =view.findViewById(R.id.CreatePlan);
        create_btn.setOnClickListener(view1 -> {
            Intent intent=new Intent(getActivity(), CreatePlanActivity.class);
            Bundle bundle=new Bundle();
            bundle.putBoolean("is_creating",true);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        Button download_btn = view.findViewById(R.id.dragDown);
        download_btn.setOnClickListener(view12 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("<开发中>");
            builder.setMessage("这个软件计划支持同步课表、二课项目、运动场馆等信息，敬请期待！");
            builder.setPositiveButton(R.string.IKnow, (dialogInterface, i) -> {});
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        });

        repaint();

        Button refresh = view.findViewById(R.id.update);
        refresh.setOnClickListener(view1 -> {
            repaint();
            Toast.makeText(getActivity(),"已更新",Toast.LENGTH_SHORT).show();
        });


        //删除标签
        Button remove = view.findViewById(R.id.remove);
        remove.setOnClickListener(view1 -> {
            List<Integer> choice=new ArrayList<>();
            String[] strings=tagss.toArray(new String[tagss.size()]);
            boolean init_choice[]=new boolean[tagss.size()];
            AlertDialog dialog =new AlertDialog.Builder(getActivity())
                    .setTitle("删除标签")
                    .setMultiChoiceItems(strings, init_choice, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            if(b){
                                choice.add(i);
                            }
                            else {
                                choice.remove(i);
                            }
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int w) {

                            SharedPreferences tag_in_share = getActivity().getSharedPreferences("tag",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor =tag_in_share.edit();

                            String st="";
                            int num=0;
                            for(int i=0;i<tagss.size();i++){
                                if(!choice.contains(i)){
                                    num++;
                                    st+=tagss.get(i)+"|";
                                }
                                else{
                                    db_plan=DB_Plan.getInstance(getActivity(),1);
                                    db_plan.openWriteLink();
                                    db_plan.delete(String.format("tag = '%s'",tagss.get(i)));
                                    db_plan.closeLink();
                                }
                            }
                            editor.putInt("num",num);
                            editor.putString("tag",st);
                            editor.commit();
                            repaint();
                        }
                    })
                    .create();
            dialog.show();

//            db_plan=DB_Plan.getInstance(getActivity(),1);
//            db_plan.openWriteLink();
//            db_plan.deleteAll();
//            db_plan.closeLink();
//            SharedPreferences share =getActivity().getSharedPreferences("tag",Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = share.edit();
//            editor.putString("tag","学习|工作|个人|二课|");
//            editor.putInt("num",4);
//            editor.commit();
//            repaint();
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
            for(int i=0;i<tagss.size();i++){
                expandableListView.expandGroup(i);
            }
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    Plan pl=planList.get(i).get(i1);
                    Bundle bundle=new Bundle();
                    bundle.putBoolean("is_creating",false);
                    bundle.putLong("id",pl.id);
                    Intent intent = new Intent(getActivity(), CreatePlanActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return true;
                }
            });
        }
    }

}