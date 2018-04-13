package com.laisontech.mvplibrary.login;


import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.laisontech.mvp.mvp.MVPBaseActivity;
import com.laisontech.mvplibrary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_uid)
    EditText etUid;
    @BindView(R.id.et_pwd)
    EditText etPwd;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        mPresenter.login(etUid.getText().toString(), etPwd.getText().toString());
    }

    @Override
    public void loginSuccess(String successMsg) {
        Log.e("LOGIN", "loginSuccess: "+successMsg );
    }

    @Override
    public void loginFailed(String failMsg) {
        Log.e("LOGIN", "loginFAILED: "+failMsg );
    }
}
