package com.tencoding.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Transactional
	public int saveUser(User user) {
		//select
		//update
		//insert
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
	
	
}
