package com.ariari.ariari.domain.club.passreview.repository;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassReviewRepository extends JpaRepository<PassReview, Long> {
    boolean existsByClubAndMember(Club club, Member member);
}
