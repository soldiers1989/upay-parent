/*     */ package com.upay.commons.csv.bean;
/*     */ 
/*     */

import com.upay.commons.csv.CSVReader;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
/*     */ public class CsvToBean<T>
/*     */ {
/*  33 */   private Map<Class<?>, PropertyEditor> editorMap = null;
/*     */
/*     */ 
/*     */ 
/*     */   public List<T> parse(MappingStrategy<T> mapper, Reader reader)
/*     */   {
/*  39 */     return parse(mapper, new CSVReader(reader));
/*     */   }
/*     */

   // private static final Logger LOGGER = LoggerFactory
           // .getLogger(CsvToBean.class);
/*     */   public List<T> parse(MappingStrategy<T> mapper, CSVReader csv) {

      // LOGGER.info("parse========解析 begin");

/*     */     try {
/*  44 */       mapper.captureHeader(csv);
/*     */      // LOGGER.info("parse========captureHeader");
/*  46 */       List<T> list = new ArrayList();
/*  47 */       String[] line; while (null != (line = csv.readNext())&& StringUtils.isNotBlank(line[0])) {
           // LOGGER.info("parse========processLine begin"+line);
/*  48 */         T obj = processLine(mapper, line);
           // LOGGER.info("parse========processLine end"+line);
/*  49 */         list.add(obj);
/*     */       }
            //  LOGGER.info("parse========captureHeader"+list);
/*  51 */       return list;
/*     */     } catch (Exception e) {
/*  53 */       throw new RuntimeException("Error parsing CSV!", e);
/*     */     }
/*     */   }
/*     */
/*     */   protected T processLine(MappingStrategy<T> mapper, String[] line) throws IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
/*  58 */  //  LOGGER.info("parse========processLine begin"+line);
         T bean = mapper.createBean();
/*  59 */     for (int col = 0; col < line.length; col++) {
/*  60 */       PropertyDescriptor prop = mapper.findDescriptor(col);
/*  61 */       if (null != prop) {
/*  62 */         String value = checkForTrim(line[col], prop);
/*  63 */         Object obj = convertValue(value, prop);
/*  64 */         prop.getWriteMethod().invoke(bean, new Object[] { obj });
/*     */       }
/*     */     }
           //LOGGER.info("parse========processLine begin"+bean);
/*  67 */     return bean;
/*     */   }
/*     */
/*     */   private String checkForTrim(String s, PropertyDescriptor prop) {
/*  71 */     return trimmableProperty(prop) ? s.trim() : s;
/*     */   }
/*     */
/*     */   private boolean trimmableProperty(PropertyDescriptor prop) {
/*  75 */     return !prop.getPropertyType().getName().contains("String");
/*     */   }
/*     */
/*     */   protected Object convertValue(String value, PropertyDescriptor prop) throws InstantiationException, IllegalAccessException {
/*  79 */     PropertyEditor editor = getPropertyEditor(prop);
/*  80 */     Object obj = value;
/*  81 */     if (null != editor) {
/*  82 */       editor.setAsText(value);
/*  83 */       obj = editor.getValue();
/*     */     }
/*  85 */     return obj;
/*     */   }
/*     */
/*     */   private PropertyEditor getPropertyEditorValue(Class<?> cls) {
/*  89 */     if (this.editorMap == null) {
/*  90 */       this.editorMap = new HashMap();
/*     */     }
/*     */     
/*  93 */     PropertyEditor editor = (PropertyEditor)this.editorMap.get(cls);
/*     */     
/*  95 */     if (editor == null) {
/*  96 */       editor = PropertyEditorManager.findEditor(cls);
/*  97 */       addEditorToMap(cls, editor);
/*     */     }
/*     */     
/* 100 */     return editor;
/*     */   }
/*     */
/*     */   private void addEditorToMap(Class<?> cls, PropertyEditor editor) {
/* 104 */     if (editor != null) {
/* 105 */       this.editorMap.put(cls, editor);
/*     */     }
/*     */   }
/*     */
/*     */ 
/*     */ 
/*     */   protected PropertyEditor getPropertyEditor(PropertyDescriptor desc)
/*     */     throws InstantiationException, IllegalAccessException
/*     */   {
/* 114 */     Class<?> cls = desc.getPropertyEditorClass();
/* 115 */     if (null != cls) return (PropertyEditor)cls.newInstance();
/* 116 */     return getPropertyEditorValue(desc.getPropertyType());
/*     */   }
/*     */ }


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\bean\CsvToBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */