package com.tencoding.blog.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tencoding.blog.model.User;

import lombok.Data;

@Data
public class PrincipalDetail implements UserDetails{

	/*
	 * 모든 클래스는 UID(고유 식별자)를 가지고 있다.
	 * class의 내용이 변경이 되면 UID값 역시 값이 변경된다 = hashCode
	 * 직렬화 하여 통신하고 UID값으로 통신한게 정상인지 확인을 하는데 
	 * 그 값이 바뀌게 되면 다른 class로 인식을 해버릴 가능성이 있다. >> 권장사항이다.
	 * 이를 방지하기 위해서 고유값으로 미리 명시를 해주는 부분이 바로 serialVersionUID 이다.
	 */
	private static final long serialVersionUID = 1L;
	
	// 선언한 유저 멤버 변수로 둔다.
	private User user;
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	/**
	 * 하나라도 false가 되면 작동 안됨 
	 */

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	
	/*
	 *  계정이 만료되지 않았는지 리턴한다. true --> 계정이 만료 되지 x
	 *   								   false --> 계정이 만료된 유저
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/*
	 * 계정이 잠김 여부 확인  true --> 사용가능
	 *                        false --> 사용 불가
	 * 
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
	 * 비밀번호 만료 여부를 알려준다.
	 * true --> 사용 가능
	 * false --> 사용 불가
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
	 * 계정 활성화 여부
	 * ( true : 사용 가능, false : 로그인 불가 )
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * 계정의 권한을 반환 한다.
	 * 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
//		collectors.add(new GrantedAuthority() {
//			@Override
//			public String getAuthority() {
//				// "ROLE_"  스프링 시큐리티 규칙( 꼭 넣기 )
//				// "ROLE_USER","ROLE_ADMIN"
//				
//				return "ROLE_" + user.getRole();
//			}
//		});
		collectors.add(() -> {
			return "ROLE_" + user.getRole();
		});
		
		return collectors;
	}

}
