package com.bwie.riko8_30.bean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.riko8_30.utils.NetWorkInfoUtils;

/**
 * Created by Administrator on 2017/9/5.
 */

public class AAdaptere extends RecyclerView.Adapter<AAdaptere.Holder> {


    private Context context;

    public AAdaptere(Context context) {
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {


        new NetWorkInfoUtils().verify(context, new NetWorkInfoUtils.NetWork() {
            @Override
            public void netWifiVisible() {


            }

            @Override
            public void netUnVisible() {

            }

            @Override
            public void netMobileVisible() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);


        }
    }

}
