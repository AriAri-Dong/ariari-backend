package com.ariari.ariari.domain.club.passreview.access;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE pass_review_access SET deleted_date_time= CURRENT_TIMESTAMP WHERE pass_review_access_id= ?")
@SQLRestriction("deleted_date_time is null")
public class PassReviewAccess extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "pass_review_access_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pass_review_id")
    private PassReview passReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public PassReviewAccess(PassReview passReview, Member member) {
        this.passReview = passReview;
        this.member = member;
    }
}
