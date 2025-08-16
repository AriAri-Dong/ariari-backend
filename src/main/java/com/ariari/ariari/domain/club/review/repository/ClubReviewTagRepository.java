package com.ariari.ariari.domain.club.review.repository;

import com.ariari.ariari.commons.entity.ClubReview;
import com.ariari.ariari.commons.entity.ClubReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubReviewTagRepository extends JpaRepository<ClubReviewTag, Long> {
    List<ClubReviewTag> findByClubReview(ClubReview clubReview);

    List<ClubReviewTag> findByClubReview_Club_Id(Long clubId);
}
