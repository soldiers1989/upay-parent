package com.upay.commons.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.utils.common.TypeUtils;


/**
 * 文件工具类<br/>
 * 
 * @author WUDUFENG
 * 
 */
public class TransUtil {
    private static final Logger logger = LoggerFactory.getLogger(TransUtil.class);


    /**
     * 校验文件是否存在，若不存在则抛出异常
     * 
     * @param fileName
     */
    public static boolean exists(String fileName) {
        File f = new File(fileName);
        logger.debug(f.getAbsolutePath());
        return f.exists();
    }


    /**
     * 获取配置文件
     * 
     * @param transCode
     * @param transType
     * @return
     */
    public static Data getTemplateType(String transCode, String transType) {

        if (StringUtils.isBlank(transCode))
            throw new RuntimeException("交易码不能为空");

        if (StringUtils.isBlank(transType))
            throw new RuntimeException("转换类型不能为空");

        TemplateType templateType = "IN".equals(transType) ? TemplateType.IN : TemplateType.OUT;

        Data data = TransTemplateContext.getInstance().getConfig(templateType, transCode);

        return data;
    }


    /**
     * 交易前转换
     * 
     * @param paramMap
     * @param columnList
     * @return
     */
    public static Map<String, String> parseValueBeforeTrans(String transCode, Map<String, Object> paramMap,
            String transType) {
        logger.info("paramMap:[{}]", paramMap);
        if (paramMap == null || paramMap.size() == 0) {

            throw new RuntimeException("传入Map不能为空");
        }
        Data data = getTemplateType(transCode, transType);

        List<Column> columnList = data.getColumnList();
        Map<String, String> resultMap = new HashMap<String, String>();
        // 转换变量名和类型

        for (Column column : columnList) {
            String oldName = column.getOldName();// paramMap中key
            String newName = column.getNewName();// resultMap中key
            String type = column.getType();
            String newValue = "";
            String must = column.getMust();
            if (paramMap.containsKey(oldName)) {
                Object oldValue = paramMap.get(oldName);

                if (StringUtils.isNotBlank(type)) {
                    if ("java.math.BigDecimal".equals(type)) {
                        BigDecimal monney = (BigDecimal) oldValue;
                        String numMultiple = column.getNumMultiple();
                        if (StringUtils.isNotBlank(numMultiple)) {
                            monney = monney.multiply(new BigDecimal(numMultiple));
                            newValue = (monney).toString().split("\\.")[0];
                            logger.info("请求域转换:[{}]=[{}]-->[{}]=[{}]", oldName, oldValue, newName, newValue);
                        }

                    } else if ("java.util.Date".equals(type)) {
                        String dateFormat = column.getDateFormat();
                        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                        newValue = format.format((Date) oldValue);
                    } else {
                        newValue = String.valueOf(oldValue);
                    }

                } else {
                    newValue = String.valueOf(oldValue);
                }
            } else {
                if (StringUtils.isNotBlank(must) && "true".equals(must)) {
                    logger.info("参数[{}]不能为空，请检查", oldName);
                    throw new RuntimeException("参数[{" + oldName + "}]不能为空，请检查");
                }
            }
            resultMap.put(newName, newValue);

        }

        return resultMap;
    }

    
    
