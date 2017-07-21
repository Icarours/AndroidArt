package com.syl.androidart.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.bean.User;
import com.syl.androidart.config.Constant;
import com.syl.androidart.utils.IoUtil;
import com.syl.androidart.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author   Bright
 * date     2017/7/13 14:13
 * desc
 * 测试用Activity
 */
public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.btn_get_data)
    Button mBtnGetData;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv)
    ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get_data)
    public void btn_get_data(View view) {
        recoveryData();
    }

    /**
     * 恢复数据
     */
    private void recoveryData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File cacheFile = new File(Constant.DIR);
                ObjectInputStream ois = null;
                User user = null;
                if (cacheFile.exists()) {
                    try {
                        ois = new ObjectInputStream(new FileInputStream(Constant.DIR));
                        user = (User) ois.readObject();
                        LogUtil.d(this.getClass().getSimpleName(), "recoveryData==" + user);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        IoUtil.close(ois);
                    }
                }
            }
        }).start();
    }
}
