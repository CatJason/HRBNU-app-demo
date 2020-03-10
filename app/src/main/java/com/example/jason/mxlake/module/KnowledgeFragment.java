package com.example.jason.mxlake.module;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.mxlake.R;
import com.example.jason.mxlake.service.ServletService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;


public class KnowledgeFragment extends Fragment {


    private String serverPath = "http://192.168.56.1:8080/wangzhijun/file/";
    private LinkedList<TextView> ListDirItem = new LinkedList<TextView>();
    private LinkedList<LinearLayout> ListClassFileLy= new LinkedList<LinearLayout>();
    private ArrayList<String> ListDirId = new ArrayList<String>();
    private ArrayList<String> ListFilePath = new ArrayList<String>();
    private LinkedList<TextView> ListPreDir = new LinkedList<TextView>();
    private LinkedList<Button> ListTestButton = new LinkedList<Button>();




    Handler mHandler;
    int knowlege_class_page = 1;
    int knowlege_test_page = 1;
    int aimpage = 1;
    int pagecount = 20;
    int flag = 0;

    public KnowledgeFragment() {
    }



    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fg_knowledge, container, false);

        final int width = (int) getArguments().get("width");



        final EditText setPage = (EditText)view.findViewById(R.id.setpage);
        setPage.setWidth(width/8);
        final TextView sumPage = (TextView)view.findViewById(R.id.sumpage);
        sumPage.setWidth(width/8);

        final Button upPage = (Button)view.findViewById(R.id.uppage);
        final Button nextPage = (Button)view.findViewById(R.id.nextpage);
        final Button jumpPage = (Button)view.findViewById(R.id.jumppage);

        upPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1){
                    aimpage=knowlege_class_page-1;
                }else if(flag == 3){
                    aimpage=knowlege_test_page-1;
                }
                if(aimpage==1){
                    upPage.setEnabled(false);
                    upPage.setText("已到首页");
                }else{
                    upPage.setEnabled(true);
                    upPage.setText("上一页");
                }
                    if(aimpage * pagecount>=ListClassFileLy.size()&&knowlege_class_page * pagecount < ListClassFileLy.size()) {

                        nextPage.setEnabled(false);
                        nextPage.setText("已到尾页");
                    }
                    else{
                        nextPage.setEnabled(true);
                        nextPage.setText("下一页");
                    }

                    int mintmp = (knowlege_class_page-1)*pagecount;

                int maxtmp = knowlege_class_page*pagecount;

                if(ListClassFileLy.size()<maxtmp){
                    maxtmp = ListClassFileLy.size();
                }
                for(int i = mintmp;i<maxtmp;i++){
                    ListClassFileLy.get(i).setVisibility(View.GONE);
                }
                mintmp = (aimpage-1)*pagecount;
                maxtmp = aimpage*pagecount;
                if(ListClassFileLy.size()<maxtmp){
                    maxtmp = ListClassFileLy.size();
                }
                for(int i = mintmp;i<maxtmp;i++){
                    ListClassFileLy.get(i).setVisibility(View.VISIBLE);
                }
                knowlege_class_page = aimpage;


            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1){
                    aimpage=knowlege_class_page-1;
                }else if(flag == 3){
                    aimpage=knowlege_test_page-1;
                }
                    aimpage=knowlege_class_page+1;
                    if(aimpage * pagecount>=ListClassFileLy.size()&&knowlege_class_page * pagecount < ListClassFileLy.size()) {

                        nextPage.setEnabled(false);
                        nextPage.setText("已到尾页");
                    }
                    else{
                        nextPage.setEnabled(true);
                        nextPage.setText("下一页");
                    }

                    if(aimpage==1){
                        upPage.setEnabled(false);
                        upPage.setText("已到首页");
                    }else{
                        upPage.setEnabled(true);
                        upPage.setText("上一页");
                    }

                    int mintmp = (knowlege_class_page-1)*pagecount;
                    int maxtmp = knowlege_class_page*pagecount;
                    if(ListClassFileLy.size()<maxtmp){
                        maxtmp = ListClassFileLy.size();
                    }
                    for(int i = mintmp;i<maxtmp;i++){
                        ListClassFileLy.get(i).setVisibility(View.GONE);

                    }
                    mintmp = (aimpage-1)*pagecount;
                    maxtmp = aimpage*pagecount;
                    if(ListClassFileLy.size()<maxtmp){
                        maxtmp = ListClassFileLy.size();
                    }
                    for(int i = mintmp;i<maxtmp;i++){
                        ListClassFileLy.get(i).setVisibility(View.VISIBLE);

                    }

                    knowlege_class_page = aimpage;

            }
        });




        jumpPage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {

                int pagetmp = Integer.parseInt(setPage.getText().toString());

                if(pagetmp <= (ListClassFileLy.size()+pagecount-1)/pagecount){

                        aimpage=pagetmp;
                        if(aimpage * pagecount>=ListClassFileLy.size()&&knowlege_class_page * pagecount < ListClassFileLy.size()) {
                            nextPage.setEnabled(false);
                            nextPage.setText("已到尾页");
                        }
                        else{
                            nextPage.setEnabled(true);
                            nextPage.setText("下一页");
                        }

                        if(aimpage==1){
                            upPage.setEnabled(false);
                            upPage.setText("已到首页");
                        }else{
                            upPage.setEnabled(true);
                            upPage.setText("上一页");
                        }

                        int mintmp = (knowlege_class_page-1)*pagecount;
                        int maxtmp = knowlege_class_page*pagecount;
                        if(ListClassFileLy.size()<maxtmp){
                            maxtmp = ListClassFileLy.size();
                        }
                        for(int i = mintmp;i<maxtmp;i++){
                            ListClassFileLy.get(i).setVisibility(View.GONE);

                        }
                        mintmp = (aimpage-1)*pagecount;
                        maxtmp = aimpage*pagecount;
                        if(ListClassFileLy.size()<maxtmp){
                            maxtmp = ListClassFileLy.size();
                        }
                        for(int i = mintmp;i<maxtmp;i++){
                            ListClassFileLy.get(i).setVisibility(View.VISIBLE);

                        }

                        knowlege_class_page = aimpage;

                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "超过最大页数", 1).show();
                }
            }
        });



        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        //完成主界面更新,拿到数据
                        String dirName = msg.getData().getString("dirname");
                        String dirId = msg.getData().getString("dirid");
                        ListDirId.add(dirId);
                        final int dirid = Integer.parseInt(dirId);
                        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.file_system);
                        // 获取需要添加的布局

                        final TextView dirItem = (TextView) inflater.inflate(
                                R.layout.dir_ly_item, null).findViewById(R.id.dir_item);
                        dirItem.setTextColor(Color.parseColor("#ffffff"));
                        dirItem.setText(dirName);
                        ListDirItem.add(dirItem);

                        dirItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(knowlege_class_page==1){
                                    upPage.setEnabled(false);
                                    upPage.setText("已到首页");
                                }
                                flag=1;
                                int btnflag = 0;
                                TextView textViewTmp = new TextView(getContext());
                                for(int i = 0; i < ListDirId.size(); i++){
                                    if(v == ListDirItem.get(i)){
                                        textViewTmp = ListDirItem.get(i);
                                        btnflag = 1;
                                    }
                                }
                                linearLayout.removeAllViews();
                                textViewTmp.setTextColor(Color.parseColor("#000000"));
                                textViewTmp.setBackgroundColor(Color.parseColor("#30ffffff"));
                                textViewTmp.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                                if(btnflag == 1) {
                                    String tmp = "";
                                    for(int i = 0; i<ListPreDir.size(); i++){
                                        tmp = tmp + "\t\t\t\t";
                                    }
                                    textViewTmp.setText(tmp+"|__"+textViewTmp.getText().toString());
                                    ListPreDir.add(textViewTmp);
                                    Log.i("3333a",ListPreDir.size()+"");
                                }

                                ListDirItem.clear();
                                ListDirId.clear();
                                ListClassFileLy.clear();
                                ListFilePath.clear();

                                for(int i = 0; i < ListPreDir.size(); i++){
                                    linearLayout.addView(ListPreDir.get(i));
                                    Log.i("3333b",ListPreDir.size()+"");
                                    if(v==ListPreDir.get(i)){
                                        while(i+1<ListPreDir.size()){
                                            ListPreDir.remove(i+1);
                                        }
                                        break;
                                    }
                                }


                                btnflag = 0;
                                new konwledgeThread("0",dirid).start();
                            }
                        });

                        linearLayout.addView(dirItem);

                        int pagesumtmp0 = ListTestButton.size()/pagecount;
                        if(ListDirItem.size()==pagesumtmp0){
                            sumPage.setText(knowlege_class_page+"/"+pagesumtmp0);
                        }else{
                            pagesumtmp0 ++;
                            sumPage.setText(knowlege_class_page+"/"+pagesumtmp0);
                        }
                        break;

                    case 1:
                    //完成主界面更新,拿到数据
                        final String fileName = msg.getData().getString("filename");
                        String filePath = msg.getData().getString("filepath");
                        String name = msg.getData().getString("name");
                        String time = msg.getData().getString("time");
                        ListFilePath.add(filePath);
                        LinearLayout fileLinearLayout = (LinearLayout) view.findViewById(R.id.file_system);
                        // 获取需要添加的布局
                        final LinearLayout classFileLy = (LinearLayout) inflater.inflate(
                                R.layout.knowledge_item, null).findViewById(R.id.know_file);
                        final TextView knowName = classFileLy.findViewById(R.id.know_name);
                        TextView knowTime = classFileLy.findViewById(R.id.know_upload_time);
                        TextView knowUersName = classFileLy.findViewById(R.id.know_uesrname);
                        knowName.setText(fileName);
                        knowTime.setText(time);
                        knowUersName.setText(name);
                        ListClassFileLy.add(classFileLy);

                        classFileLy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int number = -1;
                                for(int i = 0; i < ListFilePath.size(); i++){

                                    if(v == ListClassFileLy.get(i)){
                                        number = i;
                                        break;
                                    }
                                }
                                   sendMessage.sendMessage(serverPath+ListFilePath.get(number),fileName);
                                knowName.setTextColor(Color.parseColor("#FF0000"));
                            }
                        });

                        if(ListClassFileLy.size()<(knowlege_class_page-1)*pagecount+1){
                            classFileLy.setVisibility(View.GONE);
                        }else if(ListClassFileLy.size()>knowlege_class_page*pagecount){
                            classFileLy.setVisibility(View.GONE);
                        }
                        fileLinearLayout.addView(classFileLy);
                        int pagesumtmp = ListDirItem.size()/pagecount;
                        if(ListDirItem.size()==pagesumtmp){
                            sumPage.setText(knowlege_class_page+"/"+pagesumtmp);
                        }else{
                            pagesumtmp ++;
                            sumPage.setText(knowlege_class_page+"/"+pagesumtmp);
                        }
                        break;
                    default:
                        break;
                }
            }

        };

        new konwledgeThread("0",0).start();


        final Button knowledgeRefresh = (Button) view.findViewById(R.id.knowledge_refresh);
        final LinearLayout fileSystem = (LinearLayout) view.findViewById(R.id.file_system);
        knowledgeRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDirId.clear();
                ListDirItem.clear();
                ListClassFileLy.clear();
                ListFilePath.clear();
                ListPreDir.clear();
                View view = null;
                for(int index = fileSystem.getChildCount();index >= 0;index --){
                    view = fileSystem.getChildAt(index);
                    fileSystem.removeView(view);
                }
                fileSystem.setVisibility(View.VISIBLE);
                new konwledgeThread("0",0).start();
            }
        });


        return view;
    }


    private SendMessageCommunitor sendMessage;


    public class konwledgeThread extends Thread {
        String count;
        int flag;

        public konwledgeThread(String count,int flag) {
            this.count = count;
            this.flag = flag;
        }

        @Override
        public void run() {

                try {
                    JSONArray jsonArray = new JSONArray(ServletService.fileByPost(count,flag+"","getDir"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            String dirName = jsonObject.getString("dirname");
                            String dirId = jsonObject.getString("dirid");
                            Message msg = new Message();
                            msg.what = 0;
                            msg.getData().putString("dirname", dirName);
                            msg.getData().putString("dirid", dirId);
                            mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            try {

                JSONArray jsonArray = new JSONArray(ServletService.fileByPost(count,flag+"","getFile"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String fileName = jsonObject.getString("filename");
                    String filePath = jsonObject.getString("filepath");
                    String time = jsonObject.getString("time");
                    String name = jsonObject.getString("name");
                    Message msg = new Message();
                    msg.what = 1;
                    msg.getData().putString("filename", fileName);
                    msg.getData().putString("filepath", filePath);
                    msg.getData().putString("name", name);
                    msg.getData().putString("time", time);
                    mHandler.sendMessage(msg);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}

