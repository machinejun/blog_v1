package com.tencoding.blog.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.RoleType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@RestController
@RequestMapping("/api")
public class UserApiController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		// validation 체크 해야함
		System.out.println("UserApiController 호출됨!!!!!");
		user.setRole(RoleType.USER);
		
		int result = userService.saveUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}
	
	@PostMapping("/user/login")
	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
		System.out.println("login 호출됨!!!!!!!");
		// 서비스에게 요청
		
		// principal ( 접근 주체 )
		User principal = userService.loginUser(user);
		// 정상적으로 접근주체의 username, password가 확인이 되었으면
		// 세션이라는 거대한 메모리에 저장
		if(principal != null) {
			session.setAttribute("principal", principal);
			System.out.println("세션정보가 저장되었습니다");
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
		
}
