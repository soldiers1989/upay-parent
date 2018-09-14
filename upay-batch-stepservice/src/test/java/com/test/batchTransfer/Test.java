package com.test.batchTransfer;

import com.upay.batch.stepservice.transfer.DownloadTransferFileStep;
import com.upay.batch.stepservice.transfer.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);




    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;

    }



    public static void main(String[] args) throws Exception {







        TestFtp.testExecute7();

                  /*  if (i < 500) { //
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 500 && i < 1000) {
                        acctNo = "";
                        accNo = "";
                    } else if (i >= 1000 && i < 1500) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 1500 && i < 2000) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 2500 && i < 3000) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 3000 && i < 3500) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 3500 && i < 4000) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 4000 && i < 4500) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 5000 && i < 5300) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 5300 && i < 5600) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 5600 && i < 5900) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 5900 && i < 6200) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 6200 && i < 6500) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 6500 && i < 6800) {
                        acctNo = "";
                        accNo = "";

                    } else if (i >= 6800 && i < 7100) {
                        acctNo = "";
                        accNo = "";

                    }*/

    //    List<Transfer> dataList = Transfer.getDataList(new File("D:\\FTPServer\\HTBANK_THDK_20180603_0001.csv"));
    }
}
