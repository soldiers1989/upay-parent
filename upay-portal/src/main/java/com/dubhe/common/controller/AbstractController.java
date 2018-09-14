package com.dubhe.common.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ocx.AESWithJCE;
import ocx.GetRandom;
import ocxkeyboard.Util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.rapid_framework.generator.util.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.dubhe.common.datastructure.Dto;
import com.dubhe.common.httpupload.File;
import com.dubhe.common.httpupload.SmartUpload;
import com.dubhe.common.properties.PropertiesFactory;
import com.dubhe.common.properties.PropertiesFile;
import com.dubhe.common.properties.PropertiesHelper;
import com.dubhe.common.properties.PropertiesUtil;
import com.dubhe.common.servlet.listener.SessionContainer;
import com.dubhe.common.util.ExportExcelUtils;
import com.dubhe.common.util.G4Utils;
import com.dubhe.common.util.StreamUtil;
import com.dubhe.common.util.StringEscapeEditor;
import com.dubhe.common.util.WebUtils;
import com.dubhe.common.util.ZipCompressorByAnt;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.utils.common.UUIDGenerator;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.QRCode;


/**
 * @author weizhao.dong
 */
public abstract class AbstractController extends MultiActionController {
    private static Logger log = LoggerFactory.getLogger(AbstractController.class);
    private static final int BUFFER_SIZE = 16 * 1024;
    private static final String TRANS_LIST = "transList";
    protected HttpServletRequest request;
    @Resource
    private IDipperCached cache;

    public final String getAppbaseUrl(HttpServletRequest req, String url) {
        Assert.hasLength(url, "url不能为空");
        Assert.isTrue(url.startsWith("/"), "必须以/打头");
        return req.getContextPath() + url;
    }


    protected ModelAndView forwardError(Exception e) {
        ModelAndView mav = new ModelAndView("err500");
        mav.addObject("error", e);
        return mav;
    }

    /**
     * Action基类中给子类暴露的一个DAO接口<br>
     * <b>只能在Action子类中使用此Dao接口进行非事物相关的操作</b>:仅能进行查询操作
     */

