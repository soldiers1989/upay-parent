//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.upay.commons.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public class CSV {
    private final char separator;
    private final char quotechar;
    private final char escapechar;
    private final Charset charset;
    private final String lineEnd;
    private final int skipLines;
    private final boolean strictQuotes;
    private final boolean ignoreLeadingWhiteSpace;

    private CSV(char separator, char quotechar, char escapechar, String lineEnd, int skipLines, boolean strictQuotes, boolean ignoreLeadingWhiteSpace, Charset charset) {
        this.separator = separator;
        this.quotechar = quotechar;
        this.escapechar = escapechar;
        this.lineEnd = lineEnd;
        this.skipLines = skipLines;
        this.strictQuotes = strictQuotes;
        this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
        this.charset = charset;
    }

    private CSV() {
        this(',', '"', '\\', "\n", 0, false, true, Charset.defaultCharset());
    }

    public CSVWriter writer(Writer writer) {
        return new CSVWriter(writer, this.separator, this.quotechar, this.escapechar, this.lineEnd);
    }

    public CSVWriter writer(OutputStream os) {
        return this.writer((Writer)(new OutputStreamWriter(os, this.charset)));
    }

    public CSVWriter writer(File file) {
        try {
            return this.writer((OutputStream)(new FileOutputStream(file)));
        } catch (FileNotFoundException var3) {
            throw new CSVRuntimeException(var3);
        }
    }

    public CSVWriter writer(String fileName) {
        return this.writer(new File(fileName));
    }

    public void write(Writer writer, CSVWriteProc proc) {
        this.write(this.writer(writer), proc);
    }

    public void write(OutputStream os, CSVWriteProc proc) {
        this.write(this.writer(os), proc);
    }

    public void write(String fileName, CSVWriteProc proc) {
        this.write(new File(fileName), proc);
    }

    public void write(CSVWriter writer, CSVWriteProc proc) {
        try {
            writer.write(proc);
            writer.flush();
        } catch (IOException var4) {
            throw new CSVRuntimeException(var4);
        }
    }

    public void write(File file, CSVWriteProc proc) {
        this.writeAndClose(this.writer(file), proc);
    }

    public void writeAndClose(Writer writer, CSVWriteProc proc) {
        this.writeAndClose(this.writer(writer), proc);
    }

    public void writeAndClose(OutputStream os, CSVWriteProc proc) {
        this.writeAndClose(this.writer(os), proc);
    }

    public void writeAndClose(CSVWriter writer, CSVWriteProc proc) {
        try {
            writer.write(proc);
        } catch (RuntimeException var7) {
            try {
                writer.close();
            } catch (Exception var5) {
                ;
            }

            throw var7;
        }

        try {
            writer.close();
        } catch (Exception var6) {
            throw new CSVRuntimeException(var6);
        }
    }

    public CSVReader reader(Reader reader) {
        return new CSVReader(reader, this.separator, this.quotechar, this.escapechar, this.skipLines, this.strictQuotes, this.ignoreLeadingWhiteSpace);
    }

    public CSVReader reader(InputStream is) {
        return this.reader((Reader)(new InputStreamReader(is, this.charset)));
    }

    public CSVReader reader(File file) {
        try {
            return this.reader((InputStream)(new FileInputStream(file)));
        } catch (IOException var3) {
            throw new CSVRuntimeException(var3);
        }
    }

    public CSVReader reader(String fileName) {
        return this.reader(new File(fileName));
    }

    public void read(InputStream is, CSVReadProc proc) {
        this.read(this.reader(is), proc);
    }

    public void read(Reader reader, CSVReadProc proc) {
        this.read(this.reader(reader), proc);
    }

    public void read(File file, CSVReadProc proc) {
        this.readAndClose(this.reader(file), proc);
    }

    public void read(String fileName, CSVReadProc proc) {
        this.read(new File(fileName), proc);
    }

    public void read(CSVReader reader, CSVReadProc proc) {
        reader.read(proc);
    }

    public void readAndClose(InputStream is, CSVReadProc proc) {
        this.readAndClose(this.reader(is), proc);
    }

    public void readAndClose(Reader reader, CSVReadProc proc) {
        this.readAndClose(this.reader(reader), proc);
    }

    public void readAndClose(CSVReader reader, CSVReadProc proc) {
        try {
            this.read(reader, proc);
        } finally {
            try {
                reader.close();
            } catch (IOException var9) {
                ;
            }

        }

    }

    public static CSV create() {
        return new CSV();
    }

    public static CSV.Builder separator(char separator) {
        return (new CSV.Builder()).separator(separator);
    }

    public static CSV.Builder quote(char quoteChar) {
        return (new CSV.Builder()).quote(quoteChar);
    }

    public static CSV.Builder noQuote() {
        return (new CSV.Builder()).noQuote();
    }

    public static CSV.Builder escape(char escapeChar) {
        return (new CSV.Builder()).escape(escapeChar);
    }

    public static CSV.Builder noEscape() {
        return (new CSV.Builder()).noEscape();
    }

    public static CSV.Builder lineEnd(String lineEnd) {
        return (new CSV.Builder()).lineEnd(lineEnd);
    }

    public static CSV.Builder skipLines(int skipLines) {
        return (new CSV.Builder()).skipLines(skipLines);
    }

    public static CSV.Builder strictQuotes() {
        return (new CSV.Builder()).strictQuotes();
    }

    public static CSV.Builder notStrictQuotes() {
        return (new CSV.Builder()).notStrictQuotes();
    }

    public static CSV.Builder ignoreLeadingWhiteSpace() {
        return (new CSV.Builder()).ignoreLeadingWhiteSpace();
    }

    public static CSV.Builder notIgnoreLeadingWhiteSpace() {
        return (new CSV.Builder()).notIgnoreLeadingWhiteSpace();
    }

    public static CSV.Builder charset(Charset charset) {
        return (new CSV.Builder()).charset(charset);
    }

    public static CSV.Builder charset(String charsetName) {
        return (new CSV.Builder()).charset(charsetName);
    }

    public static class Builder {
        private final CSV csv;

        private Builder() {
            this.csv = new CSV();
        }

        private Builder(CSV csv) {
            this.csv = csv;
        }

        public CSV create() {
            return this.csv;
        }

        public CSV.Builder separator(char separator) {
            return new CSV.Builder(new CSV(separator, this.csv.quotechar, this.csv.escapechar, this.csv.lineEnd, this.csv.skipLines, this.csv.strictQuotes, this.csv.ignoreLeadingWhiteSpace, this.csv.charset));
        }

        public CSV.Builder quote(char quoteChar) {
            return new CSV.Builder(new CSV(this.csv.separator, quoteChar, this.csv.escapechar, this.csv.lineEnd, this.csv.skipLines, this.csv.strictQuotes, this.csv.ignoreLeadingWhiteSpace, this.csv.charset));
        }

        public CSV.Builder escape(char escapeChar) {
            return new CSV.Builder(new CSV(this.csv.separator, this.csv.quotechar, escapeChar, this.csv.lineEnd, this.csv.skipLines, this.csv.strictQuotes, this.csv.ignoreLeadingWhiteSpace, this.csv.charset));
        }

        public CSV.Builder lineEnd(String lineEnd) {
            return new CSV.Builder(new CSV(this.csv.separator, this.csv.quotechar, this.csv.escapechar, lineEnd, this.csv.skipLines, this.csv.strictQuotes, this.csv.ignoreLeadingWhiteSpace, this.csv.charset));
        }

        public CSV.Builder skipLines(int skipLines) {
            return new CSV.Builder(new CSV(this.csv.separator, this.csv.quotechar, this.csv.escapechar, this.csv.lineEnd, skipLines, this.csv.strictQuotes, this.csv.ignoreLeadingWhiteSpace, this.csv.charset));
        }

        private CSV.Builder setStrictQuotes(boolean strictQuotes) {
            return new CSV.Builder(new CSV(this.csv.separator, this.csv.quotechar, this.csv.escapechar, this.csv.lineEnd, this.csv.skipLines, strictQuotes, this.csv.ignoreLeadingWhiteSpace, this.csv.charset));
        }

        private CSV.Builder setIgnoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
            return new CSV.Builder(new CSV(this.csv.separator, this.csv.quotechar, this.csv.escapechar, this.csv.lineEnd, this.csv.skipLines, this.csv.strictQuotes, ignoreLeadingWhiteSpace, this.csv.charset));
        }

        public CSV.Builder charset(Charset charset) {
            return new CSV.Builder(new CSV(this.csv.separator, this.csv.quotechar, this.csv.escapechar, this.csv.lineEnd, this.csv.skipLines, this.csv.strictQuotes, this.csv.ignoreLeadingWhiteSpace, charset));
        }

        public CSV.Builder noQuote() {
            return this.quote('\u0000');
        }

        public CSV.Builder noEscape() {
            return this.escape('\u0000');
        }

        public CSV.Builder strictQuotes() {
            return this.setStrictQuotes(true);
        }

        public CSV.Builder notStrictQuotes() {
            return this.setStrictQuotes(false);
        }

        public CSV.Builder ignoreLeadingWhiteSpace() {
            return this.setIgnoreLeadingWhiteSpace(true);
        }

        public CSV.Builder notIgnoreLeadingWhiteSpace() {
            return this.setIgnoreLeadingWhiteSpace(false);
        }

        public CSV.Builder charset(String charsetName) {
            return this.charset(Charset.forName(charsetName));
        }
    }
}
