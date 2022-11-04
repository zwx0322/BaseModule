package com.smartxin.baselibrary.utils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES加解密
 *
 * @author zhuwenyang
 * @version [版本号, 2020年4月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AESSecretUtil {

    private static String encryption = "AES";

    private static String scheme = "CBC";

    private static String complementWay = "PKCS5Padding";

    private static String pwdKey = "1raa70xiea6r1qm0";

    private static String pyl = "83h8ew1kx0gcsn4x";

    public static String encrypt(String content, String pwdKey, String iv) {
        try {
            byte[] raw = pwdKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, encryption);
            Cipher cipher = Cipher.getInstance(encryption + "/" + scheme + "/" + complementWay);
            IvParameterSpec iv2 = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv2);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64Utils.encode(encrypted);
        } catch (Exception ex) {
            //iLogger.error("aes加密失败：" + content);
            return null;
        }
    }

    public static String decrypt(String content) {
        return decrypt(content, pwdKey, pyl);
    }

    public static String encrypt(String content) {
        return encrypt(content, pwdKey, pyl);
    }

    private static String decrypt(String content, String pwdKey, String iv) {
        try {
            byte[] raw = pwdKey.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, encryption);
            Cipher cipher = Cipher.getInstance(encryption + "/" + scheme + "/" + complementWay);
            IvParameterSpec iv2 = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv2);
            byte[] encrypted1 = Base64Utils.decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            //iLogger.error("aes解密失败：" + content);
            return null;
        }
    }

    /**
     * 生成16位不重复的随机数，含数字+大小写
     *
     * @return
     */
    public static String getGUID(int length) {
        StringBuilder uid = new StringBuilder();
        // 产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < length; i++) {
            // 产生0-2的3位随机数
            int type = rd.nextInt(2);
            switch (type) {
                case 0:
                    // 0-9的随机数
                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    // ASCII在65-90之间为大写,获取大写随机
                    uid.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    // ASCII在97-122之间为小写，获取小写随机
                    uid.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;
            }
        }
        return uid.toString();
    }
}