    protected static PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.APP);


    /**
     * 获取一个Session属性对象
     *
     * @param request
     * @param sessionName
     * @return
     */
    protected Object getSessionAttribute(HttpServletRequest request, String sessionKey) {
        Object objSessionAttribute = null;
        HttpSession session = request.getSession(false);
        if (session != null) {
            objSessionAttribute = session.getAttribute(sessionKey);
        }
        return objSessionAttribute;
    }


    /**
     * 设置一个Session属性对象
     *
     * @param request
     * @param sessionName
     * @return
     */
    protected void setSessionAttribute(HttpServletRequest request, String sessionKey,
                                       Object objSessionAttribute) {
        HttpSession session = request.getSession();
        if (session != null)
            session.setAttribute(sessionKey, objSessionAttribute);
    }


    /**
     * 移除Session对象属性值
     *
     * @param request
     * @param sessionName
     * @return
     */
    protected void removeSessionAttribute(HttpServletRequest request, String sessionKey) {
        HttpSession session = request.getSession();
        if (session != null)
            session.removeAttribute(sessionKey);
    }


    /**
     * 获取一个SessionContainer容器,如果为null则创建之
     *
     * @param form
     * @param obj
     */
    protected SessionContainer getSessionContainer(HttpServletRequest request) {
        SessionContainer sessionContainer =
                (SessionContainer) this.getSessionAttribute(request, "SessionContainer");
        if (sessionContainer == null) {
            sessionContainer = new SessionContainer();
            HttpSession session = request.getSession(true);
            session.setAttribute("SessionContainer", sessionContainer);
        }
        return sessionContainer;
    }


    /**
     * 将请求参数封装为Dto
     *
     * @param request
     * @return
     */
    protected static Dto getPraramsAsDto(HttpServletRequest request) {
        return WebUtils.getPraramsAsDto(request);
    }


    /**
     * 获取代码对照值
     *
     * @param field   代码类别
     * @param code    代码值
     * @param request
     * @return
     */
    protected String getCodeDesc(String pField, String pCode, HttpServletRequest request) {
        return WebUtils.getCodeDesc(pField, pCode, request);
    }


    /**
     * 根据代码类别获取代码表列表
     *
     * @param field
     * @param request
     * @return
     */
    protected List getCodeListByField(String pField, HttpServletRequest request) {
        return WebUtils.getCodeListByField(pField, request);
    }


    /**
     * 获取全局参数值
     *
     * @param pParamKey 参数键名
     * @return
     */
    protected String getParamValue(String pParamKey, HttpServletRequest request) {
        return WebUtils.getParamValue(pParamKey, request);
    }


    /**
     * 输出响应
     *
     * @param str
     * @throws java.io.IOException
     */
    protected void write(String str, HttpServletResponse response) throws IOException {
        response.getWriter().write(str);
        response.getWriter().flush();
        response.getWriter().close();
    }


    protected void write(HttpServletResponse response, Object object) throws Exception {
        StreamUtil.writeStream(response, object);
    }


    /**
     * 获取客户端IP
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getUserIpAddr")
    public ModelAndView getUserIpAddr(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String ip = WebUtils.getClientIp(request);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (StringUtils.isBlank(ip)) {
            ip = "127.0.0.1";
        }
        jsonMap.put("userIp", ip);
        StreamUtil.writeStream(response, jsonMap);
        return null;
    }

    /**
     * 获取威通APP加密KEY
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRandKeyApp")
    public ModelAndView getRandKeyApp(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.debug("获取密码控件_RANDKEY=================================");
        String pwdComId = request.getParameter("pwdComId");
        String mcrypt_key = Util.getRandomKey(32).trim();
        cache.set(pwdComId + "_RANDKEY", mcrypt_key, 60 * 60 * 12);

        log.debug("获取密码控件    控件ID    " + pwdComId + "  RANDKE    " + mcrypt_key);
        StreamUtil.writeStream(response, mcrypt_key);
        return null;
    }

    /**
     * 获取威通APP加密KEY
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPwdKeyApp")
    public ModelAndView getPwdKeyApp(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.debug("获取密码控件APP key=================================");
        String pwdComId = request.getParameter("pwdComId");
        //因微通密码控件获取MAPARR的时候传入的ID会加是？和时间，所以要分割一下
        String splitStr = "\\u003F";
        pwdComId = pwdComId.split(splitStr)[0];
        log.debug("获取密码控件    控件ID    " + pwdComId);
//    	Map<String, Object> jsonMap = new HashMap<String, Object>();
        String[] mArr = Util.getMappingString();
        String mString = Util.getBase64(Arrays.toString(mArr)).trim();
        cache.set(pwdComId + "_MAPARR", mArr, 60 * 60 * 12);
//     jsonMap.put("MAPARR", mArr);
//       jsonMap.put("M_STRING", mString);
        StringBuffer sb = new StringBuffer();
        for (String aa : mArr) {
            sb.append(aa + ",");
        }
        log.debug("获取密码控件    MAPARR=" + sb.toString() + "=========M_STRING" + mString);
//       StreamUtil.writeStream(response, jsonMap);
        StreamUtil.writeStream(response, mString);
        return null;
    }

    /**
     * 获取威通加密KEY
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPwdKey")
    public ModelAndView getPwdKey(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
//    	String sm2Key = (String) DipperParm.getParmByKey(CacheConstants.SM2_KEY);
//    	String sm2Key="12345678";
        String sKey = GetRandom.generateString(32);
        String enStr = AESWithJCE.getCipher(sKey, sKey);
//        jsonMap.put("SM2_KEY", sm2Key);
        jsonMap.put("S_KEY", sKey);
        jsonMap.put("EN_STR", enStr);
        StreamUtil.writeStream(response, jsonMap);
        return null;
    }

    @RequestMapping(value = "/getPwdMcrypt")
    public ModelAndView getPwdMcrypt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String mcrypt = GetRandom.generateString(32);
//        setSessionAttribute(request, "mcrypt_key",mcrypt);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mcrypt_key", mcrypt);
        StreamUtil.writeStream(response, map);
        return null;

    }


    /**
     * 将url生成二维码返回Base64编码
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getQRCode")
    public ModelAndView getQRCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String urlData = request.getParameter("urlData");
        QRCode qrCode = new QRCode();
        String urlCode = qrCode.createQR(urlData);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("urlCode", urlCode);
        StreamUtil.writeStream(response, jsonMap);
        return null;
    }


    /**
     * 将url生成二维码,返回下载url
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getQRCodeUrl.do", method = RequestMethod.GET)
    public ModelAndView getQRCodeUrl(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.debug("获取二维码开始=========================================");
        QRCode qrCode = new QRCode();
        String content = request.getParameter("content");
        String picUrl = (String) PropertiesUtil.getProperty("qRCodeUrl");
        picUrl = picUrl + content + ".png";
        String contentUrl =
                (String) PropertiesUtil.getProperty("weiXinOpenUrl") + content
                        + (String) PropertiesUtil.getProperty("weiXinEndUrl");
        log.debug("商户开通微信支付的二维码地址：" + contentUrl + "   图片地址是：" + picUrl);
        qrCode.createGnrQROfWinxinMer(picUrl, contentUrl);
        Map<String, Object> jsonMap = new HashMap<>();
        log.debug("二维码地址====================" + (String) PropertiesUtil.getProperty("picUrl") + content + ".png");
        jsonMap.put("urlCode", (String) PropertiesUtil.getProperty("picUrl") + content + ".png");
        log.debug("获取二维码结束=========================================");
        StreamUtil.writeStream(response, jsonMap);

        return null;
    }


    @SuppressWarnings("finally")
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    public Object uploadFile(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(required = true) MultipartFile[] file,
                             @RequestParam(required = false) String[] fileName,
                             @RequestParam(required = false) Long lastModifiedDate,
                             @RequestParam(required = false) String type, @RequestParam(required = false) String iplDirPath)
            throws Exception {

        log.debug("file::::" + file + "              fileName:::::::: " + fileName
                + "           lastModifiedDate::::::::::" + lastModifiedDate + "           type:::::::"
                + type + "           iplDirPath:::::::::" + iplDirPath);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            String destPath = null;
            String sharemapp = null;
            String fileType = "gif,png,jpg";
            boolean isuploaded = true;
            String result = "-1";
            String msg = "";

            List<Map<String, Object>> uploadedPath = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < file.length; i++) {
                Map<String, Object> fileInfo = new HashMap<String, Object>();
                String realName =
                        null != fileName && fileName.length > 0 ? fileName[i] : file[i].getOriginalFilename();
                realName = realName.substring(realName.lastIndexOf(".") + 1);
                realName = realName.toLowerCase();
                if (!Arrays.<String>asList(fileType.split(",")).contains(realName)) {
                    result = "-1";
                    msg = "只能上传gif,png,jpg的图片格式。";
                    isuploaded = false;
                    break;
                }
                // String allowedExtensions = PropertiesUtil
                // .getProperty("allowedExtensions");
                // if (allowedExtensions.indexOf(realName) < 0) {
                // throw new Exception("文件格式不对,请选择其他文件");
                // }
                realName =
                        G4Utils
                                .getUUID()
                                .concat("_")
                                .concat(
                                        DateFormatUtils.format(new Date(),
                                                DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1)).concat(".")
                                .concat(realName);
                destPath = (String) PropertiesUtil.getProperty("virtualUploadPath");
                sharemapp = (String) PropertiesUtil.getProperty("sharemapp");

                switch (type) {
                    case "1":// 头像
                        realName = "HEAD_PIC/" + realName;
                        break;

                    case "2":// 身份证
                        realName = "CERT_PIC/" + realName;
                        break;
                    case "3":// 营业执照
                        realName = "LICENSE_PIC/" + realName;
                        break;
                }
                FileHelper.uploadFile(file[i], destPath.concat(realName));
                fileInfo.put("name", realName);
                fileInfo.put("path", sharemapp);
                fileInfo.put("src", sharemapp.concat(realName));
                uploadedPath.add(fileInfo);
            }
            if (isuploaded) {
                jsonMap.put("result", "0");
                jsonMap.put("message", "uploaded.");
                jsonMap.put("fileInfo", uploadedPath);
            } else {
                jsonMap.put("result", result);
                jsonMap.put("message", msg);
            }

        } catch (Exception e) {
            log.error("上传文件失败", e);
            jsonMap.put("result", "-1");
            jsonMap.put("message", e.getMessage());
        } finally {
            StreamUtil.writeStream(response, jsonMap);
            return null;
        }

    }


    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/headPicUpload.do", method = RequestMethod.POST)
    protected String uploadImage(HttpServletRequest request, HttpServletResponse response, String fileName)
            throws Exception {
        try {
            File suFile = this.initUpload(request, response).getFiles().getFile(0);

            // 图片格式判断
            if (!"jpg".equals(suFile.getFileExt()) && !"jpeg".equals(suFile.getFileExt())
                    && !"gif".equals(suFile.getFileExt())) {
                this.write(response, "上传文件不是正确的图片格式！");
                return null;
            }

            // 传入fileName为空，直接获取当前文件的名字用以保存，否则使用传入的fileName保存。
            if (fileName.isEmpty()) {
                fileName = suFile.getFileName() + "." + suFile.getFileExt();
            } else {
                fileName = fileName + "." + suFile.getFileExt();
            }
            FileHelper.mkdir(PropertiesUtil.getProperty("uploadImagePath"), fileName);// 创建文件夹
            suFile.saveAs(PropertiesUtil.getProperty("uploadImagePath") + "/" + fileName,
                    SmartUpload.SAVE_PHYSICAL);// 保存文件
            return fileName;
        } catch (Exception e) {
            log.error("导入异常", e);
            throw new RuntimeException(e);
        }

    }

    @Resource(name = "SI_ACC0007")
    private IDipperHandler<Message> SI_ACC0007;
    @Resource(name = "SI_MER0017")
    private IDipperHandler<Message> SI_MER0017;

    @Resource(name = "SI_PAY6003")
    private IDipperHandler<Message> SI_PAY6003;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/dwonloadExcel.do", method = RequestMethod.GET)
    public ModelAndView dwonloadExcel(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("====Start=====");
        String user_id = request.getParameter("user_id");
        String trans_type = request.getParameter("trans_type");
        String order_stat = request.getParameter("order_stat");
        String query_scope = request.getParameter("query_scope");
        String query_start = request.getParameter("query_start");
        String query_end = request.getParameter("query_end");
        String order_no = request.getParameter("order_no");
        String route_code = request.getParameter("route_code");
        String sec_mer_no = request.getParameter("sec_mer_no");
        Map<String, Object> bodys = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<String, Object>();
        // 判断是否超过31天
        if (null != query_start && null != query_end) {
            PrintWriter pw = response.getWriter();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(query_end));
            calendar.add(Calendar.YEAR, -1);
            Date nearbyOneYear = calendar.getTime(); //近一年的时间
            Date startDate = sdf.parse(query_start);//开始时间
            //开始时间  大于小于了近一年的时间
            if(startDate.before(nearbyOneYear)){
                pw.print("false");
                pw.close();
                return null;
//                return new ModelAndView().addObject(false);
            }
          /*  Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(query_start));
            int startMonth = cal.get(Calendar.MONTH);
            int startYear = cal.get(Calendar.YEAR);
            int startDay = cal.get(Calendar.DATE);
            cal.setTime(sdf.parse(query_end));
            int endMonth = cal.get(Calendar.MONTH);
            int endYear = cal.get(Calendar.YEAR);
            int endDay = cal.get(Calendar.DATE);
            int result = endMonth - startMonth;
            if (endYear > startYear) {
                if (result >= 0) {
                    pw.print("false");
                    pw.close();
                    return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                }
                if (result < 0) {
                    if (Math.abs(result) < 10) {
                        pw.print("false");
                        pw.close();
                        return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                    } else if (Math.abs(result) == 10) {
                        if (endDay - startDay > 0) {
                            pw.print("false");
                            pw.close();
                            return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                        }
                    }
                }
            }
            if (endYear == startYear) {
                if (result > 3) {
                    pw.print("false");
                    pw.close();
                    return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                } else if (result == 3) {
                    if (endDay - startDay > 0) {
                        pw.print("false");
                        pw.close();
                        return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                    }
                }
            }*/
            /*
             * if
             * (DateUtil.betweenDays(sdf.parse(query_end),sdf.parse(query_start
             * )) > 31){ pw.print("false"); pw.close(); return new
             * ModelAndView().addObject("msg", "开始时间和结束时间跨度超过31天，不予下载！"); }
             */
        }
        Message msg = null;
        bodys.put("userId", user_id);
        bodys.put("transCode", "SI_MER0017");
        bodys.put("serviceCode", "SI_MER0017");

        headers.put("serviceCode", "SI_MER0017");
        headers.put("transCode", "SI_MER0017");
        headers.put("chnlId", "01");
        headers.put("platForm", "22");
        headers.put("address", "192.168.1.103");
        msg =
                MessageFactory.create(UUIDGenerator.randomUUID(),
                        MessageFactory.createSimpleMessageInstance(),
                        MessageFactory.createSimpleMessage(headers, bodys),
                        FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
        msg = SI_MER0017.handle(msg);
        Map<String, Object> maps = (Map<String, Object>) msg.getTarget().getBodys();
        String merNo = request.getParameter("mer_no");//(String) maps.get("merNo");
        log.info("user_id》》》》》》》" + user_id);
        bodys.put("transCode", "SI_ACC0007");
        bodys.put("serviceCode", "SI_ACC0007");
        bodys.put("userId", user_id);
        if(StringUtils.isNotBlank(trans_type)){
            bodys.put("transType", trans_type);
        }
        if(StringUtils.isNotBlank(order_stat)){
            bodys.put("orderStat", order_stat);
        }
        bodys.put("queryScope", query_scope);
        bodys.put("startDate", query_start);
        bodys.put("endDate", query_end);
        bodys.put("merNo", merNo);
        bodys.put("orderNo", order_no);
        if(StringUtils.isNotBlank(route_code)){
            bodys.put("routeCode",route_code);
        }
        bodys.put("secMerNo",sec_mer_no);
        headers.put("serviceCode", "SI_ACC0007");
        headers.put("transCode", "SI_ACC0007");
        headers.put("chnlId", "01");
        headers.put("platForm", "22");
        headers.put("address", "192.168.1.103");
        msg =
                MessageFactory.create(UUIDGenerator.randomUUID(),
                        MessageFactory.createSimpleMessageInstance(),
                        MessageFactory.createSimpleMessage(headers, bodys),
                        FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));

        // 交易明细查询
        msg = SI_ACC0007.handle(msg);
        Map<String, Object> map = (Map<String, Object>) msg.getTarget().getBodys();
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        result = (List<HashMap<String, Object>>) map.get(TRANS_LIST);
        String title = "账户交易明细";
        String[] headers1 = null;
        String[] columns=null;
        if (!(null == merNo || "".equals(merNo))) {
             headers1 = new String[] {"订单号", "订单名称", "交易金额", "交易类型", "订单时间", "交易状态","商户手续费"};
             columns = new String[]{"orderNo", "orderName", "transAmt", "transType", "orderTime", "orderStat","merFeeAmt"};
        }else{
            headers1 = new String[] {"订单号", "订单名称", "交易金额", "交易类型", "订单时间", "交易状态","用户手续费"};
            columns = new String[]{"orderNo", "orderName", "transAmt", "transType", "orderTime", "orderStat","feeAmt"};
        }
        String fileName = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if (!(null == merNo || "".equals(merNo))) {
            fileName = merNo + "_" + sdf.format(new Date());
        } else {
            fileName = sdf.format(new Date());
        }
        String dirDown = "detailDownLoad/";// 对账单文件目录
        String virtualUploadPath = PropertiesUtil.getProperty("virtualUploadPath");
        virtualUploadPath = virtualUploadPath + dirDown;
        java.io.File file = new java.io.File(virtualUploadPath);
        OutputStream out = null;

        if (file.exists()) {
            log.info("file是否存在-----------" + file.exists());
            out = new FileOutputStream(virtualUploadPath + fileName + ".xls");
        } else {
            boolean flag = file.mkdirs();
            log.info("是否创建文件夹-----------" + flag);
            if (flag) {
                out = new FileOutputStream(virtualUploadPath + fileName + ".xls");
            }
        }
        String pattern = "";
        ExportExcelUtils.exportExcel(title, headers1, columns, result, out, pattern);
        log.info("方法执行结束时间222" + DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:sss"));
        out.flush();
        out.close();
        // request.setAttribute("url", fileUrl + fileName + ".xls");
        String url = "";
        String downloadFile = (String) PropertiesUtil.getProperty("downloadFile");
        url = downloadFile + dirDown + fileName + ".xls";
        PrintWriter pw = response.getWriter();
        log.info("返回前端地址时间" + DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:sss"));
        log.info("返回前端地址" + url);
        pw.print("{'url':'" + url + "'}");
        pw.close();
        return null;
    }


    /*资金结算登记薄  下载*/
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/dwonloadExcelForPublic.do", method = RequestMethod.GET)
    public ModelAndView dwonloadExcelForPublic(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("====Start=====");
        String stat = request.getParameter("stat");
        String stl_batch_no = request.getParameter("stl_batch_no");
        String query_scope = request.getParameter("query_scope");
        String query_start = request.getParameter("query_start");
        String query_end = request.getParameter("query_end");
        String merNo = request.getParameter("mer_no");
        String secMerNo = request.getParameter("sec_mer_no");
        Map<String, Object> bodys = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<String, Object>();
        // 判断是否超过31天
        if (null != query_start && null != query_end) {
            PrintWriter pw = response.getWriter();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(query_end));
            calendar.add(Calendar.YEAR, -1);
            Date nearbyOneYear = calendar.getTime(); //近一年的时间
            Date startDate = sdf.parse(query_start);//开始时间


            //开始时间  大于小于了近一年的时间
            if(startDate.before(nearbyOneYear)){
                pw.print("false");
                pw.close();
                return null;
//                return new ModelAndView().addObject(false);
            }



          /*
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(query_start));
            int startMonth = cal.get(Calendar.MONTH);
            int startYear = cal.get(Calendar.YEAR);
            int startDay = cal.get(Calendar.DATE);


            cal.setTime(sdf.parse(query_end));
            int endMonth = cal.get(Calendar.MONTH);
            int endYear = cal.get(Calendar.YEAR);
            int endDay = cal.get(Calendar.DATE);

            int result = endMonth - startMonth;*/


          /*  if (endYear > startYear) {
                if (result >= 0) {
                    pw.print("false");
                    pw.close();
                    return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                }
                if (result < 0) {
                    if (Math.abs(result) < 10) {
                        pw.print("false");
                        pw.close();
                        return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                    } else if (Math.abs(result) == 10) {
                        if (endDay - startDay > 0) {
                            pw.print("false");
                            pw.close();
                            return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                        }
                    }
                }
            }*/
            /*if (endYear == startYear) {
                if (result > 12) {
                    pw.print("false");
                    pw.close();
                    return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                } else if (result == 12) {
                    if (endDay - startDay > 0) {
                        pw.print("false");
                        pw.close();
                        return new ModelAndView().addObject("msg", "开始时间和结束时间超过三个月，不予下载！");
                    }
                }
            }*/

        /*    if (DateUtil.betweenDays(sdf.parse(query_end), sdf.parse(query_start)) > 31) {
                pw.print("false");
                pw.close();
                return new ModelAndView().addObject("msg", "开始时间和结束时间跨度超过31天，不予下载！");
            }*/


        }
        Message msg = null;
        bodys.put("transCode", "SI_PAY6003");
        bodys.put("serviceCode", "SI_PAY6003");
        bodys.put("stlBatchNo", stl_batch_no);
        bodys.put("stat", stat);
        bodys.put("queryScope", query_scope);
        bodys.put("startDate", query_start);
        bodys.put("endDate", query_end);
        bodys.put("merNo", merNo);
        bodys.put("secMerNo", secMerNo);
        headers.put("serviceCode", "SI_PAY6003");
        headers.put("transCode", "SI_PAY6003");
        headers.put("chnlId", "01");
        headers.put("platForm", "22");
        headers.put("address", "192.168.1.103");
        msg =
                MessageFactory.create(UUIDGenerator.randomUUID(),
                        MessageFactory.createSimpleMessageInstance(),
                        MessageFactory.createSimpleMessage(headers, bodys),
                        FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));

        //资金结算登记薄查询
        msg = SI_PAY6003.handle(msg);
        Map<String, Object> map = (Map<String, Object>) msg.getTarget().getBodys();
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        result = (List<HashMap<String, Object>>) map.get(TRANS_LIST);
        String title = "资金结算登记薄交易明细";
        String[] headers1 = {"结算日期", "结算批次号", "商户代码", "二级商户代码", "应付本金",
                "应付手续费", "应收本金", "应收手续费", "汇总扎差", "状态"};
        String[] columns = {"stlDate", "stlBatchNo", "merNo", "secMerNo", "payAmt", "payFee",
                "revAmt", "revFee", "barAmt", "stat"};
        String fileName = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if (!(null == merNo || "".equals(merNo))) {
            fileName = merNo + "_" + sdf.format(new Date());
            if (!(null == secMerNo || "".equals(secMerNo))) {
                fileName = "";
                fileName = merNo + "_" + secMerNo + "_" + sdf.format(new Date());
            }
        } else {
            fileName = sdf.format(new Date());
        }
        String dirDown = "detailDownLoad/";// 对账单文件目录
        String virtualUploadPath = PropertiesUtil.getProperty("virtualUploadPath");
        virtualUploadPath = virtualUploadPath + dirDown;
        java.io.File file = new java.io.File(virtualUploadPath);
        OutputStream out = null;

        if (file.exists()) {
            log.info("file是否存在-----------" + file.exists());
            out = new FileOutputStream(virtualUploadPath + fileName + ".xls");
        } else {
            boolean flag = file.mkdirs();
            log.info("是否创建文件夹-----------" + flag);
            if (flag) {
                out = new FileOutputStream(virtualUploadPath + fileName + ".xls");
            }
        }
        String pattern = "";
        ExportExcelUtils.exportExcel(title, headers1, columns, result, out, pattern);
        log.info("方法执行结束时间222" + DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:sss"));
        out.flush();
        out.close();
        // request.setAttribute("url", fileUrl + fileName + ".xls");
        String url = "";
        String downloadFile = (String) PropertiesUtil.getProperty("downloadFile");
        url = downloadFile + dirDown + fileName + ".xls";
        PrintWriter pw = response.getWriter();
        log.info("返回前端地址时间" + DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:sss"));
        log.info("返回前端地址" + url);
        pw.print("{'url':'" + url + "'}");
        pw.close();
        return null;
    }


    /**
     * 初始化上传
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected SmartUpload initUpload(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        SmartUpload su = new SmartUpload();
        su.initialize(request, response);
        su.upload();
        String fileExt = "";
        int fileSize = 0;
        String AllowedExtensions = PropertiesUtil.getProperty("allowedExtensions");// 允许上传的文件类型
        double maxFileSize = Double.valueOf(PropertiesUtil.getProperty("maxFileSize")) * 1024;// 单文件最大大小，单位KB
        // 校验文件类型和大小
        File suFile = su.getFiles().getFile(0);

        if (suFile.isMissing())
            return null;
        // 校验文件大小
        fileSize = suFile.getSize() / 1024;// 字节转换成KB
        if (fileSize == 0)
            fileSize = 1;
        if (maxFileSize < fileSize)
            throw new Exception("单个上传文件的容量不能超过[" + maxFileSize + "KB]");
        // 校验文件类型
        if (suFile.getFileExt() == null || "".equals(suFile.getFileExt())) {
            fileExt = ",,";
        } else {
            fileExt = "," + suFile.getFileExt().toLowerCase() + ",";
        }
        if (!"".equals(AllowedExtensions) && AllowedExtensions.indexOf(fileExt) == -1) {
            throw new Exception("您上传的文件[" + suFile.getFileName() + "]的类型为系统禁止上传的文件类型，不能上传！");
        }
        return su;
    }


    /**
     * 下载文件
     *
     * @param response
     * @param outRoot
     * @throws java.io.FileNotFoundException
     */
    public void downLoadFolder(HttpServletResponse response, String outRoot) throws FileNotFoundException {
        String FILE_NAME = outRoot.substring(outRoot.lastIndexOf("/") + 1, outRoot.length());
        String outputfile = outRoot + ".zip";
        String zipname = FILE_NAME + ".zip";
        java.io.File file = new java.io.File(outputfile);
        if (!file.exists()) {// 如果zip文件不存在，生存zip文件
            String tmp = outRoot;
            if (!new java.io.File(outRoot).isDirectory()) { // add by wudufeng
                // 2014.9.4
                tmp = outRoot.substring(0, outRoot.lastIndexOf("/"));
            }
            if (tmp.indexOf("/") == -1) {
                tmp += "/" + FILE_NAME;
            }

            ZipCompressorByAnt zca = new ZipCompressorByAnt(outputfile);
            zca.compressExe(tmp);
        }
        response.setHeader("Content-disposition", "attachment;filename=" + zipname);
        InputStream input = new FileInputStream(file);
        long contentLength = file.length();
        if (contentLength > 0)
            response.setContentLength((int) contentLength);
        response.setContentType("application/octet-stream");
        try {
            OutputStream output = response.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int retlen;
            while ((retlen = input.read(buffer)) != -1) {
                output.write(buffer, 0, retlen);
                output.flush();
            }
            input.close();
        } catch (IOException e) {
            log.error("下载文件出错", e);
        }

    }


    /**
     * 下载文件
     *
     * @param response
     * @param outRoot  下载文件夹的路径
     * @param includes 需要下载文件的名称
     * @param fileType 下载文件的类型
     * @param type     是否是固定报表
     * @throws java.io.FileNotFoundException
     */
    @SuppressWarnings("rawtypes")
    public void downLoadFolder(HttpServletResponse response, String outRoot, String includes,
                               boolean isNotDynamic) throws FileNotFoundException {
        String FILE_NAME = outRoot.substring(outRoot.lastIndexOf("/") + 1, outRoot.length());
        String outputfile =
                outRoot + includes.substring(includes.indexOf(".") + 1, includes.length()) + ".zip";
        String zipname = FILE_NAME + ".zip";
        java.io.File file = new java.io.File(outputfile);
        if (!file.exists()) {// 如果zip文件不存在，生存zip文件
            String tmp = outRoot;
            if (!new java.io.File(outRoot).isDirectory()) { // add by wudufeng
                // 2014.9.4
                tmp = outRoot.substring(0, outRoot.lastIndexOf("/"));
            }
            if (tmp.indexOf("/") == -1) {
                tmp += "/" + FILE_NAME;
            }

            ZipCompressorByAnt zca = new ZipCompressorByAnt(outputfile);
            zca.compressExe(tmp, includes);
        } else if (isNotDynamic) {
            // IDictTagService dictService = (IDictTagService)
            // this.getWebApplicationContext().getBean("dictService");
            // List dict_list = dictService.getDictSelectData("REBUILD_FILE");
            List dict_list = null;
            if (null != dict_list && dict_list.size() > 0) {
                Map map = (Map) dict_list.get(0);
                if (((String) map.get("code")).equals("1")) { // 需要重新生成文件
                    file.delete();
                    String tmp = outRoot;
                    if (!new java.io.File(outRoot).isDirectory()) { // add by
                        // wudufeng
                        // 2014.9.4
                        tmp = outRoot.substring(0, outRoot.lastIndexOf("/"));
                    }
                    if (tmp.indexOf("/") == -1) {
                        tmp += "/" + FILE_NAME;
                    }

                    ZipCompressorByAnt zca = new ZipCompressorByAnt(outputfile);
                    zca.compressExe(tmp, includes);
                }
            }
        }

        response.setHeader("Content-disposition", "attachment;filename=" + zipname);
        InputStream input = new FileInputStream(file);
        long contentLength = file.length();
        if (contentLength > 0)
            response.setContentLength((int) contentLength);
        response.setContentType("application/octet-stream");
        try {
            OutputStream output = response.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int retlen;
            while ((retlen = input.read(buffer)) != -1) {
                output.write(buffer, 0, retlen);
                output.flush();
            }
            input.close();
        } catch (IOException e) {
            log.error("下载文件出错", e);
        }

    }


    /**
     * 下载文件Zip
     *
     * @param response
     * @param outRoot
     * @throws java.io.FileNotFoundException
     */
    public void downLoadZip(HttpServletResponse response, String outputfile) throws FileNotFoundException {
        java.io.File file = new java.io.File(outputfile);
        InputStream input = new FileInputStream(file);
        long contentLength = file.length();
        if (contentLength > 0)
            response.setContentLength((int) contentLength);
        response.setContentType("application/octet-stream");
        try {
            OutputStream output = response.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int retlen;
            while ((retlen = input.read(buffer)) != -1) {
                output.write(buffer, 0, retlen);
                output.flush();
            }
            input.close();
        } catch (IOException e) {
            log.error("下载文件出错", e);
        }

    }


    /**
     * HTML转码(跨站脚本攻击防止)
     *
     * @param binder
     * @throws Exception
     * @author chen.nie
     */
    @InitBinder
    public void encodeForSecurity(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, true, true));
    }
}
