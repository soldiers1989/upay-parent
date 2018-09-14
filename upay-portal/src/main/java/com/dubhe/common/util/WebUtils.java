package com.dubhe.common.util;

import static com.upay.commons.dict.AppCodeDict.GWSPAY2404;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.dubhe.common.annotation.HttpField;
import com.dubhe.common.annotation.NotNull;
import com.dubhe.common.constants.PayGateConstants;
import com.dubhe.common.datastructure.Dto;
import com.dubhe.common.datastructure.impl.BaseDto;
import com.dubhe.common.properties.PropertiesUtil;
import com.dubhe.common.servlet.listener.SessionContainer;
import com.pactera.dipper.commons.exception.ExInfo;
import com.upay.commons.util.DateUtil;

/**
 * 和Web层相关的实用工具类
 * 
 * @author weizhao.dong
 */
public class WebUtils {
	
	private static String redirectIp = PropertiesUtil.getProperty(PayGateConstants.REDIRECT_IP);
	private static String http = PropertiesUtil.getProperty(PayGateConstants.HTTP);
	
	private static String redirectPort = PropertiesUtil.getProperty(PayGateConstants.REDIRECT_PORT);
	
	private static final Logger logger = LoggerFactory
			.getLogger(WebUtils.class);

	/**
	 * 获取一个SessionContainer容器,如果为null则创建之
	 * 
	 * @param form
	 * @param obj
	 */
	public static SessionContainer getSessionContainer(
			HttpServletRequest request) {
		SessionContainer sessionContainer = (SessionContainer) request
				.getSession().getAttribute("SessionContainer");
		if (sessionContainer == null) {
			sessionContainer = new SessionContainer();
			HttpSession session = request.getSession(true);
			session.setAttribute("SessionContainer", sessionContainer);
		}
		return sessionContainer;
	}



	/**
	 * 获取一个SessionContainer容器,如果为null则创建之
	 * 
	 * @param form
	 * @param obj
	 */
	public static SessionContainer getSessionContainer(HttpSession session) {
		SessionContainer sessionContainer = (SessionContainer) session
				.getAttribute("SessionContainer");
		if (sessionContainer == null) {
			sessionContainer = new SessionContainer();
			session.setAttribute("SessionContainer", sessionContainer);
		}
		return sessionContainer;
	}

