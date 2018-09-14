/*    */ package com.upay.commons.csv.bean;
/*    */ 
/*    */ import com.upay.commons.csv.CSVReader;
/*    */ import java.beans.BeanInfo;
/*    */ import java.beans.IntrospectionException;
/*    */ import java.beans.Introspector;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ 
/*    */ public class HeaderColumnNameMappingStrategy<T>
/*    */   implements MappingStrategy<T>
/*    */ {
/*    */   protected String[] header;
/* 31 */   protected Map<String, PropertyDescriptor> descriptorMap = null;
/*    */   protected Class<T> type;
/*    */   
/*    */   public void captureHeader(CSVReader reader) throws IOException {
/* 35 */     this.header = reader.readNext();
/*    */   }
/*    */   
/*    */   public PropertyDescriptor findDescriptor(int col) throws IntrospectionException {
/* 39 */     String columnName = getColumnName(col);
/* 40 */     return (null != columnName) && (columnName.trim().length() > 0) ? findDescriptor(columnName) : null;
/*    */   }
/*    */   
/*    */   protected String getColumnName(int col) {
/* 44 */     return (null != this.header) && (col < this.header.length) ? this.header[col] : null;
/*    */   }
/*    */   
/*    */   protected PropertyDescriptor findDescriptor(String name) throws IntrospectionException {
/* 48 */     if (null == this.descriptorMap) this.descriptorMap = loadDescriptorMap(getType());
/* 49 */     return (PropertyDescriptor)this.descriptorMap.get(name.toUpperCase().trim());
/*    */   }
/*    */   
/*    */   protected boolean matches(String name, PropertyDescriptor desc) {
/* 53 */     return desc.getName().equals(name.trim());
/*    */   }
/*    */   
/*    */   protected Map<String, PropertyDescriptor> loadDescriptorMap(Class<T> cls) throws IntrospectionException {
/* 57 */     Map<String, PropertyDescriptor> map = new HashMap();
/*    */     
/*    */ 
/* 60 */     PropertyDescriptor[] descriptors = loadDescriptors(getType());
/* 61 */     for (PropertyDescriptor descriptor : descriptors) {
/* 62 */       map.put(descriptor.getName().toUpperCase().trim(), descriptor);
/*    */     }
/*    */     
/* 65 */     return map;
/*    */   }
/*    */   
/*    */   private PropertyDescriptor[] loadDescriptors(Class<T> cls) throws IntrospectionException {
/* 69 */     BeanInfo beanInfo = Introspector.getBeanInfo(cls);
/* 70 */     return beanInfo.getPropertyDescriptors();
/*    */   }
/*    */   
/*    */   public T createBean() throws InstantiationException, IllegalAccessException {
/* 74 */     return (T)this.type.newInstance();
/*    */   }
/*    */   
/*    */   public Class<T> getType() {
/* 78 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(Class<T> type) {
/* 82 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\bean\HeaderColumnNameMappingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */