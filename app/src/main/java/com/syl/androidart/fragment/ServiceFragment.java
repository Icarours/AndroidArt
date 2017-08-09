package com.syl.androidart.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;
import com.syl.androidart.service.BanzhengService;
import com.syl.androidart.service.IXiaoMi;
import com.syl.androidart.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Bright on 2017/8/9.
 *
 * @Describe service 举例,使用startService()方法开启的Service可以在不被销毁的情况下一直运行在后台
 * @Called
 */

public class ServiceFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = ServiceFragment.class.getSimpleName();
    @BindView(R.id.btn_bind_service)
    Button mBtnBindService;
    @BindView(R.id.btn_call_method_in_service)
    Button mBtnCallMethodInService;
    @BindView(R.id.btn_unbind_service)
    Button mBtnUnbindService;
    Unbinder unbinder;
    @BindView(R.id.btn_get_current_time)
    Button mBtnGetCurrentTime;
    @BindView(R.id.btn_start_service)
    Button mBtnStartService;
    private View mRootView;
    private IXiaoMi mXiaoMi;
    private ServiceConnection mConn;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = View.inflate(getContext(), R.layout.fragment_banzheng, null);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void initListener() {
        mBtnBindService.setOnClickListener(this);
        mBtnCallMethodInService.setOnClickListener(this);
        mBtnUnbindService.setOnClickListener(this);
        mBtnGetCurrentTime.setOnClickListener(this);
        mBtnStartService.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                Intent intent = new Intent(getActivity(), BanzhengService.class);
                getActivity().startService(intent);
                break;
            case R.id.btn_bind_service:
                connectXiaoMi();
                break;
            case R.id.btn_call_method_in_service:
                if (mXiaoMi == null) {//如果没有绑定服务,就弹出友好提示,返回
                    Toast.makeText(getActivity(), "请先绑定服务,找小蜜咨询..", Toast.LENGTH_SHORT).show();
                    System.out.println(this.getClass().getSimpleName() + "请先绑定服务,找小蜜咨询..");
                    return;
                }
                mXiaoMi.banzheng("张三", 100);
                System.out.println(this.getClass().getSimpleName() + "调用Service中的方法..");
                Toast.makeText(getActivity(), "调用Service中的方法..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_get_current_time:
                if (mXiaoMi == null) {//如果没有绑定服务,就弹出友好提示,返回
                    Toast.makeText(getActivity(), "请先绑定服务,找小蜜咨询..", Toast.LENGTH_SHORT).show();
                    System.out.println(this.getClass().getSimpleName() + "请先绑定服务,找小蜜咨询..");
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
//                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                long currentTime = mXiaoMi.getCurrentTime();
                String str = formatter.format(currentTime);
                LogUtil.d(TAG, "当下的时间==" + str);
                break;
            case R.id.btn_unbind_service:
                getActivity().unbindService(mConn);
                mXiaoMi = null;
                System.out.println(this.getClass().getSimpleName() + "解除绑定Service..");
                Toast.makeText(getActivity(), "解除绑定Service..", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     * 格式化时间
     *
     * @param timeInMillis
     * @return
     */
    public static String formatTimeInMillis(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fmt = dateFormat.format(date);

        return fmt;
    }

    /**
     * 连接小蜜/内线
     */
    private void connectXiaoMi() {
        Intent intent = new Intent(getActivity(), BanzhengService.class);
        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mXiaoMi = (IXiaoMi) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.d(TAG, "断开连接service");
            }
        };
        getActivity().bindService(intent, mConn, BIND_AUTO_CREATE);
        System.out.println(this.getClass().getSimpleName() + "绑定服务成功..");
        Toast.makeText(getContext(), "绑定服务成功..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
