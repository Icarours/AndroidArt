package com.syl.androidart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.activity.BookManagerActivity;
import com.syl.androidart.activity.MessengerActivity;
import com.syl.androidart.activity.SecondActivity;
import com.syl.androidart.base.BaseFragment;
import com.syl.androidart.bean.User;
import com.syl.androidart.config.Constant;
import com.syl.androidart.utils.IoUtil;
import com.syl.androidart.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Bright on 2017/7/13.
 *
 * @Describe 进程间通信:文件共享,Messenger(信使),aidl
 * @Called
 */

public class ShareFileFragment extends BaseFragment {
    @BindView(R.id.btn_jump)
    Button mBtnJump;
    @BindView(R.id.btn_save_data)
    Button mBtnSaveData;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.btn_messenger)
    Button mBtnMessenger;
    Unbinder unbinder;
    @BindView(R.id.btn_aidl)
    Button mBtnAidl;
    private View mRootView;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_share_file, null);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @OnClick(R.id.btn_jump)
    public void btn_jump(View view) {
        Intent intent = new Intent(getContext(), SecondActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_save_data)
    public void btn_save_data(View view) {
        persistentToFile();
    }

    /**
     * 进程间通信,aidl
     *
     * @param view
     */
    @OnClick(R.id.btn_aidl)
    public void btn_aidl(View view) {
        Intent intent = new Intent(getContext(), BookManagerActivity.class);
        startActivity(intent);
    }

    /**
     * 进程间通信,Messenger(信使)
     *
     * @param view
     */
    @OnClick(R.id.btn_messenger)
    public void btn_messenger(View view) {
        Intent intent = new Intent(getContext(), MessengerActivity.class);
        startActivity(intent);
    }

    /**
     * 将序列化对象写入文件
     * 忘了怎么创建文件,
     */
    private void persistentToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO: 2017/7/13 创建文件
                User user = new User(1, "Tom", true);
                File dir = new File(Constant.DIR);
                if (!dir.exists()) {//如果文件夹不存在,就创建文件夹
                    dir.mkdir();
                }
                ObjectOutputStream oos = null;
                try {
                    File cacheFile = new File("test.txt");
                    oos = new ObjectOutputStream(new FileOutputStream(cacheFile));
                    oos.writeObject(user);
                    LogUtil.d(this.getClass().getSimpleName(), "persistentToFile==" + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IoUtil.close(oos);
                }
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
