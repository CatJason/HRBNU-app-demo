package com.example.jason.mxlake.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapService {

    public static Bitmap getHttpBitmap(String picturePath) {

        Bitmap bitmap = null;
        try {

            HttpURLConnection conn = null;
            URL myFileUrl = new URL(picturePath);// 声明一个URL,注意——如果用百度首页实验，请使用https
            conn = (HttpURLConnection) myFileUrl.openConnection(); // 打开该URL连接
            conn.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
            conn.setConnectTimeout(8000); // 设置连接建立的超时时间
            conn.setReadTimeout(8000); // 设置网络报文收发超时时间
            InputStream bitmapin = conn.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
            bitmap = BitmapFactory.decodeStream(bitmapin);
            bitmapin.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
