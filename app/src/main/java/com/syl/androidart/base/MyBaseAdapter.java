package com.syl.androidart.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.syl.androidart.config.ImageUrl;

/**
 * Created by Bright on 2017/8/7.
 *
 * @Describe 图片加载的Adapter的基类
 * @Called
 */

public class MyBaseAdapter extends BaseAdapter {
    private String[] mData = ImageUrl.mImageUrls;

    public String[] getData() {
        return mData;
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mData != null) {
            return mData[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mData != null) {
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
