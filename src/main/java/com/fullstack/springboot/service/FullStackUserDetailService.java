package com.fullstack.springboot.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fullstack.springboot.dto.FullStackMemberDTO;
import com.fullstack.springboot.entity.FullStackMember;
import com.fullstack.springboot.repository.FullStackMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
/*
 * 이 서비스가 로그인한 사용자의 정보를 get 하는 중요한 역할을 함.
 * 
 * User의 정보를 가져오기 위해서는 UserDetailService 의 하위 타입으로 서버로 등록 후에
 * 인증을 요청한 사용자의 요청 정보를 DTO 를 이용해서 처리하고, 처리 결과를 갖고 다음 작업을 수행하도록 함.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class FullStackUserDetailService implements UserDetailsService {

	private final FullStackMemberRepository fullStackMemberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//log.error("사용자 정보 : " + username);
		//사용자의 정보를 username 을 통해서 repo 를 이용 얻어냄.
		//이후 정보의 유무에 따라서 DTO 에 세팅을 해줌.
		
		Optional<FullStackMember> result = fullStackMemberRepository.findByEmail(username, false);
		
		if(result.isEmpty()) {
			throw new UsernameNotFoundException(username + "을 찾을 수 없음");
		}
		
		FullStackMember fullStackMember = result.get();
		
		log.error(username + "의 DB 정보 ["+fullStackMember+"]");
		
		//DTO 에 DB 정보 get
		FullStackMemberDTO member = new FullStackMemberDTO(fullStackMember.getEmail(),
				fullStackMember.getPassword(),
				fullStackMember.getRoleSet()
				.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
				.collect(Collectors.toSet()), false);
		
		member.setName(fullStackMember.getName());
		member.setFormSns(fullStackMember.isFormSns());
		return member;
	}
}
