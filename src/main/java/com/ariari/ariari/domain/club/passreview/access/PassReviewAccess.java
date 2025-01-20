package com.ariari.ariari.domain.club.passreview.access;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.passreview.PassReview;
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
public class PassReviewAccess {

    @Id @CustomPkGenerate
    @Column(name = "pass_review_access_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pass_review_id")
    private PassReview passReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private LocalDateTime deletedDateTime;

    public PassReviewAccess(PassReview passReview, Member member) {
        this.passReview = passReview;
        this.member = member;
    }
}
