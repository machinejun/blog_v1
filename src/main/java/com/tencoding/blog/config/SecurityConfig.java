package com.tencoding.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // bean 등록(IOC)
@EnableWebSecurity // 시큐리티 필터로 등록이 된다. (필터 커스텀)
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 특정주소로 접근하면 권한 및 인증 처리를 미리 체크하겠다.	
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	/*
	 * - 같이 처리할 수 있는 것
	 * 1. 비밀번호 해쉬처리
	 */
	
	// 해쉬처리
	@Bean     //Ioc가 된다 필요할 때 가져와서 쓰면 된다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	/* 2. 특정 주서 필터를 설정할 예정
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/auth/**", "/", "/js/**","/css/**","/image/**")   // 아래와 같은 주소체계는 허용한다.
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/auth/login_form"); // 허용이 되지 않은 사용자가 오면 강제로 로그인 페이즈로 보낸다.
	}

}
