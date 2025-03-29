package com.ariari.ariari.domain.club.review.repository;

import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.reviewtag.ClubReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubReviewTagRepository extends JpaRepository<ClubReviewTag, Long> {
    List<ClubReviewTag> findByClubReview(ClubReview clubReview);

    List<ClubReviewTag> findByClubReview_Club_Id(Long clubId);
}
