package com.ariari.ariari.domain.club.review.repository;

import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.access.ClubReviewAccess;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubReviewAccessRepository extends JpaRepository<ClubReviewAccess, Long> {
    Optional<ClubReviewAccess> findByClubReviewAndMember(ClubReview clubReview, Member member);
    Boolean existsByClubReviewAndMember(ClubReview clubReview, Member member);
}
