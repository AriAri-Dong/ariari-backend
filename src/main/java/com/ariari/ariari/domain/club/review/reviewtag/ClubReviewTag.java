package com.ariari.ariari.domain.club.review.reviewtag;


import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.tag.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClubReviewTag {

    @Id @CustomPkGenerate
    @Column(name = "club_review_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_review_id")
    private ClubReview clubReview;

}
