package com.bwie.riko8_30.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/09/05
 * Description:
 */
public class Myreceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("bick.test")) {
            //走收到广播的逻辑
            //Toast.makeText(context,"nihao",Toast.LENGTH_SHORT).show();
        }

    }
}
