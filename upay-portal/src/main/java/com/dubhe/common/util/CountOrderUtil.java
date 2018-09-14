package com.dubhe.common.util;

import javax.servlet.http.HttpServletRequest;


/**
 * User: weizhao.dong desc:
 */
public class CountOrderUtil {
    public static CountOrder getCountOrder(HttpServletRequest request) {
        String start = request.getParameter("page");
        String limit = request.getParameter("rows");
        // sort:positionName,positionNo
        // order:asc
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        CountOrder countOrder = new CountOrder();
        int startInt = 0;
        if (G4Utils.isNotEmpty(start)) {
        	//startInt = Integer.parseInt(start)-1;
            startInt = Integer.parseInt(start);
            countOrder.setStart(startInt);
            
        }
        if (G4Utils.isNotEmpty(limit)) {
        	
            int limitInt = Integer.parseInt(limit);
            countOrder.setLimit(limitInt);
            countOrder.setEnd(startInt + limitInt);
            
            countOrder.setMysqlstart((startInt-1)*limitInt);
        }
        countOrder.setOrder(order);
        countOrder.setSort(sort);
        return countOrder;
    }
}
