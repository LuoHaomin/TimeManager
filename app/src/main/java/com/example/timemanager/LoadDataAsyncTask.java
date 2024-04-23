package com.example.timemanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoadDataAsyncTask extends AsyncTask<String,Void,String> {
    Context context;
    onGetNetDataListener listener;
    ProgressDialog dialog;
    boolean flag=false;
    private void initDialog(){
        dialog=new ProgressDialog(context);
        dialog.setTitle("提示信息");
        dialog.setMessage("正在加载中....");
    }
    public LoadDataAsyncTask(Context context, onGetNetDataListener listener,boolean flag) {
        this.context = context;
        this.listener = listener;
        this.flag=flag;
        initDialog();
    }

    //获取网络数据接口
    public interface onGetNetDataListener{
        public void onSucess(String json);
    }

    //运行在子线程中,进行耗时操作等逻辑
    @Override
    protected String doInBackground(String... params) {
        String json=HttpsUtils.getJSON(params[0]);
        return json;
    }
    //运行主线程中,通常用来进行控件的初始化
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(flag){
            dialog.show();
        }
    }
    //运行在主线程中,用来获取doInBackground的返回数据,还可以进行控件更新
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(flag){
            dialog.dismiss();   //返回数据了就要取消提示
        }
        listener.onSucess(s);
    }
}

