package com.example.jason.mxlake.email.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

import com.example.jason.mxlake.R;

import java.util.Timer;
import java.util.TimerTask;


public class Login extends Activity {

    private EditText txtEmailAddress;
    private EditText txtPWD;
    private Button btnOK;
    private Handler handler;
    private View cursor;
    private int flag = 0;

    private static final String SAVE_INFORMATION = "save_information";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_welcome);

        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        String address = intent.getStringExtra("address");
        cursor = (View)findViewById(R.id.cursor);
        txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);
        txtPWD = (EditText) findViewById(R.id.txtPWD);
        btnOK = (Button) findViewById(R.id.btnOK);
        txtEmailAddress.setText(address);

        btnOK.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View v) {


                if (TextUtils.isEmpty(txtEmailAddress.getText().toString())){
                    Toast.makeText(getApplicationContext(), "账号不可以为空", 1).show();
                } else if(TextUtils.isEmpty(txtPWD.getText().toString())){
                    Toast.makeText(getApplicationContext(), "密码不可以为空", 1).show();
                }else {

                    Editor editor = getSharedPreferences(
                            SAVE_INFORMATION, MODE_PRIVATE).edit();
                    editor.putString("save", txtEmailAddress.getText().toString()
                            + ";" + txtPWD.getText().toString());
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(Login.this, ReceiveList.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        cursor.setVisibility(View.GONE);
                        break;
                    case 1:
                        cursor.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

        };

        new Timer().schedule(new TimerTask()

        {



            public void run()

            {

                Message msg = new Message();
                if(flag==1){
                    msg.what = 0;
                    flag = 0;
                }else{
                    msg.what = 1;
                    flag = 1;
                }
                handler.sendMessage(msg);

            }

        },0,500);



















    }
}
