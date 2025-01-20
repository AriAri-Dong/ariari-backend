package com.ariari.ariari.domain.club.review.repository;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.review.ClubReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubReviewRepository extends JpaRepository<ClubReview, Long> {
    Page<ClubReview> findByClubMember_Club(Club club, Pageable pageable);
    boolean existsByClubMember(ClubMember clubMember);
}
