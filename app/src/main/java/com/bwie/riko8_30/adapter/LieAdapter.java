package com.bwie.riko8_30.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.riko8_30.R;
import com.bwie.riko8_30.bean.MyBeanNews_lie;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class LieAdapter extends BaseAdapter {

    private Context context;
    private List<MyBeanNews_lie> list;

    public LieAdapter(Context context, List<MyBeanNews_lie> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if(0==position%2){

            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        Holderitem1 item1=null;
        Holderitem2 item2=null;
        if(convertView==null){

            switch (type){
                case 0:
                    item1=new Holderitem1();
                    convertView=View.inflate(context, R.layout.lie_item1,null);
                    item1.tv_title1= (TextView) convertView.findViewById(R.id.item1_tv_title);
                    item1.tv_date1= (TextView) convertView.findViewById(R.id.item1_tv_date);
                    item1.tv_wang1= (TextView) convertView.findViewById(R.id.item1_tv_wang);
                    item1.iv1= (ImageView) convertView.findViewById(R.id.item1_iv);
                    convertView.setTag(item1);
                    break;
                case 1:
                    item2=new Holderitem2();
                    convertView=View.inflate(context, R.layout.lie_item2,null);
                    item2.tv_title2= (TextView) convertView.findViewById(R.id.item2_tv_title);
                    item2.tv_date2= (TextView) convertView.findViewById(R.id.item2_tv_date);
                    item2.tv_wang2= (TextView) convertView.findViewById(R.id.item2_tv_wang);
                    item2.iv2= (ImageView) convertView.findViewById(R.id.item2_iv);
                    convertView.setTag(item2);
                    break;
            }

        }else {
            MyBeanNews_lie m = list.get(position);//取标签
            switch (type){
                case 0:
                    item1= (Holderitem1) convertView.getTag();
                    item1.tv_date1.setText(m.date);
                    item1.tv_title1.setText(m.title);
                    item1.tv_wang1.setText(m.author_name);
                    ImageLoader.getInstance().displayImage(m.thumbnail_pic_s,item1.iv1);
                    break;
                case 1:
                    item2= (Holderitem2) convertView.getTag();
                    item2.tv_date2.setText(m.date);
                    item2.tv_title2.setText(m.title);
                    item2.tv_wang2.setText(m.author_name);
                    ImageLoader.getInstance().displayImage(m.thumbnail_pic_s,item2.iv2);
                    break;
            }
        }
        return convertView;
    }
    public class Holderitem1{
        ImageView iv1;
        TextView tv_title1,tv_date1,tv_wang1;
    }
    public class Holderitem2{
        ImageView iv2;
        TextView tv_title2,tv_date2,tv_wang2;
    }
}
