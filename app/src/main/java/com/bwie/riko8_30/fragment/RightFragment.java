package com.bwie.riko8_30.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bwie.riko8_30.OfflineActivity;
import com.bwie.riko8_30.R;
import com.bwie.riko8_30.utils.NetWorkInfoUtils;
import com.bwie.riko8_30.utils.SharedPreferencesUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/8/30.
 */

@ContentView(R.layout.right_munn_layout)
public class RightFragment extends Fragment implements View.OnClickListener {

    private View v;

    @ViewInject(R.id.y_fanhui)
    ImageView iv_fanhui;
    @ViewInject(R.id.she_huancun)
    RelativeLayout r_qingchu;
    @ViewInject(R.id.she_ziti)
    RelativeLayout r_ziti;
    @ViewInject(R.id.she_liebiaozhaoy)
    RelativeLayout r_lbzhaiyao;
    @ViewInject(R.id.she_feiwill)
    RelativeLayout r_feiwill;
    @ViewInject(R.id.she_feiwfbftx)
    RelativeLayout r_feiwibftx;
    @ViewInject(R.id.she_tstz)
    RelativeLayout r_tstz;
    @ViewInject(R.id.she_lxxz)
    RelativeLayout r_lxxz;
    @ViewInject(R.id.she_xitong)
    RelativeLayout r_xitong;
    @ViewInject(R.id.tv_y_tstz)
    SwitchCompat swich;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(v==null){
            v= x.view().inject(this,inflater,container);
        }else {
            ViewGroup parent= (ViewGroup) v.getParent();
            if(parent!=null){
                parent.removeView(v);
            }
        }
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setOnclick();
        init();

    }

    private void init() {
        if(SharedPreferencesUtil.getPreferencesValue("switch").equals("1")){
            swich.setChecked(true);
        }
    }

    /**
     * 点击事件
     */
    private void setOnclick() {
        iv_fanhui.setOnClickListener(this);
        r_qingchu.setOnClickListener(this);
        r_ziti.setOnClickListener(this);
        r_lbzhaiyao.setOnClickListener(this);
        r_feiwill.setOnClickListener(this);
        r_feiwibftx.setOnClickListener(this);
        r_tstz.setOnClickListener(this);
        r_lxxz.setOnClickListener(this);
        r_xitong.setOnClickListener(this);
        swich.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           // case R.id.y_fanhui:getActivity().finish(); break;
            case R.id.she_feiwill:

                new AlertDialog.Builder(getActivity()).setSingleChoiceItems(new String[]{"大图", "无图"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which==0){
                            //需要存储本地状态，选择的什么网络流量节省方式，"wifi"="hasnet"，加载大图
                        }else if(which==1){
                            //需要存储本地状态，选择的什么网络流量节省方式，"wifi"="nonet"，不加载图
                        }
                        dialog.dismiss();
                    }
                }).show();

                break;
            case R.id.she_tstz:break;//推送
            case R.id.she_lxxz://离线下载
                startActivity(new Intent(getActivity(), OfflineActivity.class));
                break;

            case R.id.tv_y_tstz:
                if(swich.isChecked()){//打开推送  记录状态
                    Toast.makeText(getActivity(),"sss",Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtil.putPreference("switch","1");
                    JPushInterface.resumePush(getActivity());// 点击恢复按钮后，极光推送服务会恢复正常工作
                }else {
                    AlertDialog.Builder al=new AlertDialog.Builder(getActivity())
                            .setMessage("很危险 你确定关闭? 因为你将接收不到我们的最近消息啦！！ 嘎嘎嘎").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferencesUtil.putPreference("switch","0");//记录状态
                                    JPushInterface.stopPush(getActivity());// 点击停止按钮后，极光推送服务会被停止掉
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    swich.setChecked(true);
                                }
                            });
                    al.create().show();
                }
                break;
        }
    }

    /**
     * 加载新闻数据
     */
    private void loadNewData() {

        new NetWorkInfoUtils().verify(getActivity(), new NetWorkInfoUtils.NetWork() {
            @Override
            public void netWifiVisible() {//有wifi时加载网络接口

                if("wifi".equals("hasnet")){

                }
            }

            @Override
            public void netUnVisible() {//没有网络的操作，加载本地缓存
                if ("wifi".equals("nonet")) {

                }
            }

            @Override
            public void netMobileVisible() {//有手机网络的时候的判断
                if ("wifi".equals("hasnet")) {//如果设置了加载大图，就走加载大图逻辑

                    //加载大图

                } else if ("wifi".equals("nonet")) {//如果设置了不加载图，就不加载图片

                    //占位图，不加载大图

                }
            }
        });

    }
}
