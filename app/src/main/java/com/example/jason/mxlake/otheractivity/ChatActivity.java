package com.example.jason.mxlake.otheractivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.jason.mxlake.R;
import com.example.jason.mxlake.service.BitmapService;
import com.example.jason.mxlake.service.ServletService;
import org.json.JSONArray;
import org.json.JSONObject;
public class ChatActivity extends Activity {

    private String discussId;
    private String serverPath = "http://192.168.56.1:8080/wangzhijun/file/";;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discuss_club);
        //通过Activity.getIntent()获取当前页面接收到的Intent。
        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        discussId = intent.getStringExtra("discussid");
        Log.i("discussID2",discussId);
        new chatThread(discussId+"").start();
    }

    private final class chatHandler extends Handler {
        @SuppressLint("WrongConstant")
        public void handleMessage(Message msg) {
            String time = msg.getData().getString("time");
            String name = msg.getData().getString("name");
            String content = msg.getData().getString("content");
            Bitmap avatar = (Bitmap) msg.obj;
            switch (msg.what) {
                case 0:
                    LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
                    // 获取需要被添加控件的布局
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ly_discuss_club);
                    // 获取需要添加的布局
                    RelativeLayout chatlayout = (RelativeLayout) inflater.inflate(
                            R.layout.chat_left, null).findViewById(R.id.ly_chat_left);
                    ImageView chatAvatar = (ImageView) chatlayout.findViewById(R.id.chat_left_avatar);
                    TextView chatName = (TextView) chatlayout.findViewById(R.id.chat_left_name);
                    TextView chatTime = (TextView) chatlayout.findViewById(R.id.chat_left_time);
                    TextView chatContent = (TextView) chatlayout.findViewById(R.id.chat_left_content);
                    chatAvatar.setImageBitmap(avatar);
                    chatName.setText(name);
                    chatTime.setText(time);
                    chatContent.setText(content);
                    linearLayout.addView(chatlayout);
                    break;
                case 1:
                    LayoutInflater inflaterMe = LayoutInflater.from(ChatActivity.this);
                    // 获取需要被添加控件的布局
                    LinearLayout linearLayoutMe = (LinearLayout)findViewById(R.id.ly_discuss_club);
                    // 获取需要添加的布局
                    RelativeLayout chatlayoutMe = (RelativeLayout) inflaterMe.inflate(
                            R.layout.chat_right, null).findViewById(R.id.ly_chat_right);
                    ImageView chatAvatarMe = (ImageView) chatlayoutMe.findViewById(R.id.chat_right_avatar);
                    TextView chatNameMe = (TextView) chatlayoutMe.findViewById(R.id.chat_right_name);
                    TextView chatTimeMe = (TextView) chatlayoutMe.findViewById(R.id.chat_right_time);
                    TextView chatContentMe = (TextView) chatlayoutMe.findViewById(R.id.chat_right_content);
                    chatAvatarMe.setImageBitmap(avatar);
                    chatNameMe.setText(name);
                    chatTimeMe.setText(time);
                    chatContentMe.setText(content);
                    linearLayoutMe.addView(chatlayoutMe);
                    break;
                default:
                    break;
            }
        }
    }

    public class chatThread extends Thread {
        Handler discusshandler = new chatHandler();
        String discussId;
        public chatThread(String discussId) {
            this.discussId = discussId;
        }
        @Override
        public void run() {

            try {
                Log.i("servicestart",discussId);
                JSONArray jsonArray = new JSONArray(ServletService.listByPost(discussId,"chat"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String flag = jsonObject.getString("flag");
                    String time = jsonObject.getString("time");
                    String name = jsonObject.getString("name");
                    String content = jsonObject.getString("content");
                    String avatar = jsonObject.getString("avatar");
                    Message msg = new Message();
                    if(Integer.parseInt(flag)==0){
                        msg.obj = BitmapService.getHttpBitmap(serverPath + avatar);
                        msg.what = 0;
                    }else{
                        msg.obj = BitmapService.getHttpBitmap(serverPath + avatar);
                        msg.what = 1;
                    }
                    msg.getData().putString("time", time);
                    msg.getData().putString("name", name);
                    msg.getData().putString("content", content);

                    discusshandler.sendMessage(msg);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

/*    public class chatThread extends Thread {
        Handler discusshandler = new chatHandler();
        String discussId;
        public chatThread(String discussId) {
            this.discussId = discussId;
        }
        @Override
        public void run() {

            try {
                Log.i("servicestart",discussId);

                 = BitmapService.getHttpBitmap(serverPath + avatar);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 */
}
