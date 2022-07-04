package com.tencoding.blog.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebConfig{
	
	@Bean  // 사용하기 위해서 메모리에 올린다. <-- filter에서 web.xml 을 자바에서 사용하기 위해서
	public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
		
		FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new XssEscapeServletFilter());
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/*");
		
		return filterRegistrationBean;
	};
}
