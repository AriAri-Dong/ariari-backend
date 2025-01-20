package com.ariari.ariari.domain.club.passreview.repository;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.passreview.PassReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassReviewRepository extends JpaRepository<PassReview, Long> {
    Optional<PassReview> findByClubMemberClubId(Long clubId);
    boolean existsByClubMember(ClubMember clubMember);
}
