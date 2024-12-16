package com.ariari.ariari.domain.club.passreview.note;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.passreview.PassReview;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class PassReviewNote {

    @Id @CustomPkGenerate
    @Column(name = "pass_review_note_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 200)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pass_review_id")
    private PassReview passReview;

}
