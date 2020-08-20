package com.tuling.pqb.core.util;

/**
 * 业务过程中的 通用方法封装
 *
 * @author bianwenjing@cntytz.com
 * @version 1.0
 * @date 2020/7/17 11:17
 */
public class BusUtil {


    public static String StringsecondToTime(long second) {
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数

        return hours + ":" + minutes + ":" + second;

    }

}
