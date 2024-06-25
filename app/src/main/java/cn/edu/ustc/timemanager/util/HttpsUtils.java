package cn.edu.ustc.timemanager.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpsUtils {
    //获取网络数据
    public static String getJSON(String path){
        String json="";
        try {
            //将数据转为url对象
            URL url= new URL(path);
            //获取网络连接对象
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            //开始连接
            conn.connect();
            //读取输入流内容
            InputStream is=conn.getInputStream();
            //读取流
            int hasRead = 0;
            byte[]buf = new byte[1024];
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            //循环读取
            while (true){
                hasRead=is.read(buf);
                if(hasRead==-1){
                    break;
                }
                bos.write(buf,0,hasRead);
            }
            is.close();
            json=bos.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  json;
    }
}

