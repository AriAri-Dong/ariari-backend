package com.ariari.ariari.domain.club.passreview.access;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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

}
