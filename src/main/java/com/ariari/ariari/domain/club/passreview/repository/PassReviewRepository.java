package com.ariari.ariari.domain.club.passreview.repository;

import com.ariari.ariari.domain.club.passreview.PassReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassReviewRepository extends JpaRepository<PassReview, Long> {
    // 삭제시간 널로 검색?
}
