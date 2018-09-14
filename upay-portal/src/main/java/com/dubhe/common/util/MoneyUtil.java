package com.dubhe.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * 金额处理
 * @author freeplato
 *
 */
public class MoneyUtil {

    /** 四舍五入 */
    public static final RoundingMode HALF_UP = RoundingMode.HALF_UP;
    /** 向上取整 */
    public static final RoundingMode UP = RoundingMode.UP;


    /**
     * 以元为单位的金额转换成分
     * @param yuan
     * @param decimal
     * @return
     */
    public static BigDecimal transferY2F(BigDecimal yuan,int decimal) {
    	
    	StringBuffer transAmt = new StringBuffer("1");
    	while(decimal >0){
    		transAmt.append("0");
    		decimal--;
    	}
    	
        if (yuan != null) {
            return multiply(yuan, new BigDecimal(transAmt.toString()), RoundingMode.DOWN).setScale(0);
        }

        return BigDecimal.ZERO;
    }


    /**
     * 以分为单位的金额转换成以元为单位
     * 
     * @param fen
     * @return
     */
    public static BigDecimal transferF2Y(BigDecimal fen,int decimal) {
    	
    	StringBuffer transAmt = new StringBuffer("1");
    	while(decimal >0){
    		transAmt.append("0");
    		decimal--;
    	}
    	
        if (fen != null) {
            return divide(fen, new BigDecimal(transAmt.toString()), RoundingMode.DOWN);
        }

        return BigDecimal.ZERO;
    }


    /**
     * 金额乘法
     * 
     * @param first
     * @param second
     * @param roundingMode
     * @return
     */
    public static BigDecimal multiply(BigDecimal first, BigDecimal second, RoundingMode roundingMode) {
        if (roundingMode == null) {
            roundingMode = MoneyUtil.HALF_UP;
        }
        if (first == null)
            first = BigDecimal.ZERO;
        if (second == null)
            second = BigDecimal.ZERO;
        return (first.multiply(second)).setScale(2, roundingMode);
    }


    /**
     * 金额除法
     * 
     * @param first
     * @param second
     * @param roundingMode
     * @return
     */
    public static BigDecimal divide(BigDecimal first, BigDecimal second, RoundingMode roundingMode) {
        if (roundingMode == null) {
            roundingMode = MoneyUtil.HALF_UP;
        }
        if (first == null)
            first = BigDecimal.ZERO;
        if (second == null)
            second = BigDecimal.ZERO;
        return first.divide(second, 2, roundingMode);
    }


    /**
     * 金额减法
     * 
     * @param first
     * @param second
     * @param roundingMode
     * @return
     */
    public static BigDecimal subtract(BigDecimal first, BigDecimal second, RoundingMode roundingMode) {
        if (roundingMode == null) {
            roundingMode = MoneyUtil.HALF_UP;
        }
        if (first == null)
            first = BigDecimal.ZERO;
        if (second == null)
            second = BigDecimal.ZERO;
        return (first.subtract(second)).setScale(2, roundingMode);
    }


    /**
     * 金额加法
     * 
     * @param first
     * @param second
     * @param roundingMode
     * @return
     */
    public static BigDecimal add(BigDecimal first, BigDecimal second, RoundingMode roundingMode) {
        if (roundingMode == null) {
            roundingMode = MoneyUtil.HALF_UP;
        }
        if (first == null)
            first = BigDecimal.ZERO;
        if (second == null)
            second = BigDecimal.ZERO;
        return (first.add(second)).setScale(2, roundingMode);
    }


    /**
     * 获取随机的0.01元至配置最高金额的之间的金额
     * 
     * @return
     */
    public static BigDecimal randomTransferAmt(BigDecimal maxTransferAmt) {
        return MoneyUtil.divide(
            MoneyUtil.multiply(maxTransferAmt, new BigDecimal(new Random().nextInt(100)), null),
            new BigDecimal(100), null);
    }

    /**
     * 获取英式金额显示结果
     * @param decimal
     * @return
     */
    public static String EnMoneyFormat(BigDecimal decimal){
		String str = decimal.toString();
		String prefix = str;
		if(str.contains(".")){
			prefix = str.substring(0,str.indexOf("."));
		}
		StringBuffer sb = new StringBuffer();
		int j = 1;
		for(int i = prefix.length() - 1; i >= 0; i--){
			sb.insert(0,String.valueOf(prefix.charAt(i)));
			if(j%3==0 && j != prefix.length()){
				sb.insert(0,",");
			}
			j++;
		}
		if(str.contains(".")){
			sb.append(str.subSequence(str.indexOf("."),str.length()));
		}
		
		return sb.toString();
    }

    public static void main(String[] args) {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//
//        Date date = new Date();
//        System.out.println(sdf.format(date));
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//
//        calendar.add(Calendar.MONTH, 1);
//        System.out.println(sdf.format(calendar.getTime()));
    	
//    	System.out.println(EnMoneyFormat(new BigDecimal("1000.00")));

    }
}
