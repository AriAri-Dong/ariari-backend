package com.ariari.ariari.domain.club.review.access;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@SQLRestriction("deleted_date_time is null")
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

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private LocalDateTime deletedDateTime;

    public ClubReviewAccess(Member member, ClubReview clubReview) {
        this.member = member;
        this.clubReview = clubReview;
    }
}
