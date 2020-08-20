package com.tuling.pqb.core.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: party-question-bank
 * @description: 阿里云短信
 * @author: gaohan
 * @create: 2020-07-14 22:56
 */
@Slf4j
@Component
public class AliyunSmsUtils {

    /**
     * 阿里云 accessKeyId（安全信息管理下面）
     */
    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    /**
     * 阿里云 accessKeySecret（安全信息管理下面）
     */
    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;


    /**
     * 阿里云 templateCode
     */
    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    /**
     * 阿里云 signName
     */
    @Value("${aliyun.sms.signName}")
    private String signName;

    /**
     * 短信API产品名称（短信产品名固定，无需修改）
     */
    private static final String product = "Dysmsapi";

    /**
     * 短信API产品域名，接口地址固定，无需修改
     */
    private static final String domain = "dysmsapi.aliyuncs.com";


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public boolean sendSms(String phone, String code, String mobileKey, long seconds) {

        /* 超时时间，可自主调整 */
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        /* 初始化acsClient,暂不支持region化 */
        IClientProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        /* 组装请求对象-具体描述见控制台-文档部分内容 */
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
		/* 必填:待发送手机号 */
        request.putQueryParameter("PhoneNumbers", phone);
        /* 必填:短信签名-可在短信控制台中找到 */
        request.putQueryParameter("SignName", signName);
        /* 必填:短信模板code-可在短信控制台中找到 */
        request.putQueryParameter("TemplateCode", templateCode);
        /* 可选:模板中的变量替换JSON串,如模板内容为"亲爱的用户,您的验证码为${code}"时,此处的值为 */
        String templateParam = "{\"code\":\"" + code + "\"}";
        request.putQueryParameter("TemplateParam", templateParam);
        CommonResponse response = null;
        try {
            response = acsClient.getCommonResponse(request);
        }  catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if (null != response) {

            Map map = JSONObject.parseObject(response.getData(), Map.class);
            if (map.get("Code") != null && map.get("Code").equals("OK")) {
                //短信发送成功，验证码存入redis，有效期180s(3分钟)
                redisTemplate.opsForValue().set(mobileKey, code, seconds, TimeUnit.SECONDS);
                log.info("手机号: {}, 短信发送成功！验证码：{}", phone, code);
                return true;
            } else {
                log.info("短信发送失败！");
                return false;
            }
        }
        return false;
    }


}

