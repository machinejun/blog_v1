package com.tencoding.blog.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.tencoding.blog.auth.PrincipalDetailService;

@Configuration// bean 등록(IOC)
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
	
	@Autowired
	private PrincipalDetailService pricipalDetailService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/")
		.antMatchers("/js/**")
		.antMatchers("/css/**")
		.antMatchers("/image/**");
	}
	/* 2. 특정 주서 필터를 설정할 예정
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests() // 요청에 대한 보안검사 시작
		.antMatchers("/auth/**").permitAll() // 아래와 같은 주소체계는 모두 허용한다.
		.anyRequest().authenticated() // 어떠한 요청에도 보안검사 실행
	.and()
		.formLogin() // 보안검증은 form login으로 하겠다.
		.loginPage("/auth/login_form") // 허용이 되지 않은 사용자가 오면 강제로 로그인 페이즈로 보낸다.
		.loginProcessingUrl("/auth/loginProc")
		.defaultSuccessUrl("/")
		.failureHandler(new AuthenticationFailureHandler() {
			
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
			
				response.setContentType("text/html; charset=utf-8");
				response.getWriter().println("<script>alert('아이디&비빌번호 wrong'); history.back();</script>");
				response.getWriter().flush();		
			}
		}); 
	}
		// 시프링 시큐리티가 해동 주소로 요청이 오면 가로채서 대신 로그인 처리를 해준다.
		// 단 이 동작하기 위해서는 만들어야할 클래스가 있다.
		// UserDetails type Object를 만들어야 한다.
		// username 이 존재여부 확인, password hash알고리즘 알려주기 
	
	//3. 시큐리티가 대신 로그인을 해주는데 password를 가로채서 해당 패스워드가 무엇으로 해쉬처리되었는지 함수를
	//	알려주어야 한다. 같은 해쉬로 암호화 해서 db에 해시값과 비교할 수 있다.
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 1. 유저 디테일 서비스에 들어갈 오브젝트를 만들어 주어야한다.
		// 2. passwordEncoder에 우리가 사용하는 해쉬 함수를 알려주어야 한다.

		
		auth.userDetailsService(pricipalDetailService).passwordEncoder(encodePWD());
	}
	

}
