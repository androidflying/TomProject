package com.hub.welfare.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hub.welfare.ConstantValues;
import com.hub.welfare.R;
import com.hub.welfare.base.MyFragment;
import com.hub.welfare.player.JZExoPlayer;
import com.hub.welfare.player.JZMediaIjkplayer;
import com.hub.welfare.ui.MainActivity;
import com.hub.welfare.util.DialogUtil;
import com.hub.welfare.util.ThemeUtils;
import com.tom.baselib.utils.AppUtils;
import com.tom.baselib.utils.LogUtils;
import com.tom.baselib.utils.SPUtils;
import com.tom.baselib.utils.TimeUtils;
import com.tom.baselib.utils.ToastUtils;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZMediaSystem;
import cn.jzvd.Jzvd;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/1
 * 描述：
 */
public class MoreFragment extends MyFragment {

    private SwitchCompat switch_dayNight;
    private SwitchCompat switch_skip;
    private SwitchCompat switch_redirect;
    private SwitchCompat switch_wifi;
    private TextView tv_version;


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

    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_more;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        tv_version = contentView.findViewById(R.id.tv_version);
        switch_dayNight = contentView.findViewById(R.id.switch_dayNight);
        switch_wifi = contentView.findViewById(R.id.switch_wifi);
        switch_skip = contentView.findViewById(R.id.switch_skip);
        switch_redirect = contentView.findViewById(R.id.switch_redirect);
        tv_version.setText("版本号：" + AppUtils.getAppVersionName() + "\n©" + TimeUtils.getNowString(new SimpleDateFormat("YYYY")) + " TomFlying WelfareHub All rights reserved.");

        contentView.findViewById(R.id.ll_history).setOnClickListener(this);
        contentView.findViewById(R.id.ll_download).setOnClickListener(this);
        contentView.findViewById(R.id.ll_photo).setOnClickListener(this);
        contentView.findViewById(R.id.ll_video).setOnClickListener(this);
        contentView.findViewById(R.id.ll_player).setOnClickListener(this);
        contentView.findViewById(R.id.ll_update).setOnClickListener(this);
        contentView.findViewById(R.id.ll_pay).setOnClickListener(this);
        contentView.findViewById(R.id.ll_share).setOnClickListener(this);
        contentView.findViewById(R.id.ll_dayNight).setOnClickListener(this);
        contentView.findViewById(R.id.ll_skip).setOnClickListener(this);
        contentView.findViewById(R.id.ll_redirect).setOnClickListener(this);
        contentView.findViewById(R.id.ll_wifi).setOnClickListener(this);
        switch_dayNight.setChecked(ThemeUtils.getDayNight());
        switch_dayNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ThemeUtils.setDayNight((MainActivity) getActivity(), isChecked);
            }
        });
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.ll_history:
                ToastUtils.showShort("打开最近浏览");
                break;
            case R.id.ll_download:
                ToastUtils.showShort("打开下载记录");
                break;
            case R.id.ll_dayNight:
                ThemeUtils.setDayNight((MainActivity) getActivity());
                switch_dayNight.toggle();
                break;
            case R.id.ll_photo:
                String[] photos = {"花瓣网", "妹子图", "九妹图社"};
                //打开弹窗单选
                DialogUtil.showSingleChoose(photos, SPUtils.getInstance().getInt(ConstantValues.PHOTO_ENGINE, 0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            default:
                        }
                        SPUtils.getInstance().put(ConstantValues.PHOTO_ENGINE, which);
                        ToastUtils.showShort("图片地址切换成功");
                        dialog.dismiss();
                        //发送一条消息告诉PhotoFragment切换地址了需要刷新。
                    }
                });
                break;
            case R.id.ll_video:
                ToastUtils.showShort("设置视频地址");
                break;
            case R.id.ll_player:
                String[] players = {"MediaPlayer", "IJKplayer", "ExoPlayer"};
                //打开弹窗单选
                DialogUtil.showSingleChoose(players, SPUtils.getInstance().getInt(ConstantValues.PLAYER_ENGINE, 0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Jzvd.setMediaInterface(new JZMediaSystem());
                                break;
                            case 1:
                                Jzvd.setMediaInterface(new JZMediaIjkplayer());
                                break;
                            case 2:
                                Jzvd.setMediaInterface(new JZExoPlayer());
                                break;
                            default:
                                Jzvd.setMediaInterface(new JZMediaSystem());
                        }

                        SPUtils.getInstance().put(ConstantValues.PLAYER_ENGINE, which);
                        ToastUtils.showShort("播放引擎切换成功");
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.ll_wifi:
                ToastUtils.showShort("wifi");
                break;
            case R.id.ll_skip:
                ToastUtils.showShort("跳转");
                break;
            case R.id.ll_redirect:
                ToastUtils.showShort("重定向");
                break;
            case R.id.ll_update:
                ToastUtils.showShort("检查更新");
                break;
            case R.id.ll_pay:
                ToastUtils.showShort("打赏支付");
                break;
            case R.id.ll_share:
                ToastUtils.showShort("分享朋友");
                break;
            default:

        }
    }
}
