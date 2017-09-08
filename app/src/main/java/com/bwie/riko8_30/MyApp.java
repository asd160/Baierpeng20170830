package com.bwie.riko8_30;

import android.app.Application;
import android.content.Context;

import com.bwie.riko8_30.bean.Constants;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/8/30.
 */

public class MyApp extends Application {

    public static Context mContext;
    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mContext=this;

        //设置极光推送bug模式
        JPushInterface.setDebugMode(true);
        //初始化极光推送
        JPushInterface.init(this);

        //代码注册AppKey和AppSecret
        MobSDK.init(this, Constants.KEY,Constants.SECRET);
        initImageLoader();
        initXutils();

        UMShareAPI.get(this);

        Config.DEBUG=true;
    }

    private void initXutils() {

        x.Ext.init(this); //可以使用
        x.Ext.setDebug(false);//日志
    }

    private void initImageLoader() {

        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .threadPoolSize(3)
                .build();

        ImageLoader.getInstance().init(config);




    }
}
