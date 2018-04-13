package com.laisontech.mvp.net;

/**
 * Created by SDP on 2018/4/13.
 */

public interface OnConnectResultListener {
    void onResponse(String response);

    void onError(String errorMsg);
}
