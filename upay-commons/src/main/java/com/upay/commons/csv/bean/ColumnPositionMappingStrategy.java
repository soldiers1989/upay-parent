/*    */ package com.upay.commons.csv.bean;
/*    */ 
/*    */ import com.upay.commons.csv.CSVReader;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColumnPositionMappingStrategy<T>
/*    */   extends HeaderColumnNameMappingStrategy<T>
/*    */ {
/* 24 */   private String[] columnMapping = new String[0];
/*    */   
/*    */   public void captureHeader(CSVReader reader) throws IOException
/*    */   {}
/*    */   
/* 29 */   protected String getColumnName(int col) { return (null != this.columnMapping) && (col < this.columnMapping.length) ? this.columnMapping[col] : null; }
/*    */   
/*    */   public String[] getColumnMapping() {
/* 32 */     return this.columnMapping != null ? (String[])this.columnMapping.clone() : null;
/*    */   }
/*    */   
/* 35 */   public void setColumnMapping(String[] columnMapping) { this.columnMapping = (columnMapping != null ? (String[])columnMapping.clone() : null); }
/*    */ }


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\bean\ColumnPositionMappingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */