package com.upay.commons.util;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.bean.Fault;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.DataBaseConstants_PAY;

import java.util.Map;

public class UnionUtils {

    //银联响应码
    public static final String[] RESP_CODE = {"00", "A6", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99"};

    public static void setRespCode(Map<String, Object> target, Fault fault) {
        String respCode = null;
        String origRespCode = null;
        String origRespMsg = null;
        if (target.containsKey("respCode")) {
            respCode = (String) target.get("respCode");
        }
        if (target.containsKey("origRespCode")) {
            origRespCode = (String) target.get("origRespCode");
        }
        if (target.containsKey("origRespMsg")) {
            origRespMsg = (String) target.get("origRespMsg");
        }

        //1.查询交易受理成功  由 origRespCode 判断流水和订单状态  错误信息由 origRespCode返回
        if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(respCode) ||
                DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(respCode)) {
            if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(origRespCode) ||
                    DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(origRespCode)) {
            }else{
                for (String code : RESP_CODE) {
                    if (code.equals(origRespCode)) {
                        String tempCode = "UNIONPAY00" + code;
                        fault.setCode(tempCode);
                        fault.setMsg(origRespMsg);
                        fault.setOutCode(tempCode);
                        fault.setOutMsg(origRespMsg);
                        fault.setCodeAll(tempCode);
                        fault.setMsgAll(origRespMsg);
                        ExInfo.throwDipperEx(tempCode);
                    }


                }

            }




        }
        //2.未受理成功   由 respCode 决定 响应码  判断  错误信息由 respCode返回
        else {
            if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(respCode) ||
                    DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(respCode)) {
            }else{
                for (String code : RESP_CODE) {
                        if (code.equals(respCode)) {
                            String tempCode = "UNIONPAY00" + code;
                            fault.setCode(tempCode);
                            fault.setOutCode(tempCode);
                            fault.setCodeAll(tempCode);
                            ExInfo.throwDipperEx(tempCode);
                        }
                    }
            }

        }


    }


}
