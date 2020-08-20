package com.tuling.pqb.modular.bus.service.impl;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.tuling.pqb.core.util.AliyunSmsUtils;
import com.tuling.pqb.core.util.RedisUtil;
import com.tuling.pqb.modular.bus.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: party-question-bank
 * @description: 短信实现类
 * @author: gaohan
 * @create: 2020-07-14 22:58
 */
@Service
public class SmsServiceImpl implements SmsService {

    private final static String MOBILE_KEY = "pqb:sms:";

    private AliyunSmsUtils aliyunSmsUtils;

    private RedisUtil redisUtil;


    @Override
    public ResponseData sendValidateCode(String mobile) {
        String code = createRandom(true, 6);
        String mobileKey = MOBILE_KEY + mobile;
        //10分钟
        long seconds = 10 * 60;
        //发送验证码到手机上
        boolean validateCode = sendMobleCode(code, mobile, mobileKey, seconds);
        if (!validateCode) {
            return ResponseData.error("验证码发送失败");
        }
        return ResponseData.success("验证码发送成功");
    }

    @Override
    public Boolean checkValidateCode(String phoneNumber, String smsCode) {
        String mobileKey = MOBILE_KEY + phoneNumber;
        String code = redisUtil.get(mobileKey)+"";
        if (ToolUtil.isEmpty(code)) {
            return false;
        }
        if (smsCode.equals(code)) {
            redisUtil.del(mobileKey);
            return true;
        }
        return false;
    }

    /**
     * 验证码发送
     *
     * @param code      验证码
     * @param mobile    手机号
     * @param mobileKey key
     * @param seconds   过期时间
     * @return
     */
    private boolean sendMobleCode(String code, String mobile, String mobileKey, long seconds) {
        boolean sms = aliyunSmsUtils.sendSms(mobile, code, mobileKey, seconds);
        return sms;
    }

    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag 是否是数字
     * @param length     长度
     * @return String
     */
    private String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    @Resource
    public void setAliyunSmsUtils(AliyunSmsUtils aliyunSmsUtils) {
        this.aliyunSmsUtils = aliyunSmsUtils;
    }

    @Resource
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
