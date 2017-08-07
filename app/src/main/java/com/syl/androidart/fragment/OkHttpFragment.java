package com.syl.androidart.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;
import com.syl.androidart.utils.LogUtil;
import com.syl.androidart.utils.StringTool;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Bright on 2017/8/7.
 *
 * @Describe OkHttp 上传文件失败,post请求失败
 * @Called
 */

public class OkHttpFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = OkHttpFragment.class.getSimpleName();
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    @BindView(R.id.btn_ok_http_get)
    Button mBtnOkHttpGet;
    @BindView(R.id.btn_ok_http_post)
    Button mBtnOkHttpPost;
    @BindView(R.id.btn_ok_http_post_file)
    Button mBtnOkHttpPostFile;
    @BindView(R.id.btn_ok_http_down_file)
    Button mBtnOkHttpDownFile;
    @BindView(R.id.btn_ok_http_multipart)
    Button mBtnOkHttpMultipart;
    Unbinder unbinder;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.wv)
    WebView mWv;
    private View mRootView;
    private OkHttpClient mOkHttpClient;
    private X509Certificate mServerCert;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = View.inflate(getActivity(), R.layout.fragment_okhttp, null);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        //webView 的设置
        WebSettings settings = mWv.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        return mRootView;
    }

    @Override
    public void initListener() {
        mBtnOkHttpGet.setOnClickListener(this);
        mBtnOkHttpPost.setOnClickListener(this);
        mBtnOkHttpPostFile.setOnClickListener(this);
        mBtnOkHttpDownFile.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }

    /**
     * OkHttp的超时和缓存
     */
    private void okHttpTimeOut() {
        File sdcache = getActivity().getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        OkHttpClient mOkHttpClient = builder.build();
    }

    /**
     * 发送多文件
     */
    private void sendMultipart() {
        mOkHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "wangshu")
                .addFormDataPart("image", "wangshu.jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.i("wangshu", response.body().string());
            }
        });
    }

    /**
     * 异步下载文件(成功)
     */
    private void downAsyncFile() {
        mOkHttpClient = new OkHttpClient();
        String url = "http://192.168.31.89:8080/img/mm4.jpg";
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(TAG, "下载失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                String dir = Environment.getExternalStorageDirectory().getPath();
                File file = new File("/sdcard/mm8.jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] buf = new byte[1024 * 8];
                int len = 0;
                while ((len = inputStream.read(buf)) != -1) {
                    fileOutputStream.write(buf, 0, len);
                }
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mIvImg.setImageBitmap(bitmap);
                    }
                });
                fileOutputStream.flush();
                fileOutputStream.close();
                LogUtil.d(TAG, "下载成功");
            }
        });
    }

    /**
     * okHttp异步上传文件
     * 当然如果想要改为同步的上传文件只要调用 mOkHttpClient.newCall(request).execute()就可以了。
     */
    private void postAsyncFile() {
        mOkHttpClient = new OkHttpClient();
        String url = "http://localhost:8080/upload_download/upload.jsp";
        File file = new File("/sdcard/microlog.txt");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(TAG, "post 上传失败..");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtil.d(TAG, "post 上传成功.." + string);
            }
        });
    }

    /**
     * okHttp post异步请求
     */
    private void postAsyncOkHttp() {
        //1.创建OkHttpClient
        mOkHttpClient = new OkHttpClient();
        String url = "";// TODO: 2017/8/7  网络请求地址和编码格式
        RequestBody requestBody = new FormBody.Builder()
                .add("name", "tom")
                .addEncoded("charset", "UTF-8")
                .build();
        //2.创建Request,Request.Builder()
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        //3.创建Call,并将Call加入请求队列
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(TAG, "okHttp请求失败-----post异步请求");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseResult;
                if (null != response) {
                    responseResult = response.body().string();
                    LogUtil.d(TAG, "responseResult==" + responseResult + "post 异步请求");
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "okHttp请求成功..post异步", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * okHttp get异步请求
     */
    public void getAsyncOkHttp() {
        //1.创建OkHttpClient
        mOkHttpClient = new OkHttpClient();
        String url = "https://www.baidu.com/";
        Request request = new Request
                .Builder()
                .method("GET", null)
                .url(url)
                .build();
        //2.创建Call
        Call call = mOkHttpClient.newCall(request);
        //3.将Call添加到队列,拿到回调数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(TAG, "okHttp请求失败-----get异步请求");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseResult;
                if (null != response.cacheResponse()) {
                    responseResult = response.cacheResponse().toString();
                    String string = response.body().string();
                    LogUtil.d(TAG, "string==" + string);
                    LogUtil.d(TAG, "responseResult==" + responseResult + "get 异步请求");
                } else {
                    responseResult = response.networkResponse().toString();
//                    response.body().string();
                    LogUtil.d(TAG, "responseResult==" + responseResult + "get 异步请求");
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "okHttp请求成功..get异步", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok_http_get:
//                getAsyncOkHttp();
                okHttpHttpsGet();
                break;
            case R.id.btn_ok_http_post:
                postAsyncOkHttp();
                break;
            case R.id.btn_ok_http_post_file:
                postAsyncFile();
                break;
            case R.id.btn_ok_http_down_file:
                downAsyncFile();
                break;
            case R.id.btn_ok_http_multipart:
                sendMultipart();
                break;
            default:
                break;
        }
    }

    /**
     * OkHttp Https get请求
     */
    private void okHttpHttpsGet() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在加载,请稍后..");
        progressDialog.setTitle("加载中..");
        progressDialog.show();

        //1.加载服务器签名证书,拿到证书
        try {
            String certName = "baiducom.crt";
            InputStream certInput = new BufferedInputStream(getActivity().getAssets().open(certName));
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            mServerCert = (X509Certificate) certificateFactory.generateCertificate(certInput);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        String https_url = "https://www.baidu.com/";
        try {
            //2.获取证书管理器,校验证书
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            if (chain == null) {
                                throw new IllegalArgumentException("Check Server x509Certificate is null");
                            }
                            if (chain.length < 0) {
                                throw new IllegalArgumentException("Check Server x509Certificate is empty");
                            }
                            for (X509Certificate cert : chain) {
                                //校验证书,检查服务器端签名
                                cert.checkValidity();
                                try {
                                    //和预埋的app证书做对比
                                    cert.verify(mServerCert.getPublicKey());
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (InvalidKeyException e) {
                                    e.printStackTrace();
                                } catch (NoSuchProviderException e) {
                                    e.printStackTrace();
                                } catch (SignatureException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };
            //3.初始化SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, null);
            //4.创建SSLSocketFactory
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            URL url = new URL(https_url);
            final OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newBuilder()
                    .sslSocketFactory(socketFactory, (X509TrustManager) trustAllCerts[0])
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream inputStream = response.body().byteStream();

                    final String s = StringTool.inputStream2String(inputStream);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWv.loadDataWithBaseURL(null, s, "text/html", "utf-8", null);
                            //System.out.println(s);
                            progressDialog.dismiss();
                        }
                    });
                }
            });

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
