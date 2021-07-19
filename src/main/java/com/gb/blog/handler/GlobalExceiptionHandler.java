package com.gb.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.gb.blog.dto.ResponseDTO;

@ControllerAdvice
@RestController
public class GlobalExceiptionHandler {
	@ExceptionHandler(value = Exception.class)
	public  ResponseDTO<String> handleArgumentExceiption(Exception e) {
		return new ResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage()); //500
	}
}
