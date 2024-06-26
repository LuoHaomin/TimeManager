package cn.edu.ustc.timemanager.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cn.edu.ustc.timemanager.fragment.MonthFragment;
import cn.edu.ustc.timemanager.fragment.PlanFragment;
import cn.edu.ustc.timemanager.fragment.ScheduleFragment;
import cn.edu.ustc.timemanager.fragment.EvaluationFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new ScheduleFragment();
        } else if (position==2) {
            return new PlanFragment();
        } else if (position==1) {
            return new MonthFragment();
        }else if(position==3){
            return new EvaluationFragment();
        }
        else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
