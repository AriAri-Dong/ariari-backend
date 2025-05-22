package com.ariari.ariari.domain.club.question;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubQuestionRepository extends JpaRepository<ClubQuestion, Long> {

    Page<ClubQuestion> findByClub(Club club, Pageable pageable);

    List<ClubQuestion> findByMember(Member member);

    @Modifying(clearAutomatically = true)
    @Query("update ClubQuestion cq set cq.member= null where cq.member= :member")
    void updateMemberNull(Member member);

}
