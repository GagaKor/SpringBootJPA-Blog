package com.gb.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {
	
	//http://localhost:8080/http/get
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get요청"+m.getId()+","+m.getEmail()+","+m.getPassword()+","+m.getEmail();
	}
	//http://localhost:8080/http/post
	@PostMapping("/http/post")
	public String postTest(@RequestBody String text) {
		return "post요청";
	}
	//http://localhost:8080/http/put
	@PutMapping("/http/put")
	public String putTest() {
		return "put요청";
	}
	//http://localhost:8080/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete요청";
	}
}
