package com.tuling.pqb.core.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

public class WxUtils {

    //加密方式
    private static String keyAlgorithm = "AES";
    //避免重复new生成多个BouncyCastleProvider对象，因为GC回收不了，会造成内存溢出
    //只在第一次调用decrypt()方法时才new 对象
    private static boolean initialized = false;
    //用于Base64解密
    private static Base64.Decoder decoder = Base64.getDecoder();


    /**
     *    * 微信 数据解密<br/>
     *    * 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充<br/>
     *    * 对称解密的目标密文:encrypted=Base64_Decode(encryptData)<br/>
     *    * 对称解密秘钥:key = Base64_Decode(session_key),aeskey是16字节<br/>
     *    * 对称解密算法初始向量:iv = Base64_Decode(iv),同样是16字节<br/>
     *    *
     *    * @param encrypted 目标密文
     *    * @param session_key 会话ID
     *    * @param iv 加密算法的初始向量
     *
     */
    public static String decrypt(String encrypted, String session_key, String iv) {
        initialize();
        try {
            //数据填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key sKeySpec = new SecretKeySpec(decoder.decode(session_key), keyAlgorithm);
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(decoder.decode(iv)));
            byte[]data = cipher.doFinal(decoder.decode(encrypted));
            String datastr = new String(data, StandardCharsets.UTF_8);
            return datastr;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**BouncyCastle作为安全提供，防止我们加密解密时候因为jdk内置的不支持改模式运行报错。**/
    private static void initialize() {
        if (initialized) {
            return;
        }
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    // 生成iv
    private static AlgorithmParameters generateIV(byte[] iv) throws NoSuchAlgorithmException, InvalidParameterSpecException {
        AlgorithmParameters params = AlgorithmParameters.getInstance(keyAlgorithm);
        params.init(new IvParameterSpec(iv));
        return params;
    }
}
