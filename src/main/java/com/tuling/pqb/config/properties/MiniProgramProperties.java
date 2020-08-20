package com.tuling.pqb.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Configuration;

/**
 * @program: party-question-bank
 * @description: 小程序配置
 * @author: gaohan
 * @create: 2020-07-14 21:40
 */
@Configuration
@ConfigurationProperties(prefix = MiniProgramProperties.PREFIX)
public class MiniProgramProperties {
    public static final String PREFIX = "mini-program";

    private String appId;

    private String appSecret;

    private String jsCode2SessionUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getJsCode2SessionUrl() {
        return jsCode2SessionUrl;
    }

    public void setJsCode2SessionUrl(String jsCode2SessionUrl) {
        this.jsCode2SessionUrl = jsCode2SessionUrl;
    }
}
