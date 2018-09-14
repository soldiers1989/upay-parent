package com.upay.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrintDate {

	 public static void main(String[] args) throws ParseException {
	        DateFormat df = new SimpleDateFormat("yyyy/M/d");
	        Date date = df.parse("2018/1/1");
	        int day = date.getDay(); 
	        int startSatOffset = 6-day;
	        if(day==0){
	            System.out.println("此年的第一天是星期天");
	        }
	        for(int i=0;i<=365/7;i++){
	            Date satday = df.parse("2018/1/"+(1+startSatOffset+i*7));
	            Date sunday = df.parse("2018/1/"+(1+startSatOffset+(i*7+1)));
	            System.out.println(df.format(satday));
	            System.out.println(df.format(sunday));
	        }
	         
	    }
}
