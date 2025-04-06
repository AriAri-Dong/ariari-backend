package com.ariari.ariari.domain.club.review;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import com.ariari.ariari.domain.club.review.reviewtag.ClubReviewTag;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club_review SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_review_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubReview extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_review_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 2000)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "clubReview", cascade = CascadeType.PERSIST)
    private List<ClubReviewTag> clubReviewTags = new ArrayList<>();

    public ClubReview(String title, String body, Club club, Member member) {
        this.title = title;
        this.body = body;
        this.club = club;
        this.member = member;
    }

}
