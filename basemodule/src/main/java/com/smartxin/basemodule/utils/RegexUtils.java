package com.smartxin.basemodule.utils;

import java.util.regex.Pattern;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/22 15:41
 * Description: 正则验证工具类
 */
public class RegexUtils {

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return whether input matches regex of simple mobile.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMobilePhone(final CharSequence input) {
        String phoneReg = "^1\\d{10}$";
        return  Pattern.matches(phoneReg,input);
    }

    /**
     * Return whether input matches regex of telephone.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isTelephone(final CharSequence input) {
        String telephoneReg = "^\\d{3,4}-\\d{7,8}(-\\d{3,4})?$\n";
        return  Pattern.matches(telephoneReg,input);
    }

    /**
     * Return whether input matches regex of url.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isUrl(final CharSequence input) {
        String urlReg = "^((https|http|ftp|rtsp|mms):\\/\\/)(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?(([0-9]{1,3}.){3}[0-9]{1,3}|([0-9a-zA-Z_!~*'()-]+.)*([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z].[a-zA-Z]{2,6})(:[0-9]{1,4})?((\\/?)|(\\/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+\\/?)$\n";
        return  Pattern.matches(urlReg,input);
    }

    /**
     * Return whether input matches regex of email.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmail(final CharSequence input) {
        String emailReg = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        return  Pattern.matches(emailReg,input);
    }

    /**
     * Return whether input matches regex of id card number which length is 18.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard(final CharSequence input) {
        String cardReg = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        return  Pattern.matches(cardReg,input);
    }

    /**
     * Return whether input matches regex of carBrand.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isCarBrand(final CharSequence input) {

        return isNormalCarBrand(input) || isNewEnergyCarBrand(input);
    }

    /**
     * Return whether input matches regex of carBrand.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNormalCarBrand(final CharSequence input) {
        //参数判断
        if (StringUtils.isEmpty(input))
        {
            return false;
        }
        //匹配民用车牌和使馆车牌
        //判断标准
        //1.第一位为汉子省份缩写
        //2.第二位为大写字母城市编码
        //3.后面是5位仅含字母和数字的组合
        String carBrand1 = "[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新使]{1}[A-Z]{1}[0-9a-zA-Z]{5}$";
        boolean p1 = Pattern.matches(carBrand1,input);

        //匹配特种车牌(挂,警,学,领,港,澳)
        String carBrand2 = "[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新]{1}[A-Z]{1}[0-9a-zA-Z]{4}[挂警学领港澳超]{1}$";
        boolean p2 = Pattern.matches(carBrand2,input);

        //匹配武警车牌
        String carBrand3 = "^WJ[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新]?[0-9a-zA-Z]{5}$";
        boolean p3 = Pattern.matches(carBrand3,input);

        //匹配军牌
        String carBrand4 = "[A-Z]{2}[0-9]{5}$";
        boolean p4 = Pattern.matches(carBrand4,input);

        return p1 || p2 || p3 || p4 ;
    }

    /**
     * Return whether input matches regex of carBrand.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNewEnergyCarBrand(final CharSequence input) {
        //参数判断
        if (StringUtils.isEmpty(input))
        {
            return false;
        }
        return  isLittleNewEnergyCarBrand(input) || isLargeNewEnergyCarBrand(input);
    }

    public static boolean isLittleNewEnergyCarBrand(final CharSequence input) {
        //参数判断
        if (StringUtils.isEmpty(input))
        {
            return false;
        }
        //匹配新能源车辆6位车牌
        //小型新能源车
        String carBrand5 = "[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新]{1}[A-Z]{1}[DF]{1}[0-9a-zA-Z]{5}$";
        return Pattern.matches(carBrand5,input);
    }

    public static boolean isLargeNewEnergyCarBrand(final CharSequence input) {
        //参数判断
        if (StringUtils.isEmpty(input))
        {
            return false;
        }

        //大型新能源车

        String carBrand6 = "[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新]{1}[A-Z]{1}[0-9a-zA-Z]{5}[DF]{1}$";
        return Pattern.matches(carBrand6,input);
    }

}
