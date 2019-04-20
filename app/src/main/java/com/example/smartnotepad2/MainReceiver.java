package com.example.smartnotepad2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
/**
 * Created by wanghonggang on 2018/6/29.
 */

public class MainReceiver extends BroadcastReceiver {
    Context context;
    final int START_SERVICE=1;
    Handler handler=new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case START_SERVICE:
                    Intent intent1 = new Intent(context, MainService.class);
                    context.startService(intent1);
                    break;
            }
        }
    };
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        String action=intent.getAction();
        switch (action){
            case GlobalParams.START_PHONE_ACTION:
                handler.sendEmptyMessage(START_SERVICE);
                break;
        }
    }
}
