package com.tencoding.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 스프링 com.tenoding.blog 패키지 이하를 스캔해서 모든 자바 파일을
 * 메모리에 new 하는 것은 아니고 특정 어노테이션 붙어 있는 파일들을
 * new 해서 IoC 스프링 컨테이너에서 관리한다.
 */
@RestController
@RequestMapping("/test")
public class BlogController {
	
	@GetMapping("/hello")
	public String hello() {
		return "<h1>hello Spring Boot</h1>";
	}
	
	@GetMapping("/h1")
	public String h1() {
		return "ssssss";
	}

}
