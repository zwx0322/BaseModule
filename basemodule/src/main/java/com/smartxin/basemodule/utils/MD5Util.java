package com.smartxin.basemodule.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <md5加密> <功能详细描述>
 *
 * @author : zhuwenyang
 * @version [版本号]
 * @date : 2020-03-20 11:31
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class MD5Util {

    public static String MD5(String sourceStr) {
        if (TextUtils.isEmpty(sourceStr)) {
            return "";
        }
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            //System.out.println(e);
        }
        return result;
    }

}
