package com.example.jason.mxlake.schedule;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.mxlake.R;
import java.util.ArrayList;
import java.util.LinkedList;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    int[][] flag = new int[7][10];
    private Context mContext;
    private Button btn_insert;
    private Button name_choose;
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private EditText startYY;
    private EditText startMM;
    private EditText startDD;
    private EditText endYY;
    private EditText endMM;
    private EditText endDD;
    private EditText className;
    private EditText classDescrible;
    private TextView addRule;
    private RelativeLayout ruleLySet;
    private RelativeLayout weekRules;
    private LinkedList<TextView> ListClassName = new LinkedList<TextView>();
    private ArrayList<String> ListcNameString = new ArrayList<String>();
    int ruleNum = 0;

    //File 数据
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_setting);
        mContext = SettingActivity.this;
        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);
        bindViews();
        int id = 1;
        while(true){
            if(findClassName(id)==null){
                break;
            }else
            {
                LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
                // 获取需要被添加控件的布局
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_cname);
                // 获取需要添加的布局
                final TextView mText = (TextView) inflater.inflate(
                        R.layout.classname_text, null).findViewById(R.id.classNameText);
                mText.setText(findClassName(id));
                final int finalId = id;
                mText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int j = 0;j<ListClassName.size();j++){
                            ListClassName.get(j).setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        }
                        mText.setBackgroundColor(Color.parseColor("#FF4081"));
                        className.setText(mText.getText());
                        classDescrible.setText(findClassDescrible(finalId));
                    }
                });
                linearLayout.addView(mText);
                id++;
                ListClassName.add(mText);
            }
        }
    }
    public void bindViews() {
        //初始化
        startYY = (EditText) findViewById(R.id.start_yy);
        startMM = (EditText) findViewById(R.id.start_mm);
        startDD = (EditText) findViewById(R.id.start_dd);
        endYY = (EditText) findViewById(R.id.end_yy);
        endMM = (EditText) findViewById(R.id.end_mm);
        endDD = (EditText) findViewById(R.id.end_dd);
        className = (EditText) findViewById(R.id.set_cname);
        classDescrible = (EditText) findViewById(R.id.set_cdescrible);
        addRule = (TextView) findViewById(R.id.add_rule);
        ruleLySet = (RelativeLayout) findViewById(R.id.rule_lyset);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 10; j++) {
                flag[i][j] = 0;
            }
        }
        int p = 0;
        int q = 0;
        for (q = 0; q < 10; q++) {
            LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
            // 获取需要被添加控件的布局
            LinearLayout weekSet = (LinearLayout) findViewById(R.id.week_set);
            // 获取需要添加的布局
            LinearLayout textViews = (LinearLayout) inflater.inflate(
                    R.layout.seven_textview, null).findViewById(R.id.textviews);
            String tmp = null;
            switch (q) {
                case 0:
                    tmp = "一";
                    break;
                case 1:
                    tmp = "二";
                    break;
                case 2:
                    tmp = "三";
                    break;
                case 3:
                    tmp = "四";
                    break;
                case 4:
                    tmp = "五";
                    break;
                case 5:
                    tmp = "六";
                    break;
                case 6:
                    tmp = "七";
                    break;
                case 7:
                    tmp = "八";
                    break;
                case 8:
                    tmp = "九";
                    break;
                case 9:
                    tmp = "十";
                    break;
            }
            for (p = 0; p < 7; p++) {
                final TextView textView = (TextView) textViews.getChildAt(p);
                textView.setText(tmp);

                final int finalP = p;
                final int finalQ = q;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag[finalP][finalQ] == 0) {
                            flag[finalP][finalQ] = 1;
                            textView.setBackgroundColor(Color.parseColor("#FF4081"));
                        } else {
                            flag[finalP][finalQ] = 0;
                            textView.setBackgroundColor(Color.parseColor("#00ffffff"));
                        }
                    }
                });
            }
            weekSet.addView(textViews);
        }
        btn_insert = (Button) findViewById(R.id.set_set);
        btn_insert.setOnClickListener(this);
        addRule.setOnClickListener(this);
        name_choose = (Button) findViewById(R.id.set_name);
        name_choose.setOnClickListener(this);
        weekRules = (RelativeLayout)findViewById(R.id.week_rules);
    }



    public String findClassName(Integer id)
    {

        db = myDBHelper.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM schedule WHERE classid = ?",
                new String[]{id.toString()});
        String classname = null;
        //存在数据才返回true
        if(cursor.moveToFirst())
        {
            classname = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
        return classname;
    }

    public String findClassDescrible(Integer id)
    {

        db = myDBHelper.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM schedule WHERE classid = ?",
                new String[]{id.toString()});
        String classdescrible = null;
        //存在数据才返回true
        if(cursor.moveToFirst())
        {
            classdescrible = cursor.getString(cursor.getColumnIndex("describle"));
        }
        cursor.close();
        return classdescrible;
    }


    @Override
    public void onClick(View v) {

        db = myDBHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.set_set:

                String string = "";

                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 10; j++) {
                        string = string+flag[i][j];
                    }
                }
                Log.i("flag",string);
                ContentValues values = new ContentValues();
                values.put("name", className.getText().toString());
                values.put("describle", classDescrible.getText().toString());
                values.put("flag", string);
                values.put("startdate",  startYY.getText().toString()+"-"+startMM.getText().toString()+"-"+startDD.getText().toString());
                values.put("enddate",  endYY.getText().toString()+"-"+endMM.getText().toString()+"-"+endDD.getText().toString());
                //参数依次是：表名，强行插入null值得数据列的列名，一行记录的数据
                db.insert("schedule", null, values);
                Toast.makeText(mContext, "插入完毕~", Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
                // 获取需要被添加控件的布局
                LinearLayout rules = (LinearLayout) findViewById(R.id.rules);
                // 获取需要添加的布局
                LinearLayout rule = (LinearLayout) inflater.inflate(
                        R.layout.rule, null).findViewById(R.id.lyrule);
                TextView ruleCname =  (TextView) rule.findViewById(R.id.rule_cname);
                TextView ruleData =  (TextView) rule.findViewById(R.id.rule_data);
                if(ruleNum>0){
                    ruleCname.setVisibility(View.GONE);
                }else{
                    ruleCname.setText(className.getText().toString());
                }
                ruleData.setText(startYY.getText().toString()+"-"+startMM.getText().toString()+"-"+startDD.getText().toString()+"至"+endYY.getText().toString()+"-"+endMM.getText().toString()+"-"+endDD.getText().toString());
                rules.addView(rule);
                ruleNum++;
                startYY.setText(null);
                startMM.setText(null);
                startDD.setText(null);
                endYY.setText(null);
                endMM.setText(null);
                endDD.setText(null);
                className.setText(null);
                classDescrible.setText(null);
                ruleLySet.setVisibility(View.GONE);
                break;
            case R.id.add_rule:
                ruleLySet.setVisibility(View.VISIBLE);
                break;
            case R.id.set_name:
                weekRules.setVisibility(View.VISIBLE);

                db = myDBHelper.getWritableDatabase();
                Cursor cursor =  db.rawQuery("SELECT * FROM schedule WHERE name = ?",
                        new String[]{className.getText().toString()});
                String startDate = null;
                String endDate = null;
                //存在数据才返回true
                if(cursor.moveToFirst())
                {
                    startDate = cursor.getString(cursor.getColumnIndex("startdate"));
                    endDate = cursor.getString(cursor.getColumnIndex("enddate"));
                    LayoutInflater hasInflater = LayoutInflater.from(SettingActivity.this);
                    // 获取需要被添加控件的布局
                    LinearLayout hasRules = (LinearLayout) findViewById(R.id.rules);
                    // 获取需要添加的布局
                    LinearLayout hasRule = (LinearLayout) hasInflater.inflate(
                            R.layout.rule, null).findViewById(R.id.lyrule);
                    hasRules.removeAllViews();
                    ruleNum = 0;
                    TextView hasRuleCname =  (TextView) hasRule.findViewById(R.id.rule_cname);
                    TextView hasRuleData =  (TextView) hasRule.findViewById(R.id.rule_data);
                    hasRuleCname.setText(className.getText().toString());
                    hasRuleData.setText(startDate+"至"+endDate);
                    hasRules.addView(hasRule);
                    ruleNum++;
                }
                while(cursor.moveToNext()){
                    startDate = cursor.getString(cursor.getColumnIndex("startdate"));
                    endDate = cursor.getString(cursor.getColumnIndex("enddate"));
                    LayoutInflater hasInflater = LayoutInflater.from(SettingActivity.this);
                    // 获取需要被添加控件的布局
                    LinearLayout hasRules = (LinearLayout) findViewById(R.id.rules);
                    // 获取需要添加的布局
                    LinearLayout hasRule = (LinearLayout) hasInflater.inflate(
                            R.layout.rule, null).findViewById(R.id.lyrule);
                    TextView hasRuleCname =  (TextView) hasRule.findViewById(R.id.rule_cname);
                    TextView hasRuleData =  (TextView) hasRule.findViewById(R.id.rule_data);
                    hasRuleCname.setVisibility(View.GONE);
                    hasRuleData.setText(startDate+"至"+endDate);
                    hasRules.addView(hasRule);
                    ruleNum++;
                }
                cursor.close();


                break;
        }
    }
}
