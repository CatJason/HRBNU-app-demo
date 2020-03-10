package com.example.jason.mxlake.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.jason.mxlake.otheractivity.ChatActivity;
import com.example.jason.mxlake.otheractivity.SignActivity;
import com.example.jason.mxlake.service.BitmapService;
import com.example.jason.mxlake.service.ServletService;
import com.example.jason.mxlake.otheractivity.ClockActivity;
import com.example.jason.mxlake.R;
import com.example.jason.mxlake.downloader.DownloadProgressListener;
import com.example.jason.mxlake.downloader.FileDownloadered;
import com.example.jason.mxlake.main.view.PullToRefreshView;
import com.example.jason.mxlake.module.CampFragment;
import com.example.jason.mxlake.module.KnowledgeFragment;
import com.example.jason.mxlake.module.MyFragment;
import com.example.jason.mxlake.module.ScheduleFragment;
import com.example.jason.mxlake.module.SendMessageCommunitor;
import com.example.jason.mxlake.otheractivity.VideoFitScreenActivity;
import com.example.jason.mxlake.utils.DensityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.TimeZone;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, SendMessageCommunitor, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private AlertDialog dialog;
    private int[] num = new int[3];
    private String serverPath = "http://192.168.56.1:8080/wangzhijun/file/";
    PullToRefreshView mPullToRefreshView;
    private String UserNameNow;
    private String UserAccountNow;
    private Bitmap UserAvatarNow;
    final int dmScreenCount = 30;
    //Activity UI Object
    private LinearLayout ly_tab_home;
    private TextView tab_home;
    private LinearLayout ly_tab_learn;
    private TextView tab_learn;
    private TextView tab_learn_num;
    private LinearLayout ly_tab_schedule;
    private TextView tab_schedule;
    private TextView tab_schedule_num;
    private LinearLayout ly_tab_doge;
    private TextView tab_doge;
    private TextView tab_doge_num;
    private Button moblieButton;
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    private CampFragment fg1;
    private KnowledgeFragment fg2;
    private ScheduleFragment fg3;
    private MyFragment fg4;
    private View pendulum;
    private DrawerLayout mDrawerLayout;
    private ImageView idea;
    private TextView right_button;
    private int width;
    private int height;
    private RelativeLayout left_top;
    private VideoView mVideoView;
    private DanmakuView mDanmakuView;
    private boolean showDanmaku;
    private DanmakuContext danmakuContext;
    private TextView refresh;
    private ImageButton pauseButton;
    private ImageButton startButton;
    private RelativeLayout dm_setting;
    private ScrollView left_middle;
    private int setButtonFlag = 0;
    private LinearLayout dmDMDMLayout;
    private ImageView danmuButton;
    private ImageView allScreen;
    private LinearLayout leftBottom;
    private RelativeLayout ly_left;
    private RelativeLayout ly_right_bg;
    private ScrollView ly_right_news;
    private LinearLayout ly_right_clock;
    private ScrollView ly_right_download;
    private TextView DownloaderPost;
    private static final int PROCESSING = 1;   //正在下载实时数据传输Message标志
    private static final int FAILURE = -1;//下载失败时的Message标志
    private LinkedList<ProgressBar> ListProgressBar = new LinkedList<ProgressBar>();
    private LinkedList<TextView> ListTextView = new LinkedList<TextView>();
    private ArrayList<String> ListUrl = new ArrayList<String>();
    private LinkedList<ImageView> ListBtDown = new LinkedList<ImageView>();
    private LinkedList<ImageView> ListBtStop = new LinkedList<ImageView>();
    private LinkedList<LinearLayout> ListVideoshow = new LinkedList<LinearLayout>();
    private LinkedList<RelativeLayout> ListClock = new LinkedList<RelativeLayout>();
    private LinkedList<ImageView> ListClockCanel = new LinkedList<ImageView>();
    private ArrayList<String> ListVideoPath = new ArrayList<String>();
    private LinearLayout rightLogin;
    private LinearLayout NewsDiscuss;
    private int discussFlag;
    private TextView acountCreate;
    private TextView login;
    private EditText loginName;
    private EditText loginPassword;
    private String newShowId;
    private ImageView newsText;
    private ImageView newsTags ;
    private RelativeLayout LyRightInfoBg;
    private RelativeLayout LyRightTextBg;
    private TextView setButton;
    private AlarmManager alarmManager;
    private PendingIntent pi;
    private ScrollView rightClocks;
    private int clocki = 0;
    private boolean isLove = false;
    private boolean isLike = false;
    private boolean isDiscuss = false;
    private TextView rightButtonNum;
    private RelativeLayout lyCreateDiscuss;
    private TextView sendDiscuss;

    /**
     * 弹幕解析器
     */
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    private LinearLayout mOperationLayout;
    private Button mSend;
    private EditText mText;
    private TextView clockCreat;
    int RightPostFlag = 0;


    private LinearLayout LyNewsAction;
    private ImageView NewsActionLike;
    private ImageView NewsActionDiscuss;
    private ImageView NewsActionLove;

    /**
     * 创建文件或文件夹
     *
     * @param cfileName
     *            文件名或问文件夹名
     */
    public void createFile(String cfileName) {
        File cfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/" + cfileName);
        if (cfileName.indexOf(".") != -1) {
            // 说明包含，即使创建文件, 返回值为-1就说明不包含.,即使文件
            try {
                cfile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("创建了文件");
        } else {
            // 创建文件夹
            cfile.mkdir();
            System.out.println("创建了文件夹");
        }

    }


    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static boolean moblieNet(Context context)
    {
        final ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo mobile =connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(mobile.isAvailable())  //getState()方法是查询是否连接了数据网络
            return true;
        else
            return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new videoJsonThread().start();
        initDir();
        for(int i = 0; i < 3; i++){
            num[i] = 0;
        }
        setPermissions();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bindViews();
        ly_contentInit();
        //加载测试视频，需要在sd卡根目录提前放入测试视频,某些手机路径可能不一样，可以先运行一下看日志
        initView();
        initDanmaku();
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        netCheck();

    }

    private void initDir(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    createFile("mxlake");
                    createFile("mxlake/music");
                    createFile("mxlake/video");
                    createFile("mxlake/document");
                    createFile("mxlake/picture");
                    createFile("mxlake/app");
                    createFile("mxlake/zip");
                    createFile("mxlake/READ.ME");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }




    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                System.out.println("上拉加载");
                new videoJsonThread().start();
                mPullToRefreshView.onFooterRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 设置更新时间
                // mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
                System.out.println("下拉更新");
                GridLayout videoly = (GridLayout) findViewById(R.id.videolist);
                ListVideoPath.clear();
                ListVideoshow.clear();
                videoly.removeAllViews();
                new videoJsonThread().start();
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1000);

    }



    private final class DownloadTask implements Runnable{
        private String path;
        private File saveDir;
        private FileDownloadered loader;
        private ProgressBar progressbar;
        private String fileName;

        private Handler handler = new UIHandler();

        public DownloadTask(String path, File saveDir, ProgressBar progressbar,String fileName) {
            this.path = path;
            this.saveDir = saveDir;
            this.progressbar = progressbar;
            this.fileName = fileName;
        }

        public void exit(){
            if(loader!=null) loader.exit();
        }


        public void run() {

            try {
                int number = ListUrl.indexOf(path);
                loader = new FileDownloadered(getApplicationContext(), path, saveDir, 2,number,fileName);
                progressbar.setMax(loader.getFileSize());//设置进度条的最大刻度
                loader.download(new DownloadProgressListener() {
                    public void onDownloadSize(int size) {
                        int number = ListUrl.indexOf(path);
                        int fileSize = loader.getFileSize();
                        Message msg = new Message();
                        msg.what = 1;
                        msg.getData().putString("fileName",fileName);
                        msg.getData().putInt("size", size);
                        msg.getData().putInt("fileSize",fileSize);
                        msg.getData().putInt("number",number);
                        handler.sendMessage(msg);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                handler.sendMessage(handler.obtainMessage(-1));
            }
        }
    }

    private final class UIHandler extends Handler{
        @SuppressLint("WrongConstant")
        public void handleMessage(Message msg) {

            int size = msg.getData().getInt("size");//从消息中获取已经下载的数据长度
            int number = msg.getData().getInt("number");
            int fileSize = msg.getData().getInt("fileSize");
            String fileName = msg.getData().getString("fileName");


            ProgressBar progressbar = ListProgressBar.get(number);
            TextView textresult = ListTextView.get(number);

            switch (msg.what) {
                //下载时
                case PROCESSING:
                    progressbar.setProgress(size);
                    //设置进度条的进度
                    //计算已经下载的百分比,此处需要转换为浮点数计算
                    float num = (float)progressbar.getProgress() / (float)progressbar.getMax();
                    int result = (int)(num * 100);
                    //把获取的浮点数计算结果转换为整数
                    String resultString = result + "%";
                    textresult.setText(resultString+"\t"+size/1000000+"."+size/100000%10+"MB//"+fileSize/1000000+"."+fileSize/100000%10+"MB");   //把下载的百分比显示到界面控件上
                    if(progressbar!=null){
                    }
                    if(progressbar.getProgress() == progressbar.getMax()){ //下载完成时提示
                        Toast.makeText(getApplicationContext(), "“"+fileName+"”"+"文件下载成功", Toast.LENGTH_SHORT).show();

                    }
                    break;
                case FAILURE:    //下载失败时提示
                    Toast.makeText(getApplicationContext(), "“"+fileName+"”"+"文件下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @SuppressLint("WrongConstant")
    @Override
//fragement
    public void sendMessage(final String path, final String downFileName) {

            if(ListUrl.indexOf(path)!=-1){
                Toast.makeText(getApplicationContext(), "下载列表已经存在“"+downFileName+"”了", Toast.LENGTH_SHORT).show();
                return;
            }
        ListUrl.add(path);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        // 获取需要被添加控件的布局
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_download_item);
        // 获取需要添加的布局
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.download_item, null).findViewById(R.id.downloader);
        ImageView btndown = (ImageView) layout.findViewById(R.id.btndown);
        ImageView btnstop = (ImageView) layout.findViewById(R.id.btnstop);
        TextView btnInfo = (TextView) layout.findViewById(R.id.downFileInfo);
        btnInfo.setText(downFileName);
        btnstop.setVisibility(View.GONE);
        final ProgressBar progressbar = (ProgressBar) layout.findViewById(R.id.progressBar);
        TextView textresult = (TextView) layout.findViewById(R.id.textresult);
        ListBtStop.add(btnstop);
        ListBtDown.add(btndown);
        ListProgressBar.add(progressbar);
        ListTextView.add(textresult);
        Toast.makeText(getApplicationContext(), "“"+downFileName+"”"+"已添加到下载", Toast.LENGTH_SHORT).show();
        btndown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = ListUrl.indexOf(path);
                ProgressBar progressbar = ListProgressBar.get(number);
                ImageView btndown = ListBtDown.get(number);
                ImageView btnstop = ListBtStop.get(number);
                DownloadTask task = null;
                switch (v.getId()) {
                    case R.id.btndown:
                        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                            File saveDir = Environment.getExternalStorageDirectory();
                            task = new DownloadTask(path, saveDir, progressbar,downFileName);
                            new Thread(task).start();
                        }else{
                            Toast.makeText(getApplicationContext(), "sd卡读取失败", Toast.LENGTH_SHORT).show();
                        }
                        btndown.setVisibility(View.GONE);
                        btnstop.setVisibility(View.VISIBLE);
                        if(btnstop!=null){
                        }
                        break;
                    case R.id.btnstop:
                        if(task!=null) task.exit();
                        btndown.setVisibility(View.VISIBLE);
                        btnstop.setVisibility(View.GONE);
                        break;
                }


            }
        });
        // 将布局加入到当前布局中
        linearLayout.addView(layout);

        num[1]++;
        rightButtonNum.setText(num[1]+"");
        rightButtonNum.setVisibility(View.VISIBLE);
    }

    @Override
    public void sendMessage(String newid, String time, String usrName, String title, String tag1, String tag2, String tag3, Bitmap bitmap) {
        NewsDiscuss.removeAllViews();
        discussFlag = 0;
        Bitmap disscussZero = BitmapFactory.decodeResource(getResources(),R.drawable.discuss0);
        NewsActionDiscuss.setImageBitmap(disscussZero);
        NewsDiscuss.setVisibility(View.GONE);
        isDiscuss = false;
        ImageView Avatar = (ImageView)findViewById(R.id.news_avatar_show);
        TextView Time = (TextView)findViewById(R.id.news_time_show);
        TextView Name = (TextView)findViewById(R.id.news_usrname_show);
        TextView Title = (TextView)findViewById(R.id.news_title_show);
        TextView Tag1 = (TextView)findViewById(R.id.tag1_show);
        TextView Tag2 = (TextView)findViewById(R.id.tag2_show);
        TextView Tag3 = (TextView)findViewById(R.id.tag3_show);
        Avatar.setImageBitmap(bitmap);
        Name.setText(usrName);
        Time.setText(time);
        Title.setText(title);
        Tag1.setText(tag1);
        Tag2.setText(tag2);
        Tag3.setText(tag3);
        newShowId = newid;
        new newsThread(newid).start();
        LyNewsAction.setVisibility(View.VISIBLE);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, Gravity.END);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
        if(ListTextView.size()==0&&RightPostFlag==1){
            DownloaderPost.setVisibility(View.VISIBLE);
        }else {
            DownloaderPost.setVisibility(View.GONE);
        }
    }

        /*
        由于用户的输入事件(点击button, 触摸屏幕....)是由主线程负责处理的，如果主线程处于工作状态，
        此时用户产生的输入事件如果没能在5秒内得到处理，系统就会报“应用无响应”错误。
        所以在主线程里不能执行一件比较耗时的工作，否则会因主线程阻塞而无法处理用户的输入事件，
        导致“应用无响应”错误的出现。耗时的工作应该在子线程里执行。
         */

    /*
     * UI控件画面的重绘(更新)是由主线程负责处理的，如果在子线程中更新UI控件的值，更新后的值不会重绘到屏幕上
     * 一定要在主线程里更新UI控件的值，这样才能在屏幕上显示出来，不能在子线程中更新UI控件的值
     */

    private void setPermissions(){

        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }

    }

    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("需要获取存储空间\n否则，您将无法正常使用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setCancelable(false).show();
    }
    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else{
                        Toast.makeText(this, "用户拒绝", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void netCheck(){
        if(isWifi(this)){
            moblieButton.setVisibility(View.GONE);
            File testdir = Environment.getExternalStorageDirectory();
            mVideoView.setVideoPath(serverPath+"567.mp4");
            mVideoView.setVisibility(View.VISIBLE);
            pendulum.setVisibility(View.GONE);
            mVideoView.start();
        }
        else if(moblieNet(this)){
            moblieButton.setVisibility(View.VISIBLE);
            pendulum.setVisibility(View.GONE);
        }
        else {
            refresh.setVisibility(View.VISIBLE);
            moblieButton.setVisibility(View.GONE);
            pendulum.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化View组件
     */
    private void initView() {
        pendulum = (View) findViewById(R.id.pendulum);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        mOperationLayout = (LinearLayout) findViewById(R.id.operation_layout);
        mSend = (Button) findViewById(R.id.send);
        mText = (EditText) findViewById(R.id.edit_text);
        //给弹幕层设置点击事件，判断是否显示发弹幕操作层
        mDanmakuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOperationLayout.getVisibility()==View.GONE){
                    mOperationLayout.setVisibility(View.VISIBLE);
                }else {
                    mOperationLayout.setVisibility(View.GONE);
                }
            }
        });
        //给发送按钮设置点击事件，发送弹幕
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = mText.getText().toString();
                if (!TextUtils.isEmpty(data)){
                    addDanmaku(data,true);
                    mText.setText("");
                }
            }
        });

        //监听由于输入法弹出所致的沉浸问题
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                    onWindowFocusChanged(true);
                }
            }
        });






        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {
            }

            @Override
            public void onDrawerOpened(View view) {
            }

            @Override
            public void onDrawerClosed(View view) {
                //    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                leftBottom = (LinearLayout) findViewById(R.id.leftBottom);
                leftBottom.setVisibility(View.VISIBLE);
                RelativeLayout ly_left= (RelativeLayout) findViewById(R.id.left);
                ViewGroup.LayoutParams paraLeft;
                paraLeft = ly_left.getLayoutParams();
                paraLeft.width=width*4/5;
                ly_left.setLayoutParams(paraLeft);

                left_top = (RelativeLayout) findViewById(R.id.left_top);
                RelativeLayout.LayoutParams ParamsTop =  (RelativeLayout.LayoutParams) left_top.getLayoutParams();
                ParamsTop.height=width*9/20;
                hide_keyboard_from();
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }


    /**
     * 初始化弹幕组件
     */
    private void initDanmaku() {
        //给弹幕视图设置回调，在准备阶段获取弹幕信息并开始
        mDanmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                mDanmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        //缓存，提升绘制效率
        mDanmakuView.enableDanmakuDrawingCache(true);
        //DanmakuContext主要用于弹幕样式的设置
        danmakuContext = DanmakuContext.create();
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN,3);//描边
        danmakuContext.setDuplicateMergingEnabled(true);//重复合并
        danmakuContext.setScrollSpeedFactor(1.2f);//弹幕滚动速度
        //让弹幕进入准备状态，传入弹幕解析器和样式设置
        mDanmakuView.prepare(parser,danmakuContext);
        //显示fps、时间等调试信息
        //mDanmakuView.showFPS(true);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku) {
                    int time = new Random().nextInt(300);
                    String content = "" + time + time;
                    addDanmaku(content, false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        //弹幕实例BaseDanmaku,传入参数是弹幕方向
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = DensityUtils.sp2px(this,20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(mDanmakuView.getCurrentTime());
        //加边框
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        mDanmakuView.addDanmaku(danmaku);
    }

    /**
     * sp转px的方法。
     */

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //下载器
        //弹幕
        showDanmaku = false;
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    private void ly_contentInit(){
        fg1 = new CampFragment();
        fg2 = new KnowledgeFragment();
        fg3 = new ScheduleFragment();
        fg4 = new MyFragment();
        Bundle bundle_width = new Bundle();
        bundle_width.putInt("width",width);
        fg1.setArguments(bundle_width);
        fg2.setArguments(bundle_width);
        fg4.setArguments(bundle_width);
        fg3.setArguments(bundle_width);
        fManager = getFragmentManager();
        fTransaction = fManager.beginTransaction();
        fTransaction.add(R.id.ly_content, fg1).commit();
        fTransaction.add(R.id.ly_content, fg2);
        fTransaction.hide(fg2);
        fTransaction.add(R.id.ly_content, fg3);
        fTransaction.hide(fg3);
        fTransaction.add(R.id.ly_content, fg4);
        fTransaction.hide(fg4);

        tab_home.setSelected(true);
    }

    private void bindViews() {
        newShowId = null;
        discussFlag = 0;
        rightButtonNum = (TextView)findViewById(R.id.right_button_num);
        lyCreateDiscuss = (RelativeLayout) findViewById(R.id.ly_create_discuss);
        sendDiscuss = (TextView) findViewById(R.id.send_discuss);
        //第一个Fragment
        ly_tab_home = (LinearLayout) findViewById(R.id.ly_tab_home);
        tab_home = (TextView) findViewById(R.id.tab_home);
        //home图片大小
        Drawable homeDrawable = getResources().getDrawable(R.drawable.home);
        homeDrawable.setBounds(0, 0, (homeDrawable.getMinimumWidth())/8, (homeDrawable.getMinimumHeight())/8);
        tab_home.setCompoundDrawables(null, homeDrawable, null, null);
        //消息

        //第二个Fragment
        ly_tab_learn = (LinearLayout) findViewById(R.id.ly_tab_learn);
        tab_learn = (TextView) findViewById(R.id.tab_learn);
        //learn图片大小
        Drawable learnDrawable = getResources().getDrawable(R.drawable.learn);
        learnDrawable.setBounds(0, 0, (learnDrawable.getMinimumWidth())/8, (learnDrawable.getMinimumHeight())/8);
        tab_learn.setCompoundDrawables(null, learnDrawable, null, null);
        //消息
        tab_learn_num = (TextView) findViewById(R.id.tab_learn_num);

        //第三个Fragment
        ly_tab_schedule = (LinearLayout) findViewById(R.id.ly_tab_schedule);
        tab_schedule = (TextView) findViewById(R.id.tab_schedule);
        //schedule图片大小
        Drawable scheduleDrawable = getResources().getDrawable(R.drawable.schedule);
        scheduleDrawable.setBounds(0, 0, (scheduleDrawable.getMinimumWidth())/8, (scheduleDrawable.getMinimumHeight())/8);
        tab_schedule.setCompoundDrawables(null, scheduleDrawable, null, null);
        //消息
        tab_schedule_num = (TextView) findViewById(R.id.tab_schedule_num);

        //第四个Fragment
        ly_tab_doge = (LinearLayout) findViewById(R.id.ly_tab_doge);
        tab_doge = (TextView) findViewById(R.id.tab_doge);
        //doge图片大小
        Drawable dogeDrawable = getResources().getDrawable(R.drawable.doge);
        dogeDrawable.setBounds(0, 0, (dogeDrawable.getMinimumWidth())/8, (dogeDrawable.getMinimumHeight())/8);
        tab_doge.setCompoundDrawables(null, dogeDrawable, null, null);
        //消息
        tab_doge_num = (TextView) findViewById(R.id.tab_doge_num);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.ly_top_bar);
        //mToolbar.setTitle(null);//customize the title,个性化设置title
        //mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));//设置title颜色
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//取消显示Title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//show back button and make it enabled
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,R.string.open, R.string.close);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.setHomeAsUpIndicator(R.mipmap.ic_launcher);//channge the icon,改变图标
        mActionBarDrawerToggle.syncState();////show the default icon and sync the DrawerToggle state,如果你想改变图标的话，这句话要去掉。这个会使用默认的三杠图标
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        rightClocks = (ScrollView) findViewById(R.id.right_clock_s);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        ly_right_bg= (RelativeLayout) findViewById(R.id.right_bg);
        ViewGroup.LayoutParams paraRightBg;
        paraRightBg = ly_right_bg.getLayoutParams();
        paraRightBg.width=width;
        paraRightBg.height=height;
        ly_right_bg.setLayoutParams(paraRightBg);
        ly_right_news = (ScrollView) findViewById(R.id.right_news);

        ly_right_download = (ScrollView) findViewById(R.id.right_download_manager);

        ly_right_clock = (LinearLayout) findViewById(R.id.right_clock);

        RelativeLayout right_news_info= (RelativeLayout) findViewById(R.id.right_info_bg);
        RelativeLayout.LayoutParams paraRight_news_info;
        paraRight_news_info =  (RelativeLayout.LayoutParams) right_news_info.getLayoutParams();
        paraRight_news_info.width=width*9/10;
        paraRight_news_info.height=height*3/16;
        right_news_info.setLayoutParams(paraRight_news_info);

        RelativeLayout right_text_bg= (RelativeLayout) findViewById(R.id.right_text_bg);
        RelativeLayout.LayoutParams paraRight_text_bg;
        paraRight_text_bg =  (RelativeLayout.LayoutParams) right_text_bg.getLayoutParams();
        paraRight_text_bg.width=width*9/10;
        paraRight_text_bg.height=height*3/16;
        right_text_bg.setLayoutParams(paraRight_text_bg);

        final EditText right_text= (EditText) findViewById(R.id.right_text);
        RelativeLayout.LayoutParams paraRight_text;
        paraRight_text =  (RelativeLayout.LayoutParams) right_text.getLayoutParams();
        paraRight_text.width=width*8/10;
        paraRight_text.height=height*3/20;
        right_text.setLayoutParams(paraRight_text);

        final EditText right_title = (EditText) findViewById(R.id.right_title);
        final EditText right_tags1 = (EditText) findViewById(R.id.right_tags1);
        final EditText right_tags2 = (EditText) findViewById(R.id.right_tags2);
        final EditText right_tags3 = (EditText) findViewById(R.id.right_tags3);
        final EditText right_place = (EditText) findViewById(R.id.right_place);



        RelativeLayout right_menu= (RelativeLayout) findViewById(R.id.right_menu);
        RelativeLayout.LayoutParams paraRight_menu;
        paraRight_menu = (RelativeLayout.LayoutParams) right_menu.getLayoutParams();
        paraRight_menu.width=width*3/5;
        paraRight_menu.height=height/25;
        right_menu.setLayoutParams(paraRight_menu);


        LyRightInfoBg = (RelativeLayout)findViewById(R.id.ly_right_info_bg);
        LyRightTextBg = (RelativeLayout)findViewById(R.id.ly_right_text_bg);

        newsText = (ImageView) findViewById(R.id.newstext);
        RelativeLayout.LayoutParams paraRight_camer;
        paraRight_camer =  (RelativeLayout.LayoutParams) newsText.getLayoutParams();
        paraRight_camer.width=height/25;
        newsText.setLayoutParams(paraRight_camer);



        danmuButton = (ImageView) findViewById(R.id.danmu_btn);
        RelativeLayout.LayoutParams paradmButton;
        paradmButton =  (RelativeLayout.LayoutParams) danmuButton.getLayoutParams();
        paradmButton.width=height/16;
        danmuButton.setLayoutParams(paradmButton);


        allScreen = (ImageView) findViewById(R.id.allScreen);
        RelativeLayout.LayoutParams paraAllSreen;
        paraAllSreen =  (RelativeLayout.LayoutParams) allScreen.getLayoutParams();
        paraAllSreen.width=height/16;
        allScreen.setLayoutParams(paraAllSreen);

        newsTags = (ImageView) findViewById(R.id.newstags);
        RelativeLayout.LayoutParams paraRight_photos;
        paraRight_photos =  (RelativeLayout.LayoutParams) newsTags.getLayoutParams();
        paraRight_photos.width=height/25;
        newsTags.setLayoutParams(paraRight_photos);

        refresh = (TextView) findViewById(R.id.refresh_net);

        idea = (ImageView) findViewById(R.id.idea);
        RelativeLayout.LayoutParams paraRight_photo;
        paraRight_photo =  (RelativeLayout.LayoutParams) idea.getLayoutParams();
        paraRight_photo.width=height/16;
        idea.setLayoutParams(paraRight_photo);


        ly_left= (RelativeLayout) findViewById(R.id.left);
        ViewGroup.LayoutParams paraLeft;
        paraLeft = ly_left.getLayoutParams();
        paraLeft.width=width*4/5;
        ly_left.setLayoutParams(paraLeft);

        dm_setting = (RelativeLayout) findViewById(R.id.dm_setting);
        RelativeLayout.LayoutParams ParamsDmSetting =  (RelativeLayout.LayoutParams) dm_setting.getLayoutParams();
        ParamsDmSetting.height = width/10;
        dm_setting.setLayoutParams(ParamsDmSetting);


        left_top = (RelativeLayout) findViewById(R.id.left_top);
        RelativeLayout.LayoutParams ParamsTop =  (RelativeLayout.LayoutParams) left_top.getLayoutParams();
        ParamsTop.height=width*9/20;
        left_top.setLayoutParams(ParamsTop);

        right_button = (TextView) findViewById(R.id.right_button);
        RelativeLayout.LayoutParams ParamsShare =  (RelativeLayout.LayoutParams) right_button.getLayoutParams();
        ParamsShare.width = width/5;
        right_button.setLayoutParams(ParamsShare);


        left_middle = (ScrollView) findViewById(R.id.left_middle);
        RelativeLayout.LayoutParams ParamsMiddle =  (RelativeLayout.LayoutParams) left_middle.getLayoutParams();
        ParamsMiddle.height = (height-width/2)/3;
        left_middle.setLayoutParams(ParamsMiddle);

        dmDMDMLayout = (LinearLayout) findViewById(R.id.dmDmDmLayout);

        clockCreat = (TextView)findViewById(R.id.clock_creat);

        TextView left_bottom1 = (TextView) findViewById(R.id.sun_moon);
        LinearLayout.LayoutParams ParamsBottom1 =  (LinearLayout.LayoutParams) left_bottom1.getLayoutParams();
        ParamsBottom1.height = height/15;
        left_bottom1.setLayoutParams(ParamsBottom1);

        setButton = (TextView) findViewById(R.id.left_setting);
        LinearLayout.LayoutParams ParamsBottom2 =  (LinearLayout.LayoutParams) setButton.getLayoutParams();
        ParamsBottom2.height = height/15;
        left_bottom1.setLayoutParams(ParamsBottom2);

        DownloaderPost = (TextView) findViewById(R.id.downloader_post);

        final LinearLayout leftBottom =(LinearLayout) findViewById(R.id.leftBottom);

        moblieButton = (Button) findViewById(R.id.moblieButton);


        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        startButton = (ImageButton) findViewById(R.id.startButton);
        rightLogin = (LinearLayout) findViewById(R.id.ly_right_login);
        acountCreate = (TextView) findViewById(R.id.acount_create);
        login = (TextView) findViewById(R.id.login);
        loginName = (EditText) findViewById(R.id.login_name);
        loginPassword = (EditText) findViewById(R.id.login_password);
        ly_tab_home.setOnClickListener(this);
        ly_tab_learn.setOnClickListener(this);
        ly_tab_schedule.setOnClickListener(this);
        ly_tab_doge.setOnClickListener(this);
        acountCreate.setOnClickListener(this);
        login.setOnClickListener(this);
        loginPassword.setOnClickListener(this);

        LyNewsAction = findViewById(R.id.ly_news_action);
        NewsActionDiscuss = findViewById(R.id.news_action_discuss);
        NewsDiscuss = findViewById(R.id.news_discuss);
        NewsActionLove = findViewById(R.id.news_action_love);
        NewsActionLike = findViewById(R.id.news_action_like);

        sendDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "发送成功",Toast.LENGTH_SHORT).show();
                new Thread(){
                    public void run(){

                    }
                }.start();

            }
        });



        danmuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showDanmaku){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.nodanmu);
                    danmuButton.setImageBitmap(bitmap);
                }else{
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.danmu);
                    danmuButton.setImageBitmap(bitmap);
                    generateSomeDanmaku();
                }
                showDanmaku = !showDanmaku;
            }
        });

        newsTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LyRightInfoBg.setVisibility(View.VISIBLE);
                LyRightTextBg.setVisibility(View.GONE);
            }
        });

        newsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LyRightInfoBg.setVisibility(View.GONE);
                LyRightTextBg.setVisibility(View.VISIBLE);
            }
        });

        NewsActionLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLove){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.love0);
                    NewsActionLove.setImageBitmap(bitmap);
                }else{
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.love1);
                    NewsActionLove.setImageBitmap(bitmap);
                }
                isLove=!isLove;    }
        });


        NewsActionLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLove){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.love0);
                    NewsActionLove.setImageBitmap(bitmap);
                    new Thread(){
                        public void run(){
                            ServletService.listByPost(newShowId,"noGood");
                        }
                    }.start();
                }else{
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.love1);
                    NewsActionLove.setImageBitmap(bitmap);
                    new Thread(){
                        public void run(){
                            ServletService.listByPost(newShowId,"good");
                        }
                    }.start();

                }
                isLove=!isLove;    }
        });


        NewsActionLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLike){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.like0);
                    NewsActionLike.setImageBitmap(bitmap);
                    new Thread(){
                        public void run(){
                            ServletService.listByPost(newShowId,"noGood");
                        }
                    }.start();

                }else{
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.like1);
                    NewsActionLike.setImageBitmap(bitmap);
                    new Thread(){
                        public void run(){
                            ServletService.listByPost(newShowId,"good");
                        }
                    }.start();

                }
                isLike=!isLike;    }
        });


        NewsActionDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDiscuss){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.discuss0);
                    NewsActionDiscuss.setImageBitmap(bitmap);
                    NewsDiscuss.setVisibility(View.GONE);
                    lyCreateDiscuss.setVisibility(View.GONE);
                }else{
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.discuss1);
                    NewsActionDiscuss.setImageBitmap(bitmap);
                    if(discussFlag == 0){
                        new discussThread().start();
                        discussFlag = 1;
                    }
                    NewsDiscuss.setVisibility(View.VISIBLE);
                    lyCreateDiscuss.setVisibility(View.VISIBLE);
                }
                isDiscuss=!isDiscuss;
            }

        });


       clockCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar ca = Calendar.getInstance();
                int mYear = ca.get(Calendar.YEAR);
                int mMonth = ca.get(Calendar.MONTH);
                int mDay = ca.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(MainActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
            }
        });

        acountCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, SignActivity.class);
                    startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new loginThread(loginName.getText().toString(),loginPassword.getText().toString()).start();
                }
            });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setButtonFlag==0){
                    left_middle.setVisibility(View.VISIBLE);
                    setButtonFlag=1;
                }else{
                    left_middle.setVisibility(View.GONE);
                    setButtonFlag=0;
                }



            }
        });

        allScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int videoPosition = mVideoView.getCurrentPosition();
                Intent intent = new Intent(MainActivity.this, VideoFitScreenActivity.class);
                intent.putExtra("videoPosition", videoPosition);
                startActivity(intent);
                mVideoView.stopPlayback();
            }
        });

        idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);

                final String news_text = right_text.getText().toString();
                final String news_title = right_title.getText().toString();
                final String news_tag1 = right_tags1.getText().toString();
                final String news_tag2 = right_tags2.getText().toString();
                final String news_tag3 = right_tags3.getText().toString();
                final String news_place = right_place.getText().toString();

                if(UserAccountNow!=null){

                    new Thread(){
                        public void run(){
                            ServletService.sendNewsByPost(news_text,UserAccountNow,news_title,news_tag1,news_tag2,news_tag3,news_place);
                        }
                    }.start();



                }else{
                    Toast.makeText(MainActivity.this, "请先登陆",Toast.LENGTH_SHORT).show();
                }
            }
        });

        moblieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moblieButton.setVisibility(View.GONE);
                mVideoView.setVisibility(View.VISIBLE);
                mVideoView.start();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.setVisibility(View.GONE);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.pause();
                pauseButton.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.start();
                pauseButton.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.GONE);
            }
        });

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, Gravity.END);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
                if(ListTextView.size()==0&&RightPostFlag==1){
                    DownloaderPost.setVisibility(View.VISIBLE);
                }else {
                    DownloaderPost.setVisibility(View.GONE);
                }
            }
        });

    }


    private class loginHandler extends Handler{
        @SuppressLint("WrongConstant")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(MainActivity.this, "登陆失败",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    loginName.setVisibility(View.GONE);
                    loginPassword.setVisibility(View.GONE);
                    login.setVisibility(View.GONE);
                    Bitmap bitmap = (Bitmap)msg.obj;
                    ImageView Avatar = fg4.getView().findViewById(R.id.avatar);
                    Avatar.setImageBitmap(bitmap);
                    Toast.makeText(MainActivity.this, "登陆成功",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }




    public class loginThread extends Thread {
        String loginName;
        String loginPassword;
        Handler lhandler = new loginHandler();

        public loginThread(String loginName,String loginPassword) {
            this.loginName = loginName;
            this.loginPassword = loginPassword;
        }

        @Override
        public void run() {

            try {
                String tmp = ServletService.loginByPost(loginName,loginPassword);

                if(tmp.equals("false")){
                    Message msg = new Message();
                    msg.what = 0;
                    lhandler.sendMessage(msg);
                }else {
                    Log.i("yuyu",tmp);
                    JSONArray jsonArray = new JSONArray(tmp);
                    Log.i("yuyu",jsonArray+"");
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    String usrName = jsonObject.getString("name");
                    String avatarPath = jsonObject.getString("avatar");
                    UserAccountNow = loginName;
                    UserNameNow = usrName;
                    UserAvatarNow = BitmapService.getHttpBitmap(serverPath+avatarPath);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = UserAvatarNow;
                    lhandler.sendMessage(msg);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            final int mYear = year;
            final int mMonth = monthOfYear;
            final int mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }
            Toast.makeText(MainActivity.this,days,
                    Toast.LENGTH_SHORT).show();

                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                Intent intent = new Intent(MainActivity.this, ClockActivity.class);

                pi = PendingIntent.getActivity(MainActivity.this, clocki, intent, 0);
                clocki ++;
                Calendar currentTime = Calendar.getInstance();
                currentTime.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
                new TimePickerDialog(MainActivity.this, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                //设置当前时间
                                Calendar c = Calendar.getInstance();
                                c.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
                                c.setTimeInMillis(System.currentTimeMillis());
                                // 根据用户选择的时间来设置Calendar对象
                                c.set(Calendar.YEAR,mYear);
                                c.set(Calendar.MONTH,mMonth);
                                c.set(Calendar.DAY_OF_MONTH,mDay);
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                // ②设置AlarmManager在Calendar对应的时间启动Activity

                                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);

                                Log.e("HEHE",c.getTimeInMillis()+"");   //这里的时间是一个unix时间戳
                                // 提示闹钟设置完毕:
                                Toast.makeText(MainActivity.this, "闹钟设置完毕~",
                                        Toast.LENGTH_SHORT).show();
                                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                                // 获取需要被添加控件的布局
                                final LinearLayout rightClock = (LinearLayout) findViewById(R.id.right_clock);
                                // 获取需要添加的布局
                                final RelativeLayout lyClock = (RelativeLayout) inflater.inflate(
                                        R.layout.ly_clock, null).findViewById(R.id.lyclock);
                                TextView clockTime = (TextView)lyClock.findViewById(R.id.clock_time);

                                final ImageView clockCanel = (ImageView)lyClock.findViewById(R.id.clock_canel);
                                int cYear = c.get(Calendar.YEAR);
                                int cMonth = c.get(Calendar.MONTH) + 1;
                                int cDay = c.get(Calendar.DAY_OF_MONTH);
                                int cTime = c.get(Calendar.HOUR_OF_DAY);
                                int cMin = c.get(Calendar.MINUTE);
                                clockTime.setText(cYear+"年"+cMonth+"月"+cDay+"日"+" "+cTime+":"+cMin);
                                ListClock.add(lyClock);
                                ListClockCanel.add(clockCanel);
                                rightClock.addView(lyClock);
                                num[2]++;
                                rightButtonNum.setText(num[2]+"");
                                rightButtonNum.setVisibility(View.VISIBLE);
                                clockCanel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int number = ListClockCanel.indexOf(v);
                                        alarmManager.cancel(pi);
                                        rightClock.removeView(ListClock.get(number));
                                        ListClock.remove(number);
                                        ListClockCanel.remove(number);
                                        num[2]--;
                                        if(num[2]==0){
                                            rightButtonNum.setVisibility(View.GONE);
                                        }else{
                                            rightButtonNum.setText(num[2]+"");
                                        }
                                    }
                                });
                            }
                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
                        .get(Calendar.MINUTE), false).show();
        }
    };

    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }

    public static String getTimeZone() {
        return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.ly_tab_home:
                setSelected();
                right_button.setText("发布");
                if(num[0]==0){
                    rightButtonNum.setVisibility(View.GONE);
                }else{
                    rightButtonNum.setText(num[0]+"");
                    rightButtonNum.setVisibility(View.VISIBLE);
                }
                DownloaderPost.setVisibility(View.GONE);
                rightLogin.setVisibility(View.GONE);
                rightClocks.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
                ly_right_news.setVisibility(View.VISIBLE);
                ly_right_download.setVisibility(View.GONE);
                RightPostFlag=0;
                tab_home.setSelected(true);
                if(fg1 == null){
                    fg1 = new CampFragment();
                    Bundle bundle_width = new Bundle();
                    bundle_width.putInt("width",width);
                    fg1.setArguments(bundle_width);
                    fTransaction.add(R.id.ly_content,fg1);
                }
                    fTransaction.show(fg1);
                break;

            case R.id.ly_tab_learn:
                setSelected();
                right_button.setText("传输");
                if(num[1]==0){
                    rightButtonNum.setVisibility(View.GONE);
                }else{
                    rightButtonNum.setText(num[1]+"");
                    rightButtonNum.setVisibility(View.VISIBLE);
                }
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
                rightLogin.setVisibility(View.GONE);
                tab_learn.setSelected(true);
                tab_learn_num.setVisibility(View.INVISIBLE);
                ly_right_news.setVisibility(View.GONE);
                ly_right_download.setVisibility(View.VISIBLE);
                rightClocks.setVisibility(View.GONE);
                RightPostFlag=1;
                if(fg2 == null){
                    fg2 = new KnowledgeFragment();
                    Bundle bundle_width = new Bundle();
                    bundle_width.putInt("width",width);
                    fg2.setArguments(bundle_width);
                    fTransaction.add(R.id.ly_content,fg2);
                }
                    fTransaction.show(fg2);
                if(ListTextView.size()==0&&RightPostFlag==1){
                    DownloaderPost.setVisibility(View.VISIBLE);
                }else {
                    DownloaderPost.setVisibility(View.GONE);
                }
                break;

            case R.id.ly_tab_schedule:
                setSelected();
                right_button.setText("闹钟");
                if(num[2]==0){
                    rightButtonNum.setVisibility(View.GONE);
                }else{
                    rightButtonNum.setText(num[2]+"");
                    rightButtonNum.setVisibility(View.VISIBLE);
                }
                rightLogin.setVisibility(View.GONE);
                DownloaderPost.setVisibility(View.GONE);
                ly_right_news.setVisibility(View.GONE);
                ly_right_download.setVisibility(View.GONE);
                rightClocks.setVisibility(View.VISIBLE);
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
                RightPostFlag=0;
                tab_schedule.setSelected(true);
                tab_schedule_num.setVisibility(View.INVISIBLE);
                if(fg3 == null){
                    fg3 = new ScheduleFragment();
                    Bundle bundle_width = new Bundle();
                    bundle_width.putInt("width",width);
                    fg3.setArguments(bundle_width);
                    fTransaction.add(R.id.ly_content,fg3);
                }
                fTransaction.show(fg3);
                break;

            case R.id.ly_tab_doge:
                setSelected();
                right_button.setText("登陆");
                rightLogin.setVisibility(View.VISIBLE);
                DownloaderPost.setVisibility(View.GONE);
                rightClocks.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
                ly_right_news.setVisibility(View.GONE);
                ly_right_download.setVisibility(View.GONE);

                tab_doge.setSelected(true);
                tab_doge_num.setVisibility(View.INVISIBLE);
                RightPostFlag=0;
                if(fg4 == null){
                    fg4 = new MyFragment();
                    Bundle bundle_width = new Bundle();
                    bundle_width.putInt("width",width);
                    fg4.setArguments(bundle_width);
                    fTransaction.add(R.id.ly_content,fg4);
                }
                fTransaction.show(fg4);
                break;

            case R.id.moblieButton:
                moblieButton.setVisibility(View.GONE);
                mVideoView.start();
                break;
        }
        fTransaction.commit();
    }

    //重置所有文本的选中状态
    private void setSelected() {
        tab_home.setSelected(false);
        tab_learn.setSelected(false);
        tab_schedule.setSelected(false);
        tab_doge.setSelected(false);
    }


    private void hide_keyboard_from() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }





    private final class videoHandler extends Handler{
        @SuppressLint("WrongConstant")
        public void handleMessage(Message msg) {

            String videoName = msg.getData().getString("videoName");//从消息中获取已经下载的数据长度
            final String videoPath = msg.getData().getString("videoPath");
            Bitmap bitmap = (Bitmap) msg.obj;

            switch (msg.what) {
                //下载时
                case 1:
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    // 获取需要被添加控件的布局
                    GridLayout videoly = (GridLayout) findViewById(R.id.videolist);
                    // 获取需要添加的布局
                    LinearLayout videolayout = (LinearLayout) inflater.inflate(
                            R.layout.video_shows, null).findViewById(R.id.video_show);
                    ImageView video_picture = (ImageView) videolayout.findViewById(R.id.video_picture);

                    video_picture.setImageBitmap(bitmap);
                    LinearLayout.LayoutParams para = (LinearLayout.LayoutParams) video_picture.getLayoutParams();
                    para.width = width*2/5;
                    para.height = width*9/40;
                    video_picture.setLayoutParams(para);
                    TextView video_info = (TextView) videolayout.findViewById(R.id.video_info);
                    video_info.setWidth(width*2/5);
                    video_info.setHeight(width*9/40);
                    video_info.setText(videoName);

                    videolayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mVideoView.stopPlayback();
                            mVideoView.setVideoPath(videoPath);
                            mVideoView.start();
                        }
                    });
                    ListVideoshow.add(videolayout);
                    videoly.addView(videolayout);
                    break;
                default:
                    break;
            }
        }
    }

    public class videoJsonThread extends Thread {
        Handler vhandler = new videoHandler();

        public videoJsonThread() {

        }

        @Override
        public void run() {
                try {
                    JSONArray jsonArray = new JSONArray(ServletService.listByPost(ListVideoPath.size()+"","videoList"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String videoPath = jsonObject.getString("videoPath");

                        if(ListVideoPath.indexOf(videoPath)==-1) {
                            ListVideoPath.add(videoPath);
                            String videoName = jsonObject.getString("videoName");
                            String picturePath = jsonObject.getString("picturePath");
                            Message msg = new Message();
                            msg.what = 1;
                            msg.getData().putString("videoName", videoName);
                            msg.getData().putString("videoPath", serverPath + videoPath);
                            msg.obj = BitmapService.getHttpBitmap(serverPath + picturePath);
                            vhandler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    private final class newsHandler extends Handler{
        @SuppressLint("WrongConstant")
        public void handleMessage(Message msg) {

            String newContent = msg.getData().getString("newContent");
            switch (msg.what) {
                case 1:
                    TextView rightContent = (TextView)findViewById(R.id.right_content);
                    rightContent.setText(newContent.replace("\n", "\n\t\t\t"));
                    break;
                default:
                    break;
            }
        }
    }

    public class newsThread extends Thread {
        Handler newshandler = new newsHandler();
        String newid = null;
        public newsThread(String newid) {
            this.newid = newid;
        }
        @Override
        public void run() {
            try {
                String newContent = "\t\t\t"+ServletService.listByPost(newid,"newContent");
                Message msg = new Message();
                msg.what = 1;
                msg.getData().putString("newContent", newContent);
                newshandler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private final class discussHandler extends Handler{
        @SuppressLint("WrongConstant")
        public void handleMessage(Message msg) {

            String time = msg.getData().getString("time");
            String name = msg.getData().getString("name");
            String content = msg.getData().getString("content");
            final String discussId = msg.getData().getString("discussid");
            Log.i("discussID2",discussId);

            switch (msg.what) {
                case 0:

                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    // 获取需要被添加控件的布局
                    // 获取需要添加的布局
                    RelativeLayout discusslayout = (RelativeLayout) inflater.inflate(
                            R.layout.discuss_item, null).findViewById(R.id.ly_discuss);
                    TextView discussName = (TextView) discusslayout.findViewById(R.id.discuss_name);
                    TextView discussTime = (TextView) discusslayout.findViewById(R.id.discuss_time);
                    TextView discussContent = (TextView) discusslayout.findViewById(R.id.discuss_content);
                    TextView discussReply = (TextView) discusslayout.findViewById(R.id.discuss_reply);
                    discussReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.putExtra("discussid",discussId );
                        startActivity(intent);
                    }
                });
                    discussName.setText(name+":");
                    discussTime.setText(time);
                    discussContent.setText(content.replace("\n", "\n\t\t\t"));
                    NewsDiscuss.addView(discusslayout);
                    break;
                case 1:

                    break;
                default:
                    break;
            }
        }
    }

    public class discussThread extends Thread {
        Handler discusshandler = new discussHandler();

        public discussThread() {

        }

        @Override
        public void run() {
            try {
                JSONArray jsonArray = new JSONArray(ServletService.listByPost(newShowId,"discuss"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String discussId = jsonObject.getString("discussid");
                    String time = jsonObject.getString("time");
                    String name = jsonObject.getString("name");
                    String content = jsonObject.getString("content");
                    content = "\t\t\t"+content;
                    Message msg = new Message();
                    msg.what = 0;
                    msg.getData().putString("discussid", discussId);
                    Log.i("discussID",discussId);
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
}
