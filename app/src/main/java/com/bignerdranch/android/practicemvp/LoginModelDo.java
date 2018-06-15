package com.bignerdranch.android.practicemvp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import android.os.Handler;
import android.util.Log;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * M层
 */

public class LoginModelDo implements LoginModel {

    private HashMap<String, String> params;

    public static String sendHttpPost(final String urls, final Map<String, String> map) {
        byte[] data = getRequestData(map, "utf-8").toString().getBytes();//获得请求体
        try {
            URL url = null;
            url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);     //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                String resultData = null;      //存储处理结果
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] data1 = new byte[1024];
                int len = 0;
                try {
                    while ((len = inputStream.read(data1)) != -1) {
                        byteArrayOutputStream.write(data1, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                resultData = new String(byteArrayOutputStream.toByteArray());
                return resultData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Function  :   封装请求体信息
     * Param     :   params请求体内容，encode编码格式
     */
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    /*
     * Function  :   处理服务器的响应结果（将输入流转化成字符串）
     * Param     :   inputStream服务器的响应输入流
     */
//    public static String dealResponseResult(InputStream inputStream) {
//        String resultData = null;      //存储处理结果
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] data = new byte[1024];
//        int len = 0;
//        try {
//            while((len = inputStream.read(data)) != -1) {
//                byteArrayOutputStream.write(data, 0, len);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        resultData = new String(byteArrayOutputStream.toByteArray());
//        return resultData;
//    }

    @Override
    public void login(final String schoolID, final String cardID, final LoginFinished listener) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    params = new HashMap<String, String>();
                    params.put("stuNum", schoolID);
                    params.put("idNum", cardID);
                    JSONObject jsonObject = new JSONObject(sendHttpPost("https://wx.idsbllp.cn/api/verify", params));
                    int status = jsonObject.getInt("status");
                    final String info = jsonObject.getString("info");
                    if (status == 200) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String stuNum1 = data.getString("stuNum");
                        String idNum1 = data.getString("idNum");
                        Log.d(TAG, "onFinish: " + stuNum1 + idNum1);
                        if (schoolID.equals(stuNum1) && cardID.equals(idNum1)) {
                            listener.onSuccess();
                        }
                    } else {
                        listener.onFailure();
                    }
                } catch (Exception e) {
                    listener.onFailure();
                    e.printStackTrace();
                }
            }
        });
    }
}
