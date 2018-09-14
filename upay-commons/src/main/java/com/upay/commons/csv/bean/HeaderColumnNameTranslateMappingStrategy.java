/*    */ package com.upay.commons.csv.bean;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class HeaderColumnNameTranslateMappingStrategy<T>
/*    */   extends HeaderColumnNameMappingStrategy<T>
/*    */ {
/* 22 */   private Map<String, String> columnMapping = new HashMap();
/*    */   
/* 24 */   protected String getColumnName(int col) { return col < this.header.length ? (String)this.columnMapping.get(this.header[col].toUpperCase()) : null; }
/*    */   
/*    */ 
/* 27 */   public Map<String, String> getColumnMapping() { return this.columnMapping; }
/*    */   
/*    */   public void setColumnMapping(Map<String, String> columnMapping) {
/* 30 */     for (String key : columnMapping.keySet()) {
/* 31 */       this.columnMapping.put(key.toUpperCase(), columnMapping.get(key));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\bean\HeaderColumnNameTranslateMappingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */