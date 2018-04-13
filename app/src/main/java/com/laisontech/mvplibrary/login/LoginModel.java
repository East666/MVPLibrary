package com.laisontech.mvplibrary.login;

import com.laisontech.mvp.entity.OperatorInfo;
import com.laisontech.mvp.laisonjsonparse.JsonResultParseUtils;
import com.laisontech.mvp.net.OkHttpConnect;
import com.laisontech.mvp.net.OnConnectResultListener;

/**
 * Created by SDP on 2018/4/13.
 */

public class LoginModel {
    private String url = "http://laisontechsoft.xicp.net:18082/?function=login&operatoruid=Panda&operatorpwd=4bcbbbc00b0341b4b413cffc9eb02208";
    private String url2 ="http://laisontechsoft.xicp.net:18082/?function=maintainerlogin&accountname=Panda&password=4bcbbbc00b0341b4b413cffc9eb02208";
    interface OnLoginListener {
        void success(String successMsg);

        void failed(String failedMsg);
    }

    public void executeLogin(String uid, String pwd, final OnLoginListener listener) {
        OkHttpConnect.getInstance().buildGetString(url, "login", new OnConnectResultListener() {
            @Override
            public void onResponse(String response) {
                JsonResultParseUtils.ParseResultBean parseResultBean = JsonResultParseUtils.getResult(response, OperatorInfo.class, JsonResultParseUtils.ResultType.OBJECT);
                int errorCode = parseResultBean.getErrorCode();
                if (errorCode == JsonResultParseUtils.FORMAT_ERROR) {
                    listener.failed("data format error!");
                } else if (errorCode == JsonResultParseUtils.ERROR_CODE_SUCCESS) {
                    OperatorInfo operatorInfo = (OperatorInfo) parseResultBean.getParseResult();
                    listener.success(operatorInfo.toString());
                } else {
                    listener.success(errorCode + "");
                }
            }

            @Override
            public void onError(String errorMsg) {
                listener.failed(errorMsg);
            }
        });
    }
}
