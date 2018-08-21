package com.android.tomflying.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
public class RegisterFragment extends MyFragment {

    EditText et_name;
    EditText et_password;
    EditText et_rePassword;
    QMUIRoundButton btn_register;

    @Override
    public boolean isNeedRegister() {
        return false;
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
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        et_name = contentView.findViewById(R.id.et_name);
        et_password = contentView.findViewById(R.id.et_password);
        et_rePassword = contentView.findViewById(R.id.et_repassword);
        btn_register = contentView.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onWidgetClick(View view) {
        String name = et_name.getEditableText().toString();
        String password = et_password.getEditableText().toString();
        String rePassword = et_rePassword.getEditableText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(rePassword)) {
            ToastUtils.showShort("请再次输入密码");
            return;
        }
        if (!TextUtils.equals(password, rePassword)) {
            ToastUtils.showShort("两次输入的密码不一致");
            return;
        }

        register(name, password, rePassword);
    }

    private void register(String name, String password, String rePassword) {

        HttpParams params = new HttpParams();
        params.put("username", name);
        params.put("password", password);
        params.put("repassword", rePassword);
        OkHttpUtil.postRequest(ApiConstant.User.REGISTER_URL, this, params, new JsonCallback<LzyResponse<LoginBean>>() {

            @Override
            public void onStart(Request<LzyResponse<LoginBean>, ? extends Request> request) {
                ToastUtils.cancel();
                showLoadingDialog("正在注册");
            }

            @Override
            public void onSuccess(Response<LzyResponse<LoginBean>> response) {
                SPUtils.getInstance().put(ConstantValues.NIKE_NAME, response.body().data.getUsername());
                SPUtils.getInstance().put(ConstantValues.PASSWORD, response.body().data.getPassword());
                GlobalParams.setIsLogin(true);
                EventBus.getDefault().post(new LoginEvent());
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
