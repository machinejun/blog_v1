package com.tencoding.blog.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService{
	/*
	 * 데이터에 대한 Access 전략을 설정하는 친구
	 * 얘는 오직 readonly로만 구성되어야 한다.
	 */
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = repository.findByUsername(username).orElseThrow(() -> {
			return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
		}); 
		
		return new PrincipalDetail(principal);
	}
	
	
}
