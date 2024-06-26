package cn.edu.ustc.timemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.timemanager.R;

import cn.edu.ustc.timemanager.bean.Plan;

import java.util.List;

public class PlanAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private List<String> tags;
    private List<List<Plan>> planList;

    public PlanAdapter(Context ct,List<String> t,List<List<Plan>> list){
        context=ct;
        tags=t;
        planList=list;
    }
    @Override
    public int getGroupCount() {
        return tags.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return planList.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return planList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return planList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupHolder holder = null;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.plan_list_group,null);
            holder=new GroupHolder();
            holder.textView=view.findViewById(R.id.group_tag);
            view.setTag(holder);
        }else {
            holder=(GroupHolder) view.getTag();
        }
        if(b){

        }else {

        }
        holder.textView.setText(tags.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ItemHolder holder=null;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.plan_list_item,null);
            holder = new ItemHolder();
            holder.t0 =view.findViewById(R.id.t0);
            holder.ddl=view.findViewById(R.id.ddl_show);
            holder.t2 =view.findViewById(R.id.t2);
            holder.t4 =view.findViewById(R.id.t4);
            view.setTag(holder);
        }else {
            holder=(ItemHolder) view.getTag();
        }

        holder.t0.setText(planList.get(i).get(i1).content);

        holder.ddl.setText(planList.get(i).get(i1).end_time);

        String txt1="",txt2="";
        if(planList.get(i).get(i1).breakdowns.size()==0){
            txt1="(空)";
        }
        for (int j=0;j<planList.get(i).get(i1).breakdowns.size();j++){
            txt1 = txt1 + planList.get(i).get(i1).breakdowns.get(j).content +"    ("+ planList.get(i).get(i1).breakdowns.get(j).end_time+")";
            if(j!=planList.get(i).get(i1).breakdowns.size()-1){
                txt1+='\n';
            }
        }
        if(planList.get(i).get(i1).breakdowns.size()==0){
            txt1="(空)";
        }
        for (int j=0;j<planList.get(i).get(i1).schedules.size();j++){
            txt2 = txt2 + planList.get(i).get(i1).schedules.get(j).content ;
            if(j!=planList.get(i).get(i1).breakdowns.size()-1){
                txt2+='\n';
            }
        }
        if(planList.get(i).get(i1).schedules.size()==0){
            txt2="(空)";
        }
        holder.t2.setText(txt1);
        holder.t4.setText(txt2);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    class GroupHolder{
        public TextView textView;
    }
    class ItemHolder{
        public TextView t0;
        public TextView ddl;
        public TextView t2;
        public TextView t4;
    }
}
