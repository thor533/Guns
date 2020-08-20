package com.tuling.pqb.modular.bus.dto;

import lombok.Data;

/**
 * @program: party-question-bank
 * @description: busUserAndRelationDto
 * @author: gaohan
 * @create: 2020-07-14 23:25
 */
@Data
public class BusUserAndRelationDto {
    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 人员姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 第三方账户ID
     */
    private String openId;
    /**
     * 验证码
     */
    private String smsCode;

}
