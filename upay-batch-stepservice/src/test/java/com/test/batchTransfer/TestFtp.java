package com.test.batchTransfer;

import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpPut;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.page.QueryResult;
import com.upay.batch.stepservice.transfer.*;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.csv.CSVWriter;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FileUtil;
import com.upay.dao.IDaoService;
import com.upay.dao.po.gnr.FileInfoPo;
import com.upay.dao.po.pay.PayFlowDetailPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestFtp {
    final static Logger log = LoggerFactory.getLogger(TestFtp.class);


    @Resource(name = "DownloadTransferFileStep")
    DownloadTransferFileStep downloadTransferFileStep;
    @Resource(name = "GetTransferDataStep")
    GetTransferDataStep getTransferDataStep;
    @Resource(name = "BatchTransferStep")
    BatchTransferStep batchTransferStep;

    @Resource(name = "GenBackFileStep")
    GenBackFileStep genBackFileStep;


    @Resource(name = "UpdatePayResult2BackFileStep")
    UpdatePayResult2BackFileStep updatePayResult2BackFileStep;


    @Resource(name = "UploadBackFileStep")
    UploadBackFileStep uploadBackFileStep;

    @Test
    public void testFtp() throws FtpException {
        File file = new File("D:\\FTPServer\\HTBANK_THDK_20180809_0001.csv");
        //   String esbFtpPut = FileUtil.ESBFtpPut(file.getAbsolutePath(),"/UPP/20180524/"+file.getName(),"CBS");
        FtpClientConfigSet configSet = new FtpClientConfigSet();
        FtpPut ftpPut = new FtpPut(file.getAbsolutePath(), "/UPP/" + file.getName(), "CBS", false, configSet, null);
        String str = ftpPut.doPutFile();
    }

    @Test
    public void testFtp1() throws FtpException {
        boolean esbFtpGet =
                FileUtil.ESBFtpGet("/BATCH_TRANSFER/20180809/HTBANK_THDK_20180809_0001.csv", "D:/FTPServer/HTBANK_THDK_20180809_0002.csv", "CBS");

    }

    @Test
    public void testExecute() throws Exception {
        long begin = System.currentTimeMillis();
        long begin1 = System.currentTimeMillis();
        log.info("1=====begin");
        testExecute1();
        log.info("1=====end");
        long end1 = System.currentTimeMillis();
        float seconds1 = (end1 - begin1) / 1000F;
        String test1 = Float.toString(seconds1);

        long begin2 = System.currentTimeMillis();
        log.info("2=====begin");
        testExecute2();
        log.info("2=====end");
        long end2 = System.currentTimeMillis();
        float seconds2 = (end2 - begin2) / 1000F;
        String test2 = Float.toString(seconds2);


        long begin3 = System.currentTimeMillis();
        log.info("3=====begin");
        testExecute3();
        log.info("3=====end");
        long end3 = System.currentTimeMillis();
        float seconds3 = (end3 - begin3) / 1000F;
        String test3 = Float.toString(seconds3);


        long begin4 = System.currentTimeMillis();
        log.info("4=====begin");
        testExecute4();
        log.info("4=====end");
        long end4 = System.currentTimeMillis();
        float seconds4 = (end4 - begin4) / 1000F;
        String test4 = Float.toString(seconds4);


        long begin5 = System.currentTimeMillis();

        log.info("5=====begin");
        testExecute5();
        log.info("5=====end");
        long end5 = System.currentTimeMillis();
        float seconds5 = (end5 - begin5) / 1000F;
        String test5 = Float.toString(seconds5);


        long begin6 = System.currentTimeMillis();
        log.info("6=====begin");
        testExecute6();
        log.info("6=====end");
        long end6 = System.currentTimeMillis();
        float seconds6 = (end6 - begin6) / 1000F;
        String test6 = Float.toString(seconds6);

        System.out.println("test1 : " + test1);
        System.out.println("test2 : " + test2);
        System.out.println("test3 : " + test3);
        System.out.println("test4 : " + test4);
        System.out.println("test5 : " + test5);
        System.out.println("test6 : " + test6);
        long end = System.currentTimeMillis();
        float seconds = (end - begin) / 1000f;
        System.out.println(seconds / 60 + "总时间 分钟");
    }


    @Resource
    private IDaoService daoService;

    @Test
    public void testExecute11() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("transStat1", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N);
        map.put("transStat2", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING);
        QueryResult<PayFlowDetailPo> queryResult = daoService.selectQueryResult(PayFlowDetailPo.class.getName() + ".getTransferData", map, 700, 100);

    }


    public void testExecute1() throws Exception {
        BatchParams batchParams = new BatchParams();
        batchParams.setBatchNo("2012257845220232");
        batchParams.setTranDate(DateUtil.parse("2018-06-01", "yyyy-MM-dd"));
        downloadTransferFileStep.execute(batchParams, 0, null, null);
    }


    public void testExecute2() throws Exception {
     /*   BatchParams batchParams = new BatchParams();
        batchParams.setBatchNo("20122578452202322");
        batchParams.setTranDate(DateUtil.parse("2018-06-01", "yyyy-MM-dd"));
        List<Map<String, Object>> objectList = getTransferDataStep.getObjectList(batchParams);
        if (objectList != null && objectList.size() > 0) {
            for (Map<String, Object> object : objectList) {
                //  int totalResult = getTransferDataStep.getTotalResult(batchParams, object);
                //  List<Map<String, Object>> dataList = getTransferDataStep.getDataList(batchParams, 0, 20000, object);
                List<Transfer> dataList = getTransferDataStep.getDataList(batchParams, 0, 0, object);
                for (Transfer tf : dataList) {
                    getTransferDataStep.execute(batchParams, 0, tf, object);
                }
                getTransferDataStep.updateObject(batchParams, object);
                // }
            }
        }*/

    }


    public void testExecute3() throws Exception {
        BatchParams batchParams = new BatchParams();
        batchParams.setBatchNo("2012257845220232");
        batchParams.setTranDate(DateUtil.parse("2018-06-01", "yyyy-MM-dd"));
        int totalResult = batchTransferStep.getTotalResult(batchParams, null);
        List<PayFlowDetailPo> dataList = batchTransferStep.getDataList(batchParams, 0, 10000, null);
        for (PayFlowDetailPo map : dataList) {
            batchTransferStep.execute(batchParams, 0, map, null);
        }
    }


    public void testExecute4() throws Exception {
        BatchParams batchParams = new BatchParams();
        batchParams.setBatchNo("2012257845220232");
        batchParams.setTranDate(DateUtil.parse("2018-06-01", "yyyy-MM-dd"));
        List<FileInfoPo> dataList = genBackFileStep.getDataList(batchParams, 0, 10000, null);
        for (FileInfoPo info : dataList) {
            genBackFileStep.execute(batchParams, 0, info, null);
        }
    }


    public void testExecute5() throws Exception {
        BatchParams batchParams = new BatchParams();
        batchParams.setBatchNo("2012257845220232");
        batchParams.setTranDate(DateUtil.parse("2018-06-01", "yyyy-MM-dd"));
        List<FileInfoPo> dataList = updatePayResult2BackFileStep.getObjectList(batchParams);
        for (FileInfoPo info : dataList) {
            updatePayResult2BackFileStep.execute(batchParams, 0, null, info);
            updatePayResult2BackFileStep.updateObject(batchParams, info);
        }
    }


    public void testExecute6() throws Exception {
        BatchParams batchParams = new BatchParams();
        batchParams.setBatchNo("2012257845220232");
        batchParams.setTranDate(DateUtil.parse("2018-06-01", "yyyy-MM-dd"));
        batchParams.setPreDate(DateUtil.parse("2018-05-31", "yyyy-MM-dd"));
        List<FileInfoPo> dataList = uploadBackFileStep.getDataList(batchParams, 0, 10000, null);
        for (FileInfoPo info : dataList) {
            uploadBackFileStep.execute(batchParams, 0, info, null);
        }
    }


    public static void testExecute7() throws Exception {

        CSVWriter writer = null;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        String format = DateUtil.format(new Date(), "yyyyMMdd");
        File file = new File("D:\\FTPServer\\HTBANK_THDK_"+format+"_0001.csv");
        try {
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            writer = new CSVWriter(bufferedWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            format = DateUtil.format(new Date(), "MMddHHmm");
            String head = "HTB_BANK"+format;
            Random random=new Random();
            for (int i = 1; i <= 30; i++) {
                String no = null;
                String amt =String.valueOf((random.nextInt(9)+1)*0.01);
                String name = null;
                String bankId = "";
                String chnlId = "12";
                String flag = "";
                String acctNo = "";
                String accNo = "";
                String acctType= "";
                String accType = "";
                String bankNo = "";


                if (i>15) {
                    name = "招行测试";
                    bankId = "308";
                    flag = "2";
                } else {
                    flag = "1";
                }
                String uuid=System.currentTimeMillis()+"";
                if (i < 10) {
                    no = head + uuid + i;
                   // amt = "0.01";
                    //   chnlId="12";

                } else if (i >= 10 && i < 100) {
                    no = head + uuid + i;
                  //  amt = "0.02";
                    // chnlId="06";

                } else if (i >= 100 && i < 1000) {
                    no = head + uuid + i;
                    amt = "0.03";
                    // chnlId="12";

                } else if (i >= 1000 && i < 10000) {
                    no = head +uuid + i;
                   // amt = "0.04";
                    // chnlId="12";

                } else if (i >= 10000) {
                    no = head + uuid + i;
                  //  amt = "0.05";
                    // chnlId="06";

                }

                if ("1".equals(flag)) {//代收}
                        if (i == 1) {
                            //收款方 本行卡
                            //付款方  本行卡
                             acctNo = "6229807711600013706";
                             accNo =  "6229807711500426222";
                             acctType="11";
                             accType="11";
                        }  if (i == 2) {
                            //收款方 本行卡
                            //付款方  本行对公
                            acctNo = "6229807711600013706";
                            accNo = "5324015001301000004251";
                            acctType="11";
                            accType="23";

                        }  if (i == 3) {
                            //收款方 本行卡
                            //付款方 本行内部户
                            acctNo = "6229807711600013706";
                            accNo = "1010001300119000002";
                            acctType="11";
                            accType="21";

                        }  if (i == 4) {
                            //收款方 本行卡
                            //付款方 他行卡
                            acctNo = "6229807711600013706";
                            accNo = "6216261000000000018";
                            acctType="11";
                            accType="22";
                            bankId="621626";

                        }  if (i == 5) {
                            //收款方 本行卡
                            //付款方  他行对公
                            acctNo = "6229807711600013706";
                            accNo = "5000000000000000000000";
                            acctType="11";
                            accType="24";
                            bankId="308";

                        }  if (i == 6) {
                            //收款方   本行内部户
                            //付款方   本行卡
                            acctNo = "1010001300119000002";
                            accNo = "6229807711600013706";
                            acctType="21";
                            accType="11";

                        }  if (i == 7) {
                            //收款方   本行内部户
                            //付款方  本行对公
                            acctNo = "1010001300119000002";
                            accNo = "2013111000425492";
                            acctType="21";
                            accType="23";


                        }  if (i == 8) {
                            //收款方   本行内部户
                            //付款方      本行内部户
                            acctNo = "1010001300119000002";
                            accNo = "1010001300118000001";
                            acctType="21";
                            accType="21";

                        }  if (i == 9) {
                            //收款方   本行内部户
                            //付款方    他行卡
                            acctNo = "1010001300119000002";
                            accNo = "6216261000000000018";
                            acctType="21";
                            accType="22";
                            bankId="621626";

                        }  if (i == 10) {
                            //收款方   本行内部户
                            //付款方    他行对公
                            acctNo = "1010001300119000002";
                            accNo = "5000000000000000000000";
                            acctType="21";
                            accType="24";
                            bankId="308";

                        }  if (i == 11) {
                            //收款方     本行对公
                            //付款方   本行卡
                            acctNo = "5324019901301000058699";
                            accNo = "6229807711600013706";
                            acctType="23";
                            accType="11";


                        }  if (i == 12) {
                            //收款方     本行对公
                            //付款方    本行对公
                            acctNo = "5324019901301000058699";
                            accNo =  "5324015001301000004251";
                            acctType="23";
                            accType="23";

                        }  if (i == 13) {
                            //收款方     本行对公
                            //付款方     本行内部户
                            acctNo = "5324015001301000004251";
                            accNo = "1010001300119000002";
                            acctType="23";
                            accType="21";

                        }  if (i == 14) {
                            //收款方     本行对公
                            //付款方 他行卡
                            acctNo = "5324015001301000004251";
                            accNo = "6229807711600013706";
                            acctType="23";
                            accType="22";
                            bankId="622980";

                        }  if (i == 15) {
                            //收款方     本行对公
                            //付款方    他行对公
                            acctNo = "5324019901301000058699";
                            accNo =  "5000000000000000000000";
                            acctType="23";
                            accType="24";
                            bankId="308";
                        }





                    /**
                     *
                     * 收款方:-
                     * 付款方:本行卡  本行三类户    本行内部户  本行对公账户    他行卡   他行对公账户
                     *
                     *
                     * 收款方:本行卡
                     * 付款方:本行三类户
                     *
                     * 收款方:本行内部户
                     * 付款方:本行三类户
                     *
                     *
                     * 收款方：本行对公账户
                     * 付款方:本行三类户
                     *
                     *
                     *
                     */

                } else {
                    if (i == 16) {
                            //付款方：  本行卡
                            //收款方：本行卡
                            acctNo = "6229807711500426222";
                            accNo = "6229807711600013706";
                            acctType="11";
                            accType="11";
                        }  if (i == 17) {
                            //付款方：本行卡
                            //收款方：本行对公
                            acctNo = "6229807711600013706";
                            accNo = "2013111000425492";
                            acctType="11";
                            accType="23";

                        }  if (i == 18) {
                            //付款方：本行卡
                            //收款方：本行内部户
                            acctNo = "6229807711600013706";
                            accNo = "1010001300119000002";
                            acctType="11";
                            accType="21";

                        }  if (i == 19) {
                            //付款方：本行卡
                            //收款方：	他行卡
                            acctNo = "6229807711600013706";
                            accNo = "6216261000000000018";
                            acctType="11";
                            accType="22";
                            bankId="621626";

                        }  if (i == 20) {
                            //付款方：本行卡
                            //收款方：他行对公
                            acctNo = "6229807711600013706";
                            accNo = "5000000000000000000000";
                            acctType="11";
                            accType="24";
                            bankId="308";

                        }  if (i == 21) {
                            //付款方：本行内部户
                            //收款方：本行卡
                            acctNo = "1010001300119000002";
                            accNo = "6229807711600013706";
                            acctType="21";
                            accType="11";

                        }  if (i == 22) {
                            //付款方：本行内部户
                            //收款方：本行对公
                            acctNo = "1010001300119000002";
                            accNo = "2013111000425492";
                            acctType="21";
                            accType="23";

                        }  if (i == 23) {
                            //付款方：本行内部户
                            //收款方： 本行内部户
                            acctNo = "1010001300118000002";
                            accNo = "1010001300119000001";
                            acctType="21";
                            accType="21";
                        }  if (i == 24) {
                            //付款方：本行内部户
                            //收款方：他行卡
                            acctNo = "1010001300119000002";
                            accNo = "6216261000000000018";
                            acctType="21";
                            accType="22";
                            bankId="621626";

                        }  if (i == 25) {
                            //付款方：本行内部户
                            //收款方：他行对公
                            acctNo = "1010001300119000002";
                            accNo = "5000000000000000000000";
                            acctType="21";
                            accType="24";
                            bankId="308";

                        }  if (i == 26) {
                            //付款方：本行对公
                            //收款方：	本行卡
                            acctNo = "5324019901301000058699";
                            accNo = "6229807711600013706";
                            acctType="23";
                            accType="11";

                        }  if (i == 27) {
                            //付款方：本行对公
                            //收款方：	本行对公
                            acctNo = "5324019901301000058699";
                            accNo = "5324015001301000004251";
                            acctType="23";
                            accType="23";

                        }  if (i == 28) {
                            //付款方：本行对公

                            //收款方：	本行内部户
                            acctNo = "5324015001301000004251";
                            accNo = "1010001300119000002";
                            acctType="23";
                            accType="21";

                        }  if (i == 29) {
                            //付款方：本行对公

                            //收款方：	他行卡
                            acctNo = "5324019901301000058699";
                            accNo = "6229807711600013706";
                            acctType="23";
                            accType="22";
                            bankId="622980";

                        }  if (i == 30) {
                            //付款方：本行对公
                            //收款方：	他行对公
                            acctNo = "2013111000425492";
                            accNo = "5000000000000000000000";
                            acctType="23";
                            accType="24";
                            bankId="308";
                        }

                     /*=================================*/

                   /* if (i >= 6800 && i < 7000) {

                        acctNo = "";
                        accNo = "";


                    } else if (i >= 7000 && i < 7200) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 7200 && i < 7400) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 7400 && i < 7600) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 7600 && i < 7800) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 7800 && i < 8000) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 8000 && i < 8200) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 8200 && i < 8400) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 8400 && i < 8600) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 8800 && i < 9000) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 9000 && i < 9200) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 9200 && i < 9400) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 9400 && i < 9600) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 9600 && i < 9800) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 9800 && i <= 10000) {
                        acctNo = "";
                        accNo = "";
                    }*/
                }


                String[] data = {
                        chnlId,
                        no,
                        flag,
                        "",
                        acctType,
                      /*  "1010001300118000001",*/ acctNo,
                        bankNo,
                        "01",
                        "530421198608140321",
                        "13887738666",
                        "刘丹",
                        "",
                        /*"6216261000000000018",*/accNo,
                        accType,
                        name,
                        "",
                        bankId,
                        amt
                };
                writer.writeNext(data);
            }
        } catch (
                Exception ex)

        {
            ex.printStackTrace();
        } finally

        {
            Transfer.closeWriter(writer, fileOutputStream, outputStreamWriter, bufferedWriter);
        }
    }

    //region Description
    @Test
    public void testExecute1Cron() throws Exception {


    }
    //endregionllllproject-manager



}
