package com.ariari.ariari.domain.club.review.access;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClubReviewAccess {

    @Id @CustomPkGenerate
    @Column(name = "club_review_access_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_review_id")
    private ClubReview clubReview;
}
