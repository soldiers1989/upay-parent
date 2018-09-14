package com.dubhe.common.servlet.listener;

import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.LoggerFactory;

import com.dubhe.common.log.LogUtil;
import com.dubhe.common.util.G4Utils;

/**
 * Session监听器 完成对Seesion会话资源的实时监控
 * 
 * @author weizhao.dong
 * @see javax.servlet.http.HttpSessionBindingListener
 */
public class SessionListener implements HttpSessionListener {
    private static LogUtil  logger = new LogUtil(LoggerFactory.getLogger(SessionListener.class));
	// 集合对象，保存session对象的引用
	static Hashtable ht = new Hashtable();

	/**
	 * 实现HttpSessionListener接口，完成session创建事件控制
	 * 说明：此时的Session状态为无效会话，只有用户成功登录系统后才将此Session写入EAHTTPSESSION表作为有效SESSION来管理
	 */
	public void sessionCreated(HttpSessionEvent event) {
        logger.infoLog("session [{0}] is created",event.getSession().getId());
	}

	/**
	 * 实现HttpSessionListener接口，完成session销毁事件控制
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		SessionContainer sessionContainer =  (SessionContainer)session.getAttribute("SessionContainer");
		try {
		} catch (Exception e) {
            logger.infoLog("删除Session记录失败{0} ", G4Utils.getCurrentTime());
		}
		sessionContainer.cleanUp();
		ht.remove(session.getId());
        logger.infoLog("销毁了一个Session连接:{0} {1}", session.getId(), G4Utils.getCurrentTime());
	}
	

	/**
	 * 返回全部session对象集合
	 * @return
	 */
	static public Iterator getSessions() {
		return ht.values().iterator();
	}

	/**
	 * 依据session id返回指定的session对象
	 * @param sessionId
	 * @return
	 */
	static public HttpSession getSessionByID(String sessionId) {
		return (HttpSession) ht.get(sessionId);
	}

	
}
