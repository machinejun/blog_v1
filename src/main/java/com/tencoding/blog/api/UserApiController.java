package com.tencoding.blog.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
	private UserService userService;
	@Autowired
	private HttpSession session;
	
	
	@PostMapping("/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		// validation 체크 해야함
		System.out.println("UserApiController 호출됨!!!!!");
		user.setRole(RoleType.USER);
		
		int result = userService.saveUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}
	
	// HttpSession : 내장 객체 
	@PostMapping("/user/login")
	public ResponseDto<Integer> login(@RequestBody User user){
		System.out.println("login 호출됨!!!!!!!");
		User principal = userService.loginUser(user);
		if(principal != null) {
			session.setAttribute("principal", principal);
			System.out.println("세션정보가 저장되었습니다");
			System.out.println(session.getAttribute("principal"));
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}	
}
