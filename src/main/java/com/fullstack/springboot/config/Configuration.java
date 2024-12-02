package com.fullstack.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
//인증 방식이 boot 3.0부터 완전히 달라짐.
//이 이하 버전은 대부분 deprecated 됨.
//구글링해도 3 이하가 대부분이라 3버전에 맞춰서 강의 진행.
//이 설정 클래스가 웹 시큐어에 적용될거라고 선언을 해줘야 됨.
@EnableWebSecurity
public class Configuration {

	//암호화 빈즈를 등록함.
	//가장 많이 사용되는 암호화 라이브러리는 BCrypt Encoder임.
	//이 녀석은 암호화된 값을 리턴하고 특정 메소드(match)를 이용해서 origin 비번과 변환된 비번이 같은지의 여부를 알려줌
	//여기에 빈을 등록하면 context 전역에서 필요시에 메소드를 호출해서 등록된 빈 객체를 얻어낼 수 있음.
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//인증 대상인 UserDetailService 의 형제타입인 InMemory...Service 를 이용해서 context 내부에 임시 계정을 생성하고
	//이를 이용한 인증/인가 테스트
	
//	@Bean
//	InMemoryUserDetailsManager userDetailsManager() {
//		UserDetails userDetails = User.builder()
//				.username("user1")
//				.password(passwordEncoder().encode("1111"))
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(userDetails);
//	}
	
	//인증을 처리하기 위해서는 SecurityFilterChain 을 리턴하는 인증 처리 프로세스 메서드를 정의 해야 합니다.
	   //이 메서드를 처리하면 인증을 처리하는 필터(filter) 내에 자리 하게 되고, 요구한 대로 인증을 처리 프로세스를 실행함.
	   //대부분 아래 메서드처럼 처리함
	   @Bean
	   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	      //여기에 인증을 어떻게 처리 할 것인지를 HttpSecurity 변수를 이용해서 메서드 체인 형태로 정의합니다. (3 이상부터)
	      //HttpSecurity 에는 매우 많은 메서드들이 있는데, 다 볼 수 없고, 이중 인증/인가 처리 부분만 좀 볼게요.
	      
	      //Spring Security 가 제공하는 기본 폼 사용하는 설정.
	      return http
	    		  .authorizeHttpRequests(custom -> {
	    			  custom.requestMatchers("/springsec/member").hasRole("MEMBER");
	    			  custom.requestMatchers("/springsec/admin").hasRole("ADMIN");
	    			  custom.requestMatchers("/springsec/all").hasRole("USER");
	    		  })
	    		  //.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())//모든 리소스가 인가를 거쳐야 볼 수 있음.
	    		  .formLogin(Customizer.withDefaults())
	    		  //.formLogin(fromConfig -> fromConfig.loginPage("/login"))
	    		  .cors(corsconfig -> corsconfig.disable())
	    		  .build();
	      
	      //CSRF : 교차 사이트 설정 방지 기본적으로 보안을 유지하기 위해 disable()을 사용하는데 나중에 react 는 모두 허용을 해야함.
	      //이 부분은 사이트간 요청 위조 라고 하는데 기본적으로 허용이 되지만 악용될 경우엔 admin 이 탈취되는 문제가 발생할수 도 있음.
	      //이 부분은 나중에 react 에서 다시 한번 나옴.
	   }
}
