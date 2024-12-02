package com.fullstack.springboot.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class FullStackMemberDTO extends User {

	private String email;
	private String name;
	private boolean formSns;
	
	public FullStackMemberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean formSns) {
		super(username, password, authorities);//이건 무조건 호출 해야 함.
		
		this.email = username;
		this.formSns = formSns;
	}

	
}
