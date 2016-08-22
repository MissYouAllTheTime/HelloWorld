package com.example.timetest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout countDown;
    // 倒计时
    private TextView daysTv, hoursTv, minutesTv, secondsTv;
    private long mDay = 2;
    private long mHour = 1;
    private long mMin = 1;
    private long mSecond = 15;// 天 ,小时,分钟,秒
    private boolean isRun = true;
    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                daysTv.setText(mDay + "");
                if (mHour < 10) {
                    hoursTv.setText("0" + mHour);
                } else {
                    hoursTv.setText(mHour + "");
                }
                if (mMin < 10) {
                    minutesTv.setText("0" + mMin);
                } else {
                    minutesTv.setText(mMin + "");
                }
                if (mSecond < 10) {
                    secondsTv.setText("0" + mSecond);
                } else {
                    secondsTv.setText(mSecond + "");
                }

                if (mDay == 0 && mHour == 0 && mMin == 0 && mSecond == 0) {
                    countDown.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "时间倒计完毕", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDown = (RelativeLayout) findViewById(R.id.countdown_layout);
        daysTv = (TextView) findViewById(R.id.days_tv);
        hoursTv = (TextView) findViewById(R.id.hours_tv);
        minutesTv = (TextView) findViewById(R.id.minutes_tv);
        secondsTv = (TextView) findViewById(R.id.seconds_tv);

        startRun();
    }

    /**
     * 开启倒计时
     */
    private void startRun() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isRun) {
                    try {
                        Thread.sleep(1000); // sleep 1000ms
                        Message message = Message.obtain();
                        message.what = 1;
                        timeHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                }
            }
        }
    }
}
