package com.laisontech.mvp.net;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * Created by SDP on 2018/4/13.
 */

public class OkHttpConnect {
    private static OkHttpConnect mInstance;

    private OkHttpConnect() {
    }

    public static OkHttpConnect getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpConnect.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpConnect();
                }
            }
        }
        return mInstance;
    }

    /**
     * @param connectDuration 每次连接的时长
     * @param connectTimes    重连的次数
     */
    public void setConnectParam(int connectDuration, int connectTimes) {
        OkHttpHelper.getInstance().setConnectDuration(connectDuration);
        OkHttpHelper.getInstance().setMaxConnectTimes(connectTimes);
    }

    /**
     * 取消连接
     */
    public void cancelTag(String tag) {
        OkHttpHelper.getInstance().cancelTag(tag);
    }

    /**
     * get String请求
     */
    public void buildGetString(String url, String tag, OnConnectResultListener listener) {
        OkHttpHelper.getInstance().setDefaultConnectTimes();
        OkHttpHelper.getInstance().buildGetString(url, tag, listener);
    }

    /**
     * post String请求
     */
    public void buildPostString(String url, String tag, LinkedHashMap<String, String> map, OnConnectResultListener listener) {
        OkHttpHelper.getInstance().setDefaultConnectTimes();
        OkHttpHelper.getInstance().buildPostString(url, tag, map, listener);
    }

    /**
     * post json
     */
    public void buildPostJson(String url, String tag, Object jsonObj, OnConnectResultListener listener) {
        OkHttpHelper.getInstance().setDefaultConnectTimes();
        OkHttpHelper.getInstance().buildPostJson(url, tag, jsonObj, listener);
    }

    /**
     * post file
     */
    public void buildPostFile(String url, String tag, File file, OnConnectResultListener listener) {
        OkHttpHelper.getInstance().setDefaultConnectTimes();
        OkHttpHelper.getInstance().buildPostFile(url, tag, file, listener);
    }
}
