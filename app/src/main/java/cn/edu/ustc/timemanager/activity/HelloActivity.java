package cn.edu.ustc.timemanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.example.timemanager.R;

import cn.edu.ustc.timemanager.database.DB_Plan;
import cn.edu.ustc.timemanager.util.TagManeger;

import java.util.ArrayList;
import java.util.List;


public class HelloActivity extends AppCompatActivity {

    @Override
    /*
      创建欢迎界面
      @param savedInstanceState 保存的实例状态
     *                           用于恢复之前保存的状态
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        List<String> strings = new ArrayList<>();
        strings.add(getString(R.string.start_text));
        strings.add("逝者如斯夫，\n不舍昼夜");


        TagManeger.InitTag(this);

//        if(!checkNotificationPermission(this)){
//            requestPermission(this);
//            Notify("message");
//        }
//
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(Next, 1000);
    }

    private Runnable Next = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(HelloActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    /**
     * 检查通知权限
     * @param context 上下文
     * @return 是否有通知权限
     */
    public boolean checkNotificationPermission(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        return NotificationManagerCompat.from(context).areNotificationsEnabled() && manager != null && manager.getImportance() != NotificationManager.IMPORTANCE_NONE;
    }
    /**
     * 请求通知权限
     * @param context 上下文
     */
    public void requestPermission(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (!manager.areNotificationsEnabled()) {
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intent);
            }
            if (manager == null) {
                NotificationChannel channel = new NotificationChannel(getString(R.string.app_name), getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("");
                manager.createNotificationChannel(channel);
            }
        }
    }
    /**
     * 发送通知
     * @param message 通知内容
     */
    private void Notify(String message) {
        Intent clickIntent = new Intent(this, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this,
                R.string.app_name, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, getString(R.string.app_name));
        }
        builder.setSmallIcon(R.drawable.add_pic)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setContentTitle("测试用通知")
                .setContentText(message);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(R.string.app_name, notification);
    }

}
