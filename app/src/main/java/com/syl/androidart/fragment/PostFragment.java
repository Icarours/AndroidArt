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
import com.syl.androidart.utils.LogUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Bright on 2017/8/10.
 *
 * @Describe 提交post请求
 * @Called
 */

public class PostFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = PostFragment.class.getSimpleName();
    @BindView(R.id.btn_post_json)
    Button mBtnPostJson;
    @BindView(R.id.btn_post_key_value)
    Button mBtnPostKeyValue;
    Unbinder unbinder;
    @BindView(R.id.btn_encrypt_64)
    Button mBtnEncrypt64;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    private View mRootView;
    private String mUrl;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = View.inflate(getContext(), R.layout.fragment_post, null);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void initListener() {
        mBtnPostJson.setOnClickListener(this);
        mBtnPostKeyValue.setOnClickListener(this);
        mBtnEncrypt64.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mUrl = "http://httpbin.org/post";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_post_json:
                postJson();
                break;
            case R.id.btn_post_key_value:
                postKeyValue();
                break;
            case R.id.btn_encrypt_64:
                encryptBitmap64();
                break;
            default:
                break;
        }
    }

    /**
     * 使用Base64加密
     * <p>
     * client:drawable->bitmap->outputStream->clientStr
     * server:serverBytes->Bitmap->现实img
     */
    private void encryptBitmap64() {
        /*
         *Bitmap加密之后变成String,上传到服务器
         */
        Bitmap clientBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.mm2);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        clientBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] clientBytes = outputStream.toByteArray();
        String clientStr = Base64.encodeToString(clientBytes, Base64.DEFAULT);

        /*
         *从服务器拿到String,解密成Bitmap
         *服务器：String -> byte[] -> bitmap -显示到imageView
         */
        byte[] serverBytes = Base64.decode(clientStr, Base64.DEFAULT);
        Bitmap serverBitmap = BitmapFactory.decodeByteArray(serverBytes, 0, serverBytes.length);
        mIvImg.setImageBitmap(serverBitmap);
    }

    /**
     * httpClient提交json
     */
    private void postJson() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(mUrl);
//                    String city = "深圳";
//                    String encodeCity = URLEncoder.encode(city, "UTF-8");
                    String json = "{username:tom,}";
                    StringEntity params = new StringEntity("{\"username\":\"heima20\", \"city\":\"深圳\"}");

                    //告诉服务器，我们提交的参数类型:json
                    httpPost.setHeader("Content-Type", "application/json");
                    httpPost.setEntity(params);

                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    String result = EntityUtils.toString(entity);
                    LogUtil.d(TAG, "result==" + result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * httpClient提交表单
     */
    private void postKeyValue() {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(mUrl);

                    //设置提交参数：key value

                    //告诉服务器，我们提交的参数类型:key-value形式
                    post.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    List<NameValuePair> listParams = new ArrayList<>();
                    //添加参数
                    BasicNameValuePair nameValuePair = new BasicNameValuePair("username", "heima20");
                    BasicNameValuePair nameValuePair2 = new BasicNameValuePair("city", "深圳");

                    listParams.add(nameValuePair);
                    listParams.add(nameValuePair2);

                    UrlEncodedFormEntity params = new UrlEncodedFormEntity(listParams);
                    post.setEntity(params);

                    HttpResponse response = client.execute(post);


                    HttpEntity entity = response.getEntity();
                    String result = EntityUtils.toString(entity);

                    LogUtil.d("result", result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
