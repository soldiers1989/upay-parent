/*     */ package com.upay.commons.csv;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CSVReader
/*     */   implements Closeable
/*     */ {
/*     */   private BufferedReader br;
/*  36 */   private boolean hasNext = true;
/*     */   
/*     */ 
/*     */ 
/*     */   private CSVParser parser;
/*     */   
/*     */ 
/*     */ 
/*     */   private int skipLines;
/*     */   
/*     */ 
/*     */   private boolean linesSkiped;
/*     */   
/*     */ 
/*     */   public static final int DEFAULT_SKIP_LINES = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public CSVReader(Reader reader)
/*     */   {
/*  56 */     this(reader, ',', '"', '\\');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CSVReader(Reader reader, char separator)
/*     */   {
/*  68 */     this(reader, separator, '"', '\\');
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
/*     */   public CSVReader(Reader reader, char separator, char quotechar)
/*     */   {
/*  82 */     this(reader, separator, quotechar, '\\', 0, false);
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
/*     */   public CSVReader(Reader reader, char separator, char quotechar, boolean strictQuotes)
/*     */   {
/*  99 */     this(reader, separator, quotechar, '\\', 0, strictQuotes);
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
/*     */   public CSVReader(Reader reader, char separator, char quotechar, char escape)
/*     */   {
/* 117 */     this(reader, separator, quotechar, escape, 0, false);
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
/*     */   public CSVReader(Reader reader, char separator, char quotechar, int line)
/*     */   {
/* 133 */     this(reader, separator, quotechar, '\\', line, false);
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
/*     */   public CSVReader(Reader reader, char separator, char quotechar, char escape, int line)
/*     */   {
/* 151 */     this(reader, separator, quotechar, escape, line, false);
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
/*     */   public CSVReader(Reader reader, char separator, char quotechar, char escape, int line, boolean strictQuotes)
/*     */   {
/* 171 */     this(reader, separator, quotechar, escape, line, strictQuotes, true);
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
/*     */ 
/*     */ 
/*     */   public CSVReader(Reader reader, char separator, char quotechar, char escape, int line, boolean strictQuotes, boolean ignoreLeadingWhiteSpace)
/*     */   {
/* 193 */     this.br = new BufferedReader(reader);
/* 194 */     this.parser = new CSVParser(separator, quotechar, escape, strictQuotes, ignoreLeadingWhiteSpace);
/* 195 */     this.skipLines = line;
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
/*     */   public List<String[]> readAll()
/*     */     throws IOException
/*     */   {
/* 210 */     List<String[]> allElements = new ArrayList();
/* 211 */     while (this.hasNext) {
/* 212 */       String[] nextLineAsTokens = readNext();
/* 213 */       if (nextLineAsTokens != null)
/* 214 */         allElements.add(nextLineAsTokens);
/*     */     }
/* 216 */     return allElements;
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
/*     */   public String[] readNext()
/*     */     throws IOException
/*     */   {
/* 231 */     String[] result = null;
/*     */     do {
/* 233 */       String nextLine = getNextLine();
/* 234 */       if (!this.hasNext) {
/* 235 */         return result;
/*     */       }
/* 237 */       String[] r = this.parser.parseLineMulti(nextLine);
/* 238 */       if (r.length > 0) {
/* 239 */         if (result == null) {
/* 240 */           result = r;
/*     */         } else {
/* 242 */           String[] t = new String[result.length + r.length];
/* 243 */           System.arraycopy(result, 0, t, 0, result.length);
/* 244 */           System.arraycopy(r, 0, t, result.length, r.length);
/* 245 */           result = t;
/*     */         }
/*     */       }
/* 248 */     } while (this.parser.isPending());
/* 249 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String getNextLine()
/*     */     throws IOException
/*     */   {
/* 260 */     if (!this.linesSkiped) {
/* 261 */       for (int i = 0; i < this.skipLines; i++) {
/* 262 */         this.br.readLine();
/*     */       }
/* 264 */       this.linesSkiped = true;
/*     */     }
/* 266 */     String nextLine = this.br.readLine();
/* 267 */     if (nextLine == null) {
/* 268 */       this.hasNext = false;
/*     */     }
/* 270 */     return this.hasNext ? nextLine : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 279 */     this.br.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void read(CSVReadProc proc)
/*     */   {
/*     */     try
/*     */     {
/* 291 */       int rowIndex = 0;
/* 292 */       for (String[] values = readNext(); values != null; values = readNext()) {
/* 293 */         proc.procRow(rowIndex++, values);
/*     */       }
/*     */     } catch (IOException e) {
/* 296 */       throw new CSVRuntimeException(e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\CSVReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */