package com.upay.busi.acc.service.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFRichTextString;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.acc.service.dto.AccBindInfoDto;
import com.upay.busi.acc.service.dto.AccBindQueryDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.pay.ChannelLogoBookPo;
import com.upay.dao.po.pay.PayCardbinInfoPo;
import com.upay.dao.po.pay.PayRouteCtlInfoPo;


/**
 * 查询绑卡信息
 * 
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午10:09:59
 */
public class ImportPayCardInfoService extends AbstractDipperHandler<AccBindQueryDto> {

    private final static Logger log = LoggerFactory.getLogger(BindBookService.class);
    @Resource
    private IDaoService daoService;


    @Override
    public AccBindQueryDto execute(AccBindQueryDto dto, Message message) throws Exception {
        List<PayCardbinInfoPo> list = daoService.selectList(new PayCardbinInfoPo());
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        int i=1000;
        for(PayCardbinInfoPo pay:list){
        	String bankBinFlag = pay.getBankBinFlag();
        	if("2".equals(bankBinFlag)){
        		HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ID", i + "");
				map.put("CARD_BIN", pay.getCardBin());
				map.put("ROUTE_CODE", "0004");
				map.put("ROUTE_FUNC_CODE", "01");
				map.put("PAY_PRITY", "1");
				map.put("ROUTE_STAT", "1");
				map.put("ROUTE_LMTBAL", new BigDecimal("1"));
				map.put("LAST_UPDATE_TIME", "2015-06-16 00:00:00");
				map.put("REMARK1", null);
				map.put("REMARK2", null);
				result.add(map);
				i=i+1;
				
				
				map = new HashMap<String, Object>();
				map.put("ID", i + "");
				map.put("CARD_BIN", pay.getCardBin());
				map.put("ROUTE_CODE", "0004");
				map.put("ROUTE_FUNC_CODE", "02");
				map.put("PAY_PRITY", "1");
				map.put("ROUTE_STAT", "1");
				map.put("ROUTE_LMTBAL", new BigDecimal("1"));
				map.put("LAST_UPDATE_TIME", "2015-06-16 00:00:00");
				map.put("REMARK1", null);
				map.put("REMARK2", null);
				result.add(map);
				i=i+1;
				
				map = new HashMap<String, Object>();
				map.put("ID", i + "");
				map.put("CARD_BIN", pay.getCardBin());
				map.put("ROUTE_CODE", "0004");
				map.put("ROUTE_FUNC_CODE", "03");
				map.put("PAY_PRITY", "1");
				map.put("ROUTE_STAT", "1");
				map.put("ROUTE_LMTBAL", new BigDecimal("1"));
				map.put("LAST_UPDATE_TIME", "2015-06-16 00:00:00");
				map.put("REMARK1", null);
				map.put("REMARK2", null);
				result.add(map);
				i=i+1;
        		
        	}else{
        		HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ID", i + "");
				map.put("CARD_BIN", pay.getCardBin());
				map.put("ROUTE_CODE", "0001");
				map.put("ROUTE_FUNC_CODE", "01");
				map.put("PAY_PRITY", "1");
				map.put("ROUTE_STAT", "1");
				map.put("ROUTE_LMTBAL", new BigDecimal("1"));
				map.put("LAST_UPDATE_TIME", "2015-06-16 00:00:00");
				map.put("REMARK1", null);
				map.put("REMARK2", null);
				result.add(map);
				i=i+1;
				
				map = new HashMap<String, Object>();
				map.put("ID", i + "");
				map.put("CARD_BIN", pay.getCardBin());
				map.put("ROUTE_CODE", "0001");
				map.put("ROUTE_FUNC_CODE", "02");
				map.put("PAY_PRITY", "1");
				map.put("ROUTE_STAT", "1");
				map.put("ROUTE_LMTBAL", new BigDecimal("1"));
				map.put("LAST_UPDATE_TIME", "2015-06-16 00:00:00");
				map.put("REMARK1", null);
				map.put("REMARK2", null);
				result.add(map);
				i=i+1;
				
				map = new HashMap<String, Object>();
				map.put("ID", i + "");
				map.put("CARD_BIN", pay.getCardBin());
				map.put("ROUTE_CODE", "0001");
				map.put("ROUTE_FUNC_CODE", "03");
				map.put("PAY_PRITY", "1");
				map.put("ROUTE_STAT", "1");
				map.put("ROUTE_LMTBAL", new BigDecimal("1"));
				map.put("LAST_UPDATE_TIME", "2015-06-16 00:00:00");
				map.put("REMARK1", null);
				map.put("REMARK2", null);
				result.add(map);
				i=i+1;
        	}
        }
        OutputStream out = new FileOutputStream("e:/1.xls");
        String title = "sheet1";
        String[] headers = {"ID", "CARD_BIN", "ROUTE_CODE", "ROUTE_FUNC_CODE", "PAY_PRITY", "ROUTE_STAT", "ROUTE_LMTBAL", "LAST_UPDATE_TIME", "REMARK1", "REMARK2"};
		String[] columns = {"ID", "CARD_BIN", "ROUTE_CODE", "ROUTE_FUNC_CODE", "PAY_PRITY", "ROUTE_STAT", "ROUTE_LMTBAL", "LAST_UPDATE_TIME", "REMARK1", "REMARK2"};
		String pattern = "";
        exportExcel(title, headers, columns, result, out, pattern);
        return dto;
    }
    @SuppressWarnings("deprecation")
	public static void exportExcel(String title, String[] headers,
			String[] columns, List<HashMap<String, Object>> result,
			OutputStream out, String pattern) throws Exception {

		// 声明�?��工作�?
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		// 生成�?��表格
//		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为20个字�?
		// sheet.setDefaultColumnWidth(Short.valueOf("20"));

//		// 生成�?��样式
//		HSSFCellStyle style = workbook.createCellStyle();
//		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.GOLD.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 生成�?��字体
//		HSSFFont font = workbook.createFont();
//		font.setColor(HSSFColor.VIOLET.index);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样�?
//		style.setFont(font);
//
//		// 指定当单元格内容显示不下时自动换�?
//		style.setWrapText(true);
//
//		// 产生表格标题�?
//		HSSFRow row = sheet.createRow(0);
//		for (short i = 0; i < headers.length; i++) {
//			HSSFCell cell = row.createCell(i);
//			cell.setCellStyle(style);
//			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//			cell.setCellValue(text);
//		}
//		// 遍历集合数据，产生数据行
//		if (result != null) {
//			int index = 1;
//			for (HashMap<String, Object> m : result) {
//				row = sheet.createRow(index);
//				short cellIndex = 0;
//				for (String s : columns) {
//					HSSFCell cell = row.createCell(cellIndex);
//					HSSFRichTextString richString = new HSSFRichTextString(
//							m.get(s) == null ? "" : m.get(s).toString());
//					cell.setCellValue(richString);
//					cellIndex++;
//				}
//				index++;
//			}
//		}
//		workbook.write(out);

	}
}
