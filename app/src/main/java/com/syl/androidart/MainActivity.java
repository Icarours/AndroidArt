package com.syl.androidart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.syl.androidart.activity.NavigationActivity;
import com.syl.androidart.activity.NavigationActivity2;
import com.syl.androidart.activity.SplashActivity;
import com.syl.androidart.activity.SystemSettingActivity;
import com.syl.androidart.base.BaseFragment;
import com.syl.androidart.config.Constant;
import com.syl.androidart.factory.FragmentFactory;
import com.syl.androidart.fragment.FileFragment;
import com.syl.androidart.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2017/7/10 18:02
 * desc
 * Android开发艺术探索
 */
public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.lv_index)
    ListView mLvIndex;
    @BindView(R.id.dl)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private BaseFragment mFragment;
    private int mCurrentPosition;//记录选中的位置
    private MainActivity mContext = MainActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(TAG, "onCreate()");
        ButterKnife.bind(this);
//        View decorView = getWindow().getDecorView();
        ActionBar actionBar = getSupportActionBar();
        //设置图标
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setHomeButtonEnabled(true);
        //显示图标,默认是隐藏的,先显示icon
        actionBar.setDisplayShowHomeEnabled(true);
        //控制icon和logo显示的优先级
        //actionBar.setDisplayUseLogoEnabled(true);
        //显示回退按钮
        actionBar.setDisplayHomeAsUpEnabled(true);


        mLvIndex.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Constant.mTitles));
        mLvIndex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Constant.mFragmentIsActives[position]) {
                    mDrawerLayout.closeDrawer(mLvIndex);
                    return;
                }
                selectItem(position);
            }
        });
        if (savedInstanceState == null) {
            selectItem(2);
        }
        /**
         *ActionBarDrawerToggle要用v7包下的
         * 如果使用v4包下的,就没有动画同步的效果
         */
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(Constant.mTitles[mCurrentPosition]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("抽屉目录标题");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getPermission();
    }

    public void getPermission() {
        //如果没有权限就申请权限,如果有权限就直接打电话
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            );
        }
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// TODO: 2017/8/7 如果获取动态权限 ,正常运行
            } else {
                getPermission();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// TODO: 2017/8/7 如果获取动态权限 ,正常运行
            } else {
                getPermission();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 使用FragmentFactory,从FragmentFactory中取出Fragment
     * 修改当前条目对应Fragment的状态
     *
     * @param position
     */
    private void selectItem(int position) {
        mCurrentPosition = position;
        for (int i = 0; i < Constant.mFragmentIsActives.length; i++) {
            Constant.mFragmentIsActives[i] = false;
        }
        // update the main content by replacing fragments
        mFragment = FragmentFactory.createFragment(position);
        Bundle bundle = new Bundle();
        bundle.putInt(FileFragment.ARG_FILE_NUMBER, position);
        mFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, mFragment).commit();
        // update selected item and title, then close the drawer
        mLvIndex.setItemChecked(position, true);
        setTitle(Constant.mTitles[position]);
        mDrawerLayout.closeDrawer(mLvIndex);//关闭抽屉菜单

        //修改Fragment对应条目的状态
        Constant.mFragmentIsActives[position] = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //交给Toggle去分析并操作绑定的DrawerLayout
        mDrawerToggle.onOptionsItemSelected(item);
        Intent intent;
        switch (item.getItemId()) {
            case R.id.item_navigation:
                Toast.makeText(this, "item_navigation was clicked..", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, NavigationActivity.class);
                startActivity(intent);
                break;
            case R.id.item_system_setting:
                Toast.makeText(this, "item_system_setting was clicked..", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, SystemSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.item_navigation2:
                Toast.makeText(this, "item_navigation2 was clicked..", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, NavigationActivity2.class);
                startActivity(intent);
                break;
            case R.id.item_splash:
                Toast.makeText(this, "item_splash was clicked..", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG, "onRestart()");
    }
}
