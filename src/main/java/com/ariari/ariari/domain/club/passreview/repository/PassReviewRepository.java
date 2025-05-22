package com.ariari.ariari.domain.club.passreview.repository;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassReviewRepository extends JpaRepository<PassReview, Long> {
    boolean existsByClubAndMember(Club club, Member member);

    @Modifying(clearAutomatically = true)
    @Query("update PassReview pr set pr.member= null where pr.member= :member")
    void updateMemberNull(Member member);

}
