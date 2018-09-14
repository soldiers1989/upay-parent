package com.dubhe.common.interceptor;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dubhe.common.servlet.listener.SessionContainer;
import com.dubhe.common.util.G4Utils;

/**
 * 
 * 拦截器
 * @author weizhao.dong
 * 
 */
public class AuthorityInterceptor implements HandlerInterceptor {
	private static final long serialVersionUID = 1358600090729208361L;
	private Log log = LogFactory.getLog(AuthorityInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler){
		String path = request.getContextPath();
		
		String uri = request.getRequestURI();
		String method = request.getParameter("method");
		String[] noFilters = new String[] { path + "/arm/login.do_login",path+"/arm/login.do_fpValidateCode",path+"/arm/login.do_fpMobileCode"
				,path+"/arm/login.do_fpMobileCodeValidate",path+"/arm/login.do_fpPasswordChange",path+"/arm/user.do_updatePwd"};;
		boolean beFilter = true;
		for (String s : noFilters) {
			if (s.equals(uri + "_" + method)) {
				beFilter = false;
				break;
			}
		}
		try {
//			if (beFilter) {
//				SessionContainer sessionContainer = ((SessionContainer) request
//						.getSession().getAttribute("SessionContainer"));
//				if (sessionContainer == null) {
//					log.warn("登陆超时或未登录");
//					response.sendRedirect(path + "/login.htm");
//					return false;
//				}else if(sessionContainer.getUserInfo().getIsFirstLog().equals(ArmConstants.IS_FIRST_LOG_TRUE)){
//					response.sendRedirect(path + "/chgpwd.htm");
//					return false;
//				}
//				sessionContainer.getReportDto().put("method", method);
//				//tcmOperLogService.addOperLog(request,sessionContainer);//记录操作日志
//			}
		}catch (Exception e) {
			log.error("记录操作日志发生错误",e);
		}
			
		return true;
	}
   
	/**
	 * 查询自定义权限信息
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		SessionContainer sessionContainer = (SessionContainer) request
				.getSession().getAttribute("SessionContainer");
		String menuId = request.getParameter("aMenuId");// 菜单ID
		String aMenuName = request.getParameter("aMenuName");// 菜单名称
		if(aMenuName!=null){
			 aMenuName = URLDecoder.decode(request.getParameter("aMenuName").replace("^", "%"), "utf-8");
			if(aMenuName!=null){
				aMenuName=aMenuName.substring(aMenuName.indexOf("_")+1,aMenuName.length());
				sessionContainer.getReportDto().put("aMenuName", aMenuName);
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String auditPath=request.getParameter("auditPath");
		if(G4Utils.isNotEmpty(auditPath)){
		 String servletPath=request.getServletPath();
		 servletPath=servletPath.replace(".do","");
		 auditPath=auditPath.replace("{}", servletPath);
		 request.setAttribute("auditPath",auditPath);
		}
		try {
			SessionContainer sessionContainer = (SessionContainer) request
					.getSession().getAttribute("SessionContainer");
			if(ex!=null){
				sessionContainer.getReportDto().put("rspCode", "1");//存放失败状态
				//tcmOperLogService.addOperLog(request,sessionContainer);//记录日志
			}else{
				sessionContainer.getReportDto().put("rspCode", "0");//存放成功状态
			}
		} catch (Exception e) {
			log.error("更新操作日志出错",e);
		}
	}


}
