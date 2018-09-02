package com.example.administrator.second;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button start_btn,pause_btn;
    private static SeekBar seekBar;
    private MyConn conn;
    private CallBackInterface callBackInterface;
    private boolean isContinue = false;
    public static Handler hander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int duration = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");

            seekBar.setMax(duration);
            seekBar.setProgress(currentPosition);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Intent intent = new Intent(this,MusicService.class);
        startService(intent);
        conn = new MyConn();
        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    public void initView(){
        start_btn = findViewById(R.id.start_btn);
        pause_btn = findViewById(R.id.pause_btn);
        seekBar = findViewById(R.id.seekBar);

        start_btn.setOnClickListener(this);
        pause_btn.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进度改变的时候调用
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //开始拖动的时候调用
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //停止拖动的时候调动
                callBackInterface.callSeekTo(seekBar.getProgress());
            }
        });
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.start_btn:
                callBackInterface.callPlay(isContinue);
                isContinue = true;
                break;
            case R.id.pause_btn:
                callBackInterface.callPause();
                break;
        }
    }

    private class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            callBackInterface = (CallBackInterface)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
