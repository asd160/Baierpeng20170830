package com.bwie.riko8_30;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.bwie.riko8_30.bean.CategoryBean;
import com.bwie.riko8_30.bean.Channel;
import com.bwie.riko8_30.fragment.LeftFragment;
import com.bwie.riko8_30.fragment.RightFragment;
import com.bwie.riko8_30.fragment.TopFragment;
import com.bwie.riko8_30.myview.HorizontalScollTabhost;
import com.bwie.riko8_30.sql.MySQliteHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @ViewInject(R.id.tou_iv_shezhi)
    ImageView tou_shezhi;
    @ViewInject(R.id.tou_iv_user)
    ImageView tou_user;
    @ViewInject(R.id.hsm_container)
    HorizontalScollTabhost hsm_container;

    private SlidingMenu menu;
    private List<Fragment> flist;
    private List<CategoryBean> tvtoulist;
    private MySQliteHelper hp;
    private ArrayList<CategoryBean> mlist;
    private Map<String, String> map;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //静态广播发送
        // ***** sendBroadcast(new Intent("kson.test"));
        //setContentView(R.layout.activity_main);
        //透明导航栏
        //设置头部颜色
        //StatusBarCompat.setStatusBarColor(this, Color.parseColor("#ff4444"));

        x.view().inject(this);//加载布局

        sendBroadcast(new Intent("bick.test"));

        inint();
        initLayout();
        setOnClick();
        initData();

    }

    private void inint() {
        hp = new MySQliteHelper(this);
    }

    private void initData() {
        flist = new ArrayList<>();
        tvtoulist = new ArrayList<>();
        map = new HashMap<>();

        CategoryBean bean=new CategoryBean();
        bean.id="top";
        bean.name="头条";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "社会";
        bean.id = "shehui";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "国内";
        bean.id = "guonei";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "国际";
        bean.id = "guoji";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "娱乐";
        bean.id = "yule";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "体育";
        bean.id = "tiyu";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "军事";
        bean.id = "junshi";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "科技";
        bean.id = "keji";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "财经";
        bean.id = "caijing";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "时尚";
        bean.id = "shishang";
        map.put(bean.name,bean.id);
        tvtoulist.add(bean);

        //TopFragment fragment= (TopFragment) TopFragment.setDate("财经");

        //存到数据库 下次直接从数据库 设置
        SQLiteDatabase db = hp.getWritableDatabase();
        Cursor cursor = db.query("channel", null, null, null, null, null,null);
        while (cursor.moveToNext()){
            String json=cursor.getString(0).trim().toString();
            if(json!=null){
                jsonArr(json);//解析
                for (int i = 0; i <mlist.size() ; i++) {
                    if(mlist.get(i).state==true){

                        String name = mlist.get(i).name;
                        String id = map.get(name);
                        //tvtoulist.clear();

                        bean = new CategoryBean();
                        bean.name = name;
                        bean.id = id;
                        tvtoulist.add(bean);
                    }
                }
                for (CategoryBean  b: tvtoulist){
                    flist.add(new TopFragment().setDate(b.id));
                }
                hsm_container.display(tvtoulist,flist);

            }else {
                return;
            }
            //遍历对象集合 并且传入id 就是type
            for (CategoryBean  b: tvtoulist){
                flist.add(new TopFragment().setDate(b.id));
            }

            hsm_container.display(tvtoulist,flist);
        }

        /**
         * 频道管理 点击事件
         */
        hsm_container.setonItemClickListener(new HorizontalScollTabhost.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view) {

                ChannelBean channelBean;
                List<ChannelBean> chanlist=new ArrayList<>();//这个集合很重要 并且需要传过去  决定着那一个的页面
                for (int i = 0; i < tvtoulist.size(); i++) {
                    channelBean=new ChannelBean(tvtoulist.get(i).name,true);
                    chanlist.add(channelBean);
                }
                ChannelActivity.startChannelActivity(MainActivity.this,chanlist);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==101 &&requestCode==100){

            String result= data.getStringExtra("json");
            //存到数据库 下次直接从数据库 设置
            SQLiteDatabase db = hp.getWritableDatabase();
            db.execSQL("delete from channel");//清空表
            db.close();//关闭资源

            SQLiteDatabase db1 = hp.getWritableDatabase();
            //添加数据
            ContentValues values=new ContentValues();
            values.put("json",result.toString());
            db1.insert("channel",null,values);
            db.close();//关闭资源

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //存到数据库 下次直接从数据库 设置
        SQLiteDatabase db = hp.getWritableDatabase();
        Cursor cursor = db.query("channel", null, null, null, null, null,null);
        while (cursor.moveToNext()){
            String json=cursor.getString(0).trim().toString();
            if(json!=null){
                jsonArr(json);//解析
            }
            return;
        }
    }

    /**
     * 解析返回的集合
     */
    private List<CategoryBean> jsonArr(String result) {

        Gson gson=new Gson();
        CategoryBean c=null;
        mlist = new ArrayList<>();
        //解析啊 list
        List<Channel> channleList=gson.fromJson(result,new TypeToken<List<Channel>>(){}.getType());
        for (int i = 0; i <channleList .size(); i++) {
            c=new CategoryBean();
            c.name=channleList.get(i).name;
            c.state=channleList.get(i).isSelect;
            c.i=i;
            mlist.add(c);//存到集合了
        }
        return mlist;
    }
    /**
     * 设置左滑右滑
     */
    private void initLayout() {

        menu =new SlidingMenu(this);

        //添加左菜单
        // setBehindContentView(R.layout.left_menu_content);
        menu.setMenu(R.layout.left_menu_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_menu_content, new LeftFragment()).commit();

        //设置属性
        // menu = getSlidingMenu(); //这个是获取 现在是new
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//边缘
        menu.setBehindOffsetRes(R.dimen.BehindOffsetRes);

        //渐变
        menu.setFadeDegree(0.75f);
        //设置右菜单
        menu.setSecondaryMenu(R.layout.right_menu_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu_content,new RightFragment()).commit();


        //这一行很重要 绑定到Activity
        menu.attachToActivity(MainActivity.this,SlidingMenu.SLIDING_CONTENT);
    }

    /**
     * 设置点击事件
     */
    private void setOnClick() {
        tou_shezhi.setOnClickListener(this);
        tou_user.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tou_iv_shezhi:
                //显示右菜单
                menu.showSecondaryMenu();
                break;
            case R.id.tou_iv_user:
                //显示左菜单
                menu.showMenu();
                break;
        }

    }
}
