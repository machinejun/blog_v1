package com.tencoding.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Bean  // 사용하기 위해서 메모리에 올린다. <-- filter에서 web.xml 을 자바에서 사용하기 위해서
	public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
		
		FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new XssEscapeServletFilter());
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/*");
		
		return filterRegistrationBean;
	};
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		registry.addResourceHandler("/upload/**") // URL 패턴을 만들어주는 친구 >>> "/upload/...." 가있으면 낚아챔 
		.addResourceLocations("file:///" + uploadFolder) // 실제 물리적인 경로 >> "file:///" 붙여주어야한다.
		.setCachePeriod(60*10*6) // 캐시의 지속시간(1h)
		.resourceChain(true) // 리소스 찾는 것을 최적화 하기 위해서(for 성능향상)
		.addResolver(new PathResourceResolver()); 
		;
	}
	
	
}
