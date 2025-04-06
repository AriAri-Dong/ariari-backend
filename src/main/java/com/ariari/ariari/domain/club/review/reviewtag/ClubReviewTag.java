package com.ariari.ariari.domain.club.review.reviewtag;


import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.tag.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club_review_tag SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_review_tag_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubReviewTag extends LogicalDeleteEntity {

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

    public ClubReviewTag(Tag tag, ClubReview clubReview){
        this.tag = tag;
        this.clubReview = clubReview;
    }

    public ClubReviewTag(Tag tag){
        this.tag = tag;
    }
}
