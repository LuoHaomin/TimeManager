package cn.edu.ustc.timemanager.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.timemanager.R;

public class EditSelfCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_self_comment);

        ImageView ai_xh = findViewById(R.id.ai_xh);
        ai_xh.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("开发中");
            builder.setMessage("由讯飞星火AI辅助,提供自动生成的周程评价报告");
            builder.setNegativeButton("知道了",null);
            builder.create().show();
        });

        Button commit_button = findViewById(R.id.commit_button);
        commit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                todo:接入数据库存储self_comment
//                Intent intent = new Intent(this, MainActivity.class);
//                Bundle bundle = new Bundle();
//                intent.putExtra("entrance", 4);
//                startActivity(intent);
                finish();
            }
        });

        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示：");
            builder.setMessage("确认退出？");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNegativeButton("取消",null);
            builder.create().show();
        });
    }


//    TODO:接入讯飞星火
    //配对接口
    public static String getParnterURL(){
        String url="接口路径";
        return url;
    }

    /**
     * 加载网络数据
     */
    //创建自定义异步任务对象
//    LoadDataAsyncTask task=new LoadDataAsyncTask(this,this,true);
//    //执行异步任务
//        task("api路径");
}