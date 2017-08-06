package com.syl.androidart.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.syl.androidart.R;
import com.syl.androidart.base.MyBaseAdapter;

/**
 * Created by Bright on 2017/8/7.
 *
 * @Describe Uil ListView 对应的Adapter
 * @Called
 */

public class UilAdapter extends MyBaseAdapter {
    private Context mContext;
    private String[] mData = getData();

    public UilAdapter(Context context) {
        mContext =context;
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
        DisplayImageOptions option = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        ImageLoader.getInstance().displayImage(mData[position], holder.mIvItem, option);
        holder.mTvItem.setText("图片" + (position + 1));
        return convertView;
    }
}
