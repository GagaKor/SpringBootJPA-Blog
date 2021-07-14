package com.gb.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gb.blog.model.KakaoProfile;
import com.gb.blog.model.OAuthToken;

//인증이 안된 사용자들이 출입할 수 있는 경로 /auth/**
//그냥 주소가 / 이면 index.jsp 허용
//static 이하에 있는 /js/**, /css/**, /img/** 등 허용

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaCallback(String code ){
		//POST 방식으로 key=value 데이터를 요청
		RestTemplate rt = new RestTemplate();
		
		//httpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type","authorization_code");
		params.add("client_id","ab88154d970dd3522b2b6bd8e8e54a46");
		params.add("redirect_uri","http://localhost:8080/auth/kakao/callback");
		params.add("code",code);
		
		//httpHeader 와 httpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		//http 요청하기 - Post 방식으로,response 응답받음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest,String.class);
		
		ObjectMapper objMapper = new ObjectMapper();
		OAuthToken oauthToken =null;
		try {
			oauthToken = objMapper.readValue(response.getBody() , OAuthToken.class);
		} catch (JsonProcessingException e) {	
			e.printStackTrace();
		}
		
		RestTemplate rt2 = new RestTemplate();
		
		//httpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest,String.class);
		
		ObjectMapper objMapper2 = new ObjectMapper();
		KakaoProfile KakaoProfile =null;
		try {
			KakaoProfile = objMapper2.readValue(response.getBody() , KakaoProfile.class);
		} catch (JsonProcessingException e) {	
			e.printStackTrace();
		}

		return response2.getBody();
	}
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
}
