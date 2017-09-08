package com.bwie.riko8_30.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwie.riko8_30.GengLoginActivity;
import com.bwie.riko8_30.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/8/30.
 */

@ContentView(R.layout.left_munn_layout)
public class LeftFragment extends Fragment implements View.OnClickListener {

    private View v;


    @ViewInject(R.id.left_gengduo)
    ImageView iv_more;

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
    }

    /**
     * 设置点击事件
     */
    private void setOnclick() {
        iv_more.setOnClickListener(this);
    }

    /**
     * 重写点击事件方法
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_gengduo:
                startActivity(new Intent(getActivity(), GengLoginActivity.class));
                break;
        }

    }
}
