import com.upay.batch.stepservice.transfer.Transfer;
import com.upay.commons.csv.CSVReader;
import com.upay.commons.csv.bean.ColumnPositionMappingStrategy;
import com.upay.commons.csv.bean.CsvToBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static String[] columnMapping = {"chnlId","merSeq", "transFlag", "firstMerNo", "accountType",
            "accountNo", "bankNo",
            "certType","certNo","certMobile","certName",
            "secondMerNo", "merAcctNo",
            "secMerAcctType", "merAcctIssuer", "merAcctBankNo", "bankId", "transAmt", "remark", "orderNo", "orderStat", "resultDesc"};

    public static List<Transfer> getDataList(File csvFile) {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        CSVReader csvReader = null;
        List<Transfer> dataList = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(csvFile);
            inputStreamReader = new InputStreamReader(fileInputStream, "gbk");
            csvReader = new CSVReader(inputStreamReader);
            ColumnPositionMappingStrategy mapper = new ColumnPositionMappingStrategy<Transfer>();
            mapper.setType(Transfer.class);
            mapper.setColumnMapping(columnMapping);
            CsvToBean<Transfer> csvToBean = new CsvToBean<>();
            //List<Transfer> parse = csvToBean.parse();
            dataList = csvToBean.parse(mapper, csvReader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeReader(fileInputStream,inputStreamReader,csvReader);
        }
        return dataList;
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

    public static void main(String[] args) {
       // List<Transfer> dataList = getDataList(new File("D:\\FTPServer\\HTBANK_THDK_20190412_0001.csv"));
      //  System.out.println(dataList);

        System.out.println((int)'`');
        System.out.println((int)'\0');
    }
}
