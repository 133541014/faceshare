
package com.origen.faceshare.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

	private static Logger log = LoggerFactory.getLogger(BaseController.class);

	private PrintWriter out;

	/**
	 * 返回json数据
	 */
	public void writeJson(HttpServletResponse response, String message) {

		try
		{
			// 设置响应数据编码
			// response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.print(message);
		}
		catch ( IOException e )
		{
			log.error(e.getMessage(), e);
		} finally
		{ // 关闭流
			out.close();
		}
	}

}
