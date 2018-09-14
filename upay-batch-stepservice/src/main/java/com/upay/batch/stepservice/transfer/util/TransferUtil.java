package com.upay.batch.stepservice.transfer.util;

import com.pactera.dipper.dao.service.IDaoService;
import com.upay.dao.po.pay.PayCardbinInfoPo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * 转账使用工具方法。
 * @author zhangjianfeng
 * @since 2017/08/11 17:25
 */
public class TransferUtil {

    /**
     * 获取Cell中数据字符串值。当Cell Type为CELL_TYPE_NUMERIC时将截取掉小数部分，只取整数部分。
     * 如果Cell中为金额参考getCellStringAmt()方法。
     * @param cell
     * @return
     */
    public static String getCellStringValue(Cell cell) {
        String cellStringVal = null;
        // 如果Cell数据为null，则返回null值
        if (null == cell) {
            return null;
        }
        // 获取Cell数据类型。
        int cellType = cell.getCellType();
        // 空Cell设置空值。
        if (Cell.CELL_TYPE_BLANK == cellType) {
            cellStringVal = "";
        } else if (Cell.CELL_TYPE_BOOLEAN == cellType) {
            // boolean类型设置为字符串true或false。
            cellStringVal = Boolean.toString(cell.getBooleanCellValue());
        } else if (Cell.CELL_TYPE_ERROR == cellType) {
            cellStringVal = String.valueOf(cell.getErrorCellValue());
        } else if (Cell.CELL_TYPE_NUMERIC == cellType) {
            DecimalFormat df = new DecimalFormat("#");
            cellStringVal = df.format(cell.getNumericCellValue());
        } else if (Cell.CELL_TYPE_FORMULA == cellType) {
            int formulaResultType = cell.getCachedFormulaResultType();
            if (Cell.CELL_TYPE_BLANK == formulaResultType) {
                cellStringVal = "";
            } else if (Cell.CELL_TYPE_BOOLEAN == formulaResultType) {
                // boolean类型设置为字符串true或false。
                cellStringVal = Boolean.toString(cell.getBooleanCellValue());
            } else if (Cell.CELL_TYPE_ERROR == formulaResultType) {
                cellStringVal = String.valueOf(cell.getErrorCellValue());
            } else if (Cell.CELL_TYPE_NUMERIC == formulaResultType) {
                DecimalFormat df = new DecimalFormat("#");
                cellStringVal = df.format(cell.getNumericCellValue());
            } else {
                cellStringVal = cell.getStringCellValue();
            }
        } else {
            cellStringVal = cell.getStringCellValue();
        }
        return cellStringVal;
    }

    /**
     * 获取Cell数据，数据值为金额。
     * @param cell
     * @return 返回金额
     */
    public static BigDecimal getCellDecimalValue(Cell cell) {
        BigDecimal amount = null;
        if (null == cell) {
            return null;
        }
        // 获取Cell数据类型。
        int cellType = cell.getCellType();
        if (Cell.CELL_TYPE_BLANK == cellType) {
            amount = null;
        } else if (Cell.CELL_TYPE_NUMERIC == cellType) {
            amount = BigDecimal.valueOf(cell.getNumericCellValue());
        } else if (Cell.CELL_TYPE_STRING == cellType) {
            amount = new BigDecimal(cell.getStringCellValue());
        } else if (Cell.CELL_TYPE_FORMULA == cellType) {
            int formulaResultType = cell.getCachedFormulaResultType();
            if (Cell.CELL_TYPE_BLANK == formulaResultType) {
                amount = null;
            } else if (Cell.CELL_TYPE_NUMERIC == formulaResultType) {
                amount = new BigDecimal(cell.getStringCellValue());
            } else if (Cell.CELL_TYPE_STRING == cellType) {
                amount = new BigDecimal(cell.getStringCellValue());
            }
        }
        return amount;
    }

    /**
     * 判断转账（Excel）文件中，行数据是否有效。
     * 行数据必须按照微信转账文件规则设置各列数据。
     * @param row
     * @return false 不为有效行数据；true 为有效行数据。
     */
    public static boolean isValidRow(Row row) {
        boolean isValid = false;
        // 行（Row）数据为null，不为有效行数据。
        if (null == row) {
            isValid = false;
        } else {
            // 确认商户流水号是否有效，如果有效则行数据有效。
            String merSeq = getCellStringValue(row.getCell(0));
            isValid = StringUtils.isNotBlank(merSeq);
        }
        return isValid;
    }

    /**
     * 获取Excel文件中批定Sheet页的最后一行有效转账记录数据的索引。
     * @param sheet
     * @return
     */
    public static int getLastRowIndex(Sheet sheet) {
        if (null == sheet) {
            throw new IllegalArgumentException("无效的Sheet页！");
        }
        int lastRowNum = sheet.getLastRowNum();
        // 确认最后一行数据。
        for (boolean isLastRow = false; !isLastRow && lastRowNum > 2;) {
            Row row = sheet.getRow(lastRowNum);
            // 如果不为有效行，则移动至上一行。
            if (!isValidRow(row)) {
                lastRowNum--;
            } else {
                // 如果当前行数据有效，则为最后一行。
                isLastRow = true;
            }
        }

        return lastRowNum;
    }

    /**
     * 根据卡号查询bin信息。
     *
     * @param cardNo
     * @return
     */
    public static PayCardbinInfoPo queryCardBinInfo(String cardNo, IDaoService daoService) {
        // 卡号为空
        if (StringUtils.isBlank(cardNo)) {
            return null;
        }

        PayCardbinInfoPo cardBinInfo = null;

        HashMap<String, Object> cardNoLenMap = new HashMap<String, Object>();
        cardNoLenMap.put("cardNoLen", cardNo.length());
        List<Integer> list = daoService.selectList(
                PayCardbinInfoPo.class.getName() + ".selectCardBinLenList", cardNoLenMap);

        for (Integer cardBinLen : list) {
            // 按长度截取卡BIN（从长到短），获得数据跳出循环
            if (cardNo.length() <= cardBinLen) {
                return null;
            }

            String cardBin = cardNo.substring(0, cardBinLen);
            PayCardbinInfoPo payCardbinInfoPo = new PayCardbinInfoPo();
            payCardbinInfoPo.setCardBin(cardBin);
            payCardbinInfoPo.setCardNoLen(cardNo.length());
            payCardbinInfoPo = daoService.selectOne(payCardbinInfoPo);

            if (payCardbinInfoPo != null) {
                cardBinInfo = payCardbinInfoPo;
                break;
            }
        }
        return cardBinInfo;
    }

    /**
     * 在Excel Row中的批定列添加一个空内容的Cell。
     * @param row
     * @param columnIdx
     * @return
     */
    public static Cell initBlankCell(Row row, int columnIdx) {
        Cell cell = row.getCell(columnIdx);
        if (cell == null) {
            cell = row.createCell(columnIdx);
        }

        return cell;
    }
}
