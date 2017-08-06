package com.syl.androidart.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.syl.androidart.R;
import com.syl.androidart.base.MyBaseAdapter;

/**
 * Created by Bright on 2017/8/7.
 *
 * @Describe Picasso ListView 对应的Adapter
 * @Called
 */

public class PicassoAdapter extends MyBaseAdapter {
    private Context mContext;
    private String[] mData = getData();

    public PicassoAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.list_item_picasso, null);
            holder.mIvItem = (ImageView) convertView.findViewById(R.id.iv_item);
            holder.mTvItem = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext)
                .load(mData[position])
                .into(holder.mIvItem);
        holder.mTvItem.setText("图片" + (position + 1));
        return convertView;
    }
}
