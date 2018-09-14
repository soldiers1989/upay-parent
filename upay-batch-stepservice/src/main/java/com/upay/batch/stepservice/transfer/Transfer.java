package com.upay.batch.stepservice.transfer;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.upay.commons.csv.CSVReader;
import com.upay.commons.csv.CSVWriter;
import com.upay.commons.csv.bean.ColumnPositionMappingStrategy;
import com.upay.commons.csv.bean.CsvToBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class Transfer {

    public String merSeq;
    public String transFlag;
    public String firstMerNo;
    public String accountType;
    public String accountNo;
    public String bankNo;
    public String secondMerNo;
    public String merAcctNo;
    public String secMerAcctType;
    public String merAcctIssuer;
    public String merAcctBankNo;
    public String bankId;
    public String transAmt;
    public String remark;
    public String orderNo;
    public String orderStat;
    public String trfSeq;
    public String resultDesc;

   //四要素
    //证件类型
    public String certType;
    //证件号
    public String certNo;
    //手机号
        public String certMobile;
    //姓名
    public String certName;
    //渠道号
    public String chnlId;

    public String getChnlId() {
        return chnlId;
    }

    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getMerSeq() {
        return merSeq;
    }

    public void setMerSeq(String merSeq) {
        this.merSeq = merSeq;
    }

    public String getTransFlag() {
        return transFlag;
    }

    public void setTransFlag(String transFlag) {
        this.transFlag = transFlag;
    }

    public String getFirstMerNo() {
        return firstMerNo;
    }

    public void setFirstMerNo(String firstMerNo) {
        this.firstMerNo = firstMerNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getSecondMerNo() {
        return secondMerNo;
    }

    public void setSecondMerNo(String secondMerNo) {
        this.secondMerNo = secondMerNo;
    }

    public String getMerAcctNo() {
        return merAcctNo;
    }

    public void setMerAcctNo(String merAcctNo) {
        this.merAcctNo = merAcctNo;
    }

    public String getSecMerAcctType() {
        return secMerAcctType;
    }

    public void setSecMerAcctType(String secMerAcctType) {
        this.secMerAcctType = secMerAcctType;
    }

    public String getMerAcctIssuer() {
        return merAcctIssuer;
    }

    public void setMerAcctIssuer(String merAcctIssuer) {
        this.merAcctIssuer = merAcctIssuer;
    }

    public String getMerAcctBankNo() {
        return merAcctBankNo;
    }

    public void setMerAcctBankNo(String merAcctBankNo) {
        this.merAcctBankNo = merAcctBankNo;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    public String getTrfSeq() {
        return trfSeq;
    }

    public void setTrfSeq(String trfSeq) {
        this.trfSeq = trfSeq;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertMobile() {
        return certMobile;
    }

    public void setCertMobile(String certMobile) {
        this.certMobile = certMobile;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public static Map<String, Object> beanToMap(Object tf, Class clacc) {
        Map<String, Object> map = new HashMap<>();
        try {
            //获取指定类（Person）的BeanInfo 对象
            BeanInfo beanInfo = Introspector.getBeanInfo(clacc, Object.class);
            //获取所有的属性描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String key = pd.getName();
                Method getter = pd.getReadMethod();
                Object value = getter.invoke(tf);
                map.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger(Transfer.class);
    public static Integer getDataListCount(File csvFile) {
        LOGGER.info("getDataListCount-----------begin");
        List<Transfer> list = getDataList(csvFile);
        LOGGER.info("getDataListCount-----------begin"+list.size());
        int count = 0;
        if (list != null) {
            count = list.size();
        }
        LOGGER.info("getDataListCount-----------end"+count);
        return count;
    }

    public static String[] columnMapping = {"chnlId","merSeq", "transFlag", "firstMerNo", "accountType",
            "accountNo", "bankNo",
            "certType","certNo","certMobile","certName",
            "secondMerNo", "merAcctNo",
            "secMerAcctType", "merAcctIssuer", "merAcctBankNo", "bankId", "transAmt", "remark", "orderNo", "orderStat", "resultDesc"};

 public  static String charSet="GBK";


    public static List<Transfer> getDataList(File csvFile) {
        LOGGER.info("getDataList-----------begin"+csvFile.getAbsolutePath());
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        CSVReader csvReader = null;
        List<Transfer> dataList = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(csvFile);
            inputStreamReader = new InputStreamReader(fileInputStream, charSet);
            csvReader = new CSVReader(inputStreamReader);
            ColumnPositionMappingStrategy mapper = new ColumnPositionMappingStrategy<Transfer>();
            mapper.setType(Transfer.class);
            mapper.setColumnMapping(columnMapping);
            LOGGER.info("getDataList-----------begin  csvToBean");
            CsvToBean<Transfer> csvToBean = new CsvToBean<>();
            dataList = csvToBean.parse(mapper, csvReader);
            LOGGER.info("getDataList 大小-----------end"+dataList.size());
        } catch (Exception e) {
            LOGGER.info("getDataList-----------end  读取文件失败"+   e.getMessage());
            e.printStackTrace();
        } finally {
            LOGGER.info("getDataList-----------begin  关闭资源");
            closeReader(fileInputStream,inputStreamReader,csvReader);
            LOGGER.info("getDataList-----------end  关闭资源");
        }
        return dataList;
    }

    public String[] getDataForStringArray() {
        LOGGER.info("getDataForStringArray-----------begin ");
        String toString = this.toString();
        LOGGER.info("getDataForStringArray-----------end ");
        return toString.split("@");
    }


    public static void closeReader(  FileInputStream fileInputStream ,
            InputStreamReader inputStreamReader ,
            CSVReader csvReader ) {
        if (csvReader != null) {
            try {
                csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }

    public static void closeWriter(CSVWriter writer,
                                FileOutputStream fileOutputStream,
                                OutputStreamWriter outputStreamWriter,
                                BufferedWriter bufferedWriter) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (outputStreamWriter != null) {
                            try {
                                outputStreamWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }


    public static CSVWriter getCsvWriter(File file) {
        CSVWriter writer = null;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, charSet);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            writer = new CSVWriter(bufferedWriter, CSVWriter.NO_QUOTE_CHARACTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer;

    }

    @Override
    public String toString() {
        String split = "@";
        StringBuilder builder = new StringBuilder();
        builder.append(chnlId);
        builder.append(split);
        builder.append(merSeq);
        builder.append(split);
        builder.append(transFlag);
        builder.append(split);
        builder.append(firstMerNo);
        builder.append(split);
        builder.append(accountType);
        builder.append(split);
        builder.append(accountNo);
        builder.append(split);
        builder.append(bankNo);
        builder.append(split);

        builder.append(certType);
        builder.append(split);

        builder.append(certNo);
        builder.append(split);


        builder.append(certMobile);
        builder.append(split);


        builder.append(certName);
        builder.append(split);


        builder.append(secondMerNo);
        builder.append(split);
        builder.append(merAcctNo);
        builder.append(split);
        builder.append(secMerAcctType);
        builder.append(split);
        builder.append(merAcctIssuer);
        builder.append(split);
        builder.append(merAcctBankNo);
        builder.append(split);
        builder.append(bankId);
        builder.append(split);
        builder.append(transAmt);
        builder.append(split);
        builder.append(remark);
        builder.append(split);
        builder.append(orderNo);
        builder.append(split);
        builder.append(orderStat);
        builder.append(split);
        builder.append(resultDesc);
//        builder.append(split);
//        builder.append(trfSeq);
        return builder.toString();
    }


    public static Message dkNotify(String fileName) {
        String channel = "esbcli";
        final String charsetName = "UTF-8";
        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                        MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                                new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                                "交易成功"));
        Map<String, Object> body = (Map<String, Object>) m.getTarget().getBodys();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("transCode", "471012");
        body.put("svcCd", "30250003");
        body.put("svcScn", "01");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        body.put("fileFlg", "0");
        body.put("FileName", fileName);
        return m;
    }


    public static void main(String[] args) {
        //List<Transfer> dataList = new ArrayList<>();
       // dataList.add(new Transfer());
        List<Transfer> dataList = Transfer.getDataList(new File("D:\\FTPServer\\HTBANK_THDK_20180601_0003.csv"));

        System.out.println(dataList);

    }


}
