package com.example.jason.mxlake.service;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class ServletService {

    private static String address = "http://192.168.56.1:8080/wangzhijun/";

    public static String loginByPost(String useraccount,String userpassword){
        try {
            String path = address +"login";
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            String data = "useraccount="+URLEncoder.encode(useraccount)+"&userpassword="
                    +URLEncoder.encode(userpassword);
            System.out.println(data);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Content-length", data.length()+"");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            int code = conn.getResponseCode();
            System.out.println(code);
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text = readInputStream(is);
                return text;
            }else {
                return null;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("111111");
        } catch (ProtocolException e) {
            System.out.println("222222");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("33333");
            e.printStackTrace();
        }
        return null;
    }

    public static String listByPost(String count,String flag){
        try {
            String path = address+flag;
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            String data = "count=" +URLEncoder.encode(count);
            Log.i("servicestart",flag+":"+data);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Content-length", data.length()+"");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            int code = conn.getResponseCode();
            System.out.println(code);
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text = readInputStream(is);
                return text;
            }else {
                return null;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("111111");
        } catch (ProtocolException e) {
            System.out.println("222222");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("33333");
            e.printStackTrace();
        }
        return null;
    }

    public static String fileByPost(String count,String flagzero,String flag) {
        try {
            String path = address+flag;
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            String data = "count="+URLEncoder.encode(count)+"&flag="+URLEncoder.encode(flagzero);
            System.out.println(data);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Content-length", data.length()+"");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            int code = conn.getResponseCode();
            System.out.println(code);
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text = readInputStream(is);
                return text;
            }else {
                return null;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("111111");
        } catch (ProtocolException e) {
            System.out.println("222222");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("33333");
            e.printStackTrace();
        }
        return null;
    }

    public static String sendNewsByPost(String content,String account,String title,String tag1,String tag2,String tag3,String place) {
        try {
            String path = address+"sendNews";
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            String data = "content="+URLEncoder.encode(content)
                    +"&account="+URLEncoder.encode(account)
                    +"&title="+URLEncoder.encode(title)
                    +"&tag1="+URLEncoder.encode(tag1)
                    +"&tag2="+URLEncoder.encode(tag2)
                    +"&tag3="+URLEncoder.encode(tag3)
                    +"&place="+URLEncoder.encode(place);
            System.out.println(data);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Content-length", data.length()+"");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            int code = conn.getResponseCode();
            System.out.println(code);
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text = readInputStream(is);
                return text;
            }else {
                return null;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("111111");
        } catch (ProtocolException e) {
            System.out.println("222222");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("33333");
            e.printStackTrace();
        }
        return null;
    }

    public static String signByPost(String useraccount,String userpassword,String username){
        try {
            String path = address +"sign";
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            String data = "useraccount="+URLEncoder.encode(useraccount)+"&userpassword="
                    +URLEncoder.encode(userpassword)+"&username="
                    +URLEncoder.encode(username);
            System.out.println(data);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Content-length", data.length()+"");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            int code = conn.getResponseCode();
            System.out.println(code);
            if (code == 200) {

                InputStream is = conn.getInputStream();
                String text = readInputStream(is);
                return text;
            }else {
                return null;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("111111");
        } catch (ProtocolException e) {
            System.out.println("222222");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("33333");
            e.printStackTrace();
        }
        return null;
    }


    public static String readInputStream(InputStream is){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = -1;
        try {
            while ((len = is.read(b))!=-1) {
                baos.write(b, 0, len);
            }
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(baos.toByteArray());
    }
}