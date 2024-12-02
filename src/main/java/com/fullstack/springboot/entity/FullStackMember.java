package com.fullstack.springboot.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FullStackMember extends BaseEntity {

	@Id
	private String email;
	
	private String password;
	
	private String name;
	
	private boolean formSns;
	
	//권한 컬렉션 선언 Default 로 선언해서 값이 자동으로 대입되도록 함.
	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private Set<FullStackRole> roleSet = new HashSet<FullStackRole>();
	
	//권한을 설정하는 메소드 정의
	public void addMemberRoleSet(FullStackRole fullStackRole) {
		roleSet.add(fullStackRole);
	}
}
