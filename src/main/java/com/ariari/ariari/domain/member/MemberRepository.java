package com.ariari.ariari.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.authorities where m.id = :id")
    Optional<Member> findByIdWithAuthorities(Long id);

    Optional<Member> findByKakaoId(Long kakaoId);

}
