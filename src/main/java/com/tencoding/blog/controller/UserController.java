package com.tencoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping("/auth/login_form")
	public String loginForm(){
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
	
}
