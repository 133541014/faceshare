
package com.origen.faceshare.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.origen.faceshare.interceptor.LoginInterceptor;
import com.origen.faceshare.interceptor.MessageCheckInterceptor;

/**
 * spring boot应用配置
 * 
 * @Configuration相当于配置应用容器<beans>
 * 
 * @author origen.wang
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	/**
	 * 配置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 添加拦截器，设置拦截规则
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/").excludePathPatterns("/toErrorPage")
						.excludePathPatterns("/login").excludePathPatterns("/register");
		registry.addInterceptor(new MessageCheckInterceptor()).addPathPatterns("/**").excludePathPatterns("/").excludePathPatterns("/toErrorPage");
		super.addInterceptors(registry);
	}

}
