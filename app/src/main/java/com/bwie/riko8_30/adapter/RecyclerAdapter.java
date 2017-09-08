package com.bwie.riko8_30.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.riko8_30.R;
import com.bwie.riko8_30.bean.CategoryBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<CategoryBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(List<CategoryBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * 创建voiwHolder 和view绑定 类似于listView中的settag
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化条目view
        final View view= LayoutInflater.from(context).inflate(R.layout.recycleitem,null);

        MyViewHolder myViewHolder=new MyViewHolder(view);//每一个控件就有了Viewhodler
        //实现自己回调接口(注意回调  在哪使用 就在那设置 才能起作用)

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener((Integer) view.getTag(),view);
            }
        });

        return myViewHolder;
    }

    //这个方法主要是同于处理逻辑(绘制ui数据)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nametv.setText(list.get(position).name);

        holder.itemView.setTag(position);//通tag来设置标记 下标
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 供调用两者用的接口 所以声明为public
     */
    public void setonItemClickListener(OnItemClickListener onItemClickListener){

        this.onItemClickListener=onItemClickListener;
    }

    /**
     * 条目点击事件接口
     */
    public interface OnItemClickListener{
        void onItemClickListener(int pos,View view);
    }

    /**
     * 自定义viewholder 继承RecycleView自带的viewholder
     */
    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nametv;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            nametv= (TextView) itemView.findViewById(R.id.recycle_tv);
            checkBox= (CheckBox) itemView.findViewById(R.id.recycle_cb);
        }

    }


}