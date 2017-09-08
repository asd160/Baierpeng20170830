package com.bwie.riko8_30;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bwie.riko8_30.adapter.RecyclerAdapter;
import com.bwie.riko8_30.bean.CategoryBean;
import com.bwie.riko8_30.sql.MySQliteHelper;
import com.bwie.riko8_30.uri.MyUrl;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class OfflineActivity extends AppCompatActivity {

    private RecyclerView recyclerlv;
    private Button download;
    private MySQliteHelper sq;
    private List<CategoryBean> tvtoulist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        sq = new MySQliteHelper(this);
        initView();
        initData();

    }

    /**
     * listView
     */
    private void initData() {

        tvtoulist = new ArrayList<>();
        //初始化数据
        CategoryBean bean=new CategoryBean();
        bean.id="top";
        bean.name="头条";
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "社会";
        bean.id = "shehui";
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "国内";
        bean.id = "guonei";
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "国际";
        bean.id = "guoji";
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "娱乐";
        bean.id = "yule";
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "体育";
        bean.id = "tiyu";
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "军事";
        bean.id = "junshi";
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "科技";
        bean.id = "keji";
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "财经";
        bean.id = "caijing";
        tvtoulist.add(bean);
        bean = new CategoryBean();
        bean.name = "时尚";
        bean.id = "shishang";

        RecyclerAdapter adapter=new RecyclerAdapter(tvtoulist,this);
        recyclerlv.setLayoutManager(new LinearLayoutManager(this));
        recyclerlv.setAdapter(adapter);

        adapter.setonItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos, View view) {
                CheckBox checkbox= (CheckBox) view.findViewById(R.id.recycle_cb);
                CategoryBean c=tvtoulist.get(pos);

                if(checkbox.isChecked()){

                    checkbox.setChecked(false);
                    c.state=false;
                }else {
                    checkbox.setChecked(true);
                    c.state=true;
                }
                //修改原有list 根据pos，设置新的对象，然后更新list

                tvtoulist.set(pos,c); //修改list状态

            }
        });


    }

    private void initView() {
        download= (Button) findViewById(R.id.download);
        recyclerlv= (RecyclerView) findViewById(R.id.lv);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvtoulist!=null&&tvtoulist.size()>0){

                    for (CategoryBean catogry:tvtoulist){//判断这个状态是否选中
                        if(catogry.state){//如果选中
                            //说明要下载了a
                            loadData(catogry.id);
                            //下载走的都是子线程 不一定哪一个会先下载完
                        }
                    }
                }
            }
        });

    }

    /**
     * 只要有wifi 就下载离线数据 下载完成后保存数据库
     * @param type
     */
    private void loadData(final String type) {//选中的下载类型

        //下载 请求网络你不会吗？
        /**
         * 这里面都是子线程
         */
        RequestParams params=new RequestParams(MyUrl.URL);
        params.addQueryStringParameter("key",MyUrl.KEY);
        params.addQueryStringParameter("type",type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                //下载成功后保存的到数据库
                saveData(type,result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    /**
     * 保存数据库
     */
    private void saveData(String type, String result) {

        SQLiteDatabase db = sq.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("type",type);
        values.put("content",result);
        db.insert("toutiao",null,values);
        Toast.makeText(this,"正在下载",Toast.LENGTH_SHORT).show();


    }


}
