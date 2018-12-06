package com.tom.im.util;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.tom.baselib.utils.ProcessUtils;
import com.tom.baselib.utils.Utils;
import com.tom.im.widget.TomExtensionModule;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.provider.RealTimeLocationMessageProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/24
 * 描述：第三方即时通讯接口
 */
public class ChatUtil {

    /**
     * IM初始化
     */
    public static void init() {
        RongIM.init(Utils.getApp());
        UserInfoManager.init(Utils.getApp());
    }

    /**
     * 设置一些常用配置
     */
    public static void setDefault() {
        //设置会话界面未读新消息是否展示 注:未读新消息大于1条即展示
        RongIM.getInstance().enableNewComingMessageIcon(true);
        //设置会话界面历史消息是否展示 注:历史消息大于10条即展示
        RongIM.getInstance().enableUnreadMessageIcon(true);
    }

    /**
     * 注销当前登录，执行该方法后不会再收到推送消息
     */
    public static void logout() {
        RongIM.getInstance().logout();
    }

    /**
     * 断开连接(断开后继续接收 Push 消息)。
     */
    public static void disconnect() {
        RongIM.getInstance().disconnect();
    }

    /**
     * 注册渠道推送
     */
    public static void registerPush() {
        //TODO 把需要的平台申请的appId和appKey替换掉
        RongPushClient.registerMiPush(Utils.getApp(), "2882303761517473625", "5451747338625");
    }

    /**
     * 在获取token之后，连接IM服务器
     *
     * @param application
     * @param token
     */
    public static void connect(Application application, String token) {
        if (ProcessUtils.isMainProcess()) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    /**
     * 注册消息类型和消息模板，如果不扩展就不调用此方法
     */
    public static void registerMessage() {
        //TODO 添加消息类型和消息模板
        RongIM.registerMessageTemplate(new RealTimeLocationMessageProvider());
    }


    public static void setConnectionStatusListener() {
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                switch (connectionStatus) {
                    case CONN_USER_BLOCKED:
                        //用户被开发者后台封禁
                        break;
                    case CONNECTED:
                        //连接成功
                        break;
                    case CONNECTING:
                        //连接中
                        break;
                    case DISCONNECTED:
                        //断开连接
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT:
                        //用户账户在其他设备登录，本机会被踢掉线。
                        break;
                    case NETWORK_UNAVAILABLE:
                        //网络不可用
                        break;
                    case SERVER_INVALID:
                        //服务器异常或无法连接
                        break;
                    case TOKEN_INCORRECT:
                        //Token不正确
                        break;
                    default:
                }
            }
        });
    }

    /**
     * 设置会话列表界面操作的监听器。
     * 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    public static void setConversationListBehaviorListener() {

        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                //当点击会话头像后执行。
                return false;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                //当长按会话头像后执行。
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
                //长按会话列表中的 item 时执行。
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
                //点击会话列表中的 item 时执行。
                return false;
            }
        });

    }

    /**
     * 设置会话界面操作的监听器。
     * 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    public static void setConversationClickListener() {
        RongIM.setConversationClickListener(new RongIM.ConversationClickListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                //当点击用户头像后执行。
                return false;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                //当长按用户头像后执行。
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                //当点击消息时执行。
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s, Message message) {
                //当点击链接消息时执行。
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                //当长按消息时执行。
                return false;
            }
        });
    }

    /**
     * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
     * <p>
     * 用户信息提供者的逻辑移到UserInfoManager
     * 先从数据库读,没有数据时从网络获取
     */
    public static void setUserInfoProvider() {
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                UserInfoManager.getInstance().getUserInfo(s);
                return null;
            }
        }, true);
    }

    /**
     * 设置群组信息的提供者。
     */
    public static void setGroupInfoProvider() {
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {
                UserInfoManager.getInstance().getGroupInfo(s);
                return null;
            }
        }, true);
    }

    /**
     * 设置位置信息的提供者。
     */
    public static void setLocationProvider() {
        RongIM.setLocationProvider(new RongIM.LocationProvider() {
            @Override
            public void onStartLocation(Context context, LocationCallback locationCallback) {
                //TODO
            }
        });
    }

    /**
     * 设置输入框的提供者，扩展输入属性，比如小视频、红包、评分等插件的UI展示
     */
    public static void setInputProvider() {
        //设置接收消息的监听器。所有接收到的消息、通知、状态都经由此处设置的监听器处理。包括私聊消息、讨论组消息、群组消息、聊天室消息以及各种状态。
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                return false;
            }
        });


        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new TomExtensionModule());
            }
        }
    }
}
