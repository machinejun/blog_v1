package com.tencoding.blog.test;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {

	public void hashEncoder() {
		String encPassword = new BCryptPasswordEncoder().encode("123");
		System.out.println("해쉬값 : " + encPassword);
		//$2a$10$fS3ZdR0Il0QVJOWiBaIBcOAle60xFL8/MjgOszDttdDgFIb09k7j2
		//$2a$10$tMQ2VLq8v9neP0tMO6lDPO7mbzzMGcTVNkRmo45e1uqBNJdFAJ8YW
	}
	
}
