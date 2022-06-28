package com.tencoding.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/auth/joinProc")
	// 스프링 데이터 기본 파싱 전략 key = value 구조
	// application/x-www-form-urlencoded;charset=UTF-8
	public ResponseDto<Integer> save(User user){
		System.out.println("commmmmmmmm");
		int result = userService.saveUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		System.out.println("호출됨");
		if(userService.updateUser(user) != 1) {
			return new ResponseDto<Integer>(HttpStatus.FORBIDDEN.value(), 0);
		}
		
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
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
