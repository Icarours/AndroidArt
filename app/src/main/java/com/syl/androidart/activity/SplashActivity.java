package com.syl.androidart.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.syl.androidart.MainActivity;
import com.syl.androidart.R;
import com.syl.androidart.config.MyApplication;
import com.syl.androidart.utils.UIUtils;

/**
 * author   Bright
 * date     2017/7/10 18:39
 * desc
 * splash欢迎界面
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private ViewPager mVp;
    private int[] mPics;
    LinearLayout mItemHomePictureContainerIndicator;
    private Button mBtnJumpMain;
    private ActivityManager mActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPics = new int[]{R.mipmap.mm1, R.mipmap.mm2, R.mipmap.mm3, R.mipmap.mm4, R.mipmap.mm5};
        initView();
//        mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
//        mActivityManager.killBackgroundProcesses("com.syl.androidart");

        initListener(mBtnJumpMain);
        mVp.setAdapter(new SplashPageAdapter());
        //refreshHolderView();
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == mPics.length - 1) {
                    mBtnJumpMain.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Handler handler = new Handler();
        // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
        /*handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 3000);*/
    }

    private void initListener(Button btnJumpMain) {
        btnJumpMain.setOnClickListener(this);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mVp = (ViewPager) findViewById(R.id.vp);
        mBtnJumpMain = (Button) findViewById(R.id.btn_jump_main);
        //initIndicator();

    }

    /**
     * 初始化ViewPager的指示器
     */
    private void initIndicator() {
        /**
         * 设置指示器
         */
        for (int i = 0; i < mPics.length; i++) {
            ImageView ivIndicator = new ImageView(UIUtils.getContext());
            //图片设置
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            //默认选中第一个
            if (i == 0) {
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
            int width = UIUtils.dp2px(6);
            int height = UIUtils.dp2px(6);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = UIUtils.dp2px(6);
            params.bottomMargin = UIUtils.dp2px(6);
            mItemHomePictureContainerIndicator.addView(ivIndicator, params);//设置ivIndicator之间的间距,传入一个参数
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump_main:
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * ViewPager的Adapter
     */
    private class SplashPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (mPics != null && mPics.length > 0) {
                return mPics.length;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setImageResource(mPics[position]);
            //图片的设置
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public void refreshHolderView() {
        //监听ViewPager的滚动
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 选中Viewpager是的操作
             * @param position ViewPager中图片的索引
             */
            @Override
            public void onPageSelected(int position) {//position是ViewPager中图片的索引
                position = position % mPics.length;
                for (int i = 0; i < mPics.length; i++) {//i是ivIndicator的索引
                    ImageView ivIndicator = (ImageView) mItemHomePictureContainerIndicator.getChildAt(i);
                    //1.还原
                    ivIndicator.setImageResource(R.drawable.indicator_normal);
                    //2.选中应该选中的
                    if (i == position) {
                        ivIndicator.setImageResource(R.drawable.indicator_selected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //左右无限轮播
        //初始化ViewPager首次选中,是在第一个ivIndicator和第一张图片
        int diff = Integer.MAX_VALUE % mPics.length;//余数
        int index = Integer.MAX_VALUE / 2 - diff;
        mVp.setCurrentItem(index);

        //自动轮播
        final AutoScrollTask autoScrollTask = new AutoScrollTask();
        autoScrollTask.start();

        //按下去的时候,停止轮播
        mVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        autoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        autoScrollTask.start();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 自动轮播
     */
    class AutoScrollTask implements Runnable {
        //开始滚动
        public void start() {
            MyApplication.getMainThreadHandler().postDelayed(this, 3000);
        }

        //结束滚动
        public void stop() {
            MyApplication.getMainThreadHandler().removeCallbacks(this);
        }

        /**
         * ViewPager自动轮播
         */
        @Override
        public void run() {
            int currentItem = mVp.getCurrentItem();
            currentItem++;
            mVp.setCurrentItem(currentItem);
            start();
        }
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//使手机返回键失效
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }
}
