package com.upay.commons.util;

/**
 * 卡号相关方法类
 * 
 * @author: LL
 * @CreateDate:2015年1月13日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2015年1月13日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class CardUtils {

    /**
     * 获取申请截止卡序号:获取卡序号（卡号长度 - 卡BIN长度 - 卡号段1长度 - 卡号段2长度 - 卡号段3长度 - 1）
     * 
     * @author huaqiao
     * @param cardSeq
     * @param applyNum
     * @return
     */
    public static String getSeqNo(Long cardSeq, int i) {
        StringBuffer str = new StringBuffer();
        String cardSeqNo = String.valueOf(cardSeq);
        while (i - cardSeqNo.length() != 0) {
            str.append("0");
            i--;
        }
        str.append(cardSeq);
        return str.toString();
    }


    /**
     * 卡号生成规则为：卡BIN（6位） + 卡号段1（位数不定） + 卡号段2（位数不定） ＋ 卡号段3（位数不定） + 卡序号（卡号长度 -
     * 卡BIN长度 - 卡号段1长度 - 卡号段2长度 - 卡号段3长度 - 1） +　校验位（1）
     * 
     * @author huaqiao
     * @param cardBin
     * @param sectionOne
     * @param sectionTwo
     * @param sectionThree
     * @return
     */
    public static String getCardNo(String cardBin, String sectionOne, String sectionTwo, String sectionThree,
            Long cardSeq) {
        StringBuffer cardNo = new StringBuffer(cardBin);
        if (null != sectionOne)
            cardNo.append(sectionOne.trim());
        if (null != sectionTwo)
            cardNo.append(sectionTwo.trim());
        if (null != sectionThree)
            cardNo.append(sectionThree.trim());
        cardNo.append(getSeqNo(cardSeq, (19 - cardNo.length() - 1)));
        String checkNumber = "" + getCheckNumber(cardNo.toString());
        cardNo.append(checkNumber.trim());
        return cardNo.toString();
    }


    /**
     * Luhn算法 根据卡号获取校验位
     * 
     * @param cardNumber
     * @return
     */
    public static int getCheckNumber(String cardNumber) {
        int totalNumber = 0;
        for (int i = cardNumber.length() - 1; i >= 0; i -= 2) {
            int tmpNumber = calculate(Integer.parseInt(String.valueOf(cardNumber.charAt(i))) * 2);
            if (i == 0) {
                totalNumber += tmpNumber;
            } else {
                totalNumber += tmpNumber + Integer.parseInt(String.valueOf(cardNumber.charAt(i - 1)));
            }
        }
        if (totalNumber >= 0 && totalNumber < 9) {
            return (10 - totalNumber);
        } else {
            String str = String.valueOf(totalNumber);
            if (Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))) == 0) {
                return 0;
            } else {
                return (10 - Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))));
            }
        }

    }


    public static void main(String[] args) {
        System.out.println(getCheckNumber("7718008000101200407010000000001"));
        // System.out.println(getCheckNumber("7710410999901200407010000010015"));
        // System.out.println(vilidatorCardNo("77180080001012004070100000000013"));
    }


    /**
     * 计算数字各位和
     * 
     * @param number
     * @return
     */
    @Deprecated
    public static int calculate(int number) {
        String str = String.valueOf(number);
        int total = 0;
        for (int i = 0; i < str.length(); i++) {
            total += Integer.valueOf(Integer.parseInt(String.valueOf(str.charAt(i))));
        }
        return total;
    }


    /**
     * 返回卡尾号信息
     * 
     * @param number
     * @return
     */

    public static String getSubCardNo(String cardNo, int number) {
        String str = cardNo.substring(cardNo.length() - number, cardNo.length());

        return str;
    }


    /**
     * Luhn算法 根据卡号获取校验位 校验位的生成采用Luhn算法(Luhn algorithm)，也称为“模10”算法。 以智能存款账户771
     * 80080001 01 20040701 0000000001 为例共31位 从左往右数偶数位乘以2： 7 14 1 16 0 0 8 0 0 0
     * 1 0 1 4 0 0 4 0 7 0 1 0 0 0 0 0 0 0 0 0 1 将偶数位的十位与个位相加： 7 5 1 7 0 0 8 0 0
     * 0 1 0 1 4 0 0 4 0 7 0 1 0 0 0 0 0 0 0 0 0 1 将每位上的数字相加：
     * 7+5+1+7+0+0+8+0+0+0+1+0+1+4+0+0+4+0+7+0+1+0+0+0+0+0+0+0+0+0+1=47
     * 将数字之和取模10得到7，再用10减7得到3即为校验位 因此最后的账号组成为：771 80080001 01 20040701
     * 0000000001 3 如果得到的模10等于0的话，那么校验位就是10；
     * 
     * @param strCardNumber
     * @return
     */
    public static String getLuhnCheckNumber(String strCardNumber) {
        String cardNumber = strCardNumber.replaceAll(" ", "");
        int totalNumber = 0;
        for (int i = 1; i < cardNumber.length(); i += 2) {
            int tmpNumber = calculate(Integer.parseInt(String.valueOf(cardNumber.charAt(i))) * 2);
            totalNumber += tmpNumber + Integer.parseInt(String.valueOf(cardNumber.charAt(i - 1)));
            if (i == cardNumber.length() - 2) {
                totalNumber += Integer.parseInt(String.valueOf(cardNumber.charAt(cardNumber.length() - 1)));
            }
        }
        String vilidatorNumber = String.valueOf(10 - (totalNumber % 10));
        int j = String.valueOf(10 - (totalNumber % 10)).length();
        return vilidatorNumber.substring(j - 1, j);
    }

}
