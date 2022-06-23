package com.tencoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping("/login_form")
	public String loginForm(){
		return "/user/login_form";
	}
	
	@GetMapping("/join_form")
	public String joinForm() {
		return "/user/join_form";
	}
	
	@GetMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "redirect:/";
	}
}
