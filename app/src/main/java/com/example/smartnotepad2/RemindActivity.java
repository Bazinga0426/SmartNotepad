package com.example.smartnotepad2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by wanghonggang on 2018/6/29.
 */

public class RemindActivity extends AppCompatActivity {

    private TextView tv_content;
    private Button bt_confirm;
    private MediaPlayer mMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        Intent intent1 = new Intent(RemindActivity.this, MainService.class);
        /**启动MainService，注册广播，service能在app关闭的状态下常驻内存**/
        startService(intent1);
        findViews();
        setListeners();
        Bundle bundle=getIntent().getExtras();
        String content=bundle.getString(GlobalParams.CONTENT_KEY);
        tv_content.setText(content);
        playSound();
    }

    private void findViews(){
        tv_content=findViewById(R.id.tv_content);
        bt_confirm= findViewById(R.id.bt_confirm);
    }
    private void setListeners(){
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mMediaPlayer){
                    mMediaPlayer.stop();
                    finish();
                }
            }
        });
    }

    public void playSound() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMediaPlayer = MediaPlayer.create(RemindActivity.this, getSystemDefultRingtoneUri());
                mMediaPlayer.setLooping(true);//设置循环
                try {
                    mMediaPlayer.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMediaPlayer.start();
            }
        }).start();

    }


    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(RemindActivity.this,
                RingtoneManager.TYPE_RINGTONE);
    }

}
