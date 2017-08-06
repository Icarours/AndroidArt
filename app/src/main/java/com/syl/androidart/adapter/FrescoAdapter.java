package com.syl.androidart.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.syl.androidart.R;
import com.syl.androidart.base.MyBaseAdapter;

/**
 * Created by Bright on 2017/8/7.
 *
 * @Describe Fresco ListView 对应的Adapter
 * @Called
 */

public class FrescoAdapter extends MyBaseAdapter {
    private Context mContext;

    private String[] mData = getData();

    public FrescoAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.list_item_fresco, null);
            holder.mSdvItem = (SimpleDraweeView) convertView.findViewById(R.id.sdv_item);
            holder.mTvItem = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //加载图片
        DraweeController draweeController = Fresco
                .newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(mData[position])
                .build();
        holder.mSdvItem.setController(draweeController);
        holder.mTvItem.setText("图片" + (position + 1));
        return convertView;
    }

    class ViewHolder {
        SimpleDraweeView mSdvItem;
        TextView mTvItem;
    }
}
