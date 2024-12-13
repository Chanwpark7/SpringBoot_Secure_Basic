package com.fullstack.springboot;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.fullstack.springboot.entity.FullStackMember;
import com.fullstack.springboot.entity.FullStackRole;
import com.fullstack.springboot.repository.FullStackMemberRepository;

@SpringBootTest
class SpringSecureApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FullStackMemberRepository fullStackMemberRepository;
	
	@Test
	
	//빈으로 등록된 암호화 모듈 확인
//	void contextLoads() {
//		String testPw = "1111";
//		
//		String encPw = passwordEncoder.encode(testPw);
//		
//		System.out.println(encPw);
//		
//		System.out.println(passwordEncoder.matches(testPw, encPw));
//	}
	
	void insertDummies() {
		IntStream.rangeClosed(1, 100).forEach(value -> {
			FullStackMember fullStackMember = FullStackMember.builder()
					.email("user"+value+"@abc.com")
					.name("사용자"+value)
					.formSns(false)
					.password(passwordEncoder.encode("1111"))
					.build();
			
			//default role 설정
			fullStackMember.addMemberRoleSet(FullStackRole.USER);
			if(value>80) {
				//80 이상인 경우, member 권한도 줌
				fullStackMember.addMemberRoleSet(FullStackRole.MEMBER);
			}
			if(value>90) {
				//90 이상인 경우 admin 권한도 줌
				fullStackMember.addMemberRoleSet(FullStackRole.ADMIN);
			}
			
			fullStackMemberRepository.save(fullStackMember);
		});
	}

	//유저 정보와 함께 권한도 찾아보기
	void findMember() {
		Optional<FullStackMember> result = fullStackMemberRepository.findByEmail("user99@abc.com", false);
		
		System.out.println(result.get());
	}
}
