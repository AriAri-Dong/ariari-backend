package com.ariari.ariari.domain.club.passreview.repository;

import com.ariari.ariari.domain.club.passreview.PassReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasAuthenticationCallbackHandler;

import java.util.Optional;

public interface PassReviewRepository extends JpaRepository<PassReview, Long> {
    Optional<PassReview> findByClubMemberClubId(Long clubId);
}
