package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.model.RoleType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		System.out.println("------------------------------");
		System.out.println(">>>  " + user.getUsername());
		System.out.println(">>>  " + user.getPassword());
		System.out.println(">>>  " + user.getEmail());
		System.out.println("------------------------------");
		
		System.out.println("------------------------------");
		System.out.println(">>>  " + user.getId());
		System.out.println(">>>  " + user.getCreateDate());
		System.out.println(">>>  " + user.getRole());
		System.out.println("------------------------------");
		
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		return "회원가입 완료 ";
		
		
	}
	

}
