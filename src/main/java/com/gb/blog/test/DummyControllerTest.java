package com.gb.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gb.blog.model.RoleType;
import com.gb.blog.model.User;
import com.gb.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입
	private UserRepository userRepository;
	
	//http://localhost:8080/blog/dummy/join(요청)
	//http의 body에  username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id" + user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원 가입 완료";
	}
}
