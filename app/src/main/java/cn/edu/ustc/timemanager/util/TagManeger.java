package cn.edu.ustc.timemanager.util;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.ustc.timemanager.database.DB_Plan;

public class TagManeger {
    public static void InitTag(Context context) {
        SharedPreferences tag_in_share = context.getSharedPreferences("tag", MODE_PRIVATE);
        SharedPreferences.Editor editor = tag_in_share.edit();
        String s = tag_in_share.getString("tag", "");
        int num = tag_in_share.getInt("num", 0);
        if (num == 0) {
            DB_Plan db_plan;
            db_plan = DB_Plan.getInstance(context, 1);
            db_plan.openWriteLink();
            db_plan.deleteAll();
            db_plan.closeLink();
            editor.putString("tag", "学习|工作|个人|二课|");
            editor.putInt("num", 4);
            editor.apply();
        }
    }
    public static void AddTag(Context context,String newTag){
        SharedPreferences tag_in_share = context.getSharedPreferences("tag",MODE_PRIVATE);
        SharedPreferences.Editor editor =tag_in_share.edit();
        String s = tag_in_share.getString("tag","");
        int num = tag_in_share.getInt("num",0);
        editor.putInt("num",num+1);
        editor.putString("tag",s+newTag+"|");
        editor.apply();
    }

    public static List<String> ReadTag(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("tag", MODE_PRIVATE);
        String tagString = sharedPreferences.getString("tag", "");
        int num = sharedPreferences.getInt("num", 0);
        String[] tagArray = tagString.split("\\|");
        return new ArrayList<>(Arrays.asList(tagArray).subList(0, num));
    }
}
