package com.example.jason.mxlake.module;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.mxlake.R;
import com.example.jason.mxlake.schedule.MyDBOpenHelper;
import com.example.jason.mxlake.schedule.SettingActivity;

import java.util.Calendar;
import java.util.LinkedList;


public class ScheduleFragment extends Fragment implements View.OnLongClickListener {
    ImageView Schedule;
    TextView ScheduleOne;
    TextView ScheduleTwo;
    TextView ScheduleThree;
    TextView ScheduleFour;
    TextView ScheduleFive;
    TextView ScheduleSix;
    TextView ScheduleSeven;
    TextView ScheduleEight;
    TextView ScheduleNine;
    TextView ScheduleTen;
    TextView ScheduleSevenFlag;
    LinearLayout LyScheduleUp;
    LinearLayout LySchedule;
    LinearLayout LyScheduleDown;
    View MiClock;
    private MyDBOpenHelper myDBHelper;
    private SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fg_schedule,container,false);
        int width = (int)getArguments().get("width");
        myDBHelper = new MyDBOpenHelper(getContext(), "my.db", null, 1);
        MiClock = (View) view.findViewById(R.id.MiClock);
        RelativeLayout.LayoutParams ParamsClock = (RelativeLayout.LayoutParams) MiClock.getLayoutParams();
        ParamsClock.width = width *11/20;
        ParamsClock.height = width *11/20;
        MiClock.setLayoutParams(ParamsClock);

        Schedule = (ImageView) view.findViewById(R.id.schedule);
        ScheduleOne = (TextView) view.findViewById(R.id.schedule1);
        ScheduleTwo = (TextView) view.findViewById(R.id.schedule2);
        ScheduleThree = (TextView) view.findViewById(R.id.schedule3);
        ScheduleFour = (TextView) view.findViewById(R.id.schedule4);
        ScheduleFive = (TextView) view.findViewById(R.id.schedule5);
        ScheduleSix = (TextView) view.findViewById(R.id.schedule6);
        ScheduleSeven = (TextView) view.findViewById(R.id.schedule7);
        ScheduleEight = (TextView) view.findViewById(R.id.schedule8);
        ScheduleNine = (TextView) view.findViewById(R.id.schedule9);
        ScheduleTen = (TextView) view.findViewById(R.id.schedulex);
        ScheduleSevenFlag = (TextView) view.findViewById(R.id.schedule_seven_flag);
        LyScheduleUp=(LinearLayout) view.findViewById(R.id.scheduleLyout_up);
        LySchedule=(LinearLayout) view.findViewById(R.id.scheduleLyout);
        LyScheduleDown=(LinearLayout) view.findViewById(R.id.scheduleLyout_down);
        LinearLayout.LayoutParams ParamsScheduleTwo = (LinearLayout.LayoutParams) ScheduleTwo.getLayoutParams();
        ParamsScheduleTwo.height = width *3/5;
        ScheduleTwo.setLayoutParams(ParamsScheduleTwo);
        Schedule.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleOne.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleTwo.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleThree.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleFour.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleFive.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleSix.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleSeven.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleEight.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleNine.setOnLongClickListener((View.OnLongClickListener)this);
        ScheduleTen.setOnLongClickListener((View.OnLongClickListener)this);
        new scheduleThread().start();


        return view;
    }


    public class scheduleThread extends Thread {
        Handler shandler = new scheduleHandler();

        public scheduleThread() {

        }

        @Override
        public void run() {
            Calendar ca = Calendar.getInstance();
            int mYear = ca.get(Calendar.YEAR);
            int mMonth = ca.get(Calendar.MONTH);
            int mDay = ca.get(Calendar.DAY_OF_MONTH);
            int week = ca.get(Calendar.DAY_OF_WEEK);
            int hour = ca.get(Calendar.HOUR_OF_DAY);


            switch (hour){
                case 7:
                    ScheduleOne.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                case 8:
                    ScheduleTwo.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                case 9:
                    ScheduleThree.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                case 10:
                    ScheduleFour.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                case 11:
                    ScheduleFive.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                case 12:
                    ScheduleSix.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                case 13:
                    ScheduleSeven.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                case 14:
                    ScheduleEight.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                case 15:
                    ScheduleNine.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                case 16:
                    ScheduleTen.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
                default:
                    ScheduleSevenFlag.setBackgroundColor(Color.parseColor("#FFD0D0"));
                    break;
            }
            /*星期日:Calendar.SUNDAY=1
             *星期一:Calendar.MONDAY=2
             *星期二:Calendar.TUESDAY=3
             *星期三:Calendar.WEDNESDAY=4
             *星期四:Calendar.THURSDAY=5
             *星期五:Calendar.FRIDAY=6
             *星期六:Calendar.SATURDAY=7 */

            db = myDBHelper.getWritableDatabase();
            Cursor cursor =  db.rawQuery("SELECT * FROM schedule WHERE ? BETWEEN startdate AND enddate",
                    new String[]{mYear+"-"+mMonth+"-"+mDay});
            if(cursor!=null&&cursor.moveToFirst()){
                do{
                    String flag = cursor.getString(cursor.getColumnIndex("flag"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String describle = cursor.getString(cursor.getColumnIndex("describle"));
                    for (int i = (week-1)*10;i < week*10;i++){
                        char f = flag.charAt(i);
                        String s = f+"";
                        if(s.equals("1")){
                            Message msg = new Message();
                            msg.what = i-10*(week-1);
                            s = i+"";
                            Log.i("flag3",s);
                            msg.getData().putString("name", name);
                            msg.getData().putString("describle", describle);
                            shandler.sendMessage(msg);

                        }
                    }

                }while(cursor.moveToNext());
            }
            cursor.close();
        }
    }


private class scheduleHandler extends Handler{
    @SuppressLint("WrongConstant")
    public void handleMessage(Message msg) {
        String name = msg.getData().getString("name");
        String describle = msg.getData().getString("describle");
        Log.i("flag3",name);
        switch (msg.what) {
            case 0:
                ScheduleOne.setText("第一节\n8:00-8:50\n"+name+"\n"+describle);
                break;
            case 1:
                ScheduleTwo.setText("第二节\n9:00-9:50\n"+name+"\n"+describle);
                break;
            case 2:
                ScheduleThree.setText("第三节\n10:00-10:50\n"+name+"\n"+describle);
                break;
            case 3:
                ScheduleFour.setText("第四节\n11:00-11:50\n"+name+"\n"+describle);
                break;
            case 4:
                ScheduleFive.setText("第五节\n1:00-1:50\n"+name+"\n"+describle);
                break;
            case 5:
                ScheduleSix.setText("第六节\n2:00-2:50\n"+name+"\n"+describle);
                break;
            case 6:
                ScheduleSeven.setText("第七节\n3:00-3:50\n"+name+"\n"+describle);
                break;
            case 7:
                ScheduleEight.setText("第八节\n4:00-4:50\n"+name+"\n"+describle);
                break;
            case 8:
                ScheduleNine.setText("第九节\n5:00-5:50\n"+name+"\n"+describle);
                break;
            case 9:
                ScheduleTen.setText("第十节\n5:00-6:30\n"+name+"\n"+describle);
                break;
        }
    }
}

    public boolean onLongClick(View v){
        switch (v.getId()) {
            case R.id.schedule:

                Vibrator vibrator = (Vibrator)getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                vibrator.vibrate(100);

                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.schedule1:
                ScheduleOne.setText("button 被长按了");
                break;
            case R.id.schedule2:
                ScheduleTwo.setText("button 被长按了");
                break;
            case R.id.schedule3:
                ScheduleThree.setText("button 被长按了");
                break;
            case R.id.schedule4:
                ScheduleFour.setText("button 被长按了");
                break;
            case R.id.schedule5:
                ScheduleFive.setText("button 被长按了");
                break;
            case R.id.schedule6:
                ScheduleSix.setText("button 被长按了");
                break;
            case R.id.schedule7:
                ScheduleSeven.setText("button 被长按了");
                break;
            case R.id.schedule8:
                ScheduleEight.setText("button 被长按了");
                break;
            case R.id.schedule9:
                ScheduleNine.setText("button 被长按了");
                break;
            case R.id.schedulex:
                ScheduleTen.setText("button 被长按了");
                break;
        }
        return true;
    }

}