	/**
	 * 获取一个Session属性对象
	 * 
	 * @param request
	 * @param sessionName
	 * @return
	 */
	public static Object getSessionAttribute(HttpServletRequest request,
			String sessionKey) {
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
	public static void setSessionAttribute(HttpServletRequest request,
			String sessionKey, Object objSessionAttribute) {
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
	public static void removeSessionAttribute(HttpServletRequest request,
			String sessionKey) {
		HttpSession session = request.getSession();
		if (session != null)
			session.removeAttribute(sessionKey);
	}

	/**
	 * 将请求参数封装为Dto
	 * 
	 * @param request
	 * @return
	 */
	public static Dto getPraramsAsDto(HttpServletRequest request) {
		Dto dto = new BaseDto();
		Map map = request.getParameterMap();
		Iterator keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) (map.get(key)))[0];
			dto.put(key, value);
		}
		// 分页参数
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int startInt = 0;
		if (G4Utils.isNotEmpty(start)) {
			startInt = Integer.parseInt(start);
			if (G4Utils.isOracle()) {
				dto.put("start", startInt + 1);
			} else if (G4Utils.isMysql()) {
				dto.put("start", startInt);
			} else {
				dto.put("start", startInt);
			}
		}
		if (G4Utils.isNotEmpty(limit)) {
			int limitInt = Integer.parseInt(limit);
			if (G4Utils.isOracle()) {
				dto.put("end", limitInt + startInt);
			} else if (G4Utils.isMysql()) {
				dto.put("end", limitInt);
			} else {
				dto.put("end", limitInt);
			}
		}
		return dto;
	}

	/**
	 * 将请求参数封装为Dto
	 *
	 * @param request
	 * @return
	 */
	public static Dto getPagePraramsAsDto(HttpServletRequest request) {
		Dto dto = new BaseDto();
		// 分页参数
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int startInt = 0;
		if (G4Utils.isNotEmpty(start)) {
			startInt = Integer.parseInt(start);
			if (G4Utils.isOracle()) {
				dto.put("start", startInt + 1);
			} else if (G4Utils.isMysql()) {
				dto.put("start", startInt);
			} else {
				dto.put("start", startInt);
			}
		}
		if (G4Utils.isNotEmpty(limit)) {
			int limitInt = Integer.parseInt(limit);
			if (G4Utils.isOracle()) {
				dto.put("end", limitInt + startInt);
			} else if (G4Utils.isMysql()) {
				dto.put("end", limitInt);
			} else {
				dto.put("end", limitInt);
			}
		}
		return dto;
	}

	/**
	 * 获取代码对照值
	 * 
	 * @param field
	 *            代码类别
	 * @param code
	 *            代码值
	 * @param request
	 * @return
	 */
	public static String getCodeDesc(String pField, String pCode,
			HttpServletRequest request) {
		List codeList = (List) request.getSession().getServletContext()
				.getAttribute("EACODELIST");
		String codedesc = null;
		for (int i = 0; i < codeList.size(); i++) {
			Dto codeDto = (BaseDto) codeList.get(i);
			if (pField.equalsIgnoreCase(codeDto.getAsString("field"))
					&& pCode.equalsIgnoreCase(codeDto.getAsString("code")))
				codedesc = codeDto.getAsString("codedesc");
		}
		return codedesc;
	}

	/**
	 * 根据代码类别获取代码表列表
	 * 
	 * @param codeType
	 * @param request
	 * @return
	 */
	public static List getCodeListByField(String pField,
			HttpServletRequest request) {
		List codeList = (List) request.getSession().getServletContext()
				.getAttribute("EACODELIST");
		List lst = new ArrayList();
		for (int i = 0; i < codeList.size(); i++) {
			Dto codeDto = (BaseDto) codeList.get(i);
			if (codeDto.getAsString("field").equalsIgnoreCase(pField)) {
				lst.add(codeDto);
			}
		}
		return lst;
	}

	/**
	 * 获取全局参数值
	 * 
	 * @param pParamKey
	 *            参数键名
	 * @return
	 */
	public static String getParamValue(String pParamKey,
			HttpServletRequest request) {
		String paramValue = "";
		ServletContext context = request.getSession().getServletContext();
		if (G4Utils.isEmpty(context)) {
			return "";
		}
		List paramList = (List) context.getAttribute("EAPARAMLIST");
		for (int i = 0; i < paramList.size(); i++) {
			Dto paramDto = (BaseDto) paramList.get(i);
			if (pParamKey.equals(paramDto.getAsString("paramkey"))) {
				paramValue = paramDto.getAsString("paramvalue");
			}
		}
		return paramValue;
	}

	/**
	 * 获取全局参数
	 * 
	 * @return
	 */
	public static List getParamList(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		if (G4Utils.isEmpty(context)) {
			return new ArrayList();
		}
		return (List) context.getAttribute("EAPARAMLIST");
	}

	/**
	 * 取实际用户的访问地址。
	 * 
	 * @param request
	 *            当前请求。
	 * @return 客户端IP地址。
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ips = request.getHeader("x-forwarded-for");
		if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
			ips = request.getHeader("Proxy-Client-IP");
		}
		if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
			ips = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
			ips = request.getRemoteAddr();
		}

		String[] ipArray = ips.split(",");
		String clientIP = null;
		for (String ip : ipArray) {
			if (!("unknown".equalsIgnoreCase(ip))) {
				clientIP = ip;
				break;
			}
		}
		return clientIP;
	}
	
	/**
	 * 取实际用户的访问地址
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
	}

	/**
	 * 查找指定请求中的指定名称的Cookie。
	 * 
	 * @param request
	 *            请求。
	 * @param name
	 *            cookie名称。
	 * @return 如果有相应名称的Cookie，则返回相应Cookie实例。没有返回null。
	 */
	public static Cookie findCookie(HttpServletRequest request, String name) {
		if (request != null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(name)) {
						return cookie;
					}
				}
			}
		}

		return null;
	}

	/**
	 * 查找指定请求中的指定名称Cookie的值，如果不存在将返回null。
	 * 
	 * @param request
	 *            请求。
	 * @param name
	 *            Cookie名称。
	 * @return cookie的值。
	 */
	public static String findCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = findCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	/**
	 * 增加一个Cookie,使用默认域名。
	 * 
	 * @param request
	 *            请求。
	 * @param response
	 *            响应。
	 * @param name
	 *            Cookie名称 。
	 * @param value
	 *            Cookie的值。
	 * @param maxAge
	 *            生命周期。
	 */
	public static void addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value, int maxAge) {
		addCookie(request, response, name, value, null, maxAge);
	}

	/**
	 * 增加一个Cookie,使用指定域名。
	 * 
	 * @param request
	 *            请求。
	 * @param response
	 *            响应。
	 * @param name
	 *            Cookie名称 。
	 * @param value
	 *            Cookie的值。
	 * @param maxAge
	 *            生命周期。
	 */
	public static void addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			String domain, int maxAge) {
		String contextPath = request.getContextPath();
		if (contextPath == null || contextPath.isEmpty()) {
			contextPath = "/";
		}
		addCookie(request, response, name, value, domain, contextPath, maxAge);
	}

	/**
	 * 增加一个Cookie.ContextPath如果为空或者长度为0，将使用"/".
	 * 
	 * @param request
	 *            当前请求。
	 * @param response
	 *            当前响应。
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param domain
	 *            cookie域名
	 * @param contextPath
	 *            cookie路径。
	 * @param maxAge
	 *            有效时间。
	 */
	public static void addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			String domain, String contextPath, int maxAge) {
		if (request != null && response != null) {
			StringBuffer strBufferCookie = new StringBuffer();
			strBufferCookie.append(name + "=" + value + ";");

			if (maxAge >= 0) {
				strBufferCookie.append("Max-Age=" + maxAge + ";");
			}

			if (!checkObjIsNull(domain)) {
				strBufferCookie.append("domain=" + domain + ";");
			}
           
			if (!checkObjIsNull(domain)) {
				strBufferCookie.append("path=" + contextPath + ";");
			} else {
				strBufferCookie.append("path=/;");
			}

			if (request.isSecure()) {
				strBufferCookie.append("secure;HTTPOnly;");
			} else {
				strBufferCookie.append("HTTPOnly;");
			}
			response.addHeader("Set-Cookie", strBufferCookie.toString());
			logger.debug(
					"Cookie update the sessionID.[name={0},value={1},maxAge={2},secure={3},path={4},domain={5}]",
					name, value,
					String.valueOf(maxAge),
					String.valueOf(request.isSecure()),contextPath,
					domain);

		}
	}

	/**
	 * 失效一个Cookie.
	 * 
	 * @param request
	 *            当前请求。
	 * @param response
	 *            当前响应。
	 * @param name
	 *            Cookie名称。
	 * @param domain
	 *            Cookie域名。
	 * @param contextPath
	 *            有效路径。
	 */
	public static void failureCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String domain,
			String contextPath) {
		if (request != null && response != null) {
			addCookie(request, response, name, null, domain, contextPath, 0);
		}
	}

	/**
	 * 将指定的Cookie失效掉。
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应。
	 * @param name
	 *            cookie名称。
	 * @param domain
	 *            cookie的域名。
	 */
	public static void failureCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String domain) {
		String contextPath = request.getContextPath();
		if (contextPath == null || contextPath.isEmpty()) {
			contextPath = "/";
		}
		failureCookie(request, response, name, domain, contextPath);
	}

	/**
	 * 将指定的Cookie失效掉。
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应。
	 * @param name
	 *            cookie名称。
	 */
	public static void failureCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		failureCookie(request, response, name, null);
	}

	/**
	 * 获取请求的完整地址。
	 * 
	 * @param request
	 *            请求。
	 * @return 完整地址。
	 */
	public static String completeTheRequestAddress(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder(request.getRequestURL()
				.toString());
		String queryString = request.getQueryString();
		if (queryString != null) {
			buff.append("?").append(queryString);
		}

		return buff.toString();
	}

	/**
	 * 将换行符替换成html页面使用的换行元素。
	 * 
	 * @param source
	 *            原始字符串。
	 * @return 替换后的字符串。
	 */
	public static String enterToHtmlWrap(String source) {
		if (source == null || source.trim().isEmpty()) {
			return source;
		} else {
			return source.replaceAll("\r\n", "<br />");
		}
	}

	/**
	 * 一个客户端转向的方便工具方法.可以选择使用301或者302方式进行跳转.
	 * 
	 * @param response
	 *            当前响应.
	 * @param url
	 *            需要转向的地址.
	 * @param movePermanently
	 *            true表示进行301永久跳转,false表示302临时跳转.
	 * @throws java.io.IOException
	 *             I/O异常.
	 */
	public static void redirect(HttpServletResponse response, String url,
			boolean movePermanently) throws IOException {
		if (!movePermanently) {
			response.sendRedirect(url);
		} else {
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", url);
		}
	}

	/**
	 * 代理的名称,也代理了判断的顺序..
	 */
	private static final String[] AGENT_INDEX = { "MSIE", "Firefox", "Chrome",
			"Opera", "Safari" };
	/**
	 * 存放用户代理解析的正则容器.
	 */
	private static final Map<String, Pattern> AGENT_PATTERNS = new HashMap<String, Pattern>(
			AGENT_INDEX.length);

	static {
		AGENT_PATTERNS.put(AGENT_INDEX[0], Pattern.compile("MSIE ([\\d.]+)"));
		AGENT_PATTERNS.put(AGENT_INDEX[1], Pattern.compile("Firefox/(\\d.+)"));
		AGENT_PATTERNS.put(AGENT_INDEX[2], Pattern.compile("Chrome/([\\d.]+)"));
		AGENT_PATTERNS.put(AGENT_INDEX[3],
				Pattern.compile("Opera[/\\s]([\\d.]+)"));
		AGENT_PATTERNS
				.put(AGENT_INDEX[4], Pattern.compile("Version/([\\d.]+)"));
	}

	/**
	 * 获取用户代理信息.
	 * 
	 * @param userAgent
	 *            用户代理信息字符串.
	 * @return 用户代理信息.
	 */
	public static UserAgent checkUserAgent(String userAgent) {
		if (userAgent == null || userAgent.isEmpty()) {
			return null;
		}

		Pattern pattern = null;
		Matcher matcher = null;
		for (int point = 0; point < AGENT_INDEX.length; point++) {
			pattern = AGENT_PATTERNS.get(AGENT_INDEX[point]);
			matcher = pattern.matcher(userAgent);
			if (matcher.find()) {
				return new UserAgent(AGENT_INDEX[point], matcher.group(1));
			} else {
				continue;
			}
		}
		return null;
	}

	/**
	 * 获取指定请求中的用户代理.
	 * 
	 * @param request
	 *            请求.
	 * @return 用户代理信息.
	 */
	public static UserAgent checkUserAgent(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String userAgentHead = request.getHeader("User-Agent");
		return checkUserAgent(userAgentHead);
	}

	/**
	 * 表示一个用户代理的信息.
	 */
	public static class UserAgent {

		private String name = "";
		private String version = "";

		/**
		 * 构造一个用户代理信息.
		 * 
		 * @param name
		 *            代理名称.
		 * @param version
		 *            代理版本号.
		 */
		public UserAgent(String name, String version) {
			this.name = name;
			this.version = version;
		}

		/**
		 * 获取代理名称.
		 * 
		 * @return 代理名称.
		 */
		public String getName() {
			return name;
		}

		/**
		 * 获取版本号.
		 * 
		 * @return 版本号.
		 */
		public String getVersion() {
			return version;
		}
	}

	/**
	 * 票据名称
	 */
	public static final String TICKET_NAME = "ticket";

	/**
	 * 创建票据.
	 * 
	 * @param request
	 *            当前请求
	 */
	public static String createTicket(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String ticket = G4Utils.getUUID();
		session.setAttribute(TICKET_NAME, ticket);

		return ticket;
	}

	/**
	 * 验证票据. 请求中必须带有票据数据.
	 * 
	 * @param request
	 *            当前请求.
	 * @return true验证通过,false验证不通过.
	 */
	public static boolean testTicket(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String serverTicket = (String) session.getAttribute(TICKET_NAME);
		String clientTicket = request.getParameter(TICKET_NAME);

		try {
			if (serverTicket == null) {
				return true;
			} else {
				if (serverTicket.equals(clientTicket)) {
					return true;
				} else {
					return false;
				}
			}
		} finally {
			session.removeAttribute(TICKET_NAME);
		}
	}

	private static boolean checkObjIsNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * map非空校验, 非空字段需在bean字段加注解@NotNull
	 * @param clazz
	 * @param target
     */
	public static void checkNotNull(Class<?> clazz, Map<String, Object> target) {
		for(Field field : clazz.getDeclaredFields()) {
			for(Annotation annotation : field.getAnnotations()) {
				if(annotation.annotationType().equals(NotNull.class)) {
					Object obj = target.get(field.getName());
					if(null == obj || "".equals(obj)) {
						logger.error("参数[{}]为空", field.getName());
						ExInfo.throwDipperEx(GWSPAY2404);
					}
				}
			}
		}
	}

	/**
	 * 将request转为map,并进行非空判断,类型转换
	 * @param request
	 * @param clazz
	 * @param <T>
     * @return
     */
	public static <T> Map<String, Object> copyRequest2Map(HttpServletRequest request, Class<T> clazz) {
		Map<String, Object> res = new HashMap<>();
		for (Field field : clazz.getDeclaredFields()) {
			String fieldName = field.getName();
			String value = "";
			HttpField httpField = field.getAnnotation(HttpField.class);
			String type = "";
			String format = "";
			if (null != httpField) {
				fieldName = StringUtils.isNotBlank(httpField.name()) ? httpField.name() : fieldName;
				type = httpField.type();
				format = httpField.format();
			}
			value = request.getParameter(fieldName);
			if (StringUtils.isBlank(value)) {
				if (null != field.getAnnotation(NotNull.class)) {
					logger.error("参数[{}]为空", fieldName);
					ExInfo.throwDipperEx(GWSPAY2404);
				}
				continue;
			}
			if (StringUtils.isNotBlank(type)) {
				//类型转换
				switch (type) {
					case "java.math.BigDecimal":
						res.put(fieldName, new BigDecimal(value));
						break;
					case "java.lang.Integer":
						res.put(fieldName, Integer.valueOf(value));
						break;
					case "java.util.Date":
						if (StringUtils.isNotBlank(format)) {
							res.put(fieldName, DateUtil.parse(value, format));
						} else {
							throw new IllegalArgumentException("注解中[type = java.util.Date]时,必须配置[format]的属性");
						}
						break;
					case "java.lang.String":
						res.put(fieldName, value);
					default:
						throw new IllegalArgumentException("Type is error!!");
				}
			} else {
				res.put(fieldName, value);
			}
		}
		return res;
	}

	/**
	 * 将request请求参数转换为map
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getAllRequestParam(final HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}

	/**
	 * 页面跳转(nginx)
	 * @param request
	 * @param model
	 * @return
	 */
	public static ModelAndView forward(HttpServletRequest request, String viewName, Map<String, Object> model) {
//		String scheme = request.getScheme();
//		String serverName = request.getServerName();
//		int port = request.getServerPort();
		String path = request.getContextPath();
		if(StringUtils.isNotBlank(redirectPort)){
			redirectPort=":"+redirectPort;
		}
		String url = http + "://" + redirectIp + redirectPort + path;
		logger.debug(url + viewName);
		return new ModelAndView("redirect:" + url + viewName);
	}

	/**
	 * 页面跳转(nginx)
	 * @param request
	 * @param response
	 * @param viewName
	 * @param model
	 * @throws Exception
	 */
	public static void forward(HttpServletRequest request, HttpServletResponse response, String viewName, Map<String, Object> model) throws Exception {
		String scheme = request.getScheme();
//		String serverName = request.getServerName();
//		int port = request.getServerPort();
		String path = request.getContextPath();
		if(StringUtils.isNotBlank(redirectPort)){
			redirectPort=":" + redirectPort;
		}
		
		String url = http + "://" + redirectIp + redirectPort + "/" + viewName;
//		response.setContentType( "text/html;charset=utf-8");
		String html = createAutoFormHtml(url, model, request.getCharacterEncoding());
		logger.debug("返回的HTML     "+html);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(html);
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != writer) {
				writer.close();
			}
		}

	}

	/**
	 * 功能：前台交易构造HTTP POST自动提交表单<br>
	 * @param reqUrl 表单提交地址<br>
	 * @param hiddens 以MAP形式存储的表单键值<br>
	 * @param encoding 上送请求报文域encoding字段的值<br>
	 * @return 构造好的HTTP POST交易表单<br>
	 */
	public static String createAutoFormHtml(String reqUrl, Map<String, Object> hiddens, String encoding) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset="+encoding+"\"/></head><body>");
		stringBuilder.append("<form id = \"pay_form\" action=\"" + reqUrl
				+ "\" method=\"get\" accept-charset=\"UTF-8\">");
		if (null != hiddens && 0 != hiddens.size()) {
			Set<Map.Entry<String, Object>> set = hiddens.entrySet();
			Iterator<Map.Entry<String, Object>> it = set.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				String key = entry.getKey();
				String value = (String) entry.getValue();
				stringBuilder.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		stringBuilder.append("</form>");
		stringBuilder.append("</body>");
		stringBuilder.append("<script type=\"text/javascript\">");
		stringBuilder.append("document.all.pay_form.submit();");
		stringBuilder.append("</script>");
		stringBuilder.append("</html>");
		return stringBuilder.toString();
	}

	/**
	 * 页面跳转
	 * @param viewName 视图名,可以是一个可访问的url,写法:"redirect:http://www.baidu.com/s?wd=springmvc"
	 * @param model 视图的数据模型
	 * @return
	 */
	public static ModelAndView forward(String viewName, Map<String, Object> model) {
		return new ModelAndView(viewName, model);
	}

}
