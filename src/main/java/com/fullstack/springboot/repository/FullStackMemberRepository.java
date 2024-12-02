package com.fullstack.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fullstack.springboot.entity.FullStackMember;

public interface FullStackMemberRepository extends JpaRepository<FullStackMember, String> {

	@EntityGraph(attributePaths = {"roleSet"}, type = EntityGraphType.LOAD)//fetch type 을 변경하는 어노테이션. Load 값을 사용하면 eager 로 변경됨.
	@Query("select f from FullStackMember f where f.email = :email and f.formSns = :sns")
	Optional<FullStackMember> findByEmail(@Param("email") String email, @Param("sns") boolean sns);
}
