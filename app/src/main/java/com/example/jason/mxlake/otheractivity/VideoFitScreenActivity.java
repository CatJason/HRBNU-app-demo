package com.example.jason.mxlake.otheractivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.jason.mxlake.R;

public class VideoFitScreenActivity extends AppCompatActivity {
    private String serverPath = "http://192.168.56.1:8080/wangzhijun/file/";
    private int videoPosition;
    private VideoView mVideo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.video_fitscreen);

        if( savedInstanceState != null ){
            videoPosition = savedInstanceState.getInt("videoPosition");

        }else{
            //通过Activity.getIntent()获取当前页面接收到的Intent。
            Intent intent =getIntent();
            //getXxxExtra方法获取Intent传递过来的数据
            videoPosition = intent.getIntExtra("videoPosition",videoPosition);
        }

        mVideo = (VideoView) findViewById(R.id.big_video);


        mVideo.setVideoPath(serverPath+"567.mp4");
        mVideo.seekTo(videoPosition+1000);
        mVideo.start();
    }


    /**
     * 屏幕旋转时调用此方法
     */
/*    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //newConfig.orientation获得当前屏幕状态是横向或者竖向
        //Configuration.ORIENTATION_PORTRAIT 表示竖向
        //Configuration.ORIENTATION_LANDSCAPE 表示横屏
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){

            Toast.makeText(VideoFitScreenActivity.this, "现在是竖屏", Toast.LENGTH_SHORT).show();
        }
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(VideoFitScreenActivity.this, "现在是横屏", Toast.LENGTH_SHORT).show();
        }


    }*/


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //横竖屏切换前调用，保存用户想要保存的数据，以下是样例
        videoPosition = mVideo.getCurrentPosition();
        mVideo.pause();
        outState.putInt("videoPosition",videoPosition);
    }

  /*  @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 屏幕切换完毕后调用用户存储的数据，以下为样例：
    }
*/

}
