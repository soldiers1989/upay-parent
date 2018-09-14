package org.rapid_framework.generator;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.rapid_framework.generator.Generator.GeneratorModel;
import org.rapid_framework.generator.util.FileHelper;
import org.rapid_framework.generator.util.GLogger;
import org.rapid_framework.generator.util.GeneratorException;
import org.rapid_framework.generator.util.StringHelper;


/**
 * @author weizhao.dong
 */
public class GeneratorFacade {
    public Generator g = new Generator();
    private static final String keywords=":";
    public Generator getG() {
        return g;
    }
    public GeneratorFacade() {
        g.setOutRootDir(GeneratorProperties.getProperty("outRoot"));
    }
    public GeneratorFacade(ServletContext servletContext) {
    	if(!StringHelper.contains(GeneratorProperties.getProperty("outRoot"), keywords)){
    		g.setOutRootDir(servletContext.getRealPath(GeneratorProperties.getProperty("outRoot")));
    	}else{
    		g.setOutRootDir(GeneratorProperties.getProperty("outRoot"));
    	}
    }
    
    public void deleteOutRootDir() throws IOException {
        g.deleteOutRootDir();
    }
    public void generateByMap(Map map, String templateRootDir) throws Exception {
        new ProcessUtils().processByMap(map, templateRootDir, false);
    }

    public void deleteByMap(Map map, String templateRootDir) throws Exception {
        new ProcessUtils().processByMap(map, templateRootDir, true);
    }
   
    private Generator getGenerator(String templateRootDir) {
        g.setTemplateRootDir(new File(templateRootDir).getAbsoluteFile());
        return g;
    }

    /**
     * 生成器的上下文，存放的变量将可以在模板中引用
     */
    public static class GeneratorContext {
        static ThreadLocal<Map> context = new ThreadLocal<Map>();

        public static void clear() {
            Map m = context.get();
            if (m != null) m.clear();
        }

        public static Map getContext() {
            Map map = context.get();
            if (map == null) {
                setContext(new HashMap());
            }
            return context.get();
        }

        public static void setContext(Map map) {
            context.set(map);
        }

        public static void put(String key, Object value) {
            getContext().put(key, value);
        }
    }

    public class ProcessUtils {
    
        public void processByMap(Map params, String templateRootDir, boolean isDelete) throws Exception, FileNotFoundException {
            Generator g = getGenerator(templateRootDir);
            GeneratorModel m = GeneratorModelUtils.newFromMap(params);
            processByGeneratorModel(templateRootDir, isDelete, g, m);
        }

        private void processByGeneratorModel(String templateRootDir, boolean isDelete, Generator g, GeneratorModel m) throws Exception, FileNotFoundException {
            try {
                if (isDelete)
                    g.deleteBy(m.templateModel, m.filePathModel);
                else
                    g.generateBy(m.templateModel, m.filePathModel);
            } catch (GeneratorException ge) {
                PrintUtils.printExceptionsSumary(ge.getMessage(), getGenerator(templateRootDir).getOutRootDir(), ge.getExceptions());
            }
        }


    }

    @SuppressWarnings("all")
    public static class GeneratorModelUtils {


        public static GeneratorModel newFromMap(Map params) {
            Map templateModel = new HashMap();
            templateModel.putAll(params);
            setShareVars(templateModel);

            Map filePathModel = new HashMap();
            setShareVars(filePathModel);
            filePathModel.putAll(params);
            return new GeneratorModel(templateModel, filePathModel);
        }

 /*       private static void getGlobalConfig() {
            HttpSession session = request.getSession();
            HashMap<String, String> globalConfig = (HashMap<String, String>) session.getAttribute("globalConfig");
//            HashMap<String, String> keyMap = new HashMap<String, String>();
            if (globalConfig != null)
                for (String key : globalConfig.keySet()) {
                    String dir_key = key + "_dir";
                    String value = globalConfig.get(key);
                    String dir_value = value.replace('.', '/');
                    GeneratorProperties.setProperty(dir_key, dir_value);
                    GeneratorProperties.setProperty(key, value);
                }
//            keyMap.putAll(globalConfig);
//            return keyMap;
        }*/

        public static void setShareVars(Map templateModel) {

          //  getGlobalConfig();
            templateModel.putAll(GeneratorProperties.getProperties());
            templateModel.putAll(System.getProperties());

            templateModel.put("env", System.getenv());
            templateModel.put("now", new Date());
            templateModel.putAll(GeneratorContext.getContext());
        }
    }

    private static class PrintUtils {

        private static void printExceptionsSumary(String msg, String outRoot, List<Exception> exceptions) throws FileNotFoundException {
            File errorFile = new File(outRoot, "generator_error.log");
            if (exceptions != null && exceptions.size() > 0) {
                System.err.println("[Generate Error Summary] : " + msg);
                PrintStream output = new PrintStream(new FileOutputStream(errorFile));
                for (int i = 0; i < exceptions.size(); i++) {
                    Exception e = exceptions.get(i);
                    System.err.println("[GENERATE ERROR]:" + e);
                    if (i == 0) e.printStackTrace();
                    e.printStackTrace(output);
                }
                output.close();
                System.err.println("***************************************************************");
                System.err.println("* " + "* 输出目录已经生成generator_error.log用于查看错误 ");
                System.err.println("***************************************************************");
            }
        }

        private static void printBeginProcess(String displayText, boolean isDatele) {
            GLogger.println("***************************************************************");
            GLogger.println("* BEGIN " + (isDatele ? " delete by " : " generate by ") + displayText);
            GLogger.println("***************************************************************");
        }

    }
}
