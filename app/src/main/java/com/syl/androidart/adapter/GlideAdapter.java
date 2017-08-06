package com.syl.androidart.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.syl.androidart.R;
import com.syl.androidart.config.ImageUrl;

/**
 * Created by Bright on 2017/8/7.
 *
 * @Describe Glide的Adapter
 * @Called
 */

public class GlideAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mData = ImageUrl.mImageUrls;

    public GlideAdapter(Context context) {
        mContext = context;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.list_item_glide, null);
            holder.mIvItem = (ImageView) convertView.findViewById(R.id.iv_item);
            holder.mTvItem = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext)
                .load(mData[position])
                .into(holder.mIvItem);
        holder.mTvItem.setText("图片" + (position + 1));
        return convertView;
    }
}
