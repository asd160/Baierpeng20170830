package com.bwie.riko8_30.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.riko8_30.R;
import com.bwie.riko8_30.bean.CategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class HorizontalScollTabhost extends LinearLayout implements ViewPager.OnPageChangeListener {


    private Context mContext;

    private int mBgColor;//控件背景颜色

    private List<CategoryBean> list;
    private List<Fragment> fragmentList;
    private List<TextView> topViews;

    private HorizontalScrollView hscrollview; //系统的水平滑动view
    private ViewPager vp;

    private LinearLayout mll;
    private ImageView iv;


    private MyPager mypager;
    private TextView tv;
    private OnItemClickListener onItemClickListener;

    public HorizontalScollTabhost(Context context) {
        this(context, null);
    }

    public HorizontalScollTabhost(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScollTabhost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init(context, attrs);
    }

    /**
     * 自定义属性 和view
     * @param context
     * @param attrs
     */
   private void init(Context context, AttributeSet attrs) {

       TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.HorizontalScollTabhost);


       mBgColor =typedArray.getColor(R.styleable.HorizontalScollTabhost_top_background, 0xffffff);//北京颜色

       typedArray.recycle();
       initView();

    }

    /**
    * 初始化view
     */
    private void initView() {

        View view= LayoutInflater.from(mContext).inflate(R.layout.horizontal_scroll_tabhost,this,true);

        hscrollview = (HorizontalScrollView) view.findViewById(R.id.horziontalScrollView);
        vp= (ViewPager) view.findViewById(R.id.viewpager_fragment);
        mll= (LinearLayout) view.findViewById(R.id.layout_munu);
        iv=(ImageView) view.findViewById(R.id.pindao);
        //内部点击事件
        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(iv);
            }
        });
        vp.addOnPageChangeListener(this);

    }
    public void display(List<CategoryBean> list, List<Fragment> fragments){

        this.list=list;
        this.fragmentList=fragments;
        topViews=new ArrayList<>(list.size());
        drawUi();
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
        void onItemClickListener(View view);
    }


    /**
     * 绘制页面所有元素
     */
    private void drawUi() {

        drawHorizontal();
        drawViewPager();

    }

    /**
     * 绘制viewpager
     */
    private void drawViewPager() {
        mypager=new MyPager(((FragmentActivity) mContext).getSupportFragmentManager() );
        vp.setAdapter(mypager);
    }

    /**
     * 绘制横向滑动菜单
     */
    private void drawHorizontal() {
       // mll.setBackgroundColor(mBgColor);//设置水平滑动的背景

        for (int i = 0; i <list.size() ; i++) {
            CategoryBean bean = list.get(i);
            final TextView tv= (TextView) View.inflate(mContext,R.layout.news_top_tv_item,null);

            tv.setText(bean.name);

            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击移动到当前fragment
                    vp.setCurrentItem(finalI);
                    //点击让文字居中
                    moveItemToCenter(tv);
                }
            });

            mll.addView(tv);

            topViews.add(tv);//添加到集合
        }

        //默认设置第一项
        topViews.get(0).setSelected(true);
    }
    /**
     * 移动view对象到中间
     *
     * @param tv
     */
    private void moveItemToCenter(TextView tv) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int[] locations = new int[2];
        tv.getLocationInWindow(locations);
        int rbWidth = tv.getWidth();
        hscrollview.smoothScrollBy((locations[0] + rbWidth / 2 - screenWidth / 2),
                0);
    }

    class MyPager extends FragmentPagerAdapter{

        public MyPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(mll!=null &&mll.getChildCount()>0){

            for (int i = 0; i < mll.getChildCount(); i++) {
                if(i==position){
                    mll.getChildAt(i).setSelected(true);
                }else {
                    mll.getChildAt(i).setSelected(false);
                }
            }
            //移动view，水平居中
            moveItemToCenter(topViews.get(position));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
