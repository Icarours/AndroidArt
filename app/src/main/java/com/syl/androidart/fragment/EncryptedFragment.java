package com.syl.androidart.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Bright on 2017/8/10.
 *
 * @Describe
 * @Called
 */

public class EncryptedFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.iv_img)
    ImageView mIvImg;
    Unbinder unbinder;
    @BindView(R.id.btn_encrypt_64)
    Button mBtnEncrypt64;
    private View mRootView;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = View.inflate(getContext(), R.layout.fragment_encrpyt, null);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void initListener() {
        mBtnEncrypt64.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_encrypt_64:
                encryptBitmap64();
                break;
            default:
                break;
        }
    }

    private void encryptBitmap64() {
        Bitmap clientBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.mm4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        clientBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] clientBytes = outputStream.toByteArray();
        String clientStr = Base64.encodeToString(clientBytes, Base64.DEFAULT);

        byte[] serverBytes = Base64.decode(clientStr, Base64.DEFAULT);
        Bitmap serverBitmap = BitmapFactory.decodeByteArray(serverBytes, 0, serverBytes.length);
        mIvImg.setImageBitmap(serverBitmap);
    }
}
