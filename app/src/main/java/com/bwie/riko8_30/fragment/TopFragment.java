package com.bwie.riko8_30.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bwie.riko8_30.R;
import com.bwie.riko8_30.WebViewActivity;
import com.bwie.riko8_30.adapter.LieAdapter;
import com.bwie.riko8_30.bean.MyBeanNews_lie;
import com.bwie.riko8_30.uri.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import view.xlistview.XListView;

/**
 * Created by Administrator on 2017/8/30.
 */

@ContentView(R.layout.fragment_top_layout)
public class TopFragment extends Fragment implements XListView.IXListViewListener{

    @ViewInject(R.id.xlv_1)
    XListView xlv;
    private View mRootView;
    private LieAdapter ada;
    private List<MyBeanNews_lie> list;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView==null){
            mRootView= x.view().inject(this,inflater,container);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        bundle = getArguments();


        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {

        list = new ArrayList<>();
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);
        initData2();
        setOnClick();
    }

    private void setOnClick() {
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(getActivity(),WebViewActivity.class);
                in.putExtra("url",list.get(position-1).url);
                startActivity(in);
            }
        });
    }

    private void initData2() {
        //得到bundle
        String type= bundle.getString("date");

        //post请求
        RequestParams params=new RequestParams(MyUrl.URL);
        params.addBodyParameter("key",MyUrl.KEY);
        params.addBodyParameter("type",type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //请求成功
                try {
                    JSONObject objec=new JSONObject(result);

                    JSONObject object = objec.optJSONObject("result");
                    JSONArray array = object.optJSONArray("data");
                    for (int i = 0; i <array.length() ; i++) {
                        JSONObject obj= (JSONObject) array.get(i);
                        MyBeanNews_lie b=new MyBeanNews_lie();
                        b.title=obj.optString("title");
                        b.date=obj.optString("date");
                        b.author_name=obj.optString("author_name");
                        b.url=obj.optString("url");
                        b.thumbnail_pic_s=obj.optString("thumbnail_pic_s");
                        list.add(b);
                    }

                    //解析完成 添加适配器
                    if(ada==null){
                        ada = new LieAdapter(getActivity(),list);
                        xlv.setAdapter(ada);
                    }else{
                        ada.notifyDataSetChanged();
                    }

                    xlv.stopLoadMore();
                    xlv.stopRefresh();

                } catch (Exception e) {
                    e.printStackTrace();
                }

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

    @Override
    public void onRefresh() {
        if(list!=null){
            list.clear();
        }
        initData2();
    }

    @Override
    public void onLoadMore() {
        initData2();
    }

    public static Fragment setDate(String date){
        /**
         * 得到传过来的值 再传给oncreate
         */
        TopFragment top=new TopFragment();
        Bundle bundle=new Bundle();
        bundle.putString("date",date);
        //开传
        top.setArguments(bundle);
        return top;
    }
}
