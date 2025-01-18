package com.ariari.ariari.domain.club.review;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.review.reviewtag.ClubReviewTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@SQLRestriction("deleted_date_time is null")
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

    @OneToMany(mappedBy = "clubReview", cascade = CascadeType.PERSIST)
    private List<ClubReviewTag> clubReviewTags = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private LocalDateTime deletedDateTime;

    public ClubReview(String title, String body, ClubMember clubMember) {
        this.title = title;
        this.body = body;
        this.clubMember = clubMember;
    }
}
