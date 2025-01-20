package com.ariari.ariari.domain.club.passreview.repository;

import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.access.PassReviewAccess;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassReviewAccessRepository extends JpaRepository<PassReviewAccess, Long> {
    Boolean existsByPassReviewAndMember(PassReview passReview, Member member);
}
