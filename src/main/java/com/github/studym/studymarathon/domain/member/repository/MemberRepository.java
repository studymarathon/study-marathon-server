package com.github.studym.studymarathon.domain.member.repository;

import com.github.studym.studymarathon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member  m where  m.fromSocial = :social and m.email = :email")
    Optional<Member> findByEmail(String email, boolean social);

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.email = :email and m.fromSocial = false")
    Optional<Member> getWithRoles(String email);
}
