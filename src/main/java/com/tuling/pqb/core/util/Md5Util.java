package com.tuling.pqb.core.util;

import cn.stylefeng.roses.core.util.ToolUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhangzhiyuan
 */
public class Md5Util {
    public static String cryptMD5(String strSrc) {
        if (ToolUtil.isEmpty(strSrc)) {
            throw new IllegalArgumentException("param strSrc cannot be null or zero length");
        } else {
            StringBuilder hexString = new StringBuilder();

            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(strSrc.getBytes());
                byte[] hash = md.digest();
                byte[] var4 = hash;
                int var5 = hash.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    byte b = var4[var6];
                    if ((255 & b) < 16) {
                        hexString.append("0");
                        hexString.append(Integer.toHexString(255 & b));
                    } else {
                        hexString.append(Integer.toHexString(255 & b));
                    }
                }
            } catch (NoSuchAlgorithmException var8) {
            }

            return hexString.toString();
        }
    }
}
