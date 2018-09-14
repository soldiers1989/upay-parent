package com.dubhe.common.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.BeanCopyUtil;


public class MessageConvertUtil {

    /**
     * 将message的target.bodys指定的一个类型为List<Map>的数据转换成List<T>
     * 
     * @param m
     * @param key
     *            在message target.bodys 这个map里面的key值
     * @param clazz
     *            要转换的类型
     */
    @SuppressWarnings("unchecked")
    public static <T> void convertList(Message m, String key, Class<T> clazz) {

        if (m.getTarget().getBodys() instanceof Map) {

            Map<String, Object> body = ((Map<String, Object>) m.getTarget().getBodys());
            Object obj = body.get(key);

            if (obj != null && obj instanceof List) {
                List<Map<String, Object>> oriList = (List<Map<String, Object>>) obj;
                List<T> targetList = new ArrayList<T>();
                for (Map<String, Object> map : oriList) {
                    try {
                        T instance = clazz.newInstance();
                        BeanCopyUtil.copyMap2Bean(map, instance);
                        targetList.add(instance);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                body.put(key, targetList);
            }
        }
    }


    /** map转换成dto , 递归的list */
    @SuppressWarnings("unchecked")
    public static <T> void convertList(Map<String, Object> mapIncludeList, String key, Class<T> clazz) {
        if (mapIncludeList != null) {
            Object obj = mapIncludeList.get(key);

            if (obj != null && obj instanceof List) {
                List<Map<String, Object>> oriList = (List<Map<String, Object>>) obj;
                List<T> targetList = new ArrayList<T>();
                for (Map<String, Object> map : oriList) {
                    convertList(map, key, clazz);
                    try {
                        T instance = clazz.newInstance();
                        BeanCopyUtil.copyMap2Bean(map, instance);
                        targetList.add(instance);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                mapIncludeList.put(key, targetList);
            }
        }
    }


    public static void main(String[] args) throws ParseException {
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("startDate", DateUtils.parseDate("141516", new String[] { "yyMMdd" }));

        List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("acctNo", "in map acct no");
        subList.add(map);
        bodys.put("depSaviDtlList", subList);

        Message m =
                MessageFactory.create("1111", MessageFactory.createSimpleMessageInstance(),
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(), bodys),
                    FaultFactory.create("0000000000", ""), new ArrayList<Store>());

        /*
         * convertList(m, "depSaviDtlList", DEP00005SubRsp.class);
         * 
         * DEP00005Rsp aft =
         * BaseMappingUtils.populateTbyDByApache(m.getTarget().getBodys(),
         * DEP00005Rsp.class); System.out.println(aft);
         * System.out.println("...");
         */
    }
}
