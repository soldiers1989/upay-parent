/*     */ package com.upay.commons.csv;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Writer;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ public class CSVWriter
/*     */   implements Closeable
/*     */ {
/*     */   public static final int INITIAL_STRING_SIZE = 128;
/*     */   private Writer rawWriter;
/*     */   private PrintWriter pw;
/*     */   private char separator;
/*     */   private char quotechar;
/*     */   private char escapechar;
/*     */   private String lineEnd;
/*     */   public static final char DEFAULT_ESCAPE_CHARACTER = '"';
/*     */   public static final char DEFAULT_SEPARATOR = ',';
/*     */   public static final char DEFAULT_QUOTE_CHARACTER = '"';
/*     */   public static final char NO_QUOTE_CHARACTER = '\000';
/*     */   public static final char NO_ESCAPE_CHARACTER = '\000';
/*     */   public static final String DEFAULT_LINE_END = "\n";
/*  70 */   private ResultSetHelper resultService = new ResultSetHelperService();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CSVWriter(Writer writer)
/*     */   {
/*  79 */     this(writer, ',');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CSVWriter(Writer writer, char separator)
/*     */   {
/*  91 */     this(writer, separator, '"');
/*     */   }
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
/*     */   public CSVWriter(Writer writer, char separator, char quotechar)
/*     */   {
/* 105 */     this(writer, separator, quotechar, '"');
/*     */   }
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
/*     */   public CSVWriter(Writer writer, char separator, char quotechar, char escapechar)
/*     */   {
/* 121 */     this(writer, separator, quotechar, escapechar, "\n");
/*     */   }
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
/*     */   public CSVWriter(Writer writer, char separator, char quotechar, String lineEnd)
/*     */   {
/* 138 */     this(writer, separator, quotechar, '"', lineEnd);
/*     */   }
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
/*     */   public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd)
/*     */   {
/* 158 */     this.rawWriter = writer;
/* 159 */     this.pw = new PrintWriter(writer);
/* 160 */     this.separator = separator;
/* 161 */     this.quotechar = quotechar;
/* 162 */     this.escapechar = escapechar;
/* 163 */     this.lineEnd = lineEnd;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeAll(List<String[]> allLines)
/*     */   {
/* 175 */     for (String[] line : allLines) {
/* 176 */       writeNext(line);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void writeColumnNames(ResultSet rs)
/*     */     throws SQLException
/*     */   {
/* 183 */     writeNext(this.resultService.getColumnNames(rs));
/*     */   }
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
/*     */   public void writeAll(ResultSet rs, boolean includeColumnNames)
/*     */     throws SQLException, IOException
/*     */   {
/* 200 */     if (includeColumnNames) {
/* 201 */       writeColumnNames(rs);
/*     */     }
/*     */     
/* 204 */     while (rs.next())
/*     */     {
/* 206 */       writeNext(this.resultService.getColumnValues(rs));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeNext(String... nextLine)
/*     */   {
/* 220 */     if (nextLine == null) {
/* 221 */       return;
/*     */     }
/* 223 */     StringBuilder sb = new StringBuilder(128);
/* 224 */     for (int i = 0; i < nextLine.length; i++)
/*     */     {
/* 226 */       if (i != 0) {
/* 227 */         sb.append(this.separator);
/*     */       }
/*     */       
/* 230 */       String nextElement = nextLine[i];
/* 231 */       if (nextElement != null)
/*     */       {
/* 233 */         if (this.quotechar != 0) {
/* 234 */           sb.append(this.quotechar);
/*     */         }
/* 236 */         sb.append((CharSequence)(stringContainsSpecialCharacters(nextElement) ? processLine(nextElement) : nextElement));
/*     */         
/* 238 */         if (this.quotechar != 0)
/* 239 */           sb.append(this.quotechar);
/*     */       }
/*     */     }
/* 242 */     sb.append(this.lineEnd);
/* 243 */     this.pw.write(sb.toString());
/*     */   }
/*     */   
/*     */   private boolean stringContainsSpecialCharacters(String line)
/*     */   {
/* 248 */     return (line.indexOf(this.quotechar) != -1) || (line.indexOf(this.escapechar) != -1);
/*     */   }
/*     */   
/*     */   protected StringBuilder processLine(String nextElement)
/*     */   {
/* 253 */     StringBuilder sb = new StringBuilder(128);
/* 254 */     for (int j = 0; j < nextElement.length(); j++) {
/* 255 */       char nextChar = nextElement.charAt(j);
/* 256 */       if ((this.escapechar != 0) && (nextChar == this.quotechar)) {
/* 257 */         sb.append(this.escapechar).append(nextChar);
/* 258 */       } else if ((this.escapechar != 0) && (nextChar == this.escapechar)) {
/* 259 */         sb.append(this.escapechar).append(nextChar);
/*     */       } else {
/* 261 */         sb.append(nextChar);
/*     */       }
/*     */     }
/*     */     
/* 265 */     return sb;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 275 */     this.pw.flush();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 286 */     flush();
/* 287 */     this.pw.close();
/* 288 */     this.rawWriter.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean checkError()
/*     */   {
/* 295 */     return this.pw.checkError();
/*     */   }
/*     */   
/*     */   public void setResultService(ResultSetHelper resultService) {
/* 299 */     this.resultService = resultService;
/*     */   }
/*     */   
/*     */   public void write(CSVWriteProc proc) {
/* 303 */     proc.process(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\CSVWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */