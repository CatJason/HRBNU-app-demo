package com.example.jason.mxlake.filemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jason.mxlake.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/5/005.
 */

public class FileManagerActivity extends Activity {

    ListView listView;
    TextView title;
    String dir;
    //用存放路劲
    FileAdapter adapter;
    //适配器
    List<File> dateList;

    //File 数据
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_manager);

        findVById();
        init();
        //初始化
    }

    private void findVById() {
        listView = (ListView) findViewById(R.id.file_listview);
        title = (TextView) findViewById(R.id.file_title);
    }

    //初始化
    private void init() {

        Intent intent = getIntent();
        //获取Intent的，接收activity传来的值，

        dir = intent.getStringExtra("dir");
        //如果为null，dir的值为 ：Environment.getExternalStorageDirectory().getAbsolutePath();
        //这个路劲就是一般打开手机文件管理文件目录的路劲
        if (dir != null)
            ;
        else
            dir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"mxlake";

        //获取title：让其显示文件路劲：如Android>data>com......
        if (intent.getStringExtra("title") != null)
            title.setText(intent.getStringExtra("title"));
        else
            title.setText("文件管理");

        //为listView注册上下文菜单，当长按某一个文件出现菜单：
        this.registerForContextMenu(listView);

        dateList = new ArrayList<>();
        adapter = new FileAdapter(this, getDate());
        listView.setAdapter(adapter);

        //listView 点击事件，当点击的文件为目录时，
        // 把dir的值赋值为：dir+点击的目录，再次跳到此页，既可以达到循环，不要再去新建一个activity在现实：
        // intent.putExtra("dir",dir+"/"+dateList.get(i).getName());
        //intent.putExtra("title",title.getText()+">"+dateList.get(i).getName());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (dateList.get(i).isDirectory()) {
                    Intent intent = new Intent(FileManagerActivity.this, FileManagerActivity.class);
                    intent.putExtra("dir", dir + "/" + dateList.get(i).getName());
                    if(dateList.get(i).getName().equals("music")){
                        intent.putExtra("title", title.getText() + ">" + "音乐");
                    }else if(dateList.get(i).getName().equals("document")){
                        intent.putExtra("title", title.getText() + ">" + "文档");
                    }else if(dateList.get(i).getName().equals("video")){
                        intent.putExtra("title", title.getText() + ">" + "视频");
                    }else if(dateList.get(i).getName().equals("picture")){
                        intent.putExtra("title", title.getText() + ">" + "图片");
                    }else if(dateList.get(i).getName().equals("app")){
                        intent.putExtra("title", title.getText() + ">" + "应用");
                    }else if(dateList.get(i).getName().equals("zip")){
                        intent.putExtra("title", title.getText() + ">" + "压缩包");
                    }
                    else{
                        intent.putExtra("title", title.getText() + ">" + dateList.get(i).getName());
                    }
                    startActivity(intent);
                }
            }
        });
    }


    //为上下文菜单添加菜单项
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("文件操作");
        menu.setHeaderIcon(R.drawable.avatar);
        menu.add(1, 1, 1, "复制");
        menu.add(1, 2, 1, "粘贴");
        menu.add(1, 3, 1, "剪切");
        menu.add(1, 4, 1, "重命名");
    }



    //选中菜单项点击事件，这里就Toast一下，
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(FileManagerActivity.this, "已复制", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(FileManagerActivity.this, "已粘贴", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(FileManagerActivity.this, "剪切", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(FileManagerActivity.this, "重命名", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    //获取dir下所有的文件
    public List<File> getDate() {

        File file = new File(dir);
        if (file.exists()) {
            File[] file1 = file.listFiles();
            for (File filename :
                    file1) {
                dateList.add(filename);
            }
        }

        return dateList;
    }
}