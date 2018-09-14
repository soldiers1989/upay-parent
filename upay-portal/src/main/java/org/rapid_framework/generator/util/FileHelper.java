package org.rapid_framework.generator.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang.time.DateFormatUtils;
import org.rapid_framework.generator.GeneratorProperties;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.dubhe.common.log.LogUtil;
import com.dubhe.common.util.G4Utils;

/**
 * @author weizhao.dong
 */
public class FileHelper {
    private static LogUtil  logger = new LogUtil(LoggerFactory.getLogger(FileHelper.class));
    public static final int BUFFER_SIZE = 16 * 1024;
    public static final String GLOBAL_CHARSET = "utf-8";

    /**
     * 得到相对路径
     */
    public static String getRelativePath(File baseDir, File file) {
        if (baseDir.equals(file))
            return "";
        if (baseDir.getParentFile() == null)
            return file.getAbsolutePath().substring(
                    baseDir.getAbsolutePath().length());
        return file.getAbsolutePath().substring(
                baseDir.getAbsolutePath().length() + 1);
    }

    public static List<File> searchAllNotIgnoreFile(File dir)
            throws IOException {
        ArrayList arrayList = new ArrayList();
        searchAllNotIgnoreFile(dir, arrayList);
        Collections.sort(arrayList, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
            }
        });
        return arrayList;
    }

    public static InputStream getInputStream(String file)
            throws FileNotFoundException {
        InputStream inputStream = null;
        if (file.startsWith("classpath:")) {
            inputStream = FileHelper.class.getClassLoader()
                    .getResourceAsStream(file.substring("classpath:".length()));
        } else {
            inputStream = new FileInputStream(file);
        }
        return inputStream;
    }

    public static void searchAllNotIgnoreFile(File dir, List<File> collector)
            throws IOException {
        collector.add(dir);
        if ((!dir.isHidden() && dir.isDirectory()) && !isIgnoreFile(dir)) {
            File[] subFiles = dir.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                searchAllNotIgnoreFile(subFiles[i], collector);
            }
        }
    }

    public static File mkdir(String dir, String file) {
        if (dir == null)
            throw new IllegalArgumentException("dir must be not null");
        File result = new File(dir, file);
        parnetMkdir(result);
        return result;
    }

    public static File parentMkdir(String file) {
        if (file == null)
            throw new IllegalArgumentException("file must be not null");
        File result = new File(file);
        parnetMkdir(result);
        return result;
    }

    public static void parnetMkdir(File outputFile) {
        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs();
        }
    }

    public static File getFileByClassLoader(String resourceName)
            throws IOException {
        Enumeration<URL> urls = GeneratorProperties.class.getClassLoader()
                .getResources(resourceName);
        while (urls.hasMoreElements()) {
            return new File(urls.nextElement().getFile());
        }
        throw new FileNotFoundException(resourceName);
    }

    public static boolean uploadFile(MultipartFile srcFile, String destPath) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = srcFile.getInputStream();
            out = new FileOutputStream(destPath);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, len);
            }
            return true;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将源文件上传文件至目标路径
     */
    public static boolean uploadFile(File srcFile, String destPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destPath);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存图片，并返回保存后的路径
     * @param bytes  字节码
     * @param destPath  保存路径
     * @param fileNamefrefix 图片后缀名
     * @return
     */
    public static String uploadFile(byte[] bytes, String destPath, String fileNamefrefix) {
        String dateStr = destPath.concat(File.separator).concat(G4Utils.getUUID()).concat(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")).concat(".").concat(fileNamefrefix);
        FileOutputStream fos=null;
        try {
            File file = new File(dateStr);
            fos = new FileOutputStream(file);
            fos.write(bytes);

        } catch (IOException e) {
            logger.errorLog(e,"保存图片失败!");
            return  null;
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                logger.errorLog(e,"保存图片失败!");
                return null;
            }
        }
        return dateStr;
    }
    /**
     * 保存图片，并返回保存后的路径
     * @param photobase64Str  图片64编码
     * @param destPath  保存路径
     * @param fileNamefrefix 图片后缀名
     * @return
     * @throws Exception 
     */
    public static String uploadFile(String photobase64Str, String destPath, String fileNamefrefix) throws Exception {
        String dateStr = destPath.concat(G4Utils.getUUID()).concat(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")).concat(".").concat(fileNamefrefix);
        FileOutputStream fos = null;
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		try {
			fos = new FileOutputStream(dateStr);
			compressed = new BASE64Decoder()
					.decodeBuffer(photobase64Str);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				fos.write(buffer, 0, offset);
			}
		} catch (IOException e) {
			throw new Exception("获取文件失败");
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
					throw new Exception("获取文件失败");
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new Exception("获取文件失败");
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw new Exception("获取文件失败");
				}
			}
		}
        return dateStr;
    }
    
    /**
	 * 以gunzip压缩读取文件,再以base64转码
	 * @throws Exception 
	 */
	public static String gzipEncodeFile(String fileName) throws Exception{
		File file = new File(fileName);
		FileInputStream fis = null;
		byte[] buffer = null;
		try {
			fis = new FileInputStream(file);
			buffer = new byte[(int)file.length()];
			fis.read(buffer);
		} catch (Exception e) {
			throw new Exception("上传文件失败[" + e.getMessage() + "]");
		}finally{
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new Exception("上传文件失败[" + e.getMessage() + "]");
				}
			}
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(buffer);
		} catch (IOException e) {
			throw new Exception("上传文件失败[" + e.getMessage() + "]");
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					throw new Exception("上传文件失败[" + e.getMessage() + "]");
				}
			}
		}

		return new BASE64Encoder().encode(out.toByteArray());
	}

    public static List ignoreList = new ArrayList();

    static {
        ignoreList.add(".svn");
        ignoreList.add("CVS");
        ignoreList.add(".cvsignore");
        ignoreList.add(".copyarea.db"); // ClearCase
        ignoreList.add("SCCS");
        ignoreList.add("vssver.scc");
        ignoreList.add(".DS_Store");
        ignoreList.add(".git");
        ignoreList.add(".gitignore");
    }

    private static boolean isIgnoreFile(File file) {

        for (int i = 0; i < ignoreList.size(); i++) {
            if (file.getName().equals(ignoreList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static Set binaryExtentionsList = new HashSet();

    static {
        loadBinaryExtentionsList("binary_filelist.txt", true);
        // loadBinaryExtentionsList("cn/org/rapid_framework/generator/util/binary_filelist.txt",false);
    }

    public static void loadBinaryExtentionsList(String resourceName,
                                                boolean ignoreException) {
        try {
            InputStream input = GeneratorProperties.class.getClassLoader()
                    .getResourceAsStream(resourceName);
            binaryExtentionsList.addAll(IOHelper
                    .readLines(new InputStreamReader(input)));
            input.close();
        } catch (Exception e) {
            if (!ignoreException)
                throw new RuntimeException(e);
        }
    }

    /**
     * 检查文件是否是二进制文件
     */
    public static boolean isBinaryFile(File file) {
        if (file.isDirectory())
            return false;
        return isBinaryFile(file.getName());
    }

    public static boolean isBinaryFile(String filename) {
        if (StringHelper.isBlank(getExtension(filename)))
            return false;
        return binaryExtentionsList.contains(getExtension(filename)
                .toLowerCase());
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.indexOf(".");
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    // -----------------------------------------------------------------------

    /**
     * Deletes a directory recursively.
     *
     * @param directory directory to delete
     * @throws java.io.IOException in case deletion is unsuccessful
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        cleanDirectory(directory);
        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * Deletes a file, never throwing an exception. If file is a directory,
     * delete it and all sub-directories.
     * <p/>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>No exceptions are thrown when a file or directory cannot be deleted.</li>
     * </ul>
     *
     * @param file file or directory to delete, can be <code>null</code>
     * @return <code>true</code> if the file or directory was deleted, otherwise
     * <code>false</code>
     * @since Commons IO 1.4
     */
    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (Exception e) {
        }

        try {
            return file.delete();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean
     * @throws java.io.IOException in case cleaning is unsuccessful
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: "
                            + file);
                }
                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    /**
     * 删除文件
     *
     * @param @param  paramString
     * @param @return
     * @param @throws FileNotFoundException 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: delete
     * @Description: TODO
     */
    public static boolean delete(String paramString)
            throws FileNotFoundException {
        File localFile = new File(paramString);
        if (!(localFile.exists()))
            throw new FileNotFoundException(paramString);
        return localFile.delete();
    }

    /**
     * 读取文本
     *
     * @param @param  is
     * @param @param  encoding
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: readText
     * @Description: TODO
     */
    public static String readText(InputStream is, String encoding) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    encoding));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readText(String fileName) {
        return readText(fileName, GLOBAL_CHARSET);
    }

    public static String readText(String fileName, String encoding) {
        try {
            InputStream is = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    encoding));
            StringBuffer sb = new StringBuffer();

            int c = br.read();
            if ((!(encoding.equalsIgnoreCase("utf-8"))) || (c != 65279))
                sb.append((char) c);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            is.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readText(File f, String encoding) {
        try {
            InputStream is = new FileInputStream(f);
            String str = readText(is, encoding);
            is.close();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String encodeFile(String fileName) throws Exception{
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[(int)file.length()];
		fis.read(buffer);
		fis.close();
		
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String base64Str = base64Encoder.encode(buffer);
		
		return base64Str;
		
	}

}
