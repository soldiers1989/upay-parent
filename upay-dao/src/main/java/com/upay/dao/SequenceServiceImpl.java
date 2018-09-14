package com.upay.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.pactera.dipper.dao.service.DipperSequenceService;
import com.upay.commons.util.DateUtil;


public class SequenceServiceImpl extends DipperSequenceService implements ISequenceService {

    private static final String PREFIX_USER_ID = "UR";
    private static final String PREFIX_SYS_SEQ = "YM"; // 系统流水号
    private static final String PREFIX_FEE_ID = "FEE"; // 费用方法ID
    private static final String ESB_ID="UPP00001";


    @Override
    public String generateUserId() {
        return PREFIX_USER_ID + leftPad('0', generateSequence("USER_ID"), 12);
    }


    @Override
    public String generateOperSeq() {
        return PREFIX_SYS_SEQ + DateFormatUtils.format(new Date(), "yyMMdd")
                + leftPad('0', generateSequence("OPER_SEQ"), 10);
    }


    @Override
    public String generateOrderNo(String modeType) {// 模块类型
        return modeType + DateFormatUtils.format(new Date(), "yyyyMMdd")
                + leftPad('0', generateSequence("ORDER_NO"), 10);
    }
    
    @Override
    public String generateNotifyId(String modeType) {// 模块类型
        return modeType + DateFormatUtils.format(new Date(), "yyyyMMdd")
                + leftPad('0', generateSequence("NOTIFY_ID"), 10);
    }


    @Override
    public String generateSmsNoSeq() {
        // TODO Auto-generated method stub
        return leftPad('0', generateSequence("SMS_NO"), 20);
    }


    @Override
    public String generateNoticeNoSeq() {
        return leftPad('0', generateSequence("NOTICE_NO"), 20);
    }


    @Override
    public String generateProductNoSeq() {
        return DateFormatUtils.format(SysInfoContext.getSysDate(), "yyyyMMdd")
                + leftPad('0', generateSequence("PRODUCT_NO"), 5);
    }


    @Override
    public String generateBakNo() {// 副本序号
        return leftPad('0', generateSequence("BAK_NO"), 5);
    }


    @Override
    public String generateAdvertiseNo() {// 副本序号
        return leftPad('0', generateSequence("ADVERTISE_NO"), 8);
    }


    @Override
    public String generateMerNoSeq() {
        return DateUtil.format(new Date(), "YYYY") + leftPad('0', generateSequence("MER_NO"), 6);
    }


    @Override
    public String generateStoreNoSeq() {
        return leftPad('0', generateSequence("STORE_NO"), 4);
    }


    @Override
    public String generatePayFlowSeq() {
        return DateFormatUtils.format(new Date(), "yyyyMMdd")
                + leftPad('0', generateSequence("PAY_FLOW_SEQ"), 12);
    }


    @Override
    public String generateLmtAmountSeq(String accType) {
        return "SYS" + accType + leftPad('0', generateSequence("LMT_AMOUNT_SEQ"), 10);
    }


    @Override
    public String generateeAcctNoSeq() {
        return leftPad('0', generateSequence("V_ACCT_NO"), 10);
    }


    @Override
    public String vilidatorAcctNo(String key) {
        return getLuhnCheckNumber(key);
    }


    @Override
    public String authApplyNo() {
        return "MB" + DateFormatUtils.format(new Date(), "yyyyMMdd")
                + leftPad('0', generateSequence("AUTH_APPLY_NO"), 6);
    }


    /**
     * 返回卡尾号信息
     * 
     * @param number
     * @return
     */

    public String getSubCardNo(String cardNo, int number) {
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


    @Override
    public String generateMerApplyNo() {
        return DateUtil.format(new Date(), "yyyyMMdd") + leftPad('0', generateSequence("MER_APPLY_NO"), 5);
    }


    @Override
    public String generateZjpaySysSeq(Date today) {
        return DateUtil.format(today, "yyyyMMddHHmmss") + leftPad("0", generateSeq("FB_BANK_ZJPAY_SEQ"), 6);
    }


    @Override
    public String generateSeq(String key) {
        return String.valueOf(generateSequence(key));
    }


    private static String leftPad(String str, String numStr, int len) {
        // numStr已超出长度如何处理？
        String formater = "%".concat(str).concat(len + "").concat("d");
        return String.format(formater, Long.valueOf(numStr));
    }


    @Override
    public String generateFeeID() {
        // TODO Auto-generated method stub
        return PREFIX_FEE_ID + leftPad('0', generateSequence("FEE_ID"), 5);
    }


    @Override
    public String generateFeeCode() {
        return leftPad('0', generateSequence("FEE_CODE"), 6);
    }
    
    @Override
    public String generateStpNo() {
        return DateUtil.format(new Date(), "yyyyMMdd") + leftPad('0', generateSequence("STP_NO"), 10);
    }


	@Override
	public String generateEsbNo() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
        Date date = new Date();
		return ESB_ID + dateFormat.format(date)+ leftPad('0', generateSequence("ESB_NO"), 9);
	}
	

}
