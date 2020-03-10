package com.example.jason.mxlake.module;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.mxlake.R;
import com.example.jason.mxlake.main.view.PullToRefreshView;
import com.example.jason.mxlake.service.BitmapService;
import com.example.jason.mxlake.service.ServletService;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class CampFragment extends Fragment implements
        PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private String serverPath = "http://192.168.56.1:8080/wangzhijun/file/";
    private LinkedList<TextView> ListHotText = new LinkedList<TextView>();
    private LinkedList<LinearLayout> ListHotLy = new LinkedList<LinearLayout>();
    private LinkedList<RelativeLayout> ListNews = new LinkedList<RelativeLayout>();
    private int moreFlag = 0;
    private TextView textViewNews;
    private PullToRefreshView mPullToRefreshView;
    private ArrayList<String> ListNewsId = new ArrayList<String>();
    private ArrayList<String> ListHotId = new ArrayList<String>();
    private TextView btnBuildings;

    private GridLayout GridLayoutMap;
    private int mapFlag = 0;
    private SendMessageCommunitor sendMessage;

    public CampFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fg_camp,container,false);
        int width = (int)getArguments().get("width");
        int imageCampusHeight = width/5*2;
        btnBuildings = (TextView) view.findViewById(R.id.btn_buildings);
        final TextView more = (TextView) view.findViewById(R.id.more);
        ImageView campus = (ImageView) view.findViewById(R.id.campus);
        LinearLayout.LayoutParams paramFrameLayout = new LinearLayout.LayoutParams(width,imageCampusHeight);
        campus.setLayoutParams(paramFrameLayout);
        final Button GLM_ItemA1 = (Button) view.findViewById(R.id.GLMItemA1);
        final Button GLM_ItemA2 = (Button) view.findViewById(R.id.GLMItemA2);
        final Button GLM_ItemA3= (Button) view.findViewById(R.id.GLMItemA3);
        final Button GLM_ItemA4 = (Button) view.findViewById(R.id.GLMItemA4);
        final Button GLM_ItemA5 = (Button) view.findViewById(R.id.GLMItemA5);
        final Button GLM_ItemA6 = (Button) view.findViewById(R.id.GLMItemA6);
        final Button GLM_ItemA7 = (Button) view.findViewById(R.id.GLMItemA7);
        final Button GLM_ItemA8 = (Button) view.findViewById(R.id.GLMItemA8);
        int ButtonSize = width/8;
        GLM_ItemA1.setHeight(ButtonSize);
        GLM_ItemA2.setHeight(ButtonSize);
        GLM_ItemA3.setHeight(ButtonSize);
        GLM_ItemA4.setHeight(ButtonSize);
        GLM_ItemA5.setHeight(ButtonSize);
        GLM_ItemA6.setHeight(ButtonSize);
        GLM_ItemA7.setHeight(ButtonSize);
        GLM_ItemA8.setHeight(ButtonSize);
        textViewNews = (TextView) view.findViewById(R.id.textViewNews);
        GridLayoutMap = (GridLayout) view.findViewById(R.id.GridLayoutMap);
        final LinearLayout campusFragment = (LinearLayout) view.findViewById(R.id.campusFragment);
        final ViewTreeObserver observer = campusFragment.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            int flag = 0;
            public boolean onPreDraw() {
                if (flag == 0){
                    flag = campusFragment.getMeasuredHeight();
                }
                return true;
            }
        });
        new hotThread().start();
        new newsThread().start();

        /**
         * 消息处理
         */
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.main_pull_refresh_view);

        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ListHotId.size()<3){
                    Toast.makeText(getActivity(), "没有更多了", Toast.LENGTH_SHORT).show();
                }else{
                    if(moreFlag==0){
                        more.setText("|･ω･｀)");
                        for(int i=3;i<ListHotId.size();i++){
                            ListHotLy.get(i).setVisibility(View.VISIBLE);
                        }
                        moreFlag=1;
                    }
                    else{
                        more.setText("more");
                        for(int i=3;i<10;i++){
                            ListHotLy.get(i).setVisibility(View.GONE);
                        }
                        moreFlag=0;
                    }
                }

            }
        });
        textViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*  LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ly_news);
                // 获取需要添加的布局\
                linearLayout.removeAllViews();
                for(int i = 0;i<ListNews.size();i++){
                    ListNews.remove(i);
                } */
            }
        });
        btnBuildings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mapFlag==0){
                    GridLayoutMap.setVisibility(View.VISIBLE);
                    mapFlag=1;
                }else{
                    GridLayoutMap.setVisibility(View.GONE);
                    mapFlag=0;
                }
            }
        });
  return view;
    }
    public class newsHander extends Handler{
        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("WrongConstant")
        public void handleMessage(final Message msg) {
            final String newId = msg.getData().getString("newId");
            final String time = msg.getData().getString("time");
            final String usrName = msg.getData().getString("usrName");
            final String title = msg.getData().getString("title");
            final String tag1 = msg.getData().getString("tag1");
            final String tag2 = msg.getData().getString("tag2");
            final String tag3 = msg.getData().getString("tag3");
            final Bitmap bitmap = (Bitmap) msg.obj;
            switch (msg.what) {
                //下载时
                case 1:
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.ly_news);
                    // 获取需要添加的布局
                    RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.news_item, null).findViewById(R.id.news);
                    ImageView Avatar = (ImageView)layout.findViewById(R.id.news_avatar);
                    TextView Time = (TextView) layout.findViewById(R.id.news_time);
                    TextView Name = (TextView) layout.findViewById(R.id.news_usrname);
                    TextView Title = (TextView)layout.findViewById(R.id.news_title);
                    TextView Tag1 = (TextView)layout.findViewById(R.id.tag1);
                    TextView Tag2 = (TextView)layout.findViewById(R.id.tag2);
                    TextView Tag3 = (TextView)layout.findViewById(R.id.tag3);
                    Avatar.setImageBitmap(bitmap);
                    Name.setText(usrName);
                    Time.setText(time);
                    Title.setText(title);
                    Tag1.setText(tag1);
                    Tag2.setText(tag2);
                    Tag3.setText(tag3);
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendMessage.sendMessage(newId,time,usrName,title,tag1,tag2,tag3,bitmap);
                        }
                    });
                    ListNews.add(layout);
                    linearLayout.addView(layout);
                    break;
                default:
                    break;
            }
        }
    }

    public class newsThread extends Thread {
        Handler nhandler = new newsHander();
        @Override
        public void run() {
                try {
                    String tmp = ServletService.listByPost(ListNewsId.size()+"","news");
                    JSONArray jsonArray = new JSONArray(tmp);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String newId = jsonObject.getString("newid");
                        if(ListNewsId.indexOf(newId)==-1) {
                            ListNewsId.add(newId);
                            String time = jsonObject.getString("time");
                            String title = jsonObject.getString("newtitle");
                            String usrName = jsonObject.getString("name");
                            String avatarPath = jsonObject.getString("avatar");
                            String tag1 = jsonObject.getString("tag1");
                            String tag2 = jsonObject.getString("tag2");
                            String tag3 = jsonObject.getString("tag3");
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = BitmapService.getHttpBitmap(serverPath+avatarPath);
                            msg.getData().putString("newId", newId);
                            msg.getData().putString("time", time);
                            msg.getData().putString("title", title);
                            msg.getData().putString("usrName", usrName);
                            msg.getData().putString("tag1", tag1);
                            msg.getData().putString("tag2", tag2);
                            msg.getData().putString("tag3", tag3);
                            nhandler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                System.out.println("上拉加载");
                new newsThread().start();
                mPullToRefreshView.onFooterRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(final PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 设置更新时间
                // mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
                System.out.println("下拉更新");
                LinearLayout lyNews = (LinearLayout) getView().findViewById(R.id.ly_news);
                LinearLayout lyHot= (LinearLayout) getView().findViewById(R.id.camp_hots);
                ListNews.clear();
                ListNewsId.clear();
                ListHotLy.clear();
                ListHotId.clear();
                lyNews.removeAllViews();
                lyHot.removeAllViews();
                new hotThread().start();
                new newsThread().start();
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1000);

    }

    public class hotHander extends Handler{

        public void handleMessage(final Message msg) {
            final String newId = msg.getData().getString("newId");
            final String time = msg.getData().getString("time");
            final String usrName = msg.getData().getString("usrName");
            final String title = msg.getData().getString("title");
            final String tag1 = msg.getData().getString("tag1");
            final String tag2 = msg.getData().getString("tag2");
            final String tag3 = msg.getData().getString("tag3");
            final Bitmap bitmap = (Bitmap) msg.obj;
            switch (msg.what) {
                //下载时
                case 1:
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.camp_hots);
                    // 获取需要添加的布局
                    LinearLayout layout = (LinearLayout) inflater.inflate(
                            R.layout.hot_item, null).findViewById(R.id.hot);
                    TextView hot_count = (TextView) layout.findViewById(R.id.hot_count);
                    if(ListHotLy.size()<3){
                        hot_count.setTextColor(Color.parseColor("#FF0000"));
                        hot_count.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
                    }else {
                        hot_count.setTextColor(Color.parseColor("#F07F00"));
                        hot_count.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
                        layout.setVisibility(View.GONE);
                    }
                    hot_count.setText(ListHotLy.size()+1+".");
                    TextView hot_text = (TextView) layout.findViewById(R.id.hot_text);
                    hot_text.setText(title);
                    ListHotText.add(hot_text);
                    ListHotLy.add(layout);
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendMessage.sendMessage(newId,time,usrName,title,tag1,tag2,tag3,bitmap);
                        }
                    });
                    linearLayout.addView(layout);
                    break;
                default:
                    break;
            }
        }
    }

    private class hotThread extends Thread {

        Handler hhandler = new hotHander();
        public void run() {

            try {
                String tmp = ServletService.listByPost("0","hot");
                JSONArray jsonArray = new JSONArray(tmp);
                Log.i("news111","hot");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String newId = jsonObject.getString("newid");
                    if(ListHotId.indexOf(newId)==-1){
                        ListHotId.add(newId);
                        String time = jsonObject.getString("time");
                        String title = jsonObject.getString("newtitle");
                        String usrName = jsonObject.getString("name");
                        String avatarPath = jsonObject.getString("avatar");
                        String tag1 = jsonObject.getString("tag1");
                        String tag2 = jsonObject.getString("tag2");
                        String tag3 = jsonObject.getString("tag3");
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = BitmapService.getHttpBitmap(serverPath+avatarPath);
                        msg.getData().putString("newId", newId);
                        msg.getData().putString("time", time);
                        msg.getData().putString("title", title);
                        msg.getData().putString("usrName", usrName);
                        msg.getData().putString("tag1", tag1);
                        msg.getData().putString("tag2", tag2);
                        msg.getData().putString("tag3", tag3);
                        hhandler.sendMessage(msg);

                    }
                        }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sendMessage = (SendMessageCommunitor) activity;
    }

}
