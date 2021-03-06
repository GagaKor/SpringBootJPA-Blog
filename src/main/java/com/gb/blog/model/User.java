package com.gb.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//ORM -> Java Object -> 테이블로 매핑해주는 기술
@Entity //User 클래스가 MariaDB에 테이블이 생성됨
//@DynamicInsert //insert 시 null인 값은 뺴고  insert 한다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id//PrimaryKey
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100, unique = true)
	private String username;
	@Column(nullable = false ,length = 100)
	private String password;
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("'user")
	//DB에는 RoleType 이라는게 없다
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋다. // ADMIN, USER
	
	private String oauth;
	
	@CreationTimestamp//시간 자동 입력
	private Timestamp createDate;
	
}
