package com.tencoding.blog.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.tencoding.blog.auth.PrincipalDetailService;

@Configuration// bean 등록(IOC)
@EnableWebSecurity // 시큐리티 필터로 등록이 된다. (필터 커스텀)
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 특정주소로 접근하면 권한 및 인증 처리를 미리 체크하겠다.	
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	/* 
	 * Autentication 객체를 만들기 위해서는 ★ AuthenticationManger 이 친구가 필요하다.
	 * 이친구를 만들기 위해서는 비밀번호 파싱 전략(encoder)과 user데이터 액세스 전략(userDetailService)이 필요하다.
	 */
	
	// 해쉬처리
	@Bean     //Ioc가 된다 필요할 때 가져와서 쓰면 된다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private PrincipalDetailService pricipalDetailService;
	
	@Bean  // AuthenticationManager 메모리에 등록 얘를 사용할려면 adapter에서 만들어주어야한다.
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	
	// configure 메소드를 통해 어떤 요청에 대해서는 로그인을 요구하고, 어떤 요청에 대해서 로그인을 요구하지 않을지 설정한다.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
//		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//		.and()
		.authorizeRequests() // 요청에 대한 보안검사 시작
		.antMatchers("/","/js/**","/css/**", "/image/**" ,"/auth/**", "/dummy/**","/test/**").permitAll() // 아래와 같은 주소체계는 모두 허용한다.
		.anyRequest().authenticated() // 어떠한 요청에도 보안검사 실행
		.and()
		.formLogin() // 보안검증은 form login으로 하겠다.
		.loginPage("/auth/login_form") // 허용이 되지 않은 사용자가 오면 강제로 로그인 페이지로 보낸다.
		.loginProcessingUrl("/auth/loginProc") // 로그인 처리 api 주소 설정
		.defaultSuccessUrl("/");
//		.failureHandler(new AuthenticationFailureHandler() {
//			@Override
//			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//					AuthenticationException exception) throws IOException, ServletException {
//			
//				response.setContentType("text/html; charset=utf-8");
//				response.getWriter().println("<script>alert('아이디&비빌번호 wrong'); history.back();</script>");
//				response.getWriter().flush();		
//			}
//		}); 
	}
		// 시프링 시큐리티가 해동 주소로 요청이 오면 가로채서 대신 로그인 처리를 해준다.
		// 단 이 동작하기 위해서는 만들어야할 클래스가 있다.
		// UserDetails type Object를 만들어야 한다.
		// username 이 존재여부 확인, password hash알고리즘 알려주기 
	
	//3. 시큐리티가 대신 로그인을 해주는데 password를 가로채서 해당 패스워드가 무엇으로 해쉬처리되었는지 함수를
	//	알려주어야 한다. 같은 해쉬로 암호화 해서 db에 해시값과 비교할 수 있다.
	
	
	/**
	 * 매니저 빌드
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// AuthenticationManager 설정(passwordEncoder + userDetailService)s
		// 1. 유저 디테일 서비스에 들어갈 오브젝트를 만들어 주어야한다.
		// 2. passwordEncoder에 우리가 사용하는 해쉬 함수를 알려주어야 한다.

		
		auth.userDetailsService(pricipalDetailService).passwordEncoder(encodePWD());
	}
	

}
