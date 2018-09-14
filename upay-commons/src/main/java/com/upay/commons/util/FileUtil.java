package com.upay.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.client.FtpPut;
import com.dcfs.esb.ftp.server.error.FtpException;


/**
 * 文件工具类<br/>
 * 
 * @author liujiian
 * 
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


    /**
     * 校验文件是否存在，若不存在则抛出异常
     * 
     * @param fileName
     */
    public static boolean exists(String fileName) {
        File f = new File(fileName);
        logger.debug(f.getAbsolutePath());
        return f.exists();
    }


    /**
     * 获取文件内容的总记录数，除去文件头和文件尾
     * 
     * @param fileName
     *            文件名,包含文件路径
     * @param clazz
     *            文件内容对应的类
     * @return
     */
    public static <T> long getFileRowCount(String fileName, Class<T> clazz) {
        if (!exists(fileName))
            throw new RuntimeException(fileName.concat("文件不存在"));

        DataCommons data =
                FileTemplateContext.getInstance().getConfig(TemplateTypeCommons.READ, clazz.getName());

        int headLength = data.getHeadLength();
        int perRecordLength = data.getPerRecordLength();
        int endingLength = data.getEndingLength();

        // 定长的文件
        if (!data.hasLineSeparator() || !data.hasSplit()) {
            long len = new File(fileName).length();
            long detailLen = len - headLength - endingLength;// 明细长度

            // 带换行符的定长文件
            if (data.hasLineSeparator()) {
                perRecordLength += data.getLineSeparator().getBytes().length;
            }
            if (detailLen % perRecordLength != 0) {
                throw new RuntimeException("文件不完整");
            }
            return detailLen / perRecordLength;
        }

        // 非定长文件只能一行行地去统计了
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), data.getCharSet()));
            int lineIndex = 0;
            while (br.readLine() != null) {
                lineIndex++;
            }
            return lineIndex - headLength - endingLength;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                }
        }
    }


    /**
     * 读取带换行的txt文件
     * 
     * @param fileName
     *            文件名
     * @param clazz
     *            文件内容对应的class类
     * @param startLineIndex
     *            读文件到起始行从0开始 ，忽略了文件头的情况
     * @param maxLine
     *            需要读取的行数
     * @return
     */
    public static <T> List<T> readFileToList(String fileName, Class<T> clazz, int startLineIndex, int maxLine) {
        if (!exists(fileName))
            throw new RuntimeException(fileName.concat("文件不存在"));

        DataCommons data =
                FileTemplateContext.getInstance().getConfig(TemplateTypeCommons.READ, clazz.getName());
        if (data.hasLineSeparator()) {
            return readHasLineSeparatorFileToList(fileName, data, clazz, startLineIndex, maxLine);
        } else {
            return readSequenceFileToList(fileName, data, clazz, startLineIndex, maxLine);
        }
    }


    private static <T> List<T> readSequenceFileToList(String fileName, DataCommons data, Class<T> clazz,
            int startLineIndex, int maxLine) {
        int headLength = data.getHeadLength();
        int perRecordLength = data.getPerRecordLength();
        BufferedInputStream fis = null;

        try {
            fis = new BufferedInputStream(new FileInputStream(fileName));
            long startLineIndexL = startLineIndex;// 防止相乘后超过Integer最大值
            long skipByte = startLineIndexL * perRecordLength + headLength;
            long actSkipByte = fis.skip(skipByte);
            if (skipByte != actSkipByte) {
                logger.error("startLineIndex：{},lineByteSize：{},headSize：{}", startLineIndexL,
                    perRecordLength, headLength);
                logger.error("预计跳过的字节数为：{},实际跳过的字节数为：{}", skipByte, actSkipByte);
                throw new RuntimeException("读取数据错误");
            }

            List<T> list = new ArrayList<T>();

            int lineNum = 0;
            byte[] buf = new byte[perRecordLength];

            while (lineNum < maxLine && (fis.read(buf) == perRecordLength)) {
                String lineContent = new String(buf);
                T instance = convert(data, clazz, lineContent, lineNum + startLineIndex);
                list.add(instance);
                lineNum += 1;
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("读取文件失败", e);
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                }
        }

    }


    private static <T> List<T> readHasLineSeparatorFileToList(String fileName, DataCommons data,
            Class<T> clazz, int startLineIndex, int maxLine) {
        List<T> list = new ArrayList<T>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), data.getCharSet()));
            int lineIndex = 0;
            startLineIndex += data.getHeadLength();
            maxLine-=data.getEndingLength()+data.getHeadLength();
            while (lineIndex < startLineIndex) {
                br.readLine();
                lineIndex++;
            }
            String lineContent = null;
            lineIndex = 0;
            while ((lineIndex < maxLine) && (lineContent = br.readLine()) != null) {
                if (lineContent.length() == 0)
                    continue;
                if (!data.hasSplit() && lineContent.length() != data.getPerRecordLength())
                    throw new RuntimeException(String.format("文件错误,行记录长度应为%d,实际为%d",
                        data.getPerRecordLength(), lineContent.length()));
                T instance = convert(data, clazz, lineContent, lineIndex + startLineIndex);
                list.add(instance);
                lineIndex++;
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("读取文件数据失败", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        return list;
    }


    private static <T> T convert(DataCommons data, Class<T> clazz, String lineContent, int index)
            throws Exception {
        List<ColumnCommons> colList = data.getColumnList();
        String split = data.getSplit();
        int size = colList.size();// 文件里一条记录的字段数
        T instance = clazz.newInstance();

        // 带分隔符
        if (data.hasSplit()) {
            String[] values = new StringBuilder(lineContent).append(" ").toString().split(split);
            if("\\|".equals(split)&&" ".equals(values[values.length - 1])){
            	values = Arrays.copyOf(values, values.length-1);
            	values[values.length-1]=" ";
            }
            if (values.length != size) {
            	throw new IllegalFileException("文件第" + index + "行字段数不符合");
            }
            System.out.println(values.length);
            // 最后一个把加的空格去掉
            values[values.length - 1] =
                    values[values.length - 1].substring(0, values[values.length - 1].length() - 1);
            

            for (int a = 0; a < values.length; a++) {
                ColumnCommons col = colList.get(a);
                parseValue(values[a], col, instance);
            }
        } else {
            // 定长 不应该包含中文
            int c = 0;
            for (int a = 0; a < colList.size(); a++) {
                ColumnCommons col = colList.get(a);
                String valueStr = lineContent.substring(c, c + col.getLength());
                c = c + col.getLength();
                parseValue(valueStr, col, instance);
            }
        }
        return instance;
    }


    /**
     * 将字符串设置成对象属性值 ， 读文件
     * 
     * @param valueStr
     * @param column
     * @param t
     * @throws Exception
     */
    private static <T> void parseValue(String valueStr, ColumnCommons column, T t) throws Exception {
        Method method = column.getWriteValueMethod();
        if (method == null)
            return;
        Class<?> parameterType = method.getParameterTypes()[0];
        Pattern pattern = column.getPattern();
        if (column.getLength() > 0 && valueStr.length() > column.getLength()) {
            throw new IllegalFileException("属性[" + column.getName() + "]值[" + valueStr + "]超出定义的长度");
        }
        if (pattern != null && !pattern.matcher(valueStr).matches())
            throw new IllegalFileException(valueStr + " no matches " + pattern.pattern());
        if (valueStr != null && !valueStr.trim().equals("")) {
            Object args = null;
            if (Date.class.isAssignableFrom(parameterType)) {
                if (!"0".equals(valueStr))
                    args = DateUtil.parse(valueStr, column.getDateFormat());
            } else {
                valueStr = valueStr.trim();
                // 数字类型，判断是否需要转换
                // Number.class.isAssignableFrom(parameterType)
                if (column.getNumMultiple() != null) {
                    valueStr =
                            new BigDecimal(valueStr).multiply(new BigDecimal(column.getNumMultiple()))
                                .toString();
                }
                if (column.isNeedTrans()) {
                    valueStr = column.trans(valueStr);
                }
                if (!parameterType.isPrimitive()) {
                    args = parameterType.getConstructor(String.class).newInstance(valueStr);
                } else {
                    if (char.class == parameterType)
                        args = valueStr.toCharArray()[0];
                    else if (int.class == parameterType)
                        args = Integer.parseInt(valueStr);
                    else if (double.class == parameterType)
                        args = Double.parseDouble(valueStr);
                    else if (float.class == parameterType)
                        args = Float.parseFloat(valueStr);
                    else if (long.class == parameterType)
                        args = Long.parseLong(valueStr);
                    else if (short.class == parameterType)
                        args = Short.parseShort(valueStr);
                    else if (boolean.class == parameterType)
                        args = Boolean.parseBoolean(valueStr);
                    else if (byte.class == parameterType)
                        args = Byte.parseByte(valueStr);
                    else
                        throw new RuntimeException("类型转换错误" + parameterType);

                }
            }
            method.invoke(t, args);
        }
    }


    /**
     * 写文件
     * 
     * @param fileName
     * @param list
     * @param append
     *            是否追加文件
     */
    public static <T> void write(String fileName, List<T> list, boolean append) {
        StringBuilder content = new StringBuilder();
        BufferedWriter bw = null;
        String charSet = "UTF-8";

        try {
            if (list != null && list.size() > 0) {
                DataCommons data =
                        FileTemplateContext.getInstance().getConfig(TemplateTypeCommons.WRITE,
                            list.get(0).getClass().getName());
                List<ColumnCommons> cols = data.getColumnList();
                charSet = data.getCharSet();

                for (T t : list) {
                    for (int a = 0, size = cols.size(); a < size; a++) {

                        ColumnCommons col = cols.get(a);

                        Method m = col.getReadValueMethod();
                        Class<?> returnType = m.getReturnType();
                        String valueStr = null;
                        Object value = m.invoke(t);
                        if (Date.class.isAssignableFrom(returnType)) {
                            valueStr =
                                    value != null ? DateUtil.format((Date) value, col.getDateFormat()) : col
                                        .getDefaultValue();
                        } else if (Number.class.isAssignableFrom(returnType) || returnType.isPrimitive()) {
                            if (!returnType.isPrimitive()) {
                                if (value == null) {
                                    value = 0;
                                    valueStr = col.getDefaultValue();
                                } else
                                    valueStr = value.toString();
                            }
                            if (returnType.isPrimitive()) {
                                valueStr = String.valueOf(value);
                            }
                            if (col.isNeedTrans()) {
                                valueStr = col.trans(valueStr);
                            }
                            if (col.getNumMultiple() != null) {
                                BigDecimal bd =
                                        new BigDecimal(value.toString()).multiply(new BigDecimal(col
                                            .getNumMultiple()));
                                valueStr = bd.toString();
                                if (col.getLength() > 0) {
                                    // 数字左补零
                                    valueStr = String.format("%0" + col.getLength() + "d", bd.intValue());
                                }
                            }
                        } else {
                            valueStr = value == null ? col.getDefaultValue() : value.toString();
                            if (col.isNeedTrans()) {
                                valueStr = col.trans(valueStr);
                            }
                            if (col.getLength() > 0) {
                                // 字符串右补空格
                                valueStr = String.format("%-" + col.getLength() + "s", valueStr);
                            }
                        }
                        content.append(valueStr);
                        if (data.hasSplit() && a < size) {
                            content.append(data.getSplit());
                        }
                    }
                    if (data.hasLineSeparator()) {
                        content.append("\r\n");
                    }
                }
            }

            File f = new File(fileName);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }

            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, append), charSet));

            bw.write(content.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static class IllegalFileException extends RuntimeException {
        private static final long serialVersionUID = -4040593750097815090L;


        public IllegalFileException(String message) {
            super(message);
        }
    }
    /**   
     * 递归查找文件   
     * @param baseDirName  查找的文件夹路径   
     * @param targetFileName  需要查找的文件名   
     * @param fileList  查找到的文件集合   
     */    
    public static String findFiles(String baseDirName, String targetFileName) {     
    	if(!exists(baseDirName)){
    	 return "false";
    	}
        File baseDir = new File(baseDirName);       // 创建一个File对象  
        if (!baseDir.exists() || !baseDir.isDirectory()) {  // 判断目录是否存在  
            System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");  
        }  
        String tempName = null;
        String returnstr = null;
        //判断目录是否存在     
        File tempFile;  
        File[] files = baseDir.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            tempFile = files[i];  
            if(tempFile.isDirectory()){  
                findFiles(tempFile.getAbsolutePath(), targetFileName);  
            }else if(tempFile.isFile()){  
                tempName = tempFile.getName();  
                if(wildcardMatch(targetFileName, tempName)){  
                    // 匹配成功，将文件名添加到结果集  
                	if (returnstr!=null){
                		 returnstr =  returnstr+","+tempFile.getAbsoluteFile();
                	}else{
                		returnstr = tempFile.getAbsoluteFile().toString();
                	}
                   
                }  
            }  
        }  
        return returnstr;
    }
    
    /**   
     * 通配符匹配   
     * @param pattern    通配符模式   
     * @param str    待匹配的字符串   
     * @return    匹配成功则返回true，否则返回false   
     */    
    private static boolean wildcardMatch(String pattern, String str) {     
        int patternLength = pattern.length();     
        int strLength = str.length();     
        int strIndex = 0;     
        char ch;     
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {     
            ch = pattern.charAt(patternIndex);     
            if (ch == '*') {     
                //通配符星号*表示可以匹配任意多个字符     
                while (strIndex < strLength) {     
                    if (wildcardMatch(pattern.substring(patternIndex + 1),     
                            str.substring(strIndex))) {     
                        return true;     
                    }     
                    strIndex++;     
                }     
            } else if (ch == '?') {     
                //通配符问号?表示匹配任意一个字符     
                strIndex++;     
                if (strIndex > strLength) {     
                    //表示str中已经没有字符匹配?了。     
                    return false;     
                }     
            } else {     
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {     
                    return false;     
                }     
                strIndex++;     
            }     
        }     
        return (strIndex == strLength);     
    }   

    /**   
     * ESB文件下载 
     * @param targetPath  目标文件夹路径   
     * @param filePath 本地文件夹路径    
     */    
    public static boolean ESBFtpGet(String targetPath, String filePath,String tarSysName){   
    	FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			
			//从文件传输平台下载文件，目标文件（remoteFile ）需要带上用户目录，如"/cms/报价单.txt"
			//推送方案，文件需要由ESB平台去提供方下载
			//
//			ftpGet=new FtpGet("/esb_rsm.txt", "E:/init23.sql","CBS", false, configSet, null);
			
			//请求方案，文件由提供方主动传输
//			ftpGet=new FtpGet("/test/222.txt", "F:/222.txt", false, configSet, null);
			
			ftpGet=new FtpGet(targetPath, filePath,tarSysName, false, configSet, null);	
			return ftpGet.doGetFile();
//			ftpGet.doGetCBSFile();
			
		} catch (FtpException e) {
			e.printStackTrace();
		}finally{
			if(ftpGet!=null){
				try {
					ftpGet.close();
				} catch (FtpException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
    }
    

    /**   
     * ESB文件上传 
     * @param filePath  本地文件夹路径   
     * @param targetPath 目标文件夹路径    
     */   
    public static String ESBFtpPut(String filePath, String targetPath,String tarSysName){   
    	//1.加载配置文件/bin/FtpClientConfig.properties
    			FtpClientConfigSet configSet = new FtpClientConfigSet();
    			FtpPut ftpPut=null;
    			try {
    				//往文件传输平台上传文件，目标文件（remoteFile ）需要带上用户目录，如"/picp/esb_rsm.log"
    				
    				//2.认证用户名、密码、路径、权限
    				//上传到后端
//    				ftpPut = new FtpPut("E:/1111.txt", "/esb_rsm.txt","CBS",false,configSet,null);			
    				//上传到文件传输平台
//    				ftpPut = new FtpPut("E:/222.txt", "/EEPS/222.txt",false,configSet,null);
    				ftpPut = new FtpPut(filePath, targetPath,tarSysName,false,configSet,null);
    				//3.上传文件
    			    String str= ftpPut.doPutFile();
    				
    				return str;
    			} catch (FtpException e) {
    				e.printStackTrace();
    			}finally{
    				if(ftpPut!=null){
    					ftpPut.close();
    				}
    			}
				return "";
    	
    }
    
    
    
    
    
    public static void main(String[] args) {
//    	System.out.println(ESBFtpGet("fil/FILE101003514313DZ20180329.txt","D:/FILE101003514313DZ20180329.txt"));
    	System.out.println(findFiles("D:\\","FILE10100EBANK1"));
	}
}
