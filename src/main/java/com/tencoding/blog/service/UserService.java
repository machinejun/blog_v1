package com.tencoding.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tencoding.blog.model.RoleType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@Service  // Ioc 등록
public class UserService {
	/**
	 * 서비스가 필요한 이유
	 * >> 현실 모딜링 : 레스토랑( 주문 : 종업원 )
	 *                          ( 음식 : 주방장 )
	 *                          ( 주문 등 서빙 : 종업원 )
	 *                          
	 * 1. 트랜잭션(작업 단위) 관리 + 기능별 단위로 묶기 위해서
	 * ex> 송금 : 홍길동 -> 10만원 -> 이순신
	 *     1. 홍길동 계좌 확인
	 *     2. 요청금액이 잔액보다 작다면 송금( 홍길동(update), 이순신(insert) )
	 *     >>> 즉, 하나의 기능(select) + 하나의 기능(update) + ... = 묶어서 단위의 기능을 만들어 내기 위해 필요하다.
	 *     >>> 하나 하나의 기능들이 모두 완료 되었을 때 데이터 베이스에 반영 한다.
	 *     
	 *      
	 */
	
	// DI : 의존 주입
	@Autowired    // 자동으로 초기화
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encode;
	
	@Transactional
	public int saveUser(User user) {
		//select
		//update
		//insert
		try {
			String rawPassword = user.getPassword();
			String encPassword = encode.encode(rawPassword);
			user.setPassword(encPassword);
			user.setRole(RoleType.USER);
			userRepository.save(user);		
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	@Transactional
	public int updateUser(User user) {
		// 카카오가 수정이 들어오면 무시 ~~~~ 처리
		if(user.getOauth()== null || user.getOauth().equals("")) {
			return 1;
		}
		User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new RuntimeException("업데이트가 실패하였습니다.");
		});
		userEntity.setPassword(encode.encode(user.getPassword()));
		userEntity.setEmail(user.getEmail());
		
//		Authentication authentication = AuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(, userEntity))

		return 1;
	}

	public User searchUser(String userName) {
		User userEntity = userRepository.findByUsername(userName).orElseGet(() ->{
			return new User();
		});
		return userEntity;
	}
	
//	@Transactional(readOnly = true)
//	public User loginUser(User user) {
//		// 레파지토리 select
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
	
}
