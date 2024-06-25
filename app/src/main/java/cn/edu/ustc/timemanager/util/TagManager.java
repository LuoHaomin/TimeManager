package cn.edu.ustc.timemanager.util;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.ustc.timemanager.database.DB_Plan;

public class TagManager {
    private static TagManager instance;
    private SharedPreferences tag_in_share;
    private SharedPreferences.Editor editor;
    private final Context context;

    private TagManager(Context context) {
        tag_in_share = context.getSharedPreferences("tag", MODE_PRIVATE);
        editor = tag_in_share.edit();
        this.context = context;
    }

    public static synchronized TagManager getInstance(Context context) {
        if (instance == null) {
            instance = new TagManager(context);
        }
        return instance;
    }

    public void initTag() {
        int num = tag_in_share.getInt("num", 0);
        if (num == 0) {
            DB_Plan db_plan = DB_Plan.getInstance(context, 1);
            db_plan.openWriteLink();
            db_plan.deleteAll();
            db_plan.closeLink();
            editor.putString("tag", "学习|工作|个人|二课|");
            editor.putInt("num", 4);
            editor.apply();
        }
    }

    public void addTag(String newTag) {
        int num = tag_in_share.getInt("num", 0);
        editor.putInt("num", num + 1);
        String s = tag_in_share.getString("tag", "");
        editor.putString("tag", s + newTag + "|");
        editor.apply();
    }

    public List<String> readTag() {
        String tagString = tag_in_share.getString("tag", "");
        int num = tag_in_share.getInt("num", 0);
        String[] tagArray = tagString.split("\\|");
        return new ArrayList<>(Arrays.asList(tagArray).subList(0, num));
    }
}