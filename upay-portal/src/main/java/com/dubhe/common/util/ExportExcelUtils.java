package com.dubhe.common.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upay.commons.util.DateUtil;


public class ExportExcelUtils {
    /**
     * 导出Excel的方法
     * 
     * @param title
     *            excel中的sheet名称
     * @param headers
     *            表头
     * @param columns
     *            表头对应的数据库中的列名
     * @param result
     *            结果集
     * @param out
     *            输出流
     * @param pattern
     *            时间格式
     * @throws Exception
     */

    private final static Logger logger = LoggerFactory.getLogger(ExportExcelUtils.class);


    @SuppressWarnings("deprecation")
    public static void exportExcel(String title, String[] headers, String[] columns,
            List<HashMap<String, Object>> result, OutputStream out, String pattern) throws Exception {

        logger.info(" ===== export excel start =====");
        logger.info(" ===== title =====" + title);
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth(Short.valueOf("20"));

        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.GOLD.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        if (result != null) {
            int index = 1;
            for (HashMap<String, Object> m : result) {
                row = sheet.createRow(index);
                short cellIndex = 0;
                for (String s : columns) {
                    HSSFCell cell = row.createCell(cellIndex);
                    HSSFRichTextString richString =
                            new HSSFRichTextString(m.get(s) == null ? "" : m.get(s).toString());
                    cell.setCellValue(richString);
                    cellIndex++;
                }
                index++;
            }
        }
        workbook.write(out);
        logger.info("方法执行结束时间111" + DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:sss"));
        logger.info(" ===== export excel end =====");
    }


    public static void main(String[] args) throws Exception {
        String title = "sheet1";
        String[] headers = { "项目1", "项目2", "项目3" };
        String[] columns = { "ID", "USER", "PASS" };
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        int i = 0;
        while (i < 10) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ID", "00001");
            map.put("AGE", "21");
            map.put("USER", "张三");
            map.put("PASS", "123qwe");
            result.add(map);
            i++;
        }
        OutputStream out = new FileOutputStream("e:/1.xls");
        String pattern = "";
        exportExcel(title, headers, columns, result, out, pattern);
    }

}
