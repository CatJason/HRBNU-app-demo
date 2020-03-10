package com.example.jason.mxlake.otheractivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.mxlake.R;
import com.example.jason.mxlake.service.ServletService;
import com.luozm.captcha.Captcha;

public class SignActivity extends Activity {
    private Captcha captcha;
    private TextView btnMode;
    private int flag = 0;
    private EditText signName;
    private EditText signAccount;
    private EditText signPassword;
    private EditText checkSignPassword;
    private TextView catDog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initCaptcha();
    }


    private void initCaptcha(){
        captcha = (Captcha) findViewById(R.id.captCha);
        btnMode = (TextView) findViewById(R.id.btn_mode);
        catDog= (TextView) findViewById(R.id.cat_dog);
        signAccount = (EditText) findViewById(R.id.sign_account);
        signName = (EditText) findViewById(R.id.sign_name);
        signPassword = (EditText) findViewById(R.id.sign_password);
        checkSignPassword = (EditText) findViewById(R.id.check_sign_password);
        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captcha.getMode() == Captcha.MODE_BAR) {
                    captcha.setMode(Captcha.MODE_NONBAR);
                    btnMode.setText("滑动条模式");
                } else {
                    captcha.setMode(Captcha.MODE_BAR);
                    btnMode.setText("无滑动条模式");
                }
            }
        });
        captcha.setSeekBarStyle(R.drawable.po_seekbar,R.drawable.thumb);
        captcha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public String onAccess(long time) {
                Toast.makeText(SignActivity.this, "验证成功", Toast.LENGTH_SHORT).show();

                new Thread(){
                    public void run(){
                        if(signPassword.getText().toString().equals(checkSignPassword.getText().toString())){
                            String tmp = ServletService.signByPost(signAccount.getText().toString(),signPassword.getText().toString(),signName.getText().toString());
                            if(tmp.equals("sucess")) {
                                finish();
                            }
                        }

                    }
                }.start();
                return "验证通过";
            }

            @Override
            public String onFailed(int count) {
                Toast.makeText(SignActivity.this, "验证失败,失败次数" + count, Toast.LENGTH_SHORT).show();
                return "验证失败";
            }

            @Override
            public String onMaxFailed() {
                Toast.makeText(SignActivity.this, "验证超过次数，你的帐号被封锁", Toast.LENGTH_SHORT).show();
                return "可以走了";
            }

        });

    }


    boolean isCat = true;
    public void changePicture(View view){
        if(isCat){
            catDog.setText("通过猫咪的验证");
            captcha.setBitmap(R.drawable.dog_recognition);
        }else{
            catDog.setText("通过哈士奇的验证");
            captcha.setBitmap(R.drawable.cat_recognition);
        }
        isCat=!isCat;
    }


}
