
package com.tuling.pqb.core.util;

import com.tuling.pqb.config.properties.PqbProperties;
import cn.stylefeng.roses.core.util.SpringContextHolder;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(PqbProperties.class).getKaptchaOpen();
    }
}