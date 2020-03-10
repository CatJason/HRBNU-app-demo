package com.example.jason.mxlake.otheractivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jason.mxlake.R;


/**
 * Created by Jay on 2015/10/25 0025.
 */
public class ClockActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private ImageView shutdown;
    private ImageView cat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock_musicplay);
        mediaPlayer = mediaPlayer.create(this, R.raw.pig);
        shutdown = (ImageView)findViewById(R.id.c_shutdown);



        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();


        cat = (ImageView)findViewById(R.id.cat_textback);
        LinearLayout.LayoutParams paraCat =  (LinearLayout.LayoutParams) cat.getLayoutParams();
        paraCat.height = width;
        cat.setLayoutParams(paraCat);


        mediaPlayer.start();
        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();
                ClockActivity.this.finish();


            }
        });
    }
}
