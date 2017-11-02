
package com.origen.faceshare.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.repository.UserMessageRepository;
import com.origen.faceshare.utils.SpringUtils;

/**
 * 信息检查拦截器
 * 
 * @author: origen.wang
 * @date: 2017年8月8日
 */
public class MessageCheckInterceptor implements HandlerInterceptor {

	private static Logger log = LoggerFactory.getLogger(MessageCheckInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) throws Exception {

		try
		{

			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("normalUser");

			if ( user != null )
			{

				UserMessageRepository userMessageRepository = (UserMessageRepository) SpringUtils.getBean("userMessageRepository");

				int count = userMessageRepository.findUserMessageCount(user.getId());

				if ( session.getAttribute("messageCount") == null )
				{
					session.setAttribute("messageCount", count);

					log.info(user.getUsername() + " 的收到信息条数已更新");
				} else
				{

					if ( (int) session.getAttribute("messageCount") != count )
					{

						session.setAttribute("messageCount", count);

						log.info(user.getUsername() + " 的收到信息条数已更新");
					}
				}

			}
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {

		return true;
	}

}
