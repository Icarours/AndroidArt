package com.syl.androidart.fragment;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Bright on 2017/5/19.
 *
 * @Describe 文件, 数据存储
 * <p>
 * 核心类
 * Context
 * 类中提供的openFileInput()和openFileOutput()方法
 * @Called
 */

public class FileFragment extends BaseFragment implements View.OnClickListener {

    public static final String ARG_FILE_NUMBER = "file";
    private View mRootView;
    private BufferedWriter mBufferedWriter;
    private TextView mTvContent;
    private EditText mEtContent;
    private BufferedReader mBufferedReader;
    private WebView mWebView;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_file, null);
        mTvContent = (TextView) mRootView.findViewById(R.id.tv_content);
        mEtContent = (EditText) mRootView.findViewById(R.id.et_content);

        mWebView = (WebView) mRootView.findViewById(R.id.wv);

        return mRootView;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mRootView.findViewById(R.id.btn_save_file).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_read_file).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_web_view).setOnClickListener(this);
    }

    /**
     * 读取文件夹下/data/data/<package name>/files/的数据
     *
     * @return
     */
    private String readFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = getActivity().openFileInput("data.txt");
            mBufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            if ((line = mBufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mBufferedReader != null) {
                try {
                    mBufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 保存字符串数据
     * <p>
     * openFileOutput(String name, int mode)中第一个参数是文件名,第二个是存储模式
     * 主要有两种模式可选，
     * MODE_PRIVATE 和MODE_APPEND
     * 文件的存储位置:/data/data/<package name>/files/
     *
     * @param input
     */
    private void saveFile(String input) {
        try {
            FileOutputStream fileOutputStream = getActivity().openFileOutput("data.txt", Context.MODE_APPEND);
            mBufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            mBufferedWriter.write(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mBufferedWriter != null) {
                try {
                    mBufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_file:
                String input = mEtContent.getText().toString();
                saveFile(input);
                break;
            case R.id.btn_read_file:
                String s = readFile();
                mTvContent.setText(s);
                break;
            case R.id.btn_web_view:
                mWebView.loadUrl("https://www.baidu.com/");
//        web.loadUrl("http://www.9ku.com/");

                mWebView.getSettings().setJavaScriptEnabled(true);  //加上这一行网页为响应式的
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;   //返回true， 立即跳转，返回false,打开网页有延时
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        String input = mEtContent.getText().toString();
        saveFile(input);
    }

}

