package com.bwie.riko8_30;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.riko8_30.bean.User;
import com.bwie.riko8_30.utils.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.bwie.riko8_30.R.id.tv_fasong_phone;

@ContentView(R.layout.activity_geng_login)
public class GengLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EventHandler eventHandler;

    @ViewInject(R.id.logon_phone)
    EditText et_phone;
    @ViewInject(R.id.logon_yanzheng)
    EditText et_yzm;
    @ViewInject(tv_fasong_phone)
    TextView tv_songyz;
    @ViewInject(R.id.btn_jinrutoutiao)
    Button btn_deng;
    private int Dao_Time=5;
    Handler handler=new Handler();

    Runnable timeRunable=new Runnable() {//主线程
        @Override
        public void run() {
            Dao_Time--;

            if(Dao_Time==0){

                handler.removeCallbacks(this);
                tv_songyz.setText("再次获取");
                tv_songyz.setEnabled(true);//可以点击
                Dao_Time=5;
            }else {
                tv_songyz.setEnabled(false);//正在改变不可获取
                tv_songyz.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_songyz.setText(Dao_Time+"s");

                handler.postDelayed(this,1000);
            }
        }
    };

    @ViewInject(R.id.g_iv_qq)
    ImageView login_qq;
    @ViewInject(R.id.g_f_x)
    ImageView fh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);//加载布局
        //getSupportActionBar().hide();
        //StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BBBBBB"));
        initView();
        smsRegister();

    }

    private void initView() {
        tv_songyz.setOnClickListener(this);
        btn_deng.setOnClickListener(this);
        login_qq.setOnClickListener(this);
        fh.setOnClickListener(this);
    }


    /**
     * 以下代码完成 sdk注册  也就是回调方法
     * 回调接口 ~~
     */
    private void smsRegister() {
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GengLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    if (result == SMSSDK.RESULT_COMPLETE) {//只有回服务器验证成功，才能允许用户登录
                        //回调完成,提交验证码成功
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GengLoginActivity.this, "服务器验证成功", Toast.LENGTH_SHORT).show();
                                    User user = new User();
                                    user.uid = et_phone.getText().toString();
                                    user.phone = et_phone.getText().toString();

                                    SharedPreferencesUtil.putPreference("uid", user.uid);
                                    SharedPreferencesUtil.putPreference("phone", user.phone);
                                }
                            });

                        }

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GengLoginActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表

                    }
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 注销 销毁
     */
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_fasong_phone: //发送验证码
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                //发送任务 倒计时5-1
                handler.postDelayed(timeRunable,1000);
                //发送验证码 只需要电话 就可以 ，第一个都是 城市 “86”
                SMSSDK.getVerificationCode("86",et_phone.getText().toString().trim());
                break;
            case R.id.btn_jinrutoutiao: //点击登录
                verify();//先判空

                //登录 就是向服务器 发送,判断验证码 手机号 是否都是正确！！
                SMSSDK.submitVerificationCode("86",et_phone.getText().toString().trim(),et_yzm.getText().toString().trim());

                break;
            case R.id.g_iv_qq:
                shouquan(SHARE_MEDIA.QQ);

                break;
            case R.id.g_f_x:
                finish();
                break;
        }
    }

    /**
     * 授权
     */
    private void shouquan(SHARE_MEDIA a) {

        UMShareAPI.get(this).getPlatformInfo(this, a, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                String name=map.get("name");
                String photoUrl = map.get("iconurl");
                ImageLoader.getInstance().displayImage(photoUrl, login_qq);

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                if (UMShareAPI.get(GengLoginActivity.this).isInstall(GengLoginActivity.this, SHARE_MEDIA.QQ)) {
                    Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "no install QQ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    /**
     * 判断输入逻辑
     */
    private void verify() {
       if(TextUtils.isEmpty(et_phone.getText().toString().trim())){

            Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(et_yzm.getText().toString().trim())){
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
            return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

}
