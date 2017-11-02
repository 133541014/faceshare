
package com.origen.faceshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring boot项目启动器
 * 
 * @author: origen.wang
 * @date: 2017年7月26日
 */

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		// 防止启动时自动进入debug
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(Application.class, args);
	}

}
