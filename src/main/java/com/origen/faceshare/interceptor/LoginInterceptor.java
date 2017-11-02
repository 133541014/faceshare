
package com.origen.faceshare.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录验证拦截器
 * 
 * @author origen.wang
 */
public class LoginInterceptor implements HandlerInterceptor {

	private static final Object User = null;

	private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	/**
	 * 请求结束之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

	}

	/**
	 * 请求处理之后执行(渲染视图之前)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mv) throws Exception {

	}

	/**
	 * 请求处理之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		Object object = session.getAttribute("normalUser");
		if ( object == null )
		{
			log.info("验证失败，用户未登录");
			response.sendRedirect("/");
			return false;
		} else
		{
			return true;
		}
	}

}
