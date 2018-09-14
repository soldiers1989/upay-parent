/*     */ package com.upay.commons.csv;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResultSetHelperService
/*     */   implements ResultSetHelper
/*     */ {
/*     */   public static final int CLOBBUFFERSIZE = 2048;
/*     */   private static final int NVARCHAR = -9;
/*     */   private static final int NCHAR = -15;
/*     */   private static final int LONGNVARCHAR = -16;
/*     */   private static final int NCLOB = 2011;
/*     */   
/*     */   public String[] getColumnNames(ResultSet rs)
/*     */     throws SQLException
/*     */   {
/*  43 */     List<String> names = new ArrayList();
/*  44 */     ResultSetMetaData metadata = rs.getMetaData();
/*     */     
/*  46 */     for (int i = 0; i < metadata.getColumnCount(); i++) {
/*  47 */       names.add(metadata.getColumnName(i + 1));
/*     */     }
/*     */     
/*  50 */     String[] nameArray = new String[names.size()];
/*  51 */     return (String[])names.toArray(nameArray);
/*     */   }
/*     */   
/*     */   public String[] getColumnValues(ResultSet rs) throws SQLException, IOException
/*     */   {
/*  56 */     List<String> values = new ArrayList();
/*  57 */     ResultSetMetaData metadata = rs.getMetaData();
/*     */     
/*  59 */     for (int i = 0; i < metadata.getColumnCount(); i++) {
/*  60 */       values.add(getColumnValue(rs, metadata.getColumnType(i + 1), i + 1));
/*     */     }
/*     */     
/*  63 */     String[] valueArray = new String[values.size()];
/*  64 */     return (String[])values.toArray(valueArray);
/*     */   }
/*     */   
/*     */   private String handleObject(Object obj) {
/*  68 */     return obj == null ? "" : String.valueOf(obj);
/*     */   }
/*     */   
/*     */   private String handleBigDecimal(BigDecimal decimal) {
/*  72 */     return decimal == null ? "" : decimal.toString();
/*     */   }
/*     */   
/*     */   private String handleLong(ResultSet rs, int columnIndex) throws SQLException {
/*  76 */     long lv = rs.getLong(columnIndex);
/*  77 */     return rs.wasNull() ? "" : Long.toString(lv);
/*     */   }
/*     */   
/*     */   private String handleInteger(ResultSet rs, int columnIndex) throws SQLException {
/*  81 */     int i = rs.getInt(columnIndex);
/*  82 */     return rs.wasNull() ? "" : Integer.toString(i);
/*     */   }
/*     */   
/*     */   private String handleDate(ResultSet rs, int columnIndex) throws SQLException {
/*  86 */     Date date = rs.getDate(columnIndex);
/*  87 */     String value = null;
/*  88 */     if (date != null) {
/*  89 */       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
/*  90 */       value = dateFormat.format(date);
/*     */     }
/*  92 */     return value;
/*     */   }
/*     */   
/*     */   private String handleTime(Time time) {
/*  96 */     return time == null ? null : time.toString();
/*     */   }
/*     */   
/*     */   private String handleTimestamp(Timestamp timestamp) {
/* 100 */     SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
/* 101 */     return timestamp == null ? null : timeFormat.format(timestamp);
/*     */   }
/*     */   
/*     */   private String getColumnValue(ResultSet rs, int colType, int colIndex)
/*     */     throws SQLException, IOException
/*     */   {
/* 107 */     String value = "";
/*     */     
/* 109 */     switch (colType)
/*     */     {
/*     */     case -7: 
/*     */     case 2000: 
/* 113 */       value = handleObject(rs.getObject(colIndex));
/* 114 */       break;
/*     */     case 16: 
/* 116 */       boolean b = rs.getBoolean(colIndex);
/* 117 */       value = Boolean.valueOf(b).toString();
/* 118 */       break;
/*     */     case 2005: 
/*     */     case 2011: 
/* 121 */       Clob c = rs.getClob(colIndex);
/* 122 */       if (c != null) {
/* 123 */         value = read(c);
/*     */       }
/*     */       break;
/*     */     case -5: 
/* 127 */       value = handleLong(rs, colIndex);
/* 128 */       break;
/*     */     case 2: 
/*     */     case 3: 
/*     */     case 6: 
/*     */     case 7: 
/*     */     case 8: 
/* 134 */       value = handleBigDecimal(rs.getBigDecimal(colIndex));
/* 135 */       break;
/*     */     case -6: 
/*     */     case 4: 
/*     */     case 5: 
/* 139 */       value = handleInteger(rs, colIndex);
/* 140 */       break;
/*     */     case 91: 
/* 142 */       value = handleDate(rs, colIndex);
/* 143 */       break;
/*     */     case 92: 
/* 145 */       value = handleTime(rs.getTime(colIndex));
/* 146 */       break;
/*     */     case 93: 
/* 148 */       value = handleTimestamp(rs.getTimestamp(colIndex));
/* 149 */       break;
/*     */     case -16: 
/*     */     case -15: 
/*     */     case -9: 
/*     */     case -1: 
/*     */     case 1: 
/*     */     case 12: 
/* 156 */       value = rs.getString(colIndex);
/* 157 */       break;
/*     */     default: 
/* 159 */       value = "";
/*     */     }
/*     */     
/*     */     
/* 163 */     if (value == null)
/*     */     {
/* 165 */       value = "";
/*     */     }
/*     */     
/* 168 */     return value;
/*     */   }
/*     */   
/*     */   private static String read(Clob c)
/*     */     throws SQLException, IOException
/*     */   {
/* 174 */     StringBuilder sb = new StringBuilder((int)c.length());
/* 175 */     Reader r = c.getCharacterStream();
/* 176 */     char[] cbuf = new char['ࠀ'];
/*     */     int n;
/* 178 */     while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
/* 179 */       sb.append(cbuf, 0, n);
/*     */     }
/* 181 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\ResultSetHelperService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */