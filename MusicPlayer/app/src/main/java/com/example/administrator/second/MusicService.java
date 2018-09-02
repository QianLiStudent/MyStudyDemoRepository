package com.example.administrator.second;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service{

    private MediaPlayer player;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MidClass();
    }

    @Override
    public void onCreate() {
        player = new MediaPlayer();
        super.onCreate();
    }

    public void play(boolean isContinue){
        if(isContinue){
            player.start();
        }else{
            try {
//                String path = "/data/data/com.example.administrator.second/xzq.mp3";
                String netPath = "http://192.168.1.108:8080/xzq.mp3";
                /**
                 * 这个方法比较容易出现错误，主要是路径的问题，我之前放在/mnt/sdcard目录下，但是.mp3文件我并没有执行权限
                 * 因此一直报start called in state 1 错误
                 */
//                player.setDataSource(path);
                player.setDataSource(netPath);
//                player.prepare();   //如果加载的是本地音频/视频资源则调用该方法准备
                /**
                 * 设置准备监听器，在完成准备后才能播放，否则可能会出现未准备完成就执行start()方法
                 * 这样会出现start called in state 0 错误
                 */
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        updateProgress();
                    }
                });
                player.prepareAsync();      //如果加载的是网络资源则调用该方法准备
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void pause(){
        player.pause();
    }

    public void seekTo(int position){
        player.seekTo(position);
    }

    //更新进度条进度，这里采用计时器，1s更新一次
    public void updateProgress(){
        final int duration = player.getDuration();  //获得音频/视频文件的总时长

        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int currentPosition = player.getCurrentPosition();    //获得音频/视频文件的当前进度位置

                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("duration",duration);
                bundle.putInt("currentPosition",currentPosition);
                msg.setData(bundle);
                MainActivity.hander.sendMessage(msg);
            }
        };
        timer.schedule(task,100,1000);
        //给播放器设置一个完成任务监听器，当任务完成时取消定时器和定时任务
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                timer.cancel();
                task.cancel();
            }
        });
    }

    private class MidClass extends Binder implements CallBackInterface{

        @Override
        public void callPlay(boolean isContinue) {
            play(isContinue);
        }

        @Override
        public void callPause() {
            pause();
        }

        @Override
        public void callSeekTo(int position) {
            seekTo(position);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
