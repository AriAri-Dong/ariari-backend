package com.ariari.ariari.domain.member.member;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.authorities where m.id= :id")
    Optional<Member> findByIdWithAuthorities(Long id);

    Optional<Member> findByKakaoId(Long kakaoId);

    @Query("select m from Member m left join fetch m.clubBookmarks where m.id= :id")
    Optional<Member> findByIdWithClubBookmarks(Long id);

    @Query("select m from Member m left join fetch m.recruitmentBookmarks where m.id= :id")
    Optional<Member> findByIdWithRecruitmentBookmarks(Long id);

    Optional<Member> findByNickName(String nickname);

    boolean existsByNickName(String nickname);

    @Query("select m from Member m where m.nickName like %:nickname% order by m.nickName asc limit 20")
    List<Member> find20ByNickNameContains(String nickname);

}
