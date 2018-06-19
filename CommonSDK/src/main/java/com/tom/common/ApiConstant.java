package com.tom.common;

import com.tom.baselib.utils.SPUtils;
import com.tom.baselib.utils.StringUtils;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/19
 * 描述：API接口定义
 */
public class ApiConstant {
    /**
     * 测试IP TODO
     */
    private static final String DEBUG_IP = "";

    /**
     * 正式IP TODO
     */
    private static final String RELEASE_IP = "";

    /**
     * 文件接口 TODO
     */
    private static final String FILE_URL = "";

    /**
     * Base接口地址
     */
    public static final String BASE_URL = BuildConfig.DEBUG ? getIPFromSP() : RELEASE_IP;


    /**
     * 图片地址 TODO
     */
    public static final String IMG_PRE = FILE_URL + "";


    public interface ModuletTest {

        String TEST_URL = "";
    }


    /**
     * 方便测试 ,可以在设置--长按设置标题,修改ip
     *
     * @return testIp
     */
    private static String getIPFromSP() {
        String ipTest = SPUtils.getInstance().getString(ConstantValues.IP_FROM_SP);
        if (StringUtils.isEmpty(ipTest)) {
            ipTest = DEBUG_IP;
        }
        return ipTest;

    }
}
