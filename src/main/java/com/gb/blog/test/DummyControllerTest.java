package com.gb.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gb.blog.model.RoleType;
import com.gb.blog.model.User;
import com.gb.blog.repository.UserRepository;

//data를 리턴해주는 controller
@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입
	private UserRepository userRepository;
	
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			return "삭제 실패! 해당 ID는 DB에 없습니다. id : "+id;
		}
		
		return "삭제 되었습니다 id : "+id;
	}
	
	//save 함수는 id를 전달하지 않으면 insert를 함
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 함
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 함
	//email, password
	@Transactional //함수 종료시 자동 commit
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		//람다식
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//userRepository.save(user);
		//더티 체킹 
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public  List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
		Page<User> pagingUser= userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	//{id} 주소로 파라미터를 받을 수 있음
	//http://localhost:8080/blog/dummy/user/{id}
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//없는 데이터를 요청할 경우 null을 리턴하기 때문에 findById를 바로 사용 불가
		//Optional로 User 객체를 감싸서 null check 해야됨
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 아이디의 유저 정보가 없습니다. id : "+id);
			}
		});
		// 요청 : 웹 브라우저는 user객체를 읽지 못함
		// user 객체는 = 자바 오브젝트
		// 변환( 웹브라우저가 이해할 수 있는 데이터)  -> json
		//스프링부트 = MassageConverter가 응답시 자동 작동
		return user;
		
	}
	
	
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
