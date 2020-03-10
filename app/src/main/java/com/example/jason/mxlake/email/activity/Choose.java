package com.example.jason.mxlake.email.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.example.jason.mxlake.R;
public class Choose extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_choose);
        LinearLayout outlook = findViewById(R.id.email_outlook);
        LinearLayout gmail = findViewById(R.id.email_gmail);
        LinearLayout sina = findViewById(R.id.email_sina);
        LinearLayout qq = findViewById(R.id.email_qq);
        LinearLayout sohu = findViewById(R.id.email_sohu);

        outlook.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Choose.this, Login.class);
                intent.putExtra("address","@outlook.com" );
                startActivity(intent);
            }
        });

        gmail.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Choose.this, Login.class);
                intent.putExtra("address","@gmail.com" );
                startActivity(intent);
            }
        });

        sina.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Choose.this, Login.class);
                intent.putExtra("address","@sina.com" );
                startActivity(intent);
            }
        });

        qq.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Choose.this, Login.class);
                intent.putExtra("address","@qq.com" );
                startActivity(intent);
            }
        });

        sohu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Choose.this, Login.class);
                intent.putExtra("address","@sohu.com" );
                startActivity(intent);
            }
        });



    }

}
