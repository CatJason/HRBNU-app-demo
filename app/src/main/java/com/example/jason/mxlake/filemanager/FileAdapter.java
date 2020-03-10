package com.example.jason.mxlake.filemanager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.jason.mxlake.R;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/2/5/005.
 */

public class FileAdapter extends BaseAdapter {
    //传递File数据和上下文；
    List<File> list;
    Context context;

    public FileAdapter(Context context,List<File> list) {
        this.list = list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= View.inflate(context,R.layout.item_file,null);

        File file=list.get(i);
        ImageView image_photo= (ImageView) view.findViewById(R.id.file_image);
        TextView tv_name= (TextView) view.findViewById(R.id.filename);
        TextView tv_age= (TextView) view.findViewById(R.id.isDictionary);

        //如果某个文件是目录：就在后面显示》；否则显示空
        if (file.isDirectory()){
            if(file.getName().equals("music")){
                tv_name.setText("音乐");
                image_photo.setImageResource(R.drawable.music);
            }
            if(file.getName().equals("document")){
                tv_name.setText("文档");
                image_photo.setImageResource(R.drawable.document);
            }
            if(file.getName().equals("video")){
                tv_name.setText("视频");
                image_photo.setImageResource(R.drawable.video);
            }
            if(file.getName().equals("picture")){
                tv_name.setText("图片");
                image_photo.setImageResource(R.drawable.picture);
            }
            if(file.getName().equals("app")){
                tv_name.setText("应用");
                image_photo.setImageResource(R.drawable.app);
            }
            if(file.getName().equals("zip")){
                tv_name.setText("压缩包");
                image_photo.setImageResource(R.drawable.zip);
            }
            tv_age.setText("▶");
        }
        else {
            String filename = file.getName().substring(file.getName().lastIndexOf('.')+1);
            Log.i("filename",filename);

            if(filename.equals("txt")){
                image_photo.setImageResource(R.drawable.txt);
            }else if(filename.equals("ME")){
                image_photo.setImageResource(R.drawable.avatar);
            }else if(filename.equals("doc")||filename.equals("docx")){
                image_photo.setImageResource(R.drawable.doc);
            }
            tv_age.setText("打开");
        }

        return view;

    }
}