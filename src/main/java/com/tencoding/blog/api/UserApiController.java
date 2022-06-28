package com.tencoding.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.KakaoProfile;
import com.tencoding.blog.model.TokenKakao;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		System.out.println("호출됨");
		if(userService.updateUser(user) != 1) {
			return new ResponseDto<Integer>(HttpStatus.FORBIDDEN.value(), 0);
		}
		/*
		 * 회원 정보 수정하기 로직을 완성 & 처리 완료 -> DB데이터가 변경되었더라도 세션에 저장되어 있는
		 * 객체 정보는 수정이 되지 않는다.
		 * 
		 * >>
		 * Authentication 안에 객체가 들어가는 순간 부터 세션에 인증한 정보(User)가 저장이 된 상태라고 볼 수 있다.
		 * 로그인 요청시 username, password를 가지고 온다면 UserNamePasswordAuthenticationToken을 먼저 만들어 준다.
		 * >> 토큰을 만들어 주는 이유는 마지막에 AuthenticationMannager가 UserNamePasswordAuthenticationToken을 받아서
		 * 	  Authentication의 객체를 만들어 주기 때문이다.
		 * 	  
		 * >> 우리가 회원정보를 DB수정하고 SecurityContext에 있는 Authentication 객체의 정보도 함께 수정해야 한다.
		 * >> 강제로 authenticate의 객체를 만들고 SecurityContext에 집어 넣어주면 된다.
		 * 
		 * 1. Authentication 객체 생성
		 * 2. AuthenticationManager 메모리에 올려서 authenticate 메서드를 사용해서 Authentication 객체를 저장
		 * 3. 세션 - SecurityContextHolder.getContext().setAuthentication()에 Authentication 객체를 저장
ㅇ		 * 
		 */
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@GetMapping("/auth/kakao/callback")
	@ResponseBody
	public String loginFromKaKao(@RequestParam String code){
		/*
		 * httpUrlConnect
		 * retrofit2
		 * okHttp
		 * RestTemplate
		 * 
		 */
		// 1. header
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 2. body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>() ;
		params.add("grant_type", "authorization_code");
		params.add("client_id", "1550477d27cd178f856e5f3e0da6a93b");
		params.add("redirect_uri", "http://localhost:9090/auth/kakao/callback");
		params.add("code", code);
		
		//3. 하나의 오브젝트로 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params,headers);
		
		//4.http 요청
		ResponseEntity<TokenKakao> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, TokenKakao.class);
		String token = response.getBody().getAccessToken();
		
		RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        // 주의 Bearer 다음에 무조건 한칸 띄우기!!!!!!!!!!!!!!!!!!!!
        headers2.add("Authorization", "Bearer " + token);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // 바디 
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
        ResponseEntity<KakaoProfile> reponse2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, KakaoProfile.class);
        System.out.println(reponse2);
        
        
		
		
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders header = new HttpHeaders();
//		header.add("Authorization", "Bearer " + token);
//		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//		
//		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequests = new HttpEntity<>(header);
//		
//		
//		ResponseEntity<String> responseEntitiy = restTemplate.exchange("https://kapi.kakao.com/v2/user/me/",
//				HttpMethod.POST, kakaoProfileRequests,
//				String.class);
		
		
		
		return ">>" + reponse2 + "    nickname : " + reponse2.getBody().getProperties().getNickname() + "  email : " + reponse2.getBody().getKakaoAccount().getEmail();
	}
	
	
	
	
//	@PostMapping("/user")
//	public ResponseDto<Integer> save(@RequestBody User user) {
//		// validation 체크 해야함
//		System.out.println("UserApiController 호출됨!!!!!");
//		user.setRole(RoleType.USER);
//		
//		int result = userService.saveUser(user);
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
//	}
//	
//	// HttpSession : 내장 객체 
//	@PostMapping("/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user){
//		System.out.println("login 호출됨!!!!!!!");
//		User principal = userService.loginUser(user);
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//			System.out.println("세션정보가 저장되었습니다");
//			System.out.println(session.getAttribute("principal"));
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}	
}
