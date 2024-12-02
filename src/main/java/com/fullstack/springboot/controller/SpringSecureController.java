package com.fullstack.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequestMapping("/springsec")
public class SpringSecureController {

	@GetMapping("/all")//이 뷰어는 인증/인가 없이 모두 접근 가능하도록 할 예정. 나머진 인가에 따라서 접근이 달라짐.
	public void all() {
		
	}
	
	@GetMapping("/member")
	public void member() {
		
	}
	
	@GetMapping("/admin")
	public void admin() {
		
	}
	
}
