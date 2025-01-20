package com.ariari.ariari.domain.club.review.repository;

import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.reviewtag.ClubReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubReviewTagRepository extends JpaRepository<ClubReviewTag, Long> {
    List<ClubReviewTag> findByClubReview(ClubReview clubReview);
    @Query("SELECT crt FROM ClubReviewTag crt " +
            "JOIN crt.clubReview cr " +
            "JOIN cr.clubMember cm " +
            "JOIN cm.club c " +
            "WHERE c.id = :clubId")
    List<ClubReviewTag> findByClubId(@Param("clubId") Long clubId);
}
