package com.gb.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gb.blog.model.User;

import lombok.Getter;

// 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를 시큐리티의 소유한 세션 저장소에 저장을 함
@Getter
public class PrinciaplDetail implements UserDetails {
	private User user; //콤포지션
	
	public PrinciaplDetail(User user) {
		this.user = user;
	}
	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	
	//계정이 활성화 가능한지 리턴 (true 활성화)
	@Override
	public boolean isEnabled() {
	
		return true;
	} 
	
	//계정이 갖고있는 권한 목록을 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
			
		collectors.add(()->{return  "ROLE_"+user.getRole();});
		
		return collectors;
	}
}
