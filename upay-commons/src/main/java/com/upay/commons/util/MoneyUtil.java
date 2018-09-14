package com.upay.commons.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 金额处理
 * 
 * @author freeplato
 * 
 */
public class MoneyUtil {

	/** 四舍五入 */
	public static final RoundingMode HALF_UP = RoundingMode.HALF_UP;
	/** 向上取整 */
	public static final RoundingMode UP = RoundingMode.UP;
	/**金额格式化*/
	public static final DecimalFormat df = new DecimalFormat("#.00"); 
	

	/**
	 * 以元为单位的金额转换成分
	 * 
	 * @param yuan
	 * @param decimal
	 * @return
	 */
	public static BigDecimal transferY2F(BigDecimal yuan, int decimal) {

		StringBuffer transAmt = new StringBuffer("1");
		while (decimal > 0) {
			transAmt.append("0");
			decimal--;
		}

		if (yuan != null) {
			return multiply(yuan, new BigDecimal(transAmt.toString()),
					RoundingMode.DOWN).setScale(0);
		}

		return BigDecimal.ZERO;
	}

	/**
	 * 以分为单位的金额转换成以元为单位
	 * 
	 * @param fen
	 * @return
	 */
	public static BigDecimal transferF2Y(BigDecimal fen, int decimal) {

		StringBuffer transAmt = new StringBuffer("1");
		while (decimal > 0) {
			transAmt.append("0");
			decimal--;
		}

		if (fen != null) {
			return divide(fen, new BigDecimal(transAmt.toString()),
					RoundingMode.DOWN);
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
	public static BigDecimal multiply(BigDecimal first, BigDecimal second,
			RoundingMode roundingMode) {
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
	public static BigDecimal divide(BigDecimal first, BigDecimal second,
			RoundingMode roundingMode) {
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
	public static BigDecimal subtract(BigDecimal first, BigDecimal second,
			RoundingMode roundingMode) {
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
	public static BigDecimal add(BigDecimal first, BigDecimal second,
			RoundingMode roundingMode) {
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
				MoneyUtil.multiply(maxTransferAmt,
						new BigDecimal(new Random().nextInt(100)), null),
				new BigDecimal(100), null);
	}

	/**
	 * 获取英式金额显示结果
	 * 
	 * @param decimal
	 * @return
	 */
	public static String EnMoneyFormat(BigDecimal decimal) {
		String str = decimal.toString();
		String prefix = str;
		if (str.contains(".")) {
			prefix = str.substring(0, str.indexOf("."));
		}
		StringBuffer sb = new StringBuffer();
		int j = 1;
		for (int i = prefix.length() - 1; i >= 0; i--) {
			sb.insert(0, String.valueOf(prefix.charAt(i)));
			if (j % 3 == 0 && j != prefix.length()) {
				sb.insert(0, ",");
			}
			j++;
		}
		if (str.contains(".")) {
			sb.append(str.subSequence(str.indexOf("."), str.length()));
		}

		return sb.toString();
	}

	// 金额转换 分->元
	public static String getAmt(Object o) {
		String temps = null;
		if (o instanceof String) {
			o = (new BigDecimal((String) o).divide(new BigDecimal("100")))
					.toString();
		} else if (o instanceof BigDecimal) {
			o = ((BigDecimal) o).divide(new BigDecimal("100")).toString();
		}
		if (o.toString().indexOf(".") > 0) {
			o = o.toString().substring(0, o.toString().indexOf("."));
		}

		temps = (String) o;
		return temps;
	}
	

	public static BigDecimal moneyFormat(BigDecimal money){
		BigDecimal rtnMoney=new BigDecimal("0.00");
		if(null!=money){
			rtnMoney=new BigDecimal(df.format(money.doubleValue()));
		}
		return rtnMoney;
	}
	
	// 金额转换 元->分
		public static String getAmtFen(Object o) {
			String temps = null;
			if (o instanceof String) {
				o = (new BigDecimal((String) o).multiply(new BigDecimal("100")))
						.toString();
			} else if (o instanceof BigDecimal) {
				o = ((BigDecimal) o).multiply(new BigDecimal("100")).toString();
			}
			temps = (String) o;
			return temps;
		}
		
		
		 /** 大写数字 */
		  private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" }; 
		  /** 整数部分的单位 */
		  private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", 
		      "仟" }; 
		  /** 小数部分的单位 */
		  private static final String[] DUNIT = { "角", "分", "厘" }; 
		  
		  /** 
		   * 得到大写金额。 
		   */
		  public static String toChinese(String str) { 
		    str = str.replaceAll(",", "");// 去掉"," 
		    String integerStr;// 整数部分数字 
		    String decimalStr;// 小数部分数字 
		  
		    // 初始化：分离整数部分和小数部分 
		    if (str.indexOf(".") > 0) { 
		      integerStr = str.substring(0, str.indexOf(".")); 
		      decimalStr = str.substring(str.indexOf(".") + 1); 
		    } else if (str.indexOf(".") == 0) { 
		      integerStr = ""; 
		      decimalStr = str.substring(1); 
		    } else { 
		      integerStr = str; 
		      decimalStr = ""; 
		    } 
		    // integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去) 
		    if (!integerStr.equals("")) { 
		      integerStr = Long.toString(Long.parseLong(integerStr)); 
		      if (integerStr.equals("0")) { 
		        integerStr = ""; 
		      } 
		    } 
		    // overflow超出处理能力，直接返回 
		    if (integerStr.length() > IUNIT.length) { 
		      System.out.println(str + ":超出处理能力"); 
		      return str; 
		    } 
		  
		    int[] integers = toArray(integerStr);// 整数部分数字 
		    boolean isMust5 = isMust5(integerStr);// 设置万单位 
		    int[] decimals = toArray(decimalStr);// 小数部分数字 
		    return getChineseInteger(integers, isMust5) + getChineseDecimal(decimals); 
		  } 
		  
		  /** 
		   * 整数部分和小数部分转换为数组，从高位至低位 
		   */
		  private static int[] toArray(String number) { 
		    int[] array = new int[number.length()]; 
		    for (int i = 0; i < number.length(); i++) { 
		      array[i] = Integer.parseInt(number.substring(i, i + 1)); 
		    } 
		    return array; 
		  } 
		  
		  /** 
		   * 得到中文金额的整数部分。 
		   */
		  private static String getChineseInteger(int[] integers, boolean isMust5) { 
		    StringBuffer chineseInteger = new StringBuffer(""); 
		    int length = integers.length; 
		    for (int i = 0; i < length; i++) { 
		      // 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元) 
		      // 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元) 
		      String key = ""; 
		      if (integers[i] == 0) { 
		        if ((length - i) == 13)// 万(亿)(必填) 
		          key = IUNIT[4]; 
		        else if ((length - i) == 9)// 亿(必填) 
		          key = IUNIT[8]; 
		        else if ((length - i) == 5 && isMust5)// 万(不必填) 
		          key = IUNIT[4]; 
		        else if ((length - i) == 1)// 元(必填) 
		          key = IUNIT[0]; 
		        // 0遇非0时补零，不包含最后一位 
		        if ((length - i) > 1 && integers[i + 1] != 0) 
		          key += NUMBERS[0]; 
		      } 
		      chineseInteger.append(integers[i] == 0 ? key : (NUMBERS[integers[i]] + IUNIT[length - i - 1])); 
		    } 
		    return chineseInteger.toString(); 
		  } 
		  
		  /** 
		   * 得到中文金额的小数部分。 
		   */
		  private static String getChineseDecimal(int[] decimals) { 
		    StringBuffer chineseDecimal = new StringBuffer(""); 
		    for (int i = 0; i < decimals.length; i++) { 
		      // 舍去3位小数之后的 
		      if (i == 3) 
		        break; 
		      chineseDecimal.append(decimals[i] == 0 ? "" : (NUMBERS[decimals[i]] + DUNIT[i])); 
		    } 
		    return chineseDecimal.toString(); 
		  } 
		  
		  /** 
		   * 判断第5位数字的单位"万"是否应加。 
		   */
		  private static boolean isMust5(String integerStr) { 
		    int length = integerStr.length(); 
		    if (length > 4) { 
		      String subInteger = ""; 
		      if (length > 8) { 
		        // 取得从低位数，第5到第8位的字串 
		        subInteger = integerStr.substring(length - 8, length - 4); 
		      } else { 
		        subInteger = integerStr.substring(0, length - 4); 
		      } 
		      return Integer.parseInt(subInteger) > 0; 
		    } else { 
		      return false; 
		    } 
		  } 

	public static void main(String[] args) {
 
		
		//String s = getAmt("0.1");
		//System.out.println(s);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//
		// Date date = new Date();
		// System.out.println(sdf.format(date));
		//
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(date);
		//
		// calendar.add(Calendar.MONTH, 1);
		// System.out.println(sdf.format(calendar.getTime()));

		// System.out.println(EnMoneyFormat(new BigDecimal("1000.00")));
		
		
//		//System.out.println(MoneyUtil.moneyFormat(new BigDecimal(0)));
//		String number = "65.00"; 
//	    System.out.println(number + " " + MoneyUtil.toChinese(number)); 
//	    number = "1234567890123456.123"; 
//	    System.out.println(number + " " + MoneyUtil.toChinese(number)); 
//	    number = "0.0798"; 
//	    System.out.println(number + " " + MoneyUtil.toChinese(number)); 
//	    number = "10,001,000.09"; 
//	    System.out.println(number + " " + MoneyUtil.toChinese(number)); 
//	    number = "01.107700"; 
//	    System.out.println(number + " " + MoneyUtil.toChinese(number)); 
		
		
		System.out.println(toChar("12346578"));
	}
	
	
	
	
	 /***
     * 切割字符串
     * 
     * @param sb
     */
    public static void m0(String sb, int type, StringBuffer sbf) {

        int len = sb.length();
        int b = 0;
        type++;
        if (len >= 4) {
            b = len - 4;
            sbf.insert(0, m1(sb.substring(b), type));
            m0(sb.substring(0, b), type, sbf);
        } else if (len > 0)
            sbf.insert(0, m1(sb, type));
        if ('零' == sbf.charAt(0))
            sbf.deleteCharAt(0);
    }

    /***
     * 
     * 
     * @param sb
     *            大写金额
     */
    public static StringBuffer m1(String sb, int type) {

        StringBuffer sbf = new StringBuffer(sb);
        switch (type) {
        case 1:
            sbf.append("元");
            break;
        case 2:
            sbf.append("万");
            break;
        case 3:
            sbf.append("亿");
            break;
        default:
            break;
        }
        // 开始赋值
        int b = 0;
        char t = 0;
        for (int i = sbf.length() - 2; i >= 0; i--) {
            t = sbf.charAt(i);
            sbf.setCharAt(i, map.get(t));
            if (i != 0)
                sbf.insert(i, mode[b]);
            b++;
        }
        for (int i = 0; i < sbf.length(); i++) {
            t = sbf.charAt(i);
            if (t == '零') {
                t = sbf.charAt(i + 1);
                if ('元' != t && '万' != t && '亿' != t)
                    sbf.deleteCharAt(i + 1);
                else
                    sbf.deleteCharAt(i);
                if (i != 0)
                    if (sbf.charAt(i - 1) == '零') {
                        sbf.deleteCharAt(i - 1);
                        i--;
                    }
            }
        }
        if (sbf.length() == 1) {
            if ('元' != sbf.charAt(0))
                sbf.setLength(0);
        }
        return sbf;
    }

    public static StringBuffer m2(String de) {

        if (de.length() > 2)
            de = de.substring(0, 2);
        de = de.replaceFirst("00", "");
        StringBuffer sb = new StringBuffer(de);
        if (sb.length() > 0) {
            if (sb.charAt(sb.length() - 1) == '0')
                sb.deleteCharAt(sb.length() - 1);
            sb.setCharAt(0, map.get(sb.charAt(0)));
            switch (sb.length()) {
            case 1:
                sb.append("角");
                break;
            case 2:
                sb.setCharAt(1, map.get(sb.charAt(1)));
                if (sb.charAt(0) != '零')
                    sb.insert(1, '角');
                sb.append("分");
                break;
            default:
                break;
            }
        }
        return sb;
    }

    /**
     * 将字符串表示的金额转换成大写金额
     * 
     * @param in
     *            输入金额 字符串形式 如 12345.67
     * @return 大写金额 如 壹佰贰拾叁万肆仟伍佰陆拾陆元贰角贰分 整
     */
    public static String toChar(String in) {
    	String fistchar=in.substring(0,1);
    	if(fistchar.equals("0")){
    		return toChinese(in);
    	}
    	boolean zhengflag = false;
    	if(in.indexOf(".")<0){
    		zhengflag=true;
    	}

        StringBuffer sbf = new StringBuffer();
        String[] sp = in.split("\\.");
        if (sp.length == 2) {
            m0(sp[0], 0, sbf);
            sbf.append(m2(sp[1]));
        } else
            m0(in, 0, sbf);
        if(zhengflag){
            sbf.append("整");
        }
        return sbf.toString();
    }

    // 缓存所有数字的
    private static Map<Character, Character> map = new HashMap<Character, Character>(10);

    static {
        map.put('1', '壹');
        map.put('2', '贰');
        map.put('3', '叁');
        map.put('4', '肆');
        map.put('5', '伍');
        map.put('6', '陆');
        map.put('7', '柒');
        map.put('8', '捌');
        map.put('9', '玖');
        map.put('0', '零');
    }

    static char[] mode = new char[] { '拾', '佰', '仟' };
    
    
    
}
