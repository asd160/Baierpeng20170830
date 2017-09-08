package com.bwie.riko8_30.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/9/4.
 */

public class NetWorkInfoUtils {

    private Context context;

    /**
     * 网络判断方法
     */

    public void verify(Context context,NetWork netWork){

        //网络连接管理器
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //网络可用对象
        NetworkInfo info=manager.getActiveNetworkInfo();

        //如果对象不为空  说明有活动的网络对象
        if(info !=null){

            //判读是否属于手机信号
            if(info.getType() ==ConnectivityManager.TYPE_MOBILE){
                //不让加载图片
                netWork.netMobileVisible();
            }else if(info.getType() ==ConnectivityManager.TYPE_WIFI){
                //加载大图
                netWork.netWifiVisible();
            }else {
                //没有 有效网络 wifi 可能没有网
                netWork.netUnVisible();
            }

        }else {
             netWork.netUnVisible();
        }

    }

    //一定义网络判断回调接口
    public interface NetWork{

        //有wifi信号的逻辑
        void netWifiVisible();
        //无网络的逻辑
        void netUnVisible();
        //有手机信号的逻辑 手机网络
        void netMobileVisible();

    }
}
