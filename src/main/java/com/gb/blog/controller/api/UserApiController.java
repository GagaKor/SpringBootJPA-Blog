package com.gb.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gb.blog.dto.ResponseDTO;
import com.gb.blog.model.RoleType;
import com.gb.blog.model.User;
import com.gb.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/user")
	public ResponseDTO<Integer> svae(@RequestBody User user) {
		user.setRole(RoleType.USER);
		userService.회원가입(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 JSON으로 변환해서 리턴
	}
	
	@PostMapping("/api/user/login")
	public ResponseDTO<Integer> login(@RequestBody User user, HttpSession session){
		System.out.println("UserApiController : login 호출");
		User principal = userService.로그인(user);
		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
}