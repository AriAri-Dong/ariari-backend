package com.ariari.ariari.domain.club.review.reviewtag;


import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.tag.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@SQLRestriction("deleted_date_time is null")
@Getter
public class ClubReviewTag {

    @Id @CustomPkGenerate
    @Column(name = "club_review_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "club_review_id")
    private ClubReview clubReview;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private LocalDateTime deletedDateTime;

    public ClubReviewTag(Tag tag, ClubReview clubReview){
        this.tag = tag;
        this.clubReview = clubReview;
    }
}
