package com.ariari.ariari.domain.club.review.repository;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubReviewRepository extends JpaRepository<ClubReview, Long> {
    Page<ClubReview> findByClub(Club club, Pageable pageable);
    boolean existsByClubAndMember(Club club, Member member);
}
