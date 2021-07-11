package com.gb.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gb.blog.dto.ResponseDTO;
import com.gb.blog.model.User;
import com.gb.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDTO<Integer> save(@RequestBody User user) {
		userService.회원가입(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴
	}
	@PutMapping("/user")
	public ResponseDTO<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		//여기서 트랜잭션 종료 되어 DB값 변경 됨 
		//session 값은 변경되지 않음 -> 직접 변경해야됨
		//세션 등록
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	

}