    /**
     * new交易前转换
     * 
     * @param paramMap
     * @param columnList
     * @return
     */
    public static Map<String, Object> newParseValueBeforeTrans(String transCode, Map<String, Object> paramMap,
            String transType) {
        logger.info("paramMap:[{}]", paramMap);
        if (paramMap == null || paramMap.size() == 0) {

            throw new RuntimeException("传入Map不能为空");
        }
        Data data = getTemplateType(transCode, transType);

        List<Column> columnList = data.getColumnList();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 转换变量名和类型

        for (Column column : columnList) {
            String oldName = column.getOldName();// paramMap中key
            String newName = column.getNewName();// resultMap中key
            String type = column.getType();
            String newValue = "";
            String must = column.getMust();
            if (paramMap.containsKey(oldName)) {
                Object oldValue = paramMap.get(oldName);

                if (StringUtils.isNotBlank(type)) {
                    if ("java.math.BigDecimal".equals(type)) {
                        BigDecimal monney = (BigDecimal) oldValue;
                        String numMultiple = column.getNumMultiple();
                        if (StringUtils.isNotBlank(numMultiple)) {
                            monney = monney.multiply(new BigDecimal(numMultiple));
                            newValue = (monney).toString().split("\\.")[0];
                            logger.info("请求域转换:[{}]=[{}]-->[{}]=[{}]", oldName, oldValue, newName, newValue);
                        }

                    } else if ("java.util.Date".equals(type)) {
                        String dateFormat = column.getDateFormat();
                        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                        newValue = format.format((Date) oldValue);
                    } else {
                        newValue = String.valueOf(oldValue);
                    }

                } else {
                    newValue = String.valueOf(oldValue);
                }
            } else {
                if (StringUtils.isNotBlank(must) && "true".equals(must)) {
                    logger.info("参数[{}]不能为空，请检查", oldName);
                    throw new RuntimeException("参数[{" + oldName + "}]不能为空，请检查");
                }
            }
            resultMap.put(newName, newValue);

        }

        return resultMap;
    }
    
    
    public static Map<String, Object> parseValueBeforeTransForAlipay(String transCode, Map<String, Object> paramMap,
            String transType) {
        logger.info("paramMap:[{}]", paramMap);
        if (paramMap == null || paramMap.size() == 0) {

            throw new RuntimeException("传入Map不能为空");
        }
        Data data = getTemplateType(transCode, transType);

        List<Column> columnList = data.getColumnList();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 转换变量名和类型
        for (Column column : columnList) {
        	convertData(resultMap, paramMap, column);
        }
        return resultMap;
    }
    

    
    public static void convertData(Map<String, Object> resultMap, Map<String, Object> paramMap, Column column){
    	String oldName = column.getOldName();// paramMap中key
        String newName = column.getNewName();// resultMap中key
        String type = column.getType();
        Object newValue = "";
        String must = column.getMust();
        if (paramMap.containsKey(oldName) || "Map_Not".equals(type)) {
            Object oldValue = paramMap.get(oldName);
            if (StringUtils.isNotBlank(type)) {
                if ("java.math.BigDecimal".equals(type)) {
                	if(StringUtils.isNotBlank(must) && "true".equals(must) && oldValue==null){
                		 logger.info("参数[{}]不能为空，请检查", oldName);
                         throw new RuntimeException("参数[{" + oldName + "}]不能为空，请检查");
                	}
//                	BigDecimal monney = new BigDecimal((String)oldValue);
                	newValue = oldValue.toString();

                } else if ("java.util.Date".equals(type)) {
                    String dateFormat = column.getDateFormat();
                    SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                    newValue = format.format((Date) oldValue);
                }else if("Map".equals(type)){
                	Map<String, Object> map = new HashMap<String, Object>();
                	List<Column> columnList = column.getColumnList();
                	Map<String, Object> valueMap =(Map<String, Object>) paramMap.get(oldName);
                	for (Column columnMap : columnList) {
                		convertData(map,valueMap,columnMap);
					}
                	newValue = map;
                }else if("Map_Not".equals(type)){//不需要定义一级Map，
                	Map<String, Object> map = new HashMap<String, Object>();
                	List<Column> columnList = column.getColumnList();
                	for (Column columnMap : columnList) {
                		convertData(map,paramMap,columnMap);
					}
                	
                	newValue = map;
                }else if("List".equals(type)){
                	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                	List<Column> columnList = column.getColumnList();
                	List<Map<String, Object>> valueList = (List<Map<String, Object>>)paramMap.get(oldName);
                	for (Map<String, Object> valueMap : valueList) {
                		Map<String, Object> map = new HashMap<String, Object>();
                		for (Column columnMap : columnList) {
                    		convertData(map,valueMap,columnMap);
    					}
                		list.add(map);
					}
                	newValue = list;
                }else if("byte".equals(type)){
                	newValue = oldValue;
                }else{
                	if(StringUtils.isBlank((String)oldValue)){
                		return;
                	}
                    newValue = String.valueOf(oldValue);
                }

            } else {
            	if(StringUtils.isBlank((String)oldValue)){
            		return;
            	}
            	newValue = String.valueOf(oldValue);
            }
            if(newValue != null){
            	resultMap.put(newName, newValue);
            }
        } else {
            if (StringUtils.isNotBlank(must) && "true".equals(must)) {
                logger.info("参数[{}]不能为空，请检查", oldName);
                throw new RuntimeException("参数[{" + oldName + "}]不能为空，请检查");
            }
        }
    }

    /**
     * 交易后转换
     * 
     * @param paramMap
     * @param columnList
     * @return
     */
    public static Map<String, Object> parseValueAfterTrans(String transCode, Map<String, String> paramMap,
            String transType) {
        if (paramMap == null || paramMap.size() == 0) {
            logger.info("paramMap:[{}]", paramMap);
            throw new RuntimeException("传入Map不能为空");
        }
        Data data = getTemplateType(transCode, transType);

        List<Column> columnList = data.getColumnList();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        for (Column column : columnList) {
            String oldName = column.getOldName();// paramMap中key
            String newName = column.getNewName();// resultMap中key
            String type = column.getType();
            Object newValue = null;
            String must = column.getMust();

            if (paramMap.containsKey(oldName)) {
                String oldValue = paramMap.get(oldName);

                if (StringUtils.isNotBlank(type)) {
                    String dateFormat = "";
                    if ("java.util.Date".equals(type)) {
                        dateFormat = column.getDateFormat();
                    }

                    try {
                        newValue = TypeUtils.getValue(type, dateFormat, oldValue);
                        if ("java.math.BigDecimal".equals(type)) {
                            String numMultiple = column.getNumMultiple();
                            if (StringUtils.isNotBlank(numMultiple)) {
                                newValue = ((BigDecimal) newValue).multiply(new BigDecimal(numMultiple));
                                logger.info("响应域转换:[{}]=[{}]-->[{}]=[{}]", oldName, oldValue, newName,
                                    newValue);
                            }
                        }
                    } catch (Exception e) {
                        logger.info("参数[{}]转换错误", oldName);
                        throw new RuntimeException("参数[{" + oldName + "}]转换错误");
                    }

                } else {
                    newValue = oldValue;
                }
                resultMap.put(newName, newValue);
            } else {
                if (StringUtils.isNotBlank(must) && "true".equals(must)) {
                    logger.info("参数[{}]不能为空，请检查", oldName);
                    throw new RuntimeException("参数[{" + oldName + "}]不能为空，请检查");
                }
            }
        }
        return resultMap;
    }

    public static class IllegalFileException extends RuntimeException {
        private static final long serialVersionUID = -4040593750097815090L;


        public IllegalFileException(String message) {
            super(message);
        }
    }

}
