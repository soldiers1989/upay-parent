package com.dubhe.common.util;

import java.io.File;    

import org.apache.tools.ant.Project;    
import org.apache.tools.ant.taskdefs.Zip;    
import org.apache.tools.ant.types.FileSet;    
    
/** 
 * @ClassName: ZipCompressorByAnt 
 * @author : weizhao.dong 
 * @Description: 压缩文件的通用工具类-采用ant中的org.apache.tools.ant.taskdefs.Zip来实现，更加简单。 
 * 
 */  
public class ZipCompressorByAnt {    
    
    private File zipFile;    
    /** 
     * 压缩文件构造函数 
     * @param pathName 最终压缩生成的压缩文件：目录+压缩文件名.zip 
     */  
    public ZipCompressorByAnt(String finalFile) {    
        zipFile = new File(finalFile);    
    }    
        
    /** 
     * 执行压缩操作 
     * @param srcPathName 需要被压缩的文件/文件夹 
     */  
    public void compressExe(String srcPathName) {    
        File srcdir = new File(srcPathName);    
        if (!srcdir.exists()){  
            throw new RuntimeException(srcPathName + "不存在！");    
        }   
            
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj);    
        zip.setDestFile(zipFile);    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        fileSet.setDir(srcdir);    
        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");    
        //fileSet.setExcludes(...); //排除哪些文件或文件夹    
        zip.addFileset(fileSet);    
        zip.execute();    
    }    
    /** 
     * 执行压缩操作 
     * @param srcPathName 需要被压缩的文件/文件夹 
     */  
    public void compressExe(String srcPathName,String includes) {    
        File srcdir = new File(srcPathName);    
        if (!srcdir.exists()){  
            throw new RuntimeException(srcPathName + "不存在！");    
        }   
            
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj);    
        zip.setDestFile(zipFile);    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        fileSet.setDir(srcdir);    
        fileSet.setIncludes(includes); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");    
        //fileSet.setExcludes(...); //排除哪些文件或文件夹    
        zip.addFileset(fileSet);    
        zip.execute();    
    }    
}   