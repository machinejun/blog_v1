package com.tencoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {
	
	@GetMapping("/temp/home")
	public String tempHome() {
		// 파일 리턴 경로 : src/main/resources/static
		// 리턴명 : /home.html
		
		System.out.println("tempHome()");
		return "/home.html";  // "/"를 꼭 넣어주야 한다 안넣으면 src/main/resources/statichome.html로 인식한다.
		// 리턴명 : /home.html
	}

}
