package com.syl.androidart.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.syl.androidart.fragment.PageFragment;

/**
 * Created by Bright on 2017/8/5.
 *
 * @Describe PageFragmentPagerAdapter Fragment+ViewPager
 * @Called
 */

public class PageFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"Tab1", "Tab2", "Tab3", "Tab4", "Tab5", "Tab6"};
    private final int COUNT = mTitles.length;
    private Context mContext;

    public PageFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    /**
     * 返回ViewPager对应的条目内容
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstance(position + 1);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
