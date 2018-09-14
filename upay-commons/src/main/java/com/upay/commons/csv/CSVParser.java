/*     */ package com.upay.commons.csv;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CSVParser
/*     */ {
/*     */   private final char separator;
/*     */   private final char quotechar;
/*     */   private final char escape;
/*     */   private final boolean strictQuotes;
/*     */   private String pending;
/*  41 */   private boolean inField = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final boolean ignoreLeadingWhiteSpace;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final char DEFAULT_SEPARATOR = ',';
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int INITIAL_READ_SIZE = 128;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final char DEFAULT_QUOTE_CHARACTER = '"';
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final boolean DEFAULT_STRICT_QUOTES = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final boolean DEFAULT_IGNORE_LEADING_WHITESPACE = true;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final char NULL_CHARACTER = '\000';
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CSVParser()
/*     */   {
/*  87 */     this(',', '"', '\\');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CSVParser(char separator)
/*     */   {
/*  96 */     this(separator, '"', '\\');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CSVParser(char separator, char quotechar)
/*     */   {
/* 107 */     this(separator, quotechar, '\\');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CSVParser(char separator, char quotechar, char escape)
/*     */   {
/* 118 */     this(separator, quotechar, escape, false);
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
/*     */   public CSVParser(char separator, char quotechar, char escape, boolean strictQuotes)
/*     */   {
/* 131 */     this(separator, quotechar, escape, strictQuotes, true);
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
/*     */   public CSVParser(char separator, char quotechar, char escape, boolean strictQuotes, boolean ignoreLeadingWhiteSpace)
/*     */   {
/* 145 */     if (anyCharactersAreTheSame(separator, quotechar, escape)) {
/* 146 */       throw new UnsupportedOperationException("The separator, quote, and escape characters must be different!");
/*     */     }
/* 148 */     if (separator == 0) {
/* 149 */       throw new UnsupportedOperationException("The separator character must be defined!");
/*     */     }
/* 151 */     this.separator = separator;
/* 152 */     this.quotechar = quotechar;
/* 153 */     this.escape = escape;
/* 154 */     this.strictQuotes = strictQuotes;
/* 155 */     this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
/*     */   }
/*     */   
/*     */   private boolean anyCharactersAreTheSame(char separator, char quotechar, char escape) {
/* 159 */     return (isSameCharacter(separator, quotechar)) || (isSameCharacter(separator, escape)) || (isSameCharacter(quotechar, escape));
/*     */   }
/*     */   
/*     */   private boolean isSameCharacter(char c1, char c2) {
/* 163 */     return (c1 != 0) && (c1 == c2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isPending()
/*     */   {
/* 170 */     return this.pending != null;
/*     */   }
/*     */   
/*     */   public String[] parseLineMulti(String nextLine) throws IOException {
/* 174 */     return parseLine(nextLine, true);
/*     */   }
/*     */   
/*     */   public String[] parseLine(String nextLine) throws IOException {
/* 178 */     return parseLine(nextLine, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String[] parseLine(String nextLine, boolean multi)
/*     */     throws IOException
/*     */   {
/* 191 */     if ((!multi) && (this.pending != null)) {
/* 192 */       this.pending = null;
/*     */     }
/*     */     
/* 195 */     if (nextLine == null) {
/* 196 */       if (this.pending != null) {
/* 197 */         String s = this.pending;
/* 198 */         this.pending = null;
/* 199 */         return new String[] { s };
/*     */       }
/* 201 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 205 */     List<String> tokensOnThisLine = new ArrayList();
/* 206 */     StringBuilder sb = new StringBuilder(128);
/* 207 */     boolean inQuotes = false;
/* 208 */     if (this.pending != null) {
/* 209 */       sb.append(this.pending);
/* 210 */       this.pending = null;
/* 211 */       inQuotes = true;
/*     */     }
/* 213 */     for (int i = 0; i < nextLine.length(); i++)
/*     */     {
/* 215 */       char c = nextLine.charAt(i);
/* 216 */       if (c == this.escape) {
/* 217 */         if (isNextCharacterEscapable(nextLine, (inQuotes) || (this.inField), i)) {
/* 218 */           sb.append(nextLine.charAt(i + 1));
/* 219 */           i++;
/*     */         }
/* 221 */       } else if (c == this.quotechar) {
/* 222 */         if (isNextCharacterEscapedQuote(nextLine, (inQuotes) || (this.inField), i)) {
/* 223 */           sb.append(nextLine.charAt(i + 1));
/* 224 */           i++;
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 229 */           if ((!this.strictQuotes) && 
/* 230 */             (i > 2) && (nextLine.charAt(i - 1) != this.separator) && (nextLine.length() > i + 1) && (nextLine.charAt(i + 1) != this.separator))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 236 */             if ((this.ignoreLeadingWhiteSpace) && (sb.length() > 0) && (isAllWhiteSpace(sb))) {
/* 237 */               sb.setLength(0);
/*     */             } else {
/* 239 */               sb.append(c);
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 246 */           inQuotes = !inQuotes;
/*     */         }
/* 248 */         this.inField = (!this.inField);
/* 249 */       } else if ((c == this.separator) && (!inQuotes)) {
/* 250 */         tokensOnThisLine.add(sb.toString());
/* 251 */         sb.setLength(0);
/* 252 */         this.inField = false;
/*     */       }
/* 254 */       else if ((!this.strictQuotes) || (inQuotes)) {
/* 255 */         sb.append(c);
/* 256 */         this.inField = true;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 261 */     if (inQuotes) {
/* 262 */       if (multi)
/*     */       {
/* 264 */         sb.append("\n");
/* 265 */         this.pending = sb.toString();
/* 266 */         sb = null;
/*     */       } else {
/* 268 */         throw new IOException("Un-terminated quoted field at end of CSV line");
/*     */       }
/*     */     }
/* 271 */     if (sb != null) {
/* 272 */       tokensOnThisLine.add(sb.toString());
/*     */     }
/* 274 */     return (String[])tokensOnThisLine.toArray(new String[tokensOnThisLine.size()]);
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
/*     */   private boolean isNextCharacterEscapedQuote(String nextLine, boolean inQuotes, int i)
/*     */   {
/* 287 */     return (inQuotes) && (nextLine.length() > i + 1) && (nextLine.charAt(i + 1) == this.quotechar);
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
/*     */   protected boolean isNextCharacterEscapable(String nextLine, boolean inQuotes, int i)
/*     */   {
/* 301 */     return (inQuotes) && (nextLine.length() > i + 1) && ((nextLine.charAt(i + 1) == this.quotechar) || (nextLine.charAt(i + 1) == this.escape));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isAllWhiteSpace(CharSequence sb)
/*     */   {
/* 313 */     boolean result = true;
/* 314 */     for (int i = 0; i < sb.length(); i++) {
/* 315 */       char c = sb.charAt(i);
/*     */       
/* 317 */       if (!Character.isWhitespace(c)) {
/* 318 */         return false;
/*     */       }
/*     */     }
/* 321 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\CSVParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */