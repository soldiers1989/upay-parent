package com.upay.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.dao.IBaseDao;
import com.upay.dao.po.gnr.GnrHolidayPo;


/**
 * 平台节假日数据初始化
 * 
 * @author Guo
 *
 */
public class PlatFormHolidayContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlatFormHolidayContext.class);

    private static IBaseDao daoService;

    // 缓存
    // private static Map<String,Object> calendarMap = new HashMap<String,
    // Object>();
    private static Map<String, Map<String, Boolean>> calendarMap = new HashMap<>();


    /**
     * 初始化
     */
    static void init() {
        if (calendarMap.isEmpty()) {
            synchronized (calendarMap) {
                if (calendarMap.isEmpty()) {
                    // 当前年份节假日
                    Date sysDate = new Date();
                    List<GnrHolidayPo> sysList = selectDateList(sysDate);
                    if (sysList.size() > 0) {
                        putHoliday(sysList);
                    }

                    // 下一年份节假日
                    Date nextYear = add(sysDate, Calendar.YEAR, 1); // 获取当前时间的下一年
                    List<GnrHolidayPo> nextYearList = selectDateList(nextYear);
                    if (nextYearList.size() > 0) {
                        putHoliday(nextYearList);
                    }
                }
            }
        }
    }


    /**
     * 刷新calendarMap缓存
     * 
     */
    public static void refresh() {
        if (!calendarMap.isEmpty()) {
            calendarMap.clear(); // 清空 calendarMap
            LOGGER.info("calendarMap是否真的被清空 ：[{}]", calendarMap.isEmpty());
            // 当前年份节假日
            Date sysDate = new Date();
            List<GnrHolidayPo> sysDatelist = selectDateList(sysDate);
            if (sysDatelist.size() > 0) {
                putHoliday(sysDatelist);
            }

            // 下一年份节假日
            Date nextYear = add(sysDate, Calendar.YEAR, 1); // 获取当前时间的下一年
            LOGGER.info("nextYearStr : [{}]", format(nextYear, "yyyy"));
            List<GnrHolidayPo> nextYearlist = selectDateList(nextYear);
            if (nextYearlist.size() > 0) {
                putHoliday(nextYearlist);
            }
        }
    }


    /**
     * 查询指定产品，指定年的节假日
     * 
     * @param prdCode
     * @param year
     */
    private static List<GnrHolidayPo> selectDateList(String prdCode, Date year) {
        GnrHolidayPo finHolidayPo = new GnrHolidayPo();
        finHolidayPo.setYear(format(year, "yyyy"));
        finHolidayPo.setProdCode(prdCode);
        List<GnrHolidayPo> sysDatelist = daoService.selectList(finHolidayPo);
        return sysDatelist;
    }


    /**
     * 查询指定年的节假日
     * 
     * @param year
     */
    private static List<GnrHolidayPo> selectDateList(Date year) {
        GnrHolidayPo finHolidayPo = new GnrHolidayPo();
        finHolidayPo.setYear(format(year, "yyyy"));
        List<GnrHolidayPo> sysDatelist = daoService.selectList(finHolidayPo);
        return sysDatelist;
    }


    /**
     * 将指定产品代码的节假日放入calendarMap
     * 
     * @param holidays
     */
    private static void putHoliday(List<GnrHolidayPo> holidays) {
        for (GnrHolidayPo holiday : holidays) {
            putHoliday(holiday);
        }
    }


    /**
     * 将指定产品代码的节假日放入calendarMap
     * 
     * @param holiday
     */
    private static void putHoliday(GnrHolidayPo holiday) {

        String prdCode = holiday.getProdCode();
        Map<String, Boolean> holidayMap = calendarMap.get(prdCode);
        if (holidayMap == null) {
            holidayMap = new HashMap<String, Boolean>();
            calendarMap.put(prdCode, holidayMap);
        }

        if (!holidayMap.containsKey(format(holiday.getHolidayDate(), "yyyyMMdd"))) {
            holidayMap.put(format(holiday.getHolidayDate(), "yyyyMMdd"), Boolean.TRUE);// 是否节假日
            LOGGER.info("putHoliday : [{}], [{}]", format(holiday.getHolidayDate(), "yyyyMMdd"),
                holiday.getProdCode());
        }
    }


    /**
     * 将指定产品代码的节假日放入calendarMap
     * 
     * @param prdCode
     * @param list
     */
    private static void putHoliday(String prdCode, List<GnrHolidayPo> list) {

        Map<String, Boolean> tempMap = new HashMap<>();
        for (GnrHolidayPo holidayPo : list) {
            if (!tempMap.containsKey(format(holidayPo.getHolidayDate(), "yyyyMMdd"))) {
                tempMap.put(format(holidayPo.getHolidayDate(), "yyyyMMdd"), Boolean.TRUE);// 是否节假日
                LOGGER.info("putHoliday : [{}], [{}]", format(holidayPo.getHolidayDate(), "yyyyMMdd"),
                    holidayPo.getProdCode());
            }
        }
        calendarMap.put(prdCode, tempMap);
    }


    /**
     * 移除指定产品和年份的map
     * 
     * @param prodCode
     * @param year
     */
    private static void removeHoliday(String prodCode, String year) {
        Map<String, Boolean> map = calendarMap.get(prodCode);// 获取产品所有节假日列表
        if (map == null) {
            return;
        }
        GnrHolidayPo finHolidayPo = new GnrHolidayPo();
        finHolidayPo.setYear(year);
        finHolidayPo.setProdCode(prodCode);
        List<GnrHolidayPo> sysDatelist = daoService.selectList(finHolidayPo);
        if (sysDatelist.size() > 0) {
            for (GnrHolidayPo holidayPo : sysDatelist) {
                if (map.containsKey(format(holidayPo.getHolidayDate(), "yyyyMMdd"))) {
                    map.remove(format(holidayPo.getHolidayDate(), "yyyyMMdd"));
                    LOGGER.info("removeHoliday : [{}], [{}]", format(holidayPo.getHolidayDate(), "yyyyMMdd"),
                        holidayPo.getProdCode());
                }
            }
        }

    }


    /**
     * 判断是否工作日</br>
     * 节假日-false, 工作日-true
     * 
     * @param date
     * @return
     */
    // @Deprecated
    // public static boolean isWorkDay(Date date) {
    // boolean flag = Boolean.FALSE;
    // String dateStr = format(date, "yyyyMMdd");
    // Object prodCode = calendarMap.get(dateStr);
    // if (prodCode == null) {
    // FinHolidayPo finHolidayPo = new FinHolidayPo();
    // finHolidayPo.setHolidayDate(date);
    // finHolidayPo.setYear(format(date, "yyyy"));
    // finHolidayPo = daoService.selectOne(finHolidayPo);
    // if(finHolidayPo != null) {
    // calendarMap.put(dateStr, finHolidayPo.getProdCode());
    // } else {
    // flag = true;
    // }
    // }
    // return flag;
    // }

    /**
     * 判断是否工作日</br>
     * 节假日-false, 工作日-true
     * 
     * @param date
     * @param prodCode
     * @return
     */
    public static boolean isWorkDay(Date date, String prodCode) {
    	refresh();
    	init();
        boolean flag = Boolean.FALSE;
        Map<String, Boolean> map = calendarMap.get(prodCode);// 获取产品所有节假日列表
        if (map == null) {
            return true;
        }
        String dateStr = format(date, "yyyyMMdd");
        Object obj = map.get(dateStr);
        if (obj != null && (Boolean) obj) {
            return flag;
        } else {
            // FinHolidayPo finHolidayPo = new FinHolidayPo();
            // finHolidayPo.setHolidayDate(date);
            // finHolidayPo.setYear(format(date, "yyyy"));
            // finHolidayPo.setProdCode(prodCode);
            // finHolidayPo = daoService.selectOne(finHolidayPo);
            // if(finHolidayPo != null) {
            // map.put(dateStr, Boolean.TRUE); //是节假日
            // calendarMap.put(prodCode, map);
            // } else {
            flag = true;
            // }
        }
        return flag;
    }


    /**
     * 获取下一个工作日
     * 
     * @param currDate
     *            当前日期
     * @return
     */
    // @Deprecated
    // public static Date getNextWorkDay(Date currDate) {
    // Date nextDay = add(currDate, Calendar.DAY_OF_MONTH, 1);//加一天
    // while (!isWorkDay(nextDay)) {
    // nextDay = add(nextDay, Calendar.DAY_OF_MONTH, 1);
    // }
    // return nextDay;
    // }

    /**
     * 获取下一个工作日
     * 
     * @param currDate
     *            当前日期
     * @param prodCode
     *            产品代码
     * @return
     */
    public static Date getNextWorkDay(Date currDate, String prodCode) {
        Date nextDay = add(currDate, Calendar.DAY_OF_MONTH, 1);// 加一天
        while (!isWorkDay(nextDay, prodCode)) {
            nextDay = add(nextDay, Calendar.DAY_OF_MONTH, 1);
        }
        return nextDay;
    }


    /**
     * add(Calendar.DAY_OF_MONTH, -5)
     *
     * @param date
     * @param calendorField
     * @param amount
     * @return
     */
    private static Date add(Date date, int calendorField, int amount) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(calendorField, amount);

        return cal.getTime();
    }


    private static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    public static void setDaoService(IBaseDao daoService) {
        PlatFormHolidayContext.daoService = daoService;
    }

}
