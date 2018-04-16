package com.laisontech.mvp.net;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.LinkedHashMap;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by SDP on 2018/4/13.
 * 连接使用
 */

 class OkHttpHelper {
    private static OkHttpHelper mInstance;
    private OnConnectResultListener mConnectListener;
    private int DEFAULT_START_CONNECT_TIMES = 0;
    private int DEFAULT_CONNECT_DURATION = 20 * 1000;
    private int MAX_CONNECT_TIMES = 3;//最大的重连次数
    private int connectTimes = DEFAULT_START_CONNECT_TIMES;//默认从0开始

     static OkHttpHelper getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpHelper.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpHelper();
                }
            }
        }
        return mInstance;
    }

    private OkHttpHelper() {
    }

    //设置连接时间 毫秒
     void setConnectDuration(int connectTime) {
        DEFAULT_CONNECT_DURATION = connectTime;
    }

    //设置最大的连接次数
     void setMaxConnectTimes(int defaultConnectTime) {
        MAX_CONNECT_TIMES = defaultConnectTime;
    }

    //设置从第0次开始连接
     void setDefaultConnectTimes() {
        this.connectTimes = DEFAULT_START_CONNECT_TIMES;
    }

    /**
     * get请求 String(三次重发)
     */
     void buildGetString(final String url, final String tag, final OnConnectResultListener listener) {
        mConnectListener = listener;
        getString(url, tag, new OnRetryListener() {
            @Override
            public void onRetry() {
                buildGetString(url, tag, listener);
            }
        });
    }

    private void getString(String url, String tag, OnRetryListener retryListener) {
        OkHttpUtils.get().url(url).tag(tag).build().connTimeOut(DEFAULT_CONNECT_DURATION).execute(new UserStringCallback(retryListener));
    }

    /**
     * Post 请求 String
     */
     void buildPostString(final String url, final String tag, final LinkedHashMap<String, String> map, final OnConnectResultListener listener) {
         mConnectListener = listener;
        postString(url, tag, map, new OnRetryListener() {
            @Override
            public void onRetry() {
                buildPostString(url, tag, map, listener);
            }
        });
    }

    private void postString(String url, String tag, LinkedHashMap<String, String> map, final OnRetryListener listener) {
        OkHttpUtils.post().url(url).tag(tag).params(map).build().connTimeOut(DEFAULT_CONNECT_DURATION).execute(new UserStringCallback(listener));
    }

    /**
     * post 请求Json
     */
     void buildPostJson(final String url, final String tag, final Object objJson, final OnConnectResultListener listener) {
         mConnectListener = listener;
        postJson(url, tag, objJson, new OnRetryListener() {
            @Override
            public void onRetry() {
                buildPostJson(url, tag, objJson, listener);
            }
        });
    }

    private void postJson(String url, String tag, Object objJson, OnRetryListener listener) {
        OkHttpUtils.postString().url(url).tag(tag).mediaType(MediaType.parse("application/json; charset=utf-8")).content(new Gson().toJson(objJson))
                .build().connTimeOut(DEFAULT_CONNECT_DURATION).execute(new UserStringCallback(listener));
    }

    /**
     * Post File
     */
     void buildPostFile(final String url, final String tag, final File file, final OnConnectResultListener listener) {
         mConnectListener = listener;
        postFile(url, tag, file, new OnRetryListener() {
            @Override
            public void onRetry() {
                buildPostFile(url, tag, file, listener);
            }
        });
    }

    private void postFile(String url, String tag, File file, final OnRetryListener listener) {
        if (!file.exists()) return;
        OkHttpUtils.postFile().url(url).tag(tag).file(file).build().connTimeOut(DEFAULT_CONNECT_DURATION).execute(new UserStringCallback(listener));
    }

    /**
     * 取消连接，依据tag
     */
     void cancelTag(String tag) {
        OkHttpUtils.getInstance().cancelTag(tag);
    }

    /**
     * 连接的回调接口
     */
    private class UserStringCallback extends StringCallback {
        private OnRetryListener retryListener;

        UserStringCallback(OnRetryListener retryListener) {
            this.retryListener = retryListener;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            if (connectTimes >= MAX_CONNECT_TIMES) {
                mConnectListener.onError(getNetErrorMsg(e));//失败了三次才会发送失败的原因
                return;
            }
            retryListener.onRetry();            //重发的事件
            connectTimes++;
        }

        @Override
        public void onResponse(String response, int id) {
            connectTimes = MAX_CONNECT_TIMES;
            mConnectListener.onResponse(response);
        }
    }


    //网络请求错误提示
    private String getNetErrorMsg(Exception e) {
        String message = e.getMessage();
        if (message == null || TextUtils.isEmpty(message)) {
            return "Time out";
        }
        return message;
    }

    //重连事件的接口
    public interface OnRetryListener {
        void onRetry();
    }
}
