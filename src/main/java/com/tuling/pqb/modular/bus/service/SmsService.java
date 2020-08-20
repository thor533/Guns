package com.tuling.pqb.modular.bus.service;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

/**
 * @program: party-question-bank
 * @description: 短信接口
 * @author: gaohan
 * @create: 2020-07-14 22:58
 */
public interface SmsService {
    /**
     * 验证码发送
     * @param mobile 手机号
     * @return
     */
    public ResponseData sendValidateCode(String mobile);

    /**
     * 验证码校验
     * @param phoneNumber
     * @param smsCode
     * @return
     */
    Boolean checkValidateCode(String phoneNumber, String smsCode);
}
