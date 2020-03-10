package com.example.jason.mxlake.module;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.mxlake.R;
import com.example.jason.mxlake.changeavatar.activity.ModifyAvatarActivity;
import com.example.jason.mxlake.email.activity.Choose;
import com.example.jason.mxlake.filemanager.FileManagerActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.io.ByteArrayOutputStream;
import cz.msebera.android.httpclient.Header;

public class MyFragment extends Fragment implements View.OnClickListener{

    private Button btn_two;
    private Button btn_three;
    private Button btn_four;

    public MyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_my,container,false);
        LinearLayout localFile = (LinearLayout) view.findViewById(R.id.local_file);
        localFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), FileManagerActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout email= (LinearLayout) view.findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Choose.class);
                startActivity(intent);
            }
        });
        final ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
        int width = (int)getArguments().get("width");
        //UI Object
        btn_two = (Button) view.findViewById(R.id.btn_two);
        btn_three = (Button) view.findViewById(R.id.btn_three);
        btn_four = (Button) view.findViewById(R.id.btn_four);
        //Bind Event
        avatar.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        int avatar_size = width/4;
        int bg_height = width/8;
        LinearLayout.LayoutParams paramLinearLayout = new LinearLayout.LayoutParams(avatar_size,avatar_size);
        avatar.setLayoutParams(paramLinearLayout);
        final TextView Tred = (TextView) view.findViewById(R.id.usr_bg_red);
        final TextView Twhite = (TextView) view.findViewById(R.id.usr_bg_white);
        Tred.setHeight(bg_height);
        Twhite.setHeight(bg_height);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_two:
                TextView tab_learn_num = (TextView) getActivity ().findViewById(R.id.tab_learn_num);
                tab_learn_num.setText("20");
                tab_learn_num.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_three:
                TextView tab_schedule_num = (TextView) getActivity ().findViewById(R.id.tab_schedule_num);
                tab_schedule_num.setText("99+");
                tab_schedule_num.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_four:
                TextView tab_doge_doge = (TextView) getActivity ().findViewById(R.id.tab_doge_num);
                tab_doge_doge.setText("20");
                tab_doge_doge.setVisibility(View.VISIBLE);
                break;
            case R.id.avatar:
                Intent intent = new Intent(getActivity(), ModifyAvatarActivity.class);
                startActivity(intent);
        }
    }

    private void sendImage(Bitmap bm)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("img", img);
        client.post("http://192.168.56.1:8080/wangzhijun/upload", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Toast.makeText(getActivity(), "Upload Success!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "Upload Fail!", Toast.LENGTH_LONG).show();
            }
        });
    }

}