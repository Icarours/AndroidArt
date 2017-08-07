package com.syl.androidart.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.fragment.AnimationFragment;
import com.syl.androidart.fragment.FrameworksFragment;
import com.syl.androidart.fragment.ImageLoadFragment;
import com.syl.androidart.fragment.SystemViewFragment;

/**
 * author   Bright
 * date     2017/8/5 21:44
 * desc
 * 用来展示一些新的内容
 */
public class NavigationActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_system_view:
                    transaction.replace(R.id.fragment_content, new SystemViewFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_animation:
                    transaction.replace(R.id.fragment_content, new AnimationFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_image:
                    transaction.replace(R.id.fragment_content, new ImageLoadFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_frameworks:
                    mTextMessage.setText(R.string.title_frameworks);
                    transaction.replace(R.id.fragment_content, new FrameworksFragment());
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mTextMessage = (TextView) findViewById(R.id.message);//这个演示用的TextView以后可以去掉
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_system_view);//设置默认显示的条目
    }
}
