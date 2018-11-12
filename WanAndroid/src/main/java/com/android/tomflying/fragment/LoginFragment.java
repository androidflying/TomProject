package com.android.tomflying.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.ConstantValues;
import com.android.tomflying.GlobalParams;
import com.android.tomflying.R;
import com.android.tomflying.base.MyFragment;
import com.android.tomflying.bean.LoginBean;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.event.LoginEvent;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.qmuiteam.tom.widget.roundwidget.QMUIRoundButton;
import com.tom.baselib.delay.SingleCall;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.SPUtils;
import com.tom.baselib.utils.ToastUtils;
import com.tom.network.OkGo;
import com.tom.network.model.HttpParams;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/10
 * 描述：
 */
public class LoginFragment extends MyFragment {

    EditText et_name;
    EditText et_password;
    QMUIRoundButton btn_login;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        et_name = contentView.findViewById(R.id.et_name);
        et_password = contentView.findViewById(R.id.et_password);
        btn_login = contentView.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(ConstantValues.NIKE_NAME))) {
            et_name.setText(SPUtils.getInstance().getString(ConstantValues.NIKE_NAME));
        }
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void destroyViewAndThing() {
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onWidgetClick(View view) {
        String name = et_name.getEditableText().toString();
        String password = et_password.getEditableText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }

        login(name, password);
    }

    private void login(final String name, final String password) {

        HttpParams params = new HttpParams();
        params.put("username", name);
        params.put("password", password);
        OkHttpUtil.postRequest(ApiConstant.User.LOGIN_URL, this, params, new JsonCallback<LzyResponse<LoginBean>>() {

            @Override
            public void onStart(Request<LzyResponse<LoginBean>, ? extends Request> request) {
                ToastUtils.cancel();
                showLoadingDialog("正在登录");
            }

            @Override
            public void onSuccess(Response<LzyResponse<LoginBean>> response) {
                SPUtils.getInstance().put(ConstantValues.NIKE_NAME, name);
                SPUtils.getInstance().put(ConstantValues.PASSWORD, password);
                GlobalParams.setIsLogin(true);
                EventBus.getDefault().post(new LoginEvent());
                SingleCall.getInstance().doCall();
                ActivityUtils.finishActivity(mActivity);
            }

            @Override
            public void onError(Response<LzyResponse<LoginBean>> response) {
                ToastUtils.showShort(response.body().errorMsg);
            }

            @Override
            public void onFinish() {
                missLoadingDialog();
            }
        });


    }
}
