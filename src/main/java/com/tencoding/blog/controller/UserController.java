package com.tencoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.tencoding.blog.dto.KakaoProfile;
import com.tencoding.blog.dto.TokenKakao;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@Controller
public class UserController {

	@Value("${teco.key}")
	private String tecokey;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	UserService service;

	@Autowired
	HttpSession httpSession;

	@GetMapping("/auth/login_form")
	public String loginForm() {
		return "/user/login_form";
	}

	@GetMapping("/auth/join_form")
	public String joinForm() {
		return "/user/join_form";
	}

	@GetMapping("/auth/logout")
	public String logout() {
		// 세션 정보를 제거 로그아웃 처리
		// 새로운 페이지(처음 화면)로 갈수있게 처리
		httpSession.invalidate();
		return "redirect:/";
		// rediret
	}

	@GetMapping("/user/update_form")
	public String updateForm() {
		return "/user/update_form";
	}

	@PostMapping("/auth/joinProc")
	// 스프링 데이터 기본 파싱 전략 key = value 구조
	// application/x-www-form-urlencoded;charset=UTF-8
	public String save(User user) {
		int result = service.saveUser(user);
		return "redirect:/";
	}

	@GetMapping("/auth/kakao/callback")
	public String loginFromKaKao(@RequestParam String code) {
		/*
		 * httpUrlConnect retrofit2 okHttp RestTemplate
		 * 
		 */
		// 1. header
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// 2. body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "1550477d27cd178f856e5f3e0da6a93b");
		params.add("redirect_uri", "http://localhost:9090/auth/kakao/callback");
		params.add("code", code);

		// 3. 하나의 오브젝트로 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// 4.http 요청
		ResponseEntity<TokenKakao> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, TokenKakao.class);
		String token = response.getBody().getAccessToken();

		getProfile(token);
		return "redirect:/";
	}

	private void getProfile(String token) {
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		// 주의 Bearer 다음에 무조건 한칸 띄우기!!!!!!!!!!!!!!!!!!!!
		headers2.add("Authorization", "Bearer " + token);
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// 바디
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		ResponseEntity<KakaoProfile> reponse2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest, KakaoProfile.class);
		System.out.println(reponse2);

		/*
		 * 소셜 로그인 처리 --> 사용자가 로그인 했을 경우 최초 사용자라면 회원가입 한번이라도 진행이 된 경우라면 로그인 처리를 한다. 만약 회원
		 * 가입시 필요한 정보가 더 있어야 된다면 추가로 사용자한테 정보를 받아서 가입 진행 처리를 해야한다.
		 */

		KakaoProfile kakaoProfile = reponse2.getBody();

		User kakaoUser = User.builder().username(kakaoProfile.getKakaoAccount().getEmail() + "_" + kakaoProfile.getId())
				.password(tecokey) // yml file에서 설정 <--- 여기에 해당되는 것은 절대 노출되면 안됨
				.email(kakaoProfile.getKakaoAccount().getEmail()).oauth("kakao").build();
		// 1. userService 호출해서 저장 진행 but 소셜 로그인 요청자가 이미가입된 유저라면 저장하면 안됨
		// 2. 세큐리티 세션에다가 강제 저장

		User originUser = userService.searchUser(kakaoUser.getUsername());
		if (originUser.getUsername() == null) {
			userService.saveUser(kakaoUser);
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), tecokey));
		/*	
		 *  authentication 객체는 매니저로 부터 만들어 지는데 매니저를 가져오기 위해서는 먼저 apdater에 포함관계에 있는
		 *  manger객체를 bean에 등록해 주어야 한다. 아니면 가져올수가 없다.
		 */
		SecurityContextHolder.getContext().setAuthentication(authentication);
		/*
		 * SecurityContextHolder는 현재 실행되고 있는 쓰레드에 SecurityContext을 연결해주는 역할을 한다.
		 * SecurityContext : 현재 쓰레드와 연결된 최소 보안정보를 정의한다. 
		 */

	}

}
