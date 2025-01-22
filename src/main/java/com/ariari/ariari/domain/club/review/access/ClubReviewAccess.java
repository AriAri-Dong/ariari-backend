package com.ariari.ariari.domain.club.review.access;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club_review_access SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_review_access_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubReviewAccess extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_review_access_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_review_id")
    private ClubReview clubReview;

    public ClubReviewAccess(Member member, ClubReview clubReview) {
        this.member = member;
        this.clubReview = clubReview;
    }
}
