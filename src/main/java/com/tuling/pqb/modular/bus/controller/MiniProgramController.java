package com.tuling.pqb.modular.bus.controller;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuling.pqb.config.properties.MiniProgramProperties;
import com.tuling.pqb.core.util.WxUtils;
import com.tuling.pqb.core.util.HttpUtils;
import com.tuling.pqb.modular.bus.entity.BusUser;
import com.tuling.pqb.modular.bus.service.BusUserService;
import com.tuling.pqb.modular.bus.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: party-question-bank
 * @description: 小程序操作类
 * @author: gaohan
 * @create: 2020-07-14 21:48
 */
@RequestMapping("mini")
@RestController
@Slf4j
public class MiniProgramController {

    private final static String ERR_CODE_KEY = "errcode";

    private MiniProgramProperties properties;
    private SmsService smsService;
    @Autowired
    private BusUserService busUserService;

    /**
     * code换取openid
     *
     * @param code
     * @return
     */
    @RequestMapping("code2Session")
    public ResponseData code2Session(String code) {
        String url = properties.getJsCode2SessionUrl();
        url = url.replace("APPID", properties.getAppId())
                .replace("SECRET", properties.getAppSecret())
                .replace("JSCODE", code);
        String resp = null;
        try {
            resp = HttpUtils.sendGetNoSSL(url);
        } catch (Exception e) {
            log.error("openId获取失败", e);
            return ResponseData.error("openId获取失败");
        }
        System.out.println(resp);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        if (jsonObject.containsKey(ERR_CODE_KEY)) {
            return ResponseData.error("openId获取失败");
        }
        return ResponseData.success(jsonObject);
    }

    @RequestMapping("sendSms")
    public ResponseData sendSmsCode(String phoneNumber) {
        return smsService.sendValidateCode(phoneNumber);
    }

    @RequestMapping("sendRegSms")
    public ResponseData sendRegSmsCode(String phoneNumber) {
        BusUser busUser = busUserService.getByPhoneNumber(phoneNumber);
        if (ToolUtil.isNotEmpty(busUser)){
            return ResponseData.error("该手机号已进行注册");
        }
        return smsService.sendValidateCode(phoneNumber);
    }

     /**
     * 手机号一键登录
     *
     * @param iv
     * @return
     */
    @RequestMapping("oneClickLogin")
    public ResponseData oneClickLogin(String sessionKey, String encryptedData, String iv) {
        String decrypt = WxUtils.decrypt(encryptedData, sessionKey, iv);
        JSONObject jsonObject = JSON.parseObject(decrypt);
        String phoneNumber = jsonObject.getString("phoneNumber");
        BusUser busUser = busUserService.getByPhoneNumber(phoneNumber);
        if (ToolUtil.isEmpty(busUser)) {
            busUser = new BusUser();
            busUser.setPhoneNumber(phoneNumber);
        }
        return ResponseData.success(busUser);
    }


    @Resource
    public void setProperties(MiniProgramProperties properties) {
        this.properties = properties;
    }

    @Resource
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
}
