package com.ariari.ariari.domain.club.review;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClubReview {

    @Id @CustomPkGenerate
    @Column(name = "club_review_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 2000)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

}
