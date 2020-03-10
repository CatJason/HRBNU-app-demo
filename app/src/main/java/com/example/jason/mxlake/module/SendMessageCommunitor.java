package com.example.jason.mxlake.module;

import android.graphics.Bitmap;

/**
 * 用于fragment传递事件给activity
 */
public interface SendMessageCommunitor {
    /**
     * 从fragment发送消息
     *
     * @param msg 消息内容
     */
    void sendMessage(String msg,String downFileName);
    void sendMessage(String newid , String time, String usrName, String title, String tag1, String tag2, String tag3, Bitmap bitmap);
}