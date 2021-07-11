package com.gb.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb.blog.model.RoleType;
import com.gb.blog.model.User;
import com.gb.blog.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
			 userRepository.save(user);
	}

	@Transactional
	public void 회원수정(User user) {
		// 수정시에는 영속성 컨텍스트를 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
		// select 를 해서 User 오브젝트르 Db로 부터 가져오는 유는 영속화 하기 위해서
		//영속화된 오브젝트를 변경하면 자동으로 flush 함
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		//회원 수정 함수 종료시 > 서비스 종료 >  트랜잭션 종료 > commit 이 자동으로 진행
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹되어 update 문 실행
		
	}

}
